package org.firstinspires.ftc.teamcode.example.java;

import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;


public class Lift extends Subsystem {
    // BOILERPLATE
    public static final Lift INSTANCE = new Lift();
    private Lift() { }

    public MotorEx viperL, viperR;
    public MotorGroup sliders;

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);

    public Command resetZero() {
        return new InstantCommand(() -> { viperL.resetEncoder(); viperR.resetEncoder(); });
    }

    public String viperR_nome = "Viper2";
    public String viperL_nome = "Viper1";

    public Command toLow() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toMiddle() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                500.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHigh() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                1200.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }
    
    @Override
    public void initialize() {
//        motor = new MotorEx(name);

        viperR = new MotorEx(viperR_nome).reverse();
        viperL = new MotorEx(viperL_nome);

        sliders = new MotorGroup(viperR, viperL);
    }
}
