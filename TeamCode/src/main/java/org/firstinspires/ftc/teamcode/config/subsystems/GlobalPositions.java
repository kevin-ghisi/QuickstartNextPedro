package org.firstinspires.ftc.teamcode.config.subsystems;

import com.acmerobotics.dashboard.config.Config;

@Config
public class GlobalPositions {
    public enum OpModeType {
        AUTO, TELEOP
    }

    public enum Alliance {
        BLUE, RED
    }

    public enum ScorePosition {
        HIGH_BASKET, LOW_BASKET,
        SPECIMEN
    }

    public enum POSE_LOCATION {
        BLUE_HIGH_BASKET, BLUE_LOW_BASKET,
        RED_HIGH_BASKET, RED_LOW_BASKET
    }

    public static OpModeType opModeType;
    public static Alliance alliance;
    public static ScorePosition scorePosition;

//    INTAKE SLIDER CONFIG
    public static double INTAKE_SLIDER_SPEED = 0.8;
    public static double INTAKE_SLIDER_RETRACTED = 0;
    public static double INTAKE_SLIDER_TRANSFER = 200;
    public static double INTAKE_SLIDER_GRAB = 1200;

//    INTAKE SPIN CONGIF
    public static double INTAKE_SPIN_SPD = 0.8;
    public static double INTAKE_SPIN_HOLD_SPD = 0.2;
    public static double INTAKE_SPIN_REVERSE_SPD = -0.6;

//    INTAKE SHOULDER CONFIG
    public static double INTAKE_SHOULDER_RETRACTED_POS = 0;
    public static double INTAKE_SHOULDER_TRANSFER_POS = 0.3;
    public static double INTAKE_SHOULDER_GRAB_POS = 1;

//    OUTAKE CLAW CONFIG
    public static double OUTAKE_CLAW_OPEN_POS = 0;
    public static double OUTAKE_CLAW_CLOSED_POS = 1;
    public static double OUTAKE_CLAW_MIDDLE_POS = 0.5;

//    OUTAKE PIVOT CONFIG
    public static double OUTAKE_PIVOT_TRANSFER_POS = 1;
    public static double OUTAKE_PIVOT_HIGH_BASKET_POS = 1;
    public static double OUTAKE_PIVOT_LOW_BASKET_POS = 1;
    public static double OUTAKE_PIVOT_SPECIMEN_POS = 1;
    public static double OUTAKE_PIVOT_START_POS = 1;

//    OUTAKE SHOULDER CONFIG
    public static double OUTAKE_SHOULDER_TRANSFER_POS = 1;
    public static double OUTAKE_SHOULDER_HIGH_BASKET_POS = 1;
    public static double OUTAKE_SHOULDER_LOW_BASKET_POS = 1;
    public static double OUTAKE_SHOULDER_SPECIMEN_POS = 1;
    public static double OUTAKE_SHOULDER_START_POS = 1;

//    OUTAKE VIPER CONFIG
    public static double OUTAKE_VIPER_HIGH_BASKET_POS = 1500;
    public static double OUTAKE_VIPER_LOW_BASKET_POS = 1000;
    public static double OUTAKE_VIPER_SPECIMEN_POS = 700;
    public static double OUTAKE_VIPER_TRANSFER_POS = 200;
    public static double OUTAKE_VIPER_START_POS = 0;



}
