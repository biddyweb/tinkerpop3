package com.tinkerpop.gremlin.process.graph.traversal.map

import com.tinkerpop.gremlin.process.Traversal
import com.tinkerpop.gremlin.process.ComputerTestHelper
import com.tinkerpop.gremlin.process.graph.traversal.step.map.SelectTest
import com.tinkerpop.gremlin.structure.Order
import com.tinkerpop.gremlin.structure.Vertex

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public abstract class GroovySelectTest {

    public static class StandardTest extends SelectTest {

        @Override
        public Traversal<Vertex, Map<String, Vertex>> get_g_VX1X_asXaX_outXknowsX_asXbX_select(final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select()
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_VX1X_asXaX_outXknowsX_asXbX_select_byXnameX(
                final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select.by('name')
        }

        @Override
        public Traversal<Vertex, Vertex> get_g_VX1X_asXaX_outXknowsX_asXbX_selectXaX(final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select('a')
        }

        @Override
        public Traversal<Vertex, String> get_g_VX1X_asXaX_outXknowsX_asXbX_selectXaX_byXnameX(
                final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select('a').by('name')
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_out_asXbX_select_byXnameX() {
            g.V.as('a').out.as('b').select.by('name')
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_out_aggregate_asXbX_select_byXnameX() {
            g.V.as('a').out.aggregate.as('b').select.by('name')
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_name_order_asXbX_select_byXnameX_by() {
            g.V().as('a').name.order().as('b').select.by('name').by
        }

        @Override
        public Traversal<Vertex, Map<String, Object>> get_g_V_hasXname_gremlinX_inEXusesX_order_byXskill_incrX_asXaX_outV_asXbX_select_byXskillX_byXnameX() {
            g.V.has('name', 'gremlin').inE('uses').order.by('skill', Order.incr).as('a').outV.as('b').select.by('skill').by('name')
        }
    }

    public static class ComputerTest extends SelectTest {

        @Override
        public Traversal<Vertex, Map<String, Vertex>> get_g_VX1X_asXaX_outXknowsX_asXbX_select(final Object v1Id) {
            ComputerTestHelper.compute("g.V(${v1Id}).as('a').out('knows').as('b').select()", g);
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_VX1X_asXaX_outXknowsX_asXbX_select_byXnameX(
                final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select.by('name')
            // TODO
        }

        @Override
        public Traversal<Vertex, Vertex> get_g_VX1X_asXaX_outXknowsX_asXbX_selectXaX(final Object v1Id) {
            ComputerTestHelper.compute("g.V(${v1Id}).as('a').out('knows').as('b').select('a')", g);
        }

        @Override
        public Traversal<Vertex, String> get_g_VX1X_asXaX_outXknowsX_asXbX_selectXaX_byXnameX(
                final Object v1Id) {
            g.V(v1Id).as('a').out('knows').as('b').select('a').by('name')
            //TODO
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_out_asXbX_select_byXnameX() {
            g.V.as('a').out.as('b').select.by('name') // TODO computer
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_out_aggregate_asXbX_select_byXnameX() {
            g.V.as('a').out.aggregate.as('b').select.by('name') // TODO computer
        }

        @Override
        public Traversal<Vertex, Map<String, String>> get_g_V_asXaX_name_order_asXbX_select_byXnameX_by() {
            g.V().as('a').name.order().as('b').select.by('name').by // TODO: computer
        }

        @Override
        public Traversal<Vertex, Map<String, Object>> get_g_V_hasXname_gremlinX_inEXusesX_order_byXskill_incrX_asXaX_outV_asXbX_select_byXskillX_byXnameX() {
            g.V.has('name', 'gremlin').inE('uses').order.by('skill', Order.incr).as('a').outV.as('b').select.by('skill').by('name')
            // TODO: computer
        }
    }
}
