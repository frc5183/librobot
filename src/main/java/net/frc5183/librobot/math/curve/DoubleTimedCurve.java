package net.frc5183.librobot.math.curve;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link TimedCurve} that toggles between two curves.
 */
public class DoubleTimedCurve extends TimedCurve {
    /**
     * The curve to use when the curve is enabled.
     */
    @NotNull
    private Curve enabledCurve;

    /**
     * The curve to use when the curve is disabled.
     */
    @NotNull
    private Curve disabledCurve;

    /**
     * Creates a new {@link DoubleTimedCurve} with the given curves and delays.
     * @param enabledCurve The curve to use when this curve is enabled.
     * @param disabledCurve The curve to use when this curve is disabled.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     * @see TimedCurve#TimedCurve(double, double)
     */
    public DoubleTimedCurve(@NotNull Curve enabledCurve, @NotNull Curve disabledCurve, double delayEnabled, double delayDisabled) {
        super(delayEnabled, delayDisabled);
        this.enabledCurve = enabledCurve;
        this.disabledCurve = disabledCurve;
    }

    @Override
    protected double enabled(double x) {
        return enabledCurve.curve(x);
    }

    @Override
    protected double disabled(double x) {
        return disabledCurve.curve(x);
    }

    /**
     * Returns the curve to use when this curve is enabled.
     * @return The curve to use when this curve is enabled.
     */
    public @NotNull Curve getEnabledCurve() {
        return enabledCurve;
    }

    /**
     * Sets the curve to use when this curve is enabled.
     * @param enabledCurve The new curve to use when the curve is enabled.
     */
    public void setEnabledCurve(@NotNull Curve enabledCurve) {
        this.enabledCurve = enabledCurve;
    }

    /**
     * Returns the curve to use when this curve is disabled.
     * @return The curve to use when this curve is disabled.
     */
    public @NotNull Curve getDisabledCurve() {
        return disabledCurve;
    }

    /**
     * Sets the curve to use when this curve is disabled.
     * @param disabledCurve The new curve to use when the curve is disabled.
     */
    public void setDisabledCurve(@NotNull Curve disabledCurve) {
        this.disabledCurve = disabledCurve;
    }
}