# 🧩 Guide des Algorithmes de Génération de Labyrinthes

## 📚 Table des Matières

1. [Recursive Backtracker (DFS)](#1-recursive-backtracker-dfs)
2. [Prim's Algorithm](#2-prims-algorithm)
3. [Kruskal's Algorithm](#3-kruskals-algorithm)
4. [Binary Tree](#4-binary-tree)
5. [Sidewinder](#5-sidewinder)
6. [Aldous-Broder](#6-aldous-broder)
7. [Wilson's Algorithm](#7-wilsons-algorithm)
8. [Hunt-and-Kill](#8-hunt-and-kill)
9. [Growing Tree](#9-growing-tree)
10. [Eller's Algorithm](#10-ellers-algorithm)
11. [Recursive Division](#11-recursive-division)
12. [Comparaison des Algorithmes](#-comparaison-des-algorithmes)

---

## 1. Recursive Backtracker (DFS)

### 🎯 Principe
Marche aléatoire en profondeur avec retour arrière (backtracking).

### 📝 Algorithme
```
1. Commencer à une cellule aléatoire, la marquer comme visitée
2. TANT QUE il y a des voisins non visités :
   a. Choisir un voisin aléatoire non visité
   b. Supprimer le mur entre les deux cellules
   c. Appeler récursivement sur le voisin
3. SI bloqué : Retour arrière (backtrack) à la cellule précédente
```

### 🔑 Concepts Clés
- **DFS** (Depth-First Search) : Exploration en profondeur
- **Backtracking** : Retour en arrière quand bloqué
- **Stack** : Pile d'appels récursifs ou explicite

### ✅ Avantages
- Labyrinthes avec longs couloirs sinueux
- Relativement rapide
- Génère des labyrinthes "esthétiques"

### ❌ Désavantages
- Risque de stack overflow (si récursif)
- Biais léger selon l'ordre des directions

### 🎨 Caractéristiques visuelles
- Longs passages tortueux
- Peu d'embranchements courts
- Aspect "organique"

---

## 2. Prim's Algorithm

### 🎯 Principe
Construction progressive en ajoutant des cellules depuis une frontière.

### 📝 Algorithme
```
1. Commencer avec une cellule dans le labyrinthe
2. Ajouter tous ses murs voisins à une liste
3. TANT QUE la liste n'est pas vide :
   a. Choisir un mur aléatoire de la liste
   b. SI la cellule de l'autre côté n'est pas visitée :
      - Supprimer le mur
      - Marquer la cellule comme visitée
      - Ajouter ses murs voisins à la liste
   c. Retirer le mur de la liste
```

### 🔑 Concepts Clés
- **Arbre couvrant minimal** : Prim simplifié (poids uniformes)
- **Frontière** : Liste des murs "actifs"
- **Expansion progressive** : Croissance depuis le centre

### ✅ Avantages
- Nombreux embranchements courts
- Distribution équilibrée
- Pas de récursion

### ❌ Désavantages
- Moins de longs couloirs
- Nécessite une liste de murs (mémoire)

### 🎨 Caractéristiques visuelles
- Nombreux petits embranchements
- Structure moins "linéaire"
- Chemins variés

---

## 3. Kruskal's Algorithm

### 🎯 Principe
Union-Find pour connecter des ensembles de cellules.

### 📝 Algorithme
```
1. Chaque cellule commence dans son propre ensemble
2. Créer une liste de TOUS les murs possibles
3. Mélanger aléatoirement cette liste
4. POUR CHAQUE mur :
   a. SI les cellules de chaque côté sont dans des ensembles différents :
      - Supprimer le mur
      - FUSIONNER les deux ensembles (Union-Find)
5. Continuer jusqu'à avoir un seul ensemble
```

### 🔑 Concepts Clés
- **Union-Find (DSU)** : Structure de données pour ensembles disjoints
  - `find(x)` : Trouver la racine de l'ensemble contenant x
  - `union(x, y)` : Fusionner les ensembles contenant x et y
- **Compression de chemin** : Optimisation de find()
- **Union par rang** : Optimisation de union()

### ✅ Avantages
- Distribution TRÈS uniforme
- Pas de biais directionnel
- Rapide : O(n α(n)) avec α inverse d'Ackermann
- Labyrinthes équilibrés

### ❌ Désavantages
- Complexe à implémenter (Union-Find)
- Nécessite tous les murs en mémoire

### 🎨 Caractéristiques visuelles
- Uniformément distribué
- Aucun biais
- Aspect "équilibré"

### 💡 Code Union-Find (Java)
```java
class UnionFind {
    int[] parent, rank;
    
    int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]); // Compression
        return parent[x];
    }
    
    void union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rank[rx] < rank[ry]) parent[rx] = ry;
        else if (rank[rx] > rank[ry]) parent[ry] = rx;
        else { parent[ry] = rx; rank[rx]++; }
    }
}
```

---

## 4. Binary Tree

### 🎯 Principe
Algorithme le plus simple : pour chaque cellule, creuser vers le nord OU l'est.

### 📝 Algorithme
```
POUR CHAQUE cellule (x, y) :
    SI pas au bord nord ET pas au bord est :
        Choisir aléatoirement : Nord OU Est
        Creuser dans cette direction
    SINON SI au bord est uniquement :
        Creuser vers le Nord
    SINON SI au bord nord uniquement :
        Creuser vers l'Est
```

### 🔑 Concepts Clés
- **Choix binaire** : 2 directions seulement
- **Biais diagonal** : Coin nord-est toujours ouvert
- **Sans mémoire** : Chaque cellule traitée indépendamment

### ✅ Avantages
- ⚡ Extrêmement rapide : O(n)
- Très simple à implémenter (5 lignes)
- Pas de récursion ni structures de données

### ❌ Désavantages
- ❌ **Fort biais diagonal** (nord-est)
- Trop facile à résoudre
- Pas de "vrai" labyrinthe
- Coin nord-est toujours accessible

### 🎨 Caractéristiques visuelles
- Diagonale nord-est complètement ouverte
- Structure prévisible
- Chemins vers le haut et la droite

### ⚠️ Usage
Tests uniquement, pas pour production.

---

## 5. Sidewinder

### 🎯 Principe
Amélioration de Binary Tree : traitement ligne par ligne avec "runs".

### 📝 Algorithme
```
POUR CHAQUE ligne (de haut en bas) :
    run = []  // Liste de cellules connectées horizontalement
    
    POUR CHAQUE cellule de la ligne :
        run.add(cellule)
        
        SI ligne du haut :
            Creuser vers l'Est
        SINON SI dernière colonne :
            Choisir cellule aléatoire du run
            Creuser vers le Nord depuis cette cellule
            Vider le run
        SINON :
            Lancer une pièce (50/50) :
                PILE : Continuer le run (creuser Est)
                FACE : Fermer le run (cellule aléatoire → Nord)
                       Vider le run
```

### 🔑 Concepts Clés
- **Run** : Séquence de cellules connectées horizontalement
- **Fermeture de run** : Choisir UNE cellule du run pour aller au nord
- **Ligne par ligne** : Traitement séquentiel

### ✅ Avantages
- Plus intéressant que Binary Tree
- Rapide : O(n), une seule passe
- Génération en "streaming"

### ❌ Désavantages
- Biais nord-est (mais moins fort que Binary Tree)
- Ligne du haut complètement ouverte
- Structure encore prévisible

### 🎨 Caractéristiques visuelles
- Couloir horizontal complet en haut
- "Runs" horizontaux visibles
- Biais vers le nord et l'est

---

## 6. Aldous-Broder

### 🎯 Principe
Marche aléatoire PURE jusqu'à avoir visité toutes les cellules.

### 📝 Algorithme
```
1. Commencer à une cellule aléatoire, la marquer comme visitée
2. TANT QUE toutes les cellules ne sont pas visitées :
   a. Choisir un voisin aléatoire (N/S/E/O)
   b. SI le voisin n'est PAS visité :
      - Supprimer le mur entre les deux
      - Marquer le voisin comme visité
   c. Se déplacer vers le voisin (visité ou non)
```

### 🔑 Concepts Clés
- **Marche aléatoire** : Pas de stratégie, mouvement purement aléatoire
- **Distribution uniforme GARANTIE** : Tous les labyrinthes possibles ont la même probabilité
- **Revisite** : On peut revenir sur des cellules visitées

### ✅ Avantages
- 🏆 **SEUL algorithme mathématiquement uniforme**
- Simple à implémenter
- Pas de structures de données complexes

### ❌ Désavantages
- 🐌 **TRÈS LENT** : O(n²) en moyenne, peut être O(n³)
- Les dernières cellules prennent un temps exponentiel
- Beaucoup de mouvements "inutiles"

### 🎨 Caractéristiques visuelles
- Parfaitement uniforme
- Aucun biais directionnel
- Aspect aléatoire naturel

### ⚠️ Optimisation
Basculer vers Wilson's après 50% des cellules visitées.

---

## 7. Wilson's Algorithm

### 🎯 Principe
Marche aléatoire avec **effacement de boucles** (loop-erased random walk).

### 📝 Algorithme
```
1. Choisir une cellule de départ, l'ajouter au labyrinthe
2. TANT QUE toutes les cellules ne sont pas dans le labyrinthe :
   a. Choisir une cellule aléatoire PAS dans le labyrinthe
   b. Effectuer une marche aléatoire depuis cette cellule
   c. SI on revient sur ses pas : EFFACER la boucle
   d. Continuer jusqu'à atteindre le labyrinthe
   e. Ajouter tout le chemin (sans boucles) au labyrinthe
```

### 🔑 Concepts Clés
- **Loop-Erased Random Walk** : Effacement automatique des boucles
- **Chemin sans boucle** : On garde seulement le dernier passage
- **Distribution uniforme** : Comme Aldous-Broder mais plus rapide

### 📖 Exemple d'Effacement
```
Marche : A → B → C → D → B → E
         On atteint B une 2ème fois !
         
Effacement de la boucle B→C→D→B :
Résultat : A → B → E
```

### ✅ Avantages
- Distribution uniforme GARANTIE
- Plus rapide qu'Aldous-Broder : O(n log n)
- Visuellement intéressant (chemins se forment)

### ❌ Désavantages
- Plus complexe à implémenter
- Les premières marches peuvent être longues
- Nécessite mémoriser les chemins

### 🎨 Caractéristiques visuelles
- Uniforme comme Aldous-Broder
- Chemins "propres" sans boucles

---

## 8. Hunt-and-Kill

### 🎯 Principe
Alternance entre marche aléatoire (Kill) et scan de grille (Hunt).

### 📝 Algorithme
```
1. Commencer à une cellule aléatoire

RÉPÉTER :
    PHASE KILL (Marche aléatoire) :
        Tant qu'il y a des voisins non visités :
            - Choisir un voisin non visité aléatoire
            - Supprimer le mur
            - Se déplacer

    PHASE HUNT (Scan de grille) :
        Scanner la grille ligne par ligne :
            Trouver une cellule non visitée 
            ADJACENTE à une cellule visitée
            
        Connecter cette cellule au labyrinthe
        Recommencer la phase KILL depuis là

JUSQU'À avoir visité toutes les cellules
```

### 🔑 Concepts Clés
- **Kill Phase** : DFS (comme Recursive Backtracker)
- **Hunt Phase** : Recherche linéaire dans la grille
- **Sans stack** : Pas de récursion ni pile explicite

### ✅ Avantages
- Longs couloirs comme Recursive Backtracker
- Pas de récursion (pas de stack overflow)
- Labyrinthes esthétiques

### ❌ Désavantages
- Phase Hunt peut être lente (scan complet)
- Plus lent que Recursive Backtracker
- Nécessite tracker toutes les cellules

### 🎨 Caractéristiques visuelles
- Similaire à Recursive Backtracker
- Longs passages sinueux
- Aspect "organique"

---

## 9. Growing Tree

### 🎯 Principe
Framework généraliste unifiant plusieurs algorithmes selon la **stratégie de sélection**.

### 📝 Algorithme
```
1. Commencer avec une cellule dans une liste
2. TANT QUE la liste n'est pas vide :
   a. CHOISIR une cellule de la liste selon STRATÉGIE
   b. SI elle a des voisins non visités :
      - Choisir un voisin aléatoire
      - Supprimer le mur
      - Ajouter le voisin à la liste
   c. SINON :
      - Retirer la cellule de la liste
```

### 🎯 Stratégies de Sélection

| Stratégie | Effet | Équivalent |
|-----------|-------|------------|
| **Newest** (dernière) | Prendre toujours la plus récente | Recursive Backtracker (DFS) |
| **Oldest** (première) | Prendre toujours la plus ancienne | Simplified Prim (BFS) |
| **Random** | Cellule complètement aléatoire | Mix DFS/BFS |
| **Mixed** (50/50) | 50% newest, 50% random | 🏆 **Recommandé** |

### 🔑 Concepts Clés
- **Framework flexible** : Un seul algorithme pour plusieurs résultats
- **Stratégie paramétrable** : Change complètement le labyrinthe
- **Liste active** : Cellules en cours de traitement

### ✅ Avantages
- Extrêmement flexible
- Balance entre longs couloirs et embranchements (Mixed)
- Pas de récursion

### ❌ Désavantages
- Concept plus abstrait
- Nécessite maintenir une liste

### 🎨 Caractéristiques visuelles (Mixed)
- Équilibre entre DFS et BFS
- Couloirs longs ET embranchements
- Très polyvalent

---

## 10. Eller's Algorithm

### 🎯 Principe
Génération **ligne par ligne** avec gestion d'ensembles. Le SEUL algorithme pouvant créer des labyrinthes **infinis** en mémoire O(largeur) !

### 📝 Algorithme
```
POUR CHAQUE ligne :
    1. INITIALISATION :
       Assigner chaque cellule sans ensemble à un nouvel ensemble
    
    2. JONCTIONS HORIZONTALES (Est) :
       Pour chaque cellule et sa voisine de droite :
           SI dans des ensembles différents :
               Décider aléatoirement de fusionner
               SI oui : Supprimer mur + fusionner ensembles
    
    3. JONCTIONS VERTICALES (Sud) :
       Pour chaque ensemble :
           AU MOINS UN passage vers le sud (obligatoire)
           Pour chaque cellule : décider aléatoirement de descendre
           Cellules qui descendent conservent leur ensemble
    
    4. LIGNE SUIVANTE :
       Cellules avec passage au-dessus conservent leur ensemble
       Autres reçoivent de nouveaux ensembles

DERNIÈRE LIGNE : Fusionner tous les ensembles restants
```

### 🔑 Concepts Clés
- **Ensembles par ligne** : Chaque ligne gère ses propres ensembles
- **Garantie de connexion** : Au moins 1 passage sud par ensemble
- **Streaming** : Peut générer ligne par ligne sans tout stocker
- **Mémoire O(width)** : Au lieu de O(width × height)

### 📖 Exemple Visuel
```
Ligne 1: [A][A][B]    A-A même ensemble, B séparé
Descente:  ↓      ↓    A descend 1 fois, B descend  
Ligne 2: [A][C][B]    C = nouveau ensemble
```

### ✅ Avantages
- 🏆 **Peut créer des labyrinthes INFINIS**
- Mémoire minimale : O(largeur)
- Rapide : O(largeur × hauteur)
- Labyrinthes uniformes

### ❌ Désavantages
- 😰 **Algorithme le plus complexe à implémenter**
- Bugs subtils si erreur
- Difficile à visualiser en temps réel

### 🎨 Caractéristiques visuelles
- Distribution équilibrée
- Pas de biais
- Structure ligne par ligne parfois visible

### 💡 Cas d'Usage
- Jeux procéduraux (Minecraft-like)
- Monde infini (génération à la demande)
- Roguelikes avec niveaux infinis

---

## 11. Recursive Division

### 🎯 Principe
Approche **top-down** : au lieu de creuser des passages, on **construit des murs** !

### 📝 Algorithme
```
1. Commencer avec une pièce COMPLÈTEMENT VIDE
2. Diviser la pièce en 4 quadrants :
   - Tracer un mur HORIZONTAL
   - Tracer un mur VERTICAL
3. Faire 3 passages (sur 4 intersections mur-mur)
4. Appliquer récursivement sur chaque quadrant
5. Arrêter quand les pièces sont trop petites

ORIENTATION :
    SI largeur > hauteur : Diviser verticalement
    SI hauteur > largeur : Diviser horizontalement
    SI carré : Choisir aléatoirement
```

### 🔑 Concepts Clés
- **Top-down** : Diviser pour régner (inverse de tous les autres)
- **Construction de murs** : Au lieu de creuser
- **Structure hiérarchique** : Quadrants emboîtés
- **Division adaptative** : Selon les proportions de la pièce

### 📖 Exemple Visuel ASCII
```
Étape 1: Grande pièce vide

Étape 2: Division
┌─────┬─────┐
│  A  │  B  │
├─────┼─────┤
│  C  │  D  │
└─────┴─────┘

Étape 3: 3 passages (ex: A-B, A-C, B-D)
┌─────○─────┐
│  A  │  B  │
○─────█─────○    (█ = pas de passage)
│  C  │  D  │
└─────┴─────┘

Étape 4: Récursion sur A, B, C, D
```

### ✅ Avantages
- Visuellement distinct et reconnaissable
- Excellent pour les jeux (salles avec couloirs)
- Rapide
- Structure hiérarchique claire

### ❌ Désavantages
- Trop prévisible (motif visible)
- Manque d'aspect "organique"
- Facile à résoudre
- Pas réaliste

### 🎨 Caractéristiques visuelles
- Motifs géométriques rectangulaires
- Longs couloirs droits
- Structure de "salles" visible
- Quadrants emboîtés

### 💡 Cas d'Usage
- Cartes de jeux vidéo
- Donjons RPG
- Niveaux procéduraux avec structure

---

## 📊 Comparaison des Algorithmes

### Vitesse

| Rang | Algorithme | Complexité | Vitesse |
|------|------------|------------|---------|
| 🥇 | Binary Tree | O(n) | ⚡⚡⚡⚡⚡ Instantané |
| 🥇 | Sidewinder | O(n) | ⚡⚡⚡⚡⚡ Instantané |
| 🥈 | Eller's | O(n) | ⚡⚡⚡⚡ Très rapide |
| 🥈 | Recursive Division | O(n) | ⚡⚡⚡⚡ Très rapide |
| 🥉 | Recursive Backtracker | O(n) | ⚡⚡⚡ Rapide |
| 🥉 | Kruskal's | O(n α(n)) | ⚡⚡⚡ Rapide |
| 🥉 | Prim's | O(n log n) | ⚡⚡⚡ Rapide |
| 🥉 | Growing Tree | O(n) | ⚡⚡⚡ Rapide |
| 🥉 | Hunt-and-Kill | O(n²) | ⚡⚡ Moyen |
| 🥉 | Wilson's | O(n log n) | ⚡⚡ Moyen |
| 🐌 | Aldous-Broder | O(n²) | 🐌 Lent |

### Qualité du Labyrinthe

| Critère | Meilleurs Algorithmes |
|---------|----------------------|
| **Distribution uniforme** | Wilson's, Aldous-Broder, Kruskal's |
| **Longs couloirs** | Recursive Backtracker, Hunt-and-Kill |
| **Embranchements** | Prim's, Growing Tree (Mixed) |
| **Équilibre** | Kruskal's, Wilson's, Growing Tree |
| **Aspect organique** | Recursive Backtracker, Hunt-and-Kill |
| **Structure géométrique** | Recursive Division |

### Complexité d'Implémentation

| Difficulté | Algorithmes |
|------------|-------------|
| ⭐ Facile | Binary Tree, Sidewinder, Random |
| ⭐⭐ Moyen | Recursive Backtracker, Prim's |
| ⭐⭐⭐ Avancé | Growing Tree, Hunt-and-Kill, Aldous-Broder |
| ⭐⭐⭐⭐ Expert | Kruskal's (Union-Find), Wilson's, Recursive Division |
| ⭐⭐⭐⭐⭐ Maître | Eller's |

### Usage Recommandé

| Cas d'Usage | Algorithme |
|-------------|------------|
| **Apprentissage** | Binary Tree → Sidewinder → Recursive Backtracker |
| **Production générale** | Recursive Backtracker, Kruskal's |
| **Mondes infinis** | Eller's |
| **Niv eaux de jeu** | Recursive Division, Prim's |
| **Statistiques/Recherche** | Wilson's, Aldous-Broder |
| **Tests rapides** | Binary Tree, Random |
| **Labyrinthes difficiles** | Prim's, Growing Tree |

### Biais Directionnels

| Algorithme | Biais | Force |
|------------|-------|-------|
| Binary Tree | Nord-Est | ⚠️⚠️⚠️ Fort |
| Sidewinder | Nord-Est | ⚠️⚠️ Moyen |
| Recursive Division | Géométrique | ⚠️ Léger |
| **Autres** | **Aucun** | ✅ Pas de biais |

---

## 🎓 Parcours d'Apprentissage Recommandé

### Niveau 1 : Débutant
1. **Binary Tree** - Comprendre les bases
2. **Recursive Backtracker** - Apprendre la récursion
3. **Sidewinder** - Concept de "runs"

### Niveau 2 : Intermédiaire
4. **Prim's** - Structures de frontière
5. **Growing Tree** - Paramétrage flexible
6. **Hunt-and-Kill** - Deux phases distinctes

### Niveau 3 : Avancé
7. **Kruskal's** - Union-Find
8. **Wilson's** - Loop-erased random walk
9. **Recursive Division** - Top-down

### Niveau 4 : Expert
10. **Eller's** - Génération streaming
11. **Aldous-Broder** - Distribution uniforme

---

## 🚀 Prochaines Étapes

1. **Implémenter** ces algorithmes un par un
2. **Comparer** visuellement les résultats
3. **Analyser** les performances
4. **Tester** différentes stratégies pour Growing Tree
5. **Créer** vos propres variantes !

---

## 📚 Ressources

- [Maze Generation Wikipedia](https://en.wikipedia.org/wiki/Maze_generation_algorithm)
- [Think Labyrinth](http://www.astrolog.org/labyrnth/algrithm.htm)
- [Jamis Buck's Blog](http://weblog.jamisbuck.org/2011/2/7/maze-generation-algorithm-recap)

---

**Bon apprentissage ! 🎉**
