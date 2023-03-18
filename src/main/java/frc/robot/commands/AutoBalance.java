package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.AccelerometerWrapper;

public class AutoBalance extends CommandBase {

    private static final double kFastDriveSpeed = 0.4;
    private static final double kSlowDriveSpeed = 0.2;
    private static final double kDebounceTime = 0.2;
    private static final double kOnChargeStationDegree = 13.0;
    private static final double kLevelDegree = 6.0;

    private Drivetrain drivetrain;
    private AccelerometerWrapper accelerometer;
    private State state;
    private int debounceCount;

    public AutoBalance(Drivetrain drivetrain, AccelerometerWrapper accelerometer, State state) {
        this.drivetrain = drivetrain;
        this.accelerometer = accelerometer;
        this.state = state;
        this.debounceCount = 0;

        addRequirements(drivetrain);
    }

    public AutoBalance(Drivetrain drivetrain, AccelerometerWrapper accelerometer) {
        this(drivetrain, accelerometer, State.Approaching);
    }

    @Override
    public void execute() {
        drivetrain.autoDrive(calculateDriveSpeed());
    }

    private double calculateDriveSpeed() {
        switch (state) {
            // drive forwards to approach station, exit when tilt is detected
            case Approaching:
                if (accelerometer.getTilt() > kOnChargeStationDegree) {
                    debounceCount++;
                }
                if (debounceCount > secondsToTicks(kDebounceTime)) {
                    state = State.DrivingUp;
                    debounceCount = 0;
                    return kSlowDriveSpeed;
                }
                return kFastDriveSpeed;
            // driving up charge station, drive slower, stopping when level
            case DrivingUp:
                if (accelerometer.getTilt() < kLevelDegree) {
                    debounceCount++;
                }
                if (debounceCount > secondsToTicks(kDebounceTime)) {
                    state = State.OnTop;
                    debounceCount = 0;
                    return 0;
                }
                return kSlowDriveSpeed;
            // on charge station, stop motors and wait for end of auto
            case OnTop:
                if (Math.abs(accelerometer.getTilt()) <= kLevelDegree / 2) {
                    debounceCount++;
                }
                if (debounceCount > secondsToTicks(kDebounceTime)) {
                    state = State.Rest;
                    debounceCount = 0;
                    return 0;
                }
                if (accelerometer.getTilt() >= kLevelDegree) {
                    return 0.1;
                } else if (accelerometer.getTilt() <= -kLevelDegree) {
                    return -0.1;
                }
            case Rest:
            default:
                return 0;
        }

    }

    private int secondsToTicks(double time) {
        return (int) (time * 50);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }

    public enum State {
       Approaching, DrivingUp, OnTop, Rest;
    }
    
}
