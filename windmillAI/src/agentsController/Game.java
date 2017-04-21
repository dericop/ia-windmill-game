/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agentsController;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import model.Board;
import model.Player;

/**
 *
 * @author Dericop
 */
public class Game extends GuiAgent{
    
    private Board board;
    private Player player1;
    private Player player2;
    private char type;
    
    public Game(){}
    
    public void createBoard(){
    
    }
    
    public void createPlayers(){
    
    }
    
    public Board getBoard() {
        return board;
    }
    
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setType(char type) {
        this.type = type;
    }
    
    @Override
    protected void onGuiEvent(GuiEvent ge) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    class GameBehavior extends CyclicBehaviour{

        public GameBehavior() {
        
        }

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    } 
    
}
