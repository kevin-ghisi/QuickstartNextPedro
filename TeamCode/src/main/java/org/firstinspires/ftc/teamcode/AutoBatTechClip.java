package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.config.subsystems.Intake;
import org.firstinspires.ftc.teamcode.config.subsystems.Maum;
import org.firstinspires.ftc.teamcode.config.subsystems.Outake;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
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

@Autonomous(name = "BatTech Auto Clip Azul", group = "Auto")
public class AutoBatTechClip extends PedroOpMode {

    public AutoBatTechClip() {
        super(Intake.INSTANCE, Outake.INSTANCE, Maum.INSTANCE);
    }

    private final Pose startPose = new Pose(9, 64, Math.toRadians(0));

    private Path
            clipeUm, alinharEmpurraum, empurraumUm, voltaUm, empurraumDois,
            voltaDois, empurraumTres, jogadorUm,  clipeDois, jogadorDois, clipeTres, jogadorTres,
            clipeQuatro, estacionar;

    private PathChain move;

    public void buildPaths() {
        //1
        clipeUm = new Path(
                new BezierLine(
                        new Point(9.000, 64.000, Point.CARTESIAN),
                        new Point(41.050, 75.000, Point.CARTESIAN)
                )
        );
        clipeUm.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //2
        alinharEmpurraum = new Path(
                new BezierCurve(
                        new Point(41.050, 75.000, Point.CARTESIAN),
                        new Point(1.000, 8.000, Point.CARTESIAN),
                        new Point(50.000, 42.000, Point.CARTESIAN),
                        new Point(65.000, 37.000, Point.CARTESIAN),
                        new Point(64.000, 25.000, Point.CARTESIAN)
                )
        );
        alinharEmpurraum.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //3
        empurraumUm = new Path(
                new BezierLine(
                        new Point(64.000, 25.000, Point.CARTESIAN),
                        new Point(12.000, 25.000, Point.CARTESIAN)
                )
        );
        empurraumUm.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //4
        voltaUm = new Path(
                new BezierCurve(
                        new Point(12.000, 25.000, Point.CARTESIAN),
                        new Point(72.000, 29.000, Point.CARTESIAN),
                        new Point(62.000, 15.000, Point.CARTESIAN)
                )
        );
        voltaUm.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //5
        empurraumDois = new Path(
                new BezierLine(
                        new Point(62.000, 15.000, Point.CARTESIAN),
                        new Point(12.000, 14.000, Point.CARTESIAN)
                )
        );
        empurraumDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //6
        voltaDois = new Path(
                new BezierCurve(
                        new Point(12.000, 14.000, Point.CARTESIAN),
                        new Point(72.000, 19.000, Point.CARTESIAN),
                        new Point(62.000, 10.000, Point.CARTESIAN)
                )
        );
        voltaDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //7
        empurraumTres = new Path(
                new BezierLine(
                        new Point(62.000, 10.000, Point.CARTESIAN),
                        new Point(12.000, 10.000, Point.CARTESIAN)
                )
        );
        empurraumTres.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
        //8
        jogadorUm = new Path(
                new BezierCurve(
                        new Point(12.000, 14.000, Point.CARTESIAN),
                        new Point(25.157, 14.313, Point.CARTESIAN),
                        new Point(23.700, 23.200, Point.CARTESIAN)
                )
        );
        jogadorUm.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));
        //-----==========
        //9
        clipeDois = new Path(
                new BezierCurve(
                        new Point(23.700, 23.200, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(41.050, 72.500, Point.CARTESIAN)
                )
        );
        clipeDois.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));
        //10
        jogadorDois = new Path(
                new BezierCurve(
                        new Point(41.050, 72.500, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(24.000, 25.000, Point.CARTESIAN)
                )
        );
        jogadorDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));
        //11
        clipeTres = new Path(
                new BezierCurve(
                        new Point(24.000, 25.000, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(41.050, 70.000, Point.CARTESIAN)
                )
        );
        clipeTres.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));
        //12
        jogadorTres = new Path(
                new BezierCurve(
                        new Point(41.100, 70.000, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(25.000, 25.000, Point.CARTESIAN)
                )
        );
        jogadorTres.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));
        //13
        clipeQuatro = new Path(
                new BezierCurve(
                        new Point(25.000, 25.000, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(41.100, 68.000, Point.CARTESIAN)
                )
        );
        clipeQuatro.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));
        //14
        estacionar = new Path(
                new BezierCurve(
                        new Point(41.050, 70.000, Point.CARTESIAN),
                        new Point(15.000, 60.000, Point.CARTESIAN),
                        new Point(30.000, 40.000, Point.CARTESIAN),
                        new Point(9.000, 10.000, Point.CARTESIAN)
                )
        );
        estacionar.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));
    }

    /*new FollowPath(inicioPHighBasket, true),
                new Delay(1),*/

    public Command secondRoutine() {
        return new SequentialGroup(

                //-----Início

                Outake.INSTANCE.outFechar(),
                new ParallelGroup(
                        Outake.INSTANCE.anconClip(),
                        Outake.INSTANCE.elevatorToMiddle(),
                        new FollowPath(clipeUm, true)
                ),
                new Delay(0.3),
                Outake.INSTANCE.anconTransfer(),
                new Delay(0.3),
                Outake.INSTANCE.elevatorToScore(),
                Outake.INSTANCE.outAbrir(),

                //-----PREPARAAH

                new ParallelGroup(
                        new FollowPath(alinharEmpurraum, true),
                        Outake.INSTANCE.anconTransfer(),
                        Outake.INSTANCE.elevatorToLow()
                ),


                //-----EMPURRA

                new FollowPath(empurraumUm),
                new FollowPath(voltaUm),
                new FollowPath(empurraumDois, true),


                new ParallelGroup(
                        Intake.INSTANCE.piqueOpenRightFinger(),
                        Intake.INSTANCE.piqueOpenLeftFinger(),
                        new FollowPath(jogadorUm, true),
                        new SequentialGroup(
                                new Delay(0.4),
                                Intake.INSTANCE.colletClip()
                        )
                ),
                new Delay(0.3),
                new ParallelGroup(
                        Maum.INSTANCE.maumColetaAutonomo(),
                        Outake.INSTANCE.anconTransfer(),
                        new SequentialGroup(
                                new Delay(0.6),
                                Intake.INSTANCE.piqueCloseRightFinger(),
                                Intake.INSTANCE.piqueCloseLeftFinger()
                        )
                ),
                new Delay(0.5),


                //-----CLIPARDNOVO

                new SequentialGroup(
                        new ParallelGroup(
                                new SequentialGroup(
                                        new Delay(0.7),
                                        new FollowPath(clipeDois,true)
                                ),
                                //-----
                                new SequentialGroup(
                                        new ParallelGroup(
                                                Intake.INSTANCE.colletTransferencia(),
                                                Maum.INSTANCE.maumTransferencia()
                                        ),
                                        new Delay(0.3),
                                        Outake.INSTANCE.outFechar(),
                                        new Delay(0.6),
                                        new ParallelGroup(
                                                Intake.INSTANCE.piqueOpenRightFinger(),
                                                Intake.INSTANCE.piqueOpenLeftFinger()
                                        ),
                                        new Delay(0.3),
                                        new ParallelGroup(
                                                Outake.INSTANCE.anconClip(),
                                                Outake.INSTANCE.elevatorToMiddle()
                                        )
                                )
                        ),
                        new SequentialGroup(
                                new Delay(0.2),
                                Outake.INSTANCE.anconTransfer(),
                                new Delay(0.1),
                                Outake.INSTANCE.elevatorToScore(),
                                Outake.INSTANCE.outAbrir()
                        )
                ),

                //-----VoltaPraEle

                new ParallelGroup(
                        Outake.INSTANCE.elevatorToLow(),
                        new FollowPath(jogadorDois,true),
                        new SequentialGroup(
                                new Delay(0.5),
                                new ParallelGroup(
                                        Intake.INSTANCE.colletClip(),
                                        Outake.INSTANCE.anconTransfer(),
                                        Maum.INSTANCE.maumColetaAutonomo()
                                )
                        )
                ),
                new Delay(0.3),
                new ParallelGroup(
                        Intake.INSTANCE.piqueCloseRightFinger(),
                        Intake.INSTANCE.piqueCloseLeftFinger()
                ),
                new Delay(0.7),
                //-----Clipe3

                new SequentialGroup(
                        new ParallelGroup(
                                new SequentialGroup(
                                        new Delay(0.5),
                                        new FollowPath(clipeTres,true)
                                ),
                                //-----
                                new SequentialGroup(
                                        new ParallelGroup(
                                                Intake.INSTANCE.colletTransferencia(),
                                                Maum.INSTANCE.maumTransferencia()
                                        ),
                                        new Delay(0.3),
                                        Outake.INSTANCE.outFechar(),
                                        new Delay(0.6),
                                        new ParallelGroup(
                                                Intake.INSTANCE.piqueOpenRightFinger(),
                                                Intake.INSTANCE.piqueOpenLeftFinger()
                                        ),
                                        new Delay(0.3),
                                        new ParallelGroup(
                                                Outake.INSTANCE.anconClip(),
                                                Outake.INSTANCE.elevatorToMiddle()
                                        )
                                )
                        ),
                        new SequentialGroup(
                                new Delay(0.2),
                                Outake.INSTANCE.anconTransfer(),
                                new Delay(0.1),
                                Outake.INSTANCE.elevatorToScore(),
                                Outake.INSTANCE.outAbrir()
                        )
                ),
                new ParallelGroup(
                        new FollowPath(estacionar),
                        Outake.INSTANCE.elevatorToLow()
                )
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
        telemetry.update();
    }


    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        new SequentialGroup(new InstantCommand(() ->Outake.INSTANCE.outFechar())).invoke();

    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }


}

