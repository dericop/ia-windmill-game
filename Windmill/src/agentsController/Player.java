/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.Agent;
import model.Board;
import model.Package;

/**
 *
 * @author Dericop
 */
public abstract class Player extends Agent {

    protected int slugs = 9;
    protected int wQuantity = 0;
    protected int slugsAssigned = 0;
    protected String id;

    public Player() {

    }

    @Override
    protected void setup() {
        this.id = getArguments()[0] + "";
    }

    public abstract void play(Board board, Package pck);

    public int getSlugsAssigned() {
        return slugsAssigned;
    }

    public int getSlugs() {
        return slugs;
    }

    public int getwQuantity() {
        return wQuantity;
    }

    public String getId() {
        return id;
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

    public void incrementSlugsAssigned() {
        slugsAssigned++;
    }

}
