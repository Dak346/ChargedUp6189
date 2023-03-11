package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class RunWrist extends InstantCommand {
    public RunWrist(Arm arm, double wristPower) {
        super(() -> arm.setWristPower(wristPower), arm);
    }
}

