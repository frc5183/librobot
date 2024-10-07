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
     * Creates a new {@link ADIS16470IMU} using the RIO's Onboard MXP port and 4s calibration time, and yaw pitch roll as ZXY respectively.
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
     * Uses default RIO's Onboard MXP port and 4s calibration time.
     * @param yaw the YAW axis.
     * @param pitch the PITCH axis.
     * @param roll the ROLL axis.
     */
    public ADIS16470IMU(
            @NotNull IMUAxis yaw,
            @NotNull IMUAxis pitch,
            @NotNull IMUAxis roll) {
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
    public ADIS16470IMU(
            @NotNull IMUAxis yaw,
            @NotNull IMUAxis pitch,
            @NotNull IMUAxis roll,
            @NotNull SPI.Port port
    ) {
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
    public ADIS16470IMU(
            @NotNull IMUAxis yaw,
            @NotNull IMUAxis pitch,
            @NotNull IMUAxis roll,
            @NotNull SPI.Port port,
            @NotNull ADIS16470_IMU.CalibrationTime calibrationTime
    ) {
        imu = new ADIS16470_IMU(
                fromIMUAxis(yaw),
                fromIMUAxis(pitch),
                fromIMUAxis(roll),
                port,
                calibrationTime
        );

        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    @Override
    public double getRawAngleRadians(@NotNull SingleAxisGyroscope.Axis axis) {
        return imu.getAngle(fromSingleAxis(axis));
    }

    @Override
    public double getRawAngleRadians(@NotNull IMUAxis axis) {
        return imu.getAngle(fromIMUAxis(axis));
    }

    @Override
    public @NotNull Rotation3d getRawRotation3dRadians() {
        return new Rotation3d(
                Math.toRadians(imu.getAngle(fromIMUAxis(this.roll))),
                Math.toRadians(imu.getAngle(fromIMUAxis(this.pitch))),
                Math.toRadians(imu.getAngle(fromIMUAxis(this.yaw)))
        );
    }

    @Override
    public @NotNull Translation3d getAccelerationMetersPerSecondSquared() {
        return new Translation3d(imu.getAccelX(), imu.getAccelY(), imu.getAccelZ());
    }

    @Override
    public double getAccelerationMetersPerSecondSquared(IMUAxis axis) {
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
        // The ADIS16470 IMU does not have a method for clearing sticky faults.
    }

    @Override
    public IMUAxis getYawAxis() {
        return yaw;
    }

    @Override
    public IMUAxis getPitchAxis() {
        return pitch;
    }

    @Override
    public IMUAxis getRollAxis() {
        return roll;
    }

    @Override
    public @NotNull ADIS16470_IMU getIMU() {
        return imu;
    }

    /**
     * Returns a new {@link ADIS16470_IMU.IMUAxis} from an {@link IMUAxis}.
     * @param axis the {@link IMUAxis} to convert.
     * @return the converted {@link ADIS16470_IMU.IMUAxis}.
     */
    @NotNull
    public static ADIS16470_IMU.IMUAxis fromIMUAxis(@NotNull IMUAxis axis) {
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
    @NotNull
    public static ADIS16470_IMU.IMUAxis fromSingleAxis(@NotNull SingleAxisGyroscope.Axis axis) {
        return switch (axis) {
            case YAW -> ADIS16470_IMU.IMUAxis.kYaw;
            case PITCH -> ADIS16470_IMU.IMUAxis.kPitch;
            case ROLL -> ADIS16470_IMU.IMUAxis.kRoll;
        };
    }

    /**
     * Returns a new {@link IMUAxis} from an {@link ADIS16470_IMU.IMUAxis}.
     * @param axis the {@link ADIS16470_IMU.IMUAxis} to convert.
     * @return the converted {@link IMUAxis}.
     * @throws IllegalArgumentException if {@code axis} is not one of kX, kY, or kZ.
     */
    public static IMUAxis toIMUAxis(@NotNull ADIS16470_IMU.IMUAxis axis) {
        return switch (axis) {
            case kX -> IMUAxis.X;
            case kY -> IMUAxis.Y;
            case kZ -> IMUAxis.Z;
            default -> throw new IllegalArgumentException("ADIS16470_IMU Axis must be one of kX, kY, or kZ. Was: " + axis);
        };
    }

    /**
     * Returns a new {@link SingleAxisGyroscope.Axis} from an {@link ADIS16470_IMU.IMUAxis}.
     * @param axis the {@link ADIS16470_IMU.IMUAxis} to convert.
     * @return the converted {@link SingleAxisGyroscope.Axis}.
     * @throws IllegalArgumentException if {@code axis} is not one of kYaw, kPitch, or kRoll.
     */
    public static SingleAxisGyroscope.Axis toSingleAxis(@NotNull ADIS16470_IMU.IMUAxis axis) {
        return switch (axis) {
            case kYaw -> SingleAxisGyroscope.Axis.YAW;
            case kPitch -> SingleAxisGyroscope.Axis.PITCH;
            case kRoll -> SingleAxisGyroscope.Axis.ROLL;
            default -> throw new IllegalArgumentException("ADIS16470_IMU Axis must be one of kYaw, kPitch, or kRoll. Was" + axis);
        };
    }
}
