package net.frc5183.librobot.hardware.gyro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IMUTests {
    @Test
    void testAssignCartesianAxes() {
        IMU.CartesianAxis[] yawX = IMU.CartesianAxis.assignAxes(IMU.CartesianAxis.X);
        assertEquals(IMU.CartesianAxis.Y, yawX[0], "Incorrect pitch when yaw is X.");
        assertEquals(IMU.CartesianAxis.Z, yawX[1], "Incorrect roll when yaw is X.");

        IMU.CartesianAxis[] yawY = IMU.CartesianAxis.assignAxes(IMU.CartesianAxis.Y);
        assertEquals(IMU.CartesianAxis.X, yawY[0], "Incorrect pitch when yaw is Y.");
        assertEquals(IMU.CartesianAxis.Z, yawY[1], "Incorrect roll when yaw is Y.");

        IMU.CartesianAxis[] yawZ = IMU.CartesianAxis.assignAxes(IMU.CartesianAxis.Z);
        assertEquals(IMU.CartesianAxis.X, yawZ[0], "Incorrect pitch when yaw is Z.");
        assertEquals(IMU.CartesianAxis.Y, yawZ[1], "Incorrect roll when yaw is Z.");
    }
}
