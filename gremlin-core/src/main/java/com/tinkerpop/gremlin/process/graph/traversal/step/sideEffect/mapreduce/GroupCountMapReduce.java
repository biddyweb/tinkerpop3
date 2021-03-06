package com.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.mapreduce;

import com.tinkerpop.gremlin.process.computer.KeyValue;
import com.tinkerpop.gremlin.process.computer.traversal.TraversalVertexProgram;
import com.tinkerpop.gremlin.process.computer.traversal.VertexTraversalSideEffects;
import com.tinkerpop.gremlin.process.computer.util.StaticMapReduce;
import com.tinkerpop.gremlin.process.graph.traversal.step.sideEffect.GroupCountStep;
import com.tinkerpop.gremlin.structure.Vertex;
import org.apache.commons.configuration.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public final class GroupCountMapReduce extends StaticMapReduce<Object, Long, Object, Long, Map<Object, Long>> {

    public static final String GROUP_COUNT_STEP_SIDE_EFFECT_KEY = "gremlin.groupCountStep.sideEffectKey";

    private String sideEffectKey;
    private Supplier<Map<Object, Long>> mapSupplier;

    private GroupCountMapReduce() {

    }

    public GroupCountMapReduce(final GroupCountStep step) {
        this.sideEffectKey = step.getSideEffectKey();
        this.mapSupplier = step.getTraversal().asAdmin().getSideEffects().<Map<Object, Long>>getRegisteredSupplier(this.sideEffectKey).orElse(HashMap::new);
    }

    @Override
    public void storeState(final Configuration configuration) {
        super.storeState(configuration);
        configuration.setProperty(GROUP_COUNT_STEP_SIDE_EFFECT_KEY, this.sideEffectKey);
    }

    @Override
    public void loadState(final Configuration configuration) {
        this.sideEffectKey = configuration.getString(GROUP_COUNT_STEP_SIDE_EFFECT_KEY);
        this.mapSupplier = TraversalVertexProgram.getTraversalSupplier(configuration).get().getSideEffects().<Map<Object, Long>>getRegisteredSupplier(this.sideEffectKey).orElse(HashMap::new);
    }

    @Override
    public boolean doStage(final Stage stage) {
        return true;
    }

    @Override
    public void map(final Vertex vertex, final MapEmitter<Object, Long> emitter) {
        VertexTraversalSideEffects.of(vertex).<Map<Object, Number>>orElse(this.sideEffectKey, Collections.emptyMap()).forEach((k, v) -> emitter.emit(k, v.longValue()));
    }

    @Override
    public void reduce(final Object key, final Iterator<Long> values, final ReduceEmitter<Object, Long> emitter) {
        long counter = 0;
        while (values.hasNext()) {
            counter = counter + values.next();
        }
        emitter.emit(key, counter);
    }

    @Override
    public void combine(final Object key, final Iterator<Long> values, final ReduceEmitter<Object, Long> emitter) {
        reduce(key, values, emitter);
    }

    @Override
    public Map<Object, Long> generateFinalResult(final Iterator<KeyValue<Object, Long>> keyValues) {
        final Map<Object, Long> map = this.mapSupplier.get();
        keyValues.forEachRemaining(keyValue -> map.put(keyValue.getKey(), keyValue.getValue()));
        return map;
    }

    @Override
    public String getMemoryKey() {
        return this.sideEffectKey;
    }
}
