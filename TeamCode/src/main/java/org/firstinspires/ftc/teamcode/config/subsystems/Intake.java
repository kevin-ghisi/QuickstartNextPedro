package org.firstinspires.ftc.teamcode.config.subsystems;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.SwitchableLight;
import com.rowanmcalpin.nextftc.core.Subsystem;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.LambdaCommand;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.OpModeData;
import com.rowanmcalpin.nextftc.ftc.StopOpModeCommand;
import com.rowanmcalpin.nextftc.ftc.hardware.MultipleServosToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.HoldPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToVelocity;

import java.util.List;

public class Intake extends Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    public MotorEx intake_slider, intake_spinner;
    public Servo Shoulder_Right, Shoulder_Left;

    public NormalizedColorSensor colorSensor;
    public String intake_slider_nome = "intake_slider";
    public String intake_spinner_name = "intake_spinner";
    public String Shoulder_Right_nome = "shoulder_right";
    public String Shoulder_Left_nome = "shoulder_left";
    public String colorSensor_name = "intake_color";

    public PIDFController intake_slider_PIDF = new PIDFController(0.005, 0.0, 0.0);
    public PIDFController intake_spinner_PIDF = new PIDFController(0.005, 0.0, 0.0);

    public Command resetSliderZero() {
        return new InstantCommand(() -> { intake_slider.resetEncoder();});
    }

    public Command toHome() {
        return new ParallelGroup(
            new RunToPosition(intake_slider, // MOTOR TO MOVE
                    GlobalPositions.INTAKE_SLIDER_RETRACTED, // TARGET POSITION, IN TICKS
                    intake_slider_PIDF, // CONTROLLER TO IMPLEMENT
                    this), // IMPLEMENTED SUBSYSTEM
            new MultipleServosToPosition(
                    List.of(Shoulder_Left, Shoulder_Right),
                    GlobalPositions.INTAKE_SHOULDER_RETRACTED_POS,
                    this
            )
        );
    }

    public Command toIntake() {
        return new ParallelGroup(
                new InstantCommand(
                        () -> ((SwitchableLight)colorSensor).enableLight(true)
                ),
                new RunToPosition(intake_slider, // MOTOR TO MOVE
                        GlobalPositions.INTAKE_SLIDER_GRAB, // TARGET POSITION, IN TICKS
                        intake_slider_PIDF, // CONTROLLER TO IMPLEMENT
                        this), // IMPLEMENTED SUBSYSTEM
                new MultipleServosToPosition(
                        List.of(Shoulder_Left, Shoulder_Right),
                        GlobalPositions.INTAKE_SHOULDER_GRAB_POS,
                        this
                ),
                new RunToVelocity(
                        intake_spinner,
                        GlobalPositions.INTAKE_SPIN_SPD,
                        intake_slider_PIDF,
                        this
                )
        );
    }

    public Command toTransfer() {
        return new ParallelGroup(
                new RunToPosition(intake_slider, // MOTOR TO MOVE
                        GlobalPositions.INTAKE_SLIDER_TRANSFER, // TARGET POSITION, IN TICKS
                        intake_slider_PIDF, // CONTROLLER TO IMPLEMENT
                        this), // IMPLEMENTED SUBSYSTEM
                new MultipleServosToPosition(
                        List.of(Shoulder_Left, Shoulder_Right),
                        GlobalPositions.INTAKE_SHOULDER_TRANSFER_POS,
                        this
                ),
                new RunToVelocity(
                        intake_spinner,
                        GlobalPositions.INTAKE_SPIN_HOLD_SPD,
                        intake_slider_PIDF,
                        this
                )
        );
    }

    public Command handColeta() {
        return new RunToPosition(intake_spinner, // MOTOR TO MOVE
                300, // TARGET POSITION, IN TICKS
                intake_spinner_PIDF, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }

    public Command handTransferencia() {
        return new RunToPosition(intake_spinner, // MOTOR TO MOVE
                160, // TARGET POSITION, IN TICKS
                intake_spinner_PIDF, // CONTROLLER TO IMPLEMENT
                this); // IMPLEMENTED SUBSYSTEM
    }


    @Override
    public Command getDefaultCommand() {
        return new HoldPosition(intake_slider, intake_slider_PIDF, this);
    }

    @Override
    public void initialize() {
        intake_slider = new MotorEx(intake_slider_nome).reverse();
        intake_spinner = new MotorEx(intake_spinner_name).reverse();

        Shoulder_Right = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Shoulder_Right_nome);
        Shoulder_Left = OpModeData.INSTANCE.getHardwareMap().get(Servo.class, Shoulder_Left_nome);
        colorSensor = OpModeData.INSTANCE.getHardwareMap().get(NormalizedColorSensor.class, colorSensor_name);
    }
}
