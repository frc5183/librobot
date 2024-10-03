package net.frc5183.librobot.hardware.gyro.imu;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
    private final IMUAxis yaw;

    /**
     * The pitch axis.
     */
    @NotNull
    private final IMUAxis pitch;

    /**
     * The roll axis.
     */
    private final IMUAxis roll;

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
        yaw = toIMUAxis(imu.getYawAxis());

        //todo: confirm this is correct i had copilot do it
        if (yaw == IMUAxis.X) {
            pitch = IMUAxis.Y;
            roll = IMUAxis.Z;
        } else if (yaw == IMUAxis.Y) {
            pitch = IMUAxis.X;
            roll = IMUAxis.Z;
        } else {
            pitch = IMUAxis.X;
            roll = IMUAxis.Y;
        }

        this.imu = imu;
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis.
     * Uses default RIO's Onboard MXP port and 512ms calibration time.
     * @param yaw the YAW axis.
     */
    public ADIS16448IMU(@NotNull IMUAxis yaw) {
        this(yaw, SPI.Port.kMXP);
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis and SPI port.
     * Uses default 512ms calibration time.
     * @param yaw the YAW axis.
     * @param port the SPI port.
     */
    public ADIS16448IMU(@NotNull IMUAxis yaw, @NotNull SPI.Port port) {
        this(yaw, port, ADIS16448_IMU.CalibrationTime._512ms);
    }

    /**
     * Creates a new {@link ADIS16448IMU} with a specified yaw axis, SPI port, and calibration time.
     * @param yaw the YAW axis.
     * @param port the SPI port.
     * @param calibrationTime the calibration time.
     */
    public ADIS16448IMU(@NotNull IMUAxis yaw, @NotNull SPI.Port port, @NotNull ADIS16448_IMU.CalibrationTime calibrationTime) {
        this.yaw = yaw;

        //todo: confirm this is correct i had copilot do it
        if (yaw == IMUAxis.X) {
            pitch = IMUAxis.Y;
            roll = IMUAxis.Z;
        } else if (yaw == IMUAxis.Y) {
            pitch = IMUAxis.X;
            roll = IMUAxis.Z;
        } else {
            pitch = IMUAxis.X;
            roll = IMUAxis.Y;
        }

        imu = new ADIS16448_IMU(fromIMUAxis(yaw), port, calibrationTime);
    }

    @Override
    public @NotNull Rotation3d getRawRotation3d() {
        return new Rotation3d(
                Math.toRadians(imu.getGyroAngleX()),
                Math.toRadians(imu.getGyroAngleY()),
                Math.toRadians(imu.getGyroAngleZ())
        );
    }

    @Override
    public @NotNull Translation3d getAccelerationMetersPerSecondSquared() {
        return new Translation3d(imu.getAccelX(), imu.getAccelY(), imu.getAccelZ());
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
    public @NotNull ADIS16448_IMU getRawIMU() {
        return imu;
    }

    /**
     * Returns a new {@link ADIS16448_IMU.IMUAxis} from an {@link IMUAxis}.
     * @param axis the {@link IMUAxis} to convert.
     * @return the converted {@link ADIS16448_IMU.IMUAxis}.
     */
    @NotNull
    public static ADIS16448_IMU.IMUAxis fromIMUAxis(@NotNull IMUAxis axis) {
        return switch (axis) {
            case X -> ADIS16448_IMU.IMUAxis.kX;
            case Y -> ADIS16448_IMU.IMUAxis.kY;
            case Z -> ADIS16448_IMU.IMUAxis.kZ;
        };
    }


    /**
     * Returns a new {@link IMUAxis} from an {@link ADIS16448_IMU.IMUAxis}.
     * @param axis the {@link ADIS16448_IMU.IMUAxis} to convert.
     * @return the converted {@link IMUAxis}.
     */
    @NotNull
    public static IMUAxis toIMUAxis(@NotNull ADIS16448_IMU.IMUAxis axis) {
        return switch (axis) {
            case kX -> IMUAxis.X;
            case kY -> IMUAxis.Y;
            case kZ -> IMUAxis.Z;
        };
    }
}