
package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.AutoBalance;
import frc.robot.commands.BestAutoEver;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.AccelerometerWrapper;

public class Robot extends TimedRobot {

    private XboxController driver;
    private Drivetrain drivetrain;
    private Arm arm;

    @SuppressWarnings("unused")
    private AccelerometerWrapper accelerometer;

    /**
     * start of the user written robot code, initializing the controller and the subsystems
     */
    public Robot() {
        driver = new XboxController(Constants.kDriverPort);

        drivetrain = new Drivetrain();
        arm = new Arm();
        accelerometer = new AccelerometerWrapper(new BuiltInAccelerometer());
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
        driveControl();
        wristControl();
        intakeControl();
    }

    @Override
    public void teleopExit() {

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

        if (Constants.kInvertThrottleAxis) throttle *= -1;
        if (Constants.kInvertTurnAxis) turn *= -1;

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
            arm.outtake(Constants.kDefaultIntakePower);
        }
        else if (driver.getRightBumper()) {
            arm.outtake(Constants.kDefaultShootPower);
        }
        else {
            arm.stopIntake();
        }
    }

    /**
     * Autonomous command
     */
    public CommandBase getAutonomousCommand() { 
        // TODO if would like to test charge station auto run the line below instead and comment out the other one so you can still use it
        // return new AutoBalance(drivetrain, accelerometer);
        return new BestAutoEver(drivetrain, arm);
    }

    /**
     * Control mode enum to define how the driver inputs are read
     */
    public enum ControlMode {
        GTA, Sticks;
    }

}