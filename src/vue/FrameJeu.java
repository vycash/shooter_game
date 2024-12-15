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





public class FrameJeu extends JFrame {

    public FrameJeu(int size, Grid grid) {
        this.setLayout(new GridLayout(1, 2));
        this.setSize(size, (size/2) + 40); 
        this.setTitle("La grille du jeu");

        // Panneau de la grille
        /* PanelGrid fenetreJeu = new PanelGrid(); 
        this.add(fenetreJeu);

        // Panneau des boutons
        PanelBoutons boutons = new PanelBoutons(grid, fenetreJeu);
        this.add(boutons); */

        // Configurations de la fenêtre
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

