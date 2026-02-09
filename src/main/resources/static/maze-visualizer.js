// ========================================
// CONFIGURATION DU LABYRINTHE
// ========================================

const canvas = document.getElementById('mazeCanvas');
const ctx = canvas.getContext('2d');

const GRID_SIZE = 40; // Nombre de cellules par côté
const CELL_SIZE = canvas.width / GRID_SIZE;

// Types de cellules
const CELL_TYPES = {
    EMPTY: 0,
    WALL: 1,
    START: 2,
    END: 3,
    VISITED: 4,
    PATH: 5,
    CURRENT: 6
};

// Couleurs
const COLORS = {
    EMPTY: '#FFFFFF',
    WALL: '#333333',
    START: '#4CAF50',
    END: '#F44336',
    VISITED: '#64B5F6',
    PATH: '#FFD54F',
    CURRENT: '#FF6B6B',
    GRID: '#DDDDDD'
};

// Grille du labyrinthe
let maze = [];
let startPos = { x: 1, y: 1 };
let endPos = { x: GRID_SIZE - 2, y: GRID_SIZE - 2 };
let currentAlgorithm = null;
let isRunning = false;
let isDrawing = false;

// ========================================
// INITIALISATION
// ========================================

function initMaze() {
    maze = [];
    for (let y = 0; y < GRID_SIZE; y++) {
        maze[y] = [];
        for (let x = 0; x < GRID_SIZE; x++) {
            maze[y][x] = CELL_TYPES.EMPTY;
        }
    }
    maze[startPos.y][startPos.x] = CELL_TYPES.START;
    maze[endPos.y][endPos.x] = CELL_TYPES.END;
    drawMaze();
}

// ========================================
// RENDU GRAPHIQUE
// ========================================

function drawMaze() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Dessiner les cellules
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            const cellType = maze[y][x];
            let color = COLORS.EMPTY;

            switch (cellType) {
                case CELL_TYPES.WALL:
                    color = COLORS.WALL;
                    break;
                case CELL_TYPES.START:
                    color = COLORS.START;
                    break;
                case CELL_TYPES.END:
                    color = COLORS.END;
                    break;
                case CELL_TYPES.VISITED:
                    color = COLORS.VISITED;
                    break;
                case CELL_TYPES.PATH:
                    color = COLORS.PATH;
                    break;
                case CELL_TYPES.CURRENT:
                    color = COLORS.CURRENT;
                    break;
            }

            ctx.fillStyle = color;
            ctx.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

            // Bordure de la grille
            ctx.strokeStyle = COLORS.GRID;
            ctx.lineWidth = 0.5;
            ctx.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}

// ========================================
// INTERACTIONS UTILISATEUR
// ========================================

canvas.addEventListener('mousedown', (e) => {
    isDrawing = true;
    handleCanvasClick(e);
});

canvas.addEventListener('mousemove', (e) => {
    if (isDrawing) {
        handleCanvasClick(e);
    }
});

canvas.addEventListener('mouseup', () => {
    isDrawing = false;
});

canvas.addEventListener('mouseleave', () => {
    isDrawing = false;
});

function handleCanvasClick(e) {
    if (isRunning) return;

    const rect = canvas.getBoundingClientRect();
    const x = Math.floor((e.clientX - rect.left) / CELL_SIZE);
    const y = Math.floor((e.clientY - rect.top) / CELL_SIZE);

    if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return;

    // Ne pas modifier les cases départ et arrivée lors du dessin
    if ((x === startPos.x && y === startPos.y) || (x === endPos.x && y === endPos.y)) {
        return;
    }

    // Toggle mur
    if (maze[y][x] === CELL_TYPES.WALL) {
        maze[y][x] = CELL_TYPES.EMPTY;
    } else if (maze[y][x] === CELL_TYPES.EMPTY || maze[y][x] === CELL_TYPES.VISITED || maze[y][x] === CELL_TYPES.PATH) {
        maze[y][x] = CELL_TYPES.WALL;
    }

    drawMaze();
}

// ========================================
// GÉNÉRATION DE LABYRINTHE
// ========================================

function generateMaze() {
    initMaze();
    
    // Générer un labyrinthe aléatoire simple
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            if ((x === startPos.x && y === startPos.y) || (x === endPos.x && y === endPos.y)) {
                continue;
            }
            // 30% de chance d'être un mur
            if (Math.random() < 0.3) {
                maze[y][x] = CELL_TYPES.WALL;
            }
        }
    }
    
    drawMaze();
}

function clearWalls() {
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            if (maze[y][x] === CELL_TYPES.WALL) {
                maze[y][x] = CELL_TYPES.EMPTY;
            }
        }
    }
    drawMaze();
}

function resetMaze() {
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            if (maze[y][x] === CELL_TYPES.VISITED || maze[y][x] === CELL_TYPES.PATH || maze[y][x] === CELL_TYPES.CURRENT) {
                maze[y][x] = CELL_TYPES.EMPTY;
            }
        }
    }
    maze[startPos.y][startPos.x] = CELL_TYPES.START;
    maze[endPos.y][endPos.x] = CELL_TYPES.END;
    
    document.getElementById('pathLength').textContent = '-';
    document.getElementById('visitedCount').textContent = '0';
    document.getElementById('executionTime').textContent = '0ms';
    
    drawMaze();
}

// ========================================
// SÉLECTION D'ALGORITHME
// ========================================

function selectAlgorithm(algo) {
    if (isRunning) return;
    
    currentAlgorithm = algo;
    const algoNames = {
        'bfs': 'BFS (Breadth-First Search)',
        'dfs': 'DFS (Depth-First Search)',
        'astar': 'A* (A-Star)',
        'dijkstra': 'Dijkstra',
        'greedy': 'Greedy Best-First'
    };
    
    document.getElementById('currentAlgo').textContent = algoNames[algo] || 'Aucun';
    
    resetMaze();
    runAlgorithm();
}

// ========================================
// UTILITAIRES
// ========================================

function getNeighbors(x, y) {
    const neighbors = [];
    const directions = [
        { dx: 0, dy: -1 }, // Haut
        { dx: 1, dy: 0 },  // Droite
        { dx: 0, dy: 1 },  // Bas
        { dx: -1, dy: 0 }  // Gauche
    ];
    
    for (const dir of directions) {
        const newX = x + dir.dx;
        const newY = y + dir.dy;
        
        if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE) {
            if (maze[newY][newX] !== CELL_TYPES.WALL) {
                neighbors.push({ x: newX, y: newY });
            }
        }
    }
    
    return neighbors;
}

function manhattanDistance(x1, y1, x2, y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
}

function euclideanDistance(x1, y1, x2, y2) {
    return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
}

function reconstructPath(cameFrom, current) {
    const path = [];
    let node = current;
    
    while (cameFrom.has(`${node.x},${node.y}`)) {
        path.unshift(node);
        const prev = cameFrom.get(`${node.x},${node.y}`);
        node = prev;
    }
    
    return path;
}

async function visualizePath(path) {
    for (const node of path) {
        if (maze[node.y][node.x] !== CELL_TYPES.START && maze[node.y][node.x] !== CELL_TYPES.END) {
            maze[node.y][node.x] = CELL_TYPES.PATH;
            drawMaze();
            await sleep(30);
        }
    }
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

// ========================================
// EXÉCUTION DES ALGORITHMES
// ========================================

async function runAlgorithm() {
    if (!currentAlgorithm || isRunning) return;
    
    isRunning = true;
    const startTime = performance.now();
    let visitedCount = 0;
    let path = null;
    
    switch (currentAlgorithm) {
        case 'bfs':
            ({ path, visitedCount } = await bfs());
            break;
        case 'dfs':
            ({ path, visitedCount } = await dfs());
            break;
        case 'astar':
            ({ path, visitedCount } = await astar());
            break;
        case 'dijkstra':
            ({ path, visitedCount } = await dijkstra());
            break;
        case 'greedy':
            ({ path, visitedCount } = await greedyBestFirst());
            break;
    }
    
    const endTime = performance.now();
    const executionTime = Math.round(endTime - startTime);
    
    if (path) {
        await visualizePath(path);
        document.getElementById('pathLength').textContent = path.length;
    } else {
        document.getElementById('pathLength').textContent = 'Aucun chemin';
    }
    
    document.getElementById('visitedCount').textContent = visitedCount;
    document.getElementById('executionTime').textContent = executionTime + 'ms';
    
    isRunning = false;
}

// ========================================
// ALGORITHME BFS (Breadth-First Search)
// ========================================
// À IMPLÉMENTER ICI
// Le BFS explore tous les voisins d'un nœud avant de passer au niveau suivant
// Utilise une file (queue) FIFO
// Garantit le chemin le plus court en nombre de cases

async function bfs() {
    // TODO: Implémenter l'algorithme BFS
    // 1. Créer une file avec la position de départ
    // 2. Créer un Set pour les nœuds visités
    // 3. Créer une Map pour reconstruire le chemin (cameFrom)
    // 4. Tant que la file n'est pas vide:
    //    - Défiler le premier nœud
    //    - Si c'est l'arrivée, reconstruire et retourner le chemin
    //    - Pour chaque voisin non visité:
    //      * Marquer comme visité
    //      * Ajouter à la file
    //      * Enregistrer le chemin
    //      * Visualiser (maze[y][x] = CELL_TYPES.VISITED, drawMaze(), await sleep(20))
    // 5. Si la file est vide, retourner null (pas de chemin)
    
    const queue = [];
    const visited = new Set();
    const cameFrom = new Map();
    let visitedCount = 0;
    
    // VOTRE CODE ICI
    console.log("BFS - À implémenter!");
    
    // Exemple de base (à compléter):
    queue.push(startPos);
    visited.add(`${startPos.x},${startPos.y}`);
    
    while (queue.length > 0) {
        const current = queue.shift();
        
        // Si on a atteint la fin
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, current);
            return { path, visitedCount };
        }
        
        // Explorer les voisins (à compléter)
        // ...
    }
    
    return { path: null, visitedCount };
}

// ========================================
// ALGORITHME DFS (Depth-First Search)
// ========================================
// À IMPLÉMENTER ICI
// Le DFS explore en profondeur avant d'explorer en largeur
// Utilise une pile (stack) LIFO
// Ne garantit PAS le chemin le plus court

async function dfs() {
    // TODO: Implémenter l'algorithme DFS
    // Similaire au BFS mais utilise une pile au lieu d'une file
    // 1. Créer une pile avec la position de départ
    // 2. Créer un Set pour les nœuds visités
    // 3. Créer une Map pour reconstruire le chemin
    // 4. Tant que la pile n'est pas vide:
    //    - Dépiler le dernier nœud
    //    - Si c'est l'arrivée, reconstruire et retourner le chemin
    //    - Pour chaque voisin non visité:
    //      * Marquer comme visité
    //      * Ajouter à la pile
    //      * Enregistrer le chemin
    //      * Visualiser
    
    const stack = [];
    const visited = new Set();
    const cameFrom = new Map();
    let visitedCount = 0;
    
    // VOTRE CODE ICI
    console.log("DFS - À implémenter!");
    
    return { path: null, visitedCount };
}

// ========================================
// ALGORITHME A* (A-Star)
// ========================================
// À IMPLÉMENTER ICI
// A* utilise une heuristique pour estimer la distance à l'arrivée
// f(n) = g(n) + h(n)
// g(n) = coût du chemin depuis le départ
// h(n) = estimation heuristique jusqu'à l'arrivée (Manhattan ou Euclidienne)
// Garantit le chemin le plus court si l'heuristique est admissible

async function astar() {
    // TODO: Implémenter l'algorithme A*
    // 1. Créer une priority queue (ou array trié) avec la position de départ
    // 2. Créer des Maps pour g_score (coût depuis départ) et f_score (g + heuristique)
    // 3. Créer un Set pour les nœuds visités
    // 4. Créer une Map pour reconstruire le chemin
    // 5. Tant que la priority queue n'est pas vide:
    //    - Extraire le nœud avec le plus petit f_score
    //    - Si c'est l'arrivée, reconstruire et retourner le chemin
    //    - Pour chaque voisin:
    //      * Calculer tentative_g_score = g_score[current] + 1
    //      * Si tentative_g_score < g_score[voisin]:
    //        - Mettre à jour cameFrom, g_score et f_score
    //        - Ajouter le voisin à la queue si pas déjà dans
    //        - Visualiser
    
    const openSet = [];
    const closedSet = new Set();
    const cameFrom = new Map();
    const gScore = new Map();
    const fScore = new Map();
    let visitedCount = 0;
    
    // VOTRE CODE ICI
    console.log("A* - À implémenter!");
    
    // Utiliser manhattanDistance(x1, y1, x2, y2) comme heuristique
    
    return { path: null, visitedCount };
}

// ========================================
// ALGORITHME DIJKSTRA
// ========================================
// À IMPLÉMENTER ICI
// Dijkstra est similaire à A* mais sans heuristique
// Garantit le chemin le plus court
// Plus lent que A* car explore plus de nœuds

async function dijkstra() {
    // TODO: Implémenter l'algorithme de Dijkstra
    // Similaire à A* mais f_score = g_score seulement (pas d'heuristique)
    // 1. Créer une priority queue avec la position de départ
    // 2. Créer une Map pour distance (initialisée à Infinity sauf départ = 0)
    // 3. Créer un Set pour les nœuds visités
    // 4. Créer une Map pour reconstruire le chemin
    // 5. Tant que la priority queue n'est pas vide:
    //    - Extraire le nœud avec la plus petite distance
    //    - Si c'est l'arrivée, reconstruire et retourner le chemin
    //    - Pour chaque voisin non visité:
    //      * Calculer nouvelle_distance = distance[current] + 1
    //      * Si nouvelle_distance < distance[voisin]:
    //        - Mettre à jour distance et cameFrom
    //        - Ajouter à la queue
    //        - Visualiser
    
    const priorityQueue = [];
    const distance = new Map();
    const visited = new Set();
    const cameFrom = new Map();
    let visitedCount = 0;
    
    // VOTRE CODE ICI
    console.log("Dijkstra - À implémenter!");
    
    return { path: null, visitedCount };
}

// ========================================
// ALGORITHME GREEDY BEST-FIRST SEARCH
// ========================================
// À IMPLÉMENTER ICI
// Greedy utilise uniquement l'heuristique (sans coût g)
// Plus rapide mais ne garantit PAS le chemin optimal

async function greedyBestFirst() {
    // TODO: Implémenter l'algorithme Greedy Best-First
    // Similaire à A* mais utilise seulement h(n) (heuristique)
    // Ne prend pas en compte le coût du chemin parcouru
    // 1. Créer une priority queue avec la position de départ
    // 2. Trier par distance heuristique à l'arrivée
    // 3. Créer un Set pour les nœuds visités
    // 4. Créer une Map pour reconstruire le chemin
    // 5. Tant que la queue n'est pas vide:
    //    - Extraire le nœud le plus proche de l'arrivée (selon heuristique)
    //    - Si c'est l'arrivée, reconstruire et retourner le chemin
    //    - Pour chaque voisin non visité:
    //      * Calculer heuristique jusqu'à l'arrivée
    //      * Ajouter à la queue triée
    //      * Visualiser
    
    const priorityQueue = [];
    const visited = new Set();
    const cameFrom = new Map();
    let visitedCount = 0;
    
    // VOTRE CODE ICI
    console.log("Greedy Best-First - À implémenter!");
    
    // Utiliser manhattanDistance(x1, y1, x2, y2) comme heuristique
    
    return { path: null, visitedCount };
}

// ========================================
// INITIALISATION AU CHARGEMENT
// ========================================

window.addEventListener('DOMContentLoaded', () => {
    initMaze();
});
