package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.command.utility.delays.WaitUntil;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.DebugLogLevelParameters;
import org.firstinspires.ftc.teamcode.config.subsystems.GlobalPositions;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Outake;
import org.firstinspires.ftc.teamcode.config.subsystems.Vipers;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Tele OP Bat Tech")
public class TeleOp extends NextFTCOpMode {

    public TeleOp() {
        super(Outake.INSTANCE, Vipers.INSTANCE, Intake.INSTANCE);
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

//        MotorEx intake = new MotorEx("Collet");

        // Change the motor directions to suit your robot.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        motors = new MotorEx[] {frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};

//        define qual tipo de modo de jogo é e também a aliança à ser jogada;
        GlobalPositions.opModeType    = GlobalPositions.OpModeType.TELEOP;
        GlobalPositions.alliance      = GlobalPositions.Alliance.RED;
        GlobalPositions.scorePosition = GlobalPositions.ScorePosition.HIGH_BASKET;


        new ParallelGroup(
                Outake.INSTANCE.centro()
        ).invoke();


//        Intake.INSTANCE.retrair();

        new InstantCommand(Intake.INSTANCE::toHome);

        new InstantCommand(() -> {
//            new RunToPosition(intake, 0,  this);
            Intake.INSTANCE.toHome();
        });
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), true);
        driverControlled.invoke();

        gamepadManager.getGamepad1().getLeftBumper().setHeldCommand(
                () -> new SequentialGroup(
                        Intake.INSTANCE.toIntake(),
//                        new WaitUntil()
                        new Delay(.3),
                        new ParallelGroup(
                                Intake.INSTANCE.toTransfer(),
                                Outake.INSTANCE.transfer()
                        )
                )
        );





        gamepadManager.getGamepad2().getX().setPressedCommand(Outake.INSTANCE::abrir);
        gamepadManager.getGamepad2().getY().setPressedCommand(Outake.INSTANCE::fechar);


        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.abrir(),
                        new Delay(TimeSpan.fromSec(1)),
                        Outake.INSTANCE.fechar()
                )
        );

        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.score(),
                        new Delay(TimeSpan.fromSec(0.5)),
                        Outake.INSTANCE.fechar()
                )
        );

        gamepadManager.getGamepad1().getDpadDown().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.transfer(),
                        new Delay(TimeSpan.fromSec(0.5)),
                        Outake.INSTANCE.abrir()
                )
        );

        gamepadManager.getGamepad2().getDpadDown().setPressedCommand(Vipers.INSTANCE::toLow);
        gamepadManager.getGamepad2().getDpadRight().setPressedCommand(Vipers.INSTANCE::toMiddle);

        gamepadManager.getGamepad1().getDpadRight().setPressedCommand(Intake.INSTANCE::toIntake);
        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(Intake.INSTANCE::toTransfer);

    }
    @Override
    public void onUpdate() {
        telemetry.addData("Pos Intake", Intake.INSTANCE.intake_slider.getCurrentPosition());
        telemetry.update();
    }

}