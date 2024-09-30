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
    private Double delayEnabled;

    /**
     * The delay in seconds before the curve is disabled.
     */
    private Double delayDisabled;

    /**
     * Creates a new {@link TimedCurve} with the given delays.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public TimedCurve(Double delayEnabled, Double delayDisabled) {
        this.delayEnabled = delayEnabled;
        this.delayDisabled = delayDisabled;
    }

    @Override
    public Double curve(Double x) {
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
    protected abstract Double enabled(Double x);

    /**
     * Returns the value of the curve when it is disabled.
     * @param x The x value to evaluate the curve at.
     * @return The value of the curve when it is disabled.
     */
    protected abstract Double disabled(Double x);

    /**
     * Returns whether the curve is currently disabled.
     * @return Whether the curve is currently disabled.
     */
    public Double getDelayEnabled() {
        return delayEnabled;
    }

    /**
     * Sets whether the curve is currently disabled.
     * @param delayEnabled Whether the curve is currently disabled.
     */
    public void setDelayEnabled(Double delayEnabled) {
        this.delayEnabled = delayEnabled;
    }

    /**
     * Returns the delay in seconds before the curve is disabled.
     * @return The delay in seconds before the curve is disabled.
     */
    public Double getDelayDisabled() {
        return delayDisabled;
    }

    /**
     * Sets the delay in seconds before the curve is disabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public void setDelayDisabled(Double delayDisabled) {
        this.delayDisabled = delayDisabled;
    }
}