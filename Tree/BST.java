package test.tree;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


/*** 
 * @author jingma1
 * What is non-empty tree: there is a root, there might be more than 0 sub-tree, and root connect its sub-tree with at least one edge
 * Assumption: Integer Element; no-duplicated elements; 
 * This will be a binary-search tree;
 * 
 * 1.extract note
 * 2.DFS, BFS: done
 * 3.Pre-order, in-order, post-order done
 * 4.Add & Delete, re-balance - rebalance TODO
 * 5.Try stack way for traverse:done
 * 6.AVL tree TODO
 *  
 */

public class BST {
	private TreeElement rootNode;
	
	public TreeElement getRoot() {
		return this.rootNode;
	}
	
	public BST(TreeElement root) {
		this.rootNode = root;		
		//TODO: but this will make this tree's root has parent! if I change this root's parent, 
		//then I kind of split that sub-tree out, How should I do that?
	}
	
	public BST(Integer[] initArray, int beginIndex, int endIndex) {
				
		//TODO:1. how to implement re-balance???? 2. HOW to traverse a tree with all? DFS, or BFS
		
		//sorting the array
		Integer[] sortedArray = sortArrayBubble(initArray);
		
		//step1: get the root
		int indexSplit = (int)findMidValueIndex(sortedArray, beginIndex, endIndex);
		
		if ( indexSplit >= 0 ) {
			
			if (endIndex - beginIndex == 0 ) {
				
				this.rootNode =  new TreeElement(initArray[beginIndex]);
				System.out.println("one node root:"+this.rootNode.getVaule());
			    this.rootNode.setLeftChild(null);
			    this.rootNode.setRightChild(null);
								
			} else if (endIndex - beginIndex == 1) {
				
				this.rootNode =  new TreeElement(null,initArray[endIndex]);	
				TreeElement leftChild = new TreeElement(rootNode,initArray[beginIndex]);				
				this.rootNode.setLeftChild( leftChild );
			    		    
				
			} else if (endIndex - beginIndex > 2) {
				
				System.out.println(indexSplit+"indexSplit");
				Integer rootValue = initArray[indexSplit];
				System.out.println(rootValue+" rootValue");
				this.rootNode = new  TreeElement(rootValue);
				this.rootNode.setVaule(rootValue);	

				//step2: find left tree root: L-root; connect root with L-root;		
				BST leftSubTree = new BST(sortedArray,beginIndex,indexSplit - 1);

				//step3: find right tree root: R-root; connect root with R-root;		
				BST rightSubTree = new BST(sortedArray,indexSplit + 1, endIndex);		
				
				//step4.1 get root of left tree and right tree 
				TreeElement leftRoot = leftSubTree.getRoot();
				TreeElement rightRoot = rightSubTree.getRoot();
				
				//step4.2 connect left/right node with root
				rightRoot.setParent(rootNode);
				leftRoot.setParent(rootNode);
				rootNode.setLeftChild(leftRoot);
				rootNode.setRightChild(rightRoot);
				//TODO: NOTE: special condition + end condition (1.handling findMidValueIndex = "-1", 2.end condition)	
				
			}
			
		}
		 
	}
	
	
	
	private static long findMidValueIndex(Integer[] valueArray,int rangeStartIndex, int rangeEndIndex) {
		
		if (valueArray == null || valueArray.length == 0 || rangeEndIndex  < rangeStartIndex 
				|| (valueArray.length == 1 && rangeStartIndex > 0) ) {			
			System.out.println("Nothing in this array!! ");
			return -1;
		} else if (rangeEndIndex - rangeStartIndex <= 1 ) {
			return rangeEndIndex;
		} else { 
			double midSize = ( rangeEndIndex + rangeStartIndex ) / 2.0;
			// TODO: note this, want: double, expression MUST be all at least double, if there is any int/long, this expression result 
			// have to be int/long
			return Math.round(midSize);		
		}		
	}
	
	private static Integer[] sortArrayBubble(Integer[] integerElement ) {
		
		//TODO: this way will change the value/order of integerElement, not sure if this is the best way;
		
		if (integerElement == null || integerElement.length < 2 ) {		
			
			return integerElement;			
		}
		else {
			for (int tranverseTimes = integerElement.length -1; tranverseTimes > 0 ; tranverseTimes -- ) {
				
				for (int index = 1; index <= tranverseTimes; index ++ ) {					
					Integer intermediaValue;
					if (integerElement[index-1] > integerElement[index]) {						
						intermediaValue = integerElement[index - 1];
						integerElement[index -1] = integerElement[index];
						integerElement[index] = intermediaValue;					
					}
			   }					
			}			
			return integerElement;
			
		}		
	}
	
	private static Integer[] sortArrayInsertionSort(Integer[] integerElementArray) { 
		
		if (integerElementArray == null || integerElementArray.length < 1 ) {	
			System.out.println("Invalid input of array");
			return null;			
		
		} else if (integerElementArray.length  == 1) {
			return integerElementArray;			
	
		} else {
			
			//Integer firstElement = integerElementArray[0];
			//Integer[] rsArray = new Integer[integerElementArray.length];
			LinkedList<Integer> sortedList = new LinkedList<Integer>();
			//sortedList.add(firstElement);			
			
			for( int index =0; index < integerElementArray.length; index ++ ) {
				
				Integer orgnArrayElement = integerElementArray[index];
				sortedList.add(orgnArrayElement);			
				
				if (sortedList.size() == 1) {
					continue;					
				}
				
				for (int listIndex = 0; listIndex < sortedList.size() && listIndex +1 < sortedList.size(); listIndex ++ ) {
					
					if ( sortedList.get(listIndex + 1 ) < sortedList.get(listIndex) ) {
						Integer tmpInteger = sortedList.get(listIndex + 1 );
						sortedList.add(listIndex, sortedList.get(listIndex) );
						sortedList.add(listIndex, tmpInteger);	
					}
					
				}
			}
			
			//TODO: should I use linkedList here?
			Integer[] resultArray = new Integer[integerElementArray.length];
			int newArrayIndex = 0;
			for (Integer ele: sortedList) {
				resultArray[newArrayIndex] = ele;
				newArrayIndex ++;				
			}			
			return resultArray;		  	
		}
	}
	
	private static Integer[] sorArrayShell(Integer[] subArray, int numOfSubArray) {
		
		if (subArray == null || subArray.length < 2 ) {		
			return subArray; 
		}
		
		if (numOfSubArray > subArray.length  ) {			
			return sortArrayBubble(subArray);			
		}	
		
		//TODO: also can perform other sorting here
		for ( int interationTimes = numOfSubArray; interationTimes >0 ; interationTimes -- ) {			
			
			for (int index =0; index < subArray.length && index + interationTimes < subArray.length;
					index = index + interationTimes ) {
				if (subArray[index] > subArray[index + interationTimes]) {				
				
					Integer tmpInteger = subArray[index];
					subArray[index] = subArray[index + interationTimes];
					subArray[index + interationTimes] = tmpInteger;
				
				}	
			}			
		}
		 return subArray;	
		 
	}
	
	
	private static Integer[] sortArrayShell(Integer[] integerElementArray, int beginIntervalLength){
		
		return sorArrayShell(integerElementArray,beginIntervalLength);	
		
	}
	
	private static void sortTwoArrayElement(Integer[] basicArray, int elementOneIndex, int elementSecIndex) {
		
		//Bigger one will be the second
		
		Integer tmpElement;
		if (basicArray[elementOneIndex] > basicArray[elementSecIndex]) {
			
			tmpElement = basicArray[elementOneIndex];				
			basicArray[elementOneIndex] = basicArray[elementSecIndex];
			basicArray[elementSecIndex] = tmpElement;
			
		}
		
	}
	
	/**
	 * 
	 * @param integerElementArray
	 * @param firstBeginIndex
	 * @param firstEndIndex
	 * @param secondEndIndex
	 * 
	 * NOTE: two improvements: change double-loops (Cartesian product) to moving two lines towards the same direction
	 * TODO: this one will implicate change original Array： integerElementArray.
	 * 
	 */
	
    private static void sortArrayMergeFirst(Integer[] integerElementArray,int firstBeginIndex, int firstEndIndex, int secondEndIndex){
    	    	
		if ( integerElementArray == null || integerElementArray.length == 1 || secondEndIndex - firstBeginIndex == 0 
				|| secondEndIndex > integerElementArray.length -1 || firstEndIndex > secondEndIndex  || firstBeginIndex > firstEndIndex ) {
			System.out.println("illegal ones " );
			return;			
			
		} else if ( secondEndIndex - firstBeginIndex == 1  ) {
			System.out.println("only two elements " );
			sortTwoArrayElement(integerElementArray,firstBeginIndex,secondEndIndex);
			return;
			
		} else {
			
			int firstFHalfEndIndex =  (firstEndIndex+firstBeginIndex) / 2  ;
			int firstSHalfEndIndex = firstEndIndex;
			int secondFHalfEndIndex = (secondEndIndex + firstEndIndex + 1) / 2;
			int secondSHalfEndIndex = secondEndIndex;
						
			// first&second part sorting
			if ( firstEndIndex - firstBeginIndex > 1) {
				sortArrayMergeFirst(integerElementArray,firstBeginIndex,firstFHalfEndIndex, firstSHalfEndIndex);
			} else if ( firstEndIndex - firstBeginIndex ==1  )  {
				sortTwoArrayElement(integerElementArray,firstBeginIndex,firstEndIndex);
			} else {
				System.out.println("Nothing I can do" );
			}
				
			if ( secondEndIndex - (firstEndIndex + 1) > 1) {
				sortArrayMergeFirst(integerElementArray,firstEndIndex + 1,secondFHalfEndIndex, secondSHalfEndIndex);
			} else if ( secondEndIndex - (firstEndIndex + 1) ==1  )  {
				sortTwoArrayElement(integerElementArray,(firstEndIndex + 1),secondEndIndex);
			} else {
				System.out.println("Nothing I can do second" );
			}
						
			int secArrayPopIndex = firstEndIndex + 1;
			
			// merge two parts with sorting functions
			LinkedList<Integer> sortedResultList = new LinkedList<Integer>();			
			
			int j = 0;
			int i = 0;
			
			for ( i = firstBeginIndex; i <= firstSHalfEndIndex; i ++ ) {
				
				//System.out.println("firstBeginIndex: " + firstBeginIndex + "   firstSHalfEndIndex: " + firstSHalfEndIndex);
				
				for ( j = secArrayPopIndex; j <= secondSHalfEndIndex; j ++) {					
								
					if ( integerElementArray[i] > integerElementArray[j]  ) {
						//System.out.println(" ************integerElementArray[j] added " + integerElementArray[j]);
						sortedResultList.add(integerElementArray[j]);	
						secArrayPopIndex = j + 1;
						
					} else if ( integerElementArray[i] == integerElementArray[j] ) {
						//System.out.println(" ************integerElementArray[j] added " + integerElementArray[j]);
						sortedResultList.add(integerElementArray[j]);
						sortedResultList.add(integerElementArray[i]);
						secArrayPopIndex = j + 1;
						break;
						
					} else {
						//System.out.println(" ************integerElementArray[i] added " + integerElementArray[i]);
						sortedResultList.add(integerElementArray[i]);
						break;						
					} 	
				}	
			}
			
			// handling those tails
			while (i <= firstEndIndex) {				
				sortedResultList.add(integerElementArray[i]);
				i ++;
			}			
			while ( j <= secondEndIndex ) {
				sortedResultList.add(integerElementArray[j]);
				j ++;
			}
			
			//System.out.println("sortedResultList size" + sortedResultList.size());
			int orgnArrayBeginIndex = firstBeginIndex;
			for ( Integer sortedListElement: sortedResultList ) {
				
				integerElementArray[orgnArrayBeginIndex] = sortedListElement;
				orgnArrayBeginIndex ++;	
			}			
		}		
		return;		
	}
    
    
    private static void sortArrayMergeSec(Integer[] rgnArray, int firstBeginIndex, int firstEndIndex, int secondEndIndex ) {
    	
    	if ( rgnArray == null || rgnArray.length == 1 || secondEndIndex - firstBeginIndex == 0 
				|| secondEndIndex > rgnArray.length -1 || firstEndIndex > secondEndIndex  || firstBeginIndex > firstEndIndex ) {
			System.out.println("illegal ones " );
			return;	
			
		} else {
			
			// split this array
			int midFirstIndex = (firstBeginIndex + firstEndIndex) / 2;			
			int midSecondIndex = (firstEndIndex + 1 + secondEndIndex) / 2;
			
			
			// sort sub-arrays
			if ( firstEndIndex - firstBeginIndex == 1 ) {				
				sortTwoArrayElement(rgnArray,firstBeginIndex,firstEndIndex );								
			} else {
				sortArrayMergeFirst( rgnArray, firstBeginIndex,midFirstIndex ,firstEndIndex);
			}
			
			if ( secondEndIndex - (firstEndIndex + 1) == 1 ) {				
				sortTwoArrayElement(rgnArray,firstEndIndex + 1, secondEndIndex );								
			} else {
				sortArrayMergeFirst(rgnArray, firstEndIndex + 1,midSecondIndex, secondEndIndex);
			}
			
			
			//merge sub arraies
			int i = firstBeginIndex;
			int j = firstEndIndex + 1;
			LinkedList<Integer> sortedList = new LinkedList<Integer>();
			System.out.println("first while");
			while( i <= firstEndIndex && j <= secondEndIndex )  {				
				if ( rgnArray[i] > rgnArray[j] ) {		
					
					sortedList.add(rgnArray[j] );
					j ++ ;					
				} else if ( rgnArray[i] == rgnArray[j] ) {	
					
					sortedList.add(rgnArray[j] );
					sortedList.add(rgnArray[i] );
					i ++ ;
					j ++ ;					
				} else {	
					
					sortedList.add(rgnArray[i] );
					i ++ ;				
				}						
			}
			
			//System.out.println("second while i:" + i+ "j:" + j);
			while ( i <= firstEndIndex) {
				sortedList.add(rgnArray[i]);
				i ++ ;
			}
			while ( j <= secondEndIndex ) {
				sortedList.add(rgnArray[j]);
				j ++ ;
			}
			System.out.println("list length:" + sortedList.size());
			int orgnArrayBeginIndex = firstBeginIndex;
			for ( Integer sortedListElement: sortedList ) {				
				rgnArray[orgnArrayBeginIndex] = sortedListElement;
				//System.out.println(sortedListElement);
				orgnArrayBeginIndex ++;	
			}
		}
    	
    }
    
    

    	
    private static void sortArrayQuickFirst(Integer[] rgnArray, int startIndex, int endIndex){

    	LinkedList<Integer> sortedList = new LinkedList<Integer>();
    	
    	if (rgnArray.length == 1 || endIndex == startIndex ) {
    		// only one element in this array
    		System.out.println("only one ele, do noting");
    		
    	} else if (rgnArray.length == 2) {
    		
    		sortTwoArrayElement(rgnArray,startIndex,endIndex);
    		
    	} else {
    		// 1. generate a start point here, randomly
    		Random rd = new Random(endIndex - startIndex);
    		System.out.println(endIndex - startIndex);
    		int sampleOne = startIndex + rd.nextInt(endIndex - startIndex);
    		int sampleSecond = startIndex + rd.nextInt(endIndex - startIndex);
    		    		
    		int splitPoint = (sampleOne + sampleSecond ) / 2; 
    		
    		
    		
    		// 2. get collection bigger than the point, get collection less than the point
    		Integer splitValue = rgnArray[splitPoint]; 
    		int littleListSize;
    		{
    			LinkedList<Integer> bigList = new LinkedList<Integer>();
        		LinkedList<Integer> littleList = new LinkedList<Integer>();
        	    for (int eleIndex = startIndex; eleIndex<= endIndex; eleIndex ++) {
        	    	
        	    	if (rgnArray[eleIndex] >= splitValue) {
        	    		bigList.add(rgnArray[eleIndex]);    	    		
        	    	    	    		    	    		
        	    	} else {    	    		
        	    		littleList.add(rgnArray[eleIndex]);
        	    	}
        	    }    	
        	    
        	    littleListSize = littleList.size();
        	           	    
        	    littleList.addAll(bigList);
        	    int arrayEleIndex = startIndex; 
        	    
        	    for (Integer ele: littleList)  {
        	    	rgnArray[arrayEleIndex]  = ele;
        	    	arrayEleIndex ++;
        	    }
        	    
    		}
    		
    		// 3. call sort method recursively
    		sortArrayQuickFirst(rgnArray,startIndex, littleListSize + startIndex);
    		sortArrayQuickFirst(rgnArray,littleListSize + startIndex + 1, endIndex);    		
    	}
		
	}
    
    // TODO: note how to handle moving elements 
    private static void sortArrayQuickSec(Integer[] rgnArray, int startIndex, int endIndex){

    	LinkedList<Integer> sortedList = new LinkedList<Integer>();
    	
    	if (rgnArray.length == 1 || endIndex == startIndex ) {
    		// only one element in this array
    		System.out.println("only one ele, do noting");
    		
    	} else if (rgnArray.length == 2) {
    		
    		sortTwoArrayElement(rgnArray,startIndex,endIndex);
    		
    	} else {
    		// 1. generate a start point here, randomly
    		Random rd = new Random(endIndex - startIndex);
    		//System.out.println(endIndex - startIndex);
    		int sampleOne = startIndex + rd.nextInt(endIndex - startIndex);
    		int sampleSecond = startIndex + rd.nextInt(endIndex - startIndex);
    		    		
    		int splitPoint = (sampleOne + sampleSecond) / 2; 
    		
    		// 2. get collection bigger than the point, get collection less than the point
    		Integer splitValue = rgnArray[splitPoint]; 
    		int arrayBackwordIndex = endIndex;
    		int arrayForwardIndex;
    		for(arrayForwardIndex = startIndex; arrayForwardIndex <= arrayBackwordIndex ; arrayForwardIndex ++ ) {
    			
    			if ( rgnArray[arrayForwardIndex] > splitValue ) {    				
    				while ( arrayBackwordIndex >= arrayForwardIndex) {    					
    					if ( rgnArray[arrayBackwordIndex] <= splitValue ) {
    						//exchange 
    						Integer tmpValueHolder = rgnArray[arrayBackwordIndex];
    						rgnArray[arrayBackwordIndex] = rgnArray[arrayForwardIndex];
    						rgnArray[arrayForwardIndex] = tmpValueHolder;
    						arrayBackwordIndex --; 
    						break;
    					} else {
    						arrayBackwordIndex --; 
    					}    					   					
    				}
    			}	
    			
    		}
    		
    		// two situations: 1. ended on b -1 = f; 2. ended on b = f;
    		int splitPointSec = 0;
    		if (arrayBackwordIndex == arrayForwardIndex ) {
    			if (rgnArray[arrayBackwordIndex] <= splitValue  ) {
    				splitPointSec = arrayBackwordIndex + 1; 
    			} else {
    				splitPointSec = arrayBackwordIndex;
    			}    			 
    		} else if (arrayForwardIndex > arrayBackwordIndex ) {
    			splitPointSec = arrayBackwordIndex;    			
    		} else {
    			System.out.println("something is wrong!");
    		}
    		
    		// 3. call sort method recursively
    		sortArrayQuickSec(rgnArray,startIndex, splitPointSec);
    		sortArrayQuickSec(rgnArray,splitPointSec + 1, endIndex);    		
    	}
		
	}
    
    private static void sortArrayQuickThird(Integer[] rgnArray, int startIndex, int endIndex){

    	LinkedList<Integer> sortedList = new LinkedList<Integer>();
    	
    	if (rgnArray.length == 1 || endIndex == startIndex ) {
    		// only one element in this array
    		System.out.println("only one ele, do noting");
    		
    	} else if (rgnArray.length == 2) {
    		
    		sortTwoArrayElement(rgnArray,startIndex,endIndex);
    		
    	} else {
    		// 1. generate a start point here, randomly
    		Random rd = new Random(endIndex - startIndex);
    		//System.out.println(endIndex - startIndex);
    		int sampleOne = startIndex + rd.nextInt(endIndex - startIndex);
    		int sampleSecond = startIndex + rd.nextInt(endIndex - startIndex);
    		    		
    		int splitPoint = (sampleOne + sampleSecond) / 2; 
    		
    		// 2. get collection bigger than the point, get collection less than the point
    		Integer splitValue = rgnArray[splitPoint]; 
    		
    		int arrayBackwordIndex = endIndex;    		
    		int arrayForwardIndex = startIndex;
    		while (arrayForwardIndex<=arrayBackwordIndex) {
    			
    			if ( rgnArray[arrayForwardIndex] > splitValue && rgnArray[arrayBackwordIndex] <= splitValue) {
    				
    				Integer tmpValueHolder = rgnArray[arrayBackwordIndex];
					rgnArray[arrayBackwordIndex] = rgnArray[arrayForwardIndex];
					rgnArray[arrayForwardIndex] = tmpValueHolder;
					
					arrayForwardIndex ++ ;
					arrayBackwordIndex --;		
    				
    			} else if ( rgnArray[arrayForwardIndex] > splitValue && rgnArray[arrayBackwordIndex] > splitValue ) {
    				arrayBackwordIndex -- ;
    				
    			} else if (rgnArray[arrayForwardIndex] <=  splitValue) {
    				arrayForwardIndex ++;    				
    			}    	
    			
    		}
    		
    		
    		// two situations: 1. ended on b -1 = f; 2. ended on b = f;
    		int splitPointSec = 0;
    		if (arrayBackwordIndex == arrayForwardIndex ) {
    			if (rgnArray[arrayBackwordIndex] <= splitValue  ) {
    				splitPointSec = arrayBackwordIndex + 1; 
    			} else {
    				splitPointSec = arrayBackwordIndex;
    			}    			 
    		} else if (arrayForwardIndex > arrayBackwordIndex ) {
    			splitPointSec = arrayBackwordIndex;    			
    		} else {
    			System.out.println("something is wrong!");
    		}
    		
    		// 3. call sort method recursively
    		sortArrayQuickThird(rgnArray,startIndex, splitPointSec);
    		sortArrayQuickThird(rgnArray,splitPointSec + 1, endIndex);    		
    	}
		
	}
       
    /**
     * traverse whole tree with DFS and pre order
     * @return
     */
	public void  TranverseDFSPreOrder(TreeElement subTreeRoot) {
			
		if ( subTreeRoot != null ) {
			System.out.println(" this node@:" + subTreeRoot.getVaule());
			if (  subTreeRoot.getParent() != null  ) {
				System.out.println(" (parent node@:" + subTreeRoot.getParent().getVaule() + ")");
			}			
						
			System.out.print(" left sub-tree");
			TranverseDFSPreOrder(subTreeRoot.getLeftChild()) ;
			System.out.print(" right sub-tree");
			TranverseDFSPreOrder(subTreeRoot.getRightChild()) ;			
		}			
	}
	
	/**
	 * left-root-right  +DFS
	 */
	public void TranverseDFSInOrder(TreeElement subTreeRoot) {
		
		if (subTreeRoot != null ) { //kind of end condition, leaf's child is null node
			//left subtree
			TranverseDFSInOrder(subTreeRoot.getLeftChild());
			
			//root
			System.out.println(" this node@:" + subTreeRoot.getVaule());
			if (  subTreeRoot.getParent() != null  ) {
				System.out.println(" (parent node@:" + subTreeRoot.getParent().getVaule() + ")");
			}	
			
			//right subtree
			TranverseDFSInOrder(subTreeRoot.getRightChild());
		}
		
	}
	
	/**
	 * right-root-left +DFS
	 */
	public void TranverseDFSPostOrder(TreeElement subTreeRoot) {
		
		if (subTreeRoot != null ) { //kind of end condition, leaf's child is null node
			//right subtree
			TranverseDFSPostOrder(subTreeRoot.getRightChild());
			
			//root
			System.out.println(" this node@:" + subTreeRoot.getVaule());
			if (  subTreeRoot.getParent() != null  ) {
				System.out.println(" (parent node@:" + subTreeRoot.getParent().getVaule() + ")");
			}	
			
			//left subtree
			TranverseDFSPostOrder(subTreeRoot.getLeftChild());
		}		
	}

	/**
	 * BFS
	 */	
	public void TraverseBFS(TreeElement treeRoot) {		
		LinkedList<TreeElement> nodeQueue = new LinkedList<TreeElement>();				
		nodeQueue.add(treeRoot);		
        while ( ! nodeQueue.isEmpty() ) {
        	TreeElement currNode = nodeQueue.poll();
        	treeNodeBFSProcessor(currNode, nodeQueue);        	
        }		
	}
	
	private void treeNodeBFSProcessor(TreeElement node, LinkedList<TreeElement> treeNodeQueue){
		if ( node != null  ) {				
			System.out.println(node);				
			treeNodeQueue.add(node.getLeftChild());
			treeNodeQueue.add(node.getRightChild());				
		} 
	}
	
	/**
	 * DFS non-recursive way
	 */
	public TreeElement TraverseDFSPreOrderBiggerThan( TreeElement treeRoot, Integer stopValue ) {		
		ArrayDeque<TreeElement> nodeStack = new ArrayDeque<TreeElement>();		
		nodeStack.addFirst(treeRoot);		
		while ( nodeStack != null ) {	
			TreeElement  currNode = nodeStack.pollFirst();
			if ( currNode != null ) {
				System.out.println("current node:"+ currNode );		
				if ( currNode.getVaule() > stopValue ) {
					return currNode;
				}
				nodeStack.addFirst(currNode.getRightChild());
				nodeStack.addFirst(currNode.getRightChild());					
			} 
		}	
		return null;
	}
	
	public void TraverseDFSPreOrderNonR( TreeElement treeRoot ) {		
		ArrayDeque<TreeElement> nodeStack = new ArrayDeque<TreeElement>();		
		nodeStack.addFirst(treeRoot);		
		while ( nodeStack != null ) {			
			treeNodeDFSProcessor(nodeStack.pollFirst(),nodeStack);
		}				
	}
	
	private void treeNodeDFSProcessor(TreeElement node, ArrayDeque<TreeElement> treeNodeStack) {
		if ( node != null ) {
			System.out.println("current node:"+ node );			
			treeNodeStack.addFirst(node.getRightChild());
			treeNodeStack.addFirst(node.getRightChild());			
		} 
	}
	
	/*** 
	 * TODO: insert, delete, successor, predecessor, maxOfSubTree, minOfSubTree
	 ***/
	public TreeElement getMaxOfTree() {
		
		//get the rightest leaf of one tree		
		TreeElement currRoot = this.rootNode;
		while( currRoot != null && currRoot.getRightChild() == null ) {
			currRoot = currRoot.getLeftChild();			
		}		
		return currRoot;
	}
	
	public TreeElement getMinOfTree() {		
		//get the leftest leaf of one tree		
		TreeElement currNode = this.rootNode;
		while ( currNode != null && currNode.getRightChild() == null  ) {			
			currNode = currNode.getRightChild();			
		}
		return currNode;		
	}
	
	public TreeElement getTreeSuccessorInOrder(TreeElement currNode) {
		
		if ( currNode == null || currNode == this.getMinOfTree()) {
			return null;
		} else  if ( currNode.getLeftChild() != null ) {
			
			//TODO: How to new a BST with a given root? check question on constructor
			BST leftSubTree = new BST(currNode.getLeftChild());			
			return leftSubTree.getMaxOfTree();
			
		} else {
			//TreeElement currParent = currNode.getParent();
			while ( currNode.getParent() != null && currNode.getParent().getLeftChild() == currNode ) {
				currNode = currNode.getParent();
			}			
			return currNode.getParent();			
		}
	
	}
	
	public TreeElement getTreePredecessorInOrder(TreeElement currNode) {
		if ( currNode == null || currNode == this.getMaxOfTree() ) {
			return null;			
		} else if ( currNode.getRightChild() != null ) {
			BST rightSubTree = new BST( currNode.getRightChild() );
			return rightSubTree.getMinOfTree();			
		} else {			
			while ( currNode.getParent() != null && currNode.getParent().getRightChild() == currNode ) {
				currNode = currNode.getParent();
			}			
			return currNode.getParent();
		}		
	}
	
    public void insertNode(TreeElement node) {
		if ( node.getVaule() <= this.getMinOfTree().getVaule()  ) {
			//smaller than min, add to the min node;			
			node.setParent(this.getMinOfTree());
			this.getMinOfTree().setLeftChild(node);
		} else if ( node.getVaule() > this.getMaxOfTree().getVaule()   ) {			
			node.setParent(this.getMaxOfTree());
			this.getMaxOfTree().setRightChild(node);			
		} else {
			TreeElement firstBiggerNode = this.TraverseDFSPreOrderBiggerThan(this.getRoot(), node.getVaule());
			
			if ( firstBiggerNode.getLeftChild() != null ) {				
				TreeElement preLeftNode = firstBiggerNode.getLeftChild();
				preLeftNode.setParent(node);
				node.setLeftChild(preLeftNode);
				node.setParent(firstBiggerNode);
				firstBiggerNode.setLeftChild(node);				
			}
		}
		
	}
    
    public boolean deleteNode(TreeElement node) {
    	if (node == null || node.getVaule() <= this.getMinOfTree().getVaule() || node.getVaule() > this.getMaxOfTree().getVaule()   ) {
    		System.out.println("Can not find this node!! ");
			return false;
		} else {
			//Assume there all keys are unique	
			BST leftSubTree = new BST(node);
			
			TreeElement maxNodeOfLeft = leftSubTree.getMaxOfTree();
			maxNodeOfLeft.setParent(node.getParent());
			maxNodeOfLeft.setLeftChild(node.getLeftChild());
			maxNodeOfLeft.setRightChild(node.getRightChild());
			TreeElement parentNode = node.getParent();
			
			if ( parentNode.getLeftChild() == node  ) {
				parentNode.setLeftChild(maxNodeOfLeft);
			} else if ( parentNode.getRightChild() == node  ) {
				parentNode.setRightChild(node);
			}
			return true;			
		}
    	
    }
	
	public TreeElement getNodeByKey(Integer searchKey, TreeElement rootNode) {
		//Assume there is no duplicate
		if (rootNode == null || searchKey == null) {
			System.out.println("Can't get node with search key = "+ searchKey);			
			return null;
			
		} else if (searchKey == rootNode.getVaule()) {
			return rootNode;
			
		} else {
			TreeElement leftChild = rootNode.getLeftChild();
			TreeElement rightChild = rootNode.getRightChild();
			if ( searchKey >   rootNode.getVaule() ) {
				return getNodeByKey(searchKey, rightChild);				
			} else {
				return getNodeByKey(searchKey, leftChild);
			}			
		}
		
	}
	
	public static void main(String[] args) {
		Integer[] iniIntArrary = {10,1,3,2,5,4,6,9,8,7,0};
		
		for (Integer element: sortArrayBubble(iniIntArrary) ){
			//System.out.println(element);
			
		}
		
		for (Integer element: sortArrayInsertionSort(iniIntArrary) ) {
			//System.out.println(element);
		}
		
		//shell sorting
		for (Integer element: sortArrayShell(iniIntArrary,3) ) {
			//System.out.println(element);
		}
		//sortArrayMergeSec(iniIntArrary,0, 4, iniIntArrary.length -1 );	
		//sortArrayMergeFirst(iniIntArrary,0, 4, iniIntArrary.length -1 );
		//sortArrayQuickSec(iniIntArrary,0,iniIntArrary.length -1);
		//sortArrayQuickThird(iniIntArrary,0,iniIntArrary.length -1);
		for (Integer element: iniIntArrary ) {
			//System.out.println(element);
		}
		
		int x = (int)findMidValueIndex(iniIntArrary,0,10);
		//System.out.println(findMidValueIndex(iniIntArrary,0,10));
		//System.out.println(iniIntArrary[x]);
		//System.out.println(Math.round((3-0)/2.0)+0);
		
		/**
		 * test initialization
		 */
		Integer[] iniIntArrary2 = {11};
		BST testTree = new BST(iniIntArrary, 0, iniIntArrary.length -1 );		
		System.out.println("root:" + testTree.getRoot().getVaule());
		
		/**
		 * test traverse
		 */
		
	    TreeElement root = testTree.getRoot();
	   // testTree.TranverseDFSPreOrder(root);
	   // testTree.TranverseDFSInOrder(root);
	    
	    System.out.println( testTree.getNodeByKey(90, testTree.getRoot()) );
		
	}
	
	
	
}
