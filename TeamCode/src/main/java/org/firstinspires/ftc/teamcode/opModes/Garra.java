package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "teste da garra")
public class Garra extends OpMode {
    public Servo claw;
    public Servo wrist;

    public void init() {
        claw = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class, "wrist");
    }

    @Override
    public void loop() {
        if (gamepad1.cross) {
            claw.setPosition(1);
        }

        if  (gamepad1.triangle) {
            claw.setPosition(0);
        }

        if (gamepad1.circle) {
            wrist.setPosition(0.8);
        }

        if (gamepad1.square) {
            wrist.setPosition(0.2);
        }
    }
}