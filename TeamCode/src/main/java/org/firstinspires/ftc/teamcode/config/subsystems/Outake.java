package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

import java.util.List;

public class Outake extends Subsystem {
    public static final Outake INSTANCE = new Outake();

    private Outake() {}

    public Servo garra, anconL, anconR;
    public String nome_garra = "Out";
    public String nome_anconL = "Ancon2";
    public String nome_anconR = "Ancon1";

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
                anconL, anconR
        ), 0.3, this);
    }

    public Command score() {
        return new MultipleServosToPosition(List.of(
                anconL, anconR
        ), 1, this);
    }

    @Override
    public void initialize() {
        garra = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_garra);
        anconL = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_anconL);
        anconR = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome_anconR);

        anconL.setDirection(Servo.Direction.REVERSE);
    }

}
