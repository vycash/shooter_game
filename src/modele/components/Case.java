package modele.components;

import modele.armes.Bomb;

/**
 * Représente une case de la grille du jeu.
 * Une case peut contenir un mur, être vide, ou contenir un composant spécifique
 * comme un joueur, une bombe ou un bonus.
 */
public class Case {

    /**
     * Indique si la case est un mur.
     */
    private boolean wall;

    /**
     * Indique si la case a été visitée.
     */
    private boolean visited;

    /**
     * Contient le composant présent sur la case (joueur, arme, bonus, etc.).
     */
    private Component val;

    /**
     * Coordonnée ligne de la case dans la grille.
     */
    private int line;

    /**
     * Coordonnée colonne de la case dans la grille.
     */
    private int col;

    /**
     * Constructeur principal d'une case.
     * 
     * @param line La coordonnée ligne.
     * @param col La coordonnée colonne.
     * @param val Le composant présent sur la case.
     * @param wall Indique si la case est un mur.
     */
    public Case(int line, int col, Component val, boolean wall) {
        this.wall = wall;
        this.visited = false;
        this.val = val;
        this.line = line;
        this.col = col;
    }

    /**
     * Constructeur d'une case sans mur par défaut.
     * 
     * @param line La coordonnée ligne.
     * @param col La coordonnée colonne.
     * @param val Le composant présent sur la case.
     */
    public Case(int line, int col, Component val) {
        this(line, col, val, false);
    }

    /**
     * Constructeur d'une case avec seulement ses coordonnées et un mur par défaut.
     * 
     * @param line La coordonnée ligne.
     * @param col La coordonnée colonne.
     * @param wall Indique si la case est un mur.
     */
    public Case(int line, int col, boolean wall) {
        this(line, col, null, wall);
    }

    /**
     * Constructeur d'une case uniquement avec l'indicateur de mur.
     * 
     * @param wall Indique si la case est un mur.
     */
    public Case(boolean wall) {
        this(-1, -1, true);
    }

    /**
     * Vérifie si la case est vide (pas de composant).
     * 
     * @return {@code true} si la case est vide, sinon {@code false}.
     */
    public boolean isEmpty() {
        return this.val == null;
    }

    /**
     * Vérifie si la case est un mur.
     * 
     * @return {@code true} si la case est un mur, sinon {@code false}.
     */
    public boolean isWall() {
        return wall;
    }

    /**
     * Vérifie si la case contient un joueur.
     * 
     * @return {@code true} si un joueur est présent, sinon {@code false}.
     */
    public boolean containsPlayer() {
        return this.val instanceof Player;
    }

    /**
     * Retourne le composant présent sur la case.
     * 
     * @return Le composant sur la case.
     */
    public Component getVal() {
        return this.val;
    }

    /**
     * Définit un composant sur la case.
     * 
     * @param val Le composant à définir.
     */
    public void setVal(Component val) {
        this.val = val;
        System.out.println("========= j'ai changé !!");
        // TODO : Ajouter un mécanisme pour notifier les changements.
    }

    /**
     * Définit les coordonnées de la case.
     * 
     * @param line La coordonnée ligne.
     * @param col La coordonnée colonne.
     */
    public void setCoordinates(int line, int col) {
        this.line = line;
        this.col = col;
    }

    /**
     * Retourne les coordonnées de la case sous forme de tableau.
     * 
     * @return Les coordonnées ligne et colonne de la case.
     */
    public Integer[] getCoordinates() {
        return new Integer[] { this.line, this.col };
    }

    /**
     * Retourne la coordonnée ligne de la case.
     * 
     * @return La coordonnée ligne.
     */
    public int getLine() {
        return this.line;
    }

    /**
     * Retourne la coordonnée colonne de la case.
     * 
     * @return La coordonnée colonne.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Définit la coordonnée ligne de la case.
     * 
     * @param x La nouvelle coordonnée ligne.
     */
    public void setLigne(int x) {
        this.line = x;
    }

    /**
     * Définit la coordonnée colonne de la case.
     * 
     * @param x La nouvelle coordonnée colonne.
     */
    public void setCol(int x) {
        this.col = x;
    }

    /**
     * Définit si la case est un mur.
     * 
     * @param wall {@code true} si la case devient un mur, sinon {@code false}.
     */
    public void setWall(boolean wall) {
        this.wall = wall;
    }

    /**
     * Vérifie si la case a été visitée.
     * 
     * @return {@code true} si la case est visitée, sinon {@code false}.
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Définit l'état de visite de la case.
     * 
     * @param visited {@code true} si la case est visitée, sinon {@code false}.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Gère l'interaction entre un joueur et cette case.
     * Si la case contient un composant, ce dernier interagit avec le joueur.
     * Si elle est vide, le joueur est placé sur la case.
     * 
     * @param player Le joueur qui interagit avec la case.
     */
    public void interact(Player player) {
        if (val != null) {
            val.interact(player);
        }
        if (!containsPlayer()) {
            setVal(player);
        } else {
            System.out.println("Case contains Player");
        }
    }

    /**
     * Retourne une représentation textuelle de la case.
     * Permet de visualiser la grille de jeu.
     * 
     * @return Une chaîne de caractères représentant la case.
     */
    @Override
    public String toString() {
        if (isWall()) {
            return "##";
        } else if (val != null) {
            if (val instanceof Bomb) {
                Bomb bomb = (Bomb) val;
                return bomb.isMine() ? "mi" : "b" + bomb.getTimer();
            }
            return val.toString();
        }
        return "  ";
    }
}
