package modele.util;

import modele.*;
import modele.armes.*;
import modele.components.*;
import java.util.*;

/**
 * Classe responsable de la gestion des armes dans le jeu.
 * Elle gère notamment le placement des bombes, le tir des armes, et le suivi des bombes actives sur la grille.
 */
public class WeaponManager {

    /**
     * Map associant chaque bombe à sa position sur la grille.
     */
    private Map<Bomb, Case> bombLocation;

    /**
     * Référence au jeu principal.
     */
    private Game g;

    /**
     * Gestionnaire des joueurs pour accéder aux informations des joueurs.
     */
    private PlayerManager playerManager;

    /**
     * Grille de jeu sous forme de tableau 2D de cases.
     */
    private Case[][] cases;

    /**
     * Constructeur de la classe WeaponManager.
     *
     * @param g Instance du jeu principal.
     */
    public WeaponManager(Game g) {
        this.g = g;
        this.bombLocation = new HashMap<>();
        this.playerManager = g.getPlayerManager();
        this.cases = g.getGrid().getCases();
    }

    /**
     * Permet au joueur de choisir une arme parmi celles disponibles.
     *
     * @param player Le joueur qui doit choisir une arme.
     * @return L'arme choisie par le joueur, ou {@code null} s'il n'en possède pas.
     */
    private Weapon chooseWeapon(Player player) {
        List<Weapon> weapons = player.getWeapons();

        if (weapons.isEmpty()) {
            System.out.println("No weapons available for this player.");
            return null;
        }

        StringBuilder prompt = new StringBuilder("Choose a weapon from the following list: \n");
        for (int i = 0; i < weapons.size(); i++) {
            prompt.append((i + 1)).append(") ").append(weapons.get(i)).append("\n");
        }

        int weaponIndex = InputValidator.getValidatedInput(prompt.toString(), 1, weapons.size());
        return weapons.get(weaponIndex - 1);
    }

    /**
     * Gère l'action de tirer d'un joueur avec une arme donnée dans une direction spécifique.
     *
     * @param player       Le joueur qui effectue l'action.
     * @param direction    La direction du tir ("h", "b", "g", "d").
     * @param chosenWeapon L'arme choisie pour le tir.
     * @return {@code true} si le tir a été effectué avec succès, sinon {@code false}.
     */
    public boolean shoot(Player player, String direction, Weapon chosenWeapon) {
        boolean ok = false;
        if (player == null) {
            System.out.println("Invalid player.");
            return false;
        }

        if (chosenWeapon == null) return false;

        int x = playerManager.getPlayerLocation(player).getLine();
        int y = playerManager.getPlayerLocation(player).getCol();

        if (chosenWeapon instanceof Bomb) {
            ok = placeBomb(player, (Bomb) chosenWeapon, direction);
        } else {
            chosenWeapon.use(x, y, direction, cases, playerManager);
            ok = true;
        }
        return ok;
    }

    /**
     * Place une bombe sur la grille à une position adjacente au joueur dans une direction donnée.
     *
     * @param player   Le joueur qui place la bombe.
     * @param bomb     La bombe à placer.
     * @param direction La direction dans laquelle placer la bombe ("h", "b", "g", "d").
     * @return {@code true} si la bombe a été placée avec succès, sinon {@code false}.
     */
    public boolean placeBomb(Player player, Bomb bomb, String direction) {
        int x = playerManager.getPlayerLocation(player).getLine();
        int y = playerManager.getPlayerLocation(player).getCol();

        int targetX = x, targetY = y;

        // Calcul des coordonnées cibles en fonction de la direction
        switch (direction) {
            case "h": targetX = x - 1; break; // haut
            case "b": targetX = x + 1; break; // bas
            case "g": targetY = y - 1; break; // gauche
            case "d": targetY = y + 1; break; // droite
            default:
                System.out.println("Invalid direction.");
                return false;
        }

        // Validation des coordonnées cibles
        if (!g.validCoordinates(targetX, targetY)) {
            System.out.println("Invalid placement for bomb: Out of bounds.");
            return false;
        }

        Case targetCase = cases[targetX][targetY];

        // Vérification si la case cible est libre et non un mur
        if (targetCase.isWall() || !targetCase.isEmpty()) {
            System.out.println("Invalid placement for bomb: Case occupied or wall.");
            return false;
        }

        // Placement de la bombe si des munitions sont disponibles
        if (bomb.getMunitions() > 0) {
            targetCase.setVal(bomb);
            bombLocation.put(bomb, targetCase);
            System.out.println("Bomb placed at (" + targetX + ", " + targetY + "). Timer: " + bomb.getTimer() + (bomb.isMine() ? " (mine)" : ""));
            return true;
        }else{
            System.out.println("plus de munitions pour la bombe");
        }
        return false;
    }

    /**
     * Vérifie l'état des bombes placées sur la grille.
     * Décrémente le timer des bombes et déclenche leur explosion si nécessaire.
     */
    public void checkBombs() {
        Iterator<Map.Entry<Bomb, Case>> iterator = bombLocation.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Bomb, Case> entry = iterator.next();
            Bomb bomb = entry.getKey();
            Case cell = entry.getValue();

            if (!bomb.isMine()) {
                bomb.decrease();
                System.out.println("Bomb timer decreased to " + bomb.getTimer());

                if (bomb.getTimer() <= 0) {
                    bomb.use(cell.getLine(), cell.getCol(), "", cases, playerManager);
                    cell.setVal(null); // Retire la bombe de la case
                    bomb.reset();      // Réinitialise le timer pour une éventuelle réutilisation
                    iterator.remove(); // Retire la bombe de la map
                }
            }
        }
    }

    /**
     * Vérifie si un joueur déclenche une mine en entrant dans une case.
     * Si une mine est présente, elle explose et inflige des dégâts au joueur.
     *
     * @param player Le joueur entrant dans la case.
     * @param c      La case dans laquelle le joueur entre.
     * @param cases  La grille de jeu.
     * @param manager Le gestionnaire des joueurs.
     */
    public static void checkMineTrigger(Player player, Case c, Case[][] cases, PlayerManager manager) {
        Object val = c.getVal();

        if (val instanceof Bomb) {
            Bomb bomb = (Bomb) val;
            if (bomb.isMine()) {
                bomb.use(c.getLine(), c.getCol(), "", cases, manager);
                System.out.println("Player " + player.getID() + " stepped on a mine and took " + bomb.getDamage() + " damage!");
                c.setVal(null); // Retire la mine de la case
            }
        }
    }
}
