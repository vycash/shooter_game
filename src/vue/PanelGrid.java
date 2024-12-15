package vue;

import config.*;
import modele.*;
import modele.components.*;
import modele.armes.*;
import javax.swing.*;
import java.awt.*;

public class PanelGrid extends JPanel {

    private static final int nbColonnes = Constants.NB_COLS;
    private static final int nbLignes = Constants.NB_LIGNES;
    private final Game game; 

    public PanelGrid(Game game) {
        this.game = game;
        this.setPreferredSize(new Dimension(nbColonnes * 100, nbLignes * 100)); // Taille des cases
    }

    public void updateGrid() {
        this.repaint(); // rafraîchir la grille
    }

    @Override
    protected void paintComponent(Graphics g) {
        Case[][] currentGrid = game.getCurrentGrid();
        super.paintComponent(g);

        int largeurCellule = this.getWidth() / nbColonnes;
        int hauteurCellule = this.getHeight() / nbLignes;

        // Parcourir la grille
        for (int y = 0; y < nbLignes; y++) {
            for (int x = 0; x < nbColonnes; x++) {
                Case currentCase = currentGrid[y][x]; // Obtenir la case actuelle
                drawCell(g, currentCase, x, y, largeurCellule, hauteurCellule); // Dessiner la cellule
            }
        }
    }

    /**
     * Dessine une cellule en fonction de son contenu.
     */
    private void drawCell(Graphics g, Case caseType, int x, int y, int largeur, int hauteur) {
        if (caseType.isWall()) {
            drawWallCell(g, x, y, largeur, hauteur);
            return;
        } else if (caseType.getVal() instanceof Bomb) {
            g.setColor(Color.BLACK); // Bombe
        } else if (caseType.getVal() instanceof Player) {
            Player player = (Player) caseType.getVal();
            g.setColor(player.getColor()); 
            
            drawPlayerCell(g, player, x, y, largeur, hauteur);
            return;
        } else if (caseType.getVal() instanceof Health) {
            g.setColor(Color.GREEN); // Bonus santé
        } else if (caseType.getVal() instanceof Ammo) {
            g.setColor(Color.ORANGE); // Bonus munitions
        } else {
            g.setColor(Color.WHITE); // Case vide
        }

        g.fillRect(x * largeur, y * hauteur, largeur, hauteur);

        g.setColor(Color.BLACK);
        g.drawRect(x * largeur, y * hauteur, largeur, hauteur);

        // Ajouter du texte pour les éléments spécifiques
        if (caseType.getVal() instanceof Bomb) {
            g.setColor(Color.YELLOW);
            g.drawString("B", x * largeur + largeur / 4, y * hauteur + hauteur / 2);
        }
    }

    /**
     * Dessine une cellule spéciale pour un joueur avec un design unique.
     */
    private void drawPlayerCell(Graphics g, Player player, int x, int y, int largeur, int hauteur) {
        // Dessiner la cellule avec un fond arrondi et une bordure épaisse
        g.setColor(player.getColor());
        g.fillRoundRect(x * largeur, y * hauteur, largeur, hauteur, 20, 20); // Cellule arrondie

        // Ajouter une bordure épaisse pour le joueur
        g.setColor(Color.BLACK);
        g.drawRoundRect(x * largeur, y * hauteur, largeur, hauteur, 20, 20); // Bordure épaisse

        // Ajouter un texte centralisé dans la cellule
        g.setColor(Color.WHITE); // Texte en blanc pour le joueur
        String playerText = "P" + player.getID();
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(playerText);
        int textHeight = fm.getHeight();
        g.drawString(playerText, x * largeur + (largeur - textWidth) / 2, y * hauteur + (hauteur + textHeight) / 2);
    }

    /**
     * Dessine une cellule pour un mur avec un motif unique.
     */
   private void drawWallCell(Graphics g, int x, int y, int largeur, int hauteur) {
    
    Graphics2D g2d = (Graphics2D) g;

    g.setColor(Color.GRAY);
    g.fillRect(x * largeur, y * hauteur, largeur, hauteur); 

    // Ajouter un motif de hachures (ou lignes diagonales) pour différencier visuellement les murs
    g.setColor(Color.DARK_GRAY); 
    for (int i = 0; i < largeur; i += 10) {
        g.drawLine(x * largeur + i, y * hauteur, x * largeur + i + 10, y * hauteur + hauteur); 
    }

    // Dessiner la bordure du mur avec une ligne plus épaisse
    g2d.setColor(Color.BLACK);
    g2d.setStroke(new BasicStroke(3));
    g2d.drawRect(x * largeur, y * hauteur, largeur, hauteur); 
    g2d.setStroke(new BasicStroke(1));

    }

}
