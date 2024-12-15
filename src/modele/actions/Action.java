package modele.actions;

import modele.components.*;

/**
 * Classe abstraite représentant une action dans le jeu.
 * Les actions sont effectuées par les joueurs et peuvent inclure des déplacements,
 * des attaques ou d'autres interactions.
 */
public abstract class Action {

    /**
     * Le type d'action (par exemple, "m" pour déplacement, "s" pour bouclier, etc.).
     */
    private String type;

    /**
     * Le joueur qui effectue cette action.
     */
    private Player p;

    /**
     * Constructeur de la classe Action.
     * 
     * @param type Le type d'action.
     * @param p Le joueur associé à cette action.
     */
    public Action(String type, Player p) {
        this.type = type;
        this.p = p;
    }

    /**
     * Récupère le type de l'action.
     * 
     * @return Une chaîne représentant le type de l'action.
     */
    public String getActionType() {
        return this.type;
    }

    /**
     * Récupère le joueur associé à cette action.
     * 
     * @return Le joueur qui effectue l'action.
     */
    public Player getPlayer() {
        return this.p;
    }
}
