package net.frc5183.librobot.hardware.gyro;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class NavXTests {
    static final double DELTA = 1E-4;

    static boolean canResetSimData = false;

    NavXIMU imu;
    AHRS wpiIMU;
    NavXSim sim;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTags().contains("noBefore")) return;

        if (canResetSimData)
            SimDeviceSim.resetData();
        else canResetSimData = true;

        wpiIMU = new AHRS();
        sim = new NavXSim();
        imu = new NavXIMU(wpiIMU);
    }

    //<editor-fold desc="Angle Tests">
    static final double TEST_ANGLE_1 = 0;
    static final double TEST_ANGLE_2 = 90;
    static final double TEST_ANGLE_3 = 180;
    static final double TEST_ANGLE_4 = 270;
    static final double TEST_ANGLE_5 = Math.random() * 360;

    @Test
    void testAngleYaw() {
        sim.setYaw(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setYaw(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setYaw(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setYaw(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setYaw(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);
    }

    @Test
    void testAnglePitch() {
        sim.setPitch(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setPitch(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setPitch(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setPitch(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setPitch(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);
    }

    @Test
    void testAngleRoll() {
        sim.setRoll(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setRoll(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setRoll(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setRoll(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setRoll(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);
    }


    // Default Omnimount for NavX is X = PITCH, Y = ROLL, Z = YAW.
    @Test
    void testAngleCartesianX() {
        sim.setPitch(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setPitch(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setPitch(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setPitch(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setPitch(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);
    }

    @Test
    void testAngleCartesianY() {
        sim.setRoll(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setRoll(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setRoll(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setRoll(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setRoll(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);
    }

    @Test
    void testAngleCartesianZ() {
        sim.setYaw(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setYaw(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setYaw(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setYaw(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setYaw(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);
    }

    @Test
    void testAngleRotation3d() {
        // Degrees
        Consumer<Double> setAngle = (Double angle) -> {
            sim.setYaw(angle);
            sim.setPitch(angle);
            sim.setRoll(angle);
        };

        // Radians
        Consumer<Double> verifyRot = (Double angle) -> {
            assertEquals(
                    new Rotation3d(
                            Math.toRadians(angle),
                            Math.toRadians(angle),
                            Math.toRadians(angle)
                    ),
                    imu.getRotation3dRadians(),
                    "Rotation3d failed."
            );
        };

        setAngle.accept(TEST_ANGLE_1);
        verifyRot.accept(TEST_ANGLE_1);

        setAngle.accept(TEST_ANGLE_2);
        verifyRot.accept(TEST_ANGLE_2);

        setAngle.accept(TEST_ANGLE_3);
        verifyRot.accept(TEST_ANGLE_3);

        setAngle.accept(TEST_ANGLE_4);
        verifyRot.accept(TEST_ANGLE_4);

        setAngle.accept(TEST_ANGLE_5);
        verifyRot.accept(TEST_ANGLE_5);
    }

    @Test
    void testAngleOffset() {
        // Radians.
        Consumer<Double> setOffset = (Double offset) -> {
            imu.setOffsetX((double) offset);
            imu.setOffsetY((double) offset);
            imu.setOffsetZ((double) offset);
        };

        // Degrees.
        Consumer<Double> setAngle = (Double angle) -> {
            sim.setYaw(angle);
            sim.setPitch(angle);
            sim.setRoll(angle);
        };

        // Radians.
        BiConsumer<Double, Double> testAngles = (Double angle, Double offset) -> {
            double expected = angle - offset;

            assertEquals(
                    expected,
                    imu.getAngleRadians(IMU.CartesianAxis.X),
                    DELTA,
                    "X offset failed."
            );

            assertEquals(
                    expected,
                    imu.getAngleRadians(IMU.CartesianAxis.Y),
                    DELTA,
                    "Y offset failed."
            );

            assertEquals(
                    expected,
                    imu.getAngleRadians(IMU.CartesianAxis.Z),
                    DELTA,
                    "Z offset failed."
            );

            assertEquals(
                    new Rotation3d(
                            angle, angle, angle
                    ).minus(new Rotation3d(offset, offset, offset)),
                    imu.getRotation3dRadians(),
                    "Rotation3d offset failed."
            );
        };


        setOffset.accept(Math.toRadians(TEST_ANGLE_1));
        //<editor-fold desc="TEST_ANGLE_1 as offset.">
        setAngle.accept(TEST_ANGLE_1);
        testAngles.accept(Math.toRadians(TEST_ANGLE_1), Math.toRadians(TEST_ANGLE_1));

        setAngle.accept(TEST_ANGLE_2);
        testAngles.accept(Math.toRadians(TEST_ANGLE_2), Math.toRadians(TEST_ANGLE_1));

        setAngle.accept(TEST_ANGLE_3);
        testAngles.accept(Math.toRadians(TEST_ANGLE_3), Math.toRadians(TEST_ANGLE_1));

        setAngle.accept(TEST_ANGLE_4);
        testAngles.accept(Math.toRadians(TEST_ANGLE_4), Math.toRadians(TEST_ANGLE_1));

        setAngle.accept(TEST_ANGLE_5);
        testAngles.accept(Math.toRadians(TEST_ANGLE_5), Math.toRadians(TEST_ANGLE_1));
        //</editor-fold>

        setOffset.accept(Math.toRadians(TEST_ANGLE_2));
        //<editor-fold desc="TEST_ANGLE_2 as offset.">
        setAngle.accept(TEST_ANGLE_1);
        testAngles.accept(Math.toRadians(TEST_ANGLE_1), Math.toRadians(TEST_ANGLE_2));

        setAngle.accept(TEST_ANGLE_2);
        testAngles.accept(Math.toRadians(TEST_ANGLE_2), Math.toRadians(TEST_ANGLE_2));

        setAngle.accept(TEST_ANGLE_3);
        testAngles.accept(Math.toRadians(TEST_ANGLE_3), Math.toRadians(TEST_ANGLE_2));

        setAngle.accept(TEST_ANGLE_4);
        testAngles.accept(Math.toRadians(TEST_ANGLE_4), Math.toRadians(TEST_ANGLE_2));

        setAngle.accept(TEST_ANGLE_5);
        testAngles.accept(Math.toRadians(TEST_ANGLE_5), Math.toRadians(TEST_ANGLE_2));
        //</editor-fold>

        setOffset.accept(Math.toRadians(TEST_ANGLE_3));
        //<editor-fold desc="TEST_ANGLE_3 as offset.">
        setAngle.accept(TEST_ANGLE_1);
        testAngles.accept(Math.toRadians(TEST_ANGLE_1), Math.toRadians(TEST_ANGLE_3));

        setAngle.accept(TEST_ANGLE_2);
        testAngles.accept(Math.toRadians(TEST_ANGLE_2), Math.toRadians(TEST_ANGLE_3));

        setAngle.accept(TEST_ANGLE_3);
        testAngles.accept(Math.toRadians(TEST_ANGLE_3), Math.toRadians(TEST_ANGLE_3));

        setAngle.accept(TEST_ANGLE_4);
        testAngles.accept(Math.toRadians(TEST_ANGLE_4), Math.toRadians(TEST_ANGLE_3));

        setAngle.accept(TEST_ANGLE_5);
        testAngles.accept(Math.toRadians(TEST_ANGLE_5), Math.toRadians(TEST_ANGLE_3));
        //</editor-fold>

        setOffset.accept(Math.toRadians(TEST_ANGLE_4));
        //<editor-fold desc="TEST_ANGLE_4 as offset.">
        setAngle.accept(TEST_ANGLE_1);
        testAngles.accept(Math.toRadians(TEST_ANGLE_1), Math.toRadians(TEST_ANGLE_4));

        setAngle.accept(TEST_ANGLE_2);
        testAngles.accept(Math.toRadians(TEST_ANGLE_2), Math.toRadians(TEST_ANGLE_4));

        setAngle.accept(TEST_ANGLE_3);
        testAngles.accept(Math.toRadians(TEST_ANGLE_3), Math.toRadians(TEST_ANGLE_4));

        setAngle.accept(TEST_ANGLE_4);
        testAngles.accept(Math.toRadians(TEST_ANGLE_4), Math.toRadians(TEST_ANGLE_4));

        setAngle.accept(TEST_ANGLE_5);
        testAngles.accept(Math.toRadians(TEST_ANGLE_5), Math.toRadians(TEST_ANGLE_4));
        //</editor-fold>

        setOffset.accept(Math.toRadians(TEST_ANGLE_5));
        //<editor-fold desc="TEST_ANGLE_5 as offset.">
        setAngle.accept(TEST_ANGLE_1);
        testAngles.accept(Math.toRadians(TEST_ANGLE_1), Math.toRadians(TEST_ANGLE_5));

        setAngle.accept(TEST_ANGLE_2);
        testAngles.accept(Math.toRadians(TEST_ANGLE_2), Math.toRadians(TEST_ANGLE_5));

        setAngle.accept(TEST_ANGLE_3);
        testAngles.accept(Math.toRadians(TEST_ANGLE_3), Math.toRadians(TEST_ANGLE_5));

        setAngle.accept(TEST_ANGLE_4);
        testAngles.accept(Math.toRadians(TEST_ANGLE_4), Math.toRadians(TEST_ANGLE_5));

        setAngle.accept(TEST_ANGLE_5);
        testAngles.accept(Math.toRadians(TEST_ANGLE_5), Math.toRadians(TEST_ANGLE_5));
        //</editor-fold>
    }

    //</editor-fold>

    //<editor-fold desc="Angle Rate Tests">
    @Test
    void testRateCartesianX() {
        assertThrowsExactly(
                UnsupportedOperationException.class,
                () -> imu.getRateDegreesPerSecond(IMU.CartesianAxis.X)
        );
    }

    @Test
    void testRateCartesianY() {
        assertThrowsExactly(
                UnsupportedOperationException.class,
                () -> imu.getRateDegreesPerSecond(IMU.CartesianAxis.Y)
        );
    }

    // Only test YAW (aka Z with default omnimount) because the other axes are unsupported.
    @Test
    void testRateCartesianZ() {
        sim.setRate(TEST_ANGLE_1/2);
        assertEquals(TEST_ANGLE_1/2, imu.getRateDegreesPerSecond(IMU.CartesianAxis.Z), DELTA);

        sim.setRate(TEST_ANGLE_2/2);
        assertEquals(TEST_ANGLE_2/2, imu.getRateDegreesPerSecond(IMU.CartesianAxis.Z), DELTA);

        sim.setRate(TEST_ANGLE_3/2);
        assertEquals(TEST_ANGLE_3/2, imu.getRateDegreesPerSecond(IMU.CartesianAxis.Z), DELTA);

        sim.setRate(TEST_ANGLE_4/2);
        assertEquals(TEST_ANGLE_4/2, imu.getRateDegreesPerSecond(IMU.CartesianAxis.Z), DELTA);

        sim.setRate(TEST_ANGLE_5/2);
        assertEquals(TEST_ANGLE_5/2, imu.getRateDegreesPerSecond(IMU.CartesianAxis.Z), DELTA);
    }

    @Test
    void testRateYaw() {
        sim.setRate(TEST_ANGLE_1/2);
        assertEquals(TEST_ANGLE_1/2, imu.getRateDegreesPerSecond(IMU.Attitude.YAW), DELTA);

        sim.setRate(TEST_ANGLE_2/2);
        assertEquals(TEST_ANGLE_2/2, imu.getRateDegreesPerSecond(IMU.Attitude.YAW), DELTA);

        sim.setRate(TEST_ANGLE_3/2);
        assertEquals(TEST_ANGLE_3/2, imu.getRateDegreesPerSecond(IMU.Attitude.YAW), DELTA);

        sim.setRate(TEST_ANGLE_4/2);
        assertEquals(TEST_ANGLE_4/2, imu.getRateDegreesPerSecond(IMU.Attitude.YAW), DELTA);

        sim.setRate(TEST_ANGLE_5/2);
        assertEquals(TEST_ANGLE_5/2, imu.getRateDegreesPerSecond(IMU.Attitude.YAW), DELTA);
    }

    @Test
    void testRatePitch() {
        assertThrowsExactly(
                UnsupportedOperationException.class,
                () -> imu.getRateDegreesPerSecond(IMU.Attitude.PITCH)
        );
    }

    @Test
    void testRateRoll() {
        assertThrowsExactly(
                UnsupportedOperationException.class,
                () -> imu.getRateDegreesPerSecond(IMU.Attitude.ROLL)
        );
    }
    //</editor-fold>

    //<editor-fold desc="Acceleration Tests">
    static final double TEST_ACCEL_1 = 0;
    static final double TEST_ACCEL_2 = 1;
    static final double TEST_ACCEL_3 = 2;
    static final double TEST_ACCEL_4 = 3;
    static final double TEST_ACCEL_5 = Math.random() * 3;

    @Test
    void testAccelerationCartesianX() {
        sim.setAccelX(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X), DELTA);

        sim.setAccelX(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X), DELTA);

        sim.setAccelX(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X), DELTA);

        sim.setAccelX(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X), DELTA);

        sim.setAccelX(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X), DELTA);
    }

    @Test
    void testAccelerationCartesianY() {
        sim.setAccelY(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y), DELTA);

        sim.setAccelY(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y), DELTA);

        sim.setAccelY(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y), DELTA);

        sim.setAccelY(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y), DELTA);

        sim.setAccelY(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y), DELTA);
    }

    @Test
    void testAccelerationCartesianZ() {
        sim.setAccelZ(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z), DELTA);

        sim.setAccelZ(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z), DELTA);

        sim.setAccelZ(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z), DELTA);

        sim.setAccelZ(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z), DELTA);

        sim.setAccelZ(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z), DELTA);
    }

    @Test
    void testAccelerationTranslation3d() {
        // Meters per second squared.
        Consumer<Double> setAccel = (Double accel) -> {
            sim.setAccelX(accel);
            sim.setAccelY(accel);
            sim.setAccelZ(accel);
        };

        // Meters per second squared.
        BiConsumer<Double, Double> testAccel = (Double accel, Double expected) -> {
            Translation3d translation = imu.getAccelerationMetersPerSecondSquared();

            assertEquals(
                    accel,
                    translation.getX(),
                    DELTA,
                    "X failed."
            );

            assertEquals(
                    accel,
                    translation.getY(),
                    DELTA,
                    "Y failed."
            );

            assertEquals(
                    accel,
                    translation.getZ(),
                    DELTA,
                    "Z failed."
            );
        };

        setAccel.accept(TEST_ACCEL_1);
        testAccel.accept(TEST_ACCEL_1, TEST_ACCEL_1);

        setAccel.accept(TEST_ACCEL_2);
        testAccel.accept(TEST_ACCEL_2, TEST_ACCEL_2);

        setAccel.accept(TEST_ACCEL_3);
        testAccel.accept(TEST_ACCEL_3, TEST_ACCEL_3);

        setAccel.accept(TEST_ACCEL_4);
        testAccel.accept(TEST_ACCEL_4, TEST_ACCEL_4);

        setAccel.accept(TEST_ACCEL_5);
        testAccel.accept(TEST_ACCEL_5, TEST_ACCEL_5);
    }
    //</editor-fold>

    //<editor-fold desc="Misc Tests">
    @Test
    void testReset() {
        sim.setYaw(33);
        assertDoesNotThrow(() -> imu.reset());
    }

    @Test
    void testIMU() {
        assertEquals(wpiIMU, imu.getIMU());
    }

    @Test
    void testToCartesian() {
        assertEquals(IMU.CartesianAxis.X, NavXIMU.toCartesian(AHRS.BoardAxis.kBoardAxisX));
        assertEquals(IMU.CartesianAxis.Y, NavXIMU.toCartesian(AHRS.BoardAxis.kBoardAxisY));
        assertEquals(IMU.CartesianAxis.Z, NavXIMU.toCartesian(AHRS.BoardAxis.kBoardAxisZ));
    }

    @Test
    void testFromCartesian() {
        assertEquals(AHRS.BoardAxis.kBoardAxisX, NavXIMU.fromCartesian(IMU.CartesianAxis.X));
        assertEquals(AHRS.BoardAxis.kBoardAxisY, NavXIMU.fromCartesian(IMU.CartesianAxis.Y));
        assertEquals(AHRS.BoardAxis.kBoardAxisZ, NavXIMU.fromCartesian(IMU.CartesianAxis.Z));
    }
    //</editor-fold>

    /**
     * Class to control a simulated NavX gyroscope.
     */
    class NavXSim {
        private static final int waitDuration = 45;

        private final SimDouble yaw;
        private final SimDouble pitch;
        private final SimDouble roll;

        private final SimDouble rate;

        private final SimDouble accelX;
        private final SimDouble accelY;
        private final SimDouble accelZ;

        public NavXSim() {
            int deviceHandle = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");

            yaw = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "Yaw"));
            pitch = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "Pitch"));
            roll = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "Roll"));

            rate = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "Rate"));

            accelX = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "LinearWorldAccelX"));
            accelY = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "LinearWorldAccelY"));
            accelZ = new SimDouble(SimDeviceDataJNI.getSimValueHandle(deviceHandle, "LinearWorldAccelZ"));
        }

        /**
         * NavX simulation does not update on-demand, so we have to wait until it updates next.
         * I could grab this value programmatically, but I'm lazy and the current delay seems fine.
         */
        private void delay() {
            try {
                Thread.sleep(waitDuration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void setYaw(double angle) {
            yaw.set(angle);
            delay();
        }

        public void setPitch(double angle) {
            pitch.set(angle);
            delay();
        }

        public void setRoll(double angle) {
            roll.set(angle);
            delay();
        }

        public void setRate(double rate) {
            this.rate.set(rate);
            delay();
        }

        public void setAccelX(double accel) {
            accelX.set(accel);
            delay();
        }

        public void setAccelY(double accel) {
            accelY.set(accel);
            delay();
        }

        public void setAccelZ(double accel) {
            accelZ.set(accel);
            delay();
        }

        public void reset() {
            yaw.set(0);
            pitch.set(0);
            roll.set(0);
            accelX.set(0);
            accelY.set(0);
            accelZ.set(0);
            delay();
        }
    }
}


