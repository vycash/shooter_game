package vue;
import modele.components.*;
import modele.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



public class FrameIntroduction extends JFrame {

    private boolean player1Ready = false;
    private boolean player2Ready = false;

    public FrameIntroduction(int size, Grid grid) {
        this.setLayout(new GridLayout(2, 1));
        this.setSize(size, size / 2);
        this.setTitle("Introduction au Jeu");

        // Panneau pour le joueur 1
        JPanel panelPlayer1 = new JPanel();
        panelPlayer1.setLayout(new FlowLayout());
        JLabel labelPlayer1 = new JLabel("Player 1 : Commencer ?");
        JButton yesButton1 = new JButton("Oui");
        JButton noButton1 = new JButton("Non");

        panelPlayer1.add(labelPlayer1);
        panelPlayer1.add(yesButton1);
        panelPlayer1.add(noButton1);

        // Panneau pour le joueur 2
        JPanel panelPlayer2 = new JPanel();
        panelPlayer2.setLayout(new FlowLayout());
        JLabel labelPlayer2 = new JLabel("Player 2 : Commencer ?");
        JButton yesButton2 = new JButton("Oui");
        JButton noButton2 = new JButton("Non");

        panelPlayer2.add(labelPlayer2);
        panelPlayer2.add(yesButton2);
        panelPlayer2.add(noButton2);

        // Ajouter les panneaux à la fenêtre
        this.add(panelPlayer1);
        this.add(panelPlayer2);

        // Ajouter des écouteurs d'action pour les boutons
        yesButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Ready = true;
                checkIfBothReady(size, grid);
            }
        });

        noButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Ready = false;
                JOptionPane.showMessageDialog(null, "Player 1 a choisi Non. Le jeu ne commencera pas !");
            }
        });

        yesButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2Ready = true;
                checkIfBothReady(size, grid);
            }
        });

        noButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2Ready = false;
                JOptionPane.showMessageDialog(null, "Player 2 a choisi Non. Le jeu ne commencera pas !");
            }
        });

        // Configurations de la fenêtre
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void checkIfBothReady(int size, Grid grid) {
        if (player1Ready && player2Ready) {
            JOptionPane.showMessageDialog(null, "Les deux joueurs sont prêts ! Le jeu commence !");
            new FrameJeu(size, grid); // Affiche la fenêtre du jeu
            dispose(); // Ferme la fenêtre d'introduction
        }
    }

    public static void main(String[] args) {
        // Exemple d'initialisation pour démarrer le jeu
        Game g = Game.getInstance();
        new FrameIntroduction(700, g.getGrid()); // Ouvre l'écran d'introduction
        g.commencer();
    }
}

