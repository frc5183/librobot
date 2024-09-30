package net.frc5183.librobot.math.curve;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link TimedCurve} that toggles between 0 (when disabled) and curve(x) (when enabled);
 */
public class ZeroTimedCurve extends TimedCurve {
    /**
     * The curve to use when this curve is enabled.
     */
    @NotNull
    private Curve curve;

    /**
     * Creates a new {@link ZeroTimedCurve} with the given curve and delays.
     * @param curve The curve to use when this curve is enabled.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public ZeroTimedCurve(@NotNull Curve curve, double delayEnabled, double delayDisabled) {
        super(delayEnabled, delayDisabled);
        this.curve = curve;
    }

    @Override
    protected double enabled(double x) {
        return curve.curve(x);
    }

    @Override
    protected double disabled(double x) {
        return 0.0;
    }

    /**
     * Returns the curve to use when this curve is enabled.
     * @return The curve to use when this curve is enabled.
     */
    @NotNull
    public Curve getCurve() {
        return curve;
    }

    /**
     * Sets the curve to use when this curve is enabled.
     * @param curve The new curve to use when the curve is enabled.
     */
    public void setCurve(@NotNull Curve curve) {
        this.curve = curve;
    }
}