package modele.components;

/**
 * Classe représentant un bonus de munitions dans le jeu.
 * Une case contenant des munitions permet au joueur d'augmenter le nombre
 * de munitions disponibles pour ses armes.
 */
public class Ammo implements Component {

    /**
     * Quantité de munitions ajoutées lorsqu'un joueur interagit avec cette case.
     */
    private static int ammoAmount = 10;

    /**
     * Méthode appelée lorsque le joueur interagit avec cette case.
     * Ajoute une quantité fixe de munitions au joueur.
     *
     * @param player Le joueur qui interagit avec la case.
     */
    @Override
    public void interact(Player player) {
        player.addAmmo(ammoAmount);
        System.out.println("ammo case activated!");
    }

    /**
     * Retourne une représentation textuelle de la case munitions.
     * Utilise une couleur ANSI (orange approximatif) pour distinguer visuellement cette case.
     *
     * @return Une chaîne de caractères représentant une case contenant des munitions.
     */
    @Override
    public String toString() {
        // Code ANSI pour la couleur jaune (approximation pour orange)
        final String ANSI_ORANGE = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        return ANSI_ORANGE + "a+" + ANSI_RESET;
    }
}
