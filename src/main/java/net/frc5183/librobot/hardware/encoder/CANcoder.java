package net.frc5183.librobot.hardware.encoder;

public class CANcoder extends Encoder {
    private final com.ctre.phoenix6.hardware.CANcoder encoder;

    public CANcoder (com.ctre.phoenix6.hardware.CANcoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public double getUnitsRotations() {
        return encoder.getPosition().getValue();
    }

    @Override
    public double getVelocityRotationsPerSecond() {
        return encoder.getVelocity().getValue();
    }

    @Override
    public void reset() {
        encoder.setPosition(0);
    }
}
