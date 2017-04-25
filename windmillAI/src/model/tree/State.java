/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tree;

import agentsController.Player;
import java.util.LinkedList;
import model.Board;

/**
 *
 * @author Camilo
 */
public class State {
    Board board;
    LinkedList<State> children;

    public State() {
    }

    public State(Board board, LinkedList<State> children) {
        this.board = board;
        this.children = children;
    }

    public Board getBoard() {
        return board;
    }

    public LinkedList<State> getChildren() {
        return children;
    }
}
