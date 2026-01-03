package waypoint;

import java.util.List;
import java.util.ArrayList;

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
	
	private List<Node> nodes;
	private int size;
	
	public GraphMap() {
		this.nodes = new ArrayList<>();
		this.size = 0;
	}
	
	public int getSize() {return this.size;}
	
	public List<Node> getNodes() {return this.nodes;}
	
	public void addEdgeDirected(Node target, Node destination, double distance) {
		
	}
	
	public void addEdgeUndirected(Node target, Node destination, double weight) {
		
	}
	
	public Node addNode(T data) {
		return null;
	}
	
	public Node removeNode(T data) {
		return null;
	}
	
	public Node findNode(T data) {
		return null;
	}
	
	public List<Edge> adjacentNodes(T data) {
		return null;
	}
}
