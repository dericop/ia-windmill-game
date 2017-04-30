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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.Board;
import model.Line;
import model.Node;
import model.Package;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Game extends GuiAgent {

    private Board board;
    public static String player1 = "p1";
    public static String player2 = "p2";
    private String cPlayer; //usuario jugando actualmente
    private String state;
    private char gameType;
    

    public Game() {
    }

    @Override
    protected void setup() {
        /*this.addBehaviour(new GameBehavior());
        gameType = Messages.TYPE_MACHINE_MACHINE;
        state = Messages.STATE_PLACING_SLUGS;

        board = new Board();
        
        createPlayerAgents();
        assignTurns();
        play();*/
        
        
        
        

    }

    private void createPlayerAgents() {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createAgentContainer(p);
        AgentController agent;

        try {
            Object args[] = new Object[1];

            String p1;
            String p2;

            if (Messages.TYPE_HUMAN_MACHINE == gameType) {
                p1 = "agentsController.Machine";
                p2 = "agentsController.Human";
            } else {
                p1 = "agentsController.Machine";
                p2 = "agentsController.Machine";
            }

            args[0] = player1;
            agent = cc.createNewAgent(player1, p1, args);
            agent.start();

            args[0] = player2;
            agent = cc.createNewAgent(player2, p2, args);
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
        
        // solo para la prueba
        board.getNodes().get(0).setmCurrentPlayer(player1);
        board.getNodes().get(3).setmCurrentPlayer(player1);
        board.getNodes().get(8).setmCurrentPlayer(player1);
        board.getNodes().get(10).setmCurrentPlayer(player1);
        board.getNodes().get(13).setmCurrentPlayer(player1);
        board.getNodes().get(15).setmCurrentPlayer(player1);
        board.getNodes().get(16).setmCurrentPlayer(player1);
        board.getNodes().get(19).setmCurrentPlayer(player1);
        board.getNodes().get(23).setmCurrentPlayer(player1);

        board.getNodes().get(2).setmCurrentPlayer(player2);
        board.getNodes().get(5).setmCurrentPlayer(player2);
        board.getNodes().get(6).setmCurrentPlayer(player2);
        board.getNodes().get(7).setmCurrentPlayer(player2);
        board.getNodes().get(9).setmCurrentPlayer(player2);
        board.getNodes().get(11).setmCurrentPlayer(player2);
        board.getNodes().get(14).setmCurrentPlayer(player2);
        board.getNodes().get(21).setmCurrentPlayer(player2);
        board.getNodes().get(22).setmCurrentPlayer(player2);
        
        Package pck = new Package(board, Messages.NEXT_TURN);
        sendMessage(cPlayer, pck);
        
    }

    private void playerCanAttack() {
        Package pck = new Package(null, Messages.ATTACK);
        sendMessage(cPlayer, pck);
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

    }

    private void showGraphicalWillmill() {

    }

    private void showGraphicalAttack(String node) {

    }

    private String[] setLogicalMove(Line line) {
        //ubicar y guardar el origen
        //hayar el nodo objetivo
        //reemplazar el objetivo con el nodo origen
        String[] nodes = new String[2];

        return nodes;
    }

    private String[] setLogicalMove(Line linO, Line lineD) {
        String[] nodes = new String[2];

        return nodes;
    }

    private void attackNode(String node) {

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
                            Line line = (Line) pck.args.get("line");
                            setLogicalMove(line);
                            showGraphicalMove();
                            changePlayer();
                            play();

                            break;

                        case Messages.TURN_FINISHED_WFOUNDED:
                            Line l = (Line) pck.args.get("line");
                            setLogicalMove(l);
                            showGraphicalWillmill();
                            playerCanAttack();

                            break;

                        case Messages.PLAYER_LOSE:
                            showWinner();
                            System.out.println("Has perdido");
                            break;

                        case Messages.ATTACKED_FINISHED:
                            String node = pck.args.get("nodeAttacked") + "";
                            attackNode(node);
                            showGraphicalAttack(node);
                            changePlayer();

                            if (pck.args.get("willmillWasDestroyed") != null) {
                                pck.mMessage = Messages.NOTIFY_DECREMENT_SLUG_AND_WM;
                                sendMessage(cPlayer, pck);
                            } else {
                                pck.mMessage = Messages.NOTIFY_DECREMENT_SLUG;
                                sendMessage(cPlayer, pck);
                            }

                            break;

                        case Messages.SLUG_DECREMENTED_FINISHED:
                            play();

                            break;

                    }
                } catch (UnreadableException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }

}
