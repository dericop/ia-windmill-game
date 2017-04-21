/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Dericop
 */
public class Line {
    
    public Node mLeft;
    public Node mCenter;
    public Node mRight;

    public Line(Node left, Node center, Node right){
        mLeft = left;
        mCenter = center;
        mRight = right;
    }
    
    public Line(){}
    
    public boolean isComplete(){
        
        return false;
    }
}
