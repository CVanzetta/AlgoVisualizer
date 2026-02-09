let animationState = {
    isRunning: false,
    isPaused: false,
    intervalId: null,
    timerIntervalId: null,
    startTime: null,
    elapsedTime: 0,
    pausedTime: 0
};

async function startSorting(size = 50) {
    // Prevent starting a new sort if one is already running
    if (animationState.isRunning) {
        showNotification('A sort is already running! Use the Stop button to end it.');
        return;
    }
    
    const algorithm = document.getElementById("algorithm").value;
    
    // Warn about slow algorithms with large datasets
    const slowAlgorithms = ['bubble-sort', 'insertion-sort'];
    const mediumAlgorithms = ['shell-sort'];
    
    if (slowAlgorithms.includes(algorithm) && size > 500) {
        const message = `⚠️ WARNING: ${algorithm.toUpperCase()} is very slow with ${size} elements!\n\n` +
                       `• Complexity: O(n²) = ${(size * size).toLocaleString()} operations\n` +
                       `• Estimated time: ${size > 5000 ? 'Several minutes' : size > 1000 ? '1-2 minutes' : '10-30 seconds'}\n\n` +
                       `Recommendation: Use Quick Sort, Merge Sort or Radix Sort for large datasets.\n\n` +
                       `Continue anyway?`;
        if (!confirm(message)) {
            disableSortingButtons(false);
            return;
        }
    } else if (mediumAlgorithms.includes(algorithm) && size > 5000) {
        if (!confirm(`⚠️ ${algorithm} with ${size} elements may take 30-60 seconds.\n\nContinue?`)) {
            disableSortingButtons(false);
            return;
        }
    }
    
    // Info notification for large datasets with fast algorithms
    if (size >= 1000 && !slowAlgorithms.includes(algorithm) && !mediumAlgorithms.includes(algorithm)) {
        showNotification(`✅ Good choice! ${algorithm} handles ${size} elements efficiently (O(n log n))`, 3000);
    }

    // Reset animation state (preserving individual properties to avoid losing references)
    stopAnimation();
    animationState.isRunning = false;
    animationState.isPaused = false;
    animationState.intervalId = null;
    animationState.timerIntervalId = null;
    animationState.startTime = null;
    animationState.elapsedTime = 0;
    animationState.pausedTime = 0;
    
    // Disable all sorting buttons
    disableSortingButtons(true);

    // Générer un tableau aléatoire
    // NOSONAR javascript:S2245 - Math.random() est sûr ici car utilisé uniquement
    // pour générer des données de test à des fins de visualisation, pas pour de la sécurité
    const array = Array.from({ length: size }, () => Math.floor(Math.random() * 1000)); // NOSONAR
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

    // Start visualization
    visualizeSorting(steps);
}

function visualizeSorting(steps) {
    console.log("VERSION 2 - Using setInterval");
    const canvas = document.getElementById("canvas");
    const ctx = canvas.getContext("2d");

    const width = canvas.width;
    const height = canvas.height;

    // Find maximum value without using flat() to avoid stack overflow
    let maxVal = 0;
    for (let i = 0; i < steps.length; i++) {
        for (let j = 0; j < steps[i].length; j++) {
            if (steps[i][j] > maxVal) {
                maxVal = steps[i][j];
            }
        }
    }

    // Optimization: limit the number of steps displayed to avoid stack overflow
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

    // Start the timer
    animationState.startTime = Date.now();
    animationState.isRunning = true;
    updateTimer();

    // Use setInterval instead of requestAnimationFrame to avoid stack overflow
    animationState.intervalId = setInterval(() => {
        if (animationState.isPaused) {
            return; // Do nothing if paused
        }

        if (index >= steps.length) {
            stopAnimation(true);
            drawArray(steps[steps.length - 1]);
            return;
        }

        // Skip steps if necessary to keep animation smooth
        if (index % stepInterval === 0 || index === steps.length - 1) {
            drawArray(steps[index]);
        }

        index++;
    }, 1); // 1ms between each frame for fast animation
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
    if (animationState.timerIntervalId) {
        clearInterval(animationState.timerIntervalId);
        animationState.timerIntervalId = null;
    }
    animationState.isRunning = false;
    
    // Calculate and display total time
    const totalTime = (animationState.elapsedTime / 1000).toFixed(2);
    document.getElementById('timer').textContent = totalTime + 's';
    
    if (completed) {
        updateStatus('completed');
        document.getElementById('status').textContent = 'Completed (Total time: ' + totalTime + 's)';
    } else {
        updateStatus('stopped');
        document.getElementById('status').textContent = 'Stopped';
    }
    
    // Re-enable sorting buttons
    disableSortingButtons(false);
}

function updateTimer() {
    if (!animationState.isRunning) return;
    
    // Clear existing timer if any
    if (animationState.timerIntervalId) {
        clearInterval(animationState.timerIntervalId);
    }
    
    animationState.timerIntervalId = setInterval(() => {
        if (!animationState.isRunning) {
            clearInterval(animationState.timerIntervalId);
            animationState.timerIntervalId = null;
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
        'ready': 'Ready',
        'running': 'Running',
        'paused': 'Paused',
        'stopped': 'Stopped',
        'completed': 'Completed'
    };
    
    statusElement.textContent = statusText[status] || status;
}

function stopSorting() {
    stopAnimation(false);
    // Hide controls
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

function showNotification(message, duration = 3000) {
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.textContent = message;
    document.body.appendChild(notification);
    
    // Show notification
    setTimeout(() => notification.classList.add('show'), 10);
    
    // Hide and remove after specified duration
    setTimeout(() => {
        notification.classList.remove('show');
        setTimeout(() => notification.remove(), 300);
    }, duration);
}
