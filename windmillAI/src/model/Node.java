/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Dericop
 */
public class Node {

    private String mCurrentPlayer;
    private int id;

    public Node(int id) {
        this.id = id;
    }

    public String getmCurrentPlayer() {
        return mCurrentPlayer;
    }


    public void setmCurrentPlayer(String mCurrentPlayer) {
        this.mCurrentPlayer = mCurrentPlayer;
    }

    public void attack() {
        mCurrentPlayer = "";
    }

    @Override
    public String toString() {
        return this.id + "" ;
    }

}
