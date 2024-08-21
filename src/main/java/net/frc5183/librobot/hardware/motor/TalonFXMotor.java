package net.frc5183.librobot.hardware.motor;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/**
 * Represents a TalonFX motor controller.
 */
public class TalonFXMotor extends Motor {
    private final TalonFX motor;

    public TalonFXMotor(int port) {
        motor = new TalonFX(port);
    }

    public TalonFXMotor(TalonFX motor) {
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

    public TalonFX getRawMotor() {
        return motor;
    }
}
