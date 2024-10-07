package net.frc5183.librobot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import swervelib.imu.SwerveIMU;

import java.util.Optional;

/**
 * Represents an IMU (Inertial Measurement Unit).
 */
public abstract class IMU extends SwerveIMU {
    /**
     * Whether the IMU's values should be inverted.
     */
    private boolean inverted = false;

    /**
     * The offset to be added to the gyroscope.
     */
    private Rotation3d offset = new Rotation3d(0, 0, 0);

    /**
     * Gets the specified angle's rotation (with offset) in radians.
     * @param axis the {@link CartesianAxis} to get the angle's rotation of.
     * @return the angle's rotation (with offset) in radians.
     */
    public double getAngleRadians(CartesianAxis axis) {
        return switch (axis) {
            case Z -> getRawAngleRadians(axis) + offset.getZ();
            case Y -> getRawAngleRadians(axis) + offset.getY();
            case X -> getRawAngleRadians(axis) + offset.getX();
        };
    }

    /**
     * Gets the specified angle's rotation (with offset) in radians.
     * @param axis the {@link Attitude} to get the angle's rotation of.
     * @return the angle's rotation (with offset) in radians.
     */
    public double getAngleRadians(Attitude axis) {
        return switch (axis) {
            case YAW -> getRawAngleRadians(axis) + offset.getZ();
            case PITCH -> getRawAngleRadians(axis) + offset.getY();
            case ROLL -> getRawAngleRadians(axis) + offset.getX();
        };
    }

    /**
     * Gets the specified angle's rotation (without offset) in radians.
     * @param axis the {@link Attitude} to get the angle's rotation of.
     * @return the angle's rotation (without offset) in radians.
     */
    public abstract double getRawAngleRadians(Attitude axis);

    /**
     * Gets the specified angle's rotation (without offset) in radians.
     * @param axis the {@link CartesianAxis} to get the angle's rotation of.
     * @return the angle's rotation (without offset) in radians.
     */
    public abstract double getRawAngleRadians(CartesianAxis axis);

    /**
     * Gets the rotation of the gyroscope (with offset and inverted state) in radians.
     * @return {@link Rotation3d} of the rotation of the gyroscope (with offset and inverted state) in radians.
     */
    public Rotation3d getRotation3dRadians() {
        return !inverted ? getRawRotation3dRadians().minus(getOffset()) : getRawRotation3dRadians().minus(getOffset()).unaryMinus();
    }

    /**
     * Gets the rotation of the gyroscope (with offset and inverted state) in radians.
     * @return {@link Rotation3d} of the rotation of the gyroscope (with offset and inverted state) in radians.
     * @deprecated this only exists for YAGSL, use {@link #getRotation3dRadians()} instead (since that's what this method calls internally).
     * @see #getRotation3dRadians()
     */
    public Rotation3d getRawRotation3d() {
        return getRawRotation3dRadians();
    }

    /**
     * Gets the rotation of the gyroscope (without offset or inverted state) in radians.
     * @return {@link Rotation3d} of the rotation of the gyroscope (without offset or inverted state) in radians.
     */
    public abstract Rotation3d getRawRotation3dRadians();

    /**
     * Gets the rotation of the gyroscope (without offset or inverted state) in radians.
     * @return {@link Rotation3d} of the rotation of the gyroscope (without offset or inverted state) in radians.
     * @deprecated this only exists for YAGSL, use {@link #getRotation3dRadians()} instead (since that's what this method calls internally).
     * @see #getRotation3dRadians()
     */
    public Rotation3d getRotation3d() {
        return getRotation3dRadians();
    }

    /**
     * Gets the acceleration from the IMU in meters per second squared.
     * @return {@link Translation3d} of the acceleration from the IMU in meters per second squared.
     */
    public abstract Translation3d getAccelerationMetersPerSecondSquared();

    /**
     * Returns the acceleration of the IMU.
     * @return {@link Optional <Translation3d>} of the acceleration of the IMU.
     * @see #getAccelerationMetersPerSecondSquared()
     * @deprecated this only exists for use by YAGSL, use {@link #getAccelerationMetersPerSecondSquared()} instead (since that's what this method calls internally).
     */
    public Optional<Translation3d> getAccel() {
        return Optional.of(getAccelerationMetersPerSecondSquared());
    }

    /**
     * Gets the acceleration from the IMU in meters per second squared on a specific axis.
     * @param axis the {@link CartesianAxis} to get the acceleration of.
     * @return the acceleration from the IMU in meters per second squared on a specific axis.
     */
    public abstract double getAccelerationMetersPerSecondSquared(CartesianAxis axis);

    /**
     * Sets the offset of the gyroscope in radians.
     * @param offset the {@link Rotation3d} of the offset of the gyroscope in radians.
     */
    public void setOffset(Rotation3d offset) {
        this.offset = offset;
    }

    /**
     * Gets the offset of the gyroscope in radians.
     * @return {@link Rotation3d} the offset of the gyroscope in radians.
     */
    public Rotation3d getOffset() {
        return offset;
    }

    /**
     * Sets the IMU's values to be inverted.
     * @param inverted whether the IMU's values should be inverted.
     */
    public void setInverted(boolean inverted) {
        this.inverted = inverted;
    }

    /**
     * Gets whether the IMU's values are inverted.
     * @return whether the IMU's values are inverted.
     */
    public boolean getInverted() {
        return inverted;
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
     * Resets the IMU's settings to factory defaults and clears offsets.
     */
    public abstract void factoryDefault();

    /**
     * Clears all sticky faults on the IMU.
     */
    public abstract void clearStickyFaults();

    /**
     * Returns the {@link CartesianAxis} of the yaw.
     * @return the {@link CartesianAxis} of the yaw.
     */
    public abstract CartesianAxis getYawAxis();

    /**
     * Returns the {@link CartesianAxis} of the pitch.
     * @return the {@link CartesianAxis} of the pitch.
     */
    public abstract CartesianAxis getPitchAxis();

    /**
     * Returns the {@link CartesianAxis} of the roll.
     * @return the {@link CartesianAxis} of the roll.
     */
    public abstract CartesianAxis getRollAxis();

    /**
     * Returns the raw gyroscope object.
     * Should only be used when you know the subclass of the gyroscope.
     * <br><br>
     * Implementations should override the return type to the type of the gyroscope used in the vendor's API.
     * (e.g the {@link ADIS16448IMU} would return {@link edu.wpi.first.wpilibj.ADIS16448_IMU})
     * @return the raw gyroscope object.
     */
    public abstract Object getIMU();

    /**
     * Represents the cartesian axes of a gyroscope (xyz).
     */
    public enum CartesianAxis {
        X, Y, Z;

        /**
         * A utility method used to assign the pitch and roll axes based on the yaw axis.
         * @param yaw the yaw axis.
         * @return an array of the pitch and roll axes, index 0 and 1 respectively.
         */
        public static CartesianAxis[] assignAxes(CartesianAxis yaw) {
            CartesianAxis pitch;
            CartesianAxis roll;

            if (yaw == X) {
                pitch = Y;
                roll = Z;
            } else if (yaw == Y) {
                pitch = X;
                roll = Z;
            } else {
                pitch = X;
                roll = Y;
            }

            return new CartesianAxis[]{pitch, roll};
        }
    }

    /**
     * Represents the attitude of a gyroscope (yaw, pitch, roll).
     */
    public enum Attitude {
        YAW, PITCH, ROLL
    }
}
