package hexmap;

import java.util.*;

public class HexMap {

	public static void main(String[] args) {
		int hexRadius = 2;
		Graph hexMap = new Graph(hexRadius);

		hexMap.printGraph();

		BFS path = new BFS();

		Queue<Node> solution = path.bfs(Graph.nodeList.get(1));

		String txt = "";

		for(int i = 0; i < solution.size(); i++) {

			txt += solution.poll().getHexId();
		}

		System.out.println(txt);
	}
}