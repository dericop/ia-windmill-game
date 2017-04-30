/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Dericop
 */
public class Line implements Serializable{

    private static final long serialVersionUID = 879965647867471L;
    public int mId;
    public Node mLeft;
    public Node mCenter;
    public Node mRight;

    public Line(int id,Node left, Node center, Node right) {
        mId = id;
        mLeft = left;
        mCenter = center;
        mRight = right;
    }

    public Line() {
    }

    public boolean isComplete(String usr) {

        boolean isWillmill = false;

        if (mLeft.getmCurrentPlayer().equals(usr)
                && mCenter.getmCurrentPlayer().equals(usr)
                && mRight.getmCurrentPlayer().equals(usr)) {

            isWillmill = true;
        }
        return isWillmill;
    }
    
    public int getPlayerCountInLine(String player){
        int playerCount = 0;
        int emptyCount = 0;
        
        if (this.mLeft.getmCurrentPlayer().equals(player)) {
            playerCount++;
        }
        if (this.mCenter.getmCurrentPlayer().equals(player)) {
            playerCount++;
        }
        if (this.mRight.getmCurrentPlayer().equals(player)) {
            playerCount++;
        }
        
        if (this.mLeft.getmCurrentPlayer().equals("")) {
            emptyCount++;
        }
        if (this.mCenter.getmCurrentPlayer().equals("")) {
            emptyCount++;
        }
        if (this.mRight.getmCurrentPlayer().equals("")) {
            emptyCount++;
        }
        
        if(!(playerCount == 2 && emptyCount == 1))
            playerCount = 0;
        
        return playerCount;
    }

    @Override
    public String toString() {
        return "[ "+this.mLeft + " " + this.mCenter + " " + this.mRight+" ]";
    }
}
