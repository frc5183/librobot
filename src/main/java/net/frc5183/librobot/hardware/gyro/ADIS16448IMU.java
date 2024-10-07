package net.frc5183.librobot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an ADIS16448 {@link IMU}.
 */
public class ADIS16448IMU extends IMU {
    /**
     * The ADIS16448 IMU.
     */
    @NotNull
    private final ADIS16448_IMU imu;

    /**
     * The yaw axis.
     */
    @NotNull
    private final CartesianAxis yaw;

    /**
     * The pitch axis.
     */
    @NotNull
    private final CartesianAxis pitch;

    /**
     * The roll axis.
     */
    @NotNull
    private final CartesianAxis roll;

    /**
     * Creates a new {@link ADIS16448IMU} using the RIO's Onboard MXP port, 500ms calibration time, and Z axis as yaw.
     * @see ADIS16448_IMU#ADIS16448_IMU()
     */
    public ADIS16448IMU() {
        this(new ADIS16448_IMU());
    }

    /**
     * Creates a new {@link ADIS16448IMU} from an existing ADIS16448 IMU instance.
     * @param imu the ADIS16448 IMU to use.
     */
    public ADIS16448IMU(@NotNull ADIS16448_IMU imu) {
        yaw = toCartesian(imu.getYawAxis());
        CartesianAxis[] axes = CartesianAxis.assignAxes(yaw);
        pitch = axes[0];
        roll = axes[1];

        this.imu = imu;
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis.
     * Uses default RIO's Onboard MXP port and 512ms calibration time.
     * @param yaw the YAW axis.
     */
    public ADIS16448IMU(@NotNull CartesianAxis yaw) {
        this(yaw, SPI.Port.kMXP);
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis and SPI port.
     * Uses default 512ms calibration time.
     * @param yaw the YAW axis.
     * @param port the SPI port.
     */
    public ADIS16448IMU(@NotNull CartesianAxis yaw, @NotNull SPI.Port port) {
        this(yaw, port, ADIS16448_IMU.CalibrationTime._512ms);
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis, SPI port, and calibration time.
     * @param yaw the YAW axis.
     * @param port the SPI port.
     * @param calibrationTime the calibration time.
     */
    public ADIS16448IMU(@NotNull CartesianAxis yaw, @NotNull SPI.Port port, @NotNull ADIS16448_IMU.CalibrationTime calibrationTime) {
        this.yaw = yaw;
        CartesianAxis[] axes = CartesianAxis.assignAxes(yaw);
        pitch = axes[0];
        roll = axes[1];

        imu = new ADIS16448_IMU(fromCartesian(yaw), port, calibrationTime);
    }

    @Override
    public double getRawAngleRadians(@NotNull Attitude axis) {
        CartesianAxis cartesianAxis = switch (axis) {
            case YAW -> yaw;
            case PITCH -> pitch;
            case ROLL -> roll;
        };

        return getRawAngleRadians(cartesianAxis);
    }

    @Override
    public double getRawAngleRadians(CartesianAxis axis) {
        return switch (axis) {
            case X -> imu.getGyroAngleX();
            case Y -> imu.getGyroAngleY();
            case Z -> imu.getGyroAngleZ();
        };
    }

    @Override
    public @NotNull Rotation3d getRawRotation3dRadians() {
        return new Rotation3d(
                getRawAngleRadians(this.roll),
                getRawAngleRadians(this.pitch),
                getRawAngleRadians(this.yaw)
        );
    }

    @Override
    public @NotNull Translation3d getAccelerationMetersPerSecondSquared() {
        return new Translation3d(imu.getAccelX(), imu.getAccelY(), imu.getAccelZ());
    }

    @Override
    public double getAccelerationMetersPerSecondSquared(CartesianAxis axis) {
        return switch (axis) {
            case X -> imu.getAccelX();
            case Y -> imu.getAccelY();
            case Z -> imu.getAccelZ();
        };
    }

    @Override
    public void calibrate() {
        imu.calibrate();
    }

    @Override
    public void reset() {
        imu.reset();
    }

    @Override
    public void factoryDefault() {
        imu.calibrate();
        super.setOffset(new Rotation3d());
    }

    @Override
    public void clearStickyFaults() {
        // The ADIS16448 IMU does not have a method for clearing sticky faults.
    }

    @Override
    public @NotNull CartesianAxis getYawAxis() {
        return yaw;
    }

    @Override
    public @NotNull CartesianAxis getPitchAxis() {
        return pitch;
    }

    @Override
    public CartesianAxis getRollAxis() {
        return roll;
    }

    @Override
    public @NotNull ADIS16448_IMU getIMU() {
        return imu;
    }

    /**
     * Returns a new {@link ADIS16448_IMU.IMUAxis} from an {@link CartesianAxis}.
     * @param axis the {@link CartesianAxis} to convert.
     * @return the converted {@link ADIS16448_IMU.IMUAxis}.
     */
    @NotNull
    public static ADIS16448_IMU.IMUAxis fromCartesian(@NotNull CartesianAxis axis) {
        return switch (axis) {
            case X -> ADIS16448_IMU.IMUAxis.kX;
            case Y -> ADIS16448_IMU.IMUAxis.kY;
            case Z -> ADIS16448_IMU.IMUAxis.kZ;
        };
    }

    /**
     * Returns a new {@link CartesianAxis} from an {@link ADIS16448_IMU.IMUAxis}.
     * @param axis the {@link ADIS16448_IMU.IMUAxis} to convert.
     * @return the converted {@link CartesianAxis}.
     */
    @NotNull
    public static CartesianAxis toCartesian(@NotNull ADIS16448_IMU.IMUAxis axis) {
        return switch (axis) {
            case kX -> CartesianAxis.X;
            case kY -> CartesianAxis.Y;
            case kZ -> CartesianAxis.Z;
        };
    }
}