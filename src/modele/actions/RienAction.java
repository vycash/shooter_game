package modele.actions;

import modele.components.*;

/**
 * Représente une action où le joueur ne fait rien.
 * Cette classe hérite de la classe abstraite {@link Action}.
 */
public class RienAction extends Action {

    /**
     * Constructeur de la classe RienAction.
     * 
     * @param type Le type de l'action (fixé à "r" pour rien).
     * @param p Le joueur associé à cette action.
     */
    public RienAction(String type, Player p) {
        super("r", p);
    }

    /**
     * Renvoie une description textuelle de l'action de ne rien faire.
     * 
     * @return Une chaîne indiquant que le joueur n'a rien fait.
     */
    @Override
    public String toString() {
        return "Le joueur " + getPlayer().getName() + " n'a rien fait.";
    }
}
