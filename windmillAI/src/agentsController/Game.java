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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Board;
import model.Line;
import model.Node;
import model.Package;
import model.tree.State;
import model.tree.Tree;
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
        this.addBehaviour(new GameBehavior());
        gameType = Messages.TYPE_MACHINE_MACHINE;

        board = new Board();

        // solo para la prueba
        cPlayer = player1;

        board.getNodes().get(0).setmCurrentPlayer(cPlayer);
        board.getNodes().get(3).setmCurrentPlayer(cPlayer);
        board.getNodes().get(8).setmCurrentPlayer(cPlayer);
        board.getNodes().get(10).setmCurrentPlayer(cPlayer);
        board.getNodes().get(13).setmCurrentPlayer(cPlayer);
        board.getNodes().get(15).setmCurrentPlayer(cPlayer);
        board.getNodes().get(16).setmCurrentPlayer(cPlayer);
        board.getNodes().get(19).setmCurrentPlayer(cPlayer);
        board.getNodes().get(23).setmCurrentPlayer(cPlayer);

        board.getNodes().get(2).setmCurrentPlayer(player2);
        board.getNodes().get(5).setmCurrentPlayer(player2);
        board.getNodes().get(6).setmCurrentPlayer(player2);
        board.getNodes().get(7).setmCurrentPlayer(player2);
        board.getNodes().get(9).setmCurrentPlayer(player2);
        board.getNodes().get(11).setmCurrentPlayer(player2);
        board.getNodes().get(14).setmCurrentPlayer(player2);
        board.getNodes().get(21).setmCurrentPlayer(player2);
        board.getNodes().get(22).setmCurrentPlayer(player2);
        //#####################       

        createTreeOfSolutions(board, null, null);
        //createPlayerAgents();
        //assignTurns();
        //play();

    }

    private void createPlayerAgents() {
        Runtime rt = Runtime.instance();
        // Create a default profile
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

    private String[] setLogivalMove(Line linO, Line lineD) {
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

    //########################################################################
    // mueve la ficha del jugador actual a el nodo adyacente
    private void move(Node current, Node ady) {
        ady.setmCurrentPlayer(cPlayer);
        current.setmCurrentPlayer("");
    }

    // verifica si hay molino en el board
    private boolean isWindMillInLines(LinkedList<Line> lines) {
        boolean isWindMill = false;
        for (Line line : lines) {
            if (line.isComplete(cPlayer)) {
                isWindMill = true;
            }
        }
        return isWindMill;
    }

    // este metodo obtiene los nodos en los que hay fichas del jugador actual, eliminando el nodo anterior
    private LinkedList<Node> getSlugs(Board board, Node previous) {
        LinkedList<Node> slugs = new LinkedList<>();
        for (Node node : board.getNodes()) {
            if (node != previous && node.getmCurrentPlayer().equals(cPlayer)) {
                slugs.addLast(node);
            }
        }
        return slugs;
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

    //elimina una ficha enemiga 
    private void removeSlugEnemy(Board board) {
        changePlayer();
        for (Node node : board.getNodes()) {
            if ((cPlayer).equals(node.getmCurrentPlayer())) {
                node.setmCurrentPlayer("");
                break;
            }
        }
        changePlayer();
    }

    // retorna el conjunto de lineas que contienen al nodo ady
    private LinkedList<Line> getLinesOfNode(Board board, Node ady) {
        LinkedList<Line> lines = new LinkedList<>();
        for (Line line : board.getLines()) {
            if (line.mLeft.equals(ady) || line.mCenter.equals(ady) || line.mRight.equals(ady)) {
                lines.addLast(line);
            }
        }
        return lines;
    }

    // cuando ya estan las fichas de los jugadores
    public void createTreeOfSolutions(Board board, Node previous, Node current) {
        LinkedList<Node> slugs = getSlugs(board, previous), adys;  // conjunto de nodos marcados por el jugador actual
        LinkedList<Line> lines;
        Board temp;

        if (slugs.size() > 2 && isPosibleMove(slugs)) {  // codiciones de perdida del juego
            // mueve las fichas del jugador y verifica si se generan molinos 
            for (Node slug : slugs) {
                adys = slug.getAdjacents();
                for (Node ady : adys) {
                    if (ady.getmCurrentPlayer().equals("")) {   //verifica que el nodo adyacente no este ocupado
                        // tener control de los nodos visitados para evitar el ciclo infinito
                        System.out.println("entré");
                        move(slug, ady); // mueve la ficha a una posicion adyacente
                        lines = getLinesOfNode(board, ady);
                        if (isWindMillInLines(lines)) {
                            System.out.println("molino !!");
                            removeSlugEnemy(board);
                        }
                        changePlayer();
                        createTreeOfSolutions(board, slug, ady);  // debe enviar la copia de board
                    }
                }
            }
        } else {
            System.out.println("juego perdido");
        }

//        for (Node slug : slugs) {
//            LinkedList<Node> adys = slug.getAdjacents();   // conjunto de nodos adyacentes por cada nodo marcado
//            for (Node ady : adys) {
//                temp = move(board, slug, ady); // enviar un clone de board
//                LinkedList<Line> lines = getLinesOfNode(temp, ady);
//
//                if (isWindMillInLines(lines)) {
//                    removeSlugEnemy(temp);
//                }
//                
//            }
//        }
    }

    //#######################################################################
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
