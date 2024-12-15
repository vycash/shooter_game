package modele.armes;

import modele.strategy.weaponStrategy.*;
import modele.components.Case;
import modele.util.*;

/**
 * Classe abstraite représentant une arme dans le jeu.
 * Les armes ont des propriétés de base comme le nom, les dégâts, la portée et les munitions.
 * Chaque type d'arme peut définir son propre comportement via une stratégie.
 * @author Qach
 */
public abstract class Weapon {

    /** Nom de l'arme */
    private String name;

    /** Dégâts infligés par l'arme */
    private int damage;

    /** Portée maximale de l'arme */
    private int range;

    /** Nombre de munitions disponibles */
    private int munitions;

    /** Stratégie définissant le comportement de l'arme */
    protected WeaponBehavior strategy;

    /** ID unique pour chaque arme */
    private static int dernierID = 0;
    private int iD;

    /**
     * Constructeur principal pour initialiser une arme avec des paramètres personnalisés.
     *
     * @param name      Nom de l'arme.
     * @param damage    Dégâts infligés par l'arme.
     * @param range     Portée maximale de l'arme.
     * @param munitions Nombre initial de munitions.
     */
    public Weapon(String name, int damage, int range, int munitions) {
        this.name = name;
        this.damage = damage;
        this.range = range;
        this.munitions = munitions;
        this.iD = ++dernierID;
        initializeStrategy(); // Configure la stratégie dans la sous-classe
    }

    /**
     * Constructeur simplifié pour une arme avec un nombre de munitions par défaut.
     *
     * @param name   Nom de l'arme.
     * @param damage Dégâts infligés par l'arme.
     * @param range  Portée maximale de l'arme.
     */
    public Weapon(String name, int damage, int range) {
        this(name, damage, range, 60);
    }

    /**
     * Méthode abstraite que les sous-classes doivent implémenter pour définir leur stratégie.
     */
    protected abstract void initializeStrategy();

    /**
     * Retourne l'ID unique de l'arme.
     *
     * @return L'ID de l'arme.
     */
    public int getID() {
        return this.iD;
    }

    /**
     * Retourne le nom de l'arme.
     *
     * @return Le nom de l'arme.
     */
    public String getName() {
        return name;
    }

    /**
     * Retourne le nombre de munitions restantes.
     *
     * @return Le nombre de munitions.
     */
    public int getMunitions() {
        return this.munitions;
    }

    /**
     * Modifie le nom de l'arme.
     *
     * @param name Le nouveau nom.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retourne les dégâts infligés par l'arme.
     *
     * @return Les dégâts.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Modifie les dégâts infligés par l'arme.
     *
     * @param damage Les nouveaux dégâts.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Retourne la portée de l'arme.
     *
     * @return La portée.
     */
    public int getRange() {
        return range;
    }

    /**
     * Modifie la portée de l'arme.
     *
     * @param range La nouvelle portée.
     */
    public void setRange(int range) {
        this.range = range;
    }

    /**
     * Ajoute des munitions à l'arme.
     *
     * @param nbMunitionsAjouter Nombre de munitions à ajouter.
     */
    public void ajouterMunitions(int nbMunitionsAjouter) {
        this.munitions += nbMunitionsAjouter;
    }

    /**
     * Diminue le nombre de munitions de l'arme après un tir.
     */
    public void diminuerMuntitions() {
        if (munitions > 0) {
            this.munitions--;
        }
    }

    /**
     * Utilise l'arme pour effectuer une action basée sur sa stratégie.
     *
     * @param x             Coordonnée x de départ.
     * @param y             Coordonnée y de départ.
     * @param direction     Direction du tir.
     * @param cases         Grille du jeu.
     * @param playerManager Gestionnaire des joueurs.
     */
    public final void use(int x, int y, String direction, Case[][] cases, PlayerManager playerManager) {
        if (munitions > 0) {
            strategy.execute(x, y, direction, cases, playerManager);
            diminuerMuntitions();
        } else {
            System.out.println("No munitions left for " + name + ".");
        }
    }

    /**
     * Représente les détails de l'arme sous forme de chaîne.
     *
     * @return Une chaîne décrivant l'arme.
     */
    @Override
    public String toString() {
        return name + ", damage=" + damage + ", range=" + range + ", munitions=" + munitions;
    }
}
