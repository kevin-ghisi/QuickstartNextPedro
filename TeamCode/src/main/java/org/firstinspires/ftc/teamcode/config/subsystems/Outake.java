package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;

import java.util.List;

public class Outake extends Subsystem {
    public static final Outake INSTANCE = new Outake();

    private Outake() {}

    public MotorEx viperL, viperR;
    public MotorGroup sliders;

    public Servo garra, pivot_Right, pivot_Left;
    public String nome_garra = "claw";
    public String nome_pivot_Right = "pivot_right";
    public String nome_pivot_Left = "pivot_left";
    public String viperR_nome = "outake_viper_right";
    public String viperL_nome = "outake_viper_left";

    public PIDFController viper_PIDF = new PIDFController(0.005, 0.0, 0.0);

    public Command resetZero() {
        return new InstantCommand(() -> { viperL.resetEncoder(); viperR.resetEncoder(); });
    }

    public Command abrir() {
        return new ServoToPosition(garra, 0.8, this);
    }

    public Command fechar() {
        return new ServoToPosition(garra, 0.4, this);
    }

    public Command centro() {
        return new ServoToPosition(garra, 0.6, this);
    }

    public Command transfer() {
        return new MultipleServosToPosition(List.of(
                pivot_Right, pivot_Left
        ), 0.3, this);
    }

    public Command score() {
        return new MultipleServosToPosition(List.of(
                pivot_Right, pivot_Left
        ), 1, this);
    }

    @Override
    public void initialize() {
        garra = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_garra);
        pivot_Right = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_pivot_Right);
        pivot_Left = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_pivot_Left);

        pivot_Right.setDirection(Servo.Direction.REVERSE);
    }

}
