/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Collections;
import java.util.LinkedList;
import model.tree.State;
import model.tree.Tree;

/**
 *
 * @author Dericop
 */
public class MinimaxPodaAB {

    public Line calculateMove(Board board) {
        return null;
    }

    public Board getNewAssign(Board board) {
        return null;
    }

    public State getMovement(Tree treeOfStates, boolean isMax) {
        LinkedList<State> children = treeOfStates.getRoot().getChildren();
        
        
        double heuristic = Double.NEGATIVE_INFINITY;
        State state = null;

        for (State s : children) {
            if (isMax) {
                if (s.getHeuristic() > heuristic) {
                    heuristic = s.getHeuristic();
                    state = s;
                }
            } else {

                if (s.getHeuristic() < heuristic) {
                    heuristic = s.getHeuristic();
                    state = s;
                }
            }
        }
        
        return state;
    }
    
    private LinkedList<Line> getLinesModified(State parent, State child){
        LinkedList<Line> modifiedLines = new LinkedList();
        LinkedList<Line> parentLines = parent.getBoard().getLines();
        
        for(Line cLine: child.getBoard().getLines()){
            if(!parentLines.contains(cLine)){
              modifiedLines.add(cLine);
            }
        }
        
        return modifiedLines;
    }

    public int calculateHeuristic(State current, String player) {
        int heuristic = 0;
        int mills = 0;
        int possibleMills = 0;
        for (Line line : current.getBoard().getLines()) {
            if (line.isComplete(player)) {
                mills++;
            } else {
                possibleMills += line.getPlayerCountInLine(player) == 2 ? 1 : 0;
            }
        }
        heuristic = mills + possibleMills;

        return heuristic;
    }

    private boolean isMax(State child) {
        if (child == null) {
            return true;
        }

        boolean isMax = false;
        if (child.getDepth() % 2 != 0) {
            isMax = true;
        }
        return isMax;
    }

    public boolean miniMaxOfState(State child) {

        boolean canBound = false;
        int heuristic = child.getHeuristic();
        State parent = child.getParent();

        boolean isMax = isMax(child.getParent());

        if (parent != null) {
            double alpha = parent.getAlpha();
            double beta = parent.getBeta();

            if (isMax) {

                if (heuristic >= beta) {
                    canBound = true;
                }

                if (heuristic > alpha) {
                    parent.setAlpha(heuristic);
                }

            } else {

                if (heuristic <= alpha) {
                    canBound = true;
                }

                if (heuristic < beta) {
                    parent.setBeta(heuristic);
                }
            }
        }

        return canBound;
    }

}
