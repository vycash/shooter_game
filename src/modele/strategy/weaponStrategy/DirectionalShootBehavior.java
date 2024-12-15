package modele.strategy.weaponStrategy;

import modele.components.Case;
import modele.components.Player;
import modele.util.PlayerManager;

/**
 * Comportement pour une arme effectuant un tir directionnel.
 * Le tir se propage dans une direction donnée jusqu'à atteindre une portée maximale
 * ou jusqu'à rencontrer un obstacle ou un joueur.
 */
public class DirectionalShootBehavior implements WeaponBehavior {

    private int range; // Portée maximale du tir
    private int damage; // Dégâts infligés par le tir

    /**
     * Constructeur pour initialiser la portée et les dégâts du comportement de tir.
     *
     * @param range Portée maximale du tir.
     * @param damage Dégâts infligés par le tir.
     */
    public DirectionalShootBehavior(int range, int damage) {
        this.range = range;
        this.damage = damage;
    }

    /**
     * Exécute le tir directionnel à partir d'une position donnée dans une direction spécifiée.
     * Le tir se propage case par case jusqu'à atteindre un joueur, un mur ou dépasser la portée.
     *
     * @param x Coordonnée X de départ du tir.
     * @param y Coordonnée Y de départ du tir.
     * @param direction Direction du tir ("h" pour haut, "b" pour bas, "g" pour gauche, "d" pour droite).
     * @param cases Grille de jeu représentée par un tableau 2D de cases.
     * @param playerManager Gestionnaire des joueurs pour vérifier la présence de joueurs dans les cases.
     */
    @Override
    public void execute(int x, int y, String direction, Case[][] cases, PlayerManager playerManager) {
        int dx = 0, dy = 0;

        // Définir les décalages en fonction de la direction
        switch (direction) {
            case "h": dx = -1; break; // haut
            case "b": dx = 1; break;  // bas
            case "g": dy = -1; break; // gauche
            case "d": dy = 1; break;  // droite
            default:
                System.out.println("Invalid direction.");
                return;
        }

        // Parcourir les cases dans la direction donnée jusqu'à la portée maximale
        for (int k = 1; k <= range; k++) {
            x += dx;
            y += dy;

            // Vérifier si le tir dépasse les limites de la grille
            if (x < 0 || x >= cases.length || y < 0 || y >= cases[0].length) {
                System.out.println("Shot out of bounds.");
                break;
            }

            Case targetCase = cases[x][y];

            // Vérifier si la case contient un joueur
            if (!targetCase.isWall() && playerManager.playerExists(targetCase)) {
                Player target = (Player) targetCase.getVal();
                if (target != null) {
                    target.damage(damage); // Infliger des dégâts au joueur

                    if (!target.isShieldActive()) {
                        System.out.println("Player " + target.getID() + " was hit and took " + damage + " damage!");
                    }
                    break; // Arrêter le tir après avoir touché un joueur
                }
            }
        }
    }
}
