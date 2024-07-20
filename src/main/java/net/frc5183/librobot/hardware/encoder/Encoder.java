package net.frc5183.librobot.hardware.encoder;


/**
 * An abstract class that represents a one-axis gyroscope.
 */
// todo
public abstract class Encoder {
    /**
     * Returns the current position of the encoder in radians
     * @return the current position of the encoder in radians
     */
    public abstract double getUnitsRotations();

    /**
     * Returns the current velocity of the encoder in rotations per second
     * @return the current velocity of the encoder in rotations per second
     */
    public abstract double getVelocityRotationsPerSecond();

    /**
     * Resets the encoder's value to zero.
     */
    public abstract void reset();
}