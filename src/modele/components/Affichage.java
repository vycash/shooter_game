package modele.components;

/**
 * Interface représentant un affichage de grille.
 * Cette interface est utilisée pour fournir une méthode permettant d'afficher
 * une grille ou une représentation visuelle associée à un modèle.
 */
public interface Affichage {

    /**
     * Méthode pour générer une représentation visuelle ou textuelle de la grille.
     *
     * @return Une chaîne de caractères représentant l'état actuel de la grille.
     */
    public String AfficheGrid();
}
