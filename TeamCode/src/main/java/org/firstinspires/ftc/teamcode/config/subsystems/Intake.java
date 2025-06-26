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

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    public MotorEx collet;

    public Servo rightFinger, leftFinger, phtero;

    //-----

    public String intake_nome = "Collet";

    //-----

    public String hand_name = "Maum";

    //-----

    public String RightFinger  = "PiquePique1";
    public String LeftFinger  = "PiquePique2";

    //-----

    public String Phtero = "Phtero";

    //-----
    //public PIDFController controller = new PIDFController(0.022, 0.0001, 0.0001);
    //public PIDFController colletPID = new PIDFController(0.015, 0.00005, 0.00005);

    public PIDFController colletPID = new PIDFController(0.015, 0.00005, 0.00005);

    public Command resetZero() {
        return new InstantCommand(() -> {
            collet.resetEncoder();
        });
    }

    //-=-=-=-=-=+=-=-=-=-=-

    //-----Collet

    public Command colletPaca() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet, // MOTOR TO MOVE
                Math.min(posAtual +75,1500), // TARGET POSITION, IN TICKS
                colletPID,
                this);
         // IMPLEMENTED SUBSYSTEM
    }

    public Command colletPala() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet,
                Math.max(posAtual - 75,0),
                colletPID,
                this);

    }

    //-----

    public Command colletTransferencia() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet, // MOTOR TO MOVE
                235, // TARGET POSITION, IN TICKS
                colletPID,
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command colletClip() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet, // MOTOR TO MOVE
                1500, // TARGET POSITION, IN TICKS
                colletPID,
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command colletRetrair() {
        return new RunToPosition(collet, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                colletPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command colletColeta() {
        return new RunToPosition(collet, // MOTOR TO MOVE
                1250, // TARGET POSITION, IN TICKS
                colletPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    //-----Maum


    //-----PiquePique

    public Command piqueOpenRightFinger() {
        return new ServoToPosition(rightFinger, // SERVO TO MOVE
                0.6, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command piqueOpenLeftFinger() {
        return new ServoToPosition(leftFinger, // SERVO TO MOVE
                0.45, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
    public Command piqueOpenRightFingerAuto() {
        return new ServoToPosition(rightFinger, // SERVO TO MOVE
                0.7, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command piqueOpenLeftFingerAuto() {
        return new ServoToPosition(leftFinger, // SERVO TO MOVE
                0.35, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }


    /*
    public Command piqueCloseRightFinger() {
        return new ServoToPosition(rightFinger, // SERVO TO MOVE
                0.1125, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
     */
    public Command piqueCloseRightFinger() {
        return new ServoToPosition(rightFinger, // SERVO TO MOVE
                0.1025, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }

    /*
    public Command piqueCloseLeftFinger() {
        return new ServoToPosition(leftFinger, // SERVO TO MOVE
                0.9375, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
    }
     */
    public Command piqueCloseLeftFinger() {
        return new ServoToPosition(leftFinger, // SERVO TO MOVE
                0.9475, // POSITION TO MOVE TO
                this); // IMPLEMENTED SUBSYSTEM
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

    public Command getDefaultCommand() {
        return new HoldPosition(collet, colletPID, this);
    }

    @Override
    public void initialize() {
        collet = new MotorEx(intake_nome);
        collet.setDirection(DcMotorSimple.Direction.REVERSE);

        phtero = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Phtero);
        phtero.setPosition(0.5);

        leftFinger = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, LeftFinger);
        rightFinger = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, RightFinger);
        leftFinger.setPosition(0.6);
        rightFinger.setPosition(0.45);


    }
}
