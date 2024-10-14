package net.frc5183.librobot.hardware.gyro;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.ADIS16448_IMU;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.simulation.ADIS16448_IMUSim;
import org.junit.jupiter.api.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ADIS16448Tests {
    static final double DELTA = 1E-12;

    ADIS16448IMU imu;
    ADIS16448_IMU wpiIMU;
    ADIS16448_IMUSim sim;

    @BeforeEach
    void setup(TestInfo info) {
        if (info.getTags().contains("noBefore")) return;

        wpiIMU = new ADIS16448_IMU();
        sim = new ADIS16448_IMUSim(wpiIMU);
        imu = new ADIS16448IMU(wpiIMU);
    }

    @AfterEach
    void shutdown() {
        wpiIMU.close();
    }

    //<editor-fold desc="Constructor Tests">
    @Test()
    @Tag("noBefore")
    void testConstructor_givenNothing() {
        imu = new ADIS16448IMU();
        wpiIMU = imu.getIMU();
        sim = new ADIS16448_IMUSim(wpiIMU);

        assertEquals(SPI.Port.kMXP.value, wpiIMU.getPort(), "Default port is not MXP.");
        assertEquals(IMU.CartesianAxis.Z, imu.getYawAxis(), "Default yaw axis is not Z.");
    }

    @Test
    @Tag("noBefore")
    void testConstructor_givenYaw() {
        imu = new ADIS16448IMU(IMU.CartesianAxis.X);
        wpiIMU = imu.getIMU();
        sim = new ADIS16448_IMUSim(wpiIMU);

        assertEquals(SPI.Port.kMXP.value, wpiIMU.getPort(), "Default port is not MXP.");
        assertEquals(IMU.CartesianAxis.X, imu.getYawAxis(), "Given yaw axis is not the same.");
    }

    @Test
    @Tag("noBefore")
    void testConstructor_givenYawPort() {
        imu = new ADIS16448IMU(IMU.CartesianAxis.X, SPI.Port.kOnboardCS0);
        wpiIMU = imu.getIMU();
        sim = new ADIS16448_IMUSim(wpiIMU);

        assertEquals(SPI.Port.kOnboardCS0.value, wpiIMU.getPort(), "Given port is not the same.");
        assertEquals(IMU.CartesianAxis.X, imu.getYawAxis(), "Given yaw axis is not the same.");
    }

    @Test
    @Tag("noBefore")
    void testConstructor_givenYawPortCalibrationTime() {
        imu = new ADIS16448IMU(IMU.CartesianAxis.X, SPI.Port.kOnboardCS0, ADIS16448_IMU.CalibrationTime._2s);
        wpiIMU = imu.getIMU();
        sim = new ADIS16448_IMUSim(wpiIMU);

        assertEquals(SPI.Port.kOnboardCS0.value, wpiIMU.getPort(), "Given port is not the same.");
        assertEquals(IMU.CartesianAxis.X, imu.getYawAxis(), "Given yaw axis is not the same.");
    }
    //</editor-fold>

    //<editor-fold desc="Angle Tests">
    static final double TEST_ANGLE_1 = 0;
    static final double TEST_ANGLE_2 = 90;
    static final double TEST_ANGLE_3 = 180;
    static final double TEST_ANGLE_4 = 270;
    static final double TEST_ANGLE_5 = Math.random() * 360;

    @Test
    void testAngleYaw() {
        sim.setGyroAngleZ(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.YAW)), DELTA);
    }

    @Test
    void testAnglePitch() {
        sim.setGyroAngleX(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.PITCH)), DELTA);
    }

    @Test
    void testAngleRoll() {
        sim.setGyroAngleY(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.Attitude.ROLL)), DELTA);
    }

    @Test
    void testAngleCartesianX() {
        sim.setGyroAngleX(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);

        sim.setGyroAngleX(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.X)), DELTA);
    }

    @Test
    void testAngleCartesianY() {
        sim.setGyroAngleY(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);

        sim.setGyroAngleY(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Y)), DELTA);
    }

    @Test
    void testAngleCartesianZ() {
        sim.setGyroAngleZ(TEST_ANGLE_1);
        assertEquals(TEST_ANGLE_1, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_2);
        assertEquals(TEST_ANGLE_2, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_3);
        assertEquals(TEST_ANGLE_3, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_4);
        assertEquals(TEST_ANGLE_4, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);

        sim.setGyroAngleZ(TEST_ANGLE_5);
        assertEquals(TEST_ANGLE_5, Math.toDegrees(imu.getAngleRadians(IMU.CartesianAxis.Z)), DELTA);
    }

    @Test
    void testAngleRotation3d() {
        // Degrees
        Consumer<Double> setAngle = (Double angle) -> {
            sim.setGyroAngleX(angle);
            sim.setGyroAngleY(angle);
            sim.setGyroAngleZ(angle);
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
            sim.setGyroAngleX(angle);
            sim.setGyroAngleY(angle);
            sim.setGyroAngleZ(angle);
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

    //<editor-fold desc="Acceleration Tests">
    static final double TEST_ACCEL_1 = 0;
    static final double TEST_ACCEL_2 = 1;
    static final double TEST_ACCEL_3 = 2;
    static final double TEST_ACCEL_4 = 3;
    static final double TEST_ACCEL_5 = Math.random() * 3;

    @Test
    void testAccelerationCartesianX() {
        sim.setAccelX(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X));

        sim.setAccelX(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X));

        sim.setAccelX(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X));

        sim.setAccelX(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X));

        sim.setAccelX(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.X));
    }

    @Test
    void testAccelerationCartesianY() {
        sim.setAccelY(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y));

        sim.setAccelY(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y));

        sim.setAccelY(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y));

        sim.setAccelY(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y));

        sim.setAccelY(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Y));
    }

    @Test
    void testAccelerationCartesianZ() {
        sim.setAccelZ(TEST_ACCEL_1);
        assertEquals(TEST_ACCEL_1, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z));

        sim.setAccelZ(TEST_ACCEL_2);
        assertEquals(TEST_ACCEL_2, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z));

        sim.setAccelZ(TEST_ACCEL_3);
        assertEquals(TEST_ACCEL_3, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z));

        sim.setAccelZ(TEST_ACCEL_4);
        assertEquals(TEST_ACCEL_4, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z));

        sim.setAccelZ(TEST_ACCEL_5);
        assertEquals(TEST_ACCEL_5, imu.getAccelerationMetersPerSecondSquared(IMU.CartesianAxis.Z));
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
            assertEquals(
                    new Translation3d(expected, expected, expected),
                    imu.getAccelerationMetersPerSecondSquared(),
                    "Translation3d failed."
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
    void testCalibrate() {
        assertDoesNotThrow(() -> imu.calibrate());
        assertEquals(0, wpiIMU.getAccelX(), "X calibrate failed.");
        assertEquals(0, wpiIMU.getAccelY(), "Y calibrate failed.");
        assertEquals(0, wpiIMU.getAccelZ(), "Z calibrate failed.");
    }

    @Test
    void testReset() {
        assertDoesNotThrow(() -> imu.reset());
        assertEquals(0, wpiIMU.getAccelX(), "X reset failed.");
        assertEquals(0, wpiIMU.getAccelY(), "Y reset failed.");
        assertEquals(0, wpiIMU.getAccelZ(), "Z reset failed.");
    }

    @Test
    void testFactoryDefault() {
        assertDoesNotThrow(() -> imu.factoryDefault());
        assertEquals(0, wpiIMU.getAccelX(), "X factory default failed.");
        assertEquals(0, wpiIMU.getAccelY(), "Y factory default failed.");
        assertEquals(0, wpiIMU.getAccelZ(), "Z factory default failed.");

        assertEquals(0, imu.getOffsetX(), "X factory default failed.");
        assertEquals(0, imu.getOffsetY(), "Y factory default failed.");
        assertEquals(0, imu.getOffsetZ(), "Z factory default failed.");

        assertEquals(0, wpiIMU.getGyroAngleX(), "X factory default failed.");
        assertEquals(0, wpiIMU.getGyroAngleY(), "Y factory default failed.");
        assertEquals(0, wpiIMU.getGyroAngleZ(), "Z factory default failed.");
    }

    @Test
    void testIMU() {
        assertEquals(wpiIMU, imu.getIMU());
    }

    @Test
    void testToCartesian() {
        assertEquals(IMU.CartesianAxis.X, ADIS16448IMU.toCartesian(ADIS16448_IMU.IMUAxis.kX));
        assertEquals(IMU.CartesianAxis.Y, ADIS16448IMU.toCartesian(ADIS16448_IMU.IMUAxis.kY));
        assertEquals(IMU.CartesianAxis.Z, ADIS16448IMU.toCartesian(ADIS16448_IMU.IMUAxis.kZ));
    }

    @Test
    void testFromCartesian() {
        assertEquals(ADIS16448_IMU.IMUAxis.kX, ADIS16448IMU.fromCartesian(IMU.CartesianAxis.X));
        assertEquals(ADIS16448_IMU.IMUAxis.kY, ADIS16448IMU.fromCartesian(IMU.CartesianAxis.Y));
        assertEquals(ADIS16448_IMU.IMUAxis.kZ, ADIS16448IMU.fromCartesian(IMU.CartesianAxis.Z));
    }
}


