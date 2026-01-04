package waypoint;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;

public class GraphMapTest {

    @Test
    public void testAddNodeAndGetSize() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        assertEquals("Graph should have 3 nodes", 3, graph.getSize());
    }
    
    @Test
    public void testAddSingleNode() {
        GraphMap graph = new GraphMap();
        graph.addNode(10L, "X");
        assertEquals("Graph should have 1 node", 1, graph.getSize());
    }

    @Test
    public void testAddMultipleNodes() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");
        graph.addNode(4L, "D");
        graph.addNode(5L, "E");
        assertEquals("Graph should have 5 nodes", 5, graph.getSize());
    }

    @Test
    public void testAddDuplicateNode() {
        GraphMap graph = new GraphMap();
        graph.addNode(100L, "Node100");
        graph.addNode(100L, "Node100-Duplicate"); // Should be ignored
        assertEquals("Graph should still have 1 node", 1, graph.getSize());
        assertEquals("Name should remain the original", "Node100", graph.getNodes().get(100L).name);
    }

    @Test
    public void testAddNodesIncrementally() {
        GraphMap graph = new GraphMap();
        assertEquals("Initially graph should be empty", 0, graph.getSize());

        graph.addNode(1L, "A");
        assertEquals("Graph should have 1 node", 1, graph.getSize());

        graph.addNode(2L, "B");
        assertEquals("Graph should have 2 nodes", 2, graph.getSize());

        graph.addNode(3L, "C");
        assertEquals("Graph should have 3 nodes", 3, graph.getSize());
    }

    @Test
    public void testAddEdgeUndirectedByNode() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");

        GraphMap.Node nodeA = graph.getNodes().get(1L);
        GraphMap.Node nodeB = graph.getNodes().get(2L);

        graph.addEdgeUndirected(nodeA, nodeB, 5.0);

        List<GraphMap.Edge> edgesA = graph.getEdges(1L);
        List<GraphMap.Edge> edgesB = graph.getEdges(2L);

        assertEquals("Node A should have 1 edge", 1, edgesA.size());
        assertEquals("Node B should have 1 edge", 1, edgesB.size());
        assertEquals("Node A's edge should point to Node B", nodeB, edgesA.get(0).target);
        assertEquals("Node B's edge should point to Node A", nodeA, edgesB.get(0).target);
        assertEquals("Distance should be 5.0 miles", 5.0, edgesA.get(0).distance, 0.001);
    }
    
    @Test
    public void testAddMultipleUndirectedEdges() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        GraphMap.Node nodeA = graph.getNodes().get(1L);
        GraphMap.Node nodeB = graph.getNodes().get(2L);
        GraphMap.Node nodeC = graph.getNodes().get(3L);

        graph.addEdgeUndirected(nodeA, nodeB, 5.0);
        graph.addEdgeUndirected(nodeA, nodeC, 7.5);

        assertEquals(2, graph.getEdges(1L).size());
        assertEquals(1, graph.getEdges(2L).size());
        assertEquals(1, graph.getEdges(3L).size());
    }

    @Test
    public void testUndirectedEdgeSelfLoop() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");

        GraphMap.Node nodeA = graph.getNodes().get(1L);

        graph.addEdgeUndirected(nodeA, nodeA, 3.0);

        List<GraphMap.Edge> edges = graph.getEdges(1L);
        assertEquals("Self-loop should create two edges (to itself)", 2, edges.size());

        assertEquals(nodeA, edges.get(0).target);
        assertEquals(nodeA, edges.get(1).target);
        assertEquals(3.0, edges.get(0).distance, 0.001);
        assertEquals(3.0, edges.get(1).distance, 0.001);
    }

    @Test
    public void testUndirectedEdgeDifferentDistances() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");

        GraphMap.Node nodeA = graph.getNodes().get(1L);
        GraphMap.Node nodeB = graph.getNodes().get(2L);

        graph.addEdgeUndirected(nodeA, nodeB, 5.0);
        graph.addEdgeUndirected(nodeA, nodeB, 10.0); // add a second edge with a different distance

        List<GraphMap.Edge> edgesA = graph.getEdges(1L);
        List<GraphMap.Edge> edgesB = graph.getEdges(2L);

        assertEquals("Node A should have 2 edges to Node B", 2, edgesA.size());
        assertEquals("Node B should have 2 edges to Node A", 2, edgesB.size());
        assertEquals(5.0, edgesA.get(0).distance, 0.001);
        assertEquals(10.0, edgesA.get(1).distance, 0.001);
    }

    @Test
    public void testIncrementalUndirectedEdges() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        GraphMap.Node nodeA = graph.getNodes().get(1L);
        GraphMap.Node nodeB = graph.getNodes().get(2L);
        GraphMap.Node nodeC = graph.getNodes().get(3L);

        graph.addEdgeUndirected(nodeA, nodeB, 2.0);
        assertEquals(1, graph.getEdges(1L).size());
        assertEquals(1, graph.getEdges(2L).size());

        graph.addEdgeUndirected(nodeB, nodeC, 3.5);
        assertEquals("Node B should now have 2 edges", 2, graph.getEdges(2L).size());
        assertEquals("Node C should have 1 edge", 1, graph.getEdges(3L).size());
    }

    @Test
    public void testAddEdgeUndirectedById() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");

        graph.addEdgeUndirected(1L, 2L, 3.5);

        assertEquals("Node A should have 1 edge", 1, graph.getEdges(1L).size());
        assertEquals("Node B should have 1 edge", 1, graph.getEdges(2L).size());
        assertEquals("Distance should be 3.5 miles", 3.5, graph.getEdges(1L).get(0).distance, 0.001);
    }
    
    @Test
    public void testAddMultipleUndirectedEdgesById() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        graph.addEdgeUndirected(1L, 2L, 5.0);
        graph.addEdgeUndirected(1L, 3L, 7.5);

        assertEquals("Node A should have 2 edges", 2, graph.getEdges(1L).size());
        assertEquals("Node B should have 1 edge", 1, graph.getEdges(2L).size());
        assertEquals("Node C should have 1 edge", 1, graph.getEdges(3L).size());
    }

    @Test
    public void testUndirectedEdgeSelfLoopById() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");

        graph.addEdgeUndirected(1L, 1L, 3.0);

        assertEquals("Node A should have 2 edges (self-loop)", 2, graph.getEdges(1L).size());
        assertEquals("First edge distance should be 3.0", 3.0, graph.getEdges(1L).get(0).distance, 0.001);
        assertEquals("Second edge distance should be 3.0", 3.0, graph.getEdges(1L).get(1).distance, 0.001);
    }

    @Test
    public void testUndirectedEdgeDifferentDistancesById() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");

        graph.addEdgeUndirected(1L, 2L, 5.0);
        graph.addEdgeUndirected(1L, 2L, 10.0); // add second edge with different distance

        assertEquals("Node A should have 2 edges to Node B", 2, graph.getEdges(1L).size());
        assertEquals("Node B should have 2 edges to Node A", 2, graph.getEdges(2L).size());
        assertEquals("First edge distance should be 5.0", 5.0, graph.getEdges(1L).get(0).distance, 0.001);
        assertEquals("Second edge distance should be 10.0", 10.0, graph.getEdges(1L).get(1).distance, 0.001);
    }

    @Test
    public void testIncrementalUndirectedEdgesById() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        graph.addEdgeUndirected(1L, 2L, 2.0);
        assertEquals("Node A should have 1 edge", 1, graph.getEdges(1L).size());
        assertEquals("Node B should have 1 edge", 1, graph.getEdges(2L).size());

        graph.addEdgeUndirected(2L, 3L, 3.5);
        assertEquals("Node B should have 2 edges", 2, graph.getEdges(2L).size());
        assertEquals("Node C should have 1 edge", 1, graph.getEdges(3L).size());
    }

    @Test
    public void testRemoveNode() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addEdgeUndirected(1L, 2L, 2.0);

        GraphMap.Node removed = graph.removeNode(1L);

        assertEquals("Removed node should be A", "A", removed.name);
        assertNull("Node 1 should no longer exist", graph.getNodes().get(1L));
        assertEquals("Node B should have 0 edges after removal", 0, graph.getEdges(2L).size());
    }
    
    @Test
    public void testRemoveSingleNode() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");

        GraphMap.Node removed = graph.removeNode(1L);

        assertEquals("Removed node should be A", "A", removed.name);
        assertNull("Node 1 should no longer exist", graph.getNodes().get(1L));
        assertEquals("Graph size should be 0", 0, graph.getSize());
    }

    @Test
    public void testRemoveNodeWithMultipleEdges() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");
        graph.addNode(2L, "B");
        graph.addNode(3L, "C");

        graph.addEdgeUndirected(1L, 2L, 1.0);
        graph.addEdgeUndirected(1L, 3L, 2.0);

        GraphMap.Node removed = graph.removeNode(1L);

        assertEquals("Removed node should be A", "A", removed.name);
        assertNull("Node 1 should no longer exist", graph.getNodes().get(1L));
        assertEquals("Node B should have 0 edges after removal", 0, graph.getEdges(2L).size());
        assertEquals("Node C should have 0 edges after removal", 0, graph.getEdges(3L).size());
    }

    @Test
    public void testRemoveNodeNonExistent() {
        GraphMap graph = new GraphMap();
        graph.addNode(1L, "A");

        GraphMap.Node removed = graph.removeNode(999L); // Node 999 does not exist

        assertNull("Removing non-existent node should return null", removed);
        assertEquals("Graph should still have 1 node", 1, graph.getSize());
    }

    @Test
    public void testRemoveHalfOfManyNodes() {
        GraphMap graph = new GraphMap();

        // Add 100 nodes
        for (long i = 1; i <= 100; i++) {
            graph.addNode(i, "Node" + i);
        }

        // Connect consecutive nodes
        for (long i = 1; i < 100; i++) {
            graph.addEdgeUndirected(i, i + 1, 1.0);
        }

        // Remove 50 nodes
        for (long i = 1; i <= 50; i++) {
            graph.removeNode(i);
        }

        assertEquals("Graph should have 50 nodes remaining", 50, graph.getSize());

        // Check first remaining node's edges (should only connect to next remaining node)
        assertEquals("Node 51 should have 1 edge", 1, graph.getEdges(51L).size());
        assertEquals("Node 52 should have 2 edges", 2, graph.getEdges(52L).size());
    }

}
