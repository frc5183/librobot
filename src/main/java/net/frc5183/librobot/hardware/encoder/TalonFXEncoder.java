package net.frc5183.librobot.hardware.encoder;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;

/**
 * An implementation of Encoder for TalonFX Motors
 * Accepts any raw TalonFX motor including CAN_TalonFX
 */
public class TalonFXEncoder extends Encoder {
    private final TalonFX motor;
    private final StatusSignal<Double> signal;
    private final StatusSignal<Double> velocity;
    public TalonFXEncoder(TalonFX tx) {
        signal = tx.getPosition();
        motor = tx;
        velocity = tx.getVelocity();
    }
    public double getUnitsRadians() {
        signal.refresh(); return signal.getValue() * 2 * Math.PI;
    }

    @Override
    public double getUnitsRotations() {
        signal.refresh();  return signal.getValue();
    }

    public double getUnitsDegrees() {
        signal.refresh();  return signal.getValue() * 360;
    }

    public double getVelocityRadiansPerSecond() {
        return getVelocityRotationsPerSecond()*2*Math.PI;
    }

    public double getVelocityRotationsPerMinute() {
        return getVelocityRotationsPerSecond() * 60;
    }

    @Override
    public double getVelocityRotationsPerSecond() {
        velocity.refresh();
        return velocity.getValue();
    }


    public double getVelocityDegrees() {
        return getVelocityRotationsPerMinute()*360;
    }

    @Override
    public void reset() {
        motor.setPosition(0);
    }
}