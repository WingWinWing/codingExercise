package test.Dijkstra;
import test.Dijkstra.Node;

public class Edge {

	private int length;
	private Node NodeOne;
	private Node NodeTwo;
	
	
	
	public Edge(Node nodeOne, Node nodeTwo, int length) {
		this.NodeOne = nodeOne;
		this.NodeTwo = nodeTwo;
		this.length = length;		
	}
	
	public Node[] getNodes() {
		if (this.NodeOne !=null && this.NodeTwo != null ) {
			Node[] nodes = {this.NodeOne, this.NodeTwo};
			return nodes;
		}
		return null;		
	}
	
	public Node theOtherNode(Node oneNode) {
		
		if(this.NodeOne != null && this.NodeOne == oneNode ) {
			return this.NodeTwo;			
		} else if (this.NodeTwo != null && this.NodeTwo == oneNode ) {
			return this.NodeOne;			
		}
		
		System.err.println(" Invalid Nodes! ");
		return null;
	}
	
	public int getLength() {
		 return this.length;
	}
	
	public void setLength(int length){
		this.length = length;
	}
	
	@Override
	public int hashCode() {
		//using Edges's hashset
		return (NodeOne.getID() + NodeTwo.getID()).hashCode();		
	}
	
}
