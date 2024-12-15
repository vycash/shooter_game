package modele.components;

/**
 * Classe représentant un composant de type "Health" (santé).
 * Cette classe permet aux joueurs de récupérer des points de vie lorsqu'ils interagissent avec une case contenant un objet "Health".
 */
public class Health implements Component {

    /**
     * Quantité de points de vie restaurée lorsqu'un joueur interagit avec ce composant.
     */
    private static int healingAmount = 20;

    /**
     * Interagit avec un joueur en restaurant ses points de vie.
     * 
     * @param player Le joueur qui interagit avec le composant.
     */
    @Override
    public void interact(Player player) {
        player.heal(healingAmount);
        System.out.println("Healing case activated!");
    }

    /**
     * Retourne une représentation textuelle de l'objet Health.
     * Le texte est coloré en vert pour représenter la santé.
     * 
     * @return Représentation textuelle du composant Health.
     */
    @Override
    public String toString() {
        // Code ANSI pour la couleur verte
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_RESET = "\u001B[0m";
        return ANSI_GREEN + "h+" + ANSI_RESET;
    }
}
