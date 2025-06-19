package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.units.TimeSpan;
import com.rowanmcalpin.nextftc.ftc.NextFTCOpMode;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;

import org.firstinspires.ftc.teamcode.config.subsystems.Outake;


public class TeleOp extends NextFTCOpMode {

    public TeleOp() {
        super(Outake.INSTANCE);
    }

    // Change the motor names to suit your robot.
    public String frontLeftName = "front_left";
    public String frontRightName = "front_right";
    public String backLeftName = "back_left";
    public String backRightName = "back_right";

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
    }

    @Override
    public   void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1(), true);
        driverControlled.invoke();

        gamepadManager.getGamepad2().getX().setPressedCommand(Outake.INSTANCE::abrir);
        gamepadManager.getGamepad2().getY().setPressedCommand(Outake.INSTANCE::fechar);


        gamepadManager.getGamepad2().getB().setPressedCommand(
                () -> new SequentialGroup(
                        Outake.INSTANCE.abrir(),
                        new Delay(TimeSpan.fromSec(1)),
                        Outake.INSTANCE.fechar()
                )
        );

    }
}
