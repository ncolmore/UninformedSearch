import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class solver {

    public static void main(String[] args) throws IOException {

        ArrayList<ArrayList<Integer>> puzzlesToSolve = new ArrayList<ArrayList<Integer>>(); //this is the arraylist of puzzles to be solved.
        ArrayList<node> expandedNodes=new ArrayList<>();
        for(int j=0;j<5;j++){
            ArrayList<Integer> blank=new ArrayList<>();
            puzzlesToSolve.add(blank);
        }
        File file = new File("src/input.txt");
        Scanner read = new Scanner(file);
        read.useDelimiter(",|\r\n");
        int i = 0;
        int array = 0;
        while (read.hasNext()) {
            for(int j=0;j<9;j++){
                puzzlesToSolve.get(array).add(Integer.parseInt(read.next()));

            }
            array++;
        }
        for(i=0;i<5;i++){
            node node1=new node(puzzlesToSolve.get(i));
            node1.expand(node1,puzzlesToSolve.get(i)); //expand first node
            //TODO add solve puzzle function here
            expandedNodes.add(node1);
            sortNodesHemming(node1.children);
            solveByH1(node1);
        }
        solver.printBoards(expandedNodes);
        printHemmingToFile(expandedNodes);
        printManhattanToFile(expandedNodes);


    }

    private static void printBoards(ArrayList<node> expandedNodes) {
        for(int i=0;i<expandedNodes.size();i++){
            System.out.println("Original Position\n");
            System.out.println(expandedNodes.get(i).state.get(0)+" "+expandedNodes.get(i).state.get(1)+" "+expandedNodes.get(i).state.get(2));
            System.out.println(expandedNodes.get(i).state.get(3)+" "+expandedNodes.get(i).state.get(4)+" "+expandedNodes.get(i).state.get(5));
            System.out.println(expandedNodes.get(i).state.get(6)+" "+expandedNodes.get(i).state.get(7)+" "+expandedNodes.get(i).state.get(8));
            System.out.println("\n");
            for(int j=0;j<expandedNodes.get(i).children.size();j++){
                System.out.println("Node "+ j);
                System.out.println(("# Diplaced Tiles + gVal= "+expandedNodes.get(i).children.get(j).gPlusHemming));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(0)+" "+expandedNodes.get(i).children.get(j).state.get(1)+" "+expandedNodes.get(i).children.get(j).state.get(2));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(3)+" "+expandedNodes.get(i).children.get(j).state.get(4)+" "+expandedNodes.get(i).children.get(j).state.get(5));
                System.out.println(expandedNodes.get(i).children.get(j).state.get(6)+" "+expandedNodes.get(i).children.get(j).state.get(7)+" "+expandedNodes.get(i).children.get(j).state.get(8));
                System.out.println("\n");
            }
        }
    }

    private static void sortNodesHemming(ArrayList<node> childrenNodes){
        Collections.sort(childrenNodes, new SortbyrollHemming());
    }
    private static void sortNodesHemmingManhattan(ArrayList<node> childrenNodes){
        Collections.sort(childrenNodes, new SortbyrollManhattanHemming());
    }
    static class SortbyrollHemming implements Comparator<node>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b)
        {
            return a.gPlusHemming - b.gPlusHemming;
        }
    }

    private static void sortNodesManhattan(ArrayList<node> childrenNodes){
        Collections.sort(childrenNodes, new SortbyrollManhattan());
    }
    static class SortbyrollManhattan implements Comparator<node>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b)
        {
            return a.gPlusManhattan - b.gPlusManhattan;
        }
    }
    static class SortbyrollManhattanHemming implements Comparator<node>
    {
        // Used for sorting in ascending order of
        // roll number
        public int compare(node a, node b)
        {
            return a.hemmingPlusManhattan - b.hemmingPlusManhattan;
        }
    }
    static void printHemmingToFile(ArrayList<node> expandedNodes) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter Writer = new PrintWriter("OutfileHeuristic1.txt", "UTF-8");
        sortNodesHemming(expandedNodes);

        for(int i=0;i<expandedNodes.size();i++){
            Writer.println("Problem "+ (i+1) );
            Writer.println("Original Position\n");
            Writer.println(expandedNodes.get(i).state.get(0)+" "+expandedNodes.get(i).state.get(1)+" "+expandedNodes.get(i).state.get(2));
            Writer.println(expandedNodes.get(i).state.get(3)+" "+expandedNodes.get(i).state.get(4)+" "+expandedNodes.get(i).state.get(5));
            Writer.println(expandedNodes.get(i).state.get(6)+" "+expandedNodes.get(i).state.get(7)+" "+expandedNodes.get(i).state.get(8));
            Writer.println();
            Writer.println("Sorted List");
            for(int j=0;j<expandedNodes.get(i).children.size();j++){
                Writer.println("Node "+ j);
                Writer.println(("# Diplaced Tiles + gVal= "+expandedNodes.get(i).children.get(j).gPlusHemming));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(0)+" "+expandedNodes.get(i).children.get(j).state.get(1)+" "+expandedNodes.get(i).children.get(j).state.get(2));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(3)+" "+expandedNodes.get(i).children.get(j).state.get(4)+" "+expandedNodes.get(i).children.get(j).state.get(5));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(6)+" "+expandedNodes.get(i).children.get(j).state.get(7)+" "+expandedNodes.get(i).children.get(j).state.get(8));
                Writer.println("\n");

            }
        }
        Writer.close();
    }
    static void printManhattanToFile(ArrayList<node> expandedNodes) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter Writer = new PrintWriter("OutfileHeuristic2.txt", "UTF-8");
        sortNodesManhattan(expandedNodes);
        for(int i=0;i<expandedNodes.size();i++){
            Writer.println("Problem "+ (i+1) );
            Writer.println("Original Position\n");
            Writer.println(expandedNodes.get(i).state.get(0)+" "+expandedNodes.get(i).state.get(1)+" "+expandedNodes.get(i).state.get(2));
            Writer.println(expandedNodes.get(i).state.get(3)+" "+expandedNodes.get(i).state.get(4)+" "+expandedNodes.get(i).state.get(5));
            Writer.println(expandedNodes.get(i).state.get(6)+" "+expandedNodes.get(i).state.get(7)+" "+expandedNodes.get(i).state.get(8));
            Writer.println();
            Writer.println("Sorted List");
            for(int j=0;j<expandedNodes.get(i).children.size();j++){
                Writer.println("Node "+ j);
                Writer.println(("hManhattan + gVal= "+expandedNodes.get(i).children.get(j).gPlusManhattan));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(0)+" "+expandedNodes.get(i).children.get(j).state.get(1)+" "+expandedNodes.get(i).children.get(j).state.get(2));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(3)+" "+expandedNodes.get(i).children.get(j).state.get(4)+" "+expandedNodes.get(i).children.get(j).state.get(5));
                Writer.println(expandedNodes.get(i).children.get(j).state.get(6)+" "+expandedNodes.get(i).children.get(j).state.get(7)+" "+expandedNodes.get(i).children.get(j).state.get(8));
                Writer.println("\n");

            }
        }
        Writer.close();
    }

    static node solveByH1(node puzzleToSolve) {
        boolean goalReached = false;
        ArrayList<node> expandedNodes=new ArrayList<>();
        node node1 = new node();
        ArrayList<node> graph = new ArrayList<>();
        ArrayList<node> closed = new ArrayList<>();
        ArrayList<node> open = new ArrayList<>();
        open.add(puzzleToSolve);
        graph.add(puzzleToSolve);
        while (open.size() != 0 && goalReached == false) {
            node1 = open.get(0);
            closed.add(node1);
            open.remove(0);
            goalReached = node1.equalstate();
            if (goalReached == true) {
                return node1;

            }
            node1.expand(node1, node1.state);
            for(int i=0;i<node1.children.size();i++){
                node tempNode=new node(node1.children.get(i));
               int index=graph.indexOf(tempNode.parent);
               graph.get(index).children.add(tempNode);
            }
            System.out.println();

        }
        return node1;

    }
    static void solveByH2(ArrayList<node> puzzleToSolve){

    }
    static void solveByH3(ArrayList<node> puzzleToSolve){

    }

}

