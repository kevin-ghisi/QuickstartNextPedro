package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelDeadlineGroup;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelRaceGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
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
    public PIDFController controller = new PIDFController(0.013, 0.0001, 0.0005,kF,100);

    public PIDFController controllerLow = new PIDFController(0.013, 0.0001, 0.0005,kF,300);
    public PIDFController holdController = new PIDFController(0.013, 0.0001, 0.0005,kF,200);

    public String viperR_nome = "Viper2";
    public String viperL_nome = "Viper1";

    public Command resetZero() {
        return new InstantCommand(() -> {
            viperR.resetEncoder();
            viperL.resetEncoder();
        });
    }

    //-=-=-=-=-=+=-=-=-=-=-

    //-----Elevator

    public Command elevatorToLow() {
        return new RunToPosition(sliders, 0, controller, this);
    }
    public Command elevatorToLowAuto() {
        return new SequentialGroup(
                new ParallelRaceGroup(
                        new RunToPosition(sliders, 30, controllerLow, this),
                        new Delay(2)
                ),
                new RunToPosition(sliders, sliders.getCurrentPosition(), controllerLow,this),
                new SetPower(sliders, -0.5, this),
                new Delay(1),
                new SetPower(sliders, 0, this)
        );
    }

    public Command elevatorManter() {
        double pos = sliders.getCurrentPosition();
        return new RunToPosition(sliders, pos, controller, this);
    }

    public Command elevatorToTransfer() {
        return new ParallelRaceGroup(
                new RunToPosition(sliders, 175, controller, this),
                new Delay(2)
        );
    }

    public Command elevatorToScore() {
        return new ParallelRaceGroup(
                new RunToPosition(sliders, 900, controller, this),
                new Delay(2)
        );
    }

    public Command elevatorToMiddle() {
        return new ParallelRaceGroup(
                new RunToPosition(sliders, 1200, controller, this),
                new Delay(2)

        );
    }

    public Command elevatorToHight() {
        return new RunToPosition(sliders, 3000, controller, this);
    }

    public Command getDefaultCommand() {
        return new HoldPosition(sliders, holdController, this);
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