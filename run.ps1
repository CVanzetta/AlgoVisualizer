# Script de démarrage AlgoVisualizer avec JDK 21
# Usage: .\run.ps1

Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "       AlgoVisualizer - Démarrage avec JDK 21" -ForegroundColor Green
Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

# Configuration JDK 21
$JDK_PATH = "C:\Program Files\Java\jdk-21.0.10"

if (-not (Test-Path $JDK_PATH)) {
    Write-Host "❌ ERREUR: JDK 21 non trouvé à: $JDK_PATH" -ForegroundColor Red
    Write-Host ""
    Write-Host "Chemins Java disponibles:" -ForegroundColor Yellow
    Get-ChildItem "C:\Program Files\Java" -ErrorAction SilentlyContinue | Select-Object Name | Format-Table
    exit 1
}

# Configuration des variables d'environnement
$env:JAVA_HOME = $JDK_PATH
$env:Path = "$JDK_PATH\bin;" + $env:Path

Write-Host "✓ JAVA_HOME configuré: $env:JAVA_HOME" -ForegroundColor Green

# Vérification version Java
Write-Host ""
Write-Host "Version Java:" -ForegroundColor Cyan
java -version
Write-Host ""

# Démarrage de l'application
Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host "       Démarrage de l'application..." -ForegroundColor Yellow
Write-Host "═══════════════════════════════════════════════════════════" -ForegroundColor Cyan
Write-Host ""

.\mvnw.cmd spring-boot:run
