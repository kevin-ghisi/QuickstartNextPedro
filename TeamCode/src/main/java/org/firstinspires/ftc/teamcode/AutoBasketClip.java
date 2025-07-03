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

@Autonomous(name = "BatTech Auto Basket Clip", group = "Autonomo")
public class AutoBasketClip extends PedroOpMode {

    public AutoBasketClip() {
        super(OutakeAncon.INSTANCE, OutakeClaw.INSTANCE, IntakeClaw.INSTANCE, IntakeHand.INSTANCE, IntakeSlider.INSTANCE, Elevator.INSTANCE);
    }

    private final Pose startPose = new Pose(9, 90, Math.toRadians(0));

    private final double xBasket = 17.700;
    private final double yBasket = 127.300;

    private final double xSample = 26.500;

    private Path    cliparUm, pegarSampleDois, depositarSampleDois, pegarSampleTres,
            depositarSampleTres, pegarSampleQuatro, depositarSampleQuatro;

    private PathChain move;

    public void buildPaths() {

        //linha 1
        cliparUm = new Path(
                new BezierCurve(
                        new Point(9.000, 90.000, Point.CARTESIAN),
                        new Point(18.000, 76.000, Point.CARTESIAN),
                        new Point(41.100, 76.000, Point.CARTESIAN)
                )
        );
        cliparUm.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

        //linha 2
        pegarSampleDois = new Path(
                new BezierCurve(
                        new Point(41.100, 76.000, Point.CARTESIAN),
                        new Point(14.000, 80.000, Point.CARTESIAN),
                        new Point(xSample, 120.500, Point.CARTESIAN)
                )
        );
        pegarSampleDois.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0));

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
                        new Point(xSample, 131.500, Point.CARTESIAN)
                )
        );
        pegarSampleTres.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(0));

        //linha 5
        depositarSampleTres = new Path(
                new BezierLine(
                        new Point(xSample, 131.500, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleTres.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45));

        //linha 6
        pegarSampleQuatro = new Path(
                new BezierLine(
                        new Point(xBasket, yBasket, Point.CARTESIAN),
                        new Point(32.500, 125.500, Point.CARTESIAN)
                )
        );
        pegarSampleQuatro.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(45));

        //linha 7
        depositarSampleQuatro = new Path(
                new BezierLine(
                        new Point(32.500, 125.500, Point.CARTESIAN),
                        new Point(xBasket, yBasket, Point.CARTESIAN)
                )
        );
        depositarSampleQuatro.setLinearHeadingInterpolation(Math.toRadians(45), Math.toRadians(-45));
    }

    public Command secondRoutine() {
        return new SequentialGroup(

                //cliparUm
                OutakeClaw.INSTANCE.Fechar(),
                new ParallelGroup(
                        OutakeAncon.INSTANCE.Clip(),
                        Elevator.INSTANCE.elevatorToMiddle(),
                        IntakeClaw.INSTANCE.piqueOpen(),
                        new FollowPath(cliparUm, true)
                ),
                new Delay(0.3),
                OutakeAncon.INSTANCE.Transfer(),
                new Delay(0.3),
                Elevator.INSTANCE.elevatorToScore(),
                new Delay(0.1),
                OutakeClaw.INSTANCE.Abrir(),

                //pegarSamplesDois

                new ParallelGroup(
                        new SequentialGroup(
                                Elevator.INSTANCE.elevatorToLowAuto()
                        ),
                        new SequentialGroup(
                                new Delay(0.5),
                                new ParallelGroup(
                                        new FollowPath(pegarSampleDois, true),
                                        OutakeAncon.INSTANCE.Transfer(),
                                        IntakeHand.INSTANCE.ColetaAutonomo(),
                                        new SequentialGroup(
                                                new Delay(0.4),
                                                IntakeSlider.INSTANCE.Clip()
                                        )
                                )
                        )
                ),
                new Delay(0.2),
                IntakeClaw.INSTANCE.piqueClose(),
                new Delay(0.5),

                //depositarSamplesDois
                new ParallelGroup(
                        new FollowPath(depositarSampleDois, true),
                        new SequentialGroup(
                                new ParallelGroup(
                                        Elevator.INSTANCE.elevatorToLowAuto(),
                                        IntakeHand.INSTANCE.Transferencia(),
                                        IntakeSlider.INSTANCE.Transferencia()
                                ),
                                IntakeSlider.INSTANCE.Transferencia(),
                                new Delay(0.3),
                                OutakeClaw.INSTANCE.Fechar(),
                                new Delay(0.3),
                                IntakeClaw.INSTANCE.piqueOpen(),
                                new Delay(0.15),
                                new ParallelGroup(
                                        Elevator.INSTANCE.elevatorToHight(),
                                        new SequentialGroup(
                                                new Delay(0.15),
                                                OutakeAncon.INSTANCE.Basket()
                                        )
                                )
                        )
                ),
                new Delay(0.5),
                OutakeClaw.INSTANCE.Abrir(),
                new Delay(0.4),

                //pegarSampleTres
                new ParallelGroup(
                        new SequentialGroup(
                                Elevator.INSTANCE.elevatorToLowAuto()
                        ),
                        new SequentialGroup(
                                new Delay(0.5),
                                new ParallelGroup(
                                        new FollowPath(pegarSampleTres, true),
                                        IntakeSlider.INSTANCE.Clip(),
                                        OutakeAncon.INSTANCE.Transfer(),
                                        IntakeHand.INSTANCE.ColetaAutonomo()
                                )
                        )
                ),
                new Delay(0.2),
                IntakeClaw.INSTANCE.piqueClose(),
                new Delay(0.5),

                //depositarSampleTres
                new ParallelGroup(
                        new FollowPath(depositarSampleTres, true),
                        new SequentialGroup(
                                new ParallelGroup(
                                        IntakeHand.INSTANCE.Transferencia(),
                                        IntakeSlider.INSTANCE.Transferencia()
                                ),
                                IntakeSlider.INSTANCE.Transferencia(),
                                new Delay(0.3),
                                OutakeClaw.INSTANCE.Fechar(),
                                new Delay(0.3),
                                IntakeClaw.INSTANCE.piqueOpen(),
                                new Delay(0.15),
                                new ParallelGroup(
                                        Elevator.INSTANCE.elevatorToHight(),
                                        new SequentialGroup(
                                                new Delay(0.15),
                                                OutakeAncon.INSTANCE.Basket()
                                        )
                                )
                        )
                ),
                new Delay(0.5),
                OutakeClaw.INSTANCE.Abrir(),
                new Delay(0.4),

                //pegarSampleQuatro
                new ParallelGroup(
                        new SequentialGroup(
                                Elevator.INSTANCE.elevatorToLowAuto()
                        ),
                        new SequentialGroup(
                                new Delay(0.5),
                                new ParallelGroup(
                                        new FollowPath(pegarSampleQuatro, true),
                                        IntakeSlider.INSTANCE.Clip(),
                                        OutakeAncon.INSTANCE.Transfer(),
                                        IntakeHand.INSTANCE.ColetaAutonomo()
                                )
                        )
                ),
                new Delay(0.2),
                IntakeClaw.INSTANCE.ptheroRotateMinusYaw(),
                new Delay(0.3),
                IntakeClaw.INSTANCE.piqueClose(),
                new Delay(0.5),

                //depositarSampleQuatro
                new ParallelGroup(
                        new FollowPath(depositarSampleQuatro, true),
                        new SequentialGroup(
                                new ParallelGroup(
                                        IntakeClaw.INSTANCE.ptheroInitPosYaw(),
                                        IntakeHand.INSTANCE.Transferencia(),
                                        IntakeSlider.INSTANCE.Transferencia()
                                ),
                                IntakeSlider.INSTANCE.Transferencia(),
                                new Delay(0.3),
                                OutakeClaw.INSTANCE.Fechar(),
                                new Delay(0.3),
                                IntakeClaw.INSTANCE.piqueOpen(),
                                new Delay(0.15),
                                new ParallelGroup(
                                        Elevator.INSTANCE.elevatorToHight(),
                                        new SequentialGroup(
                                                new Delay(0.15),
                                                OutakeAncon.INSTANCE.Basket()
                                        )
                                )
                        )
                ),
                new Delay(0.5),
                OutakeClaw.INSTANCE.Abrir()
        );
    }

    @Override
    public void onUpdate() {
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("ViperL pos", Elevator.INSTANCE.viperL.getCurrentPosition());
        telemetry.addData("ViperR pos", Elevator.INSTANCE.viperR.getCurrentPosition());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("corehand", IntakeHand.INSTANCE.hand.getCurrentPosition());
        telemetry.addData("collet", IntakeSlider.INSTANCE.collet.getCurrentPosition());
        telemetry.update();
    }


    @Override
    public void onInit() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        IntakeSlider.INSTANCE.resetZero().invoke();
        Elevator.INSTANCE.resetZero().invoke();
        IntakeHand.INSTANCE.resetZero().invoke();
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().invoke();
    }


}
