package org.firstinspires.ftc.teamcode.opModes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.config.subsystems.Outake;
import org.firstinspires.ftc.teamcode.controller.Controller;
import org.firstinspires.ftc.teamcode.controller.Button;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Point;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.control.controllers.PIDFController;
import com.rowanmcalpin.nextftc.ftc.driving.MecanumDriverControlled;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.MotorEx;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToPosition;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.RunToVelocity;
import com.rowanmcalpin.nextftc.ftc.hardware.controllables.SetPower;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.pedropathing.pathgen.Path;

import org.firstinspires.ftc.teamcode.config.subsystems.Vipers;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.util.List;

@TeleOp(group = "Basket Azul com AT")
public class TeleOpCR extends PedroOpMode {

    public TeleOpCR() {
        super(Vipers.INSTANCE, Outake.INSTANCE);
    }
    public String frontLeftName = "leftFront";
    public String frontRightName = "rightFront";
    public String backLeftName = "leftRear";
    public String backRightName = "rightRear";
    public Limelight3A limelight3A;
    public MotorEx frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    public MotorEx[] motors;
    public Servo garra;
    public Command driverControlled;
        private HuskyLens huskyLens;
    private boolean tagHandle = false;
    private final Pose startPose = new Pose(73.313, 93.666, Math.toRadians(0.135));
    private Follower follower;
    private Path AtBasketAzul;

    private double anguloSample = 0;
    private double anguloGarra = 0;
    private double posicaoGarra = 0;

    @Override
    public void onInit() {

        //Motores
        frontLeftMotor = new MotorEx(frontLeftName);
        backLeftMotor = new MotorEx(backLeftName);
        backRightMotor = new MotorEx(backRightName);
        frontRightMotor = new MotorEx(frontRightName);

        garra = hardwareMap.get(Servo.class, "claw");

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        motors = new MotorEx[]{frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor};

//        Husky Lens
        huskyLens = hardwareMap.get(HuskyLens.class, "Husky Lens");
        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);

        //Limelight
        limelight3A = hardwareMap.get(Limelight3A.class, "Limelight");
        limelight3A.pipelineSwitch(0);
        limelight3A.pipelineSwitch(1);
        limelight3A.pipelineSwitch(2);

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
    }

    @Override
    public void onStartButtonPressed() {
        driverControlled = new MecanumDriverControlled(motors, gamepadManager.getGamepad1());
        driverControlled.invoke();

        follower.startTeleopDrive();

        //Controles do elevador
        gamepadManager.getGamepad1().getDpadUp().setPressedCommand(Vipers.INSTANCE::toHighBasket);
        gamepadManager.getGamepad1().getDpadDown().setPressedCommand(Vipers.INSTANCE::toLowBasket);
        gamepadManager.getGamepad1().getDpadLeft().setPressedCommand(Vipers.INSTANCE::toHighSpecimen);

        //Controle da angulação da garra
        if (gamepad1.cross) {
            garra.setPosition(posicaoGarra);
        }
    }


    @Override
    public void onUpdate() {

        // Obter ângulo do Limelight3A (ajuste conforme a API do Limelight3A)
        // Substitua isso pelo método correto para obter o ângulo do Limelight3A
//        anguloSample = limelight3A.getTargetXDegrees(); // Exemplo - verifique o método real

        // Calcular ângulo da garra (offset de 90°)
        anguloGarra = anguloSample + 90;
        anguloGarra = Math.max(0, Math.min(270, anguloGarra)); // Limita a 0°-270°

        // Converter para posição do servo (0.0 a 1.0)
        posicaoGarra = anguloGarra / 270.0;

        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Ele D", Vipers.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("Ele L", Vipers.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("Ângulo Sample", anguloSample);
        telemetry.addData("Ângulo Garra", anguloGarra);
        telemetry.addData("Posição Garra", posicaoGarra);
        telemetry.update();
    }

//        HuskyLens.Block[] tags = huskyLens.blocks();
//
//        if (tags.length > 0) {
//            int tagId = tags[0].id;
//            telemetry.addData("Tag Detected", tagId);
//
//            if (gamepad1.cross) {
//                switch (tagId) {
//                    case 1:
//                        BezierCurve curve = new BezierCurve(
//                                follower.getPose(), new Pose(24.948, 127.587, Point.CARTESIAN),
//                                new Pose(20.790, 135.684, Point.CARTESIAN),
//                                new Pose(13.787, 129.994, Point.CARTESIAN)
//                        );
//                        AtBasketAzul.setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(135));
//                        break;
//                }
//            }
//        } else {
//            telemetry.addLine("Sem TAG");
//        }
}