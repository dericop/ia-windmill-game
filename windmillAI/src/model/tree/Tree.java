/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.tree;

/**
 *
 * @author Camilo
 */
public class Tree {
    State root;

    public Tree() {
    }

    public Tree(State root) {
        this.root = root;
    }

    public State getRoot() {
        return root;
    }    
}
