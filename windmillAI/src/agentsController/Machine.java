/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agentsController;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import model.MinimaxPodaAB;
import model.Player;

/**
 *
 * @author Dericop
 */
public class Machine extends Player{
    
    private MinimaxPodaAB minimax;

    @Override
    protected void setup() {
        super.setup(); 
    }

    @Override
    public void play() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
    class MachineBehavior extends CyclicBehaviour{

        public MachineBehavior() {
        
        }

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    
}
