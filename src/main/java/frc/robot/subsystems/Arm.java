package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    private WPI_TalonSRX wrist, leftIntake, rightIntake;

    public Arm() {
        initMotors();
    }

    public void setWristPower(double power) {
        wrist.set(power);
    }

    public void stopWrist() {
        wrist.set(0);
    }

    public void intake(double power) {
        leftIntake.set(power);
    }

    public void stopIntake() {
        leftIntake.set(0);
    }

    private void initMotors() {
        wrist = new WPI_TalonSRX(kWristTalonID);
        leftIntake = new WPI_TalonSRX(kLeftIntakeTalonID);
        rightIntake = new WPI_TalonSRX(kRightIntakeTalonID);

        wrist.setInverted(kWristTalonInverted);
        leftIntake.setInverted(kLeftIntakeTalonInverted);
        rightIntake.setInverted(kRightIntakeTalonInverted);

        rightIntake.follow(leftIntake);

        wrist.setNeutralMode(NeutralMode.Brake);
        leftIntake.setNeutralMode(NeutralMode.Coast);
        rightIntake.setNeutralMode(NeutralMode.Coast);
    }
}
