package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.conditionals.BlockingConditionalCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.core.control.controllers.feedforward.Feedforward;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;

import java.util.List;

public class OutakeAncon extends Subsystem {
    public static final OutakeAncon INSTANCE = new OutakeAncon();

    private OutakeAncon() {}

    public Servo anconL, anconR;

    public Command resetZero() {
        return new InstantCommand(() -> {  });
    }

    public String nome_anconL = "Ancon2";
    public String nome_anconR = "Ancon1";

    //-=-=-=-=-=+=-=-=-=-=-

    public Command Transfer() {
        return new MultipleServosToPosition(List.of(
                anconL, anconR
        ), 0.4);
    }

    public Command Clip() {
        return new MultipleServosToPosition(List.of(
                anconL, anconR
        ), 0.7);
    }

    public Command Basket() {
        return new MultipleServosToPosition(List.of(
                anconL, anconR
        ), 0.94);
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        anconL = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_anconL);
        anconR = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_anconR);
        anconL.setDirection(Servo.Direction.REVERSE);
    }

}