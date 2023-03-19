package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Arm;

public class RunIntakeOut extends InstantCommand {
    public RunIntakeOut(Arm arm, double outtakePower) {
        super(() -> arm.outtake(outtakePower), arm);
    }
}
