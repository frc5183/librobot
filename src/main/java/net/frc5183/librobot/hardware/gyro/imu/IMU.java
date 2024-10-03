package net.frc5183.librobot.hardware.gyro.imu;

import edu.wpi.first.math.geometry.Rotation3d;

/**
 * Represents an IMU (Inertial Measurement Unit).
 */
public abstract class IMU {
    /**
     * The offset to be added to the gyroscope.
     */
    private Rotation3d offset = new Rotation3d(0, 0, 0);

    /**
     * Gets the rotation of the gyroscope (with offset) in degrees.
     * @return {@link Rotation3d} of the rotation of the gyroscope (with offset) in degrees.
     */
    public Rotation3d getRotation3dDegrees() {
        return getRawRotation3dDegrees().plus(getOffset());
    }

    /**
     * Gets the rotation of the gyroscope (without offset) in degrees.
     * @return {@link Rotation3d} of the rotation of the gyroscope (without offset) in degrees.
     */
    public abstract Rotation3d getRawRotation3dDegrees();

    /**
     * Gets the acceleration from the IMU in meters per second squared.
     * @return {@link Rotation3d} of the acceleration from the IMU in meters per second squared.
     */
    public abstract Rotation3d getAccelerationMetersPerSecondSquared();

    /**
     * Sets the offset of the gyroscope.
     * @param offset the offset of the gyroscope.
     */
    public void setOffset(Rotation3d offset) {
        this.offset = offset;
    }

    /**
     * Gets the offset of the gyroscope.
     * @return {@link Rotation3d} the offset of the gyroscope.
     */
    public Rotation3d getOffset() {
        return offset;
    }

    /**
     * Calibrates the gyroscope.
     */
    public abstract void calibrate();

    /**
     * Resets the gyroscope.
     */
    public abstract void reset();

    /**
     * Returns the raw gyroscope object.
     * Should only be used when you know the subclass of the gyroscope.
     * <br><br>
     * Implementations should override the return type to the type of the gyroscope used in the vendor's API.
     * (e.g the {@link ADIS16448IMU} would return {@link edu.wpi.first.wpilibj.ADIS16448_IMU})
     * @return the raw gyroscope object.
     */
    public abstract Object getRawIMU();

    /**
     * Represents the three axes of a gyroscope.
     */
    public enum IMUAxis {
        X, Y, Z
    }
}
