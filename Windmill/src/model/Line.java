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
public class Line implements Serializable {

    private static final long serialVersionUID = 879965647867471L;
    public int mId;
    public Node mLeft;
    public Node mCenter;
    public Node mRight;

    public Line(int id, Node left, Node center, Node right) {
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

    public int countEmptyNodes() {
        int count = 0;
        if (mLeft.getmCurrentPlayer().equals("")) {
            count++;
        }
        if (mCenter.getmCurrentPlayer().equals("")) {
            count++;
        }
        if (mRight.getmCurrentPlayer().equals("")) {
            count++;
        }

        return count;
    }

    public Node getNodeByIndex(int index) {
        Node node = null;
        if (index == 0) {
            node = mLeft;
        }
        if (index == 1) {
            node = mCenter;
        }
        if (index == 2) {
            node = mRight;
        }
        return node;
    }

    public Node getEmptyNode() {
        Node node;
        if (mLeft.getmCurrentPlayer().equals("")) {
            node = mLeft;
        } else {
            if (mCenter.getmCurrentPlayer().equals("")) {
                node = mCenter;
            } else {
                node = mRight;
            }
        }
        return node;
    }

    public int getPlayerCountInLine(String player) {
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

        return playerCount;
    }

    @Override
    public String toString() {
        return "[ " + this.mLeft + " " + this.mCenter + " " + this.mRight + " ]";
    }
}
