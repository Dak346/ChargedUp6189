package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class AutoDrive extends CommandBase {

    private Drivetrain drivetrain;
    private double power;

    public AutoDrive(Drivetrain drivetrain, double power) {
        this.drivetrain = drivetrain;
        this.power = power;
    }

    @Override
    public void execute() {
        drivetrain.autoDrive(power);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.stop();
    }
}
