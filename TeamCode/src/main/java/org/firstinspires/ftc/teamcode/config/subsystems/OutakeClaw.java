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

public class OutakeClaw extends Subsystem {
    public static final OutakeClaw INSTANCE = new OutakeClaw();

    private OutakeClaw() {}

    public Servo garra;

    public Command resetZero() {
        return new InstantCommand(() -> {  });
    }

    public String nome_garra = "Out";

    //-=-=-=-=-=+=-=-=-=-=-

    public Command Abrir() {
        return new ServoToPosition(garra, 0.4, this);
    }

    public Command Fechar() {
        return new ServoToPosition(garra, 0.2, this);
    }

    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        garra = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_garra);
    }

}