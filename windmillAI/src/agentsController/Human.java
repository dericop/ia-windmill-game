/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Player;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Human extends Player {

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    class HumanBehavior extends CyclicBehaviour {

        public HumanBehavior() {
        }

        @Override
        public void action() {
            ACLMessage message = receive();

            if (message != null) {
                switch (message.getContent()) {
                    case Messages.STATE_PLAYING:
                        break;
                    case Messages.STATE_GAME_OVER:
                        break;
                    case Messages.STATE_ATTACK:
                        break;
                }
            }
        }

        private void reply(ACLMessage message, char c, Object rep) {
            ACLMessage answer = message.createReply();
            answer.setPerformative(ACLMessage.INFORM);

            answer.setContent(rep + "");
            send(answer);
        }

    }

}
