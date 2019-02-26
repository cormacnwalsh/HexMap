package hexmap;

import java.util.*;

public class HexMap {

	public static void main(String[] args) {
		int hexRadius = 3;
		Graph hexMap = new Graph(hexRadius);

		hexMap.printGraph();

		Scanner input = new Scanner(System.in);

		System.out.println("Enter starting hex: ");
		int start = input.nextInt();

		System.out.println("Enter goal hex: ");
		int end = input.nextInt();

		hexMap.setPath(start, end);

		List<Integer> path = hexMap.getPath(end);

		System.out.println("Path: " + path);
	}
}