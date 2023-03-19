package frc.robot;

import frc.robot.Robot.ControlMode;
import frc.robot.subsystems.Arm.WristMotorController;
import frc.robot.subsystems.Drivetrain.DriveMode;

public class Constants {
    public static final int kDriverPort = 0;
    public static final int kOperatorPort = 1;

    // drive controls and stuff
    public static final DriveMode kDriveMode = DriveMode.Curvature;
    public static final ControlMode kControlMode = ControlMode.GTA;
    public static final boolean kInvertThrottleAxis = false;
    public static final double kDefaultMaxThrottle = 0.80;
    public static final boolean kInvertTurnAxis = true;
    public static final double kDefaultMaxTurn = 0.55;
    public static final boolean kSquareArcadeInputs = false;
    public static final boolean kCurvatureTurnInPlace = true;
    public static final double kDefaultIntakePower = 0.20; //.2
    public static final double kDefaultWristPower = 0.60;
    public static final WristMotorController kWristController = WristMotorController.SparkMax;
    public static final int kCameraWidth = 0;
    public static final int kCameraHeight = 0;
    public static final int kCameraFramesPerSecond = 0;
    
    public static final int kFrontLeftDriveTalonID = 01;
    public static final boolean kFrontLeftDriveTalonInverted = false;
    public static final int kFrontRightDriveTalonID = 03;
    public static final boolean kFrontRightDriveTalonInverted = true;
    public static final int kRearLeftDriveTalonID = 04;
    public static final boolean kRearLeftDriveTalonInverted = false;
    public static final int kRearRightDriveTalonID = 25;
    public static final boolean kRearRightDriveTalonInverted = true;
    
    public static final int kWristID = 31;
    public static final boolean kWristInverted = false;
    public static final int kLeftIntakeTalonID = 34;
    public static final boolean kLeftIntakeTalonInverted = true;
    public static final int kRightIntakeTalonID = 02;
    public static final boolean kRightIntakeTalonInverted = true;
}
