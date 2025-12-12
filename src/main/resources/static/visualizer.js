let animationState = {
    isRunning: false,
    isPaused: false,
    intervalId: null,
    startTime: null,
    elapsedTime: 0,
    pausedTime: 0
};

async function startSorting(size = 50) {
    // Empêcher de lancer un nouveau tri si un est déjà en cours
    if (animationState.isRunning) {
        alert('Un tri est déjà en cours ! Utilisez le bouton Stop pour l\'arrêter.');
        return;
    }
    
    const algorithm = document.getElementById("algorithm").value;

    // Réinitialiser l'état
    stopAnimation();
    animationState = {
        isRunning: false,
        isPaused: false,
        intervalId: null,
        startTime: null,
        elapsedTime: 0,
        pausedTime: 0
    };
    
    // Désactiver tous les boutons de tri
    disableSortingButtons(true);

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

    // Afficher les contrôles
    document.getElementById('playPauseBtn').style.display = 'inline-flex';
    document.getElementById('stopBtn').style.display = 'inline-block';
    document.getElementById('timerDisplay').style.display = 'block';
    document.getElementById('playPauseInput').checked = true;
    updateStatus('running');

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

    // Démarrer le timer
    animationState.startTime = Date.now();
    animationState.isRunning = true;
    updateTimer();

    // Utiliser setInterval au lieu de requestAnimationFrame pour éviter le stack overflow
    animationState.intervalId = setInterval(() => {
        if (animationState.isPaused) {
            return; // Ne rien faire si en pause
        }

        if (index >= steps.length) {
            stopAnimation(true);
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

function togglePlayPause() {
    const checkbox = document.getElementById('playPauseInput');
    
    if (checkbox.checked) {
        // Play
        animationState.isPaused = false;
        if (animationState.pausedTime > 0) {
            animationState.startTime = Date.now() - animationState.elapsedTime;
        }
        updateStatus('running');
    } else {
        // Pause
        animationState.isPaused = true;
        animationState.pausedTime = Date.now();
        updateStatus('paused');
    }
}

function stopAnimation(completed = false) {
    if (animationState.intervalId) {
        clearInterval(animationState.intervalId);
        animationState.intervalId = null;
    }
    animationState.isRunning = false;
    
    // Calculer et afficher le temps total
    const totalTime = (animationState.elapsedTime / 1000).toFixed(2);
    document.getElementById('timer').textContent = totalTime + 's';
    
    if (completed) {
        updateStatus('completed');
        document.getElementById('status').textContent = 'Terminé (Temps total: ' + totalTime + 's)';
    } else {
        updateStatus('paused');
        document.getElementById('status').textContent = 'Arrêté';
    }
    
    // Réactiver les boutons de tri
    disableSortingButtons(false);
}

function updateTimer() {
    if (!animationState.isRunning) return;
    
    const timerInterval = setInterval(() => {
        if (!animationState.isRunning) {
            clearInterval(timerInterval);
            return;
        }
        
        if (!animationState.isPaused) {
            animationState.elapsedTime = Date.now() - animationState.startTime;
            const seconds = (animationState.elapsedTime / 1000).toFixed(2);
            document.getElementById('timer').textContent = seconds + 's';
        }
    }, 10);
}

function updateStatus(status) {
    const statusElement = document.getElementById('status');
    statusElement.className = 'status ' + status;
    
    const statusText = {
        'running': 'En cours',
        'paused': 'En pause',
        'completed': 'Terminé'
    };
    
    statusElement.textContent = statusText[status] || status;
}

function stopSorting() {
    stopAnimation(false);
    // Cacher les contrôles
    document.getElementById('playPauseBtn').style.display = 'none';
    document.getElementById('stopBtn').style.display = 'none';
    document.getElementById('timerDisplay').style.display = 'none';
}

function disableSortingButtons(disabled) {
    const buttons = document.querySelectorAll('.size-btn');
    const algorithmSelect = document.getElementById('algorithm');
    
    buttons.forEach(btn => {
        btn.disabled = disabled;
        btn.style.opacity = disabled ? '0.5' : '1';
        btn.style.cursor = disabled ? 'not-allowed' : 'pointer';
    });
    
    algorithmSelect.disabled = disabled;
    algorithmSelect.style.opacity = disabled ? '0.5' : '1';
}
