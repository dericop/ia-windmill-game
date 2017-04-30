/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tree;

import java.util.LinkedList;
import model.Board;

/**
 *
 * @author Camilo
 */
public class State {

    Board board;
    State parent;
    LinkedList<State> children;
    String id;
    int depth;
    int heuristic;
    double alpha = Double.NEGATIVE_INFINITY;
    double beta = Double.POSITIVE_INFINITY;
    private boolean isLoserNode = false;

    public State() {
    }

    public State(String id, State parent, Board board, LinkedList<State> children, int depth) {
        this.board = board;
        this.children = children;
        this.id = id;
        this.parent = parent;
        this.depth = depth;
        this.heuristic = 0;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getHeuristic() {
        return heuristic;
    }
    

    public Board getBoard() {
        return board;
    }

    public LinkedList<State> getChildren() {
        return children;
    }

    public State getParent() {
        return parent;
    }
    public void addchild(State child){
        this.children.addLast(child);
    }

    @Override
    public String toString() {
        return id+" : "+ heuristic;
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public int getDepth() {
        return depth;
    }
    
    public void setIsLoserNode(boolean isLoserNode) {
        this.isLoserNode = isLoserNode;
    }

    public boolean isIsLoserNode() {
        return isLoserNode;
    }
    
    
    
}
