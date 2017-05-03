/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import static agentsController.Game.player1;
import static agentsController.Game.player2;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Board;
import model.Line;
import model.MinimaxPodaAB;
import model.Node;
import model.Package;
import model.tree.State;
import model.tree.Tree;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Machine extends Player {

    private MinimaxPodaAB minimax;
    private Board board;
    private Tree treeOfstates;
    private boolean configuredForWin = false;

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new MachineBehavior());

        id = super.getArguments()[0] + "";
        configuredForWin = (boolean) super.getArguments()[1];
        minimax = new MinimaxPodaAB();
    }

    @Override
    public void play(Board board, Package pkc) {
        State moveState = null;
        createNewMachineMove(board, id);

        moveState = minimax.getMovement(treeOfstates, configuredForWin);
        if (moveState != null) {
            board = moveState.getBoard();

        }

    }

    private int verifyWillmill(Board board, LinkedList<Line> currentMills) {
        int mills = 0;

        for (Line line : currentMills) {
            if (!board.getLines().contains(line)) {
                mills++;
            }
        }

        return mills;
    }

    private boolean isAssignTime() {
        return slugsAssigned == Board.NUMBER_OF_SLUGS_BY_PLAYER;
    }

    private Board assignSlugInBoard(Board board) {
        slugsAssigned++;
        return minimax.getNewAssign(board);
    }

    private boolean putSlugInBoard(Board board) {
        String enemy = getEnemy();
        Line lineEnemy = null, linePlayer = null;
        Node nodeEnemy = null, nodePlayer = null;
        if (slugsAssigned == 0) {
            int numberLine = (int) (Math.random() * (board.getLines().size()));
            linePlayer = board.getLines().get(numberLine);
            int numberNode = (int) (Math.random() * 3);
            nodePlayer = linePlayer.getNodeByIndex(numberNode);
            while (!nodePlayer.getmCurrentPlayer().equals("")) {
                numberNode = (int) (Math.random() * 3);
                nodePlayer = linePlayer.getNodeByIndex(numberNode);
            }
        } else {
            for (Line line : board.getLines()) {
                int countEmptyNodes = line.countEmptyNodes();
                if (line.getPlayerCountInLine(enemy) == 2 && countEmptyNodes == 1) {
                    lineEnemy = line;
                    nodeEnemy = line.getEmptyNode();
                } else {
                    if ((line.getPlayerCountInLine(id) == 2 && countEmptyNodes == 1)
                            || (line.getPlayerCountInLine(id) == 1 && countEmptyNodes == 2)
                            || (countEmptyNodes == 3) || (line.getPlayerCountInLine(id) == 1 && line.getPlayerCountInLine(enemy) == 1 && countEmptyNodes == 1)) {
                        linePlayer = line;
                        nodePlayer = line.getEmptyNode();
                    }
                }
            }
        }
        boolean willmill = false;
        if (nodeEnemy != null && lineEnemy != null) {
            nodeEnemy.setmCurrentPlayer(id);
            willmill = lineEnemy.isComplete(id);
        } else if (nodePlayer != null && linePlayer != null) {
            nodePlayer.setmCurrentPlayer(id);
            willmill = linePlayer.isComplete(id);
        }
        incrementSlugsAssigned();
        return willmill;
    }

    private String getEnemy() {
        String enemy = "";
        if (id.equals(player1)) {
            enemy = player2;
        } else {
            enemy = player1;
        }
        return enemy;
    }

    private Node calculateNodeToAttack(Board board) {
        Node nodeTarget = null;
        String otherUsr = getEnemy();
        boolean nodeWasFounded = false;

        //iteracion para evitar molinos
        for (Line line : board.getLines()) {
            if (compare(line.mLeft, otherUsr) && compare(line.mCenter, otherUsr)) {
                nodeTarget = line.mRight;
                nodeWasFounded = true;
                break;

            } else if (compare(line.mCenter, otherUsr) && compare(line.mRight, otherUsr)) {
                nodeTarget = line.mLeft;
                nodeWasFounded = true;
                break;

            } else if (compare(line.mLeft, otherUsr) && compare(line.mRight, otherUsr)) {
                nodeTarget = line.mCenter;
                nodeWasFounded = true;
                break;
            }
        }

        //iteración para encontrar nodo a atacar
        if (!nodeWasFounded) {
            for (Line line : board.getLines()) {
                if (compare(line.mLeft, otherUsr)) {
                    nodeTarget = line.mRight;
                    nodeWasFounded = true;
                    break;

                } else if (compare(line.mCenter, otherUsr)) {
                    nodeTarget = line.mLeft;
                    nodeWasFounded = true;
                    break;

                } else if (compare(line.mRight, otherUsr)) {
                    nodeTarget = line.mCenter;
                    nodeWasFounded = true;
                    break;
                }
            }
        }

        if (!nodeWasFounded) {
            //solo queda atacar los molinos
            for (Line line : board.getLines()) {
                if (compare(line.mLeft, otherUsr) && compare(line.mCenter, otherUsr) && compare(line.mRight, otherUsr)) {
                    int n = (int) (Math.random() * 3);

                    switch (n) {
                        case 0:
                            nodeTarget = line.mLeft;
                            break;
                        case 1:
                            nodeTarget = line.mCenter;
                            break;
                        case 2:
                            nodeTarget = line.mRight;
                            break;
                    }
                    break;
                }
            }
        }

        return nodeTarget;
    }

    private boolean compare(Node node, String compare) {
        return node.getmCurrentPlayer().equals(compare);
    }

    private Line moveSlugInBoard(Board board) {
        Line lineMove = minimax.calculateMove(board);
        return lineMove;
    }

    // Verifica si es posible mover alguna ficha del jugador
    private boolean isPosibleMove(LinkedList<Node> slugs) {
        boolean isPosible = false;
        for (Node slug : slugs) {
            LinkedList<Node> adjs = slug.getAdjacents();
            for (Node ady : adjs) {
                if (ady.getmCurrentPlayer().equals("")) {
                    isPosible = true;
                    break;
                }
            }
        }
        return isPosible;
    }

    boolean isOnMinimax = false;
    private int depth = 3;
    int counter = 1;

    // este metodo obtiene los nodos en los que hay fichas del jugador actual, eliminando el nodo anterior
    private LinkedList<Node> getSlugs(Board board, String player) {
        LinkedList<Node> slugs = new LinkedList<>();
        for (Node node : board.getNodes()) {
            if (node.getmCurrentPlayer().equals(player)) {
                slugs.addLast(node);
            }
        }
        return slugs;
    }

    // mueve la ficha del jugador actual a el nodo adyacente
    private void move(Node current, Node ady) {
        ady.setmCurrentPlayer(current.getmCurrentPlayer());
        current.setmCurrentPlayer("");
    }

    //int counter = 1; cuando ya estan las fichas de los jugadores
    private void createNewMachineMove(Board board, String player) {
        State root = new State(counter + "", null, board, new LinkedList<State>(), 0);
        this.treeOfstates = new Tree(root);
        createTreeOfSolutions(root, player, 0);
    }

    public void createTreeOfSolutions(State root, String player, int cDepth) {

        LinkedList<Node> slugs = getSlugs(root.getBoard(), player), adys;  // conjunto de nodos marcados por el jugador actual
        LinkedList<Line> lines;

        if (slugs.size() > 2 && isPosibleMove(slugs) && cDepth <= depth) {  // codiciones de perdida del juego
            cDepth++;
            boolean canBound = false;

            // mueve las fichas del jugador y verifica si se generan molinos 
            for (Node slug : slugs) {
                adys = slug.getAdjacents();
                for (Node ady : adys) {
                    if (ady.getmCurrentPlayer().equals("")) {   //verifica que el nodo adyacente no este ocupado
                        // tener control de los nodos visitados para evitar el ciclo infinito
                        move(slug, ady); // mueve la ficha a una posicion adyacente
                        lines = Game.getLinesOfNode(board, ady);
                        if (Game.isWindMillInLines(lines, player)) {
                            System.out.println("molino !!");
                            Game.removeSlugEnemy(root.getBoard(), player);
                        }

                        if (player.equals("p1")) {
                            player = "p2";
                        } else {
                            player = "p1";
                        }

                        counter++;
                        State child = new State(counter + "", root, root.getBoard(), new LinkedList<State>(), cDepth);
                        root.addchild(child);
                        if (cDepth == depth || isOnMinimax) {

                            if (cDepth == depth) {
                                child.setHeuristic(minimax.calculateHeuristic(child, player));
                                isOnMinimax = true;
                            }

                            if (isOnMinimax) {
                                canBound = minimax.miniMaxOfState(child);

                                if (canBound) {
                                    break;
                                } else {
                                    isOnMinimax = false;
                                }
                            }

                        } else {
                            createTreeOfSolutions(child, player, cDepth);  // debe enviar la copia de board
                        }
                    }
                }

                if (canBound) {
                    break;
                } else {
                    isOnMinimax = false;
                }
            }

        } else {
            root.setIsLoserNode(true);
        }
    }

    class MachineBehavior extends CyclicBehaviour {

        public MachineBehavior() {

        }

        @Override
        public void action() {
            ACLMessage message = receive();

            if (message != null) {
                try {
                    Package pck = (Package) message.getContentObject();
                    System.out.println(id);
                    switch (pck.mMessage) {
                        case Messages.NEXT_TURN:
                            if (slugsAssigned < 9) {
                                boolean willmillFounded = putSlugInBoard(pck.mBoard);
                                if (willmillFounded) {
                                    Node node = calculateNodeToAttack(pck.mBoard);
                                    node.setmCurrentPlayer("");
                                    pck.mMessage = Messages.TURN_FINISHED_WFOUNDED;
                                } else {
                                    pck.mMessage = Messages.TURN_FINISHED;
                                    reply(message, pck);
                                }
                            } else {
                                play(pck.mBoard, pck);
                            }
                            break;

                        case Messages.NOTIFY_DECREMENT_SLUG:
                            decrementSlugs();
                            if (slugs < 3 || !isPosibleMove(getSlugs(board, id))) {
                                pck.mMessage = Messages.PLAYER_LOSE;
                            } else {
                                pck.mMessage = Messages.SLUG_DECREMENTED_FINISHED;
                            }
                            reply(message, pck);

                            break;

                        case Messages.NOTIFY_DECREMENT_SLUG_AND_WM:
                            decrementSlugs();
                            decrementWillmills();
                            pck.mMessage = Messages.SLUG_DECREMENTED_FINISHED;
                            reply(message, pck);

                            break;
                    }
                } catch (UnreadableException ex) {
                    Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void reply(ACLMessage message, Package rep) {
            try {
                ACLMessage answer = message.createReply();
                answer.setPerformative(ACLMessage.INFORM);

                answer.setContentObject(rep);
                send(answer);
            } catch (IOException ex) {
                Logger.getLogger(Machine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
