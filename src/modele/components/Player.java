package modele.components;

import java.util.*;
import modele.armes.Weapon;
import modele.strategy.playerStrategy.*;
import config.Constants;
import modele.actions.*;
import java.awt.Color;


/**
 * Classe représentant un joueur dans le jeu.
 * Chaque joueur possède des caractéristiques comme un nom, une liste d'armes, une énergie, et un état de vie.
 * Les joueurs peuvent exécuter des actions en fonction de leur stratégie.
 */
public class Player implements Component {
    
    /**
     * Nom du joueur.
     */
    private String Name;

    /**
     * Liste des armes du joueur.
     */
    private List<Weapon> weapons;

    /**
     * Niveau d'énergie du joueur.
     */
    private int energy;

    /**
     * Indique si le joueur est en vie.
     */
    private boolean alive;

    /**
     * Indique si le bouclier est actif.
     */
    private boolean isShieldActive;

    /**
     * ID unique pour chaque joueur.
     */
    private static int dernierID = 0; 
    private int iD;

    /**
     * Stratégie du joueur pour déterminer ses actions.
     */
    private PlayerStrategy strategy;

    /**
     * Constructeur principal du joueur.
     * 
     * @param Name Nom du joueur.
     * @param weapons Liste des armes du joueur.
     * @param strat Stratégie utilisée par le joueur.
     */
    public Player(String Name, List<Weapon> weapons, PlayerStrategy strat) {
        this.weapons = weapons;
        this.energy = Constants.INITIAL_ENERGY;
        this.alive = true;
        this.iD = ++dernierID;
        this.Name = Name + iD;
        this.isShieldActive = false;
        this.strategy = strat;
    }

    /**
     * Constructeur simplifié avec une liste d'armes et une stratégie par défaut.
     * 
     * @param Name Nom du joueur.
     * @param weapons Liste des armes du joueur.
     */
    public Player(String Name, List<Weapon> weapons) {
        this(Name, weapons, new RandomBehaviour());
    }

    /**
     * Constructeur avec uniquement un nom, sans armes ni stratégie.
     * 
     * @param Name Nom du joueur.
     */
    public Player(String Name) {
        this(Name, new ArrayList<>());
    }

    /**
     * Vérifie si le joueur est en vie.
     * 
     * @return true si le joueur est en vie, false sinon.
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Obtient le nom du joueur.
     * 
     * @return Nom du joueur.
     */
    public String getName() {
        return Name;
    }

    /**
     * Définit le nom du joueur.
     * 
     * @param Name Nouveau nom du joueur.
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Obtient la stratégie actuelle du joueur.
     * 
     * @return Stratégie du joueur.
     */
    public PlayerStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * Définit une nouvelle stratégie pour le joueur.
     * 
     * @param strategy Nouvelle stratégie.
     */
    public void setStrategy(PlayerStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Obtient la liste des armes du joueur.
     * 
     * @return Liste des armes.
     */
    public List<Weapon> getWeapons() {
        return weapons;
    }

    /**
     * Obtient l'ID unique du joueur.
     * 
     * @return ID du joueur.
     */
    public int getID() {
        return this.iD;
    }

    /**
     * Obtient le niveau d'énergie du joueur.
     * 
     * @return Niveau d'énergie.
     */
    public int getEnergy() {
        return this.energy;
    }

    /**
     * Vérifie si le bouclier est actif.
     * 
     * @return true si le bouclier est actif, false sinon.
     */
    public boolean isShieldActive() {
        return isShieldActive;
    }

    /**
     * Active le bouclier du joueur pour un tour.
     */
    public void activateShield() {
        if (!isShieldActive) {
            System.out.println("Shield activated for one turn!");
            isShieldActive = true;
        } else {
            System.out.println("Shield is already active!");
        }
    }

    /**
     * Applique des dégâts au joueur. Si le bouclier est actif, aucun dégât n'est pris.
     * 
     * @param damage Quantité de dégâts à appliquer.
     */
    public void damage(int damage) {
        if (isShieldActive) {
            System.out.println("Shield is active! No damage taken.");
            isShieldActive = false; // Le bouclier est désactivé après usage
        } else {
            if (damage > 0 && damage < 100) {
                this.energy -= damage;
            }
            if (energy <= 0) {
                this.alive = false;
                System.out.println("=========== Player " + Name + iD + " killed in action");
            }
        }
    }

    /**
     * Réduit l'énergie du joueur lors d'un déplacement.
     */
    public void move() {
        this.energy -= Constants.MOVE_COST;
    }

    /**
     * Soigne le joueur en ajoutant une quantité d'énergie.
     * 
     * @param value Quantité d'énergie à ajouter.
     */
    public void heal(int value) {
        if (value > 0 && value < 100) {
            this.energy += value;
        }
    }

    /**
     * Sélectionne une arme aléatoire dans l'inventaire du joueur.
     * 
     * @return Arme sélectionnée ou null si aucune arme n'est disponible.
     */
    public Weapon getRandomWeapon() {
        if (weapons == null || weapons.isEmpty()) {
            System.out.println("Le joueur " + Name + " n'a aucune arme.");
            return null;
        }

        Random random = new Random();
        return weapons.get(random.nextInt(weapons.size()));
    }

    /**
     * Ajoute une arme à l'inventaire du joueur.
     * 
     * @param weapon Arme à ajouter.
     */
    public void addWeapon(Weapon weapon) {
        this.weapons.add(weapon);
    }

    /**
     * Ajoute une liste d'armes à l'inventaire du joueur.
     * 
     * @param weapons Liste des armes à ajouter.
     */
    public void addWeapons(List<Weapon> weapons) {
        this.weapons.addAll(weapons);
    }

    /**
     * Ajoute des munitions à toutes les armes du joueur.
     * 
     * @param ammo Quantité de munitions à ajouter.
     */
    public void addAmmo(int ammo) {
        for (Weapon w : weapons) {
            w.ajouterMunitions(ammo);
        }
    }

    /**
     * Détermine l'action du joueur en fonction de sa stratégie.
     * 
     * @return Action à effectuer.
     */
    public Action getAction() {
        if (strategy != null) {
            return strategy.getAction(this);
        } else {
            throw new IllegalStateException("Player strategy is not set.");
        }
    }

    @Override
    public void interact(Player p) {
        System.out.println("Salut " + p);
    }

    @Override
    public String toString() {
        final String[] COLORS = {
            "\u001B[31m", // Rouge
            "\u001B[34m", // Bleu
            "\u001B[35m", // Magenta
            "\u001B[36m", // Cyan
        };
        final String ANSI_RESET = "\u001B[0m";
    
        String color = COLORS[(iD - 1) % COLORS.length];
        return color + Name + ANSI_RESET;
    }



    public Color getColor() {
        // Palette de couleurs
        Color[] colors = {
            Color.RED,    // Rouge
            Color.BLUE,   // Bleu            
            new Color(128, 0, 128), 
            new Color(128, 128, 0),
           // new Color(75, 0, 130), 
           // new Color(244, 164, 96),
            new Color(233, 150, 122),
           // new Color(127, 255, 212),
           // new Color(138, 43, 226),
            new Color(147, 112, 219)
        };
        // Retourne une couleur basée sur l'ID (modulo pour cycler les couleurs)
        return colors[(iD - 1) % colors.length];
    }

    

}
