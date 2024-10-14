package net.frc5183.librobot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import org.jetbrains.annotations.Nullable;
import swervelib.imu.SwerveIMU;

import java.util.Map;
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
     * The {@link Rotation3d} offset of the gyroscope.
     * @deprecated use {@link #offsetX}, {@link #offsetY}, and {@link #offsetZ} instead, this only exists for YAGSL.<br>Changes are not reflected on {@link #offsetX}, {@link #offsetY}, and {@link #offsetZ}.
     */
    @Deprecated
    @Nullable
    private Rotation3d offset;

    /**
     * The offset to be subtracted from the gyroscope's X axis (in radians).
     */
    private double offsetX;

    /**
     * The offset to be subtracted from the gyroscope's Y axis (in radians).
     */
    private double offsetY;

    /**
     * The offset to be subtracted from the gyroscope's Z axis (in radians).
     */
    private double offsetZ;

    /**
     * Gets the specified angle's rotation (with offset) in radians.
     * @param axis the {@link CartesianAxis} to get the angle's rotation of.
     * @return the angle's rotation (with offset) in radians.
     */
    public double getAngleRadians(CartesianAxis axis) {
        return switch (axis) {
            case Z -> getRawAngleRadians(axis) - offsetZ;
            case Y -> getRawAngleRadians(axis) - offsetY;
            case X -> getRawAngleRadians(axis) - offsetX;
        };
    }

    /**
     * Gets the specified angle's rotation (with offset) in radians.
     * @param axis the {@link Attitude} to get the angle's rotation of.
     * @return the angle's rotation (with offset) in radians.
     */
    public double getAngleRadians(Attitude axis) {
        return switch (axis) {
            case YAW -> getAngleRadians(getYawAxis());
            case PITCH -> getAngleRadians(getPitchAxis());
            case ROLL -> getAngleRadians(getRollAxis());
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
     * Returns the rotation rate of the specified axis in degrees per second.
     * @param axis the {@link Attitude} to get the rotation rate of.
     * @return the rotation rate of the specified axis in degrees per second.
     */
    public abstract double getRateDegreesPerSecond(Attitude axis);

    /**
     * Returns the rotation rate of the specified axis in degrees per second.
     * @param axis the {@link CartesianAxis} to get the rotation rate of.
     * @return the rotation rate of the specified axis in degrees per second.
     */
    public abstract double getRateDegreesPerSecond(CartesianAxis axis);

    /**
     * Returns the rotation rate of the yaw axis in degrees per second.
     * @return the rotation rate of the yaw axis in degrees per second.
     * @deprecated this only exists for YAGSL, use {@link #getRateDegreesPerSecond(Attitude)} instead (since that's what this method calls internally).
     * @see #getRateDegreesPerSecond(Attitude)
     */
    public double getRate() {
        return getRateDegreesPerSecond(Attitude.YAW);
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
     * Returns the offset as a {@link Rotation3d}.
     * <br><br>
     * If {@link #offset} is null, it will be calculated from {@link #offsetX}, {@link #offsetY}, and {@link #offsetZ}.
     * @return the offset as a {@link Rotation3d}.
     * @deprecated use {@link #getOffsetX()}, {@link #getOffsetY()}, and {@link #getOffsetZ()} instead, this only exists for YAGSL.
     */
    @Deprecated
    public Rotation3d getOffset() {
        if (offset != null) return offset;

        Map<CartesianAxis, Double> offset = Map.of(
                CartesianAxis.X, offsetX,
                CartesianAxis.Y, offsetY,
                CartesianAxis.Z, offsetZ
        );

        return new Rotation3d(
                offset.get(getYawAxis()),
                offset.get(getPitchAxis()),
                offset.get(getRollAxis())
        );
    }

    /**
     * Sets the offset as a {@link Rotation3d}.
     * @param offset the offset as a {@link Rotation3d}.
     * @deprecated use {@link #setOffsetX(double)}, {@link #setOffsetY(double)}, and {@link #setOffsetZ(double)} instead, this only exists for YAGSL.<br>Changes are not reflected on {@link #offsetX}, {@link #offsetY}, and {@link #offsetZ}.
     */
    @Deprecated
    public void setOffset(@Nullable Rotation3d offset) {
        this.offset = offset;
    }

    /**
     * Returns the offset to be subtracted from the gyroscope's rotation.
     * @return the offset to be subtracted from the gyroscope's rotation.
     */
    public double getOffsetX() {
        return offsetX;
    }

    /**
     * Sets the offset to be subtracted from the gyroscope's rotation.
     * @param offsetX the offset to be subtracted from the gyroscope's rotation.
     */
    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    /**
     * Returns the offset to be subtracted from the gyroscope's rotation.
     * @return the offset to be subtracted from the gyroscope's rotation.
     */
    public double getOffsetY() {
        return offsetY;
    }

    /**
     * Sets the offset to be subtracted from the gyroscope's rotation.
     * @param offsetY the offset to be subtracted from the gyroscope's rotation.
     */
    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Returns the offset to be subtracted from the gyroscope's rotation.
     * @return the offset to be subtracted from the gyroscope's rotation.
     */
    public double getOffsetZ() {
        return offsetZ;
    }

    /**
     * Sets the offset to be subtracted from the gyroscope's rotation.
     * @param offsetZ the offset to be subtracted from the gyroscope's rotation.
     */
    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
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
