/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agentsController;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import model.Player;

/**
 *
 * @author Dericop
 */
public class Human extends Player{

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    class HumanBehavior extends CyclicBehaviour{

        public HumanBehavior() {
        }

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
}
