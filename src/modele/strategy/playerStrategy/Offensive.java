package modele.strategy.playerStrategy;

import modele.components.*;
import java.util.Random;
import config.Constants;
import modele.actions.*;
import modele.armes.Weapon;

/**
 * Implémente une stratégie offensive pour le joueur.
 * Cette stratégie favorise une approche agressive :
 * - Si une arme est disponible, le joueur décide aléatoirement de tirer ou de se déplacer.
 * - Si aucune arme n'est disponible, le joueur se déplace dans une direction aléatoire.
 */
public class Offensive implements PlayerStrategy {

    /**
     * Détermine l'action à effectuer selon la stratégie offensive.
     * - Tente de tirer dans une direction aléatoire si une arme est disponible.
     * - Sinon, effectue un déplacement dans une direction aléatoire.
     *
     * @param player Le joueur auquel la stratégie est appliquée.
     * @return L'action choisie (tir ou mouvement).
     */
    @Override
    public Action getAction(Player player) {
        Random random = new Random();
        boolean shouldShoot = random.nextBoolean(); // Décision aléatoire : tirer ou se déplacer

        if (shouldShoot) {
            // Tente de tirer si une arme est disponible
            Weapon weapon = player.getRandomWeapon();
            if (weapon != null) {
                return new ShootAction(weapon, Constants.getRandomDirection(), player);
            }
        }

        // Si tirer n'est pas possible ou choisi, effectuer un déplacement aléatoire
        return new MoveAction(Constants.getRandomDirection(), player);
    }

    /**
     * Fournit une description textuelle de cette stratégie.
     *
     * @return Une chaîne de caractères indiquant "OffensiveStrategy".
     */
    @Override
    public String toString() {
        return "OffensiveStrategy";
    }
}
