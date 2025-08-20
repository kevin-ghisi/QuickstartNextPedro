package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.subsystems.LimelightVision;

@TeleOp(name = "teste da garra")
public class Garra extends OpMode {
//    public Servo claw;
//    public Servo wrist;
    LimelightVision vision = new LimelightVision();
    public Servo test;

    public void init() {
//        claw = hardwareMap.get(Servo.class, "claw");
//        wrist = hardwareMap.get(Servo.class, "wrist");
        vision.init(hardwareMap);
        test = hardwareMap.get(Servo.class, "servo de teste");
    }

    @Override
    public void loop() {
        if (gamepad1.cross) {
//            claw.setPosition(1);
        }

        if  (gamepad1.triangle) {
//            claw.setPosition(0);
        }


        if (gamepad1.right_bumper) {
//            wrist.setPosition(wrist.getPosition()+0.1);
        }

        if  (gamepad1.left_bumper) {
//            wrist.setPosition(wrist.getPosition()-0.1);
        }
    }
}