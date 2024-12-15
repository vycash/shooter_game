package modele.armes;

import modele.strategy.weaponStrategy.*;
import modele.components.Component;
import modele.components.Player;

/**
 * Classe représentant une bombe dans le jeu.
 * La bombe peut être utilisée comme une arme ou un piège (mine).
 * Elle implémente l'interface {@link Component} pour permettre l'interaction avec les joueurs.
 */
public class Bomb extends Weapon implements Component {

    /**
     * Temps restant avant l'explosion de la bombe.
     */
    private int timer;

    /**
     * Indique si la bombe est une mine (active en cas de contact).
     */
    private boolean mine;

    /**
     * Identifiant du joueur qui possède ou a placé la bombe.
     */
    private int owner;

    /**
     * Constructeur de la classe Bomb.
     *
     * @param name  Le nom de la bombe.
     * @param damage Les dégâts causés par l'explosion.
     * @param range La portée de l'explosion.
     * @param timer Le temps restant avant l'explosion.
     * @param isMine Indique si la bombe est une mine.
     * @param owner L'identifiant du joueur qui possède ou a placé la bombe.
     */
    public Bomb(String name, int damage, int range, int timer, boolean isMine, int owner) {
        super(name, damage, range, 1);
        this.timer = timer;
        this.mine = isMine;
        this.owner = owner;
    }

    /**
     * Constructeur alternatif pour créer une bombe sans limite de temps.
     *
     * @param name Le nom de la bombe.
     * @param damage Les dégâts causés par l'explosion.
     * @param range La portée de l'explosion.
     * @param isMine Indique si la bombe est une mine.
     * @param owner L'identifiant du joueur qui possède ou a placé la bombe.
     */
    public Bomb(String name, int damage, int range, boolean isMine, int owner) {
        this(name, damage, range, Integer.MAX_VALUE, isMine, owner);
    }

    /**
     * Initialise la stratégie de la bombe (explosion).
     */
    @Override
    protected void initializeStrategy() {
        this.strategy = new ExplosionBehavior(getDamage());
    }

    /**
     * Retourne l'identifiant du propriétaire de la bombe.
     *
     * @return L'identifiant du joueur.
     */
    public int getOwnerID() {
        return this.owner;
    }

    /**
     * Vérifie si un joueur est le propriétaire de la bombe.
     *
     * @param id L'identifiant du joueur.
     * @return True si le joueur est le propriétaire, false sinon.
     */
    public boolean isOwner(int id) {
        return this.owner == id;
    }

    /**
     * Retourne le temps restant avant l'explosion de la bombe.
     *
     * @return Le temps restant (en tours).
     */
    public int getTimer() {
        return this.timer;
    }

    /**
     * Réinitialise le temps de la bombe si ce n'est pas une mine.
     */
    public void reset() {
        if (!mine) {
            this.timer = 4;
        }
    }

    /**
     * Vérifie si la bombe est une mine.
     *
     * @return True si c'est une mine, false sinon.
     */
    public boolean isMine() {
        return this.mine;
    }

    /**
     * Réduit le temps restant avant l'explosion de la bombe.
     */
    public void decrease() {
        this.timer--;
    }

    /**
     * Déclenche l'interaction de la bombe avec un joueur (explosion).
     *
     * @param p Le joueur interagissant avec la bombe.
     */
    @Override
    public void interact(Player p) {
        System.out.println("Boom " + p);
    }
}
