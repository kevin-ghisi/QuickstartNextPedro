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
import com.rowanmcalpin.nextftc.core.command.groups.ParallelGroup;
import com.rowanmcalpin.nextftc.core.command.groups.SequentialGroup;
import com.rowanmcalpin.nextftc.core.command.utility.InstantCommand;
import com.rowanmcalpin.nextftc.core.command.utility.delays.Delay;
import com.rowanmcalpin.nextftc.pedro.FollowPath;
import com.rowanmcalpin.nextftc.pedro.PedroOpMode;

import org.firstinspires.ftc.teamcode.config.subsystems.Elevator;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeClaw;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeHand;
import org.firstinspires.ftc.teamcode.config.subsystems.IntakeSlider;
import org.firstinspires.ftc.teamcode.config.subsystems.OutakeAncon;
import org.firstinspires.ftc.teamcode.config.subsystems.OutakeClaw;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

@Autonomous(name = "BatTech Auto Clip Vermelho", group = "AutoVermelho")
public class AutoVermelhoClip extends PedroOpMode {

    public AutoVermelhoClip() {
        super(OutakeAncon.INSTANCE, OutakeClaw.INSTANCE, IntakeClaw.INSTANCE, IntakeHand.INSTANCE, IntakeSlider.INSTANCE, Elevator.INSTANCE);
    }

    private final double compX= 144;
    private final double compY= 130;

    private final Pose startPose = new Pose(compX - 9, compY - 64, Math.toRadians(180));



    private Path
            clipeUm, alinharEmpurraum, empurraumUm, voltaUm, empurraumDois,
            voltaDois, empurraumTres, jogadorUm,  clipeDois, jogadorDois, clipeTres, jogadorTres,
            clipeQuatro, estacionar;

    private PathChain move;

    public void buildPaths() {
        // 1
        clipeUm = new Path(
                new BezierLine(
                        new Point(compX - 9.000, compY - 64.000, Point.CARTESIAN),
                        new Point(compX - 41.050, compY - 75.000, Point.CARTESIAN)
                )
        );
        clipeUm.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 2
        alinharEmpurraum = new Path(
                new BezierCurve(
                        new Point(compX - 41.050, compY - 75.000, Point.CARTESIAN),
                        new Point(compX - 1.000, compY - 8.000, Point.CARTESIAN),
                        new Point(compX - 50.000, compY - 42.000, Point.CARTESIAN),
                        new Point(compX - 65.000, compY - 37.000, Point.CARTESIAN),
                        new Point(compX - 64.000, compY - 25.000, Point.CARTESIAN)
                )
        );
        alinharEmpurraum.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 3
        empurraumUm = new Path(
                new BezierLine(
                        new Point(compX - 64.000, compY - 25.000, Point.CARTESIAN),
                        new Point(compX - 12.000, compY - 25.000, Point.CARTESIAN)
                )
        );
        empurraumUm.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 4
        voltaUm = new Path(
                new BezierCurve(
                        new Point(compX - 12.000, compY - 25.000, Point.CARTESIAN),
                        new Point(compX - 72.000, compY - 29.000, Point.CARTESIAN),
                        new Point(compX - 62.000, compY - 15.000, Point.CARTESIAN)
                )
        );
        voltaUm.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 5
        empurraumDois = new Path(
                new BezierLine(
                        new Point(compX - 62.000, compY - 15.000, Point.CARTESIAN),
                        new Point(compX - 12.000, compY - 14.000, Point.CARTESIAN)
                )
        );
        empurraumDois.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 6
        voltaDois = new Path(
                new BezierCurve(
                        new Point(compX - 12.000, compY - 14.000, Point.CARTESIAN),
                        new Point(compX - 72.000, compY - 19.000, Point.CARTESIAN),
                        new Point(compX - 62.000, compY - 10.000, Point.CARTESIAN)
                )
        );
        voltaDois.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 7
        empurraumTres = new Path(
                new BezierLine(
                        new Point(compX - 62.000, compY - 10.000, Point.CARTESIAN),
                        new Point(compX - 12.000, compY - 10.000, Point.CARTESIAN)
                )
        );
        empurraumTres.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));

        // 8
        jogadorUm = new Path(
                new BezierCurve(
                        new Point(compX - 12.000, compY - 14.000, Point.CARTESIAN),
                        new Point(compX - 25.157, compY - 14.313, Point.CARTESIAN),
                        new Point(compX - 23.700, compY - 23.200, Point.CARTESIAN)
                )
        );
        jogadorUm.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));

        // 9
        clipeDois = new Path(
                new BezierCurve(
                        new Point(compX - 23.700, compY - 23.200, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 41.050, compY - 72.500, Point.CARTESIAN)
                )
        );
        clipeDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));

        // 10
        jogadorDois = new Path(
                new BezierCurve(
                        new Point(compX - 41.050, compY - 72.500, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 24.000, compY - 25.000, Point.CARTESIAN)
                )
        );
        jogadorDois.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));

        // 11
        clipeTres = new Path(
                new BezierCurve(
                        new Point(compX - 24.000, compY - 25.000, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 41.050, compY - 70.000, Point.CARTESIAN)
                )
        );
        clipeTres.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));

        // 12
        jogadorTres = new Path(
                new BezierCurve(
                        new Point(compX - 41.100, compY - 70.000, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 25.000, compY - 25.000, Point.CARTESIAN)
                )
        );
        jogadorTres.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0));

        // 13
        clipeQuatro = new Path(
                new BezierCurve(
                        new Point(compX - 25.000, compY - 25.000, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 41.100, compY - 68.000, Point.CARTESIAN)
                )
        );
        clipeQuatro.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180));

        // 14
        estacionar = new Path(
                new BezierCurve(
                        new Point(compX - 41.050, compY - 70.000, Point.CARTESIAN),
                        new Point(compX - 15.000, compY - 60.000, Point.CARTESIAN),
                        new Point(compX - 30.000, compY - 40.000, Point.CARTESIAN),
                        new Point(compX - 9.000, compY - 10.000, Point.CARTESIAN)
                )
        );
        estacionar.setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(180));
    }

    public Command secondRoutine() {
        return new SequentialGroup(

                //-----Início

                OutakeClaw.INSTANCE.Fechar(),
                new ParallelGroup(
                        OutakeAncon.INSTANCE.Clip(),
                        Elevator.INSTANCE.elevatorToMiddle(),
                        new FollowPath(clipeUm, true)
                ),
                new Delay(0.2),
                OutakeAncon.INSTANCE.Transfer(),
                new Delay(0.1),
                Elevator.INSTANCE.elevatorToScore(),
                new Delay(0.3),
                OutakeClaw.INSTANCE.Abrir(),

                //-----PREPARAAH

                new ParallelGroup(
                        new FollowPath(alinharEmpurraum, true),
                        OutakeAncon.INSTANCE.Transfer(),
                        new SequentialGroup(
                                new Delay(0.4),
                                Elevator.INSTANCE.elevatorToLowAuto()
                        )

                ),


                //-----EMPURRA

                new FollowPath(empurraumUm),
                new FollowPath(voltaUm),
                new FollowPath(empurraumDois, true),


                new ParallelGroup(
                        IntakeClaw.INSTANCE.piqueOpen(),
                        new FollowPath(jogadorUm, true),
                        new SequentialGroup(
                                new Delay(0.4),
                                IntakeSlider.INSTANCE.Clip()
                        )
                ),
                new Delay(0.3),
                new ParallelGroup(
                        IntakeHand.INSTANCE.ColetaAutonomo(),
                        OutakeAncon.INSTANCE.Transfer(),
                        new SequentialGroup(
                                new Delay(0.6),
                                IntakeClaw.INSTANCE.piqueClose()
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
                                                Elevator.INSTANCE.elevatorToLowAuto(),
                                                IntakeSlider.INSTANCE.Transferencia(),
                                                IntakeHand.INSTANCE.Transferencia()
                                        ),
                                        new Delay(0.3),
                                        OutakeClaw.INSTANCE.Fechar(),
                                        new Delay(0.6),
                                        IntakeClaw.INSTANCE.piqueOpen(),
                                        new Delay(0.3),
                                        new ParallelGroup(
                                                OutakeAncon.INSTANCE.Clip(),
                                                Elevator.INSTANCE.elevatorToMiddle()
                                        )
                                )
                        ),
                        new SequentialGroup(
                                new Delay(0.2),
                                OutakeAncon.INSTANCE.Transfer(),
                                new Delay(0.1),
                                Elevator.INSTANCE.elevatorToScore(),
                                new Delay(0.3),
                                OutakeClaw.INSTANCE.Abrir()

                        )
                ),

                //-----VoltaPraEle

                new ParallelGroup(
                        Elevator.INSTANCE.elevatorToLowAuto(),
                        new FollowPath(jogadorDois,true),
                        new SequentialGroup(
                                new Delay(0.5),
                                new ParallelGroup(
                                        IntakeSlider.INSTANCE.Clip(),
                                        OutakeAncon.INSTANCE.Transfer(),
                                        IntakeHand.INSTANCE.ColetaAutonomo()
                                )
                        )
                ),
                new Delay(0.3),
                IntakeClaw.INSTANCE.piqueClose(),
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
                                                IntakeSlider.INSTANCE.Transferencia(),
                                                IntakeHand.INSTANCE.Transferencia()
                                        ),
                                        new Delay(0.3),
                                        OutakeClaw.INSTANCE.Fechar(),
                                        new Delay(0.6),
                                        IntakeClaw.INSTANCE.piqueOpen(),
                                        new Delay(0.3),
                                        new ParallelGroup(
                                                OutakeAncon.INSTANCE.Clip(),
                                                Elevator.INSTANCE.elevatorToMiddle()
                                        )
                                )
                        ),
                        new SequentialGroup(
                                new Delay(0.2),
                                OutakeAncon.INSTANCE.Transfer(),
                                new Delay(0.1),
                                Elevator.INSTANCE.elevatorToScore(),
                                new Delay(0.3),
                                OutakeClaw.INSTANCE.Abrir()

                        )
                ),
                new ParallelGroup(
                        new FollowPath(estacionar),
                        Elevator.INSTANCE.elevatorToLowAuto()
                )
        );
    }

    @Override
    public void onUpdate() {
        // Feedback to Driver Hub
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("ViperL pos", Elevator.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("ViperR pos", Elevator.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }


    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();
        new SequentialGroup(new InstantCommand(() ->OutakeClaw.INSTANCE.Fechar())).invoke();
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }


}
