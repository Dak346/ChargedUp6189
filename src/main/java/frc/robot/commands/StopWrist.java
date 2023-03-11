package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class StopWrist extends InstantCommand {
    public StopWrist(Arm arm) {
        super(arm::stopWrist, arm);
    }
}