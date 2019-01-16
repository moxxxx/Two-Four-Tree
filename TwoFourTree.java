
package comp2402a8;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Collections;

/**
 * The TwoFourTree class is an implementation of the 2-4 tree from notes/textbook.
 * The tree will store Strings as values.
 * It extends the (modified) sorted set interface (for strings).
 * It implements the LevelOrderTraversal interface.
 */
public class TwoFourTree extends StringSSet implements LevelOrderTraversal{

	/* your class MUST have a zero argument constructor. All testing will
	   use this constructor to create a 2-3 tree.
    */
	protected Node root;
	public TwoFourTree(){
		// add whatever code you want.
		root = new Node();
	}
	public String levelOrder(){
		Queue<Node> q = new LinkedList<Node>();
		String output = "";
		q.add(root);
		q.add(null);
		//System.out.println("add null");
		while (!(q.size()==1 && q.peek()== null)) {
			//System.out.println("!");
			Node u = q.remove();
			//System.out.println(u);
			if (u == null) {//new level!
				q.add(null);
				output = output.substring(0, output.length() - 1);//
				output += "|";
				//System.out.println("new level");
			} else if (u != StringSSet.NIL) {//print node data! with comma
				for (String x : u.data) {
					if (x == StringSSet.EMPTY)
						break;
					output += (x + ",");
				}
				output = output.substring(0, output.length()-1);
				output += ":";//new node!
				for (Node x : u.children) {
					if (x == StringSSet.NIL)
						break;
					((LinkedList<Node>) q).add(x);
				}
			}
		}

		return output.substring(0,output.length()-1);
	}
	public int size(){
		if (root != StringSSet.NIL)
			return traverse(root);
		return 0;
	}
	protected int traverse(Node e){
		if (e == StringSSet.NIL)return 0;
		int i = 0;
		for (String x : e.data){
			if (x == StringSSet.EMPTY)break;
			i++;
			//System.out.println("nmsl");
		}
		int sum =0;
		for (Node n: e.children){
			if (n == StringSSet.NIL)
				break;
			sum += traverse(n);
		}
		return sum + i;
	}

	public boolean remove(String x){
		return false;
	}

	protected Node findNode(String x){
		Node n = root;
		while(n!= StringSSet.NIL) {
			if (n.data[0] == x || n.data[1] == x || n.data[2] == x || n.children[0] == StringSSet.NIL)//reach leaf or find x
				return n;
			else {
				if (x.compareTo(n.data[0])<0)
					n = n.children[0];
				else if ((n.data[1] == StringSSet.EMPTY ||x.compareTo(n.data[1])<0))
					n = n.children[1];
				else if (n.data[2] == StringSSet.EMPTY || x.compareTo(n.data[2])<0)
					n = n.children[2];
				else if (x.compareTo(n.data[2])>0){
					n = n.children[3];
				}
			}
		}
		//System.out.println("nmsl");
		return n;
	}

	public String find(String x){
		Node n = root;
		String prev = null;
		if (root.data[0] == StringSSet.EMPTY)return null;
		while(n!= StringSSet.NIL) {
			if (n.data[0] == x || n.data[1] == x || n.data[2] == x)//reach leaf or find x
				return x;
			else {
				if(x.compareTo(n.data[0])<=0){
					prev = n.data[0];
					n = n.children[0];
				}
				else if(n.data[1]==StringSSet.EMPTY){
					n = n.children[1];
				}
				else if(x.compareTo(n.data[1])<=0){
					prev = n.data[1];
					n = n.children[1];
				}
				else if(n.data[2]==StringSSet.EMPTY){
					n = n.children[2];
				}
				else if(x.compareTo(n.data[2])<=0){
					prev = n.data[2];
					n = n.children[2];
				}
				else{
					n = n.children[3];
				}
				/*
				if (x.compareTo(n.data[0])<0){
					prev = n.data[0];
					n = n.children[0];
				}
				else if ((n.data[1] == StringSSet.EMPTY ||x.compareTo(n.data[1])<0)) {
					prev = n.data[1];
					n = n.children[1];
				}
				else if (n.data[2] == StringSSet.EMPTY || x.compareTo(n.data[2])<0) {
					prev = n.data[2];
					n = n.children[2];
				}
				else if (x.compareTo(n.data[2])>0){
					n = n.children[3];
				}
				if (n.children[0] == StringSSet.NIL){// leaf!
					if (x.compareTo(n.data[0])<0){
						return n.data[0];
					}else if (n.data[1] == StringSSet.EMPTY ||x.compareTo(n.data[1])<=0){
						if (n.data[1]==StringSSet.EMPTY){
							return prev;
						}
						else{
							return n.data[1];
						}
					}else if (n.data[2] == StringSSet.EMPTY ||x.compareTo(n.data[2])<=0){
						if (n.data[2]==StringSSet.EMPTY){
							return prev;
						}
						else{
							return n.data[2];
						}
					}else{
						if (prev == StringSSet.EMPTY)
							return null;
					}
				}
				*/
			}
		}
		//if (prev == StringSSet.EMPTY) return null;
		return prev;
	}
	/*
	public String goParent(Node parent,Node child, String x){
		int i =0;
		for (Node n : parent.children){
			if (n == child || n == StringSSet.NIL)break;
			i++;
		}
		if (i == 4){
			if (parent == root)return null;
			goParent(parent.parent,parent,x);

		}
		return parent.data[i];
	}
	*/

	public boolean add(String x){
		//System.out.println(find(x));
		Node n = root;
		String prev = null;
		while(n.children[0]!= StringSSet.NIL) {
			if (n.data[0] == x || n.data[1] == x || n.data[2] == x)//reach leaf or find x
				return false;
			else {
				if(x.compareTo(n.data[0])<0){
					prev = n.data[0];
					n = n.children[0];
				}
				else if(n.data[1]==StringSSet.EMPTY){
					n = n.children[1];
				}
				else if(x.compareTo(n.data[1])<0){
					prev = n.data[1];
					n = n.children[1];
				}
				else if(n.data[2]==StringSSet.EMPTY){
					n = n.children[2];
				}
				else if(x.compareTo(n.data[2])<0){
					prev = n.data[2];
					n = n.children[2];
				}
				else{
					n = n.children[3];
				}
			}
		}
		if (n.data[0] == x || n.data[1] == x || n.data[2] == x)//reach leaf or find x
			return false;

		//System.out.println(n.data[0]);
		if (n.data[0] == StringSSet.EMPTY){//no data
			n.data[0] = x;
			//System.out.println("Hellow");
		}else if (n.data[1] == StringSSet.EMPTY){// one data
			if (x.compareTo( n.data[0])<0){
				n.data[1] = n.data[0];
				n.data[0] = x;
			}else {
				n.data[1] = x;
			}
		}else if (n.data[2] == StringSSet.EMPTY){//two data
			if (x.compareTo( n.data[0])<0){
				n.data[2] = n.data[1];
				n.data[1] = n.data[0];
				n.data[0] = x;
			}else if (x.compareTo( n.data[0])>0 && x.compareTo( n.data[1])<0){
				n.data[2] = n.data[1];
				n.data[1] = x;
			}else{
				n.data[2] = x;
			}
		}else {//overflow!
			ArrayList<String> temp = new ArrayList<String>();
			//String[] temperate = new String[4];

			temp.add(x);
			for (String a: n.data){
				temp.add(a);
			}

			Collections.sort(temp);
			Node right = new Node();//new node with nil child
			right.data[0] = temp.get(3);
			String popUp = temp.get(2);
			for (int i =0; i < temp.size()-1;i++){
				n.data[i] = temp.get(i);
			}
			n.data[2] = StringSSet.EMPTY;
			//temp =null;//clear
			popToParent(popUp,n.parent,n,right);

		}
		return true;
	}

	public void popToParent(String x, Node parent, Node l,Node r){
		if (parent == StringSSet.NIL){//nil parent?
			//System.out.println("new root");
			Node newRoot = new Node();
			root = newRoot;
			newRoot.data[0] = x;
			//System.out.println("the new root is " + x);
			//System.out.println("the new root right is " + r.data[0]);
			newRoot.children[0] = l;
			newRoot.children[1] = r;
			r.parent = newRoot;
			l.parent = newRoot;

			//System.out.println("parent's right node is "+r.data[0]);
		}else if (parent.data[2]!= StringSSet.EMPTY){//overflow!
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(x);

			//sSystem.out.println("overflow");

			for (String a: parent.data){
				temp.add(a);
			}
			Collections.sort(temp);
			Node newRightNode = new Node();
			if (temp.indexOf(x) == 0){//1 st
				newRightNode.children[1] = parent.children[3];
				parent.children[3].parent = newRightNode;

				//System.out.println(parent.children[3]);

				newRightNode.children[0] = parent.children[2];//set child to new node
				parent.children[2].parent = newRightNode;

				parent.children[3] = StringSSet.NIL;// clear the child
				parent.children[2] = parent.children[1];
				parent.children[1] = r;
				parent.children[0] = l;
				r.parent = parent;
				//l.parent = parent;
			}else if (temp.indexOf(x) == 1){// 2nd
				newRightNode.children[1] = parent.children[3];
				parent.children[3].parent = newRightNode;

				//System.out.println(parent.children[3]);

				newRightNode.children[0] = parent.children[2];
				parent.children[2].parent = newRightNode;
				//set child to new node
				parent.children[3] = StringSSet.NIL;
				parent.children[1] = l;
				parent.children[2] = r;
				r.parent = parent;
				//l.parent = parent;
			}else if (temp.indexOf(x) == 2) {// 3rd
				newRightNode.children[0] = r;
				r.parent = newRightNode;
				newRightNode.children[1] = parent.children[3];
				parent.children[3].parent = newRightNode;

				//System.out.println(parent.children[3].data[0]);

				parent.children[2] = l;
				l.parent = parent;
				parent.children[3] = StringSSet.NIL;

			}else {//4th
				newRightNode.children[0] = l;
				newRightNode.children[1] = r;
				l.parent = newRightNode;
				r.parent = newRightNode;

				//System.out.println(parent.children[3]);

				parent.children[3] = StringSSet.NIL;
			}
			//System.out.println(newRightNode.data[0]);
			String popUp = temp.get(2);
			newRightNode.data[0] =  temp.get(3);
			//newRightNode.parent = parent.parent;
			for (int i =0; i < temp.size()-1;i++){
				parent.data[i] = temp.get(i);
			}
			parent.data[2] = StringSSet.EMPTY;
			//temp =null;//clear
			popToParent(popUp,parent.parent,parent,newRightNode);//recursion!

		} else if (parent.data[1] != StringSSet.EMPTY){// two elements
			//System.out.println("2");
			if (x.compareTo(parent.data[0]) < 0) {//from left
				parent.data[2] = parent.data[1];
				parent.data[1] = parent.data[0];
				parent.data[0] = x;
				parent.children[3] = parent.children[2];
				parent.children[2] = parent.children[1];
				parent.children[0] = l;
				parent.children[1] = r;
			}else if (0<x.compareTo(parent.data[0]) &&x.compareTo(parent.data[1]) <0){//2nd
				parent.data[2] = parent.data[1];
				parent.data[1] = x;
				parent.children[3] = parent.children[2];
				parent.children[2] = r;
				parent.children[1] = l;
			}else{// from right
				parent.data[2] = x;
				parent.children[2] = l;
				parent.children[3] = r;
			}
			r.parent = parent;
		}
		else{// no overflow 1
			//System.out.println("1");
			if (x.compareTo(parent.data[0]) < 0){//from left
				parent.data[1] = parent.data[0];
				parent.data[0] = x;
				parent.children[2] = parent.children[1];
				parent.children[0] = l;
				parent.children[1] = r;
				//r.parent = parent;
			}else{
				parent.data[1] = x;
				parent.children[1] = l;
				parent.children[2] = r;
				//r.parent = parent;
			}
			r.parent = parent;
		}

		//overflow?
		//not overflow?
	}
	protected int sizeOfData(String[] array){
		if(array[0] == StringSSet.EMPTY){
			return 0;
		}else if (array[1] == StringSSet.EMPTY)return 1;
		else if (array[2] == StringSSet.EMPTY)return 2;
		else if (array[2]!= StringSSet.EMPTY)return 3;
		System.out.println("error");
		return 0;
	}
	public void clear(){
		root = new Node();
	}
	protected void printList(){

	}
}
