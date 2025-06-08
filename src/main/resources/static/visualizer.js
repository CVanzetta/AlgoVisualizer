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

codex/créer-la-fonction-visualizesorting
function visualizeSorting(steps) {
    const canvas = document.getElementById("canvas");
    const ctx = canvas.getContext("2d");

    const width = canvas.width;
    const height = canvas.height;

    // Déterminer la valeur maximale pour mettre à l'échelle les barres
    const allValues = steps.flat();
    const maxVal = Math.max(...allValues);

    let index = 0;

    function draw() {
        if (index >= steps.length) {
            return;
        }

        ctx.clearRect(0, 0, width, height);

        const array = steps[index];
        const barWidth = width / array.length;

        for (let i = 0; i < array.length; i++) {
            const value = array[i];
            const barHeight = (value / maxVal) * height;
            ctx.fillStyle = "steelblue";
            ctx.fillRect(i * barWidth, height - barHeight, barWidth - 1, barHeight);
        }

        index++;
        requestAnimationFrame(draw);
    }

    draw();
}
main
