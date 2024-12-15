#!/bin/bash

# Étape 1 : Compilation des fichiers source
echo "Compilation des fichiers source..."
javac -d build/ src/config/*.java src/modele/*.java src/modele/*/*.java src/modele/*/*/*.java src/vue/*.java src/controller/*.java src/Main.java src/MainVue.java 


# Vérification de la compilation
if [ $? -eq 0 ]; then
    # Proposition des options
    echo "Choisissez une option :"
    echo "1. Jouer en terminal"
    echo "2. Jouer avec l'interface graphique"
    read -p "Entrez votre choix (1, 2 ou 3) : " choix

    case $choix in
        1)
            echo "Lancement du jeu en mode terminal..."
            java -cp build/ Main
            ;;
        2)
            echo "Lancement du jeu en mode graphique..."
            java -cp build/ MainVue
            ;;
        *)
            echo "Choix invalide. Veuillez relancer le script et entrer 1, 2 ou 3."
            exit 1
            ;;
    esac
else
    echo "Erreur lors de la compilation. Vérifiez votre code."
    exit 1
fi
