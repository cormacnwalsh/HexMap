# HexMap
Creates a Hexagonal grid as an undirected weighted Graph and performs a Uniform Cost Search for pathfinding

This Java application procedurally generates a hexagonal grid as an undirected weighted graph.
The process is as follows:

1 - The first step asks for a radius to be input.
    The radius is the number of rings in addition to the center hex.
    (index 0 - the CENTER hex - always exists; 1 - 6 are on ring 1; 7 - 18 are on ring 2; etc)
    
2 - The number of hexes (nodes) needed are calculated by multiplying the ring number by 6 and looping through the ring
    adding each hex (node) index to a list in sequence. The id of each node is the same as the list index. Each node has its
    type determined at this point. They can be CENTER, CORNER or EDGE nodes. This is important for the adjacency algorithm.
    Each node has a weight that is determined by which ring it is on; with the highest weight in the CENTER and decreasing
    with each ring.
    
3 - Edges are then added to all nodes following a simple arithmetic formula: The CENTER connects to nodes 1 - 6 (and each target node is       connected back to the origin node). The CORNER nodes use an offset number to find which nodes they share an
    edge with (Starts at 6 and increment every time a CORNER hex is found). The EDGE nodes use the offset number but don't increment it.
    
4 - The user is asked for a start node and an end node.

5 - A Uniform Cost Search is performed to find the  path between the start and end node.

6 - The path is returned and printed to the console.
