package com.tinkerpop.gremlin.process.graph.traversal.step.filter;

import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.traversal.step.TraversalParent;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class OrStep<S> extends ConjunctionStep<S> implements TraversalParent {

    public OrStep(final Traversal.Admin traversal, final Traversal.Admin<S, ?>... orTraversals) {
        super(traversal, orTraversals);

    }
}
