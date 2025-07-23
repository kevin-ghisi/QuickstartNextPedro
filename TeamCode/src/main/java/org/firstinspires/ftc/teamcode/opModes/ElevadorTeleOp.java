package org.firstinspires.ftc.teamcode.opModes;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@TeleOp(name = "teste elevador")
public class ElevadorTeleOp extends OpMode {

    public DcMotor elevadorEsquerdo = null;
    public DcMotor elevadorDireito = null;

    private Follower follower;
    private Pose startPose = new Pose(0,0, Math.toRadians(0));

    @Override
    public void init() {

        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

        elevadorEsquerdo = hardwareMap.get(DcMotor.class, "elevadorEsquerdo");
        elevadorDireito = hardwareMap.get(DcMotor.class, "elevadorDireito");

        elevadorEsquerdo.setDirection(DcMotorSimple.Direction.FORWARD);
        elevadorDireito.setDirection(DcMotorSimple.Direction.REVERSE);

        elevadorEsquerdo.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevadorDireito.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        elevadorEsquerdo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevadorDireito.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            elevadorEsquerdo.setTargetPosition(elevadorEsquerdo.getCurrentPosition()+100);
            elevadorDireito.setTargetPosition(elevadorEsquerdo.getCurrentPosition()+100);

//            elevadorEsquerdo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            elevadorDireito.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elevadorEsquerdo.setPower(0.4);
            elevadorDireito.setPower(0.4);
        }

        if (gamepad1.left_bumper) {
            elevadorEsquerdo.setTargetPosition(elevadorEsquerdo.getCurrentPosition()-100);
            elevadorDireito.setTargetPosition(elevadorEsquerdo.getCurrentPosition()-100);

//            elevadorEsquerdo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            elevadorDireito.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            elevadorEsquerdo.setPower(0.6);
            elevadorDireito.setPower(0.6);
        }

        telemetry.addData("EL. E. POS:", elevadorEsquerdo.getCurrentPosition());
        telemetry.addData("EL. D. POS:", elevadorDireito.getCurrentPosition());
        telemetry.update();
    }
}
