package org.firstinspires.ftc.teamcode.config.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class IntakeClaw extends Subsystem {
    public static final IntakeClaw INSTANCE = new IntakeClaw();
    private IntakeClaw() { }

    public Servo rightFinger, leftFinger, phtero;

    public String RightFinger  = "PiquePique1";
    public String LeftFinger  = "PiquePique2";

    public String Phtero = "Phtero";

    //-=-=-=-=-=+=-=-=-=-=-

    //-----PiquePique

    public Command piqueOpen() {
        return new ParallelGroup(
                new ServoToPosition(rightFinger, 0.45, this),
                new ServoToPosition(leftFinger, 0.5, this)
        );
    }

    public Command piqueOpenTele() {
        return new ParallelGroup(
                new ServoToPosition(rightFinger, 0.6, this),
                new ServoToPosition(leftFinger, 0.65, this)
        );
    }

    public Command piqueClose() {
        return new ParallelGroup(
                new ServoToPosition(rightFinger, 0.896, this),
                new ServoToPosition(leftFinger, 0.946, this)
        );
    }

    //-----Phtero

    public Command ptheroInitPosYaw() {
        return new ServoToPosition(phtero, // SERVO TO MOVE
                0.5, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command ptheroMinusYaw() {
        return new ServoToPosition(phtero, // SERVO TO MOVE
                0.35, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command ptheroRotatePlusYaw() {
        double posAtual = phtero.getPosition();
        return new ServoToPosition(phtero, // SERVO TO MOVE
                Math.min(posAtual+0.1, 0.7), // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command ptheroRotateMinusYaw() {
        double posAtual = phtero.getPosition();
        return new ServoToPosition(phtero, // SERVO TO MOVE
                Math.max(posAtual-0.1, 0.2), // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        phtero = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Phtero);
        phtero.setPosition(0.5);

        leftFinger = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, LeftFinger);
        rightFinger = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, RightFinger);
        rightFinger.setDirection(Servo.Direction.REVERSE);
        leftFinger.setPosition(0.5);
        rightFinger.setPosition(0.45);
    }
}