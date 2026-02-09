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
    console.log('🚀 Initialisation des labyrinthes...');
    
    // Canvas 1
    state.canvas1 = document.getElementById('mazeCanvas1');
    if (!state.canvas1) {
        console.error('❌ Canvas 1 introuvable !');
        return;
    }
    state.ctx1 = state.canvas1.getContext('2d');
    state.maze1 = createEmptyMaze();
    console.log('✅ Canvas 1 initialisé');
    
    // Canvas 2
    state.canvas2 = document.getElementById('mazeCanvas2');
    if (!state.canvas2) {
        console.error('❌ Canvas 2 introuvable !');
        return;
    }
    state.ctx2 = state.canvas2.getContext('2d');
    state.maze2 = createEmptyMaze();
    console.log('✅ Canvas 2 initialisé');
    
    // Dessiner les deux labyrinthes
    drawMaze(1);
    drawMaze(2);
    console.log('✅ Labyrinthes dessinés');
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
    console.log('🎮 Configuration des event listeners...');
    
    if (!state.canvas1 || !state.canvas2) {
        console.error('❌ Canvas non initialisés, event listeners non configurés');
        return;
    }
    
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
    
    console.log('✅ Event listeners configurés');
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
            // S'assurer que départ et arrivée sont des passages
            state.maze1[state.startPos1.y][state.startPos1.x] = CELL_TYPES.EMPTY;
            state.maze1[state.endPos1.y][state.endPos1.x] = CELL_TYPES.EMPTY;
        } else {
            state.maze2 = data.maze;
            // S'assurer que départ et arrivée sont des passages
            state.maze2[state.startPos2.y][state.startPos2.x] = CELL_TYPES.EMPTY;
            state.maze2[state.endPos2.y][state.endPos2.x] = CELL_TYPES.EMPTY;
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
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    // Génération aléatoire simple (fallback)
    for (let y = 0; y < GRID_SIZE; y++) {
        for (let x = 0; x < GRID_SIZE; x++) {
            maze[y][x] = Math.random() < 0.3 ? CELL_TYPES.WALL : CELL_TYPES.EMPTY;
        }
    }
    
    // S'assurer que départ et arrivée sont des passages
    maze[startPos.y][startPos.x] = CELL_TYPES.EMPTY;
    maze[endPos.y][endPos.x] = CELL_TYPES.EMPTY;
    
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
// RÉSOLUTION DE LABYRINTHE
// ========================================

async function solveMaze(panel) {
    console.log(`🔍 Début résolution panel ${panel}`);
    
    try {
        const solverSelect = document.getElementById(`solver${panel}`);
        const algorithm = solverSelect.value;
        const maze = panel === 1 ? state.maze1 : state.maze2;
        const startPos = panel === 1 ? state.startPos1 : state.startPos2;
        const endPos = panel === 1 ? state.endPos1 : state.endPos2;
        
        console.log(`Algorithme: ${algorithm}, Départ: (${startPos.x},${startPos.y}), Arrivée: (${endPos.x},${endPos.y})`);
        
        if (panel === 1) state.isRunning1 = true;
        else state.isRunning2 = true;
        
        resetMaze(panel);
        
        const startTime = performance.now();
        let path = null;
        let visitedCount = 0;
        
        // Appeler l'algorithme approprié
        switch (algorithm) {
            case 'bfs':
                console.log('🔵 Lancement BFS');
                ({ path, visitedCount } = await bfs(panel));
                break;
            case 'dfs':
                console.log('🟣 Lancement DFS');
                ({ path, visitedCount } = await dfs(panel));
                break;
            case 'astar':
                console.log('⭐ Lancement A*');
                ({ path, visitedCount } = await astar(panel));
                break;
            case 'dijkstra':
                console.log('🔷 Lancement Dijkstra');
                ({ path, visitedCount } = await dijkstra(panel));
                break;
            case 'greedy':
                console.log('🟢 Lancement Greedy');
                ({ path, visitedCount } = await greedyBestFirst(panel));
                break;
            default:
                console.error(`❌ Algorithme inconnu: ${algorithm}`);
                return;
        }
        
        const endTime = performance.now();
        const executionTime = Math.round(endTime - startTime);
        
        console.log(`✅ Résolution terminée: ${path ? path.length : 'Aucun'} cellules, ${visitedCount} explorées, ${executionTime}ms`);
        
        if (path) {
            await visualizePath(panel, path);
            document.getElementById(`pathLength${panel}`).textContent = path.length;
        } else {
            document.getElementById(`pathLength${panel}`).textContent = 'Aucun';
            console.warn('⚠️ Aucun chemin trouvé !');
        }
        
        document.getElementById(`visitedCount${panel}`).textContent = visitedCount;
        document.getElementById(`executionTime${panel}`).textContent = executionTime + 'ms';
        
        if (panel === 1) state.isRunning1 = false;
        else state.isRunning2 = false;
        
    } catch (error) {
        console.error('❌ Erreur lors de la résolution:', error);
        if (panel === 1) state.isRunning1 = false;
        else state.isRunning2 = false;
    }
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
// ========================================

async function bfs(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    const queue = [startPos];
    const visited = new Set([`${startPos.x},${startPos.y}`]);
    const cameFrom = new Map();
    let visitedCount = 0;
    
    while (queue.length > 0) {
        const current = queue.shift();
        visitedCount++;
        
        // Marquer comme visité
        if (current.x !== startPos.x || current.y !== startPos.y) {
            maze[current.y][current.x] = CELL_TYPES.VISITED;
            drawMaze(panel);
            await sleep(5);
        }
        
        // Objectif atteint
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, endPos);
            return { path, visitedCount };
        }
        
        // Explorer les voisins
        for (const neighbor of getNeighbors(panel, current.x, current.y)) {
            const key = `${neighbor.x},${neighbor.y}`;
            if (!visited.has(key)) {
                visited.add(key);
                cameFrom.set(key, `${current.x},${current.y}`);
                queue.push(neighbor);
            }
        }
    }
    
    return { path: null, visitedCount };
}

async function dfs(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    const stack = [startPos];
    const visited = new Set([`${startPos.x},${startPos.y}`]);
    const cameFrom = new Map();
    let visitedCount = 0;
    
    while (stack.length > 0) {
        const current = stack.pop();
        visitedCount++;
        
        // Marquer comme visité
        if (current.x !== startPos.x || current.y !== startPos.y) {
            maze[current.y][current.x] = CELL_TYPES.VISITED;
            drawMaze(panel);
            await sleep(5);
        }
        
        // Objectif atteint
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, endPos);
            return { path, visitedCount };
        }
        
        // Explorer les voisins
        for (const neighbor of getNeighbors(panel, current.x, current.y)) {
            const key = `${neighbor.x},${neighbor.y}`;
            if (!visited.has(key)) {
                visited.add(key);
                cameFrom.set(key, `${current.x},${current.y}`);
                stack.push(neighbor);
            }
        }
    }
    
    return { path: null, visitedCount };
}

async function astar(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    const openSet = [{ ...startPos, f: 0, g: 0 }];
    const visited = new Set();
    const cameFrom = new Map();
    const gScore = new Map([[`${startPos.x},${startPos.y}`, 0]]);
    let visitedCount = 0;
    
    while (openSet.length > 0) {
        // Trouver le nœud avec le plus petit f
        openSet.sort((a, b) => a.f - b.f);
        const current = openSet.shift();
        const currentKey = `${current.x},${current.y}`;
        
        if (visited.has(currentKey)) continue;
        visited.add(currentKey);
        visitedCount++;
        
        // Marquer comme visité
        if (current.x !== startPos.x || current.y !== startPos.y) {
            maze[current.y][current.x] = CELL_TYPES.VISITED;
            drawMaze(panel);
            await sleep(5);
        }
        
        // Objectif atteint
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, endPos);
            return { path, visitedCount };
        }
        
        // Explorer les voisins
        for (const neighbor of getNeighbors(panel, current.x, current.y)) {
            const neighborKey = `${neighbor.x},${neighbor.y}`;
            if (visited.has(neighborKey)) continue;
            
            const tentativeG = current.g + 1;
            
            if (!gScore.has(neighborKey) || tentativeG < gScore.get(neighborKey)) {
                gScore.set(neighborKey, tentativeG);
                const h = manhattanDistance(neighbor.x, neighbor.y, endPos.x, endPos.y);
                const f = tentativeG + h;
                
                cameFrom.set(neighborKey, currentKey);
                openSet.push({ ...neighbor, f, g: tentativeG });
            }
        }
    }
    
    return { path: null, visitedCount };
}

async function dijkstra(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    const openSet = [{ ...startPos, dist: 0 }];
    const visited = new Set();
    const cameFrom = new Map();
    const distances = new Map([[`${startPos.x},${startPos.y}`, 0]]);
    let visitedCount = 0;
    
    while (openSet.length > 0) {
        // Trouver le nœud avec la plus petite distance
        openSet.sort((a, b) => a.dist - b.dist);
        const current = openSet.shift();
        const currentKey = `${current.x},${current.y}`;
        
        if (visited.has(currentKey)) continue;
        visited.add(currentKey);
        visitedCount++;
        
        // Marquer comme visité
        if (current.x !== startPos.x || current.y !== startPos.y) {
            maze[current.y][current.x] = CELL_TYPES.VISITED;
            drawMaze(panel);
            await sleep(5);
        }
        
        // Objectif atteint
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, endPos);
            return { path, visitedCount };
        }
        
        // Explorer les voisins
        for (const neighbor of getNeighbors(panel, current.x, current.y)) {
            const neighborKey = `${neighbor.x},${neighbor.y}`;
            if (visited.has(neighborKey)) continue;
            
            const tentativeDist = current.dist + 1;
            
            if (!distances.has(neighborKey) || tentativeDist < distances.get(neighborKey)) {
                distances.set(neighborKey, tentativeDist);
                cameFrom.set(neighborKey, currentKey);
                openSet.push({ ...neighbor, dist: tentativeDist });
            }
        }
    }
    
    return { path: null, visitedCount };
}

async function greedyBestFirst(panel) {
    const maze = panel === 1 ? state.maze1 : state.maze2;
    const startPos = panel === 1 ? state.startPos1 : state.startPos2;
    const endPos = panel === 1 ? state.endPos1 : state.endPos2;
    
    const openSet = [{ ...startPos, h: 0 }];
    const visited = new Set();
    const cameFrom = new Map();
    let visitedCount = 0;
    
    while (openSet.length > 0) {
        // Trouver le nœud avec la plus petite heuristique
        openSet.sort((a, b) => a.h - b.h);
        const current = openSet.shift();
        const currentKey = `${current.x},${current.y}`;
        
        if (visited.has(currentKey)) continue;
        visited.add(currentKey);
        visitedCount++;
        
        // Marquer comme visité
        if (current.x !== startPos.x || current.y !== startPos.y) {
            maze[current.y][current.x] = CELL_TYPES.VISITED;
            drawMaze(panel);
            await sleep(5);
        }
        
        // Objectif atteint
        if (current.x === endPos.x && current.y === endPos.y) {
            const path = reconstructPath(cameFrom, endPos);
            return { path, visitedCount };
        }
        
        // Explorer les voisins
        for (const neighbor of getNeighbors(panel, current.x, current.y)) {
            const neighborKey = `${neighbor.x},${neighbor.y}`;
            if (!visited.has(neighborKey)) {
                visited.add(neighborKey);
                const h = manhattanDistance(neighbor.x, neighbor.y, endPos.x, endPos.y);
                cameFrom.set(neighborKey, currentKey);
                openSet.push({ ...neighbor, h });
            }
        }
    }
    
    return { path: null, visitedCount };
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
