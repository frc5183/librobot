package net.frc5183.librobot.math.curve;

/**
 * A {@link Curve} which limits the output of another curve to a certain range.
 */
public class LimitedCurve extends Curve {
    /**
     * The curve to limit.
     */
    private Curve curve;

    /**
     * The minimum value of the curve.
     */
    private Double min;

    /**
     * The maximum value of the curve.
     */
    private Double max;

    /**
     * Creates a new {@link LimitedCurve} with the given curve, minimum, and maximum values.
     * @param curve The curve to limit.
     * @param min The minimum value of the curve.
     * @param max The maximum value of the curve.
     */
    public LimitedCurve(Curve curve, Double min, Double max) {
        this.curve = curve;
        this.min = min;
        this.max = max;
    }

    @Override
    public Double curve(Double x) {
        Double y = curve.curve(x);
        return Math.min(Math.max(y, min), max);
    }

    /**
     * Returns the curve to limit.
     * @return The curve to limit.
     */
    public Curve getCurve() {
        return curve;
    }

    /**
     * Sets the curve to limit.
     * @param curve The new curve to limit.
     */
    public void setCurve(Curve curve) {
        this.curve = curve;
    }

    /**
     * Returns the minimum value of the curve.
     * @return The minimum value of the curve.
     */
    public Double getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the curve.
     * @param min The new minimum value of the curve.
     */
    public void setMin(Double min) {
        this.min = min;
    }

    /**
     * Returns the maximum value of the curve.
     * @return The maximum value of the curve.
     */
    public Double getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the curve.
     * @param max The new maximum value of the curve.
     */
    public void setMax(Double max) {
        this.max = max;
    }
}