package com.tinkerpop.gremlin.algorithm.generator;

import java.util.Random;

/**
 * Interface for a distribution over discrete values.
 * <p/>
 * Used, for instance, by {@link DistributionGenerator} to define the in- and out-degree distributions and by
 * {@link CommunityGenerator} to define the community size distribution.
 *
 * @author Matthias Broecheler (me@matthiasb.com)
 */
public interface Distribution {

    /**
     * Initializes the distribution such that expectedTotal is equal to the expected sum of generated values
     * after the given number of invocatiosn.
     * <p/>
     * Since most distributions have an element of randomness, these values are the expected values.
     *
     * @return A new distribution configured to match the expected total for the number of invocations.
     */
    Distribution initialize(final int invocations, final int expectedTotal);

    /**
     * Returns the next value. If this value is randomly generated, the randomness must be drawn from
     * the provided random generator.
     * <p/>
     * DO NOT use your own internal random generator as this makes the generated values non-reproducible and leads
     * to faulty behavior.
     *
     * @param random random generator to use for randomness
     * @return next value
     */
    int nextValue(final Random random);

    /**
     * Returns the next value conditional on another given value.
     * <p/>
     * This can be used, for instance, to define conditional degree distributions where the in-degree is conditional on the out-degree.
     * <p/>
     * If this value is randomly generated, the randomness must be drawn from the provided random generator.
     * DO NOT use your own internal random generator as this makes the generated values non-reproducible and leads
     * to faulty behavior.
     *
     * @param random     random generator to use for randomness
     * @param otherValue The prior value
     * @return next value
     */
    int nextConditionalValue(final Random random, final int otherValue);

}
