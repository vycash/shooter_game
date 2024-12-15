package controller;

import modele.Game;

/**
 * Contrôleur pour gérer les interactions entre la vue et le modèle du jeu.
 * 
 * Cette classe agit comme un intermédiaire entre la vue et le modèle, 
 * en fournissant des méthodes pour manipuler l'état du jeu.
 */
public class GameController {

    /**
     * Référence au modèle du jeu.
     */
    private final Game game;

    /**
     * Constructeur du contrôleur.
     * 
     * @param game Une instance du modèle {@link Game} à contrôler.
     */
    public GameController(Game game) {
        this.game = game;
    }

    /**
     * Gère l'avancement du jeu d'un tour.
     * 
     * Cette méthode invoque la méthode {@code advanceTurn} du modèle pour
     * effectuer les opérations nécessaires à l'avancement du tour.
     */
    public void handleNextTurn() {
        game.advanceTurn();
    }
}
