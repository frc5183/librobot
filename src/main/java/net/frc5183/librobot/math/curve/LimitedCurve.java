package net.frc5183.librobot.math.curve;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link Curve} which limits the output of another curve to a certain range.
 */
public class LimitedCurve extends Curve {
    /**
     * The curve to limit.
     */
    @NotNull
    private Curve curve;

    /**
     * The minimum value of the curve.
     */
    private double min;

    /**
     * The maximum value of the curve.
     */
    private double max;

    /**
     * Creates a new {@link LimitedCurve} with the given curve, minimum, and maximum values.
     * @param curve The curve to limit.
     * @param min The minimum value of the curve.
     * @param max The maximum value of the curve.
     * @throws IllegalArgumentException If the minimum value is greater than the maximum value.
     */
    public LimitedCurve(@NotNull Curve curve, double min, double max) {
        if (min > max) throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");

        this.curve = curve;
        this.min = min;
        this.max = max;
    }

    @Override
    public double curve(double x) {
        double y = curve.curve(x);
        return Math.min(Math.max(y, min), max);
    }

    /**
     * Returns the curve to limit.
     * @return The curve to limit.
     */
    public @NotNull Curve getCurve() {
        return curve;
    }

    /**
     * Sets the curve to limit.
     * @param curve The new curve to limit.
     */
    public void setCurve(@NotNull Curve curve) {
        this.curve = curve;
    }

    /**
     * Returns the minimum value of the curve.
     * @return The minimum value of the curve.
     */
    public double getMin() {
        return min;
    }

    /**
     * Sets the minimum value of the curve.
     * @param min The new minimum value of the curve.
     * @throws IllegalArgumentException If the minimum value is greater than the maximum value.
     */
    public void setMin(double min) {
        if (min > max) throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        this.min = min;
    }

    /**
     * Returns the maximum value of the curve.
     * @return The maximum value of the curve.
     */
    public double getMax() {
        return max;
    }

    /**
     * Sets the maximum value of the curve.
     * @param max The new maximum value of the curve.
     * @throws IllegalArgumentException If the minimum value is greater than the maximum value.
     */
    public void setMax(double max) {
        if (min > max) throw new IllegalArgumentException("Minimum value cannot be greater than maximum value.");
        this.max = max;
    }
}