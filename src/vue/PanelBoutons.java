package vue;
import modele.util.*;
import modele.components.*;
import modele.*;
import java.util.List;
import modele.armes.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import java.io.*;


class PanelBoutons extends JPanel {
    private WeaponFactory weaponFactory;

    public PanelBoutons(Grid matrice, PanelGrid fenetreJeu) {
        this.setLayout(new GridLayout(4, 1, 50, 20)); 
        this.setBackground(Color.LIGHT_GRAY);

        weaponFactory = new WeaponFactory();

        final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 20);

        final Color RED_SHADE = new Color(204, 0, 0);
        final Color BLUE_SHADE = new Color(0, 0, 153);
        final Color GOLD_SHADE = new Color(255, 204, 0);

        // Bouton "Tirer"
        JButton tirerBouton = new JButton("Tirer");
        tirerBouton.setFont(BUTTON_FONT);
        tirerBouton.setBackground(GOLD_SHADE);
        tirerBouton.setForeground(Color.WHITE);
        tirerBouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherArmes();
            }
        });


    



        JButton exitBouton = new JButton("Quitter la partie");
exitBouton.setFont(BUTTON_FONT);
exitBouton.setBackground(BLUE_SHADE);
exitBouton.setForeground(Color.WHITE);
exitBouton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        int confirmation = JOptionPane.showConfirmDialog(
            PanelBoutons.this,
            "Êtes-vous sûr de vouloir quitter la partie ?",
            "Quitter la Partie",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmation == JOptionPane.YES_OPTION) {
            System.exit(0); // Ferme l'application
        }
    }
});

        // Ajout des composants au panneau
        this.add(tirerBouton);
        this.add(exitBouton);
        PanelMouvement panel = new PanelMouvement(matrice,fenetreJeu) ;
        this.add(panel);
    }

    private void afficherArmes() {
    List<Weapon> weapons = weaponFactory.buildWeapons(5, 1); // Exemple : 5 armes pour le joueur 1

    // Créer une liste des noms d'armes
    String[] weaponNames = weapons.stream()
                                  .map(Weapon::getName)
                                  .toArray(String[]::new);

    // Boîte de dialogue avec une liste déroulante pour sélectionner une arme
    String selectedWeapon = (String) JOptionPane.showInputDialog(
        this,
        "Choisissez une arme :",
        "Armes Disponibles",
        JOptionPane.QUESTION_MESSAGE,
        null,
        weaponNames, // Options dans la liste déroulante
        weaponNames[0] // Option par défaut
    );

    // Vérifier si une arme a été choisie
    if (selectedWeapon != null) {
        Weapon chosenWeapon = weapons.stream()
                                     .filter(w -> w.getName().equals(selectedWeapon))
                                     .findFirst()
                                     .orElse(null);

        // Afficher un message confirmant le choix de l'arme
        JOptionPane.showMessageDialog(
            this,
            "Vous avez choisi : " + chosenWeapon.getName() + 
            "\nDommages : " + chosenWeapon.getDamage(),
            "Arme Sélectionnée",
            JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        // Si l'utilisateur annule
        JOptionPane.showMessageDialog(
            this,
            "Aucune arme n'a été choisie.",
            "Choix annulé",
            JOptionPane.WARNING_MESSAGE
        );
    }
}

    
    
}

