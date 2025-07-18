package org.firstinspires.ftc.teamcode.opModes;


import static com.rowanmcalpin.nextftc.ftc.OpModeData.hardwareMap;
import org.firstinspires.ftc.teamcode.opModes.SensorHuskyLens;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;
import com.rowanmcalpin.nextftc.pedro.FollowPath;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "Auto Basket")
public class AutoBasket extends PedroOpMode {

//    private final Pose startPose = new Pose(8.09726443768997, 88.6322188449848, Math.toRadians(0.0));
    private final Pose startPose = new Pose(6.800, 88.632, Math.toRadians(0.0));

    private Path basket, estacionar, clipe;


    public void buildPaths()
    {
        basket = new Path(
                new BezierCurve(
                        new Point(6.800, 88.632, Point.CARTESIAN),
                        new Point(33.264, 110.079, Point.CARTESIAN),
                        new Point(12.474, 130.213, Point.CARTESIAN)
                ));
        basket.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(135));

        estacionar = new Path(
                new BezierCurve(
                        new Point(12.474, 130.213, Point.CARTESIAN),
                        new Point(54.274, 118.176, Point.CARTESIAN),
                        new Point(73.313, 93.666, Point.CARTESIAN)
                ));
        estacionar.setTangentHeadingInterpolation();
    }
    {
        clipe = new Path(
                new BezierLine(
                        new Point(8.000, 80.000, Point.CARTESIAN),
                        new Point(38.517, 79.660, Point.CARTESIAN)
                ));
        clipe.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        basket = new Path(
                new BezierCurve(
                        new Point(38.517, 79.660, Point.CARTESIAN),
                        new Point(43.112, 117.520, Point.CARTESIAN),
                        new Point(14.225, 128.462, Point.CARTESIAN)
                ));
        basket.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(135));
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(basket)
//                        new FollowPath(clipe)
////                        Lift.INSTANCE.toHigh()
                )
//                new ParallelGroup(
//                        new FollowPath(basket)
//                )
//                new ParallelGroup(
//                        Claw.INSTANCE.open(),
//                        Lift.INSTANCE.toMiddle()
//                ),
//                new Delay(1.0),
//                Lift.INSTANCE.toLow()
        );
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }
}
