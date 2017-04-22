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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Board;
import model.Line;
import model.MinimaxPodaAB;
import model.Node;
import model.Package;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Machine extends Player {

    private MinimaxPodaAB minimax;

    @Override
    protected void setup() {
        super.setup();
        this.addBehaviour(new MachineBehavior());

        minimax = new MinimaxPodaAB();
    }

    @Override
    public void play(Board board, Package pkc) {
        pkc.mBoard = null;

        if (super.slugs < 3) {//perdi
            pkc.mMessage = Messages.PLAYER_LOSE;

        } else {
            Line result;
            if (isAssignTime()) {
                result = assignSlugInBoard(board);
            } else {
                result = moveSlugInBoard(board);
            }

            if (result == null) {
                pkc.mMessage = Messages.PLAYER_LOSE;

            } else { //encontre una solución
                pkc.args.put("line", result); //linea a modificar en la jugada

                boolean isWillmill = verifyWillmill(result);
                if (isWillmill) {//encontre molinos
                    incrementWillmills();
                    pkc.mMessage = Messages.TURN_FINISHED_WFOUNDED;
                } else {
                    pkc.mMessage = Messages.TURN_FINISHED;
                }
            }
        }
    }

    private boolean verifyWillmill(Line line) {
        return line.isComplete(this.id);
    }

    private boolean isAssignTime() {
        return slugsAssigned == Board.NUMBER_OF_SLUGS_BY_PLAYER;
    }

    private Line assignSlugInBoard(Board board) {
        slugsAssigned++;
        Line lineModified = minimax.getNewAssign(board);
        return lineModified;
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
