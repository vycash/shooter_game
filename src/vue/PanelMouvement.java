package vue;
import modele.util.*;
import modele.components.*;
import modele.*;
import java.util.*;
import modele.armes.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.*;


public class PanelMouvement extends JPanel {

    private JTextField playerNumber;
    private JPanel directionPanel;  // Panel pour les directions
    private boolean isDirectionPanelVisible = false;  // Etat du panel des directions

    public PanelMouvement (Grid matrice, PanelGrid fenetreJeu) {
        this.setLayout(new GridLayout(2, 5, 10, 100));  // GridLayout pour aligner les boutons
        this.setBackground(Color.LIGHT_GRAY);

        // Création du bouton "Se déplacer"
        JButton moveBoutton = new JButton("Se déplacer");
        moveBoutton.setFont(new Font("Arial", Font.PLAIN, 20));
        moveBoutton.setBackground(Color.RED);
        moveBoutton.setForeground(Color.WHITE);
        moveBoutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleDirectionPanel();  // Afficher ou cacher le panel des directions
            }
        });

        // Création des boutons de direction
        JButton upBouton = new JButton("⬆");
        upBouton.setFont(new Font("Arial", Font.PLAIN, 50));
        upBouton.setBackground(Color.CYAN);
        upBouton.setForeground(Color.BLACK);
        upBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appeler la méthode pour déplacer le joueur vers le haut
                // matrice.deplacer("haut");
                System.out.println("Déplacement vers le haut");
                toggleDirectionPanel();  // Cacher le panel après avoir fait un choix
            }
        });

        JButton downBouton = new JButton("⬇");
        downBouton.setFont(new Font("Arial", Font.PLAIN, 50));
        downBouton.setBackground(Color.CYAN);
        downBouton.setForeground(Color.BLACK);
        downBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appeler la méthode pour déplacer le joueur vers le bas
                // matrice.deplacer("bas");
                System.out.println("Déplacement vers le bas");
                toggleDirectionPanel();  // Cacher le panel après avoir fait un choix
            }
        });

        JButton rightBouton = new JButton(" ⮕ ");
        rightBouton.setFont(new Font("Arial", Font.PLAIN, 50));
        rightBouton.setBackground(Color.CYAN);
        rightBouton.setForeground(Color.BLACK);
        rightBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appeler la méthode pour déplacer le joueur vers la droite
                // matrice.deplacer("droite");
                System.out.println("Déplacement vers la droite");
                toggleDirectionPanel();  // Cacher le panel après avoir fait un choix
            }
        });

        JButton leftBouton = new JButton("⬅");
        leftBouton.setFont(new Font("Arial", Font.PLAIN, 50));
        leftBouton.setBackground(Color.CYAN);
        leftBouton.setForeground(Color.BLACK);
        leftBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appeler la méthode pour déplacer le joueur vers la gauche
                // matrice.deplacer("gauche");
                System.out.println("Déplacement vers la gauche");
                toggleDirectionPanel();  // Cacher le panel après avoir fait un choix
            }
        });

        // Panel contenant les 4 boutons de direction (initialement masqué)
        directionPanel = new JPanel(new GridLayout(1, 4));  // Utilisation d'un GridLayout pour aligner les boutons horizontalement
        directionPanel.add(leftBouton);
        directionPanel.add(upBouton);
        directionPanel.add(downBouton);
        directionPanel.add(rightBouton);
        directionPanel.setVisible(false);  // On cache le panel de direction au départ

        // Création du champ de texte pour afficher le numéro du joueur
        playerNumber = new JTextField();
        playerNumber.setText("Player n°");  // On ajoute l'indice du joueur ici
        playerNumber.setFont(new Font("Arial", Font.PLAIN, 20));
        playerNumber.setHorizontalAlignment(JLabel.CENTER);
        playerNumber.setEditable(false);

        // Ajout des composants au panneau
        this.add(moveBoutton);  // Ajout du bouton "Se déplacer"
        this.add(playerNumber);  // Ajout du champ de texte pour le numéro du joueur
        this.add(directionPanel);  // Ajout du panel des directions
    }

    // Méthode pour afficher/masquer le panel des directions
    private void toggleDirectionPanel() {
        isDirectionPanelVisible = !isDirectionPanelVisible;  // Inverser l'état de visibilité
        directionPanel.setVisible(isDirectionPanelVisible);  // Afficher ou masquer le panel

        // Re-dimensionner et redessiner le panneau pour que les changements soient visibles
        revalidate();
        repaint();
    }
}
