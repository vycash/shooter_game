package modele.actions;

import modele.components.*;

/**
 * Représente une action de déplacement effectuée par un joueur dans le jeu.
 * Cette classe hérite de la classe abstraite {@link Action}.
 */
public class MoveAction extends Action {

    /**
     * La direction vers laquelle le joueur se déplace (par exemple, "haut", "bas", "gauche", "droite").
     */
    private String direction;

    /**
     * Constructeur de la classe MoveAction.
     * 
     * @param direction La direction du déplacement.
     * @param p Le joueur qui effectue l'action de déplacement.
     */
    public MoveAction(String direction, Player p) {
        super("m", p);
        this.direction = direction;
    }

    /**
     * Récupère la direction du déplacement.
     * 
     * @return Une chaîne représentant la direction du déplacement.
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Renvoie une description textuelle de l'action de déplacement.
     * 
     * @return Une chaîne indiquant le joueur et la direction de son déplacement.
     */
    @Override
    public String toString() {
        return "Le joueur " + getPlayer().getName() + " a bougé vers le " + direction;
    }
}
