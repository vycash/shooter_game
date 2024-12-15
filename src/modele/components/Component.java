package modele.components;

/**
 * Interface représentant un composant pouvant interagir avec un joueur.
 * Cette interface est utilisée pour définir les comportements génériques des
 * éléments pouvant être présents dans une case de la grille du jeu, tels que
 * les joueurs, les armes, ou les bonus.
 */
public interface Component {

    /**
     * Méthode permettant au composant d'interagir avec un joueur.
     * Chaque composant doit définir la manière dont il affecte le joueur
     * lorsqu'une interaction se produit.
     *
     * @param p Le joueur interagissant avec le composant.
     */
    public void interact(Player p);
}
