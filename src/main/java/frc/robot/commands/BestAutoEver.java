package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;

public class BestAutoEver extends SequentialCommandGroup {
    public BestAutoEver(Drivetrain drivetrain, Arm arm) {
        super(
            new RunIntake(arm, -0.10),
            new WaitCommand(0.50),
            new StopIntake(arm),
            Commands.parallel(
                Commands.sequence(
                    new RunWrist(arm, -0.10),
                    new WaitCommand(1),
                    new StopWrist(arm),
                    new RunIntake(arm, 0.1)
                ),
                Commands.sequence(
                    new AutoDrive(drivetrain, 0.6).withTimeout(3), // timed drive for 3 seconds
                    new StopDrive(drivetrain))
            )
        );
    }
}
