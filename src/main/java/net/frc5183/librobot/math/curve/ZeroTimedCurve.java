package net.frc5183.librobot.math.curve;

/**
 * A {@link TimedCurve} that toggles between 0 (when disabled) and curve(x) (when enabled);
 */
public class ZeroTimedCurve extends TimedCurve {
    /**
     * The curve to use when this curve is enabled.
     */
    private Curve curve;

    /**
     * Creates a new {@link ZeroTimedCurve} with the given curve and delays.
     * @param curve The curve to use when this curve is enabled.
     * @param delayEnabled The delay in seconds before the curve is enabled.
     * @param delayDisabled The delay in seconds before the curve is disabled.
     */
    public ZeroTimedCurve(Curve curve, Double delayEnabled, Double delayDisabled) {
        super(delayEnabled, delayDisabled);
        this.curve = curve;
    }

    @Override
    protected Double enabled(Double x) {
        return curve.curve(x);
    }

    @Override
    protected Double disabled(Double x) {
        return 0.0;
    }

    /**
     * Returns the curve to use when this curve is enabled.
     * @return The curve to use when this curve is enabled.
     */
    public Curve getCurve() {
        return curve;
    }

    /**
     * Sets the curve to use when this curve is enabled.
     * @param curve The new curve to use when the curve is enabled.
     */
    public void setCurve(Curve curve) {
        this.curve = curve;
    }
}