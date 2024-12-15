package modele.armes;

import modele.strategy.weaponStrategy.*;

/**
 * Classe représentant une arme de type fusil (Gun) dans le jeu.
 * Le fusil peut tirer dans une direction donnée et infliger des dégâts aux joueurs ou objets à portée.
 */
public class Gun extends Weapon {

    /**
     * Constructeur pour initialiser un fusil avec un nom, un nombre de munitions, une portée, et des dégâts.
     *
     * @param name      Le nom du fusil.
     * @param munitions Le nombre initial de munitions.
     * @param range     La portée du tir.
     * @param damage    Les dégâts infligés par le fusil.
     */
    public Gun(String name, int munitions, int range, int damage) {
        super(name, damage, range, munitions);
    }

    /**
     * Constructeur alternatif pour initialiser un fusil avec un nom, une portée, et des dégâts.
     * Le nombre de munitions par défaut est fixé à 60.
     *
     * @param name   Le nom du fusil.
     * @param range  La portée du tir.
     * @param damage Les dégâts infligés par le fusil.
     */
    public Gun(String name, int range, int damage) {
        super(name, damage, range, 60);
    }

    /**
     * Initialise la stratégie du fusil.
     * La stratégie définie est un comportement de tir directionnel {@link DirectionalShootBehavior}.
     */
    @Override
    protected void initializeStrategy() {
        this.strategy = new DirectionalShootBehavior(getRange(), getDamage());
    }
}
