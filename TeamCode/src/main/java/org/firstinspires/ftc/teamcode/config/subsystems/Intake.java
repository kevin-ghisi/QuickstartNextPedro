package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorGroup;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    public MotorEx intake, hand;

    public Servo PincaR, PincaL, Pulso;
    public String intake_nome = "Collet";
    public String hand_name = "Maum";
    public String PincaR_nome  = "PiquePique1";
    public String PincaL_nome  = "PiquePique2";
    public String Pulso_nome  = "pulso";

    public PIDFController controller = new PIDFController(0.005, 0.0, 0.0);
    public PIDFController coreHexPID = new PIDFController(0.005, 0.0, 0.0);

    public Command resetZero() {
        return new InstantCommand(() -> { intake.resetEncoder();});
    }

    public Command retrair() {
        return new RunToPosition(intake, // MOTOR TO MOVE
                0.0, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command coleta() {
        return new RunToPosition(intake, // MOTOR TO MOVE
                1250, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command transferir() {
        double posicao = intake.getCurrentPosition() +50;
        return new RunToPosition(intake, // MOTOR TO MOVE
                315, // TARGET POSITION, IN TICKS
                controller, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command handColeta() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                300, // TARGET POSITION, IN TICKS
                coreHexPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command handTransferencia() {
        return new RunToPosition(hand, // MOTOR TO MOVE
                160, // TARGET POSITION, IN TICKS
                coreHexPID, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    @Override
    public void initialize() {
        intake = new MotorEx(intake_nome);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);

        hand = new MotorEx(hand_name);
    }
}
