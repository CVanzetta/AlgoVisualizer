// ========================================
// COMPARATEUR DE LABYRINTHES
// Architecture Double-Panel pour comparer algorithmes
// ========================================

// Constantes
const CELL_SIZE = 15;
const GRID_SIZE = 40;
const CANVAS_SIZE = GRID_SIZE * CELL_SIZE;

const CELL_TYPES = {
    EMPTY: 0,
    WALL: 1,
    START: 2,
    END: 3,
    VISITED: 4,
    PATH: 5,
    CURRENT: 6
};

const COLORS = {
    EMPTY: '#FFFFFF',
    WALL: '#333333',
    START: '#4CAF50',
    END: '#F44336',
    VISITED: '#64B5F6',
    PATH: '#FFD54F',
    CURRENT: '#FF9800'
};

// État global pour 2 labyrinthes
const state = {
    maze1: null,
    maze2: null,
    canvas1: null,
    canvas2: null,
    ctx1: null,
    ctx2: null,
    startPos1: { x: 5, y: 5 },
    endPos1: { x: 34, y: 34 },
    startPos2: { x: 5, y: 5 },
    endPos2: { x: 34, y: 34 },
    isRunning1: false,
    isRunning2: false,
    isDrawing1: false,
    isDrawing2: false
};

// ========================================
// INITIALISATION
// ========================================

window.addEventListener('DOMContentLoaded', () => {
    initMazes();
    setupEventListeners();
});

function initMazes() {
    // Canvas 1
    state.canvas1 = document.getElementById('mazeCanvas1');
    state.ctx1 = state.canvas1.getContext('2d');
    state.maze1 = createEmptyMaze();
    
    // Canvas 2
    state.canvas2 = document.getElementById('mazeCanvas2');
    state.ctx2 = state.canvas2.getContext('2d');
    state.maze2 = createEmptyMaze();
    
    // Dessiner les deux labyrinthes
    drawMaze(1);
    drawMaze(2);
}

function createEmptyMaze() {
    const maze = [];
    for (let y = 0; y < GRID_SIZE; y++) {
        maze[y] = [];
        for (let x = 0; x < GRID_SIZE; x++) {
            maze[y][x] = CELL_TYPES.EMPTY;
        }
    }
    return maze;
}

function setupEventListeners() {
    // Canvas 1 mouse events
    state.canvas1.addEventListener('mousedown', (e) => handleMouseDown(e, 1));
    state.canvas1.addEventListener('mousemove', (e) => handleMouseMove(e, 1));
    state.canvas1.addEventListener('mouseup', () => handleMouseUp(1));
    state.canvas1.addEventListener('mouseleave', () => handleMouseUp(1));
    
    // Canvas 2 mouse events
    state.canvas2.addEventListener('mousedown', (e) => handleMouseDown(e, 2));
    state.canvas2.addEventListener('mousemove', (e) => handleMouseMove(e, 2));
    state.canvas2.addEventListener('mouseup', () => handleMouseUp(2));
    state.canvas2.addEventListener('mouseleave', () => handleMouseUp(2));
}

// ========================================
// DESSIN DU LABYRINTHE
// ========================================

function drawMaze(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const ctx = panel === 1 ? state.ctx1 : state.ctx2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    // Effacer le canvas
    ctx.clearRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
    
    // Dessiner chaque cellule
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            const cellType = maze[y][x];
            
            // Couleur de base
            let color = COLORS.EMPTY;
            if (x === startPos.x && y === startPos.y) {
                color = COLORS.START;
            } else if (x === endPos.x && y === endPos.y) {
                color = COLORS.END;
            } else if (cellType === CELL_TYPES.WALL) {
                color = COLORS.WALL;
            } else if (cellType === CELL_TYPES.VISITED) {
                color = COLORS.VISITED;
            } else if (cellType === CELL_TYPES.PATH) {
                color = COLORS.PATH;
            } else if (cellType === CELL_TYPES.CURRENT) {
                color = COLORS.CURRENT;
            }
            
            // Dessiner la cellule
            ctx.fillStyle = color;
            ctx.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            
            // Grille
            ctx.strokeStyle = '#E0E0E0';
            ctx.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}

// ========================================
// GESTION SOURIS
// ========================================

function handleMouseDown(e, panel) {
    if (panel === 1) state.isDrawing1 = true;
    else state.isDrawing2 = true;
    
    handleMouseMove(e, panel);
}

function handleMouseMove(e, panel) {
    const isDrawing = panel === 1 ? state.isDrawing1 : state.isDrawing2;
    if (!isDrawing) return;
    
    const canvas = panel === 1 ? state.canvas1 : state.canvas2;
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const rect = canvas.getBoundingClientRect();
    
    const x = Math.floor((e.clientX - rect.left) / CELL_SIZE);
    const y = Math.floor((e.clientY - rect.top) / CELL_SIZE);
    
    if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
        const startPos = panel === 1 ? state.startPos1 : state.startPos2;
        const endPos = panel === 1 ? state.endPos1 : state.endPos2;
        
        // Ne pas dessiner sur départ/arrivée
        if ((x === startPos.x && y === startPos.y) || (x === endPos.x && y === endPos.y)) {
            return;
        }
        
        // Toggle mur
        if (maze[y][x] === CELL_TYPES.WALL) {
            maze[y][x] = CELL_TYPES.EMPTY;
        } else {
            maze[y][x] = CELL_TYPES.WALL;
        }
        
        drawMaze(panel);
    }
}

function handleMouseUp(panel) {
    if (panel === 1) state.isDrawing1 = false;
    else state.isDrawing2 = false;
}

// ========================================
// GÉNÉRATION DE LABYRINTHE
// ========================================

async function generateMaze(panel) {
    const generatorSelect = document.getElementById(`generator${panel}`);
    const algorithm = generatorSelect.value;
    
    try {
        const response = await fetch('/api/maze/generate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                algorithm: algorithm,
                width: GRID_SIZE,
                height: GRID_SIZE
            })
        });
        
        if (!response.ok) throw new Error('Erreur de génération');
        
        const data = await response.json();
        
        // Mettre à jour le labyrinthe
        if (panel === 1) {
            state.maze1 = data.maze;
        } else {
            state.maze2 = data.maze;
        }
        
        resetMaze(panel);
        drawMaze(panel);
        
        console.log(`Labyrinthe ${panel} généré avec ${algorithm} en ${data.executionTime}ms`);
    } catch (error) {
        console.error('Erreur:', error);
        // Fallback sur génération locale
        generateMazeLocal(panel);
    }
}

function generateMazeLocal(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    
    // Génération aléatoire simple (fallback)
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            maze[y][x] = Math.random() < 0.3 ? CELL_TYPES.WALL : CELL_TYPES.EMPTY;
        }
    }
    
    resetMaze(panel);
    drawMaze(panel);
}

// ========================================
// ACTIONS LABYRINTHE
// ========================================

function clearWalls(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            if (maze[y][x] === CELL_TYPES.WALL) {
                maze[y][x] = CELL_TYPES.EMPTY;
            }
        }
    }
    
    drawMaze(panel);
}

function resetMaze(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            if (maze[y][x] === CELL_TYPES.VISITED || 
                maze[y][x] === CELL_TYPES.PATH || 
                maze[y][x] === CELL_TYPES.CURRENT) {
                maze[y][x] = CELL_TYPES.EMPTY;
            }
        }
    }
    
    document.getElementById(`pathLength${panel}`).textContent = '-';
    document.getElementById(`visitedCount${panel}`).textContent = '0';
    document.getElementById(`executionTime${panel}`).textContent = '0ms';
    
    drawMaze(panel);
}

function resetBoth() {
    resetMaze(1);
    resetMaze(2);
}

function copyMaze1To2() {
    // Copier la structure du labyrinthe 1 vers 2
    state.maze2 = state.maze1.map(row => [...row]);
    state.startPos2 = { ...state.startPos1 };
    state.endPos2 = { ...state.endPos1 };
    
    document.getElementById('generator2').value = document.getElementById('generator1').value;
    
    drawMaze(2);
    console.log('Labyrinthe 1 dupliqué vers labyrinthe 2');
}

// ========================================
// RÉSOLUTION DE LABYRINTHE
// ========================================

async function solveMaze(panel) {
    const solverSelect = document.getElementById(`solver${panel}`);
    const algorithm = solverSelect.value;
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    if (panel === 1) state.isRunning1 = true;
    else state.isRunning2 = true;
    
    resetMaze(panel);
    
    const startTime = performance.now();
    let path = null;
    let visitedCount = 0;
    
    // Appeler l'algorithme approprié
    switch (algorithm) {
        case 'bfs':
            ({ path, visitedCount } = await bfs(panel));
            break;
        case 'dfs':
            ({ path, visitedCount } = await dfs(panel));
            break;
        case 'astar':
            ({ path, visitedCount } = await astar(panel));
            break;
        case 'dijkstra':
            ({ path, visitedCount } = await dijkstra(panel));
            break;
        case 'greedy':
            ({ path, visitedCount } = await greedyBestFirst(panel));
            break;
    }
    
    const endTime = performance.now();
    const executionTime = Math.round(endTime - startTime);
    
    if (path) {
        await visualizePath(panel, path);
        document.getElementById(`pathLength${panel}`).textContent = path.length;
    } else {
        document.getElementById(`pathLength${panel}`).textContent = 'Aucun';
    }
    
    document.getElementById(`visitedCount${panel}`).textContent = visitedCount;
    document.getElementById(`executionTime${panel}`).textContent = executionTime + 'ms';
    
    if (panel === 1) state.isRunning1 = false;
    else state.isRunning2 = false;
}

async function visualizePath(panel, path) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    
    for (const pos of path) {
        maze[pos.y][pos.x] = CELL_TYPES.PATH;
        drawMaze(panel);
        await sleep(10);
    }
}

// ========================================
// ALGORITHMES DE RÉSOLUTION
// À IMPLÉMENTER
// ========================================

async function bfs(panel) {
    // TODO: Implémenter BFS pour le panel spécifié
    // Copier la logique de l'ancien maze-visualizer-old.js
    console.log(`BFS - Panel ${panel} - À implémenter`);
    return { path: null, visitedCount: 0 };
}

async function dfs(panel) {
    // TODO: Implémenter DFS
    console.log(`DFS - Panel ${panel} - À implémenter`);
    return { path: null, visitedCount: 0 };
}

async function astar(panel) {
    // TODO: Implémenter A*
    console.log(`A* - Panel ${panel} - À implémenter`);
    return { path: null, visitedCount: 0 };
}

async function dijkstra(panel) {
    // TODO: Implémenter Dijkstra
    console.log(`Dijkstra - Panel ${panel} - À implémenter`);
    return { path: null, visitedCount: 0 };
}

async function greedyBestFirst(panel) {
    // TODO: Implémenter Greedy
    console.log(`Greedy - Panel ${panel} - À implémenter`);
    return { path: null, visitedCount: 0 };
}

// ========================================
// UTILITAIRES
// ========================================

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

function manhattanDistance(x1, y1, x2, y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
}

function getNeighbors(panel, x, y) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const neighbors = [];
    const directions = [[0, -1], [1, 0], [0, 1], [-1, 0]]; // haut, droite, bas, gauche
    
    for (const [dx, dy] of directions) {
        const nx = x + dx;
        const ny = y + dy;
        
        if (nx >= 0 && nx < GRID_SIZE && ny >= 0 && ny < GRID_SIZE && maze[ny][nx] !== CELL_TYPES.WALL) {
            neighbors.push({ x: nx, y: ny });
        }
    }
    
    return neighbors;
}

function reconstructPath(cameFrom, end) {
    const path = [];
    let current = `${end.x},${end.y}`;
    
    while (cameFrom.has(current)) {
        const [x, y] = current.split(',').map(Number);
        path.unshift({ x, y });
        current = cameFrom.get(current);
    }
    
    return path;
}
