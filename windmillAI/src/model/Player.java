/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import jade.core.Agent;

/**
 *
 * @author Dericop
 */
public abstract class Player extends Agent{

    protected int slugs;
    protected int wQuantity;
    protected String id;

    public Player() {
        slugs = 9;
        wQuantity = 0;
    }

    public abstract void play();

    public int getSlugs() {
        return slugs;
    }

    public int getwQuantity() {
        return wQuantity;
    }

    public String getId() {
        return id;
    }

    public void moveSlugToNode(Node source, Node target) {

    }

    public void pinNode(Node node) {

    }

    public void attack(Player opponent, Node node) {

    }

    public void incrementWillmills() {
        wQuantity++;
    }

    public void decrementWillmills() {
        wQuantity--;
    }

    public void decrementSlugs() {
        slugs--;
    }

}
