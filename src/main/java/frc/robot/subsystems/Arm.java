package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.*;

public class Arm extends SubsystemBase {
    private MotorController wrist;
    private CANSparkMax sparkWrist;
    private WPI_TalonSRX talonWrist, leftIntake, rightIntake;

    /**
     * initializes the arm subsystem
     */
    public Arm() {
        initMotors();
    }

    /**
     * sets the power of the wrist motor
     */
    public void setWristPower(double power) {
        wrist.set(power); // TODO consider putting a max value on the power
    }

    /**
     * stops the wrist
     */
    public void stopWrist() {
        wrist.set(0);
    }

    /**
     * sets the intake power
     */
    public void intake(double power) {
        leftIntake.set(Math.abs(power));
    }

    /**
     * sets the intake to some outtaking power regardless of the sign of power
     */
    public void outtake(double power) {
        leftIntake.set(-Math.abs(power));
    }

    /**
     * stops the intake
     */
    public void stopIntake() {
        leftIntake.set(0);
    }

    /**
     * initialize motor controllers
     */
    private void initMotors() {
        if (kWristController == WristMotorController.TalonSRX) {
            talonWrist = new WPI_TalonSRX(kWristID);
            talonWrist.setInverted(kWristInverted);
            talonWrist.setNeutralMode(NeutralMode.Brake);
            wrist = (MotorController) talonWrist;
        }
        else {
            sparkWrist = new CANSparkMax(kWristID, MotorType.kBrushless);
            sparkWrist.setInverted(kWristInverted);
            sparkWrist.setIdleMode(IdleMode.kBrake);
            wrist = (MotorController) sparkWrist;
        }

        leftIntake = new WPI_TalonSRX(kLeftIntakeTalonID);
        rightIntake = new WPI_TalonSRX(kRightIntakeTalonID);

        leftIntake.setInverted(kLeftIntakeTalonInverted);
        rightIntake.setInverted(kRightIntakeTalonInverted);

        rightIntake.follow(leftIntake);

        leftIntake.setNeutralMode(NeutralMode.Coast);
        rightIntake.setNeutralMode(NeutralMode.Coast);
    }

    /**
     * enum to enable quick switch between using a spark max and a talon srx
     */
    public enum WristMotorController {
        TalonSRX, SparkMax;
    }
}
