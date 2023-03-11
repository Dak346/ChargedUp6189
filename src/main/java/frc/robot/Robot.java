
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.BestAutoEver;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {

    private XboxController driver;
    private Drivetrain drivetrain;
    private Arm arm;

    /**
     * start of the user written robot code, initializing the controller and the subsystems
     */
    public Robot() {
        driver = new XboxController(Constants.kDriverPort);

        drivetrain = new Drivetrain();
        arm = new Arm();
    }

    @Override
    public void robotInit() {
        UsbCamera camera = CameraServer.startAutomaticCapture();
        camera.setResolution(Constants.kCameraWidth, Constants.kCameraHeight);
        camera.setFPS(Constants.kCameraFramesPerSecond);

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        getAutonomousCommand().schedule();
    }

    @Override
    public void autonomousExit() {
        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void teleopInit() {

    }

    @Override
    public void teleopPeriodic() {
        // TODO finish direction testing for rear right and then comment the line below out
        drivetrainMotorDirectionTesting();

        // TODO uncomment these three lines below after direction testing and uncomment initDifferentialDrive
        //      call in Drivetrain.java line 25

        // driveControl();
        // wristControl();
        // intakeControl();
    }

    @Override
    public void teleopExit() {
        drivetrain.stopFrontLeft();
        drivetrain.stopFrontRight();
        drivetrain.stopRearLeft();
        drivetrain.stopRearRight();
    }

    /**
     * testing code for direction of drive motors to ensure that they are going the right direction
     * before they are used in the differential drive
     */
    public void drivetrainMotorDirectionTesting() {
        // drivetrain.runFrontLeftForward();
        // drivetrain.runFrontRightForward();
        // drivetrain.runRearLeftForward();
         drivetrain.runRearRightForward();
    }

    /**
     * teleop drivetrain control
     */
    public void driveControl() {
        double throttle, turn;

        switch (Constants.kControlMode) {
            case Sticks:
                throttle = -driver.getRightY();
                turn = driver.getLeftX();
                break;
            default:
            case GTA:
                throttle = driver.getRightTriggerAxis() - driver.getLeftTriggerAxis();
                turn = driver.getLeftX();
                break;
        }

        drivetrain.drive(throttle, turn);
    }

    /**
     * teleop wrist control
     */
    public void wristControl() {
        if (driver.getXButton()) {
            arm.setWristPower(Constants.kDefaultWristPower);
        }
        else if (driver.getYButton()) {
            arm.setWristPower(-Constants.kDefaultWristPower);
        }
        else {
            arm.stopWrist();
        }
    }

    /**
     * teleop intake control
     */
    public void intakeControl() {
        if (driver.getAButton()) {
            arm.intake(Constants.kDefaultIntakePower);
        }
        else if (driver.getBButton()) {
            arm.intake(-Constants.kDefaultIntakePower);
        }
        else {
            arm.stopIntake();
        }
    }

    /**
     * Autonomous command
     */
    public CommandBase getAutonomousCommand() { 
        return new BestAutoEver(drivetrain, arm);
    }

    /**
     * Control mode enum to define how the driver inputs are read
     */
    public enum ControlMode {
        GTA, Sticks;
    }

}