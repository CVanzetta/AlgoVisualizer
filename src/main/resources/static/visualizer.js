async function startSorting() {
    const algorithm = document.getElementById("algorithm").value;

    // Générer un tableau aléatoire
    const array = Array.from({ length: 50 }, () => Math.floor(Math.random() * 300));
    console.log("Tableau envoyé :", array); // Log pour vérifier le contenu

    // Envoyer une requête à l'API pour récupérer les étapes du tri
    const response = await fetch(`/api/sort/${algorithm}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(array), // Vérifier que le tableau est bien converti en JSON
    });

    if (!response.ok) {
        console.error("Erreur de requête :", response.statusText); // Log de l'erreur
        alert("Error: " + response.statusText);
        return;
    }

    const steps = await response.json();

    // Démarrer la visualisation
    visualizeSorting(steps);
}

async function loadAlgorithms() {
    try {
        const response = await fetch('/api/sort/algorithms');
        if (!response.ok) {
            throw new Error('Failed to load algorithms');
        }
        const algorithms = await response.json();
        const select = document.getElementById('algorithm');
        select.innerHTML = '';
        Object.keys(algorithms).forEach(key => {
            const option = document.createElement('option');
            option.value = key;
            option.textContent = algorithms[key];
            select.appendChild(option);
        });
    } catch (e) {
        console.error('Error loading algorithms', e);
    }
}

document.addEventListener('DOMContentLoaded', loadAlgorithms);
