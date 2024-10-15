package net.frc5183.librobot.hardware.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.units.Units;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a NavX {@link IMU}.
 */
public class NavXIMU extends IMU {
    /**
     * The NavX IMU.
     */
    @NotNull
    private final AHRS imu;

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
     * Creates a new {@link NavXIMU} using the RIO's Onboard MXP port and default update rate.
     * @see AHRS#AHRS()
     */
    public NavXIMU() {
        this(new AHRS());
    }

    /**
     * Creates a new {@link NavXIMU} from an existing NavX IMU instance.
     * @param imu the NavX IMU to use.
     */
    public NavXIMU(@NotNull AHRS imu) {
        this.imu = imu;

        yaw = toCartesian(imu.getBoardYawAxis().board_axis);

        CartesianAxis[] axes = CartesianAxis.assignAxes(yaw);
        pitch = axes[0];
        roll = axes[1];
    }

    /**
     * Creates a new {@link NavXIMU} with a specified SPI port and default update rate.
     * @param spiPort the SPI port to use.
     * @see AHRS#AHRS(SPI.Port)
     */
    public NavXIMU(@NotNull SPI.Port spiPort) {
        this(new AHRS(spiPort));
    }

    /**
     * Creates a new {@link NavXIMU} with a specified I2C port and default update rate.
     * @param i2cPort the I2C port to use.
     * @see AHRS#AHRS(I2C.Port)
     */
    public NavXIMU(@NotNull I2C.Port i2cPort) {
        this(new AHRS(i2cPort));
    }

    /**
     * Creates a new {@link NavXIMU} with a specified Serial port and default update rate.
     * @param serialPort the Serial port to use.
     * @see AHRS#AHRS(SerialPort.Port)
     */
    public NavXIMU(@NotNull SerialPort.Port serialPort) {
        this(new AHRS(serialPort));
    }


    @Override
    public double getRawAngleRadians(@NotNull Attitude axis) {
        return switch (axis) {
            case YAW -> Math.toRadians(convertNavXDegrees(imu.getYaw()));
            case PITCH -> Math.toRadians(convertNavXDegrees(imu.getPitch()));
            case ROLL -> Math.toRadians(convertNavXDegrees(imu.getRoll()));
        };
    }

    @Override
    public double getRawAngleRadians(@NotNull CartesianAxis axis) {
        Attitude attitude;

        if (axis == yaw) {
            attitude = Attitude.YAW;
        } else if (axis == pitch) {
            attitude = Attitude.PITCH;
        } else {
            attitude = Attitude.ROLL;
        }

        return getRawAngleRadians(attitude);
    }

    @Override
    public @NotNull Rotation3d getRawRotation3dRadians() {
        return new Rotation3d(
                getRawAngleRadians(Attitude.ROLL),
                getRawAngleRadians(Attitude.PITCH),
                getRawAngleRadians(Attitude.YAW)
        );
    }

    @Override
    public double getRateDegreesPerSecond(Attitude axis) {
        return switch (axis) {
            case YAW -> imu.getRate();
            case PITCH -> throw new UnsupportedOperationException("NavX does not support getting pitch rate.");
            case ROLL -> throw new UnsupportedOperationException("NavX does not support getting roll rate.");
        };
    }

    @Override
    public double getRateDegreesPerSecond(CartesianAxis axis) {
        Attitude attitude;

        if (axis == yaw) {
            attitude = Attitude.YAW;
        } else if (axis == pitch) {
            attitude = Attitude.PITCH;
        } else {
            attitude = Attitude.ROLL;
        }

        return getRateDegreesPerSecond(attitude);
    }

    @Override
    public @NotNull Translation3d getAccelerationMetersPerSecondSquared() {
        return new Translation3d(
                Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelX(), Units.Gs),
                Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelY(), Units.Gs),
                Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelZ(), Units.Gs)
        );
    }

    @Override
    public double getAccelerationMetersPerSecondSquared(@NotNull CartesianAxis axis) {
        return switch (axis) {
            case X -> Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelX(), Units.Gs);
            case Y -> Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelY(), Units.Gs);
            case Z -> Units.MetersPerSecondPerSecond.convertFrom(imu.getWorldLinearAccelZ(), Units.Gs);
        };
    }

    @Override
    public void calibrate() {
        imu.reset();
    }

    @Override
    public void reset() {
        imu.reset();
    }

    @Override
    public void factoryDefault() {
        imu.reset();
        super.setOffset(new Rotation3d());
    }

    @Override
    public void clearStickyFaults() {
        // The NavX IMU does not have a method for clearing sticky faults.
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
    public @NotNull CartesianAxis getRollAxis() {
        return roll;
    }

    @Override
    public @NotNull AHRS getIMU() {
        return imu;
    }

    /**
     * The NavX returns degrees from -180 to 180, however we return degrees from 0 to 360, so we need to convert it.
     * @param navXDegrees the angle from -180 to 180 degrees
     * @return the angle from 0 to 360 degrees.
     */
    private double convertNavXDegrees(double navXDegrees) {
        if (navXDegrees < 0) {
            return navXDegrees + 360;
        }

        return navXDegrees;
    }

    /**
     * Returns a new {@link CartesianAxis} from an {@link AHRS.BoardYawAxis}.
     * @param axis the {@link AHRS.BoardAxis} to convert.
     * @return the converted {@link CartesianAxis}.
     */
    @NotNull
    public static CartesianAxis toCartesian(@NotNull AHRS.BoardAxis axis) {
        return switch (axis) {
            case kBoardAxisX -> CartesianAxis.X;
            case kBoardAxisY -> CartesianAxis.Y;
            case kBoardAxisZ -> CartesianAxis.Z;
        };
    }

    /**
     * Returns a new {@link AHRS.BoardAxis} from an {@link CartesianAxis}.
     * @param axis the {@link CartesianAxis} to convert.
     * @return the converted {@link AHRS.BoardAxis}.
     */
    @NotNull
    public static AHRS.BoardAxis fromCartesian(@NotNull CartesianAxis axis) {
        return switch (axis) {
            case X -> AHRS.BoardAxis.kBoardAxisX;
            case Y -> AHRS.BoardAxis.kBoardAxisY;
            case Z -> AHRS.BoardAxis.kBoardAxisZ;
        };
    }
}

