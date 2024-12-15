package modele.components;

import config.Constants;
import modele.armes.Bomb;

/**
 * Classe ProxyGrid permettant d'afficher une vue personnalisée de la grille pour un joueur spécifique.
 * Elle masque certaines informations (comme les bombes d'autres joueurs) pour maintenir une logique de visibilité limitée.
 */
public class ProxyGrid implements Affichage {

    /**
     * Le joueur pour lequel la vue est générée.
     */
    private Player subjectPlayer;

    /**
     * Référence à la grille principale du jeu.
     */
    private Grid grid;

    /**
     * Constructeur de ProxyGrid.
     * 
     * @param grid Grille principale du jeu.
     */
    public ProxyGrid(Grid grid) {
        this.subjectPlayer = null;
        this.grid = grid;
    }

    /**
     * Définit le joueur pour lequel la grille sera affichée.
     * 
     * @param player Joueur courant.
     */
    public void setPlayer(Player player) {
        this.subjectPlayer = player;
    }

    /**
     * Génère une représentation en chaîne de caractères de la grille.
     * Les bombes appartenant à d'autres joueurs ne sont pas affichées.
     * 
     * @return Représentation en chaîne de la grille.
     */
    @Override
    public String AfficheGrid() {
        int currentPlayerID = subjectPlayer.getID();
        StringBuilder representation = new StringBuilder();

        for (int i = 0; i < Constants.NB_LIGNES; i++) {
            for (int j = 0; j < Constants.NB_COLS; j++) {

                Case currentCase = this.grid.getCases()[i][j];
                // Si la case contient une bombe, afficher uniquement si le joueur courant est le propriétaire
                if (currentCase.getVal() instanceof Bomb) {
                    if (((Bomb) currentCase.getVal()).isOwner(currentPlayerID)) {
                        representation.append(this.grid.getCase(i, j).toString());
                    } else {
                        representation.append("  "); // Masquer la bombe
                    }
                } else {
                    representation.append(this.grid.getCase(i, j).toString());
                }
            }
            representation.append("\n");
        }
        return representation.toString();
    }

    /**
     * Retourne une grille personnalisée sous forme de tableau de cases.
     * Les bombes appartenant à d'autres joueurs sont remplacées par des cases vides.
     * 
     * @return Tableau de cases personnalisé pour le joueur courant.
     */
    public Case[][] getCurrentGrid() {
        if (subjectPlayer == null) {
            return this.grid.getCases(); // Si aucun joueur n'est défini, retourner la grille originale
        }

        int currentPlayerID = subjectPlayer.getID();
        Case[][] originalGrid = grid.getCases();
        Case[][] customizedGrid = new Case[Constants.NB_LIGNES][Constants.NB_COLS];

        for (int i = 0; i < Constants.NB_LIGNES; i++) {
            for (int j = 0; j < Constants.NB_COLS; j++) {
                Case currentCase = originalGrid[i][j];

                // Remplacer les bombes appartenant à d'autres joueurs par des cases vides
                if (currentCase.getVal() instanceof Bomb && !((Bomb) currentCase.getVal()).isOwner(currentPlayerID)) {
                    customizedGrid[i][j] = new Case(i, j, currentCase.isWall()); // Case vide
                } else {
                    customizedGrid[i][j] = currentCase; // Conserver la case originale
                }
            }
        }

        return customizedGrid;
    }
}
