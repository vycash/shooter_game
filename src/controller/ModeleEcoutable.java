package controller;

/**
 * Interface définissant un modèle écoutable.
 * 
 * Un modèle écoutable permet à des objets (écouteurs) de s'abonner pour recevoir
 * des notifications lors des changements d'état du modèle.
 */
public interface ModeleEcoutable {

    /**
     * Ajoute un écouteur pour recevoir les notifications des changements du modèle.
     * 
     * @param e L'écouteur à ajouter.
     */
    public void ajoutEcouteur(Ecouteur e);

    /**
     * Retire un écouteur de la liste des abonnés au modèle.
     * 
     * @param e L'écouteur à retirer.
     */
    public void retraitEcouteur(Ecouteur e);
}
