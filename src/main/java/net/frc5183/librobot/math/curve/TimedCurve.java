package net.frc5183.librobot.math.curve;

import edu.wpi.first.wpilibj.Timer;

/**
 * A {@link Curve} which toggles between two outputs based on a timer.
 */
public abstract class TimedCurve extends Curve {
    /**
     * The timer used to keep track of the time.
     */
    private final Timer timer = new Timer();

    /**
     * Whether the curve is currently disabled.
     */
    private boolean disabled = true;

    /**
     * The delay in seconds before the curve is enabled.
     */
    private double delayEnabled;

    /**
     * The delay in seconds before the curve is disabled.
     */
    private double delayDisabled;

    /**
     * Creates a new {@link TimedCurve} with the given delays.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public TimedCurve(double delayEnabled, double delayDisabled) {
        this.delayEnabled = delayEnabled;
        this.delayDisabled = delayDisabled;
    }

    @Override
    public double curve(double x) {
        if (disabled && timer.hasElapsed(delayDisabled)) {
            disabled = false;
            timer.reset();
        } else if (!disabled && timer.hasElapsed(delayEnabled)) {
            disabled = true;
            timer.reset();
        }

        return disabled ? disabled(x) : enabled(x);
    }

    /**
     * Returns the value of the curve when it is enabled.
     * @param x The x value to evaluate the curve at.
     * @return The value of the curve when it is enabled.
     */
    protected abstract double enabled(double x);

    /**
     * Returns the value of the curve when it is disabled.
     * @param x The x value to evaluate the curve at.
     * @return The value of the curve when it is disabled.
     */
    protected abstract double disabled(double x);

    /**
     * Returns the delay in seconds before the curve is enabled.
     * @return The delay in seconds before the curve is enabled.
     */
    public double getDelayEnabled() {
        return delayEnabled;
    }

    /**
     * Sets the delay in seconds before the curve is enabled.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     */
    public void setDelayEnabled(double delayEnabled) {
        this.delayEnabled = delayEnabled;
    }

    /**
     * Returns the delay in seconds before the curve is disabled.
     * @return The delay in seconds before the curve is disabled.
     */
    public double getDelayDisabled() {
        return delayDisabled;
    }

    /**
     * Sets the delay in seconds before the curve is disabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public void setDelayDisabled(double delayDisabled) {
        this.delayDisabled = delayDisabled;
    }
}