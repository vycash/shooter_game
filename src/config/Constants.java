package config;

import java.util.*;

/** Classe contenant les constantes utilisées dans le jeu. */
public class Constants {

    /** Nombre de lignes dans la grille du jeu. */
    public static final int NB_LIGNES = 20;

    /** Nombre de colonnes dans la grille du jeu. */
    public static final int NB_COLS = 40;

    /** Nombre initial de joueurs dans le jeu. */
    public static int NB_JOUEURS = 5;

    /** Densité des murs dans la grille (pourcentage de cases qui sont des murs). */
    public static final double WALL_DENSITY = 0.05;

    /** Densité des pastilles d'énergie dans la grille (pourcentage de cases qui sont des Health). */
    public static final double HEALTH_DENSITY = 0.02;

    /** Densité des pastilles de munitions dans la grille (pourcentage de cases qui sont des Ammo). */
    public static final double AMMO_DENSITY = 0.02;

    /** Nombre de chambres dans la grille. */
    public static final int NB_ROOMS = 20;

    /** Taille d'une chambre dans la grille. */
    public static final int ROOM_SIZE = 5;

    /** Énergie initiale de chaque joueur. */
    public static final int INITIAL_ENERGY = 100;

    /** Coût en énergie pour effectuer un déplacement. */
    public static final int MOVE_COST = 5;

    /** Coût en énergie pour activer le bouclier. */
    public static final int SHIELD_COST = 10;

    /** Dégâts infligés par une bombe. */
    public static final int BOMB_DAMAGE = 15;

    /** Dégâts infligés par une mine. */
    public static final int MINE_DAMAGE = 15;

    /** Temps avant l'explosion d'une bombe (en tours). */
    public static final int BOMB_TIMER = 3;

    /** Constante pour représenter le déplacement vers le haut. */
    public static final String HAUT = "h";

    /** Constante pour représenter le déplacement vers le bas. */
    public static final String BAS = "b";

    /** Constante pour représenter le déplacement vers la gauche. */
    public static final String GAUCHE = "g";

    /** Constante pour représenter le déplacement vers la droite. */
    public static final String DROITE = "d";

    /** Ensemble des directions possibles dans le jeu. */
    public static final Set<String> DIRECTIONS = new HashSet<>(Set.of("h", "g", "b", "d"));

    /**
     * Retourne une direction aléatoire parmi les directions possibles.
     * @return Une chaîne de caractères représentant une direction aléatoire ("h", "g", "b", ou "d").
     */
    public static String getRandomDirection() {
        // Convertit le Set en tableau pour un accès indexé.
        String[] directionArray = DIRECTIONS.toArray(new String[0]);
        Random random = new Random();
        return directionArray[random.nextInt(directionArray.length)];
    }
}
