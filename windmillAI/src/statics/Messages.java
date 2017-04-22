/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package statics;

/**
 *
 * @author Dericop
 */
public class Messages {
    
    //----- Tipos de juego -->
    public static char TYPE_HUMAN_MACHINE = 'H';
    public static char TYPE_MACHINE_MACHINE = 'M';
    
    //---- Protocolos de mensajes -->
    public static final String NEXT_TURN = "Next Turn";
    public static final String ATTACK = "Can Attack";
    public static final String TURN_FINISHED = "Turn finished";
    
    
    //---- Estados del juego -->
    public static final String STATE_PLAYING = "P";
    public static final String STATE_GAME_OVER = "G";
    public static final String STATE_ATTACK = "A";
}
