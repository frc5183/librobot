package net.frc5183.librobot.math.curve;

/**
 * A {@link Curve} which represents a radical equation in the form y = (x+C)^(1/A)+B.
 */
public class RadicalCurve extends Curve {
    /**
     * The "A" variable in the curve equation.
     */
    private double a;

    /**
     * The "B" variable in the curve equation.
     */
    private double b;

    /**
     * The "C" variable in the curve equation.
     */
    private double c;

    /**
     * Creates a new {@link RadicalCurve} with the given A, B, and C values.
     * @param a The "A" variable in the curve equation.
     * @param b The "B" variable in the curve equation.
     * @param c The "C" variable in the curve equation.
     */
    public RadicalCurve(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double curve(double x) {
        // y = (x+C)^(1/A)+B
        return Math.pow(x + c, 1 / a) + b;
    }

    /**
     * Returns the "A" variable in the curve equation.
     * @return The "A" variable in the curve equation.
     */
    public double getA() {
        return a;
    }

    /**
     * Sets the "A" variable in the curve equation.
     * @param a The new "A" variable in the curve equation.
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * Returns the "B" variable in the curve equation.
     * @return The "B" variable in the curve equation.
     */
    public double getB() {
        return b;
    }

    /**
     * Sets the "B" variable in the curve equation.
     * @param b The new "B" variable in the curve equation.
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * Returns the "C" variable in the curve equation.
     * @return The "C" variable in the curve equation.
     */
    public double getC() {
        return c;
    }

    /**
     * Sets the "C" variable in the curve equation.
     * @param c The new "C" variable in the curve equation.
     */
    public void setC(double c) {
        this.c = c;
    }
}