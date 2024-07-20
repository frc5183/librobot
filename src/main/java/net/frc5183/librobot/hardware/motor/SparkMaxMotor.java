package net.frc5183.librobot.hardware.motor;

import com.revrobotics.SparkRelativeEncoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import net.frc5183.librobot.hardware.encoder.Encoder;
import net.frc5183.librobot.hardware.encoder.SparkMaxEncoder;

/**
 * A wrapper class around CANSparkMax to make it compatible with other motor types
 */
public class SparkMaxMotor extends Motor implements EncodedMotor {
    private final CANSparkMax motor;
    private final Encoder encoder;

    public SparkMaxMotor(int id, MotorType motorType) {
        motor = new CANSparkMax(id, motorType);
        encoder = new SparkMaxEncoder(motor.getEncoder());
    }

    public SparkMaxMotor(int id, MotorType motorType, SparkRelativeEncoder.Type encoderType, int countsPerRev) {
        motor = new CANSparkMax(id, motorType);
        encoder = new SparkMaxEncoder(motor.getEncoder(encoderType, countsPerRev));
    }

    @Override
    public void set(double speed) {
        motor.set(speed);
        if (RobotBase.isSimulation()) {
            motor.setVoltage(speed);
        }
    }

    @Override
    public void periodic() {
        // The Spark Max does not have a periodic method.
    }

    @Override
    public double get() {
        return motor.get();
    }

    @Override
    public void setSafety(boolean on) {
        // The Spark Max does not have a setSafety method.
    }

    @Override
    public void setInverted(boolean inverted) {
        motor.setInverted(inverted);
    }

    @Override
    public boolean getInverted() {
        return motor.getInverted();
    }

    @Override
    public void disable() {
        motor.disable();
    }

    @Override
    public void stopMotor() {
        motor.stopMotor();
    }

    @Override
    public MotorController getRawMotor() {
        return motor;
    }

    public CANSparkMax getTrueRawMotor() {
        return motor;
    }

    public void setRamp(double ramp) {
        motor.setOpenLoopRampRate(ramp);
        motor.setClosedLoopRampRate(ramp);
    }

    @Override
    public Encoder getEncoder() {
        return encoder;
    }
}