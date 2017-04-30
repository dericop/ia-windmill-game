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
public class Board implements Serializable, Cloneable {

    private static final long serialVersionUID = 123456L;
    public static int NUMBER_OF_LINES = 16;
    public static int NUMBER_OF_NODES = 24;
    public static int NUMBER_OF_SLUGS_BY_PLAYER = 9;

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
            this.getNodes().addLast(new Node(i + 1));
        }
        System.out.println(this.getNodes());
    }

    private void initLines() {
        int counterNode = 0, currentLine = 0;
        boolean flat = false;
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            if (counterNode < NUMBER_OF_NODES && !flat) {
                currentLine++;
                if (currentLine < 4) {
                    this.getLines().addLast(new Line(i, this.getNodes().get(counterNode), this.getNodes().get(counterNode + 1), this.getNodes().get(counterNode + 2)));
                } else {
                    this.getLines().addLast(new Line(i, this.getNodes().get(counterNode), this.getNodes().get(counterNode + 1), this.getNodes().get(counterNode - 6)));
                    currentLine = 0;
                }
                counterNode += 2;
            } else {
                flat = true;

                this.getLines().addLast(new Line(i, this.getNodes().get(counterNode - 17), this.getNodes().get(counterNode - 9), this.getNodes().get(counterNode - 1)));
                counterNode -= 2;
            }
        }
        System.out.println(this.getLines());
    }

    private void calculateAdjacents() {
        for (Node node : getNodes()) {
            for (Line line : getLines()) {
                if (line.mCenter.equals(node)) {
                    node.addAdjacent(line.mLeft);
                    node.addAdjacent(line.mRight);
                } else if (line.mLeft.equals(node) || line.mRight.equals(node)) {
                    node.addAdjacent(line.mCenter);
                }
            }
        }

        showAdjacents();

    }

    public void showAdjacents() {
        for (Node node : getNodes()) {
            System.out.println(node.getId() + " " + node.getAdjacents());
        }
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(LinkedList<Line> lines) {
        this.lines = lines;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(LinkedList<Node> nodes) {
        this.nodes = nodes;
    }
}
