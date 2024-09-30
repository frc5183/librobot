package net.frc5183.librobot.math.curve;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A {@link Curve} which represents a curve that is defined by multiple curves.
 * The curve is determined by the first curve that returns true for its condition.
 * If no conditions are met, the curve returns 0.
 */
public class PiecewiseCurve extends Curve {
    /**
     * A map of conditions to curves.
     */
    private final Map<Function<Double, Boolean>, Curve> curves;

    /**
     * Creates a new {@link PiecewiseCurve} with no curves.
     */
    public PiecewiseCurve() {
        this.curves = Map.of();
    }

    /**
     * Creates a new {@link PiecewiseCurve} with the given condition and curve.
     * @param condition The condition for the curve.
     * @param curve The curve to use when the condition is true.
     */
    public PiecewiseCurve(Function<Double, Boolean> condition, Curve curve) {
        this.curves = Map.of(condition, curve);
    }

    /**
     * Creates a new {@link PiecewiseCurve} with the given conditions and curves.
     * @param curves The conditions and curves to use.
     */
    @SafeVarargs
    public PiecewiseCurve(Map.Entry<Function<Double, Boolean>, Curve>... curves) {
        this.curves = Map.ofEntries(curves);
    }

    /**
     * Creates a new {@link PiecewiseCurve} with the given conditions and curves.
     * @param curves The conditions and curves to use.
     */
    public PiecewiseCurve(Map<Function<Double, Boolean>, Curve> curves) {
        this.curves = curves;
    }

    @Override
    public Double curve(Double x) {
        for (Map.Entry<Function<Double, Boolean>, Curve> entry : curves.entrySet()) {
            if (entry.getKey().apply(x)) {
                return entry.getValue().curve(x);
            }
        }
        return 0d;
    }

    /**
     * Iterates over all the curves in this piecewise curve.
     * @param consumer The consumer to apply to each curve.
     */
    public void forEach(Consumer<Curve> consumer) {
        curves.values().forEach(consumer);
    }

    /**
     * Iterates over all the conditions in this piecewise curve.
     * @param consumer The consumer to apply to each condition.
     */
    public void forEachKey(Consumer<Function<Double, Boolean>> consumer) {
        curves.keySet().forEach(consumer);
    }

    /**
     * Iterates over all the conditions and curves in this piecewise curve.
     * @param consumer The consumer to apply to each condition and curve.
     */
    public void forEachEntry(BiConsumer<Function<Double, Boolean>, Curve> consumer) {
        curves.forEach(consumer);
    }

    /**
     * Replaces the curve with the given condition with the given curve.
     * @param condition The condition to target when replacing.
     * @param curve The curve to replace the condition with.
     */
    public void replace(Function<Double, Boolean> condition, Curve curve) {
        curves.replace(condition, curve);
    }

    /**
     * Adds a new condition and curve to this piecewise curve.
     * @param condition The condition for the curve.
     * @param curve The curve to use when the condition is true.
     */
    public void put(Function<Double, Boolean> condition, Curve curve) {
        curves.put(condition, curve);
    }

    /**
     * Gets the curve with the given condition.
     * @param condition The condition to get the curve for.
     * @return The curve with the given condition.
     */
    public Curve get(Function<Double, Boolean> condition) {
        return curves.get(condition);
    }

    /**
     * Removes the curve with the given condition.
     * @param condition The condition to remove the curve for.
     */
    public void remove(Function<Double, Boolean> condition) {
        curves.remove(condition);
    }

    /**
     * Removes all curves from this piecewise curve.
     */
    public void clear() {
        curves.clear();
    }
}