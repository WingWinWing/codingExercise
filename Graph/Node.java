package Graph;

public class Node {
	
	private String ID;		
	private int shortestPathLength;
	private Node preNode;
	
	public Node(String ID) {
		this.ID = ID;
		this.shortestPathLength = Integer.MAX_VALUE;
	}	
	public void setID (String ID) {
		this.ID = ID;
	}
	public String getID() {
		return this.ID;
	}		
	@Override
	public int hashCode(){
		return ID.hashCode();
	}	
	public void setShortestPath(int shortestPath) {
		this.shortestPathLength = shortestPath;
	}
	public int getShortestPath(){
		return this.shortestPathLength;
	}	
	public void setPreNode( Node node) {
		this.preNode = node;
	}
	
	public Node getPreNode() {
		return this.preNode;		
	}
		
}
