package net.frc5183.librobot.hardware.gyro.imu;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.SPI;
import net.frc5183.librobot.hardware.gyro.single.SingleAxisGyroscope;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an ADIS16470 {@link IMU}.
 */
public class ADIS16470IMU extends IMU {
    /**
     * The ADIS16470 IMU.
     */
    @NotNull
    private final ADIS16470_IMU imu;

    /**
     * The YAW axis.
     */
    private final IMUAxis yaw;

    /**
     * The PITCH axis.
     */
    private final IMUAxis pitch;

    /**
     * The ROLL axis.
     */
    private final IMUAxis roll;

    /**
     * Creates a new {@link ADIS16470IMU} using the RIO's Onboard MXP port and 500ms calibration time, and yaw pitch roll as ZXY respectively.
     * @see ADIS16470_IMU#ADIS16470_IMU()
     */
    public ADIS16470IMU() {
        this(new ADIS16470_IMU());
    }

    /**
     * Creates a new {@link ADIS16470IMU} from an existing ADIS16470 IMU instance.
     * @param imu the ADIS16470 IMU to use.
     */
    public ADIS16470IMU(@NotNull ADIS16470_IMU imu) {
        this.imu = imu;

        yaw = toIMUAxis(imu.getYawAxis());
        pitch = toIMUAxis(imu.getPitchAxis());
        roll = toIMUAxis(imu.getRollAxis());
    }

    /**
     * Creates a new {@link ADIS16470IMU} with a specified yaw, pitch, and roll axis.
     * Uses default RIO's Onboard MXP port and 500ms calibration time.
     * @param yaw the YAW axis.
     * @param pitch the PITCH axis.
     * @param roll the ROLL axis.
     */
    public ADIS16470IMU(IMUAxis yaw, IMUAxis pitch, IMUAxis roll) {
        this(yaw, pitch, roll, SPI.Port.kMXP);
    }

    /**
     * Creates a new {@link ADIS16470IMU} with a specified yaw, pitch, roll axis, and SPI port.
     * Uses default 4s calibration time.
     * @param yaw the YAW axis.
     * @param pitch the PITCH axis.
     * @param roll the ROLL axis.
     * @param port the SPI port.
     */
    public ADIS16470IMU(IMUAxis yaw, IMUAxis pitch, IMUAxis roll, SPI.Port port) {
        this(yaw, pitch, roll, port, ADIS16470_IMU.CalibrationTime._4s);
    }

    /**
     * Creates a new {@link ADIS16470IMU} with a specified yaw, pitch, roll axis, SPI port, and calibration time.
     * @param yaw the YAW axis.
     * @param pitch the PITCH axis.
     * @param roll the ROLL axis.
     * @param port the SPI port.
     * @param calibrationTime the calibration time.
     */
    public ADIS16470IMU(IMUAxis yaw, IMUAxis pitch, IMUAxis roll, SPI.Port port, ADIS16470_IMU.CalibrationTime calibrationTime) {
        imu = new ADIS16470_IMU(fromIMUAxis(yaw), fromIMUAxis(pitch), fromIMUAxis(roll), port, calibrationTime);

        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    @Override
    public double getAngleRadians(@NotNull SingleAxisGyroscope.Axis axis) {
        return imu.getAngle(fromSingleAxis(axis));
    }

    @Override
    public @NotNull Rotation3d getRawRotation3dRadians() {
        return new Rotation3d(
                Math.toRadians(imu.getAngle(fromIMUAxis(IMUAxis.X))),
                Math.toRadians(imu.getAngle(fromIMUAxis(IMUAxis.Y))),
                Math.toRadians(imu.getAngle(fromIMUAxis(IMUAxis.Z)))
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
    public @NotNull ADIS16470_IMU getRawIMU() {
        return imu;
    }

    /**
     * Returns a new {@link ADIS16470_IMU.IMUAxis} from an {@link IMUAxis}.
     * @param axis the {@link IMUAxis} to convert.
     * @return the converted {@link ADIS16470_IMU.IMUAxis}.
     */
    public static ADIS16470_IMU.IMUAxis fromIMUAxis(IMUAxis axis) {
        return switch (axis) {
            case X -> ADIS16470_IMU.IMUAxis.kX;
            case Y -> ADIS16470_IMU.IMUAxis.kY;
            case Z -> ADIS16470_IMU.IMUAxis.kZ;
        };
    }

    /**
     * Returns a new {@link ADIS16470_IMU.IMUAxis} from an {@link SingleAxisGyroscope.Axis}
     * @param axis the {@link SingleAxisGyroscope.Axis} to convert.
     * @return the converted {@link ADIS16470_IMU.IMUAxis}
     */
    public static ADIS16470_IMU.IMUAxis fromSingleAxis(SingleAxisGyroscope.Axis axis) {
        return switch (axis) {
            case YAW -> ADIS16470_IMU.IMUAxis.kYaw;
            case PITCH -> ADIS16470_IMU.IMUAxis.kPitch;
            case ROLL -> ADIS16470_IMU.IMUAxis.kRoll;
        };
    }

    /**
     * Returns a new {@link IMUAxis} from an {@link ADIS16470_IMU.IMUAxis}.
     * @param axis the {@link IMUAxis} to convert.
     * @return the converted {@link ADIS16470_IMU.IMUAxis}.
     */
    public static IMUAxis toIMUAxis(ADIS16470_IMU.IMUAxis axis) {
        return switch (axis) {
            case kX -> IMUAxis.X;
            case kY -> IMUAxis.Y;
            case kZ -> IMUAxis.Z;
            default -> throw new IllegalArgumentException("IMU axis must be one of  " + axis);
        };
    }
}
