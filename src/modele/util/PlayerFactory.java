package modele.util;

import java.util.*;
import modele.armes.Bomb;
import modele.armes.Weapon;
import modele.components.Player;
import modele.strategy.playerStrategy.*;

/**
 * Classe responsable de la création de joueurs dans le jeu.
 * Cette fabrique permet de construire des joueurs avec des armes et des stratégies variées.
 */
public class PlayerFactory {

    private Random rand;
    private WeaponFactory wb;

    /**
     * Constructeur de la fabrique de joueurs.
     * Initialise les objets nécessaires pour générer des joueurs et leurs armes.
     */
    public PlayerFactory() {
        this.rand = new Random();
        this.wb = new WeaponFactory();
    }

    /**
     * Génère un joueur avec un nom par défaut et un nombre aléatoire d'armes.
     *
     * @return Un joueur avec des armes aléatoires.
     */
    public Player buildRandomPlayer() {
        Player p = new Player("P");
        p.addWeapons(wb.buildWeapons(rand.nextInt(10), p.getID()));
        return p;
    }

    /**
     * Crée un joueur avec un nom spécifique mais sans armes.
     *
     * @param name Le nom du joueur.
     * @return Un joueur avec le nom spécifié.
     */
    public Player buildPlayer(String name) {
        return new Player(name);
    }

    /**
     * Crée un joueur avec un nom spécifique et un nombre donné d'armes.
     *
     * @param name Le nom du joueur.
     * @param nbWeapons Le nombre d'armes à attribuer au joueur.
     * @return Un joueur avec le nom spécifié et des armes.
     */
    public Player buildPlayer(String name, int nbWeapons) {
        Player p = new Player(name);
        p.addWeapons(wb.buildWeapons(nbWeapons, p.getID()));
        return p;
    }

    /**
     * Crée un joueur avec un nom spécifique et une liste d'armes prédéfinies.
     *
     * @param name Le nom du joueur.
     * @param weapons La liste d'armes à attribuer au joueur.
     * @return Un joueur avec le nom spécifié et les armes fournies.
     */
    public Player buildPlayer(String name, List<Weapon> weapons) {
        return new Player(name, weapons);
    }

    /**
     * Crée un joueur aléatoire parmi les trois types possibles :
     * un joueur soldat, un sniper ou un joueur aléatoire.
     *
     * @return Un joueur avec un type défini aléatoirement.
     */
    public Player buildPlayer() {
        int choice = rand.nextInt(3);

        switch (choice) {
            case 0:
                return soldat();
            case 1:
                return sniper();
            default:
                return buildRandomPlayer();
        }
    }

    /**
     * Crée un joueur de type soldat avec une stratégie agressive.
     *
     * @return Un joueur soldat avec des armes prédéfinies.
     */
    public Player soldat() {
        Player p = new Player("S");

        Weapon pi = wb.pistol();
        Weapon k = wb.kalashnikov();
        Bomb g = wb.grenade(p.getID());

        p.addWeapons(List.of(pi, k, g));
        p.setStrategy(new Aggressive());

        return p;
    }

    /**
     * Crée un joueur de type sniper avec une stratégie offensive.
     *
     * @return Un joueur sniper avec des armes prédéfinies.
     */
    public Player sniper() {
        Player p = new Player("S");

        Weapon pi = wb.pistol();
        Weapon k = wb.kalashnikov();
        Bomb m = wb.mine(p.getID());

        p.addWeapons(List.of(pi, k, m));
        p.setStrategy(new Offensive());

        return p;
    }
}
