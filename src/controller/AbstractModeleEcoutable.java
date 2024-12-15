package controller;

import java.util.ArrayList;

/**
 * Classe abstraite implémentant l'interface {@link ModeleEcoutable}.
 * Cette classe fournit une structure de base pour un modèle qui peut notifier
 * ses changements à un ensemble d'écouteurs.
 */
public class AbstractModeleEcoutable implements ModeleEcoutable {

    /**
     * Liste des écouteurs abonnés aux changements du modèle.
     */
    private ArrayList<Ecouteur> ecouteurs;

    /**
     * Constructeur protégé pour initialiser la liste des écouteurs.
     * Les sous-classes peuvent l'utiliser pour gérer les notifications.
     */
    protected AbstractModeleEcoutable() {
        this.ecouteurs = new ArrayList<>();
    }

    /**
     * Ajoute un écouteur à la liste des écouteurs.
     * 
     * @param e L'écouteur à ajouter.
     */
    public void ajoutEcouteur(Ecouteur e) {
        this.ecouteurs.add(e);
    }

    /**
     * Retire un écouteur de la liste des écouteurs.
     * 
     * @param e L'écouteur à retirer.
     */
    public void retraitEcouteur(Ecouteur e) {
        this.ecouteurs.remove(e);
    }

    /**
     * Notifie tous les écouteurs d'un changement dans le modèle.
     * Cette méthode appelle la méthode {@code modeleMisAJour} sur chaque écouteur.
     */
    public void fireChangement() {
        for (Ecouteur ecouteur : this.ecouteurs) {
            ecouteur.modeleMisAJour(this);
        }
    }
}
