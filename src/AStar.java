
import java.util.ArrayList;
import java.util.List;

/**
 * This is the template for a class that performs A* search on a given
 * rush hour puzzle with a given heuristic.  The main search
 * computation is carried out by the constructor for this class, which
 * must be filled in.  The solution (a path from the initial state to
 * a goal state) is returned as an array of <tt>State</tt>s called
 * <tt>path</tt> (where the first element <tt>path[0]</tt> is the
 * initial state).  If no solution is found, the <tt>path</tt> field
 * should be set to <tt>null</tt>.  You may also wish to return other
 * information by adding additional fields to the class.
 */
public class AStar {

    public State[] path; //Variabel untuk menyimpan path menuju solusi
    
    private SortableList<HeuristicNode> open = new SortableList<HeuristicNode>(); //List untuk menyimpan node open
    private List<HeuristicNode> closed = new ArrayList<HeuristicNode>(); //List untuk menyimpan node closed

    /**
     * This is the constructor that performs A* search to compute a
     * solution for the given puzzle using the given heuristic.
     */
    public AStar(Puzzle puzzle, Heuristic heuristic) {
    	int h = heuristic.getValue(puzzle.getInitNode().getState()); //Mengambil nilai heuristic dari node
    	HeuristicNode root = new HeuristicNode(puzzle.getInitNode(), h); //Inisialisasi node root dengan cost heuristic dan path
    	
    	open.add(root);	//Menambahkan node root ke open list
    	
    	while(!open.isEmpty()) { //Hanya dijalankan ketika list open sudah diisi
            
            //sort isi dari list node open
            open.sort();
            
            HeuristicNode current = open.remove(0); //Current node diambil dari list open
    		
            if (current.getState().isGoal()) {
                //Jika state node current adalah goal, maka depth menuju current disimpan
                //dengan mengambil depth current node dan +1 untuk memasukkan node root
                path = new State[current.getDepth() + 1];
    			
                //Current node diisi ke path node
                Node pathNode = current;
    			
                //Mengambil state dari semua node yang ada di path dan disimpan ke array path
                //Untuk mendapatkan path, current node (path node) digantikan dengan parentnya
                //hingga parent dari current bernilai null
                while (pathNode != null) {
                    path[pathNode.getDepth()] = pathNode.getState();
                    pathNode = (HeuristicNode) pathNode.getParent();
                } 
    			
                //Solusi ditemukan
                return;
            }
    		
            closed.add(current); //Node current ditutup
    		
            //Jika current bukan goal, maka node di-expand
            for (Node successor : current.expand()) {

                h = heuristic.getValue(successor.getState()); //Mengambil value heuristik untuk successor
                HeuristicNode hSuccessor = new HeuristicNode(successor, h); //Membuat node successor
    			
                if (open.contains(hSuccessor)) {
                    keepBetterNodeOnOpenList(hSuccessor);
                } else if (!closed.contains(hSuccessor)) {
                    open.add(hSuccessor);
                }
            }
    	}
    }
    
    // Idea from: http://web.mit.edu/eranki/www/tutorials/search/
      private void keepBetterNodeOnOpenList(HeuristicNode successor) {
          HeuristicNode existing = open.get(open.get(successor));
    	
    	if (existing != null) {
            if (existing.compareTo(successor) > 0) { //Jika existing > dari successor maka ambil successornya
                open.remove(existing);
                open.add(successor);
            }
    	}
    }
}