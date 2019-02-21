

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.StrictMath.abs;

public class node {
    public ArrayList<Integer> state;
    public node parent = null;
    public Integer parentAction = 0;
    public int gVal; //#moves so far
    public int hManhattan;
    public int hDisplaced; //# displaced tiles
    public int gPlusManhattan;
    public int gPlusHemming;
    public int hemmingPlusManhattan;
    public ArrayList<node> children;
    public ArrayList <Integer> goal=new ArrayList<>(Arrays.asList(1,2,3,8,0,4,7,6,5));

    boolean equalstate() {
       for(int i=0;i<9;i++){
           if(goal.get(i)!= state.get(i)){
               return false;
           }
       }
        return true;
    }

    public node() {
        state = new ArrayList<>();
        children = new ArrayList<>();
    }
    public node(node n1) {
        state=new ArrayList<>(n1.state);
        parent=n1.parent;
        parentAction=n1.parentAction;
        gVal=n1.gVal;
        hManhattan=n1.hManhattan;
        hDisplaced=n1.hDisplaced;
        gPlusManhattan=n1.gPlusManhattan;
        gPlusHemming=n1.gPlusHemming;
        hemmingPlusManhattan=n1.hemmingPlusManhattan;
        children=new ArrayList<>(n1.children);
        goal=n1.goal;
    }
    public node(ArrayList<Integer> state) {
        this.state = state;
        parent = null;
        parentAction = 0;
        gVal=0;
        children = new ArrayList<>();
    }

    void expand(node parent, ArrayList<Integer> prevState) {
        ArrayList<Integer> tempState;
        node tempNode = new node();
        int position = prevState.indexOf(0);
        switch (position) {
            case 0://0 located in first position, can go down,right
                moveRight(prevState, position,parent);
                moveDown(prevState, position,parent);
                break;
            case 1://0 located in second position, can go down,right,left
                moveDown(prevState, position,parent);
                moveLeft(prevState, position,parent);
                moveRight(prevState, position,parent);
                break;
            case 2://0 located in third position, can go down,left
                moveDown(prevState, position,parent);
                moveLeft(prevState, position,parent);
                break;
            case 3://0 located in fourth position, can go down,right,up
                moveDown(prevState, position,parent);
                moveRight(prevState, position,parent);
                moveUp(prevState, position,parent);
                break;
            case 4://0 located in fourth position, can go down,right,up
                moveDown(prevState, position,parent);
                moveRight(prevState, position,parent);
                moveUp(prevState, position,parent);
                moveLeft(prevState, position,parent);
                break;
            case 5://0 located in fourth position, can go down,right,up
                moveDown(prevState, position,parent);
                moveUp(prevState, position,parent);
                moveLeft(prevState, position,parent);
                break;
            case 6://0 located in fourth position, can go down,right,up
                moveUp(prevState, position,parent);
                moveRight(prevState, position,parent);
                break;
            case 7://0 located in fourth position, can go down,right,up
                moveRight(prevState, position,parent);
                moveUp(prevState, position,parent);
                moveLeft(prevState, position,parent);
                break;
            case 8://0 located in fourth position, can go down,right,up
                moveUp(prevState, position,parent);
                moveLeft(prevState, position,parent);
                break;
        }
    }

    ArrayList<Integer> createArray(ArrayList<Integer> prevState) {
        ArrayList<Integer> tempState = new ArrayList<>();
        for (int i = 0; i < prevState.size(); i++) {
            tempState.add(prevState.get(i));
        }
        return tempState;
    }

    ArrayList<Integer> swapValues(ArrayList<Integer> tempList, int indexOf0, int indexOfSwap) {
        int tempValue = tempList.get(indexOfSwap);
        tempList.set(indexOf0, tempValue);
        tempList.set(indexOfSwap, 0);
        return tempList;
    }

    void moveLeft(ArrayList<Integer> prevVal, int index,node cp) {
        node tempNode = new node();
        tempNode.gVal=cp.gVal;
        ArrayList<Integer> tempState = createArray(prevVal);
        tempState = swapValues(tempState, index, index - 1);
        tempNode.state = tempState;
        node temp;
        tempNode.parent = cp;
        tempNode.parentAction = 4;
        tempNode.gVal=tempNode.gVal+1;
        hamming(tempNode);
        manhattan(tempNode);
        calculateHeuristic(tempNode);
        this.children.add(tempNode);
        return;
    }

    void moveRight(ArrayList<Integer> prevVal, int index, node cp) {
        node tempNode = new node();
        tempNode.gVal=cp.gVal;
        ArrayList<Integer> tempState = createArray(prevVal);
        tempState = swapValues(tempState, index, index + 1);
        tempNode.state = tempState;
        tempNode.parent = cp;
        tempNode.parentAction = 2;
        tempNode.gVal=tempNode.gVal+1;
        hamming(tempNode);
        manhattan(tempNode);
        calculateHeuristic(tempNode);
        this.children.add(tempNode);
        return;
    }

    void moveUp(ArrayList<Integer> prevVal, int index, node cp) {
        node tempNode = new node();
        tempNode.gVal=cp.gVal;
        ArrayList<Integer> tempState = createArray(prevVal);
        tempState = swapValues(tempState, index, index - 3);
        tempNode.state = tempState;
        tempNode.parent = cp;
        tempNode.parentAction = 1;
        tempNode.gVal=tempNode.gVal+1;
        hamming(tempNode);
        manhattan(tempNode);
        calculateHeuristic(tempNode);
        this.children.add(tempNode);
        return;
    }

    void moveDown(ArrayList<Integer> prevVal, int index,node cp) {
        node tempNode = new node();
        tempNode.gVal=cp.gVal;
        ArrayList<Integer> tempState = createArray(prevVal);
        tempState = swapValues(tempState, index, index + 3);
        tempNode.state = tempState;
        tempNode.parent = cp;
        tempNode.parentAction = 3;
        tempNode.gVal=tempNode.gVal+1;
        hamming(tempNode);
        manhattan(tempNode);
        calculateHeuristic(tempNode);
        this.children.add(tempNode);
        return;
    }

    void hamming(node state){
        int hamming=0;
        for(int i=0;i<9;i++){
            if(!(state.state.get(i).equals(goal.get(i)))){
                hamming++;
            }
        }
        state.hDisplaced=hamming;
    }

    void manhattan(node state){
        int manhattan=0;
        for(int i=0;i<9;i++){
          int position=state.state.indexOf(i);
          int goalPosition=goal.indexOf(i);
          int difference=abs(position-goalPosition);
          switch(difference){
              case 0:
                  manhattan=manhattan+0;
                  break;
              case 1:
                  manhattan++;
                  break;
              case 2:
                  manhattan=manhattan+2;
                  break;
              case 3:
                  manhattan++;
                  break;
              case 4:
                  manhattan=manhattan+2;
                  break;
              case 5:
                  manhattan=manhattan+3;
                  break;
              case 6:
                  manhattan=manhattan+2;
                  break;
              case 7:
                  manhattan=manhattan+3;
                  break;
              case 8:
                  manhattan=manhattan+4;
                  break;
          }
        }
        state.hManhattan=manhattan;
    }

    void calculateHeuristic(node tempNode){
        tempNode.gPlusHemming=tempNode.gVal+tempNode.hDisplaced;
        tempNode.gPlusManhattan=tempNode.gVal+tempNode.hManhattan;
        tempNode.hemmingPlusManhattan=tempNode.hManhattan+tempNode.hDisplaced;
    }

    public ArrayList<Integer> getState() {
        return state;
    }

    public void setState(ArrayList<Integer> state) {
        this.state = state;
    }

    public node getParent() {
        return parent;
    }

    public void setParent(node parent) {
        this.parent = parent;
    }

    public Integer getParentAction() {
        return parentAction;
    }

    public void setParentAction(Integer parentAction) {
        this.parentAction = parentAction;
    }

    public int getgVal() {
        return gVal;
    }

    public void setgVal(int gVal) {
        this.gVal = gVal;
    }

    public int gethManhattan() {
        return hManhattan;
    }

    public void sethManhattan(int hManhattan) {
        this.hManhattan = hManhattan;
    }

    public int gethDisplaced() {
        return hDisplaced;
    }

    public void sethDisplaced(int hDisplaced) {
        this.hDisplaced = hDisplaced;
    }

    public int getgPlusManhattan() {
        return gPlusManhattan;
    }

    public void setgPlusManhattan(int gPlusManhattan) {
        this.gPlusManhattan = gPlusManhattan;
    }

    public int getgPlusHemming() {
        return gPlusHemming;
    }

    public void setgPlusHemming(int gPlusHemming) {
        this.gPlusHemming = gPlusHemming;
    }

    public ArrayList<node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<node> children) {
        this.children = children;
    }
    public void addChildren(node children1) {
        children1.parent=this;
        children.add(children1);
    }

    public ArrayList<Integer> getGoal() {
        return goal;
    }

    public void setGoal(ArrayList<Integer> goal) {
        this.goal = goal;
    }
}
