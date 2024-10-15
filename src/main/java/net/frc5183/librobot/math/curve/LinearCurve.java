package net.frc5183.librobot.math.curve;

/**
 * A {@link Curve} which represents a linear equation in the form y = mx + b.
 */
public class LinearCurve extends Curve {
    /**
     * The slope of the curve.
     */
    private double slope;

    /**
     * The y-intercept of the curve.
     */
    private double yIntercept;

    /**
     * Creates a new {@link LinearCurve} with the given slope and y-intercept.
     * @param slope The slope of the curve.
     * @param yIntercept The y-intercept of the curve.
     */
    public LinearCurve(double slope, double yIntercept) {
        this.slope = slope;
        this.yIntercept = yIntercept;
    }

    @Override
    public double curve(double x) {
        // y = mx + b
        return slope * x + yIntercept;
    }

    /**
     * Returns the slope of the curve.
     * @return The slope of the curve.
     */
    public double getSlope() {
        return slope;
    }

    /**
     * Sets the slope of the curve.
     * @param slope The new slope of the curve.
     */
    public void setSlope(double slope) {
        this.slope = slope;
    }

    /**
     * Returns the y-intercept of the curve.
     * @return The y-intercept of the curve.
     */
    public double getYIntercept() {
        return yIntercept;
    }

    /**
     * Sets the y-intercept of the curve.
     * @param yIntercept The new y-intercept of the curve.
     */
    public void setYIntercept(double yIntercept) {
        this.yIntercept = yIntercept;
    }
}