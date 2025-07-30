package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class Vipers extends Subsystem {
    public static final Vipers INSTANCE = new Vipers();
    private Vipers() { }

    public MotorEx viperL, viperR;
    public MotorGroup sliders;

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);

    public Command resetZero() {
        return new InstantCommand(() -> { viperL.resetEncoder(); viperR.resetEncoder(); });
    }

    public String viperR_nome = "elevadorDireito";
    public String viperL_nome = "elevadorEsquerdo";

    public Command toLowBasket() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                600, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHighBasket() {
        return new RunToPosition(sliders, // MOTOR TO MOVE
                        2260, // TARGET POSITION, IN TICKS
                        controller, // CONTROLLER TO IMPLEMENT
                        this); // IMPLEMENTED SUBSYSTEM
    }

    public Command toHighSpecimen() {
        return new RunToPosition(sliders,
                0.0,
                controller,
                this);
    }

    @Override
    public Command getDefaultCommand() {
        return new HoldPosition(sliders, controller, this);
    }

    @Override
    public void initialize() {
        viperR = new MotorEx(viperR_nome).reverse();
        viperL = new MotorEx(viperL_nome);

        sliders = new MotorGroup(viperR, viperL);
    }
}
