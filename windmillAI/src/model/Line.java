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

    @Override
    public String toString() {
        return "[ "+this.mLeft + " " + this.mCenter + " " + this.mRight+" ]";
    }
}
