package net.frc5183.librobot.hardware.gyro;

public abstract class SingleAxisGyroscope {
    /**
     * @return the angle in degrees
     */
    public abstract double getAngle();

    /**
     * @return the Rotation2d of the gyro.
     */
    public abstract Rotation2d getRotation2d();

    /**
     * Calibrates the gyroscope
     */
    public abstract void calibrate();

    /**
     * Resets the gyroscope
     */
    public abstract void reset();

    /**
     * Sets the offset of the gyroscope
     */
    public abstract void setOffset(double offset);

    /**
     * @return the offset of the gyroscope
     */
    public abstract double getOffset();

    /**
     * @return the axis of the gyroscope
     */
    public abstract Axis getAxis();

    /**
     * The axis of the gyroscope
     */
    public enum Axis {
        YAW, PITCH, ROLL
    }
}
