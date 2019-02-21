import java.io.*;
import java.util.*;

@SuppressWarnings("ALL")
public class solver {

    public static void main(String[] args) throws IOException {
        ArrayList<Double> h1 = new ArrayList<>();
        ArrayList<Double> h2 = new ArrayList<>();
        ArrayList<Double> h3 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> puzzlesToSolve = new ArrayList<ArrayList<Integer>>(); //this is the arraylist of puzzles to be solved.
        ArrayList<node> expandedNodes1 = new ArrayList<>();
        ArrayList<node> expandedNodes2= new ArrayList<>();
        ArrayList<node> expandedNodes3 = new ArrayList<>();
        double h1Factor = 0.0;
        double h2Factor=0.0;
        double h3Factor=0.0;
        for (int j = 0; j < 15; j++) {
            ArrayList<Integer> blank = new ArrayList<>();
            puzzlesToSolve.add(blank);
        }
        File file = new File("src/input.txt");
        Scanner read = new Scanner(file);
        read.useDelimiter(",|\r\n|\n");
        int i = 0;
        int array = 0;
        while (read.hasNext()) {
            for (int j = 0; j < 9; j++) {
                puzzlesToSolve.get(array).add(Integer.parseInt(read.next()));

            }
            array++;
        }
        for (i = 0; i < 15; i++) {
            node node1 = new node(puzzlesToSolve.get(i));
           // node1.expand(node1, puzzlesToSolve.get(i)); //expand first node
            //TODO add solve puzzle function here
            //expandedNodes.add(node1);
            //sortNodesHemming(node1.children);
            expandedNodes1.add(solveByH1(node1, h1));
            node1 = new node(puzzlesToSolve.get(i));
            //node1.expand(node1, puzzlesToSolve.get(i)); //expand first node
            expandedNodes2.add(solveByH2(node1, h2));
            node1 = new node(puzzlesToSolve.get(i));
          //  node1.expand(node1, puzzlesToSolve.get(i)); //expand first node
            expandedNodes3.add(solveByH3(node1, h3));
        }
        System.out.println("Problem size 5");
        for(i=0;i<5;i++){
            h1Factor=h1Factor+h1.get(i).doubleValue();
            h2Factor=h2Factor+h2.get(i).doubleValue();
            h3Factor=h3Factor+h3.get(i).doubleValue();
        }
        h1Factor=h1Factor/3.0;
        h2Factor=h2Factor/3.0;
        h3Factor=h3Factor/3.0;
        System.out.println(h1Factor);
        System.out.println(h2Factor);
        System.out.println(h3Factor);
        h1Factor=0.0;
        h2Factor=0.0;
        h3Factor=0.0;
        System.out.println("Problem size 10");
        for(i=5;i<10;i++){
            h1Factor=h1Factor+h1.get(i).doubleValue();
            h2Factor=h2Factor+h2.get(i).doubleValue();
            h3Factor=h3Factor+h3.get(i).doubleValue();
        }

        h1Factor=h1Factor/3.0;
        h2Factor=h2Factor/3.0;
        h3Factor=h3Factor/3.0;
        System.out.println(h1Factor);
        System.out.println(h2Factor);
        System.out.println(h3Factor);
        h1Factor=0.0;
        h2Factor=0.0;
        h3Factor=0.0;
        System.out.println("Problem size 15");
        for(i=10;i<15;i++){
            h1Factor=h1Factor+h1.get(i).doubleValue();
            h2Factor=h2Factor+h2.get(i).doubleValue();
            h3Factor=h3Factor+h3.get(i).doubleValue();
        }
        h1Factor=h1Factor/3.0;
        h2Factor=h2Factor/3.0;
        h3Factor=h3Factor/3.0;
        System.out.println(h1Factor);
        System.out.println(h2Factor);
        System.out.println(h3Factor);
        h1Factor=0.0;
        h2Factor=0.0;
        h3Factor=0.0;



    }

    private static void printBoards(ArrayList<node> expandedNodes) {
        for (int i = 0; i < expandedNodes.size(); i++) {
            System.out.println("Original Position\n");
            System.out.println(expandedNodes.get(i).state.get(0) + " " + expandedNodes.get(i).state.get(1) + " " + expandedNodes.get(i).state.get(2));
            System.out.println(expandedNodes.get(i).state.get(3) + " " + expandedNodes.get(i).state.get(4) + " " + expandedNodes.get(i).state.get(5));
            System.out.println(expandedNodes.get(i).state.get(6) + " " + expandedNodes.get(i).state.get(7) + " " + expandedNodes.get(i).state.get(8));
            System.out.println("\n");
            for (int j = 0; j < expandedNodes.get(i).children.size(); j++) {
                System.out.println("Node " + j);
                System.out.println(("# Diplaced Tiles + gVal= " + expandedNodes.get(i).children.get(j).gPlusHemming));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(0) + " " + expandedNodes.get(i).children.get(j).state.get(1) + " " + expandedNodes.get(i).children.get(j).state.get(2));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(3) + " " + expandedNodes.get(i).children.get(j).state.get(4) + " " + expandedNodes.get(i).children.get(j).state.get(5));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(6) + " " + expandedNodes.get(i).children.get(j).state.get(7) + " " + expandedNodes.get(i).children.get(j).state.get(8));
                System.out.println("\n");
            }
        }
    }

    private static void sortNodesHemming(ArrayList<node> childrenNodes) {
        Collections.sort(childrenNodes, new SortbyrollHemming());
    }

    private static void sortNodesHemmingManhattan(ArrayList<node> childrenNodes) {
        Collections.sort(childrenNodes, new SortbyrollManhattanHemming());
    }

    static class SortbyrollHemming implements Comparator<node> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b) {
            return a.gPlusHemming - b.gPlusHemming;
        }
    }

    private static void sortNodesManhattan(ArrayList<node> childrenNodes) {
        Collections.sort(childrenNodes, new SortbyrollManhattan());
    }

    static class SortbyrollManhattan implements Comparator<node> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b) {
            return a.gPlusManhattan - b.gPlusManhattan;
        }
    }

    static class SortbyrollManhattanHemming implements Comparator<node> {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b) {
            return a.hemmingPlusManhattan - b.hemmingPlusManhattan;
        }
    }

    static void printHemmingToFile(ArrayList<node> expandedNodes) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter Writer = new PrintWriter("OutfileHeuristic1.txt", "UTF-8");
        sortNodesHemming(expandedNodes);

        for (int i = 0; i < expandedNodes.size(); i++) {
            Writer.println("Problem " + (i + 1));
            Writer.println("Original Position\n");
            Writer.println(expandedNodes.get(i).state.get(0) + " " + expandedNodes.get(i).state.get(1) + " " + expandedNodes.get(i).state.get(2));
            Writer.println(expandedNodes.get(i).state.get(3) + " " + expandedNodes.get(i).state.get(4) + " " + expandedNodes.get(i).state.get(5));
            Writer.println(expandedNodes.get(i).state.get(6) + " " + expandedNodes.get(i).state.get(7) + " " + expandedNodes.get(i).state.get(8));
            Writer.println();
            Writer.println("Sorted List");
            for (int j = 0; j < expandedNodes.get(i).children.size(); j++) {
                Writer.println("Node " + j);
                Writer.println(("# Diplaced Tiles + gVal= " + expandedNodes.get(i).children.get(j).gPlusHemming));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(0) + " " + expandedNodes.get(i).children.get(j).state.get(1) + " " + expandedNodes.get(i).children.get(j).state.get(2));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(3) + " " + expandedNodes.get(i).children.get(j).state.get(4) + " " + expandedNodes.get(i).children.get(j).state.get(5));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(6) + " " + expandedNodes.get(i).children.get(j).state.get(7) + " " + expandedNodes.get(i).children.get(j).state.get(8));
                Writer.println("\n");

            }
        }
        Writer.close();
    }

    static void printManhattanToFile(ArrayList<node> expandedNodes) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter Writer = new PrintWriter("OutfileHeuristic2.txt", "UTF-8");
        sortNodesManhattan(expandedNodes);
        for (int i = 0; i < expandedNodes.size(); i++) {
            Writer.println("Problem " + (i + 1));
            Writer.println("Original Position\n");
            Writer.println(expandedNodes.get(i).state.get(0) + " " + expandedNodes.get(i).state.get(1) + " " + expandedNodes.get(i).state.get(2));
            Writer.println(expandedNodes.get(i).state.get(3) + " " + expandedNodes.get(i).state.get(4) + " " + expandedNodes.get(i).state.get(5));
            Writer.println(expandedNodes.get(i).state.get(6) + " " + expandedNodes.get(i).state.get(7) + " " + expandedNodes.get(i).state.get(8));
            Writer.println();
            Writer.println("Sorted List");
            for (int j = 0; j < expandedNodes.get(i).children.size(); j++) {
                Writer.println("Node " + j);
                Writer.println(("hManhattan + gVal= " + expandedNodes.get(i).children.get(j).gPlusManhattan));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(0) + " " + expandedNodes.get(i).children.get(j).state.get(1) + " " + expandedNodes.get(i).children.get(j).state.get(2));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(3) + " " + expandedNodes.get(i).children.get(j).state.get(4) + " " + expandedNodes.get(i).children.get(j).state.get(5));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(6) + " " + expandedNodes.get(i).children.get(j).state.get(7) + " " + expandedNodes.get(i).children.get(j).state.get(8));
                Writer.println("\n");

            }
        }
        Writer.close();
    }

    static node solveByH1(node puzzleToSolve, ArrayList<Double> h1) {
        boolean goalReached = false;
        int graphIndex;
        int depth = 0;
        int iteration=0;
        node temporaryNode = new node();
        node correctParent = puzzleToSolve;
        node graph = puzzleToSolve;
        ArrayList<node> closed = new ArrayList<>();
        ArrayList<node> open = new ArrayList<>();
        ArrayList<node> children = new ArrayList<>();
        open.add(correctParent);
        while (open.size() != 0) {
            temporaryNode = new node(open.get(0));//copy the node so we don't modify the original in the loop
            correctParent = open.get(0); //stores the actual object in correctParent
            temporaryNode.parent = correctParent.parent;//set the parent correctly so we can find it later;
            closed.add(open.get(0));
            open.remove(0);
            if (temporaryNode.gVal > depth) {
                depth = temporaryNode.gVal;
            }
            if (temporaryNode.equalstate()) {
                break;
            } else {
                temporaryNode.expand(correctParent, temporaryNode.state);
                children = temporaryNode.children;
                correctParent.children = temporaryNode.children;
                for (int i = 0; i < children.size(); i++) {
                    if (!checkOpen(children.get(i), open) && !checkClosed(children.get(i), closed)) {
                        open.add(children.get(i));
                    }
                }
            }
            iteration ++;
            sortNodesHemming(open);
        }
        h1.add(Math.pow((double) (closed.size()+open.size()), (double) (1.0 / depth)));
        return graph;

    }

    static node solveByH2(node puzzleToSolve, ArrayList<Double> h1) {
        boolean goalReached = false;
        int graphIndex;
        int depth = 0;
        int iteration=0;
        node temporaryNode = new node();
        node correctParent = puzzleToSolve;
        node graph = puzzleToSolve;
        ArrayList<node> closed = new ArrayList<>();
        ArrayList<node> open = new ArrayList<>();
        ArrayList<node> children = new ArrayList<>();
        open.add(correctParent);
        while (open.size() != 0) {
            temporaryNode = new node(open.get(0));//copy the node so we don't modify the original in the loop
            correctParent = open.get(0); //stores the actual object in correctParent
            temporaryNode.parent = correctParent.parent;//set the parent correctly so we can find it later;
            closed.add(open.get(0));
            open.remove(0);
            if (temporaryNode.gVal > depth) {
                depth = temporaryNode.gVal;
            }
            if (temporaryNode.equalstate()) {
                break;
            } else {
                temporaryNode.expand(correctParent, temporaryNode.state);
                children = temporaryNode.children;
                correctParent.children = temporaryNode.children;
                for (int i = 0; i < children.size(); i++) {
                    if (!checkOpen(children.get(i), open) && !checkClosed(children.get(i), closed)) {
                        open.add(children.get(i));
                    }
                }
            }
            iteration++;
            sortNodesManhattan(open);
        }
        double oneOver = 1.0 / depth;
        h1.add(Math.pow((double) (closed.size()+open.size()), (1.0 / depth)));
        return graph;

    }

    static node solveByH3(node puzzleToSolve, ArrayList<Double> h1) {
        boolean goalReached = false;
        int graphIndex;
        int depth = 0;
        int iteration=0;
        node temporaryNode = new node();
        node correctParent = puzzleToSolve;
        node graph = puzzleToSolve;
        ArrayList<node> closed = new ArrayList<>();
        ArrayList<node> open = new ArrayList<>();
        ArrayList<node> children = new ArrayList<>();
        open.add(correctParent);
        while (open.size() != 0) {
            temporaryNode = new node(open.get(0));//copy the node so we don't modify the original in the loop
            correctParent = open.get(0); //stores the actual object in correctParent
            temporaryNode.parent = correctParent.parent;//set the parent correctly so we can find it later;
            closed.add(open.get(0));
            open.remove(0);
            if (temporaryNode.gVal > depth) {
                depth = temporaryNode.gVal;
            }
            if (temporaryNode.equalstate()) {
                break;
            } else {
                temporaryNode.expand(correctParent, temporaryNode.state);
                children = temporaryNode.children;
                correctParent.children = temporaryNode.children;
                for (int i = 0; i < children.size(); i++) {
                    if (!checkOpen(children.get(i), open) && !checkClosed(children.get(i), closed)) {
                        open.add(children.get(i));
                    }
                }
            }
            iteration++;
            sortNodesHemmingManhattan(open);
        }
        h1.add(Math.pow((double) (closed.size()+open.size()), 1.0 / depth));
        return graph;

    }

    static boolean checkClosed(node n1, ArrayList<node> closed) {
        for (int i = 0; i < closed.size(); i++) {
            if (n1.state.equals(closed.get(i).state)) {
                return true; //exists
            }
        }
        return false;
    }

    static boolean checkOpen(node n1, ArrayList<node> open) {
        for (int i = 0; i < open.size(); i++) {
            for (int j = 0; j < open.size(); j++) {
                if (n1.state.equals(open.get(j).state)) {
                    return true; //exists
                }
            }
        }
        return false;
    }

}

