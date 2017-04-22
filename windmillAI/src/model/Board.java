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
public class Board{
    
    public static int NUMBER_OF_LINES = 16;
    public static int NUMBER_OF_NODES = 24;

    private LinkedList<Line> lines;
    private LinkedList<Node> nodes;

    public LinkedList<Line> getLines() {
        return lines;
    }

    public LinkedList<Node> getNodes() {
        return nodes;
    }

   
    public Board() {      
        this.lines = new LinkedList<>();
        this.nodes = new LinkedList<>();
        
        initNodes();
        initLines();
        calculateAdjacents();
    }

    private void initNodes() {
        for (int i = 0; i < NUMBER_OF_NODES; i++) {
            this.nodes.addLast(new Node(i + 1));
        }
        System.out.println(this.nodes);
    }

    private void initLines() {
        int counterNode = 0,currentLine = 0;
        boolean flat = false;
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            if (counterNode < NUMBER_OF_NODES && !flat) {
                currentLine++;
                if (currentLine < 4) {
                    this.lines.addLast(new Line(this.nodes.get(counterNode), this.nodes.get(counterNode + 1), this.nodes.get(counterNode + 2)));
                } else {
                    this.lines.addLast(new Line(this.nodes.get(counterNode), this.nodes.get(counterNode + 1), this.nodes.get(counterNode - 6)));
                    currentLine = 0;
                }
                counterNode += 2;
            } else {
                flat = true;
                this.lines.addLast(new Line(this.nodes.get(counterNode - 17), this.nodes.get(counterNode - 9), this.nodes.get(counterNode - 1)));
                counterNode -= 2;
            }
        }
        System.out.println(this.lines);
    }
    
    private void calculateAdjacents(){
        for(Node node:nodes){
            for(Line line: lines){
                if(line.mCenter.equals(node)){
                    node.addAdjacent(line.mLeft);
                    node.addAdjacent(line.mRight);
                }else if(line.mLeft.equals(node) || line.mRight.equals(node)){
                    node.addAdjacent(line.mCenter);
                }
            }
        }
        
        showAdjacents();
        
    }
    
    public void showAdjacents(){
        for (Node node : nodes) {
            System.out.println(node.getId()+" "+node.getAdjacents());
        }
    }
}
