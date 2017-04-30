/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Dericop
 */
public class Node implements Serializable {

    private static final long serialVersionUID = 879965L;
    private final int id;
    private String mCurrentPlayer;
    private final LinkedList<Node> adjacents;

    public Node(int id) {
        this.id = id;
        adjacents = new LinkedList();
        mCurrentPlayer = "";
    }

    public String getmCurrentPlayer() {
        return mCurrentPlayer;
    }
    

    public LinkedList<Node> getAdjacents() {
        return adjacents;
    }

    public int getId() {
        return id;
    }

    public void setmCurrentPlayer(String mCurrentPlayer) {
        this.mCurrentPlayer = mCurrentPlayer;
    }

    public void attack() {
        setmCurrentPlayer("");
    }

    public void addAdjacent(Node node) {
        adjacents.add(node);
    }

    @Override
    public String toString() {
        return this.id + "";
    }
}
