package net.frc5183.librobot.math.curve;

/**
 * A {@link Curve} which represents a quadratic equation in the form Ax^2 + Bx + C.
 */
public class QuadraticCurve extends Curve {
    /**
     * The "A" variable in the curve equation.
     */
    private Double a;

    /**
     * The "B" variable in the curve equation.
     */
    private Double b;

    /**
     * The "C" variable in the curve equation.
     */
    private Double c;

    /**
     * Creates a new {@link QuadraticCurve} with the given A, B, and C values.
     * @param a The "A" variable in the curve equation.
     * @param b The "B" variable in the curve equation.
     * @param c The "C" variable in the curve equation.
     */
    public QuadraticCurve(Double a, Double b, Double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public Double curve(Double x) {
        // y = Ax^2 + Bx + C
        return (a * Math.pow(x, 2)) + (b * x) + c;
    }

    /**
     * Returns the "A" variable in the curve equation.
     * @return The "A" variable in the curve equation.
     */
    public Double getA() {
        return a;
    }

    /**
     * Sets the "A" variable in the curve equation.
     * @param a The new "A" variable in the curve equation.
     */
    public void setA(Double a) {
        this.a = a;
    }

    /**
     * Returns the "B" variable in the curve equation.
     * @return The "B" variable in the curve equation.
     */
    public Double getB() {
        return b;
    }

    /**
     * Sets the "B" variable in the curve equation.
     * @param b The new "B" variable in the curve equation.
     */
    public void setB(Double b) {
        this.b = b;
    }

    /**
     * Returns the "C" variable in the curve equation.
     * @return The "C" variable in the curve equation.
     */
    public Double getC() {
        return c;
    }

    /**
     * Sets the "C" variable in the curve equation.
     * @param c The new "C" variable in the curve equation.
     */
    public void setC(Double c) {
        this.c = c;
    }
}