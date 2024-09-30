package net.frc5183.librobot.math.curve;

/**
 * An {@link Curve} which represents an exponential function in the form y = (x/|x|) * ((1 + k)^|x| - 1) / k.
 * Where k is the exaggeration of the curve.
 */
public class ExponentialCurve extends Curve {
    /**
     * The exaggeration of the curve.
     */
    private double exaggeration;

    /**
     * Creates a new {@link ExponentialCurve} with the given exaggeration.
     * @param exaggeration The exaggeration of the curve.
     */
    public ExponentialCurve(double exaggeration) {
        this.exaggeration = exaggeration;
    }

    @Override
    public Double curve(Double x) {
        if (x == 0) return 0d;

        // y = (x/|x|) * ((1 + exaggeration)^|x| - 1) / exaggeration
        return (x / Math.abs(x)) * ((Math.pow(1 + exaggeration, Math.abs(x))) - 1) / exaggeration; // NOPMD - extra parentheses make the equation easier to read
    }

    /**
     * Returns the exaggeration of the curve.
     * @return The exaggeration of the curve.
     */
    public double getExaggeration() {
        return exaggeration;
    }

    /**
     * Sets the exaggeration of the curve.
     * @param exaggeration The new exaggeration of the curve.
     */
    public void setExaggeration(double exaggeration) {
        this.exaggeration = exaggeration;
    }
}