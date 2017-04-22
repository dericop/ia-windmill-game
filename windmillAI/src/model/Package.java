/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;

/**
 *
 * @author Dericop
 */
public class Package implements Serializable{
    private static final long serialVersionUID = 8799656478674716638L;
    public Board mBoard;
    public String mMessage;
    //poner la posicion a atacar.
    
    public Package() {}
    
    public Package(Board board, String message){
        mBoard = board;
        mMessage = message;
    }
    
    public Package(String message){
        mMessage = message;
    }
    
    public Package(Board board){
        mBoard = board;
    }
}
