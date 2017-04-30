/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.Agent;
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
        
        id = super.getArguments()[0]+"";
        minimax = new MinimaxPodaAB();
    }

    @Override
    public void play(Board board, Package pkc) {
        pkc.mBoard = null;

        if (super.slugs < 3) {//perdi
            pkc.mMessage = Messages.PLAYER_LOSE;

        } else {
            
            Board rBoard;
            State moveState = null;
            if (isAssignTime()) {
                rBoard = assignSlugInBoard(board);
            } else {
                createNewMachineMove(board, id);
                moveState = minimax.getMovement(treeOfstates, configuredForWin);
                rBoard = moveState.getBoard();
            }

            if (moveState!= null && moveState.isIsLoserNode()) {
                pkc.mMessage = Messages.PLAYER_LOSE;

            } else { //encontre una solución
                LinkedList<Line> currentMills = pkc.mCurrentMills;
                
                int millsDetected = verifyWillmill(rBoard, currentMills);
                if (millsDetected > 0) {//encontre molinos
                    incrementWillmills();
                    pkc.mMessage = Messages.TURN_FINISHED_WFOUNDED;
                    pkc.args.put("cMills", millsDetected);
                } else {
                    pkc.mMessage = Messages.TURN_FINISHED;
                    pkc.mBoard = rBoard;
                }
            }
        }
    }

    private int verifyWillmill(Board board, LinkedList<Line> currentMills) {
        int mills = 0;
        
        for (Line line : currentMills) {
            if(!board.getLines().contains(line))
                mills++;
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

    private int calculateNodeToAttack(Board board) {
        int nodeTarget = 0;
        String otherUsr;
        boolean nodeWasFounded = false;

        if (this.id.equals(Game.player1)) {
            otherUsr = Game.player2;
        } else {
            otherUsr = Game.player1;
        }

        //iteracion para evitar molinos
        for (Line line : board.getLines()) {
            if (compare(line.mLeft, otherUsr) && compare(line.mCenter, otherUsr)) {
                nodeTarget = line.mRight.getId();
                nodeWasFounded = true;
                break;

            } else if (compare(line.mCenter, otherUsr) && compare(line.mRight, otherUsr)) {
                nodeTarget = line.mLeft.getId();
                nodeWasFounded = true;
                break;

            } else if (compare(line.mLeft, otherUsr) && compare(line.mRight, otherUsr)) {
                nodeTarget = line.mCenter.getId();
                nodeWasFounded = true;
                break;
            }
        }

        //iteración para encontrar nodo a atacar
        if (!nodeWasFounded) {
            for (Line line : board.getLines()) {
                if (compare(line.mLeft, otherUsr)) {
                    nodeTarget = line.mRight.getId();
                    nodeWasFounded = true;
                    break;

                } else if (compare(line.mCenter, otherUsr)) {
                    nodeTarget = line.mLeft.getId();
                    nodeWasFounded = true;
                    break;

                } else if (compare(line.mRight, otherUsr)) {
                    nodeTarget = line.mCenter.getId();
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
                            nodeTarget = line.mLeft.getId();
                            break;
                        case 1:
                            nodeTarget = line.mCenter.getId();
                            break;
                        case 2:
                            nodeTarget = line.mRight.getId();
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

    private void attack(Board board, Package pck) {
        pck.mBoard = null;
        pck.mMessage = Messages.ATTACKED_FINISHED;
        int node = calculateNodeToAttack(board); // Calcular el nodo a atacar
        pck.args.put("nodeAttacked", node);
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
                                }else{
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
                }else{
                    isOnMinimax = false;
                }
            }

        }else{
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

                    switch (pck.mMessage) {
                        case Messages.NEXT_TURN:
                            play(pck.mBoard, pck);
                            reply(message, pck);
                            break;

                        case Messages.ATTACK:
                            attack(pck.mBoard, pck);
                            reply(message, pck);
                            break;

                        case Messages.NOTIFY_DECREMENT_SLUG:
                            decrementSlugs();
                            pck.mMessage = Messages.SLUG_DECREMENTED_FINISHED;
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
