package hexmap;

import java.util.*;

public class HexMap {

	public static void main(String[] args) {
		// int hexRadius = 3;

		Scanner input = new Scanner(System.in);

		System.out.println("Enter number of hex grid rings: ");
		int hexRadius = input.nextInt();

		Graph hexMap = new Graph(hexRadius);
		hexMap.printGraph();

		boolean quit = false;

		while (!quit) {
			System.out.println("Enter starting hex: ");
			int start = input.nextInt();
			input.nextLine();

			System.out.println("Enter goal hex: ");
			int end = input.nextInt();
			input.nextLine();

			hexMap.setPath(start, end);

			List<Integer> path = hexMap.getPath(end);

			System.out.println("Path: " + path + "\n");
			
			System.out.println("Another path? (y to continue; any other input quits)");
			
			String choice = input.next();
			
			if(choice.equals("y")) {
				input.nextLine();
				continue;
			}else {
				quit = true;
			}
		}
		
		System.out.println("Goodbye");

	}
}