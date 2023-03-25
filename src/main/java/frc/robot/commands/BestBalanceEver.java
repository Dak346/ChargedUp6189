package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.AccelerometerWrapper;

public class BestBalanceEver extends SequentialCommandGroup {
    public BestBalanceEver(Drivetrain drivetrain, Arm arm, AccelerometerWrapper accelerometer) {
        super(
            new RunIntakeOut(arm, -0.40),
            new WaitCommand(0.50),
            new StopIntake(arm),
            new AutoBalance(drivetrain, accelerometer)
        );
    }
}