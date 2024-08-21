package net.frc5183.librobot.hardware.motor;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

/**
 * Represents a Spark motor controller.
 */
public class SparkMotor extends Motor {
    private final Spark motor;

    public SparkMotor(int port) {
        this.motor = new Spark(port);
    }

    public SparkMotor(Spark motor) {
        this.motor = motor;
    }

    @Override
    public void set(double speed) {
        motor.set(speed);
    }

    @Override
    public void periodic() {
        motor.feed();
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setSafety(boolean on) {
        motor.setSafetyEnabled(on);
    }

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    public Spark getRawMotor() {
        return motor;
    }
}
