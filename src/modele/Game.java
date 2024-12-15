package modele;

import java.util.*;
import modele.components.*;
import modele.util.*;
import config.Constants;
import modele.actions.*;
import controller.*;

/**
 * Classe représentant un jeu avec un labyrinthe.
 * Implémentation du patron de conception Singleton.
 */
public class Game extends AbstractModeleEcoutable {
    
    private static Game instance = null;
    private Grid grid;
    private ProxyGrid gridProxy;
    private PlayerManager playerManager;
    private WeaponManager weaponManager;
    private Action currentAction;

    /**
     * Retourne l'instance unique de la classe Game.
     * @return L'instance unique de Game.
     */
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    /**
     * Constructeur privé pour initialiser le jeu et ses composants.
     * Configure la grille, les joueurs, et les gestionnaires.
     */
    private Game() {
        this.grid = Grid.getInstance();
        this.playerManager = new PlayerManager(this);
        this.weaponManager = new WeaponManager(this);
        this.gridProxy = new ProxyGrid(grid);
        playerManager.addPlayers(Constants.NB_JOUEURS);
    }

    /**
     * Vérifie si des coordonnées sont valides dans la grille.
     * @param line La ligne.
     * @param col La colonne.
     * @return true si les coordonnées sont valides, false sinon.
     */
    public boolean validCoordinates(int line, int col) {
        return grid.validCoordinates(line, col);
    }

    /**
     * Retourne une case spécifique de la grille.
     * @param ligne La ligne de la case.
     * @param col La colonne de la case.
     * @return La case correspondante.
     */
    public Case getCase(int ligne, int col) {
        return grid.getCase(ligne, col);
    }

    /**
     * Retourne la grille actuelle.
     * @return La grille du jeu.
     */
    public Grid getGrid() {
        return this.grid;
    }

    /**
     * Retourne l'action en cours dans le jeu.
     * @return L'action actuelle.
     */
    public Action getCurrentAction() {
        return this.currentAction;
    }

    /**
     * Retourne la grille actuelle sous une forme personnalisée pour le joueur.
     * @return La grille actuelle personnalisée.
     */
    public Case[][] getCurrentGrid() {
        return gridProxy.getCurrentGrid();
    }

    /**
     * Retourne le gestionnaire de joueurs.
     * @return Le gestionnaire de joueurs.
     */
    public PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    /**
     * Retourne le gestionnaire d'armes.
     * @return Le gestionnaire d'armes.
     */
    public WeaponManager getWeaponManager() {
        return this.weaponManager;
    }

    /**
     * Démarre le jeu en boucle jusqu'à ce qu'il ne reste qu'un joueur.
     */
    public void commencer() {
        Scanner scanner = new Scanner(System.in);
        while (playerManager.nbPlayers() > 1) {
            System.out.println("Appuyez sur Entrée pour continuer...");
            scanner.nextLine(); // Attend une entrée de l'utilisateur
            advanceTurn(); // Avance d'un tour
        }
    }

    /**
     * Avance le jeu d'un tour en exécutant les actions du joueur et en mettant à jour les bombes.
     */
    public void advanceTurn() {
        if (playerManager.nbPlayers() <= 1) {
            System.out.println("Le jeu est terminé. Le gagnant est : " + playerManager.getPlayers());
            return;
        }

        Player currentPlayer = playerManager.getPlayer();
        gridProxy.setPlayer(currentPlayer);

        afficheGame(currentPlayer);

        boolean actionEffectuée = false;
        while (!actionEffectuée) {
            currentAction = currentPlayer.getAction();
            actionEffectuée = effectuerMouvement(currentAction, currentPlayer);
        }

        if (currentPlayer.isAlive()) {
            playerManager.enfilerPlayer(currentPlayer);
        } else {
            System.out.println("Player " + currentPlayer.getID() + " est mort.");
        }

        playerManager.removeDeadPlayers();
        weaponManager.checkBombs();

        if (playerManager.nbPlayers() <= 1) {
            System.out.println("Le jeu est terminé. Le gagnant est : " + playerManager.getPlayers());
        }
    }

    /**
     * Exécute une action spécifique pour le joueur.
     * @param mouvement L'action à exécuter.
     * @param player Le joueur qui effectue l'action.
     * @return true si l'action a été effectuée avec succès, false sinon.
     */
    private boolean effectuerMouvement(Action mouvement, Player player) {
        String typeMouvement = mouvement.getActionType();
        boolean ok = false;

        try {
            System.out.println(mouvement);
            switch (typeMouvement) {
                case "m":
                    MoveAction a = (MoveAction) mouvement;
                    ok = playerManager.deplacerPlayer(player, a.getDirection());
                    break;
                case "s":
                    playerManager.activateShield(player);
                    ok = true;
                    break;
                case "t":
                    ShootAction b = (ShootAction) mouvement;
                    ok = weaponManager.shoot(player, b.getDirection(), b.getWeapon());
                    break;
                case "r":
                    ok = true;
                    break;
                default:
                    System.out.println("\n --- !!!  Mouvement non valide. Utilisez 'h' pour haut, 'b' pour bas, 'g' pour gauche, 'd' pour droite. !!! --- \n");
            }
            fireChangement();
        } catch (Exception e) {
            System.out.println("Mouvement non valide: " + e.getMessage());
        }
        return ok;
    }

    /**
     * Retourne la représentation actuelle du jeu sous forme de chaîne.
     * @return La représentation actuelle du jeu.
     */
    @Override
    public String toString() {
        StringBuilder GameRepresentation = new StringBuilder();
        for (int i = 0; i < Constants.NB_LIGNES; i++) {
            for (int j = 0; j < Constants.NB_COLS; j++) {
                GameRepresentation.append(this.grid.getCases()[i][j].toString());
            }
            GameRepresentation.append("\n");
        }
        GameRepresentation.append(playerManager);
        return GameRepresentation.toString();
    }

    /**
     * Retourne la représentation actuelle du jeu pour le joueur en cours.
     * @return La représentation actuelle du jeu pour le joueur en cours.
     */
    public String getCurrentGameRepresentation() {
        Player currentPlayer = playerManager.getCurrentPlayer();
        gridProxy.setPlayer(currentPlayer);
        return gridProxy.AfficheGrid();   
    }

    /**
     * Affiche l'état actuel du jeu pour le joueur en cours.
     * @param currentPlayer Le joueur en cours.
     */
    public void afficheGame(Player currentPlayer) {
        gridProxy.setPlayer(currentPlayer);
        String GameRepresentation = gridProxy.AfficheGrid();
        GameRepresentation += playerManager;
        GameRepresentation += "\nC'est le tour de " + currentPlayer;
        GameRepresentation += playerManager.showWeapons(currentPlayer);
        System.out.println(GameRepresentation);
    }
}
