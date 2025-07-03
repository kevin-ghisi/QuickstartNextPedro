package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

public class IntakeHand extends Subsystem {
    public static final IntakeHand INSTANCE = new IntakeHand();
    private IntakeHand() { }

    public MotorEx hand;

    public String hand_name = "Maum";

    public PIDFController coreHexPID = new PIDFController(0.013, 0.001, 0.0001);
    public PIDFController coreHexPIDAutonomo = new PIDFController(0.008, 0.001, 0.0001);

    public Command resetZero() {
        return new InstantCommand(() -> {
            hand.resetEncoder();
        });
    }

    //-=-=-=-=-=+=-=-=-=-=-

    public Command Manter() {
        double pos = hand.getCurrentPosition();
        return new RunToPosition(hand, // MOTOR TO MOVE
                pos, // TARGET POSITION, IN TICKS
                coreHexPID,this); // IMPLEMENTED SUBSYSTEM

    }

    public Command Coleta() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                180, // TARGET POSITION, IN TICKS
                coreHexPID, // CONTROLLER TO IMPLEMENT
                this);
    }

    public Command ColetaAutonomo() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                        165, // TARGET POSITION, IN TICKS
                        coreHexPID, // CONTROLLER TO IMPLEMENT
                        this);
    }

    public Command Metade() {
        return new SequentialGroup(
                new RunToPosition(hand, // MOTOR TO MOVE
                        80, // TARGET POSITION, IN TICKS
                        coreHexPIDAutonomo, // CONTROLLER TO IMPLEMENT
                        this) // IMPLEMENTED SUBSYSTEM
        );
    }

    public Command Transferencia() {
        return new SequentialGroup(
                new RunToPosition(hand, // MOTOR TO MOVE
                        0, // TARGET POSITION, IN TICKS
                        coreHexPID, // CONTROLLER TO IMPLEMENT
                        this), // IMPLEMENTED SUBSYSTEM
                new SetPower(hand, -0.2,this),
                new Delay(0.3),
                new SetPower(hand, 0,this)
        );


    }

    public Command paCima() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                Math.min(hand.getCurrentPosition()+80, 160), // TARGET POSITION, IN TICKS
                coreHexPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command paBaixo() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                Math.max(hand.getCurrentPosition()-80, 0), // TARGET POSITION, IN TICKS
                coreHexPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command getDefaultCommand() {
        return new HoldPosition(hand, coreHexPID, this);
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        hand = new MotorEx(hand_name);
        hand.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}