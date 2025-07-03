package org.firstinspires.ftc.teamcode.config.subsystems;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class IntakeSlider extends Subsystem {
    public static final IntakeSlider INSTANCE = new IntakeSlider();
    private IntakeSlider() { }

    public MotorEx collet;

    public String intake_nome = "Collet";

    public PIDFController teleColletPID = new PIDFController(0.013, 0.00005, 0.00005);

    public PIDFController teleColletPID2 = new PIDFController(0.008, 0.00005, 0.00005);

    public PIDFController colletPID = new PIDFController(0.013, 0.00005, 0.00005);

    public PIDFController handColletPID = new PIDFController(0.017, 0.00008, 0.00008);

    public Command resetZero() {
        return new InstantCommand(() -> {
            collet.resetEncoder();
        });
    }

    //-=-=-=-=-=+=-=-=-=-=-

    public Command Paca() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet, // MOTOR TO MOVE
                Math.min(posAtual +125,1500), // TARGET POSITION, IN TICKS
                colletPID,
                this);
        // IMPLEMENTED SUBSYSTEM
    }

    public Command Pala() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet,
                Math.max(posAtual - 75,0),
                teleColletPID,
                this);

    }

    public Command Transferencia() {
        double posAtual = collet.getCurrentPosition();
        return new SequentialGroup(
                new RunToPosition(collet, 250, colletPID, this),
                new Delay(0.1),
                new RunToPosition(collet, 250, colletPID, this)
        );
    }

    public Command TransferenciaTele() {
        return new SequentialGroup(
                new RunToPosition(collet, 190, teleColletPID2, this),
                new Delay(0.1),
                new RunToPosition(collet, 190, teleColletPID2, this)
        );
    }

    public Command Clip() {
        double posAtual = collet.getCurrentPosition();
        return new RunToPosition(
                collet, // MOTOR TO MOVE
                1500, // TARGET POSITION, IN TICKS
                colletPID,
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command Retrair() {
        return new RunToPosition(collet, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                colletPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command Coleta() {
        return new RunToPosition(collet, // MOTOR TO MOVE
                1250, // TARGET POSITION, IN TICKS
                colletPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command getDefaultCommand() {
        return new HoldPosition(collet, colletPID, this);
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        collet = new MotorEx(intake_nome);
        collet.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}