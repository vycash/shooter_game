package modele.util;

import java.util.*;
import modele.armes.*;
import config.Constants;

/**
 * Fabrique permettant de créer différentes armes pour les joueurs.
 * Elle offre des méthodes pour construire des armes aléatoires, spécifiques ou personnalisées.
 */
public class WeaponFactory {

    private Random rand;

    /**
     * Constructeur de la classe WeaponFactory.
     * Initialise un générateur de nombres aléatoires.
     */
    public WeaponFactory() {
        this.rand = new Random();
    }

    /**
     * Crée une arme aléatoire avec des caractéristiques variables.
     *
     * @return Une instance de {@link Gun} avec des attributs aléatoires.
     */
    public Weapon buildRandomWeapon() {
        int val = rand.nextInt(20);
        return new Gun("Weapon" + val, rand.nextInt(50), rand.nextInt(50));
    }

    /**
     * Crée une arme basée sur un choix aléatoire parmi plusieurs types.
     *
     * @param playerID L'identifiant du joueur propriétaire de l'arme.
     * @return Une instance de {@link Weapon}.
     */
    public Weapon buildWeapon(int playerID) {
        int choice = rand.nextInt(6);

        switch (choice) {
            case 0:
                return pistol();
            case 1:
                return kalashnikov();
            case 2:
                return sniper();
            case 3:
                return grenade(playerID);
            case 4:
                return mine(playerID);
            default:
                return buildRandomWeapon();
        }
    }

    /**
     * Crée un pistolet avec des caractéristiques fixes.
     *
     * @return Une instance de {@link Gun} représentant un pistolet.
     */
    public Weapon pistol() {
        return new Gun("pistol", 5, 5, 10);
    }

    /**
     * Crée une Kalachnikov avec des caractéristiques fixes.
     *
     * @return Une instance de {@link Gun} représentant une Kalachnikov.
     */
    public Weapon kalashnikov() {
        return new Gun("AK-47", 10, 15);
    }

    /**
     * Crée un fusil de précision avec des caractéristiques fixes.
     *
     * @return Une instance de {@link Gun} représentant un fusil de précision.
     */
    public Weapon sniper() {
        return new Gun("sniper", 20, 20, 20);
    }

    /**
     * Crée une grenade avec des caractéristiques fixes.
     *
     * @param playerID L'identifiant du joueur propriétaire de la grenade.
     * @return Une instance de {@link Bomb} représentant une grenade.
     */
    public Bomb grenade(int playerID) {
        return new Bomb("Grenade", Constants.BOMB_DAMAGE, 1, Constants.BOMB_TIMER, false, playerID);
    }

    /**
     * Crée une mine avec des caractéristiques fixes.
     *
     * @param playerID L'identifiant du joueur propriétaire de la mine.
     * @return Une instance de {@link Bomb} représentant une mine.
     */
    public Bomb mine(int playerID) {
        return new Bomb("Mine", Constants.MINE_DAMAGE, 1, true, playerID);
    }

    /**
     * Crée une liste d'armes pour un joueur donné.
     *
     * @param nbWeapons Le nombre d'armes à créer.
     * @param playerID L'identifiant du joueur propriétaire des armes.
     * @return Une liste d'instances de {@link Weapon}.
     */
    public List<Weapon> buildWeapons(int nbWeapons, int playerID) {
        List<Weapon> weapons = new ArrayList<>();
        for (int i = 0; i < nbWeapons; i++) {
            weapons.add(buildWeapon(playerID));
        }
        return weapons;
    }
}
