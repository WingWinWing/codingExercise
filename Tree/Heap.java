package test.tree;

import java.util.LinkedList;

/**
 * @author jingma1
 * What is heap:
 *  Heap = priority queue
 *  1. root has more/equal pri than children; 
 *  2. heap must be a complete-binary tree 
 *  3. all delete only should take the top root of the tree
 * How to ADD and DELETE：
 *  add: add as last node/exchange with father if need/re-do step2 until father is less than this node
 *  delete: delete from root, then replace root with the last node of the tree, then replace the node until it is in the right position
 */


public class Heap {
	
	//Assume this is a small root heap
	private TreeElementSibling root;
	private TreeElementSibling theLastNode;
	private long size;
	
	public TreeElementSibling getRoot() {
		return this.root;
	}	
	public long size() {
		return this.size;
	}
	
	public Heap(Integer[] sortedArray,boolean circleLink) {		
		
		//the input array have already been sorted		
		if (sortedArray == null || sortedArray.length == 0) {
			System.out.println("This array is empty!");
		} else if (sortedArray.length == 1) {
			this.root = new TreeElementSibling(null,sortedArray[0] );		
		} else {
			
			this.root = new TreeElementSibling(null,sortedArray[0] );
			LinkedList<TreeElementSibling> nodeQueue = new LinkedList<TreeElementSibling>();
			LinkedList<TreeElementSibling> nodeQueueSec = new LinkedList<TreeElementSibling>();
			
			nodeQueue.addLast(root);
			
			int arrayIndex = 1;					
			 
			//give value to nodes following kind of BFS
			/* previous solution, this is wrong, " sortedArray.length -3 "
			while (nodeQueue != null &&  arrayIndex <= sortedArray.length -3 ) {
				
				TreeElement currNode = nodeQueue.poll();				

				currNode.setLeftChild(  new TreeElement(currNode, sortedArray[arrayIndex]) );
				arrayIndex ++;
				currNode.setRightChild(  new TreeElement(currNode, sortedArray[arrayIndex]) );
				arrayIndex ++;
				
				nodeQueue.addLast( currNode.getLeftChild() );
				nodeQueue.addLast( currNode.getRightChild() );
				
				if ( currNode.getLeftChild() != null ) {
					this.theLastNode = currNode.getLeftChild();
				} 
				if ( currNode.getRightChild() != null ) {
					this.theLastNode = currNode.getRightChild();					
				}				
			}
			*/
 
			boolean isFirstQueue = true;
			while (arrayIndex < sortedArray.length) {
				
				TreeElementSibling currNode,nextNode;
				
				// if this is for firstQueue, then poll first queue and put currNode's child to second queue
				if ( isFirstQueue  ) {
					currNode = nodeQueue.poll();
					if ( nodeQueue.size() >= 1 ) {
						nextNode = nodeQueue.peek();
						currNode.setNextSibling(nextNode);
						nextNode.setPreSibling(currNode);
					}					
					
				} else {
                    currNode = nodeQueueSec.poll();                      
                    if ( nodeQueueSec.size() >= 1 ) {
                    	nextNode = nodeQueueSec.peek();
                    	currNode.setNextSibling(nextNode);
        				nextNode.setPreSibling(currNode);
					}                    
				}	
				
				// add currNode's child from next array value;
				currNode.setLeftChild(  new TreeElementSibling(currNode, sortedArray[arrayIndex]) );
				
				if (isFirstQueue) {
					nodeQueueSec.addLast( (TreeElementSibling)currNode.getLeftChild() );
				} else {
					nodeQueue.addLast( (TreeElementSibling)currNode.getLeftChild() );
				}					
				this.theLastNode = (TreeElementSibling)currNode.getLeftChild();
				arrayIndex ++;
				
				if (arrayIndex < sortedArray.length) {					
					currNode.setRightChild(  new TreeElement(currNode, sortedArray[arrayIndex]) );
					if (isFirstQueue ) {
						nodeQueueSec.addLast((TreeElementSibling)currNode.getRightChild());
					} else {
						nodeQueue.addLast((TreeElementSibling)currNode.getRightChild());
					}					
					this.theLastNode = (TreeElementSibling)currNode.getRightChild();
					arrayIndex ++;					
				}
			
				// working on first queue until this queue becomes empty
				if (nodeQueue.isEmpty()) {
					isFirstQueue = false;
				} 
				
				if (nodeQueueSec.isEmpty()) {
					isFirstQueue = true;
				}
				
			}
			
		}		
	}
	
	
	public Heap(Integer[] sortedArray) {
		
		if ( sortedArray == null || sortedArray.length == 0 ) {			
			System.out.println("This array is empty!");
		
		}  else if ( sortedArray.length == 1 ) {
			this.root = new TreeElementSibling(null,sortedArray[0] );	
			
		} else {
			
			// root and two queue;
			this.root = new TreeElementSibling(sortedArray[0]);
			//this.root.setVaule(sortedArray[0]); 
			LinkedList<TreeElementSibling>  currQueue = new LinkedList<TreeElementSibling>();
			LinkedList<TreeElementSibling>  childQueue = new LinkedList<TreeElementSibling>();
			currQueue.add(root);
		    
			int arrayIndex = 0;
			while ( arrayIndex < sortedArray.length ) {
				
				TreeElementSibling currNode = currQueue.poll();	
				
				//add left child
				currNode.setLeftChild( new TreeElementSibling(currNode, sortedArray[arrayIndex]) );
				addNewNodeToChildQueue(childQueue,(TreeElementSibling)currNode.getLeftChild() );				
				this.theLastNode =  (TreeElementSibling)currNode.getLeftChild();
				arrayIndex ++ ;
				
				//add right child
				if ( arrayIndex < sortedArray.length ) {
					currNode.setRightChild( new TreeElementSibling(currNode, sortedArray[arrayIndex]) );
					addNewNodeToChildQueue( childQueue,(TreeElementSibling)currNode.getRightChild() );						
					this.theLastNode =  (TreeElementSibling)currNode.getRightChild();
					arrayIndex ++ ;
				}
				
				// when currQueue is empty, then switch two queues
				if ( currQueue.isEmpty() ) {
					LinkedList<TreeElementSibling>  tmpQueue = currQueue;
					currQueue = childQueue;
					childQueue = tmpQueue;
				} 				
			}			
		}
	
	}
	
	private void addNewNodeToChildQueue(LinkedList<TreeElementSibling> childQueue, TreeElementSibling newNode) {
		if (!childQueue.isEmpty()) {
			TreeElementSibling lastNodeOfChild= childQueue.getLast();
			lastNodeOfChild.setNextSibling(newNode);
			newNode.setPreSibling(lastNodeOfChild);
		}
		childQueue.add(newNode);
	}
	
	//Assume all values in this tree are unique
	public void addNode(Integer newNodeValue) {
			
		//find where we can add a node for maintaining a complete tree;
		//TODO: NOTE:what if lastnode is root or heap is null/empty--done
		if (newNodeValue == null || this.root == null || this.size == 0 || this.theLastNode == null ) {			
			System.out.println(" Invalid value or heap! ");
			return;			
		}
		
		TreeElementSibling lastParent =(TreeElementSibling) this.theLastNode.getParent(); 
		if (this.theLastNode == lastParent.getLeftChild() ) {			
			lastParent.setRightChild(new TreeElementSibling(lastParent, newNodeValue ));
			this.theLastNode = (TreeElementSibling)this.getFirstLeftLeaf().getRightChild();	
			
		} else {
			
			if ( lastParent.getNextSibling() == null ){
				TreeElementSibling firstLeftLeaf = this.getFirstLeftLeaf();
				firstLeftLeaf.setLeftChild( new TreeElementSibling(firstLeftLeaf, newNodeValue ) );
				this.theLastNode = (TreeElementSibling) firstLeftLeaf.getLeftChild();
			} else {
				TreeElementSibling nextSibling = lastParent.getNextSibling();
				nextSibling.setLeftChild( new TreeElementSibling(nextSibling, newNodeValue ) );
				this.theLastNode = (TreeElementSibling)nextSibling.getLeftChild();
			}			 
			
		}
		
		// replacing until new node find the right place;
		TreeElementSibling currNode = this.theLastNode;		
		while (currNode.getParent() != null && currNode.getVaule() < currNode.getParent().getVaule() ) {			
			Integer tmpValue = currNode.getVaule();
			currNode.setVaule(currNode.getParent().getVaule());
			currNode.getParent().setVaule(tmpValue);
			currNode = (TreeElementSibling)currNode.getParent();
		}	
		
		this.size ++ ;
	}
	
	private TreeElementSibling getFirstLeftLeaf() {					
		TreeElementSibling nextLeftChild = (TreeElementSibling)this.getRoot().getLeftChild();		
		while ( nextLeftChild != null )  {
			nextLeftChild = (TreeElementSibling)nextLeftChild.getLeftChild();		
		}		
		return nextLeftChild;	
	}
	
	private TreeElementSibling getFirstRightLeaf() {					
		TreeElementSibling nextLeftChild = (TreeElementSibling)this.getRoot().getRightChild();		
		while ( nextLeftChild != null )  {
			nextLeftChild = (TreeElementSibling)nextLeftChild.getRightChild();		
		}		
		return nextLeftChild;	
	}
	
	// heap only can delete from root;
	public void deleteNode() {
		
		if (this.root == null || this.theLastNode == null || this.size == 0 ) {
			System.out.println(" Invalid heap! or this is an empty heap");
			return;
		} 		
		
		//replace the value: root and last node
		this.root.setVaule( this.theLastNode.getVaule() );
		
		
		
		//delete last node
		TreeElementSibling lastParent = (TreeElementSibling)this.theLastNode.getParent(); 
		if (lastParent == null ) {
			this.root = null;
			this.theLastNode = null;
			this.size = 0;
			return;
		}
		
		if ( this.theLastNode == lastParent.getRightChild() ) {
			lastParent.setRightChild(null);
		} else {
			lastParent.setLeftChild(null);
		}
		this.theLastNode.setParent( null);
		
		if ( this.theLastNode.getPreSibling() == null ) {			
			// this is the most left leaf						
			TreeElementSibling theRightLeaf = this.getFirstRightLeaf();
			this.theLastNode = theRightLeaf;	
			
		} else {
			TreeElementSibling preSibling = this.theLastNode.getPreSibling();
			preSibling.setNextSibling(null);
			this.theLastNode.setPreSibling(null);			
			this.theLastNode = preSibling;
		}
		
		// 
		TreeElementSibling curNode = this.root;
		TreeElementSibling candidateChildNode = ifNodeHeavyThanChildren(curNode);
		while ( candidateChildNode != null ) {
			
			exchangeValueOfNode(candidateChildNode, curNode);
			curNode = candidateChildNode;
			candidateChildNode = ifNodeHeavyThanChildren(curNode);
		}
		
		size --;
	}
	
	private TreeElementSibling ifNodeHeavyThanChildren(TreeElementSibling node) {
		
		if ( node.getLeftChild()!= null && node.getVaule() < node.getLeftChild().getVaule() ) {
			return (TreeElementSibling)node.getLeftChild();
		} else if ( node.getRightChild() != null && node.getVaule() < node.getRightChild().getVaule()) {
			return (TreeElementSibling)node.getRightChild(); 
		}
		else { return null;}
	}
	
	private void exchangeValueOfNode(TreeElementSibling nodeOne, TreeElementSibling nodeTwo) {
		Integer tmpValue = nodeOne.getVaule();
		nodeOne.setVaule(nodeTwo.getVaule());
		nodeTwo.setVaule(tmpValue);		
	}
	

}
