# AlgoVisualizer 🎨

Une application Spring Boot sécurisée pour visualiser des algorithmes de tri et de graphes en temps réel dans le navigateur.

## ✨ Fonctionnalités

### Algorithmes de Tri
- **7 algorithmes** : BubbleSort, QuickSort, InsertionSort, MergeSort, ShellSort, BucketSort, RadixSort
- **Tailles variables** : 15, 50, 100, 250, 500, 1000, 5000, 10000 éléments
- **Contrôles vidéo** : Play/Pause, Stop, Timer avec chronomètre
- **Interface moderne** avec mode sombre/clair

### Algorithmes de Graphes
- **Dijkstra** : Plus court chemin
- **Visualisation interactive** des graphes

### 🔐 Sécurité Renforcée
- ✅ Rate Limiting (protection DDoS)
- ✅ Request Timeout (30s)
- ✅ Validation stricte des entrées
- ✅ CORS configuré
- ✅ Headers de sécurité (CSP, HSTS, X-Frame-Options)
- ✅ Logging et audit complet

## 📋 Prérequis

- **Java 21** (JDK requis, pas JRE)
- Maven Wrapper inclus (pas besoin d'installer Maven)

## 🚀 Démarrage Rapide

### Windows PowerShell

#### Option 1: Scripts automatiques (Recommandé)
```powershell
# Compilation
.\build.ps1

# Démarrage
.\run.ps1
```

#### Option 2: Manuel
```powershell
# Configurer JDK 21
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21.0.10"
$env:Path = "C:\Program Files\Java\jdk-21.0.10\bin;" + $env:Path

# Lancer l'application
.\mvnw.cmd spring-boot:run
```

### Linux/Mac
```bash
# S'assurer que JAVA_HOME pointe vers JDK 21
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH

# Lancer l'application
./mvnw spring-boot:run
```

📖 **Guide complet** : Voir [CONFIGURATION_JDK.md](CONFIGURATION_JDK.md)

## 🌐 Accéder à l'Application

Ouvrez votre navigateur sur :
```
http://localhost:8080
```

### API Endpoints

```bash
# Liste des algorithmes de tri
GET http://localhost:8080/api/sort/algorithms

# Exécuter un tri
POST http://localhost:8080/api/sort/{algorithm}
Body: [5, 2, 8, 1, 9]

# Liste des algorithmes de graphes
GET http://localhost:8080/api/graph/algorithms

# Trouver le plus court chemin
POST http://localhost:8080/api/graph/dijkstra
Body: {"graph": {...}, "start": 0, "end": 5}
```

### Limites de Sécurité
- ⚠️ Rate Limit: **20 requêtes/minute** par IP sur les endpoints API
- ⚠️ Timeout: **30 secondes** maximum par requête
- ⚠️ Taille max: **100,000 éléments** pour les tableaux
- ⚠️ Graphes: **1000 nœuds** max, **100 arêtes/nœud** max

## 📚 Documentation

- [SECURITY_AUDIT.md](SECURITY_AUDIT.md) - Rapport d'audit de sécurité complet
- [SECURITY_FIXES.md](SECURITY_FIXES.md) - Résumé des corrections de sécurité
- [SECURITY_ARCHITECTURE.md](SECURITY_ARCHITECTURE.md) - Architecture et diagrammes
- [CONFIGURATION_JDK.md](CONFIGURATION_JDK.md) - Configuration JDK 21

## 🔧 Comment Ajouter un Nouvel Algorithme de Tri

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

## 📁 Structure du Projet

```
AlgoVisualizer/
├── src/main/
│   ├── java/fr/charles/algovisualizer/
│   │   ├── AlgoVisualizerApplication.java
│   │   ├── algorithms/
│   │   │   ├── sorting/                    # Algorithmes de tri
│   │   │   │   ├── SortingAlgorithm.java
│   │   │   │   ├── BubbleSort.java
│   │   │   │   ├── QuickSort.java
│   │   │   │   ├── MergeSort.java
│   │   │   │   ├── InsertionSort.java
│   │   │   │   ├── ShellSort.java
│   │   │   │   ├── BucketSort.java
│   │   │   │   └── RadixSort.java
│   │   │   └── graph/                      # Algorithmes de graphes
│   │   │       ├── GraphAlgorithm.java
│   │   │       └── Dijkstra.java
│   │   ├── config/                         # 🔐 Configuration sécurité
│   │   │   ├── SecurityConfig.java
│   │   │   ├── WebConfig.java
│   │   │   ├── RateLimitingFilter.java
│   │   │   ├── RequestTimeoutFilter.java
│   │   │   └── ContentTypeValidationFilter.java
│   │   ├── controllers/                    # API REST
│   │   │   ├── SortingController.java
│   │   │   └── GraphController.java
│   │   ├── dto/                            # Data Transfer Objects
│   │   │   └── GraphRequest.java
│   │   ├── services/                       # Business Logic
│   │   │   ├── SortingService.java
│   │   │   └── GraphService.java
│   │   ├── validation/                     # 🔐 Validateurs custom
│   │   │   ├── ValidGraph.java
│   │   │   └── ValidGraphValidator.java
│   │   └── exception/                      # Gestion erreurs
│   │       └── GlobalExceptionHandler.java
│   └── resources/
│       ├── application.properties          # Configuration app
│       └── static/                         # Frontend
│           ├── index.html
│           ├── visualizer.js
│           └── styles.css
├── src/test/                              # Tests unitaires
├── run.ps1                                # 🚀 Script démarrage
├── build.ps1                              # 🔨 Script compilation
├── README.md                              # Ce fichier
├── CONFIGURATION_JDK.md                   # Guide JDK 21
├── SECURITY_AUDIT.md                      # 🔐 Audit sécurité
├── SECURITY_FIXES.md                      # 🔐 Corrections
└── SECURITY_ARCHITECTURE.md               # 🔐 Architecture
```

## 🛠️ Technologies Utilisées

### Backend
- **Spring Boot** 3.4.10
- **Java** 21
- **Spring Security** (headers, CORS)
- **Jakarta Validation** (Bean Validation)
- **Bucket4j** 8.7.0 (Rate Limiting)
- **SLF4J** (Logging)

### Frontend
- **HTML5 Canvas** (Visualisation)
- **Vanilla JavaScript** (ES6+)
- **CSS3** (Animations, Flexbox, Grid)

### Build & Deploy
- **Maven** (Maven Wrapper inclus)
- **Tomcat** embedded (Spring Boot)

## 📝 Conseils pour les Nouveaux Algorithmes

1. **Optimisation** : Pour les grands tableaux (>500 éléments), n'enregistrez pas TOUTES les étapes

2. **Performance** : Le JavaScript limite automatiquement à 1000 frames affichées

3. **Débogage** : Utilisez `console.log()` dans `visualizer.js` et les logs Spring Boot

4. **Tests** : Créez des tests unitaires dans `src/test/java/`

5. **Sécurité** : Respectez les limites de validation (max 100K éléments)

## ✅ Tests de Sécurité

```bash
# Test Rate Limiting (doit bloquer après 20 req)
for ($i=1; $i -le 25; $i++) {
  curl http://localhost:8080/api/sort/bubble-sort `
    -H "Content-Type: application/json" `
    -d "[1,2,3]"
}

# Test Timeout (requête longue)
curl -X POST http://localhost:8080/api/sort/bubble-sort `
  -H "Content-Type: application/json" `
  -d "[$(1..50000 -join ',')]"

# Test Validation (taille excessive)
curl -X POST http://localhost:8080/api/sort/bubble-sort `
  -H "Content-Type: application/json" `
  -d "[$(1..150000 -join ',')]"
```

## 🐛 Résolution de Problèmes

### Port 8080 déjà utilisé
```powershell
# Trouver le processus
netstat -ano | findstr :8080

# Tuer le processus
Stop-Process -Id <PID> -Force
```

### Compilation échoue
```powershell
# Vérifier la version Java
java -version
# Doit afficher: java version "21.0.10"

# Si ce n'est pas le cas
.\build.ps1
```

### Rate limit atteint
```
Erreur: 429 Too Many Requests
Solution: Attendre 1 minute ou redémarrer l'application
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

## 📄 License

MIT

---

**Développé avec ☕ et 🔐**  
Sécurisé par GitHub Copilot Security Analysis - Février 2026
