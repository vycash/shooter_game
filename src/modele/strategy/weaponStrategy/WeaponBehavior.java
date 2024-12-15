package modele.strategy.weaponStrategy;

import modele.components.Case;
import modele.util.PlayerManager;

/**
 * Interface définissant le comportement des armes.
 * Chaque arme peut avoir une stratégie spécifique pour attaquer ou interagir avec la grille de jeu.
 */
public interface WeaponBehavior {

    /**
     * Exécute une action spécifique de l'arme sur la grille de jeu.
     *
     * @param x Coordonnée X de départ de l'action.
     * @param y Coordonnée Y de départ de l'action.
     * @param direction Direction dans laquelle l'action est effectuée (exemple : "h" pour haut, "b" pour bas).
     * @param cases Grille de jeu représentée par un tableau 2D de cases.
     * @param playerManager Gestionnaire des joueurs, utilisé pour accéder aux informations et interactions avec les joueurs.
     */
    void execute(int x, int y, String direction, Case[][] cases, PlayerManager playerManager);
}
