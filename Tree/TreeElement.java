package test.tree;

public class TreeElement {
	
	protected TreeElement parentNode;
	protected TreeElement leftChild;
	protected TreeElement rightChild;
    
	protected Integer value;
	
	public TreeElement(Integer value) {
		parentNode = null;
		leftChild = null;
		rightChild = null;
		this.value = value;
	}
	
	public TreeElement(TreeElement thisParentNode, Integer value) {
		parentNode = thisParentNode;
		leftChild = null;
		rightChild = null;		
		this.value = value;
	}
	
	public String toString(){
		return "" + value;
	}
	
	public Integer getVaule() {
		return this.value;		
	}
	
	public void setVaule(Integer nodeValue) {
		 this.value = nodeValue;		
	}
	
	public void setParent(TreeElement parent) {		
		this.parentNode = parent;
	}
	
	public void setLeftChild(TreeElement lChild) {		
		this.leftChild = lChild;
	}
	
	public void setRightChild(TreeElement rChild) {		
		this.rightChild = rChild;
	}
	
	public TreeElement getParent() {		
		return this.parentNode;
	}
	
	public TreeElement getLeftChild() {		
		return this.leftChild ;
	}
	
	
	public TreeElement getRightChild() {		
		return this.rightChild ;
	}
	
	public boolean isRoot() {
		if (this.parentNode == null ){
			return true;			
		} else {
			return false;
		}
	}
	

}
