package controller;
/**
 * Interface représentant un écouteur dans le modèle MVC.
 * 
 * Un écouteur est un composant qui souhaite être informé des changements
 * dans un modèle. Les classes qui implémentent cette interface doivent
 * définir la méthode {@code modeleMisAJour} pour gérer les notifications
 * de changements.
 */
public interface Ecouteur {

    /**
     * Méthode appelée lorsque le modèle est mis à jour.
     * 
     * @param e Une référence au modèle qui a été mis à jour.
     */
    public void modeleMisAJour(Object e);
}
