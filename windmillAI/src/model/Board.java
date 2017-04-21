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
public class Board {
    
    public static int NUMBER_OF_LINES = 16;
    public static int NUMBER_OF_NODES = 24;
    
    private LinkedList<Line> lines;
    private LinkedList<Node> nodes;
    private final Player player1;
    private final Player player2;
    
    public LinkedList<Line> getLines() {
        return lines;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
    
    public Board(Player p1, Player p2){
        player1 = p1;
        player2 = p2;
    }
    
    private void initNodes(){
    
    }
    
    private void initLines(){
        
    }
    
}
