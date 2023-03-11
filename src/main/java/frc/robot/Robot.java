
package frc.robot;

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

    public Robot() {
        driver = new XboxController(Constants.kDriverPort);

        drivetrain = new Drivetrain();
        arm = new Arm();
    }

    @Override
    public void robotInit() {

    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        getAuto().schedule();
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
        drivetrainMotorDirectionTesting();
    }

    @Override
    public void teleopExit() {
        drivetrain.stopFrontLeft();
        drivetrain.stopFrontRight();
        drivetrain.stopRearLeft();
        drivetrain.stopRearRight();
    }

    public void drivetrainMotorDirectionTesting() {
        // drivetrain.runFrontLeftForward();
        // drivetrain.runFrontRightForward();
        // drivetrain.runRearLeftForward();
         drivetrain.runRearRightForward();
    }

    /**
     * teleop drive control
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

    public CommandBase getAuto() { 
        return new BestAutoEver(drivetrain, arm);
    }

    public enum ControlMode {
        GTA, Sticks;
    }

}