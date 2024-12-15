package modele.components;

import java.util.*;
import config.*;


/**
 * Classe représentant la grille principale du jeu.
 * Cette classe est responsable de l'initialisation, de la gestion et de la manipulation des cases de la grille.
 * Elle suit un modèle singleton pour garantir une seule instance globale.
 */
public class Grid implements Affichage {

    /**
     * Instance unique de la grille (singleton).
     */
    private static Grid instance;
     /**
     * Tableau de cases représentant la grille du jeu.
     */
    public Case[][] cases;

    /**
     * Nombre de lignes de la grille.
     */
    public int nbLignes;

    /**
     * Nombre de colonnes de la grille.
     */
    private int nbColonnes;

    /**
     * Directions utilisées pour naviguer dans la grille (droite, bas, gauche, haut).
     */
    private static final int[][] DIRECTIONS = {
        {0, 1},   // droite
        {1, 0},   // bas
        {0, -1},  // gauche
        {-1, 0}   // haut 
    };

    /**
     * Retourne l'instance unique de la grille.
     * Si aucune instance n'existe, elle est créée.
     * 
     * @return L'instance unique de la grille.
     */
    public static Grid getInstance(){
        if(instance == null){
            instance = new Grid(Constants.NB_LIGNES,Constants.NB_COLS,Constants.WALL_DENSITY);
        }
        return instance;
    }

    /**
     * Constructeur de la grille. Initialise les dimensions et génère un labyrinthe intéressant.
     * 
     * @param nbLignes Nombre de lignes de la grille.
     * @param nbColonnes Nombre de colonnes de la grille.
     * @param wallDensity Densité des murs dans la grille.
     */

    public Grid(int nbLignes, int nbColonnes,double wallDensity) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.cases = new Case[nbLignes][nbColonnes];
        initializeGrid();
        generateInterestingMaze(wallDensity);
    }

    /**
     * Retourne le nombre de lignes de la grille.
     * 
     * @return Nombre de lignes.
     */
    public int getNbLines() {
        return this.nbLignes;
    }

    /**
     * Retourne le nombre de colonnes de la grille.
     * 
     * @return Nombre de colonnes.
     */
    public int getNbColonnes() {
        return this.nbColonnes;
    }

    /**
     * Retourne toutes les cases de la grille.
     * 
     * @return Tableau de cases.
     */
    public Case[][] getCases(){
        return this.cases;
    }

    /**
     * Retourne une case spécifique de la grille.
     * 
     * @param ligne Ligne de la case.
     * @param col Colonne de la case.
     * @return La case à la position donnée.
     */
    public Case getCase(int ligne,int col){
        return cases[ligne][col];
    }

    /**
     * Vérifie si une cellule est vide (aucune valeur n'est définie).
     * 
     * @param x Coordonnée x de la cellule.
     * @param y Coordonnée y de la cellule.
     * @return {@code true} si la cellule est vide, sinon {@code false}.
     */
    public boolean emptyCell(int x, int y) {
        return cases[x][y].getVal() == null;
    }

    /**
     * Vérifie si des coordonnées sont valides et non bloquées par un mur.
     * 
     * @param line Ligne à vérifier.
     * @param col Colonne à vérifier.
     * @return {@code true} si les coordonnées sont valides, sinon {@code false}.
     */
    public boolean validCoordinates(int line,int col){
        return line >= 0 && line < nbLignes && col >= 0 && col < nbColonnes && !cases[line][col].isWall();
    }

    /**
     * Initialise toutes les cases de la grille en tant que murs.
     */
    public void initializeGrid() {
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                // Crée toutes les cases comme des murs
                cases[i][j] = new Case(i, j, true);
            }
        }
    }

    /**
     * Génère un labyrinthe intéressant en ajoutant des couloirs, des chambres, des murs aléatoires, etc.
     * 
     * @param wallDensity Densité des murs aléatoires.
     */
    public void generateInterestingMaze(double wallDensity) {
        generateMaze(1, 1);              // Étape 1 : Génération de couloirs
        addRooms(Constants.NB_ROOMS, Constants.ROOM_SIZE);                 // Étape 2 : Ajout de chambres
        addPillars();                    // Étape 3 : Ajout de piliers
        addRandomWalls(wallDensity);     // Étape 4 : Ajouter des murs aléatoires
        addRandomComponents(Constants.HEALTH_DENSITY, Constants.AMMO_DENSITY); 
        reinforceBorders();              // Étape 5 : Restaurer les murs aux contours
    }
    
    /**
     * Génère un labyrinthe de base en utilisant une méthode de backtracking.
     * 
     * @param startX Coordonnée x de départ.
     * @param startY Coordonnée y de départ.
     */
    public void generateMaze(int startX, int startY) {
        List<int[]> stack = new ArrayList<>();
        stack.add(new int[]{startX, startY});

        while (!stack.isEmpty()) {
            int[] current = stack.remove(stack.size() - 1);
            int x = current[0];
            int y = current[1];

            if (!isValidCell(x, y)) continue;

            cases[x][y].setVisited(true);
            cases[x][y].setWall(false);

            List<int[]> neighbors = new ArrayList<>();
            for (int[] dir : DIRECTIONS) {
                int newX = x + dir[0] * 2;
                int newY = y + dir[1] * 2;
                if (isValidCell(newX, newY)) {
                    neighbors.add(new int[]{newX, newY});
                }
            }

            Collections.shuffle(neighbors);
            for (int[] neighbor : neighbors) {
                int nx = neighbor[0];
                int ny = neighbor[1];
                if (isValidCell(nx, ny)) {
                    removeWall(x, y, nx, ny);
                    stack.add(neighbor);
                }
            }
        }
    }

    /**
     * Retire un mur entre deux cases adjacentes.
     * 
     * @param x1 Ligne de la première case.
     * @param y1 Colonne de la première case.
     * @param x2 Ligne de la deuxième case.
     * @param y2 Colonne de la deuxième case.
     */
    public void removeWall(int x1, int y1, int x2, int y2) {
        int wallX = (x1 + x2) / 2;
        int wallY = (y1 + y2) / 2;
        cases[wallX][wallY].setWall(false);
    }

    public boolean isValidCell(int x, int y) {
        return x > 0 && x < nbLignes - 1 && y > 0 && y < nbColonnes - 1 && cases[x][y].isWall();
    }

    public void addRooms(int numRooms, int roomSize) {
        Random random = new Random();
        for (int i = 0; i < numRooms; i++) {
            int startX = random.nextInt(nbLignes - roomSize - 1) + 1;
            int startY = random.nextInt(nbColonnes - roomSize - 1) + 1;
            for (int x = startX; x < startX + roomSize; x++) {
                for (int y = startY; y < startY + roomSize; y++) {
                    cases[x][y].setWall(false); // Zone ouverte
                }
            }
        }
    }

    public void addPillars() {
        for (int i = 2; i < nbLignes - 1; i += 4) {
            for (int j = 2; j < nbColonnes - 1; j += 4) {
                cases[i][j].setWall(true); // Ajout de piliers
            }
        }
    }

    public void addRandomWalls(double wallDensity) {
        Random random = new Random();
        for (int i = 1; i < nbLignes - 1; i++) {
            for (int j = 1; j < nbColonnes - 1; j++) {
                if (!cases[i][j].isWall() && random.nextDouble() < wallDensity) {
                    cases[i][j].setWall(true); // Ajout d'un mur aléatoire
                }
            }
        }
    }

    public void reinforceBorders() {
        for (int i = 0; i < nbLignes; i++) {
            cases[i][0].setWall(true);                 // Mur gauche
            cases[i][nbColonnes - 1].setWall(true);    // Mur droit
        }
        for (int j = 0; j < nbColonnes; j++) {
            cases[0][j].setWall(true);                 // Mur haut
            cases[nbLignes - 1][j].setWall(true);      // Mur bas
        }
    }    

    public void addRandomComponents(double healthDensity, double ammoDensity) {
        Random random = new Random();
        for (int i = 1; i < nbLignes - 1; i++) {
            for (int j = 1; j < nbColonnes - 1; j++) {
                if (!cases[i][j].isWall() && cases[i][j].isEmpty()) {
                    double chance = random.nextDouble();
                    if (chance < healthDensity) {
                        cases[i][j].setVal(new Health()); // Ajouter un soin
                    } else if (chance < healthDensity + ammoDensity) {
                        cases[i][j].setVal(new Ammo()); // Ajouter des munitions
                    }
                }
            }
        }
    }

     /**
     * Retourne une représentation textuelle de la grille.
     * 
     * @return Représentation textuelle de la grille.
     */
    @Override
    public String AfficheGrid(){
        return this.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbColonnes; j++) {
                builder.append(cases[i][j].isWall() ? "#" : " ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
