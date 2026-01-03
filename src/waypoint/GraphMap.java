package waypoint;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class GraphMap<T> {
	class Node {
		T data;
		List<Edge> edges;
		Node(T data) {
			this.data = data;
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
	
	private Map<T, Node> nodes;
	
	public GraphMap() {
		this.nodes = new HashMap<>();
	}
	
	public int getSize() {return this.nodes.size();}
	
	public List<Edge> getEdges(T data) {return (this.nodes.get(data) != null) ? this.nodes.get(data).edges : null;}
	
	public Map<T, Node> getNodes() {return this.nodes;}
	
	public void addEdgeDirected(Node source, Node destination, double distance) {
		source.edges.add(new Edge(destination, distance));
	}
	
	public void addEdgeUndirected(Node source, Node destination, double distance) {
		source.edges.add(new Edge(destination, distance));
		destination.edges.add(new Edge(source, distance));
	}
	
	public void addNode(T data) {nodes.putIfAbsent(data, new Node(data));}
	
	public Node removeNode(T data) {
		Node nodeToRemove = new Node(data);
		for (Map.Entry<T, Node> node : nodes.entrySet()) {
			Node temp = node.getValue();
			if (node == nodeToRemove) continue;
			temp.edges.removeIf(edge -> edge.target == nodeToRemove);
		}
		
		nodeToRemove.edges.clear();
		nodes.remove(data);
		return nodeToRemove;
	}
}
