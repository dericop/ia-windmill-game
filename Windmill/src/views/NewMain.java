/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package views;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Dericop
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JFrame window = new JFrame("Morris Game");
        window.setVisible(true);
        window.setSize(500,500);
        
        JLabel label = new JLabel("asd");
        label.setAlignmentX(10);
        label.setAlignmentX(10);
        label.setIcon(new ImageIcon(window.getClass().getResource("/Resources/Circle_Grey.png")));
        window.add(label);
        
        window.repaint();
        
        
        
        
        
        
    }
    
}
