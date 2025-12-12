# AlgoVisualizer

Une application Spring Boot pour visualiser des algorithmes de tri et de graphes en temps réel dans le navigateur.

## Fonctionnalités

- **Visualisation d'algorithmes de tri** : BubbleSort, QuickSort, InsertionSort
- **Contrôles vidéo** : Play/Pause, Stop, Timer
- **Mode sombre/clair** avec toggle animé
- **Tailles variables** : 15, 50, 100, 250, 500 éléments
- **Interface moderne** avec animations CSS

## Prérequis

- **Java 21** ou supérieur
- Maven Wrapper inclus (pas besoin d'installer Maven)

## Build et Exécution

### Windows PowerShell
```powershell
# Définir JAVA_HOME (à faire une fois)
[System.Environment]::SetEnvironmentVariable('JAVA_HOME', 'C:\Program Files\Java\jdk-21', 'User')
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"

# Lancer l'application
.\mvnw.cmd spring-boot:run
```

### Linux/Mac
```bash
./mvnw spring-boot:run
```

Le serveur démarre sur le port 8080.

## Accéder au Visualiseur

Ouvrez votre navigateur et allez sur :
```
http://localhost:8080
```

Sélectionnez un algorithme, choisissez une taille, et regardez la visualisation en action !

## Comment Ajouter un Nouvel Algorithme de Tri

### 1. Créer la Classe de l'Algorithme

Créez une nouvelle classe dans `src/main/java/fr/charles/algovisualizer/algorithms/sorting/` :

```java
package fr.charles.algovisualizer.algorithms.sorting;

import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component("nomAlgorithme")
public class NomAlgorithme implements SortingAlgorithm {
    
    @Override
    public List<int[]> sort(int[] array) {
        List<int[]> steps = new ArrayList<>();
        int[] arr = array.clone();
        
        // Enregistrer l'état initial
        steps.add(arr.clone());
        
        // Votre logique de tri ici
        // Après chaque modification significative, ajoutez :
        // steps.add(arr.clone());
        
        // Exemple simple (à remplacer par votre algorithme)
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Échanger
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    
                    // Enregistrer l'étape
                    steps.add(arr.clone());
                }
            }
        }
        
        return steps;
    }
}
```

**Points clés :**
- `@Component("nomAlgorithme")` : Le nom doit correspondre à la valeur dans le HTML
- `implements SortingAlgorithm` : Obligatoire
- `steps.add(arr.clone())` : Enregistre chaque étape pour la visualisation
- Utilisez `arr.clone()` pour éviter les références

### 2. Ajouter l'Algorithme dans le HTML

Modifiez `src/main/resources/static/index.html` :

```html
<select id="algorithm">
    <option value="bubble">Bubble Sort</option>
    <option value="quick">Quick Sort</option>
    <option value="insertion">Insertion Sort</option>
    <option value="nomAlgorithme">Nom de Votre Algorithme</option>
</select>
```

La valeur `value="nomAlgorithme"` doit correspondre au nom dans `@Component("nomAlgorithme")`.

### 3. Tester

Redémarrez l'application et testez votre nouvel algorithme !

## Structure du Projet

```
src/main/
├── java/fr/charles/algovisualizer/
│   ├── AlgoVisualizerApplication.java
│   ├── algorithms/
│   │   ├── sorting/
│   │   │   ├── SortingAlgorithm.java      # Interface
│   │   │   ├── BubbleSort.java
│   │   │   ├── QuickSort.java
│   │   │   └── InsertionSort.java
│   │   └── graph/
│   │       ├── GraphAlgorithm.java
│   │       └── Dijkstra.java
│   ├── controllers/
│   │   ├── SortingController.java         # API REST pour le tri
│   │   └── GraphController.java
│   ├── services/
│   │   ├── SortingService.java
│   │   └── GraphService.java
│   └── config/
│       └── WebConfig.java
└── resources/
    ├── application.properties
    └── static/
        ├── index.html                      # Interface utilisateur
        ├── visualizer.js                   # Logique de visualisation
        └── styles.css                      # Styles et animations
```

## Technologies Utilisées

- **Backend** : Spring Boot 3.4.1, Java 21
- **Frontend** : HTML5 Canvas, Vanilla JavaScript, CSS3
- **Build** : Maven

## Conseils pour les Nouveaux Algorithmes

1. **Optimisation** : Pour les grands tableaux (500 éléments), n'enregistrez pas TOUTES les étapes. Enregistrez uniquement les étapes significatives.

2. **Performance** : Le JavaScript limite automatiquement à 1000 frames affichées pour éviter les débordements.

3. **Débogage** : Utilisez `console.log()` dans `visualizer.js` et les logs Spring Boot pour déboguer.

4. **Tests** : Créez des tests unitaires dans `src/test/java/` pour vérifier votre algorithme.

## Exemple de Test

```java
package fr.charles.algovisualizer.algorithms.sorting;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NomAlgorithmeTest {
    
    @Test
    void testSort() {
        NomAlgorithme algo = new NomAlgorithme();
        int[] input = {5, 2, 8, 1, 9};
        
        List<int[]> steps = algo.sort(input);
        
        assertNotNull(steps);
        assertTrue(steps.size() > 0);
        
        // Vérifier que le résultat final est trié
        int[] result = steps.get(steps.size() - 1);
        for (int i = 0; i < result.length - 1; i++) {
            assertTrue(result[i] <= result[i + 1]);
        }
    }
}
```

## License

MIT
