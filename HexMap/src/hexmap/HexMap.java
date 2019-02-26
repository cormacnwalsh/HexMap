package hexmap;

public class HexMap {

	public static void main(String[] args) {
		int hexRadius = 2;
		Graph hexMap = new Graph(hexRadius);
		
		hexMap.printGraph();
	}
}