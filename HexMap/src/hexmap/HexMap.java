package hexmap;

import java.util.*;

public class HexMap {

	public static void main(String[] args) {
		int hexRadius = 3;
		Graph hexMap = new Graph(hexRadius);

		hexMap.printGraph();
		
		hexMap.ucs(1, 12);
		
		List<Integer> path = hexMap.getPath(12);
		
		System.out.println("Path: " + path);
	}
}