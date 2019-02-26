package hexmap;

import java.util.Collections;
import java.util.LinkedList;

public class Node {
	private int hexType; // center = 0; corner = 1; edge = 2;
	private int hexId;
	private int hexRing;
	private int hexCost;

	// The list of adjacent Nodes
	private LinkedList<Integer> adjList;

	// Constructor
	public Node(int type, int id, int ring, int cost) {
		this.hexType = type;
		this.hexId = id;
		this.hexRing = ring;
		this.hexCost = cost;

		adjList = new LinkedList<>();
	}

	public int getHexType() {
		return hexType;
	}

	public int getHexId() {
		return hexId;
	}

	public int getHexRing() {
		return hexRing;
	}

	public int getHexCost() {
		return hexCost;
	}

	public LinkedList<Integer> getAdjList() {
		return adjList;
	}

	// add edge to undirected graph
	public void addEdge(int dest, int maxSize) {
		// src -> dest
		if (adjList.size() < 6 && dest < maxSize && !adjList.contains(dest)) {
			this.adjList.add(dest);
			Collections.sort(adjList);
		}
	}
}
