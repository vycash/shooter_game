package vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

import controller.Ecouteur;
import controller.GameController;
import modele.Game;
import modele.components.Player;

public class GameWindow extends JFrame implements Ecouteur {
    private Game model;
    private PanelGrid panelGrid;
    private JTextArea actionLog = new JTextArea();
    private JTextArea playersInfo;
    private GameController gameController;
    private JLabel currentPlayerLabel;

    public GameWindow(Game game) {
        this.model = game;
        this.gameController = new GameController(game);
        setTitle("Jeu - Grille Dynamique");
        setSize(1500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Couleur de fond principale
        getContentPane().setBackground(new Color(45, 45, 48));

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(new Color(45, 45, 48));

        // Partie gauche : Grille
        panelGrid = new PanelGrid(game);
        panelGrid.setBorder(createStyledBorder("Grille de Jeu"));
        mainPanel.add(panelGrid);

        // Partie droite : Actions et infos
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actionPanel.setBackground(new Color(50, 50, 50));

        // Label du joueur actuel
        currentPlayerLabel = new JLabel("Tour de : Aucun joueur", SwingConstants.CENTER);
        currentPlayerLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        currentPlayerLabel.setForeground(new Color(255, 193, 7));
        currentPlayerLabel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(33, 150, 243)));
        currentPlayerLabel.setBackground(new Color(50, 50, 50));
        currentPlayerLabel.setOpaque(true);

        // Bouton "Tour Suivant"
        JButton nextTurnButton = createStyledButton("Tour Suivant", new Color(33, 150, 243));
        nextTurnButton.addActionListener(e -> gameController.handleNextTurn());

        // Panneau pour le label et le bouton
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        playerPanel.setBackground(new Color(50, 50, 50));
        playerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        playerPanel.add(currentPlayerLabel);
        playerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        playerPanel.add(nextTurnButton);

        actionPanel.add(playerPanel, BorderLayout.NORTH);

        // Log des actions
        actionLog.setEditable(false);
        actionLog.setFont(new Font("Roboto Mono", Font.PLAIN, 18));
        actionLog.setBackground(new Color(35, 35, 38));
        actionLog.setForeground(Color.WHITE); // Texte en blanc
        actionLog.setBorder(createStyledBorder("Journal d'Actions"));
        actionPanel.add(new JScrollPane(actionLog), BorderLayout.CENTER);

        // Zone d'informations des joueurs
        playersInfo = new JTextArea();
        playersInfo.setEditable(false);
        playersInfo.setFont(new Font("Roboto Mono", Font.PLAIN, 14));
        playersInfo.setBackground(new Color(35, 35, 38));
        playersInfo.setForeground(new Color(135, 206, 250));
        playersInfo.setBorder(createStyledBorder("Infos du Joueur"));
        playersInfo.setPreferredSize(new Dimension(200, 200));
        actionPanel.add(new JScrollPane(playersInfo), BorderLayout.SOUTH);

        mainPanel.add(actionPanel);
        add(mainPanel);

        // Abonnement au modèle
        game.ajoutEcouteur(this);

        // Ajouter un effet de transparence globale
        setUndecorated(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private Border createStyledBorder(String title) {
        return BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 2),
            title,
            0, 0,
            new Font("Roboto", Font.BOLD, 14),
            Color.LIGHT_GRAY
        );
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 50));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Roboto", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 2));

        // Ajouter un effet de survol
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor.brighter());
                button.setBorder(BorderFactory.createLineBorder(backgroundColor.brighter(), 2));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setBorder(BorderFactory.createLineBorder(backgroundColor.darker(), 2));
            }
        });
        return button;
    }

    @Override
    public void modeleMisAJour(Object e) {
        Player currentPlayer = model.getPlayerManager().getCurrentPlayer();
        currentPlayerLabel.setText("Tour de : " + currentPlayer.getName());

        if (model.getCurrentAction() != null) {
            addActionLog(model.getCurrentAction().toString());
        }

        panelGrid.updateGrid();
        updatePlayersInfo(); // Appeler pour mettre à jour les infos du joueur courant
    }

    private void updatePlayersInfo() {
        Player currentPlayer = model.getPlayerManager().getCurrentPlayer();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(35, 35, 38));

        JLabel playerNameLabel = new JLabel("Joueur : " + currentPlayer.getName());
        playerNameLabel.setFont(new Font("Roboto", Font.BOLD, 16));
        playerNameLabel.setForeground(new Color(135, 206, 250));
        infoPanel.add(playerNameLabel);

        JProgressBar energyBar = new JProgressBar(0, 100);
        energyBar.setValue(currentPlayer.getEnergy());
        energyBar.setStringPainted(true);
        energyBar.setString(String.valueOf("Energie :" + currentPlayer.getEnergy()));
        energyBar.setForeground(new Color(76, 175, 80));
        energyBar.setBackground(new Color(45, 45, 48));
        energyBar.setFont(new Font("Roboto Mono", Font.BOLD, 12));

        JPanel energyPanel = new JPanel(new BorderLayout());
        energyPanel.setBackground(new Color(35, 35, 38));
        energyPanel.add(energyBar, BorderLayout.CENTER);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        infoPanel.add(energyPanel);
        playersInfo.removeAll();
        playersInfo.setLayout(new BorderLayout());
        playersInfo.add(infoPanel, BorderLayout.CENTER);

        playersInfo.revalidate();
        playersInfo.repaint();
    }

    public void addActionLog(String action) {
        actionLog.append(action + "\n");
    }
}
