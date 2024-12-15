package modele.strategy.weaponStrategy;

import modele.components.*;
import modele.util.*;

/**
 * Comportement pour une arme effectuant une explosion.
 * L'explosion inflige des dégâts à toutes les entités (joueurs) dans une zone 3x3 centrée sur une position donnée.
 */
public class ExplosionBehavior implements WeaponBehavior {

    private int damage; // Dégâts infligés par l'explosion

    /**
     * Constructeur pour initialiser les dégâts de l'explosion.
     *
     * @param damage Dégâts infligés par l'explosion.
     */
    public ExplosionBehavior(int damage) {
        this.damage = damage;
    }

    /**
     * Exécute une explosion à partir d'une position donnée.
     * L'explosion affecte toutes les cases voisines (dans une zone 3x3) en infligeant des dégâts aux joueurs présents.
     *
     * @param x Coordonnée X du centre de l'explosion.
     * @param y Coordonnée Y du centre de l'explosion.
     * @param direction Non utilisé dans ce contexte, l'explosion est omnidirectionnelle.
     * @param cases Grille de jeu représentée par un tableau 2D de cases.
     * @param playerManager Gestionnaire des joueurs pour vérifier la présence de joueurs dans les cases.
     */
    @Override
    public void execute(int x, int y, String direction, Case[][] cases, PlayerManager playerManager) {

        // Parcourir une zone 3x3 autour de la position (x, y)
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int nx = x + i;
                int ny = y + j;

                // Vérifier si les coordonnées sont valides
                if (nx >= 0 && nx < cases.length && ny >= 0 && ny < cases[0].length) {
                    Case targetCase = cases[nx][ny];
                    Object val = targetCase.getVal();

                    // Infliger des dégâts si un joueur est présent sur la case
                    if (val instanceof Player) {
                        Player target = (Player) val;
                        target.damage(damage);
                        System.out.println("Player " + target.getID() + " was hit by the explosion and took " + damage + " damage!");
                    }
                }
            }
        }
    }
}
