package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class StopIntake extends InstantCommand {
    public StopIntake(Arm arm) {
        super(arm::stopIntake, arm);
    }
}