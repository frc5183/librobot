package net.frc5183.librobot.hardware.encoder;

import com.revrobotics.RelativeEncoder;

public class SparkMaxEncoder extends Encoder {
    private final RelativeEncoder encoder;

    public SparkMaxEncoder(RelativeEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public double getUnitsRotations() {
        return encoder.getPosition();
    }

    @Override
    public double getVelocityRotationsPerSecond() {
        return encoder.getVelocity() / 60;
    }

    @Override
    public void reset() {
        encoder.setPosition(0);
    }
}
