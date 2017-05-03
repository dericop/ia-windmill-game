/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.AID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import model.Board;
import model.Line;
import model.Node;
import model.Package;
import statics.Messages;
import views.GameInterface;

/**
 *
 * @author Dericop
 */
public class Game extends GuiAgent {

    private Board board;
    public static String player1 = "p1";
    public static String player2 = "p2";
    private String cPlayer; //usuario jugando actualmente
    private char gameType;
    GameInterface gameGUI;

    @Override
    protected void setup() {
        this.gameGUI = new GameInterface(this);
        this.addBehaviour(new GameBehavior());
    }

    public void startGame(char type) {
        this.gameType = type;
        board = new Board();
        createPlayerAgents();
        assignTurns();
        play();
    }

    private void createPlayerAgents() {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createAgentContainer(p);
        AgentController agent;

        try {
            Object args[] = new Object[2],args1[]= new Object[2];

            String p1, p2;

            if (Messages.TYPE_HUMAN_MACHINE != gameType) {
                p2 = "agentsController.Machine";
                args1[0] = player2;
                args1[1] = false;
                agent = cc.createNewAgent(player2, p2, args1);
                agent.start();
            }

            p1 = "agentsController.Machine";
            args[0] = player1;
            args[1] = true;
            agent = cc.createNewAgent(player1, p1, args);
            agent.start();

        } catch (StaleProxyException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void assignTurns() {
        int cNumber = (int) (Math.random() * 2);
        if (cNumber == 0) {
            cPlayer = player1;
        } else {
            cPlayer = player2;
        }
    }

    private void play() {
        if (gameType == Messages.TYPE_MACHINE_MACHINE) {
            Package pck = new Package(board, Messages.NEXT_TURN);
            sendMessage(cPlayer, pck);
        }
    }

    private void changePlayer() {
        if (cPlayer.equals(player1)) {
            cPlayer = player2;
        } else {
            cPlayer = player1;
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setType(char type) {
        this.gameType = type;
    }

    public void sendMessage(String localName, Package message) {
        AID idReceptor = new AID();
        idReceptor.setLocalName(localName);
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        aclMessage.setSender(getAID());
        aclMessage.addReceiver(idReceptor);

        try {
            aclMessage.setContentObject(message);

        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        send(aclMessage);
    }

    private void showGraphicalMove() {
        for (Node node : board.getNodes()) {
            JLabel labelOfNode = gameGUI.labels.get(node.getId() - 1);
            ImageIcon icon;
            if (node.getmCurrentPlayer().equals(player1)) {
                icon = new ImageIcon(this.getClass().getResource("/Resources/circle.png"));
                labelOfNode.setIcon(icon);
            } else if (node.getmCurrentPlayer().equals(player2)) {
                icon = new ImageIcon(this.getClass().getResource("/Resources/circle2.png"));
                labelOfNode.setIcon(icon);
            }
            gameGUI.repaint();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private void showWinner() {

    }

    @Override
    protected void onGuiEvent(GuiEvent ge) {

    }

    // verifica si hay molino en el board
    public static boolean isWindMillInLines(LinkedList<Line> lines, String player) {
        boolean isWindMill = false;
        for (Line line : lines) {
            if (line.isComplete(player)) {
                isWindMill = true;
            }
        }
        return isWindMill;
    }

    //elimina una ficha enemiga 
    public static void removeSlugEnemy(Board board, String player) {

        for (Node node : board.getNodes()) {
            if (!player.equals(node.getmCurrentPlayer()) && !node.getmCurrentPlayer().equals("")) {
                node.setmCurrentPlayer("");
                break;
            }
        }
    }

    // retorna el conjunto de lineas que contienen al nodo ady
    public static LinkedList<Line> getLinesOfNode(Board board, Node ady) {
        LinkedList<Line> lines = new LinkedList<>();
        for (Line line : board.getLines()) {
            if (line.mLeft.equals(ady) || line.mCenter.equals(ady) || line.mRight.equals(ady)) {
                lines.addLast(line);
            }
        }
        return lines;
    }

    class GameBehavior extends CyclicBehaviour {

        public GameBehavior() {

        }

        @Override
        public void action() {
            ACLMessage answer = blockingReceive();

            if (answer != null && answer.getContent() != null) {
                try {
                    Package pck = (Package) answer.getContentObject();

                    switch (pck.mMessage) {
                        case Messages.TURN_FINISHED:
                            board = pck.mBoard;
                            showGraphicalMove();
                            changePlayer();
                            play();
                            break;

                        case Messages.TURN_FINISHED_WFOUNDED:
                            board = pck.mBoard;
                            showGraphicalMove();
                            changePlayer();
                            pck.mMessage = Messages.NOTIFY_DECREMENT_SLUG;

                            break;

                        case Messages.SLUG_DECREMENTED_FINISHED:
                            play();
                            break;

                        case Messages.PLAYER_LOSE:
                            changePlayer();
                            showWinner();
                            System.out.println(cPlayer + " Ha perdido");
                            break;
                    }
                } catch (UnreadableException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

}
