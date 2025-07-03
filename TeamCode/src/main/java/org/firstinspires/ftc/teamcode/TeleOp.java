package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelRaceGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import org.firstinspires.ftc.teamcode.config.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeHand;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeSlider;
import org.firstinspires.ftc.teamcode.config.subsystems.OutakeClaw;
import org.firstinspires.ftc.teamcode.config.subsystems.OutakeAncon;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "BatTeleOP")
public class TeleOp extends NextFTCOpMode {
    public TeleOp() {
        super(OutakeAncon.INSTANCE, OutakeClaw.INSTANCE, IntakeClaw.INSTANCE, IntakeHand.INSTANCE, IntakeSlider.INSTANCE, Elevator.INSTANCE);
    }

    // Change the motor names to suit your robot.
    public String frontLeftName = "leftFront";
    public String frontRightName = "rightFront";
    public String backLeftName = "leftRear";
    public String backRightName = "rightRear";

    public MotorEx frontLeftMotor;
    public MotorEx frontRightMotor;
    public MotorEx backLeftMotor;
    public MotorEx backRightMotor;

    public MotorEx[] motors;

    public Command driverControlled;

    @Override
    public void onInit() {
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        // Change the motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new MotorEx[] {frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};

        OutakeClaw.INSTANCE.Abrir().invoke();
        OutakeAncon.INSTANCE.Transfer().invoke();
        IntakeClaw.INSTANCE.piqueOpenTele().invoke();
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), true);
        driverControlled.invoke();

        //-=-=-=-=-=Gamepad1=-=-=-=-=-

        //-----Collet

        gamepadManager.getGamepad1().getLeftTrigger().setHeldCommand(
                value -> new SequentialGroup(
                        IntakeSlider.INSTANCE.Pala()
                )
        );

        gamepadManager.getGamepad1().getRightTrigger().setHeldCommand(
                value -> new SequentialGroup(
                        IntakeSlider.INSTANCE.Paca()
                )
        );

        //-----Maum

        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(IntakeHand.INSTANCE::Transferencia);

        gamepadManager.getGamepad1().getDpadDown().setPressedCommand(IntakeHand.INSTANCE::Coleta);

        //-----Pinca

        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(IntakeClaw.INSTANCE::piqueOpenTele);

        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(IntakeClaw.INSTANCE::piqueClose);

        //-----Phtero

        gamepadManager.getGamepad1().getY().setPressedCommand(IntakeClaw.INSTANCE::ptheroInitPosYaw);

        gamepadManager.getGamepad1().getX().setPressedCommand(IntakeHand.INSTANCE::Metade);

        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(IntakeClaw.INSTANCE::ptheroRotatePlusYaw);

        gamepadManager.getGamepad1().getDpadRight().setPressedCommand(IntakeClaw.INSTANCE::ptheroRotateMinusYaw);

        //-----Auto

        gamepadManager.getGamepad1().getBack().setPressedCommand(Elevator.INSTANCE::elevatorToTransfer);

        gamepadManager.getGamepad1().getA().setPressedCommand(
                () -> new SequentialGroup(
                        new ParallelGroup(
                                IntakeClaw.INSTANCE.ptheroInitPosYaw(),
                                IntakeHand.INSTANCE.Transferencia(),
                                OutakeAncon.INSTANCE.Transfer(),
                                OutakeClaw.INSTANCE.Abrir(),
                                IntakeSlider.INSTANCE.TransferenciaTele()
                        ),
                        new Delay(0.2),
                        OutakeClaw.INSTANCE.Fechar(),
                        new Delay(0.2),
                        IntakeClaw.INSTANCE.piqueOpenTele()
                )
        );

        //-=-=-=-=-=Gamepad2=-=-=-=-=-

        //-----Out

        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(OutakeClaw.INSTANCE::Abrir);

        gamepadManager.getGamepad2().getRightBumper().setPressedCommand(OutakeClaw.INSTANCE::Fechar);

        //-----Elevator

        gamepadManager.getGamepad2().getBack().setHeldCommand(Elevator.INSTANCE::negativo);
        gamepadManager.getGamepad2().getBack().setReleasedCommand(Elevator.INSTANCE::resetPosition);

        gamepadManager.getGamepad2().getA().setPressedCommand(
                () -> new SequentialGroup(
                        Elevator.INSTANCE.elevatorToLow()
                )
        );
        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> new SequentialGroup(
                        Elevator.INSTANCE.elevatorToScore()
                )
        );
        gamepadManager.getGamepad2().getX().setPressedCommand(
                () -> new SequentialGroup(
                        Elevator.INSTANCE.elevatorToMiddle()
                )
        );
        gamepadManager.getGamepad2().getY().setPressedCommand(
                () -> new SequentialGroup(
                        Elevator.INSTANCE.elevatorToHight()
                )
        );

        //-----Ancon

        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(
                () -> new SequentialGroup(
                        OutakeAncon.INSTANCE.Transfer()
                )
        );
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(
                () -> new SequentialGroup(
                        OutakeAncon.INSTANCE.Basket()
                )
        );
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(
                () -> new SequentialGroup(
                        OutakeAncon.INSTANCE.Clip()
                )
        );

        //-----

        //gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Outake.INSTANCE::score);

        //new Delay(TimeSpan.fromSec(1)),
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void onUpdate(){
        telemetry.addData("Collet", IntakeSlider.INSTANCE.collet.getCurrentPosition());
        telemetry.addData("V1", Elevator.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("V2", Elevator.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("CoreHand", IntakeHand.INSTANCE.hand.getCurrentPosition());
        telemetry.addData("rightFinger", IntakeClaw.INSTANCE.rightFinger.getPosition());
        telemetry.addData("leftFinger", IntakeClaw.INSTANCE.leftFinger.getPosition());
        telemetry.addData("yawClaw", IntakeClaw.INSTANCE.phtero.getPosition());
        telemetry.addData("ancon", OutakeAncon.INSTANCE.anconR.getPosition());
        telemetry.update();
    }

}