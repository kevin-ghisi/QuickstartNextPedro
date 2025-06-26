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

import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Maum;
import org.firstinspires.ftc.teamcode.config.subsystems.Outake;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "BatTeleOP")
public class TeleOp extends NextFTCOpMode {
    public static float powerRight ;
    public static float powerLeft;
    public TeleOp() {
        super(Outake.INSTANCE, Intake.INSTANCE, Maum.INSTANCE);
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

        Outake.INSTANCE.outAbrir();
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), true);
        driverControlled.invoke();

        powerRight = gamepad1.right_trigger;
        powerLeft = gamepad1.left_trigger;

        //-=-=-=-=-=Gamepad1=-=-=-=-=-

        //-----Collet

        gamepadManager.getGamepad1().getLeftTrigger().setHeldCommand(
                value -> new SequentialGroup(
                        Intake.INSTANCE.colletPala()
                )
        );

        gamepadManager.getGamepad1().getRightTrigger().setHeldCommand(
                value -> new SequentialGroup(
                        Intake.INSTANCE.colletPaca()
                )
        );

        //-----Maum

        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Maum.INSTANCE::maumTransferencia);

        gamepadManager.getGamepad1().getDpadDown().setPressedCommand(Maum.INSTANCE::maumColeta);

        //-----Pinca

        gamepadManager.getGamepad1().getLeftBumper().setPressedCommand(
                () -> new ParallelGroup(
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        Intake.INSTANCE.piqueOpenRightFinger()
                )
        );

        gamepadManager.getGamepad1().getRightBumper().setPressedCommand(
                () -> new ParallelGroup(
                        Intake.INSTANCE.piqueCloseLeftFinger(),
                        Intake.INSTANCE.piqueCloseRightFinger()
                )
        );

        //-----Phtero

        gamepadManager.getGamepad1().getY().setPressedCommand(Intake.INSTANCE::ptheroInitPosYaw);

        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(Intake.INSTANCE::ptheroRotatePlusYaw);

        gamepadManager.getGamepad1().getDpadRight().setPressedCommand(Intake.INSTANCE::ptheroRotateMinusYaw);

        //-----Auto

        gamepadManager.getGamepad1().getBack().setPressedCommand(Outake.INSTANCE::elevatorToTransfer);

        gamepadManager.getGamepad1().getA().setPressedCommand(
            () -> new SequentialGroup(
                    new ParallelGroup(
                            Intake.INSTANCE.ptheroInitPosYaw(),
                            Maum.INSTANCE.maumTransferencia(),
                            Outake.INSTANCE.anconTransfer(),
                            Outake.INSTANCE.outAbrir()
                    ),
                    Intake.INSTANCE.colletTransferencia(),
                    new Delay(0.3),
                    Outake.INSTANCE.outFechar(),
                    new Delay(0.5),
                    new ParallelGroup(
                            Intake.INSTANCE.piqueOpenLeftFinger(),
                            Intake.INSTANCE.piqueOpenRightFinger()
                    )
            )
        );

        //-=-=-=-=-=Gamepad2=-=-=-=-=-

        //-----Out

        gamepadManager.getGamepad2().getLeftBumper().setPressedCommand(Outake.INSTANCE::outAbrir);

        gamepadManager.getGamepad2().getRightBumper().setPressedCommand(Outake.INSTANCE::outFechar);

        //-----Elevator

        gamepadManager.getGamepad2().getA().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.elevatorToLow()
                )
        );
        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.elevatorToScore()
                )
        );
        gamepadManager.getGamepad2().getX().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.elevatorToMiddle()
                )
        );
        gamepadManager.getGamepad2().getY().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.elevatorToHight()
                )
        );

        //-----Ancon

        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.anconTransfer()
                )
        );
        gamepadManager.getGamepad2().getDpadUp().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.anconBasket()
                )
        );
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.anconClip()
                )
        );

        //-----

        //gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Outake.INSTANCE::score);

        //new Delay(TimeSpan.fromSec(1)),
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void onUpdate(){
        telemetry.addData("Collet", Intake.INSTANCE.collet.getCurrentPosition());
        telemetry.addData("V1", Outake.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("V2", Outake.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("CoreHand", Maum.INSTANCE.hand.getCurrentPosition());
        telemetry.addData("rightFinger", Intake.INSTANCE.rightFinger.getPosition());
        telemetry.addData("leftFinger", Intake.INSTANCE.leftFinger.getPosition());
        telemetry.addData("yawClaw", Intake.INSTANCE.phtero.getPosition());
        telemetry.addData("ancon", Outake.INSTANCE.anconR.getPosition());

        if(gamepad1.a){
            telemetry.addData("botão apertado autonomo", "sim");
        } else {
            telemetry.addData("botão apertado autonomo", "nao");
        }
        telemetry.update();
    }

}
