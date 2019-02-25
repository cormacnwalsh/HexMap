package hexmap;

public class HexMap {

	public static void main(String[] args) {
		int hexRadius = 3;
		Graph hexMap = new Graph(hexRadius);
		
		hexMap.printGraph();
	}
}