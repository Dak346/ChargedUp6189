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

    public Drivetrain() {
        initMotors();
        //initDifferentialDrive(); // TODO uncomment when correct directions are found
        mode = kDriveMode;
        maxThrottle = kDefaultMaxThrottle;
        maxTurn = kDefaultMaxTurn;
    }

    public void stop() {
        if (drive == null) {
            frontLeft.set(0.7);
            frontRight.set(0.7);
            rearLeft.set(0.7);
            rearRight.set(0.7);
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

    public void drive(double throttle, double turn) {
        if (drive == null) {
            System.out.println("Please uncomment the initDifferentialDrive method in the Drivetrain constructor");
            return;
        } else {

            if (mode == DriveMode.Arcade) {
                drive.arcadeDrive(maxThrottle * throttle, maxTurn * turn, kSquareArcadeInputs);
            } else if (mode == DriveMode.Curvature) {
                drive.curvatureDrive(maxThrottle * throttle, maxTurn * turn, kCurvatureTurnInPlace);
            } else {
                System.out.println("Please select a valid DriveMode");
            }
        }
    }

    public void autoDrive(double forward) {
        if (drive == null) {
            System.out.println("Please uncomment the initDifferentialDrive method in the Drivetrain constructor");
            return;
        } else {
            drive.arcadeDrive(forward, 0);
        }
    } 

    public void runFrontLeftForward() {
        if (drive == null) {
            frontLeft.set(0.30);
        } else {
            System.out.print("Please remove calls to runFrontLeftForward()");
        }
    }

    public void stopFrontLeft() {
        if (drive == null) {
            frontLeft.set(0);
        } else {
            System.out.print("Please remove calls to stopFrontLeft()");
        }
    }

    public void runFrontRightForward() {
        if (drive == null) {
            frontRight.set(0.30);
        } else {
            System.out.print("Please remove calls to runFrontRightForward()");
        }
    }

    public void stopFrontRight() {
        if (drive == null) {
            frontRight.set(0);
        } else {
            System.out.print("Please remove calls to stopFrontRight()");
        }
    }

    public void runRearLeftForward() {
        if (drive == null) {
            rearLeft.set(0.30);
        } else {
            System.out.print("Please remove calls to runRearLeftForward()");
        }
    }

    public void stopRearLeft() {
        if (drive == null) {
            rearLeft.set(0);
        } else {
            System.out.print("Please remove calls to stopRearLeft()");
        }
    }

    public void runRearRightForward() {
        if (drive == null) {
            rearRight.set(0.30);
        } else {
            System.out.print("Please remove calls to runRearRightForward()");
        }
    }

    public void stopRearRight() {
        if (drive == null) {
            rearRight.set(0);
        } else {
            System.out.print("Please remove calls to stopRearRight()");
        }
    }

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

    @SuppressWarnings("unused")
    private void initDifferentialDrive() {
        left = new MotorControllerGroup(frontLeft, rearLeft);
        right = new MotorControllerGroup(frontRight, rearRight);

        drive = new DifferentialDrive(left, right);
    }

    public enum DriveMode {
        Arcade, Curvature;
    }
}
