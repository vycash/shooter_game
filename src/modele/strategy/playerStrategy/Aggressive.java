package modele.strategy.playerStrategy;

import modele.components.*;
import config.Constants;
import modele.actions.*;

/**
 * Implémente une stratégie agressive pour un joueur.
 * Cette stratégie favorise les actions offensives en utilisant des armes si elles sont disponibles.
 * Si aucune arme n'est disponible, le joueur se déplacera aléatoirement.
 */
public class Aggressive implements PlayerStrategy {

    /**
     * Détermine l'action à effectuer pour le joueur en fonction de la stratégie agressive.
     * Cette méthode donne priorité à l'utilisation d'une arme si le joueur en possède.
     * Sinon, elle génère un mouvement aléatoire.
     * 
     * @param player Le joueur pour lequel cette stratégie est appliquée.
     * @return Une instance d'Action représentant l'action choisie par le joueur.
     */
    @Override
    public Action getAction(Player player) {
        // Si le joueur possède des armes, effectuer une action de tir.
        if (!player.getWeapons().isEmpty()) {
            return new ShootAction(player.getRandomWeapon(), Constants.getRandomDirection(), player);
        }
        // Sinon, déplacer le joueur dans une direction aléatoire.
        return new MoveAction(Constants.getRandomDirection(), player);
    }

    /**
     * Fournit une description textuelle de la stratégie.
     * 
     * @return Une chaîne de caractères indiquant le type de stratégie.
     */
    @Override
    public String toString() {
        return "AggressiveStrategy";
    }
}
