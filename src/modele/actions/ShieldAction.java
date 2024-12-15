package modele.actions;

import modele.components.*;

/**
 * Représente une action où un joueur active son bouclier.
 * Cette classe hérite de la classe abstraite {@link Action}.
 */
public class ShieldAction extends Action {

    /**
     * Constructeur de la classe ShieldAction.
     * 
     * @param p Le joueur qui effectue l'action d'activer son bouclier.
     */
    public ShieldAction(Player p) {
        super("s", p);
    }

    /**
     * Renvoie une description textuelle de l'action d'activation du bouclier.
     * 
     * @return Une chaîne indiquant que le joueur a activé son bouclier.
     */
    @Override
    public String toString() {
        return "Le joueur " + getPlayer().getName() + " a activé son bouclier.";
    }
}
