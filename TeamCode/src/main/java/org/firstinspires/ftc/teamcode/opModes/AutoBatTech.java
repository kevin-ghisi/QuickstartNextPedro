package org.firstinspires.ftc.teamcode.opModes;

import org.firstinspires.ftc.teamcode.config.subsystems.Outake;
import org.firstinspires.ftc.teamcode.config.subsystems.Vipers;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

@Autonomous(name = "BatTech Auto Teste", group = "Auto")
public class AutoBatTech extends PedroOpMode {

    public AutoBatTech() {
        super(Vipers.INSTANCE, Outake.INSTANCE);
    }
    private final Pose startPose = new Pose(8.360189573459715, 80.3601895734597, Math.toRadians(0));

    private Path inicioPHighBasket, basketToStop;
    private PathChain move;

    public void buildPaths() {
        inicioPHighBasket = new Path(
                new BezierCurve(
                        new Point(8.360, 80.360, Point.CARTESIAN),
                        new Point(22.351, 106.123, Point.CARTESIAN),
                        new Point(16.891, 127.450, Point.CARTESIAN))
        );
        inicioPHighBasket.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45));

        basketToStop = new Path(
                new BezierCurve(
                        new Point(16.891, 127.450, Point.CARTESIAN),
                        new Point(61.251, 120.114, Point.CARTESIAN),
                        new Point(72.000, 97.081, Point.CARTESIAN)
                )
        );
        basketToStop.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(270));
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                Vipers.INSTANCE.resetZero(),
                new Delay(4),
                Outake.INSTANCE.fechar(),
                Outake.INSTANCE.transfer(),
                new ParallelGroup(
                        new FollowPath(inicioPHighBasket, true),
                        Vipers.INSTANCE.toHighBasket(),
                        Outake.INSTANCE.score()
                )
//                new SequentialGroup(
//                        Outake.INSTANCE.abrir(),
//                        new Delay(1),
//                        Vipers.INSTANCE.toMiddle(),
//                        new Delay(1),
//                        new FollowPath(basketToStop, true)
//                )

        );
    }

    @Override
    public void onUpdate() {
        // Feedback to Driver Hub
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("ViperL pos", Vipers.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("ViperR pos", Vipers.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        new SequentialGroup(new InstantCommand(() ->Outake.INSTANCE.fechar())).invoke();
    }

    @Override
    public void onStartButtonPressed() {
//        secondRoutine().invoke();
//        opmodeTimer.resetTimer();
////        follower.followPath(cafe);
//
//        new ParallelGroup(
//                new FollowPath(inicioPHighBasket, true),
//                new InstantCommand(() -> Vipers.INSTANCE.toHighBasket()),
//                new SequentialGroup(
//                    new InstantCommand(() ->Outake.INSTANCE.abrir()),
//                    new Delay(0.4),
//                        new InstantCommand(() -> Vipers.INSTANCE.toMiddle())
//                ),
//                new Delay(1),
//                new FollowPath(basketToStop, true)
//        ).invoke();
    }

}

