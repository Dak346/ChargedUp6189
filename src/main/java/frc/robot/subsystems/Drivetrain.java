package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
    private DriveMode mode;
    public double maxThrottle, maxTurn;
    private WPI_TalonSRX frontLeft, frontRight, rearLeft, rearRight;
    private MotorControllerGroup left, right;
    private DifferentialDrive drive;

    /**
     * Creates the hardware software interfaces for the drivetrain
     */
    public Drivetrain() {
        initMotors();
        // initDifferentialDrive(); // TODO uncomment when correct directions are found
        mode = kDriveMode;
        maxThrottle = kDefaultMaxThrottle;
        maxTurn = kDefaultMaxTurn;
    }

    /**
     * Stops the drivetrain
     */
    public void stop() {
        if (drive == null) {
            frontLeft.set(0.0);
            frontRight.set(0.0);
            rearLeft.set(0.0);
            rearRight.set(0.0);
        } else {
            drive.stopMotor();
        }
    }

    /**
     * Sets the maximum percentage of the drivetrain input on a scale of 0 to 1 (for example 0.33 would be 33%)
     */
    public void setMaxSpeeds(double maxThrottle, double maxTurn) {
        this.maxThrottle = MathUtil.clamp(maxThrottle, 0, 1);
        this.maxTurn = MathUtil.clamp(maxTurn, 0, 1);
    }

    /**
     * The drive method that makes robot be drivable across the field (zoom zoom)
     * 
     * @param throttle forward input
     * @param turn turning input
     */
    public void drive(double throttle, double turn) {
        if (drive == null) {
            throw new IllegalStateException("If you're running the drive method you need to uncomment the initDifferentialDrive method on line 25 of Drivetrain.java");
        } else {

            if (mode == DriveMode.Arcade) {
                drive.arcadeDrive(maxThrottle * throttle, maxTurn * turn, kSquareArcadeInputs);
            } else if (mode == DriveMode.Curvature) {
                drive.curvatureDrive(maxThrottle * throttle, maxTurn * turn, kCurvatureTurnInPlace);
            } else {
                throw new IllegalStateException("Must pick a valid DriveMode");
            }
        }
    }

    /**
     * For auto driving ONLY. A drive method that makes robot be autonomously drivable in an approximately straight line 
     * 
     * @param throttle forward input
     * @param turn turning input
     */
    public void autoDrive(double forward) {
        if (drive == null) {
            throw new IllegalStateException("If you're running the autoDrive method you need to uncomment the initDifferentialDrive method on line 25 of Drivetrain.java");
        } else {
            drive.arcadeDrive(forward, 0);
        }
    } 

    /**
     * Used for testing the direction of the front left motor; only for testing purposes!
     */
    public void runFrontLeftForward() {
        if (drive == null) {
            frontLeft.set(0.30);
        } else {
            throw new IllegalStateException("Please remove calls to runFrontLeftForward()");
        }
    }

    /**
     * stops the front left motor; only for testing purposes! remove any calls once direction is verified
     */
    public void stopFrontLeft() {
        if (drive == null) {
            frontLeft.set(0);
        } else {
            throw new IllegalStateException("Please remove calls to stopFrontLeft(); use stop() instead");
        }
    }

    /**
     * Used for testing the direction of the front right motor; only for testing purposes!
     */
    public void runFrontRightForward() {
        if (drive == null) {
            frontRight.set(0.30);
        } else {
            throw new IllegalStateException("Please remove calls to runFrontRightForward()");
        }
    }

    /**
     * stops the front right motor; only for testing purposes! remove any calls once direction is verified
     */
    public void stopFrontRight() {
        if (drive == null) {
            frontRight.set(0);
        } else {
            throw new IllegalStateException("Please remove calls to stopFrontRight(); use stop() instead");
        }
    }

    /**
     * Used for testing the direction of the rear left motor; only for testing purposes!
     */
    public void runRearLeftForward() {
        if (drive == null) {
            rearLeft.set(0.30);
        } else {
            throw new IllegalStateException("Please remove calls to runRearLeftForward()");
        }
    }

    /**
     * stops the rear left motor; only for testing purposes! remove any calls once direction is verified
     */
    public void stopRearLeft() {
        if (drive == null) {
            rearLeft.set(0);
        } else {
            throw new IllegalStateException("Please remove calls to stopRearLeft(); use stop() instead");
        }
    }

    /**
     * Used for testing the direction of the rear right motor; only for testing purposes!
     */
    public void runRearRightForward() {
        if (drive == null) {
            rearRight.set(0.30);
        } else {
            throw new IllegalStateException("Please remove calls to runRearRightForward()");
        }
    }

    /**
     * stops the rear right motor; only for testing purposes! remove any calls once direction is verified
     */
    public void stopRearRight() {
        if (drive == null) {
            rearRight.set(0);
        } else {
            throw new IllegalStateException("Please remove calls to stopRearRight(); use stop() instead");
        }
    }

    /**
     * Initializes the individual motor controllers for the drivetrain
     */
    private void initMotors() {
        frontLeft = new WPI_TalonSRX(kFrontLeftDriveTalonID);
        frontRight = new WPI_TalonSRX(kFrontRightDriveTalonID);
        rearLeft = new WPI_TalonSRX(kRearLeftDriveTalonID);
        rearRight = new WPI_TalonSRX(kRearRightDriveTalonID);

        frontLeft.setInverted(kFrontLeftDriveTalonInverted);
        frontRight.setInverted(kFrontRightDriveTalonInverted);
        rearLeft.setInverted(kRearLeftDriveTalonInverted);
        rearRight.setInverted(kRearRightDriveTalonInverted);

        frontLeft.setNeutralMode(NeutralMode.Coast);
        frontRight.setNeutralMode(NeutralMode.Coast);
        rearLeft.setNeutralMode(NeutralMode.Coast);
        rearRight.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * Initializes the differential drive for the drivetrain
     */
    @SuppressWarnings("unused")
    private void initDifferentialDrive() {
        left = new MotorControllerGroup(frontLeft, rearLeft);
        right = new MotorControllerGroup(frontRight, rearRight);

        drive = new DifferentialDrive(left, right);
        drive.setSafetyEnabled(false);
    }

    /**
     * Enum to describe what drive mode is being used
     */
    public enum DriveMode {
        Arcade, Curvature;
    }
}
