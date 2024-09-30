package net.frc5183.librobot.math.curve;

/**
 * An abstract class which represents a mathematical function/curve.
 */
public abstract class Curve {
    /**
     * Returns the value of the curve at the given x value.
     * @param x The x value to evaluate the curve at.
     * @return The value of the curve at the given x value.
     */
    public abstract double curve(double x);
}