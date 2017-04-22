/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentsController;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Board;
import model.Package;
import statics.Messages;

/**
 *
 * @author Dericop
 */
public class Human extends Player {

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
        this.addBehaviour(new HumanBehavior());
    }

    @Override
    public void play(Board board, Package pck) {
        
    }

    class HumanBehavior extends CyclicBehaviour {

        public HumanBehavior() {
        }

        @Override
        public void action() {
            ACLMessage message = receive();

            if (message != null) {
                try {
                    Package pck = (Package) message.getContentObject();
                    
                    switch (message.getContent()) {
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
                    Logger.getLogger(Human.class.getName()).log(Level.SEVERE, null, ex);
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
