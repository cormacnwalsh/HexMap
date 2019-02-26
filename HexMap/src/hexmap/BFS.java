package hexmap;

import java.util.*;

public class BFS {
	private Queue<Node> queue;
	private HashSet<Node> visited;

	public BFS() {
		queue = new LinkedList<Node>();
		visited = new HashSet<Node>();
	}

	public Queue<Node> bfs(Node node){
		queue.add(node);
		visited.add(node);
		while (!queue.isEmpty()){
			Node element=queue.remove();
			System.out.print(element.getHexId() + "t");
			List<Integer> neighbours=element.getAdjList();
			for (int i = 0; i < neighbours.size(); i++) {
				Node n=Graph.nodeList.get(neighbours.get(i));
				if(n!=null && !visited.contains(n)){
					queue.add(n);
					visited.add(n);
				}
			}
		}

		return queue;
	}
}