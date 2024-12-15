package modele.strategy.playerStrategy;

import java.util.*;
import config.Constants;
import modele.components.*;
import modele.actions.*;

/**
 * Stratégie aléatoire pour un joueur.
 * Cette stratégie décide de l'action du joueur de manière aléatoire entre plusieurs options :
 * <ul>
 *   <li>Ne rien faire.</li>
 *   <li>Activer le bouclier.</li>
 *   <li>Tirer avec une arme.</li>
 *   <li>Se déplacer dans une direction aléatoire.</li>
 * </ul>
 */
public class RandomBehaviour implements PlayerStrategy {

    /**
     * Détermine une action aléatoire pour le joueur.
     * Les actions possibles incluent :
     * <ul>
     *   <li>Activer le bouclier ({@link ShieldAction}).</li>
     *   <li>Tirer avec une arme ({@link ShootAction}).</li>
     *   <li>Se déplacer ({@link MoveAction}).</li>
     *   <li>Ne rien faire ({@link RienAction}).</li>
     * </ul>
     *
     * @param player Le joueur pour lequel l'action est déterminée.
     * @return Une instance de {@link Action} représentant l'action choisie.
     */
    @Override
    public Action getAction(Player player) {
        Random random = new Random();
        int action = random.nextInt(4); // Décide aléatoirement entre 4 actions possibles
        Action a = new RienAction("r", player); // Action par défaut : ne rien faire
        switch(action) {
            case 1:
                return new ShieldAction(player); // Activer le bouclier
            case 2:
                return new ShootAction(player.getRandomWeapon(), Constants.getRandomDirection(), player); // Tirer
            case 3:
                return new MoveAction(Constants.getRandomDirection(), player); // Se déplacer
        }
        return a; // Retourne l'action par défaut
    }

    /**
     * Retourne une représentation sous forme de chaîne de la stratégie.
     *
     * @return Le nom de la stratégie ("RandomStrategy").
     */
    @Override
    public String toString() {
        return "RandomStrategy";
    }
}
