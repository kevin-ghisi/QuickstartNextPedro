package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.hardware.ServoToPosition;

public class Outake extends Subsystem {
    public static final Outake INSTANCE = new Outake();

    private Outake() {}

    public Servo garra;
    public String nome = "Out";

    public Command abrir() {
        return new ServoToPosition(garra, 0.8, this);
    }

    public Command fechar() {
        return new ServoToPosition(garra, 0.4, this);
    }

    @Override
    public void initialize() {
        garra = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, nome);
    }

}
