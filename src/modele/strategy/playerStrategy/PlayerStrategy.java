package modele.strategy.playerStrategy;

import modele.components.*;
import modele.actions.*;

/**
 * Interface représentant une stratégie de jeu pour un joueur.
 * Les stratégies définissent comment un joueur décide de son action
 * en fonction de son état actuel et des règles de jeu.
 */
public interface PlayerStrategy {

    /**
     * Détermine l'action à effectuer pour un joueur donné dans un état de jeu donné.
     *
     * @param player Le joueur pour lequel l'action est déterminée.
     * @return Une instance de {@link Action} représentant l'action choisie par la stratégie.
     * Les actions possibles incluent :
     * <ul>
     *   <li>Déplacement : {@link MoveAction} ("h", "b", "g", "d").</li>
     *   <li>Tir : {@link ShootAction} ("t").</li>
     *   <li>Activation du bouclier : {@link ShieldAction} ("s").</li>
     *   <li>Pas d'action : {@link RienAction} ("r").</li>
     * </ul>
     */
    Action getAction(Player player);
}
