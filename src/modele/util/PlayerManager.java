package modele.util;

import java.util.*;
import config.Constants;
import modele.*;
import modele.armes.Weapon;
import modele.components.*;

/**
 * Classe pour gérer les joueurs dans le jeu.
 * Elle s'occupe de leur création, de leur placement sur la grille,
 * de leurs déplacements, et de la gestion des joueurs morts.
 */
public class PlayerManager {

    private Player currentPlayer;
    private List<Player> players;
    private Map<Player, Case> playerLocation;
    private Queue<Player> playersQueue;
    private PlayerFactory pb;
    private Game g;

    /**
     * Constructeur de la classe PlayerManager.
     *
     * @param g L'instance du jeu associée à ce gestionnaire.
     */
    public PlayerManager(Game g) {
        this.players = new ArrayList<>();
        this.playerLocation = new HashMap<>();
        this.playersQueue = new LinkedList<>();
        this.pb = new PlayerFactory();
        this.g = g;
    }

    /**
     * Ajoute un joueur au jeu et le place sur la grille.
     *
     * @param j Le joueur à ajouter.
     */
    public void addPlayer(Player j) { 
        this.players.add(j); 
        this.playersQueue.add(j);
        placerPlayer(j); 
    }

    /**
     * Retourne le nombre de joueurs actifs.
     *
     * @return Le nombre de joueurs.
     */
    public int nbPlayers() {
        return this.players.size();
    }

    /**
     * Retourne le joueur suivant dans la file et le définit comme joueur actuel.
     *
     * @return Le joueur actuel.
     */
    public Player getPlayer() {
        Player p = this.playersQueue.poll();
        this.currentPlayer = p;
        return currentPlayer;
    }

    /**
     * Retourne le joueur actuellement actif.
     *
     * @return Le joueur actuel.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Retourne la carte des emplacements de tous les joueurs.
     *
     * @return Une map associant chaque joueur à sa case.
     */
    public Map<Player, Case> getAllPlayerLocation() {
        return this.playerLocation;
    }

    /**
     * Vérifie si une case contient un joueur.
     *
     * @param c La case à vérifier.
     * @return true si un joueur est présent, false sinon.
     */
    public boolean playerExists(Case c) {
        return playerLocation.containsValue(c);
    }

    /**
     * Réinsère un joueur dans la file.
     *
     * @param p Le joueur à réinsérer.
     */
    public void enfilerPlayer(Player p) {
        this.playersQueue.add(p);
    }

    /**
     * Met à jour l'emplacement d'un joueur.
     *
     * @param j Le joueur à mettre à jour.
     * @param c La nouvelle case du joueur.
     */
    private void updateLocation(Player j, Case c) {
        this.playerLocation.put(j, c); 
    }

    /**
     * Place un joueur sur une case aléatoire non murée.
     *
     * @param j Le joueur à placer.
     */
    private void placerPlayer(Player j) {
        boolean ok = false;
        Random rand = new Random();
        
        while (!ok) {
            int x = rand.nextInt(Constants.NB_LIGNES);
            int y = rand.nextInt(Constants.NB_COLS);
            Case c = g.getCase(x, y);

            if (!c.isWall()) {
                c.setVal(j);
                updateLocation(j, c);
                ok = true;
            }
        }
    }

    /**
     * Ajoute un nombre spécifié de joueurs au jeu.
     *
     * @param nbPlayers Le nombre de joueurs à ajouter.
     */
    public void addPlayers(int nbPlayers) {
        for (int i = 1; i <= nbPlayers; i++) {
            Player j = pb.buildPlayer(); 
            addPlayer(j);
        }
    }

    /**
     * Supprime un joueur du jeu et libère sa case.
     *
     * @param player Le joueur à supprimer.
     */
    private void removePlayer(Player player) {
        players.remove(player);
        Case c = playerLocation.get(player);
        g.getCase(c.getLine(), c.getCol()).setVal(null);

        updateLocation(player, null);  
        playerLocation.remove(player);
        playersQueue.remove(player);
    }

    /**
     * Supprime les joueurs morts de la liste des joueurs actifs.
     */
    public void removeDeadPlayers() {
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (!p.isAlive()) {
                iterator.remove();  // Retire le joueur de la liste
                removePlayer(p);    // Nettoyage supplémentaire
            }
        }
    }

    /**
     * Retourne un joueur par son ID.
     *
     * @param id L'ID du joueur.
     * @return Le joueur correspondant, ou null s'il n'existe pas.
     */
    public Player getPlayerById(int id) {
        return players.stream()
                      .filter(player -> player.getID() == id)
                      .findFirst()
                      .orElse(null);
    }

    /**
     * Retourne l'emplacement d'un joueur.
     *
     * @param player Le joueur dont l'emplacement est recherché.
     * @return La case contenant le joueur.
     */
    public Case getPlayerLocation(Player player) {
        return playerLocation.get(player);
    }

    /**
     * Retourne les coordonnées d'un joueur.
     *
     * @param player Le joueur dont les coordonnées sont recherchées.
     * @return Un tableau contenant les coordonnées [ligne, colonne].
     */
    public Integer[] getPlayerCoordinates(Player player) {
        return playerLocation.get(player).getCoordinates();
    }

    /**
     * Retourne la liste des joueurs.
     *
     * @return La liste des joueurs.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Déplace un joueur dans une direction donnée.
     *
     * @param player Le joueur à déplacer.
     * @param direction La direction du déplacement (h, b, g, d).
     * @return true si le déplacement a réussi, false sinon.
     */
    public boolean deplacerPlayer(Player player, String direction) {
        int currentLine = getPlayerLocation(player).getLine();
        int currentColumn = getPlayerLocation(player).getCol();
        boolean ok = false;
        
        switch (direction) {
            case "h":
                ok = deplacerPlayer(player, currentLine - 1, currentColumn);
                break;
            case "b":
                ok = deplacerPlayer(player, currentLine + 1, currentColumn);
                break;
            case "g":
                ok = deplacerPlayer(player, currentLine, currentColumn - 1);
                break;
            case "d":
                ok = deplacerPlayer(player, currentLine, currentColumn + 1);
                break;
            default:
                System.out.println("\n --- !!!  Mouvement non valide. Utilisez 'h' pour haut, 'b' pour bas, 'g' pour gauche, 'd' pour droite. !!! --- \n");
        }
        return ok;
    }

    /**
     * Déplace un joueur vers une case spécifique.
     *
     * @param j Le joueur à déplacer.
     * @param line La ligne cible.
     * @param col La colonne cible.
     * @return true si le déplacement a réussi, false sinon.
     */
    public boolean deplacerPlayer(Player j, int line, int col) {
        boolean ok = false;

        if (j != null) {
            Case currentLocation = getPlayerLocation(j);
            int x = currentLocation.getLine();
            int y = currentLocation.getCol();

            if (g.validCoordinates(line, col)) {
                Case next = g.getCase(line, col);
                Case current = g.getCase(x, y);

                if (!next.isWall() && !next.containsPlayer()) {
                    WeaponManager.checkMineTrigger(j, next, g.getGrid().getCases(), this);
                    next.interact(j);
                    current.setVal(null);
                    updateLocation(j, next);
                    ok = true;
                } else {
                    System.out.println("Case cible est un mur ou contient un autre joueur.");
                }
            } else {
                System.out.println("Mouvement impossible, Réessayez.");
            }
        } else {
            System.out.println("Player inexistant.");
        }
        return ok;
    }

    /**
     * Active le bouclier pour un joueur donné.
     *
     * @param p Le joueur dont le bouclier est activé.
     */
    public void activateShield(Player p) {
        p.activateShield();
    }

    @Override
    public String toString(){
        String res = "";
        for( Player j : players ){
            res += "Player " + j;
            res += ", Energie = " + j.getEnergy();
            res += ", Strategy = "+j.getStrategy();
            res += "\n";
        }
        return res;
    }

    /**
     * Affiche les armes disponibles pour un joueur.
     *
     * @param p Le joueur concerné.
     * @return Une chaîne contenant la liste des armes.
     */
     public String showWeapons( Player p ){
        String res = " --> Armes disponibles";
        for( Weapon w : p.getWeapons()){
            res+="\n    -"+w;
        }
        res+="\n";
        return res;
    }
    
}
