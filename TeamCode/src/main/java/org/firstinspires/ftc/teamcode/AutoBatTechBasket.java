package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.rowanmcalpin.nextftc.core.command.Command;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelDeadlineGroup;
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.teamcode.config.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Maum;
import org.firstinspires.ftc.teamcode.config.subsystems.Outake;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "BatTech Auto Basket Azul", group = "Auto")
public class AutoBatTechBasket extends PedroOpMode {

    public AutoBatTechBasket() {
        super(Intake.INSTANCE, Outake.INSTANCE, Maum.INSTANCE, Elevator.INSTANCE);
    }

    private final Pose startPose = new Pose(9, 104, Math.toRadians(-90));

    private final double xBasket = 18.000;
    private final double yBasket = 126.000;

    private final double xSample = 26.500;

    private Path    depositarSampleUm, pegarSampleDois, depositarSampleDois, pegarSampleTres,
                    depositarSampleTres, pegarSampleQuatro, depositarSampleQuatro;

    private PathChain move;

    public void buildPaths() {

        //linha 1
        depositarSampleUm = new Path(
                new BezierCurve(
                        new Point(9.000, 104.000, Point.CARTESIAN),
                        new Point(18.500, 113.500, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleUm.setLinearHeadingInterpolation(Math.toRadians(-90), Math.toRadians(-45));

        //linha 2
        pegarSampleDois = new Path(
                new BezierLine(
                        new Point(xBasket, yBasket, Point.CARTESIAN),
                        new Point(xSample, 120.500, Point.CARTESIAN)
                )
        );
        pegarSampleDois.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0));

        //linha 3
        depositarSampleDois = new Path(
                new BezierLine(
                        new Point(xSample, 120.500, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45));

        //linha 4
        pegarSampleTres = new Path(
                new BezierLine(
                        new Point(xBasket, yBasket, Point.CARTESIAN),
                        new Point(xSample, 127.500, Point.CARTESIAN)
                )
        );
        pegarSampleTres.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0));

        //linha 5
        depositarSampleTres = new Path(
                new BezierLine(
                        new Point(xSample, 127.500, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleTres.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45));

        //linha 6
        pegarSampleQuatro = new Path(
                new BezierLine(
                        new Point(xBasket, yBasket, Point.CARTESIAN),
                        new Point(31.000, 126.000, Point.CARTESIAN)
                )
        );
        pegarSampleQuatro.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(45));

        //linha 7
        depositarSampleQuatro = new Path(
                new BezierLine(
                        new Point(31.000, 127.000, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleQuatro.setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(-45));
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                Intake.INSTANCE.ptheroInitPosYaw(),
                new ParallelGroup(
                        Outake.INSTANCE.anconTransfer(),
                        Outake.INSTANCE.outFechar(),
                        new FollowPath(depositarSampleUm, true),
                        Elevator.INSTANCE.elevatorToHight(),
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        Intake.INSTANCE.piqueOpenRightFinger()
                ),
                Outake.INSTANCE.anconBasket(),
                new Delay(0.9),
                Outake.INSTANCE.outAbrir(),
                new Delay(0.5),

                //pegarSamplesDois
                Outake.INSTANCE.anconTransfer(),
                new Delay(0.6),
                new ParallelGroup(
                        Intake.INSTANCE.colletClip(),
                        Maum.INSTANCE.maumColetaAutonomo(),
                        new FollowPath(pegarSampleDois, true),
                        Elevator.INSTANCE.elevatorToLow()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueCloseLeftFinger(),
                        Intake.INSTANCE.piqueCloseRightFinger()
                ),
                new Delay(0.5),

                //depositarSamplesDois
                new ParallelGroup(
                        new FollowPath(depositarSampleDois, true),
                        Maum.INSTANCE.maumTransferencia(),
                        Intake.INSTANCE.colletTransferencia()
                ),
                Outake.INSTANCE.outFechar(),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        Intake.INSTANCE.piqueOpenRightFinger()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Elevator.INSTANCE.elevatorToHight(),
                        Intake.INSTANCE.colletClip()
                ),
                new ParallelGroup(
                        Maum.INSTANCE.maumColetaAutonomo(),
                        Outake.INSTANCE.anconBasket()
                ),
                new Delay(0.4),
                Outake.INSTANCE.outAbrir(),
                new Delay(0.5),

                //pegarSampleTres
                Outake.INSTANCE.anconTransfer(),
                new Delay(0.6),
                new ParallelGroup(
                        Intake.INSTANCE.colletClip(),
                        Maum.INSTANCE.maumColetaAutonomo(),
                        new FollowPath(pegarSampleTres, true),
                        Elevator.INSTANCE.elevatorToLow()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueCloseLeftFinger(),
                        Intake.INSTANCE.piqueCloseRightFinger()
                ),
                new Delay(0.5),

                //depositarSamplesTres
                new ParallelGroup(
                        new FollowPath(depositarSampleTres, true),
                        Maum.INSTANCE.maumTransferencia(),
                        Intake.INSTANCE.colletTransferencia()
                ),
                Outake.INSTANCE.outFechar(),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        Intake.INSTANCE.piqueOpenRightFinger()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Elevator.INSTANCE.elevatorToHight(),
                        Intake.INSTANCE.colletClip()
                ),
                new ParallelGroup(
                        Maum.INSTANCE.maumColetaAutonomo(),
                        Outake.INSTANCE.anconBasket()
                ),
                new Delay(0.4),
                Outake.INSTANCE.outAbrir(),
                new Delay(0.5),

                //pegarSampleQuatro
                Outake.INSTANCE.anconTransfer(),
                new Delay(0.6),
                new ParallelGroup(
                        Intake.INSTANCE.colletClip(),
                        Maum.INSTANCE.maumColetaAutonomo(),
                        new FollowPath(pegarSampleQuatro, true),
                        Elevator.INSTANCE.elevatorToLow()
                ),
                new Delay(0.5),
                Intake.INSTANCE.ptheroMinusYaw(),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueCloseLeftFinger(),
                        Intake.INSTANCE.piqueCloseRightFinger()
                ),
                new Delay(0.5),


                Intake.INSTANCE.ptheroInitPosYaw(),
                new ParallelGroup(
                        new FollowPath(depositarSampleQuatro, true),
                        Maum.INSTANCE.maumTransferencia(),
                        Intake.INSTANCE.colletTransferencia()
                ),
                Outake.INSTANCE.outFechar(),
                new Delay(0.5),
                new ParallelGroup(
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        Intake.INSTANCE.piqueOpenRightFinger()
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Elevator.INSTANCE.elevatorToHight(),
                        Intake.INSTANCE.colletClip()
                ),
                new ParallelGroup(
                        Maum.INSTANCE.maumColetaAutonomo(),
                        Outake.INSTANCE.anconBasket()
                ),
                new Delay(0.4),
                Outake.INSTANCE.outAbrir(),
                new Delay(0.5)
        );
    }

    @Override
    public void onUpdate() {
        // Feedback to Driver Hub
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("ViperL pos", Outake.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("ViperR pos", Outake.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("corehand", Maum.INSTANCE.hand.getCurrentPosition());
        telemetry.update();
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

