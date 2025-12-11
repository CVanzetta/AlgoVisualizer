async function startSorting(size = 50) {
    const algorithm = document.getElementById("algorithm").value;

    // Générer un tableau aléatoire
    const array = Array.from({ length: size }, () => Math.floor(Math.random() * 1000));
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

function visualizeSorting(steps) {
    console.log("VERSION 2 - Utilisation de setInterval");
    const canvas = document.getElementById("canvas");
    const ctx = canvas.getContext("2d");

    const width = canvas.width;
    const height = canvas.height;

    // Déterminer la valeur maximale sans utiliser flat() pour éviter le stack overflow
    let maxVal = 0;
    for (let i = 0; i < steps.length; i++) {
        for (let j = 0; j < steps[i].length; j++) {
            if (steps[i][j] > maxVal) {
                maxVal = steps[i][j];
            }
        }
    }

    // Optimisation : limiter le nombre d'étapes affichées pour éviter le débordement de pile
    const maxStepsToDisplay = 1000;
    let stepInterval = 1;
    if (steps.length > maxStepsToDisplay) {
        stepInterval = Math.ceil(steps.length / maxStepsToDisplay);
    }

    let index = 0;

    function drawArray(array) {
        ctx.clearRect(0, 0, width, height);
        const barWidth = width / array.length;

        for (let i = 0; i < array.length; i++) {
            const value = array[i];
            const barHeight = (value / maxVal) * height;
            ctx.fillStyle = "steelblue";
            ctx.fillRect(i * barWidth, height - barHeight, barWidth - 1, barHeight);
        }
    }

    // Utiliser setInterval au lieu de requestAnimationFrame pour éviter le stack overflow
    const interval = setInterval(() => {
        if (index >= steps.length) {
            clearInterval(interval);
            // Afficher l'état final
            drawArray(steps[steps.length - 1]);
            return;
        }

        // Sauter des étapes si nécessaire pour garder une animation fluide
        if (index % stepInterval === 0 || index === steps.length - 1) {
            drawArray(steps[index]);
        }

        index++;
    }, 1); // 1ms entre chaque frame pour une animation rapide
}
