package modele.actions;

import modele.armes.*;
import modele.components.*;

/**
 * Représente une action de tir effectuée par un joueur.
 * Cette classe hérite de la classe abstraite {@link Action}.
 */
public class ShootAction extends Action {

    /**
     * Arme utilisée pour effectuer le tir.
     */
    private Weapon weapon;

    /**
     * Direction dans laquelle le joueur tire.
     */
    private String direction;

    /**
     * Constructeur de la classe ShootAction.
     *
     * @param weapon L'arme utilisée pour le tir.
     * @param direction La direction dans laquelle le joueur tire (ex. "haut", "bas", etc.).
     * @param player Le joueur qui effectue l'action de tir.
     */
    public ShootAction(Weapon weapon, String direction, Player player) {
        super("t", player);
        this.direction = direction;
        this.weapon = weapon;
    }

    /**
     * Retourne l'arme utilisée pour le tir.
     *
     * @return L'arme utilisée.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }

    /**
     * Retourne la direction du tir.
     *
     * @return La direction du tir.
     */
    public String getDirection() {
        return this.direction;
    }

    /**
     * Renvoie une description textuelle de l'action de tir.
     *
     * @return Une chaîne indiquant que le joueur a tiré avec une arme
     *         dans une direction donnée.
     */
    @Override
    public String toString() {
        return "Le joueur " + getPlayer().getName() + " a tiré avec " + weapon + " vers le " + direction;
    }
}
