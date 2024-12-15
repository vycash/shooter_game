import modele.Game;
import vue.GameWindow;

public class MainVue {
    public static void main(String[] args) {
        // Exemple d'initialisation pour démarrer le jeu
        Game g = Game.getInstance();
        GameWindow w = new GameWindow(g);
        g.commencer();
    }
}
