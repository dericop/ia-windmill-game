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
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Board;
import model.Package;
import model.Player;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Game extends GuiAgent {

    private Board board;
    private String player1 = "p1";
    private String player2 = "p2";
    private String cPlayer; //usuario jugando actualmente
    private String state;
    private char gameType;

    public Game() {
    }

    @Override
    protected void setup() {
        gameType = Messages.TYPE_MACHINE_MACHINE;

        board = new Board();

        createPlayerAgents();
        assignTurns();
        initPlay();

    }

    private void createPlayerAgents() {
        Runtime rt = Runtime.instance();
        // Create a default profile
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createAgentContainer(p);
        AgentController agent;

        try {
            Object args[] = new Object[2];
            String p1;
            String p2;
            
            if(Messages.TYPE_HUMAN_MACHINE == gameType){
                p1 = "agentsController.Machine";
                p2 = "agentsController.Human";
            }else{
                p1 = "agentsController.Machine";
                p2 = "agentsController.Machine";
            }
                
            agent = cc.createNewAgent(player1, p1, args);
            agent.start();
            
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

        state = Messages.STATE_PLAYING;
    }

    private void initPlay() {
        Package pck = new Package(board, Messages.NEXT_TURN);
        sendMessage(cPlayer, pck);
    }

    public void createBoard() {

    }

    public void createPlayers() {

    }

    public Board getBoard() {
        return board;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
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

    @Override
    protected void onGuiEvent(GuiEvent ge) {

    }

    class GameBehavior extends CyclicBehaviour {

        public GameBehavior() {

        }

        @Override
        public void action() {
            ACLMessage answer = blockingReceive();

            if (answer != null && answer.getContent() != null) {
                switch (answer.getContent()) {
                    case Messages.TURN_FINISHED:
                        //sigue el otro a mover fichas
                        break;
                    
                    case Messages.ATTACK:
                        //atacar
                        break;
                    
                    

                }
            }

        }

    }

}
