# Jeu de Stratégie au Tour par Tour

Le jeu a encore plusieurs parties à progresser ( Joueur Humain, tests untaires , etc ... )

Vous pouvez changer les paramètres du jeu en changeant les Constantes dans le fichier Constantes.java dans le package Config dans src
`src/config/Constants.java`; nombre de joueurs, taille de la grille, proprietés de la grille (nb de chambres, taille des chambres, densité des pastilles d'nergie, densité des pastilles de munition etc....)

## Lancer le jeu

Pour exécuter le jeu, plusieurs options sont disponibles :

1. **Utiliser le script fourni** :
   - Ou à la racine du projet : `script.sh`

2. **Utiliser Apache Ant** :
   - À partir de la racine du projet, exécutez simplement la commande : `ant` pour générer l'executable .jar et générer la documentation.
   - ensuite executez la commande suivante pour jouer en mode Terminal : `java -cp dist/MyProject.jar Main`
   - ou executez la commande suivante pour jouer en mode graphique : `java -cp dist/MyProject.jar MainVue`


---

## Architecture du projet

- le repertoire src : contient l'ensemble du code commenté
- le repertoire doc : contient la javadoc
- le repertoire dist : contient l'executable et les autres librairies nécessaires à l'execution
- le repertoire rapport : contient le rapport
- le fichier build.xml : fichier qui permet de reconstruire l'executable du projet
- le fichier README.md : fichier contenant des informations utiles dur le projet ( comment compiler et executer etc )
- le fichier script.sh : fichier qui permet de compiler et executer le projet

Le projet est organisé selon le pattern **MVC** (Modèle-Vue-Contrôleur), offrant une séparation claire entre les responsabilités :

- **Modèle** : Contient toute la logique du jeu (mécanismes, gestion des joueurs et des armes, génération du labyrinthe, etc.).
- **Vue** : Gère l'interface utilisateur, permettant aux joueurs d'interagir avec le jeu de manière intuitive.
- **Contrôleur** : Relie la Vue et le Modèle, orchestrant les interactions et synchronisant les mises à jour.

- Le **modèle** a été conçu pour être complètement indépendant de la vue et du contrôleur. Cela facilite le développement, la maintenance et les éventuelles modifications ou extensions du jeu.

---

## Organisation du code

Toutes les parties du projet ont été développées indépendamment les unes des autres pour garantir une modularité et une simplicité dans la maintenance. Les packages suivants sont utilisés :

### Modèle

Le package `src/modele` contient les classes principales représentant la logique du jeu. Voici les sous-packages clés :

#### Package `util` (src/modele/util)

Les classes utilitaires gérant les éléments principaux du jeu :
- **`PlayerBuilder`** : Implémente le design pattern Factory pour créer les joueurs.
- **`WeaponBuilder`** : Même rôle que `PlayerBuilder`, mais pour les armes (incluant les bombes).
- **`PlayerManager`** : Gère tout ce qui concerne les joueurs (création, déplacement, etc.).
- **`WeaponManager`** : Gère tout ce qui concerne les armes (création, tir, placement et détonation des bombes, etc.).
- **`InputValidator`** : Vérifie et valide les entrées utilisateur (par exemple, pour les mouvements).
- **`Grid`** : Classe parent de `Game`, responsable de la création et de l'initialisation du labyrinthe.
- **`Case`** : Représente une cellule de la grille du jeu.

#### Package `composants` (src/modele/composants)

Contient les classes composantes du jeu, (Case,Grid) et les composantes de chaque case (Player,Health,Ammo)

---

### Vue

La **vue** est responsable de l'interface graphique et inclut les composants suivants :
- **`GameWindow`** : Fenêtre principale du jeu avec la grille, les boutons, et le journal des actions.
- **`PanelGrid`** : Affiche visuellement la grille de jeu.
- **`PanelBoutons`** : Gère les actions utilisateur comme tirer, se déplacer ou quitter la partie.
- **`PanelMouvement`** : Permet de déplacer les joueurs à l’aide de boutons directionnels.

---

### Contrôleur

Le **contrôleur** orchestre les interactions entre la vue et le modèle :
- **`GameController`** : Responsable de la gestion des actions utilisateur, comme passer au tour suivant.

---

## Points forts

- **Modularité** : Chaque composant est autonome, permettant des modifications ou des extensions sans impact majeur sur les autres parties.
- **Clarté** : La séparation MVC garantit un code facile à lire, comprendre et maintenir.
- **Flexibilité** : Grâce à l'utilisation de design patterns comme Factory, Observer et Singleton, le projet est conçu pour être extensible.



## Design Patterns Implémentés

### 1. Template
- implémenté dans le sous package armes, Définit un squelette d'algorithme dans une classe abstraite, laissant les sous-classes personnaliser des étapes spécifiques, dans ce cas les deux classes Gun et Bomb héritent de Weapon qui est une classe abstraite qui définit le squelette de la methode "use" qui represente l'utilisation d'une arme qui fait appelle à la methode 'execute' de la strategie correspondate.
  - `Weapon` définit la méthode `use` pour valider les munitions et exécuter un comportement spécifique.

### 2. Strategy
- implémenté pour le joueurs et les armes où chaque joueur a une strategie qui defini sont comportement dans le jeu. et dans le package armes pour encapsuler des comportements distincts d'utilisation d'une arme, ou chaque arme a un comportement different mais similaire, et on peut eventuellement ajouter d'autres armes et définir la strategie de leurs comportements dans le sous sous package strategy de meme pour les joueurs.
  - `DirectionalShootBehavior` gère le tir directionnel.
  - `ExplosionBehavior` gère les explosions.
  - Ces comportements sont attribués via `WeaponBehavior` aux classes `Gun` et `Bomb`.
  - **PlayerStrategy** : defini la strategie de jeu d'un joueur avec une methode getAction qui renvoie une action (une commande) selon la strategie définie
  - **Offensive** : defini la strategie d'un joueur attackant, où il se deplace dans une direction aleatoire ou il tire dans une direction aleatoire, le choix de l'action est aussi attribué aleatoirement

### 3. Factory
- **Description** : Centralise la création d’objets et retourne des instances configurées en fonction de paramètres ou de types.
  - `WeaponFactory` crée des armes (`Pistol`, `Bomb`).
  - `PlayerFactory` crée des joueurs (`Soldier`, `Sniper`).

### 4. Facade

  - **PlayerManager** : Cette classe agit comme une Facade pour gérer les joueurs, leur position, leurs déplacements, et leur état (vivant ou mort). Elle simplifie les interactions avec la grille et centralise toutes les opérations liées aux joueurs, comme le déplacement (deplacerPlayer) et le nettoyage des joueurs éliminés.

  - **WeaponManager** : Cette classe agit comme une Facade pour gérer les armes et les bombes. Elle centralise les actions complexes liées aux tirs, à la pose de bombes (placeBomb), et à la vérification de l’état des bombes (checkBombs). Elle fournit une interface de haut niveau pour simplifier l’utilisation des armes et leur gestion sur la grille.

### 5. Proxy

  - **ProxyGrid** : represente l'affichage specifique pour son joueur attribué
  - **Grid** : represente la grille de jeu

### 6. Composite
  - **components** : le sous package components définit tout les elements du jeu, en plus une case peut avoir comme Valeur une instance d'une classe Implémentant l'interface Component (Player,Bomb,Heal,Ammo), quand à l'interaction d'un joueur avec une case la case delegue la gestion de l'interaction à son object Val, lequel chacun a son implementation differente de interact mais en se basant sur l'interaction de Case sa classe contenaire
  - **Game** : delegue l'execution des commandes recues des joueurs à playerManager et weaponManager

### 7. Command
  - **Acion** : défini une commande à executer en étant une classe contenant les informations nécessaires pour qu le Game execute cette commande
  - **MoveAction** : contient les infos nécessaires pour que le game fait déplacer un joueur dans une direction

### 8. State
  - **Case** : sa methode interact agit differament selon son attribut Val, qui est un component, et case delegue l'execution de interact à son object Val
  
### 9. Singleton
  **Game** : il y'a une seule instance ge Game, où se battent les joueurs
  **Grid** : il y'a une seule instance de la grille où se trouvent les joueurs




