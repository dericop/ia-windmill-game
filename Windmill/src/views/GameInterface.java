/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import agentsController.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import statics.Messages;

/**
 *
 * @author Camilo
 */
public class GameInterface extends javax.swing.JFrame {

    final Game game;
    public LinkedList<JLabel> labels;

    public GameInterface(final Game game) {
        initComponents();
        this.game = game;

        this.setSize(600, 700);
        this.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = dim.width / 2 - this.getSize().width / 2;
        int y = dim.height / 2 - this.getSize().height / 2;
        this.setLocation(x, y);

        //this.add(background);
//        this.setComponentZOrder(background, 1);
        labels = new LinkedList<>();

        for (int i = 1; i < 25; i++) {
            JLabel emptyNode = new JLabel(new ImageIcon(getClass().getResource("/Resources/emptyNode1.png")));
            labels.addLast(emptyNode);
            emptyNode.setName(i + "");
            emptyNode.setSize(48, 48);
            this.add(emptyNode);

            emptyNode.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    JLabel label = (JLabel) me.getComponent();
                    label.setIcon(new ImageIcon(getClass().getResource("/Resources/circle.png")));
                    System.out.println(label.getName());
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }
            });
            //this.setComponentZOrder(emptyNode, 0);
        }

        // inferior
        int difference = 50;
        int WindowCenterX = (this.getSize().width / 2) - 25;
        int WindowCenterY = (this.getSize().height / 2) - difference;

        int line = 1;
        int distanceNode = 70;

        labels.get(16).setLocation(WindowCenterX - distanceNode, WindowCenterY - distanceNode);
        labels.get(17).setLocation(WindowCenterX, WindowCenterY - distanceNode);
        labels.get(18).setLocation(WindowCenterX + distanceNode, WindowCenterY - distanceNode);
        labels.get(19).setLocation(WindowCenterX + distanceNode, WindowCenterY);
        labels.get(20).setLocation(WindowCenterX + distanceNode, WindowCenterY + distanceNode);
        labels.get(21).setLocation(WindowCenterX, WindowCenterY + distanceNode);
        labels.get(22).setLocation(WindowCenterX - distanceNode, WindowCenterY + distanceNode);
        labels.get(23).setLocation(WindowCenterX - distanceNode, WindowCenterY);

        JLabel lineImg0 = new JLabel("");
        lineImg0.setOpaque(true);
        lineImg0.setBackground(new Color(0, 0, 0, 255));
        lineImg0.setSize(labels.get(17).getLocation().x - labels.get(16).getLocation().x, 5);
        lineImg0.setLocation(labels.get(16).getLocation().x + 22, labels.get(16).getLocation().y + 22);
        this.add(lineImg0);

        JLabel lineImg1 = new JLabel("");
        lineImg1.setOpaque(true);
        lineImg1.setBackground(new Color(0, 0, 0, 255));
        lineImg1.setSize(labels.get(18).getLocation().x - labels.get(17).getLocation().x, 5);
        lineImg1.setLocation(labels.get(17).getLocation().x + 22, labels.get(17).getLocation().y + 22);
        this.add(lineImg1);

        JLabel lineImg2 = new JLabel("");
        lineImg2.setOpaque(true);
        lineImg2.setBackground(new Color(0, 0, 0, 255));
        lineImg2.setSize(5, labels.get(19).getLocation().y - labels.get(18).getLocation().y);
        lineImg2.setLocation(labels.get(18).getLocation().x + 22, labels.get(18).getLocation().y + 22);
        this.add(lineImg2);

        JLabel lineImg3 = new JLabel("");
        lineImg3.setOpaque(true);
        lineImg3.setBackground(new Color(0, 0, 0, 255));
        lineImg3.setSize(5, labels.get(20).getLocation().y - labels.get(19).getLocation().y);
        lineImg3.setLocation(labels.get(19).getLocation().x + 22, labels.get(19).getLocation().y + 22);
        this.add(lineImg3);

        JLabel lineImg4 = new JLabel("");
        lineImg4.setOpaque(true);
        lineImg4.setBackground(new Color(0, 0, 0, 255));
        lineImg4.setSize(labels.get(20).getLocation().x - labels.get(21).getLocation().x, 5);
        lineImg4.setLocation(labels.get(21).getLocation().x + 22, labels.get(21).getLocation().y + 22);
        this.add(lineImg4);

        JLabel lineImg5 = new JLabel("");
        lineImg5.setOpaque(true);
        lineImg5.setBackground(new Color(0, 0, 0, 255));
        lineImg5.setSize(labels.get(21).getLocation().x - labels.get(22).getLocation().x, 5);
        lineImg5.setLocation(labels.get(22).getLocation().x + 22, labels.get(22).getLocation().y + 22);
        this.add(lineImg5);

        JLabel lineImg6 = new JLabel("");
        lineImg6.setOpaque(true);
        lineImg6.setBackground(new Color(0, 0, 0, 255));
        lineImg6.setSize(5, labels.get(22).getLocation().y - labels.get(23).getLocation().y);
        lineImg6.setLocation(labels.get(23).getLocation().x + 22, labels.get(23).getLocation().y + 22);
        this.add(lineImg6);

        JLabel lineImg7 = new JLabel("");
        lineImg7.setOpaque(true);
        lineImg7.setBackground(new Color(0, 0, 0, 255));
        lineImg7.setSize(5, labels.get(23).getLocation().y - labels.get(16).getLocation().y);
        lineImg7.setLocation(labels.get(16).getLocation().x + 22, labels.get(16).getLocation().y + 22);
        this.add(lineImg7);

        // ################################
        distanceNode *= 2;

        labels.get(8).setLocation(WindowCenterX - distanceNode, WindowCenterY - distanceNode);
        labels.get(9).setLocation(WindowCenterX, WindowCenterY - distanceNode);
        labels.get(10).setLocation(WindowCenterX + distanceNode, WindowCenterY - distanceNode);
        labels.get(11).setLocation(WindowCenterX + distanceNode, WindowCenterY);
        labels.get(12).setLocation(WindowCenterX + distanceNode, WindowCenterY + distanceNode);
        labels.get(13).setLocation(WindowCenterX, WindowCenterY + distanceNode);
        labels.get(14).setLocation(WindowCenterX - distanceNode, WindowCenterY + distanceNode);
        labels.get(15).setLocation(WindowCenterX - distanceNode, WindowCenterY);

        JLabel lineImg8 = new JLabel("");
        lineImg8.setOpaque(true);
        lineImg8.setBackground(new Color(0, 0, 0, 255));
        lineImg8.setSize(labels.get(9).getLocation().x - labels.get(8).getLocation().x, 5);
        lineImg8.setLocation(labels.get(8).getLocation().x + 22, labels.get(8).getLocation().y + 22);
        this.add(lineImg8);

        JLabel lineImg9 = new JLabel("");
        lineImg9.setOpaque(true);
        lineImg9.setBackground(new Color(0, 0, 0, 255));
        lineImg9.setSize(labels.get(10).getLocation().x - labels.get(9).getLocation().x, 5);
        lineImg9.setLocation(labels.get(9).getLocation().x + 22, labels.get(9).getLocation().y + 22);
        this.add(lineImg9);

        JLabel lineImg10 = new JLabel("");
        lineImg10.setOpaque(true);
        lineImg10.setBackground(new Color(0, 0, 0, 255));
        lineImg10.setSize(5, labels.get(11).getLocation().y - labels.get(10).getLocation().y);
        lineImg10.setLocation(labels.get(10).getLocation().x + 22, labels.get(10).getLocation().y + 22);
        this.add(lineImg10);

        JLabel lineImg11 = new JLabel("");
        lineImg11.setOpaque(true);
        lineImg11.setBackground(new Color(0, 0, 0, 255));
        lineImg11.setSize(5, labels.get(12).getLocation().y - labels.get(11).getLocation().y);
        lineImg11.setLocation(labels.get(11).getLocation().x + 22, labels.get(11).getLocation().y + 22);
        this.add(lineImg11);

        JLabel lineImg12 = new JLabel("");
        lineImg12.setOpaque(true);
        lineImg12.setBackground(new Color(0, 0, 0, 255));
        lineImg12.setSize(labels.get(12).getLocation().x - labels.get(13).getLocation().x, 5);
        lineImg12.setLocation(labels.get(13).getLocation().x + 22, labels.get(13).getLocation().y + 22);
        this.add(lineImg12);

        JLabel lineImg13 = new JLabel("");
        lineImg13.setOpaque(true);
        lineImg13.setBackground(new Color(0, 0, 0, 255));
        lineImg13.setSize(labels.get(13).getLocation().x - labels.get(14).getLocation().x, 5);
        lineImg13.setLocation(labels.get(14).getLocation().x + 22, labels.get(14).getLocation().y + 22);
        this.add(lineImg13);

        JLabel lineImg14 = new JLabel("");
        lineImg14.setOpaque(true);
        lineImg14.setBackground(new Color(0, 0, 0, 255));
        lineImg14.setSize(5, labels.get(14).getLocation().y - labels.get(15).getLocation().y);
        lineImg14.setLocation(labels.get(15).getLocation().x + 22, labels.get(15).getLocation().y + 22);
        this.add(lineImg14);

        JLabel lineImg15 = new JLabel("");
        lineImg15.setOpaque(true);
        lineImg15.setBackground(new Color(0, 0, 0, 255));
        lineImg15.setSize(5, labels.get(15).getLocation().y - labels.get(8).getLocation().y);
        lineImg15.setLocation(labels.get(8).getLocation().x + 22, labels.get(8).getLocation().y + 22);
        this.add(lineImg15);
        //################################
        distanceNode /= 2;
        distanceNode *= 3;

        labels.get(0).setLocation(WindowCenterX - distanceNode, WindowCenterY - distanceNode);
        labels.get(1).setLocation(WindowCenterX, WindowCenterY - distanceNode);
        labels.get(2).setLocation(WindowCenterX + distanceNode, WindowCenterY - distanceNode);
        labels.get(3).setLocation(WindowCenterX + distanceNode, WindowCenterY);
        labels.get(4).setLocation(WindowCenterX + distanceNode, WindowCenterY + distanceNode);
        labels.get(5).setLocation(WindowCenterX, WindowCenterY + distanceNode);
        labels.get(6).setLocation(WindowCenterX - distanceNode, WindowCenterY + distanceNode);
        labels.get(7).setLocation(WindowCenterX - distanceNode, WindowCenterY);

        JLabel lineImg16 = new JLabel("");
        lineImg16.setOpaque(true);
        lineImg16.setBackground(new Color(0, 0, 0, 255));
        lineImg16.setSize(labels.get(1).getLocation().x - labels.get(0).getLocation().x, 5);
        lineImg16.setLocation(labels.get(0).getLocation().x + 22, labels.get(0).getLocation().y + 22);
        this.add(lineImg16);

        JLabel lineImg17 = new JLabel("");
        lineImg17.setOpaque(true);
        lineImg17.setBackground(new Color(0, 0, 0, 255));
        lineImg17.setSize(labels.get(2).getLocation().x - labels.get(1).getLocation().x, 5);
        lineImg17.setLocation(labels.get(1).getLocation().x + 22, labels.get(1).getLocation().y + 22);
        this.add(lineImg17);

        JLabel lineImg18 = new JLabel("");
        lineImg18.setOpaque(true);
        lineImg18.setBackground(new Color(0, 0, 0, 255));
        lineImg18.setSize(5, labels.get(3).getLocation().y - labels.get(2).getLocation().y);
        lineImg18.setLocation(labels.get(2).getLocation().x + 22, labels.get(2).getLocation().y + 22);
        this.add(lineImg18);

        JLabel lineImg19 = new JLabel("");
        lineImg19.setOpaque(true);
        lineImg19.setBackground(new Color(0, 0, 0, 255));
        lineImg19.setSize(5, labels.get(4).getLocation().y - labels.get(3).getLocation().y);
        lineImg19.setLocation(labels.get(3).getLocation().x + 22, labels.get(3).getLocation().y + 22);
        this.add(lineImg19);

        JLabel lineImg20 = new JLabel("");
        lineImg20.setOpaque(true);
        lineImg20.setBackground(new Color(0, 0, 0, 255));
        lineImg20.setSize(labels.get(4).getLocation().x - labels.get(5).getLocation().x, 5);
        lineImg20.setLocation(labels.get(5).getLocation().x + 22, labels.get(5).getLocation().y + 22);
        this.add(lineImg20);

        JLabel lineImg21 = new JLabel("");
        lineImg21.setOpaque(true);
        lineImg21.setBackground(new Color(0, 0, 0, 255));
        lineImg21.setSize(labels.get(5).getLocation().x - labels.get(6).getLocation().x, 5);
        lineImg21.setLocation(labels.get(6).getLocation().x + 22, labels.get(6).getLocation().y + 22);
        this.add(lineImg21);

        JLabel lineImg22 = new JLabel("");
        lineImg22.setOpaque(true);
        lineImg22.setBackground(new Color(0, 0, 0, 255));
        lineImg22.setSize(5, labels.get(6).getLocation().y - labels.get(7).getLocation().y);
        lineImg22.setLocation(labels.get(7).getLocation().x + 22, labels.get(7).getLocation().y + 22);
        this.add(lineImg22);

        JLabel lineImg23 = new JLabel("");
        lineImg23.setOpaque(true);
        lineImg23.setBackground(new Color(0, 0, 0, 255));
        lineImg23.setSize(5, labels.get(7).getLocation().y - labels.get(0).getLocation().y);
        lineImg23.setLocation(labels.get(0).getLocation().x + 22, labels.get(0).getLocation().y + 22);
        this.add(lineImg23);

        //#########################################
        JLabel lineImg24 = new JLabel("");
        lineImg24.setOpaque(true);
        lineImg24.setBackground(new Color(0, 0, 0, 255));
        lineImg24.setSize(5, labels.get(17).getLocation().y - labels.get(1).getLocation().y);
        lineImg24.setLocation(labels.get(1).getLocation().x + 22, labels.get(1).getLocation().y + 22);
        this.add(lineImg24);

        JLabel lineImg25 = new JLabel("");
        lineImg25.setOpaque(true);
        lineImg25.setBackground(new Color(0, 0, 0, 255));
        lineImg25.setSize(labels.get(3).getLocation().x - labels.get(19).getLocation().x, 5);
        lineImg25.setLocation(labels.get(19).getLocation().x + 22, labels.get(19).getLocation().y + 22);
        this.add(lineImg25);

        JLabel lineImg26 = new JLabel("");
        lineImg26.setOpaque(true);
        lineImg26.setBackground(new Color(0, 0, 0, 255));
        lineImg26.setSize(5, labels.get(5).getLocation().y - labels.get(21).getLocation().y);
        lineImg26.setLocation(labels.get(21).getLocation().x + 22, labels.get(21).getLocation().y + 22);
        this.add(lineImg26);

        JLabel lineImg27 = new JLabel("");
        lineImg27.setOpaque(true);
        lineImg27.setBackground(new Color(0, 0, 0, 255));
        lineImg27.setSize(labels.get(23).getLocation().x - labels.get(7).getLocation().x, 5);
        lineImg27.setLocation(labels.get(7).getLocation().x + 22, labels.get(7).getLocation().y + 22);
        this.add(lineImg27);

        //#########################################
        JTextArea console = new JTextArea();
        console.setSize(400, 60);
        console.setForeground(new Color(255, 255, 255, 255));
        console.setFont(new Font(Font.SANS_SERIF, 20, 20));
        console.setBackground(new Color(100, 0, 0, 180));
        console.setLocation(100, 20);
        console.setFocusable(false);
        console.setBorder(null);
        console.setText("daniel es gay");
        console.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 5));
        this.add(console);

        //#########################################
        JButton startGame = new JButton("¡ Jugar !");
        startGame.setSize(300, 50);
        startGame.setForeground(new Color(255, 255, 255, 255));
        startGame.setFont(new Font(Font.SANS_SERIF, 20, 20));
        startGame.setBackground(new Color(80, 0, 0, 255));
        startGame.setLocation((this.getWidth() / 2) - 270, this.getHeight() - 115);
        startGame.setFocusPainted(false);
        this.add(startGame);

        // ######################################
        String[] options = {"Máquina vs Máquina", "Humano vs Máquina"};
        final JList mode = new JList(options);
        mode.setSize(200, 48);
        mode.setLocation((startGame.getLocation().x + startGame.getWidth() + 30), this.getHeight() - 115);
        mode.setSelectedIndex(1);
        mode.setFont(new Font(Font.SANS_SERIF, 15, 15));
        mode.setSelectionBackground(new Color(55, 71, 79, 255));
        mode.setSelectionForeground(new Color(255, 255, 255, 255));
        this.add(mode);

        // ######################################
        this.repaint();
        this.setVisible(true);

        startGame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                String selected = mode.getSelectedValue().toString();
                if (selected.equals("Máquina vs Máquina")) {
                    game.startGame(Messages.TYPE_MACHINE_MACHINE);
                } else {
                    game.startGame(Messages.TYPE_HUMAN_MACHINE);

                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseEntered(MouseEvent me) {
            }

            @Override
            public void mouseExited(MouseEvent me) {
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 883, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 709, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
