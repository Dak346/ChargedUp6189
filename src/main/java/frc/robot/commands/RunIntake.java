package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class RunIntake extends InstantCommand {
    public RunIntake(Arm arm, double intakePower) {
        super(() -> arm.intake(intakePower), arm);
    }
}
