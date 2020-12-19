/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 
 */

//Kelas untuk menggabungkan heuristic dengan node
//Implements comparable agar elemen ter-sort
public class HeuristicNode extends Node implements Comparable<HeuristicNode> {
	
	private int f; //f(n) = g(n) + h(n)
	
	public HeuristicNode(Node node, int h) {
		super(node.getState(), node.getDepth(), node.getParent());
		this.f = node.getDepth() + h;	
	}

	/**
	 * Comparator
	 */
	@Override
	public int compareTo(HeuristicNode other) {
		if (this.f == other.f) {
			return 0;
		}
		
		if (this.f > other.f) {
			return 1;
		}
		
		return -1;
	}

	@Override
	public int hashCode() {
		return this.getState().hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		
		if (this == other) {
			return true;
		}
		
		
		if (other instanceof Node || other instanceof HeuristicNode) {
			return ((Node) other).getState().equals(this.getState());
		}
		
		return false;
	}

}
