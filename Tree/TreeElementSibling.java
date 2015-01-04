package test.tree;

public class TreeElementSibling extends TreeElement{
	
	TreeElementSibling nextSibling;
	TreeElementSibling preSibling;
	TreeElementSibling parentNode;
	TreeElementSibling leftChild;
	TreeElementSibling rightChild;
		
	public TreeElementSibling(Integer value) {
		super(value);				
	}	
	public TreeElementSibling(TreeElementSibling thisParentNode, Integer value) {
		super(value);
		//TODO: all extended constructor should call super first
		this.parentNode = thisParentNode;		
	}	
	public TreeElementSibling(TreeElementSibling thisParentNode, Integer value,
			TreeElementSibling nextSibling,TreeElementSibling preSibling){
		super(value);		
		this.nextSibling = nextSibling;
		this.preSibling = preSibling;	
		this.parentNode = thisParentNode;
	}	
	public TreeElementSibling getNextSibling() {
		return this.nextSibling;
	}
	public TreeElementSibling getPreSibling() {
		return this.preSibling;
	}
	public void setNextSibling(TreeElementSibling nextSibling) {
		this.nextSibling = nextSibling;		
	}
	public void setPreSibling(TreeElementSibling preSibling) {
		this.preSibling = preSibling;		
	}	
	
}
