# Configuration SonarQube pour AlgoVisualizer

## Options d'analyse

### Option 1 : SonarCloud (Cloud, gratuit pour projets open source)

1. **Créer un compte sur SonarCloud** : https://sonarcloud.io/
2. **Créer une nouvelle organisation et projet**
3. **Générer un token** : My Account → Security → Generate Token
4. **Configurer le projet** :
   - Mettre à jour `sonar.organization` dans le pom.xml avec votre organisation
   - Exporter votre token : 
     ```powershell
     $env:SONAR_TOKEN="votre-token"
     ```
5. **Lancer l'analyse** :
   ```powershell
   ./mvnw clean verify sonar:sonar -Dsonar.token=$env:SONAR_TOKEN
   ```

### Option 2 : SonarQube Local (Auto-hébergé)

1. **Télécharger SonarQube** : https://www.sonarsource.com/products/sonarqube/downloads/
2. **Démarrer SonarQube** :
   ```powershell
   cd sonarqube-x.x.x/bin/windows-x86-64
   ./StartSonar.bat
   ```
3. **Accéder à l'interface** : http://localhost:9000 (admin/admin)
4. **Créer un projet et un token**
5. **Lancer l'analyse** :
   ```powershell
   ./mvnw clean verify sonar:sonar `
     -Dsonar.host.url=http://localhost:9000 `
     -Dsonar.token=votre-token
   ```

## Analyse avec Jacoco (couverture de code)

Pour générer un rapport de couverture avant l'analyse SonarQube :

```powershell
./mvnw clean test jacoco:report
```

Le rapport sera disponible dans `target/site/jacoco/index.html`

## Commandes utiles

- **Analyse complète** :
  ```powershell
  ./mvnw clean verify sonar:sonar
  ```

- **Analyse sans tests** :
  ```powershell
  ./mvnw sonar:sonar -DskipTests
  ```

- **Voir les résultats localement** :
  - Ouvrir `target/site/jacoco/index.html` pour la couverture de code

## Configuration

Les propriétés principales sont dans :
- `pom.xml` : configuration Maven et plugins
- `sonar-project.properties` : configuration SonarQube

## Plugins ajoutés

- **sonar-maven-plugin** : Scanner SonarQube pour Maven
- **jacoco-maven-plugin** : Couverture de code Java
