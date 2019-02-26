package hexmap;

import java.util.*;

public class Graph {
	private int radius;
	private int size;
	private ArrayList<Node> nodeList = new ArrayList<>();

	// Constructor
	public Graph(int r) {
		this.radius = r;

		// add nodes to nodeList
		addNode();

		// determines the edges between nodes
		size = this.nodeList.size();
		determineAdjacency(size);
	}

	public void addNode() {
		// values initialized for the center node
		int id = 0;
		int type = 0;
		int ring = 0;

		// The center node always exists
		Node root = new Node(type, id, ring, calcCost(ring));
		nodeList.add(root);

		// adds all other nodes to nodeList
		for (int i = 1; i <= this.radius; i++) { // The ring loop
			int ringSize = i * 6;
			for (int j = 1; j <= ringSize; j++) { // The node loop
				id++;
				ring = i; // sets ring
				type = determineType(id, ring); // sets type: CENTER, CORNER, EDGE
				Node n = new Node(type, id, ring, calcCost(ring));
				nodeList.add(n);
			}
		}
	}

	public void determineAdjacency(int size) {
		int ring, ringStart, ringEnd, type;
		int offset = 6;

		for (Node n : this.nodeList) {
			type = n.getHexType();
			ring = n.getHexRing();
			ringStart = getRingStart(ring);
			ringEnd = getRingEnd(ring);

			// Array of the offsets to use when joining hexes
			int[] target = new int[5];
			target[0] = n.getHexId() - 1;
			target[1] = n.getHexId() + 1;
			target[2] = n.getHexId() + offset;
			target[3] = n.getHexId() + offset + 1;
			target[4] = n.getHexId() + offset + 2;

			// 0 is CENTER type
			// Joins to 1-6
			if (type == 0 && size > 1) {
				for (int i = 1; i <= 6; i++) {
					joinNode(n, i, size);
				}

				// 1 is CORNER type
				// Increments the offset
			} else if (type == 1) {
				if (n.getHexId() > ringStart && n.getHexId() < ringEnd) {
					for (int i = 0; i < target.length; i++) {
						if (target[i] < size) {
							joinNode(n, target[i], size);
						}
					}
					offset++;

					// joins first and last node of ring
				} else if (n.getHexId() == ringStart) {
					joinNode(n, ringEnd, size);
					for (int i = 1; i < target.length; i++) {
						if (target[i] < size) {
							joinNode(n, target[i], size);
						}
					}
					offset++;

					// joins end of ring to next ring
				} else if (n.getHexId() == ringEnd) {
					for (int i = 0; i < target.length - 1; i++) {
						if (target[i] < size) {
							joinNode(n, target[i], size);
						}
					}
					offset++;
				}

				// 2 is EDGE type
				// Does not increment offset
			} else if (type == 2) {
				if (n.getHexId() > ringStart && n.getHexId() < ringEnd) {
					for (int i = 0; i < target.length - 1; i++) {
						if (target[i] < size) {
							joinNode(n, target[i], size);
						}
					}

					// joins first of ring to end of ring
				} else if (n.getHexId() == ringStart) {
					joinNode(n, ringEnd, size);

					for (int i = 0; i < target.length - 1; i++) {
						if (target[i] < size) {
							joinNode(n, target[i], size);
						}
					}
				}
			}
		}
	}

	public void joinNode(Node src, int target, int size) {
		src.addEdge(target, size);
		Node dest = this.nodeList.get(target);
		dest.addEdge(src.getHexId(), size);
	}

	public int getRingStart(int ring) {
		// adapted centered hexagonal number formula: 3n(n-1)+1; n = ring
		return 3 * ring * (ring - 1) + 1;
	}

	public int getRingEnd(int ring) {
		// adapted centered hexagonal number formula: (3n(n-1)+1) + (6 * n) - 1; n =
		// ring
		return (3 * ring * (ring - 1) + 1) + (6 * ring) - 1;
	}

	public int calcCost(int ring) {
		// the cost is inverse to distance from the center node
		return (this.radius + 1) - ring;
	}

	public int determineType(int id, int ring) {
		if (id % ring == 0) {
			return 1; // corner type
		} else {
			return 2; // edge type
		} // CENTER type predefined for 0, so no need for it here
	}

	public ArrayList<Integer> getPath(int goal) {
		ArrayList<Integer> path = new ArrayList<Integer>();
		for (Node node = this.nodeList.get(goal); node != null; node = node.parent) {
			path.add(node.getHexId());
		}

		Collections.reverse(path);

		return path;
	}

	public void ucs(int src, int dest) {
		Node start = this.nodeList.get(src);
		start.pathCost = 0;
		boolean found = false;

		PriorityQueue<Node> frontier = new PriorityQueue<Node>(new Comparator<Node>() {
			// override compare method
			public int compare(Node a, Node b) {
				if (a.getHexCost() > b.getHexCost()) {
					return 1;
				} else if (a.getHexCost() < b.getHexCost()) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		frontier.add(start);
		HashSet<Node> explored = new HashSet<>();

		do {
			Node n = frontier.poll();
			explored.add(n);

			for (int i : n.getAdjList()) {
				Node child = this.nodeList.get(i);
				int cost = child.getHexCost() + 1;

				// add node to frontier if not explored
				if (!explored.contains(child) && !frontier.contains(child)) {
					child.pathCost = n.pathCost + cost;
					child.parent = n;
					frontier.add(child);

				// if current path is lower in cost than previous path
				} else if (frontier.contains(child) && (child.pathCost > (n.pathCost + cost))) {
					child.parent = n;
					child.pathCost = n.pathCost + cost;
					frontier.remove(child);
				}
			}

		} while (!frontier.isEmpty());
	}

	public void printGraph() {
		for (Node n : nodeList) {
			int type = n.getHexType();
			String txt = "";

			switch (type) {
			case 0:
				txt = "CENTER";
				break;
			case 1:
				txt = "CORNER";
				break;
			case 2:
				txt = "EDGE";
				break;
			default:
				txt = "**Type cannot be found**";
			}

			System.out.println("Node " + n.getHexId() + ": \n" + "Type: " + txt + " | Ring: " + n.getHexRing()
					+ " | Cost: " + n.getHexCost());

			System.out.print("Adjacencies: ");

			for (Integer i : n.getAdjList()) {
				System.out.print(i + ", ");
			}
			System.out.println("\n");
		}
	}

	public class Node {
		private int hexType; // center = 0; corner = 1; edge = 2;
		private int hexId;
		private int hexRing;
		private int hexCost;
		
		private int pathCost;
		private Node parent;

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
}