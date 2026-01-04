package waypoint;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GraphMap {
	class Node {
		long id;
		String name;
		List<Edge> edges;
		Node(long id, String name) {
			this.name = name;
			this.id = id;
			this.edges = new ArrayList<>();
		}
	}
	
	class Edge {
		Node target;
		double distance; // Miles
		Edge(Node target, double distance) {
			this.target = target;
			this.distance = distance;
		}
	}
	
	// Stores all locations
	private Map<Long, Node> nodes;
	
	// Constructor
	public GraphMap() {
		this.nodes = new HashMap<>();
	}
	
	// Returns the number of locations
	public int getSize() {return this.nodes.size();}
	
	// Returns the container for all edges for a specific node
	public List<Edge> getEdges(long id) {return (this.nodes.get(id) != null) ? this.nodes.get(id).edges : null;}
	
	// Returns the container for all nodes
	public Map<Long, Node> getNodes() {return this.nodes;}
	
	// Add edge using Nodes
	public void addEdgeDirected(Node source, Node destination, double distance) {
		source.edges.add(new Edge(destination, distance));
	}
	
	// Add edge using Nodes
	public void addEdgeUndirected(Node source, Node destination, double distance) {
		source.edges.add(new Edge(destination, distance));
		destination.edges.add(new Edge(source, distance));
	}
	
	// Add edge using ID
	public void addEdgeDirected(long source_id, long destination_id, double distance) {
		Node source = nodes.get(source_id);
		Node destination = nodes.get(destination_id);
		if (source != null && destination != null) {
			source.edges.add(new Edge(destination, distance));
		}
	}
	
	// Add edge using ID
	public void addEdgeUndirected(long source_id, long destination_id, double distance) {
		Node source = nodes.get(source_id);
		Node destination = nodes.get(destination_id);
		if (source != null && destination != null) {
			source.edges.add(new Edge(destination, distance));
			destination.edges.add(new Edge(source, distance));
		}
	}
	
	// Add a node
	public void addNode(long id, String name) {nodes.putIfAbsent(id, new Node(id, name));}
	
	// Remove a node
	public Node removeNode(long id) {
		Node nodeToRemove = nodes.get(id);
		if (nodeToRemove == null) return null;
		for (Map.Entry<Long, Node> node : nodes.entrySet()) {
			Node temp = node.getValue();
			if (temp == nodeToRemove) continue;
			temp.edges.removeIf(edge -> edge.target == nodeToRemove);
		}
		nodeToRemove.edges.clear();
		nodes.remove(id);
		return nodeToRemove;
	}
}
