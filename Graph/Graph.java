package test.Dijkstra;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import test.Dijkstra.*;

/**
 * 
 * @author jingma1
 * By means of Adjacency List
 * 
 */



public class Graph {
	
	//use HashSet for getting unique Edges of one node
	private  HashMap<Node,HashSet<Edge>> nodeEdges;
	private HashSet<Edge> edges;
	
	/**
	 * 
	 * @param nodeIDs
	 * @param nodePairs = node1#node2#length
	 */
	public Graph(String[] nodeIDs, String[] nodePairs) {
		
		if ( nodeIDs == null || nodeIDs.length == 0  || nodePairs == null  || nodePairs.length == 0) {
			System.err.println(" please verify the nodes and nodePairs for edges");			
		} else {			
			this.nodeEdges = new HashMap<Node,HashSet<Edge>>();		
			this.edges = new HashSet<Edge>();	
			HashMap<String, Node> nodeMap = new HashMap<String, Node>();	
			
			// add all nodes and create a node map
			for (String nodeID: nodeIDs) {
				Node node = new Node(nodeID);
				nodeMap.put(nodeID, node);
				nodeEdges.put(node, new HashSet<Edge>());				
			} 			
			
			// add all edges to the map's set
			for (String curNodes: nodePairs ) {				
				if (curNodes != null && curNodes != "") {					
					String[] splitedNodePair = curNodes.split("#");											
					//keep one edge to be unique
					if (splitedNodePair != null && splitedNodePair.length == 3) {
						Integer node1 = Integer.valueOf(splitedNodePair[0]);
						Integer node2 = Integer.valueOf(splitedNodePair[1]);
						System.out.println(node1 + node2);
						if ( node1 > node2 ) {
							String tmpStr =splitedNodePair[0];
							splitedNodePair[0] = splitedNodePair[1];
							splitedNodePair[1] = tmpStr;						
						}					
					}
					
					// add this new edge to nodes' hashset
					Node node1 = nodeMap.get(splitedNodePair[0]);
					Node node2 = nodeMap.get(splitedNodePair[1]);
					Edge curEdge = new Edge( node1, node2,Integer.valueOf(splitedNodePair[2]) );
					nodeEdges.get(node1).add(curEdge);
					nodeEdges.get(node2).add(curEdge);
					
					this.edges.add(curEdge);
				}				
			}			
		}		
	}
	
	public Node[] getNodeArray() {
		Node[] nodesOfGrapgh = new Node[this.nodeEdges.keySet().size()];
		return this.nodeEdges.keySet().toArray(nodesOfGrapgh);		
	}

	public Edge[] getEdgeArray(){
		Edge[] edges = new Edge[this.edges.size()];
		return this.edges.toArray(edges);
	}
	
	public HashMap<Node, HashSet<Edge>> getGraphHashMap() {
		return this.nodeEdges;		
	}
	
	public void DFS(Node startNode) {
		
		HashSet<Node> visitedNodes = new HashSet<Node>();
		LinkedList<Node> nodesStack = new LinkedList<Node>();		
			
		nodesStack.push(startNode);
		
		while (nodesStack != null && !nodesStack.isEmpty() ) {
			Node curNode = nodesStack.poll();
			
			// skip those nodes which are added by processing other cur nodes
			if ( !visitedNodes.contains(curNode) ) {	
				
				// get current node			
				System.out.println( "curNode:" + curNode.getID() );			
				// get all nodes connect to this node and push them to stack;			
				pushAllCntNodeToStack(nodesStack, visitedNodes,curNode);
				visitedNodes.add(curNode);
				
			}			
		}		
	}
	
	private void pushAllCntNodeToStack(LinkedList<Node> nodesStackDFS,HashSet<Node> nodeVisitedSet, Node curNode) {
		
		HashSet<Edge> edgs = this.nodeEdges.get(curNode);
		if (edgs != null && edgs.size() >0 ){
			for (Edge curEdge: edgs) {
				Node[] nodesOfEdge = curEdge.getNodes();					
				if ( nodesOfEdge != null && nodesOfEdge.length == 2  ){
					if (nodesOfEdge[0] != curNode && !nodeVisitedSet.contains(nodesOfEdge[0]) ) {
						nodesStackDFS.push( nodesOfEdge[0] );
					}
					if (nodesOfEdge[1] != curNode && !nodeVisitedSet.contains(nodesOfEdge[1]) ) {
						nodesStackDFS.push( nodesOfEdge[1] );
					}					
				}
			}	
		}
	}
	
	public void BFS(Node startNode) {
		//process one node, there are three steps: get one curNode, put curNode's connected nodes into Queue, repeat last step and 
		//process this queue until it get empty
		
		if( startNode != null && this.nodeEdges != null && this.nodeEdges.get(startNode) != null 
				&& !this.nodeEdges.get(startNode).isEmpty()) {
			
			HashSet<Node> nodeVisitedSet = new HashSet<Node>(); 
			LinkedList<Node> nodeToVisitQueue = new LinkedList<Node>();			
			nodeToVisitQueue.addLast(startNode);			
			while (nodeToVisitQueue !=null && !nodeToVisitQueue.isEmpty()) {
				Node curNode = nodeToVisitQueue.pop();				
				// process this node add connected nodes to queue;
				System.out.println( "curNode：" + curNode.getID() );
				putNewNodesToQueue(curNode, nodeVisitedSet, nodeToVisitQueue);
				nodeToVisitQueue.add(curNode);				
			}				
		}		
	}
	
	private void putNewNodesToQueue(Node curNode, HashSet<Node> visitedNodeSet, LinkedList<Node> toVisitQueue) {
		
		HashSet<Edge> curNodeEdges = this.nodeEdges.get(curNode);
		if ( curNode!=null && visitedNodeSet != null && toVisitQueue != null && !curNodeEdges.isEmpty() ) {
			for (Edge curEdge: curNodeEdges) {
				Node[] cEdgeNodes = curEdge.getNodes();
				for (Node node: cEdgeNodes) {
					if ( node != null && node != curNode && !visitedNodeSet.contains(node) 
							&& !toVisitQueue.contains(node) ) {
						toVisitQueue.add(node);
					}
				}				
			}			
		}				
	}
	
	public void topoSort(){
		// delay this to lc/ construct one topoGraph waste too much time.		
	}
	
	public HashMap<Node, Node> Dijkstra(Node startNode, Graph graph) {	
		
		HashMap<Node, Node> rsMap = new HashMap<Node, Node>();
		HashSet<Node> sPathNodes = new HashSet<Node>();	
		
		// get all nodes set
		HashMap<Node,HashSet<Edge>> originalHashMap = graph.nodeEdges;
		HashSet<Node> originalSet = (HashSet<Node>) originalHashMap.keySet();		
		HashSet<Node> sourceNodesSet = new HashSet<Node>(originalSet);	
		
		//set start node's shortestPathLength = 0;
		startNode.setShortestPath(0);
		
		while ( !sourceNodesSet.isEmpty() ) {
			int shortestPath = Integer.MAX_VALUE;
			Node nearestNode = startNode;
			
			// find the nearestNode to startNode
			for(Node sNode: sourceNodesSet) {
				if(shortestPath > sNode.getShortestPath() ) {
					shortestPath = sNode.getShortestPath();
					nearestNode = sNode;					
				}			
			}
			//wrong: rsMap.put(nearestNode, theOtherNode); 
			//one node only have one pre node, but one pre node has a lot of next node maybe
			rsMap.put(nearestNode, nearestNode.getPreNode());
			
			//update path(ShortestPath of this node) value(nodes connect to nearestNode)
			for (Edge edge: originalHashMap.get(nearestNode)) {			
				Node theOtherNode = edge.theOtherNode(nearestNode);
				int edgeLength = edge.getLength();
				if( nearestNode.getShortestPath() + edgeLength < theOtherNode.getShortestPath() ) {
					theOtherNode.setShortestPath( nearestNode.getShortestPath() + edgeLength );
					theOtherNode.setPreNode(nearestNode);					
				}			
			}
			
			// update two node sets
			sPathNodes.add(nearestNode);
			sourceNodesSet.remove(nearestNode);
		}
				
		return rsMap;
		
	}
	
	public static void main(String[] args) {
		
		//construct a new graph
		//TODO: ? note can't put {} direct as parameter, due to ambiguous?
		String[] nodeIDs = {"0","1","2","3","4","5"}; 
		String[] nodePairs =  {"0#1#1", "0#2#2", "0#5#3", "1#2#2","2#3#1","3#4#1","4#5#1"};
		Graph graph = new Graph(nodeIDs, nodePairs);
		
		String x = "1211";
		char[] chars = x.toCharArray();
		char[][] xx;
	    LinkedList<String> xxx = new LinkedList<String>();
	    
	    
	}
	
}
