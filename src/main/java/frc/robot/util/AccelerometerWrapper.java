package frc.robot.util;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AccelerometerWrapper extends SubsystemBase implements Accelerometer {

    private BuiltInAccelerometer accelerometer;

    private double tiltPosition, tiltVelocity, lastTimeMillis;

    private double xAccel, yAccel, zAccel, pitchAccel, rollAccel, tiltAccel;

    public AccelerometerWrapper(BuiltInAccelerometer accelerometer) {
        this.accelerometer = accelerometer;

        tiltPosition = 0;
        tiltVelocity = 0;
        lastTimeMillis = System.currentTimeMillis();
    }

    public double getPitch() {
        return pitchAccel;
    }

    private void calcPitch() {
        this.pitchAccel = Math.atan2((-this.getX()),
                Math.sqrt(this.getY() * this.getY() + this.getZ() * this.getZ())) * 180 / Math.PI;
    }

    public double getRoll() {
        return rollAccel;
    }

    private void calcRoll() {
        this.rollAccel = Math.atan2(this.getY(), this.getZ()) * 180 / Math.PI;
    }

    // returns the magnititude of the robot's tilt calculated by the root of
    // pitch^2 + roll^2, used to compensate for diagonally mounted rio
    public double getTilt() {
        return tiltAccel;
    }

    private void calcTilt() {
        if ((pitchAccel + rollAccel) >= 0) {
            this.tiltAccel = Math.sqrt(pitchAccel * pitchAccel + rollAccel * rollAccel);
        } else {
            this.tiltAccel = -Math.sqrt(pitchAccel * pitchAccel + rollAccel * rollAccel);
        }
    }

    public double getTiltPosition() {
        return this.tiltPosition;
    }

    public double getTiltVelocity() {
        return this.tiltVelocity;
    }

    @Override
    public void setRange(Range range) {
        accelerometer.setRange(range);
    }

    @Override
    public double getX() {
        return this.xAccel;
    }

    private void calcX() {
        this.xAccel = accelerometer.getX();
    }

    @Override
    public double getY() {
        return this.yAccel;
    }

    private void calcY() {
        this.yAccel = accelerometer.getY();
    }

    @Override
    public double getZ() {
        return this.zAccel;
    }

    private void calcZ() {
        this.zAccel = accelerometer.getZ();
    }

    private void putTiltSmartDash() {
        SmartDashboard.putNumber("tilt position theta", tiltPosition);
        SmartDashboard.putNumber("tilt velocity omega(lul)", tiltVelocity);
        SmartDashboard.putNumber("tilt acceleration alpha", tiltAccel);
    }

    @Override
    public void periodic() {
        calcX();
        calcY();
        calcZ();
        calcPitch();
        calcRoll();
        calcTilt();

        long currentTimeMillis = System.currentTimeMillis();
        double deltaTimeSeconds = ((double) (currentTimeMillis - lastTimeMillis)) / 1000.0;
        // ω = ω₀ + αΔt
        tiltVelocity = tiltVelocity + tiltAccel * deltaTimeSeconds;
        // θ = θ₀ + ωΔt
        tiltPosition = tiltPosition + tiltVelocity * deltaTimeSeconds;
        lastTimeMillis = currentTimeMillis;

        putTiltSmartDash();
    }
}
