/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import model.MinimaxPodaAB;
import model.Player;
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
    }

    @Override
    public void play() {

        if (isAssignTime()) {
            assignSlugInBoard();
        } else {
            moveSlugInBoard();
        }

    }

    private boolean isAssignTime() {
        return false;
    }

    private void assignSlugInBoard() {

    }

    private void moveSlugInBoard() {
    }

    class MachineBehavior extends CyclicBehaviour {

        public MachineBehavior() {

        }

        @Override
        public void action() {
            ACLMessage message = receive();

            if (message != null) {
                switch (message.getContent()) {
                    case Messages.NEXT_TURN:

                        play();
                        //reply
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
