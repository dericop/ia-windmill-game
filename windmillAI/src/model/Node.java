/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.LinkedList;

/**
 *
 * @author Dericop
 */
public class Node {
       
    private String mCurrentPlayer;
    private LinkedList<Node> mAdjacents;
       
    public Node(){}
    
    public Node(LinkedList<Node> adjacents){
       mAdjacents = adjacents;
    }
    
    public String getmCurrentPlayer() {
        return mCurrentPlayer;
    }

    public LinkedList<Node> getmAdjacents() {
        return mAdjacents;
    }

    public void setmCurrentPlayer(String mCurrentPlayer) {
        this.mCurrentPlayer = mCurrentPlayer;
    }
    
    public void attack(){
        mCurrentPlayer = "";
    }
   
}
