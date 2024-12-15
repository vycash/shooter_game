package modele.strategy.playerStrategy;

import modele.components.*;
import config.Constants;
import modele.actions.*;

/**
 * Implémente une stratégie défensive pour le joueur.
 * Cette stratégie priorise la protection du joueur lorsqu'il est en danger.
 * - Si l'énergie du joueur est inférieure à 50, le bouclier est activé.
 * - Sinon, le joueur se déplace dans une direction aléatoire.
 */
public class Defensive implements PlayerStrategy {

    /**
     * Détermine l'action à effectuer selon la stratégie défensive.
     * - Si l'énergie est inférieure à 50, active un bouclier pour protéger le joueur.
     * - Sinon, effectue un déplacement dans une direction aléatoire.
     *
     * @param player Le joueur auquel la stratégie est appliquée.
     * @return L'action choisie (activation du bouclier ou mouvement).
     */
    @Override
    public Action getAction(Player player) {
        // Activer le bouclier si l'énergie du joueur est inférieure à 50
        if (player.getEnergy() < 50) {
            return new ShieldAction(player);
        }
        // Sinon, effectuer un mouvement aléatoire
        return new MoveAction(Constants.getRandomDirection(), player);
    }

    /**
     * Fournit une description textuelle de cette stratégie.
     *
     * @return Une chaîne de caractères indiquant "DefensiveStrategy".
     */
    @Override
    public String toString() {
        return "DefensiveStrategy";
    }
}
