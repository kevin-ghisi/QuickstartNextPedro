package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
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

public class Elevator extends Subsystem {
    public static final Elevator INSTANCE = new Elevator();

    private Elevator() {}

    public MotorEx viperL, viperR;

    public MotorGroup sliders;

    //public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);
    Feedforward kF = new Feedforward() {
        @Override
        public double compute(double v) {
            return 0;
        }
    };
    public PIDFController controller = new PIDFController(0.013, 0.0, 0.0005,kF,100);

    public String viperR_nome = "Viper2";
    public String viperL_nome = "Viper1";




    //-=-=-=-=-=+=-=-=-=-=-

    //-----Elevator

    public Command elevatorToLow() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                0, // TARGET POSITION, IN TICKS
                controller); // IMPLEMENTED SUBSYSTEM
    }

    public Command elevatorToTransfer() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                175, // TARGET POSITION, IN TICKS
                controller); // IMPLEMENTED SUBSYSTEM

    }

    public Command elevatorToScore() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                900, // TARGET POSITION, IN TICKS
                controller); // IMPLEMENTED SUBSYSTEM
    }

    public Command elevatorToMiddle() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                1200, // TARGET POSITION, IN TICKS
                controller); // IMPLEMENTED SUBSYSTEM
    }

    public Command elevatorToHight() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                3000, // TARGET POSITION, IN TICKS
                controller); // IMPLEMENTED SUBSYSTEM
    }

    public Command getDefaultCommand() {
        return new HoldPosition(sliders, controller, this);
    }
    //-=-=-=-=-=+=-=-=-=-=-

    @Override
    public void initialize() {
        viperR = new MotorEx(viperR_nome).reverse();
        viperL = new MotorEx(viperL_nome);

        viperR.resetEncoder();
        viperL.resetEncoder();

        sliders = new MotorGroup(viperR, viperL);
    }

}
