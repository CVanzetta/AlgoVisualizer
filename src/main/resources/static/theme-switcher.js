// Theme Switcher Component
(function() {
    'use strict';

    // Injecter le HTML du theme switcher
    function injectThemeSwitcher() {
        const themeSwitcherHTML = `
        <div class="theme-switch-container">
            <label class="switch">
                <input id="input" type="checkbox" onchange="toggleTheme()" />
                <div class="slider round">
                    <div class="sun-moon">
                        <svg id="moon-dot-1" class="moon-dot" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="moon-dot-2" class="moon-dot" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="moon-dot-3" class="moon-dot" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="light-ray-1" class="light-ray" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="light-ray-2" class="light-ray" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="light-ray-3" class="light-ray" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-1" class="cloud-dark" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-2" class="cloud-dark" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-3" class="cloud-dark" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-4" class="cloud-light" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-5" class="cloud-light" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                        <svg id="cloud-6" class="cloud-light" viewBox="0 0 100 100">
                            <circle cx="50" cy="50" r="50"></circle>
                        </svg>
                    </div>
                    <div class="stars">
                        <svg id="star-1" class="star" viewBox="0 0 20 20">
                            <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z"></path>
                        </svg>
                        <svg id="star-2" class="star" viewBox="0 0 20 20">
                            <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z"></path>
                        </svg>
                        <svg id="star-3" class="star" viewBox="0 0 20 20">
                            <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z"></path>
                        </svg>
                        <svg id="star-4" class="star" viewBox="0 0 20 20">
                            <path d="M 0 10 C 10 10,10 10 ,0 10 C 10 10 , 10 10 , 10 20 C 10 10 , 10 10 , 20 10 C 10 10 , 10 10 , 10 0 C 10 10,10 10 ,0 10 Z"></path>
                        </svg>
                    </div>
                </div>
            </label>
        </div>`;

        // Insérer le theme switcher au début du body
        document.body.insertAdjacentHTML('afterbegin', themeSwitcherHTML);
    }

    // Fonction pour basculer le thème
    window.toggleTheme = function() {
        const body = document.body;
        const checkbox = document.getElementById('input');
        if (checkbox.checked) {
            body.classList.remove('light-mode');
            body.classList.add('dark-mode');
            localStorage.setItem('theme', 'dark');
        } else {
            body.classList.remove('dark-mode');
            body.classList.add('light-mode');
            localStorage.setItem('theme', 'light');
        }
    };

    // Charger le thème sauvegardé au démarrage
    function loadSavedTheme() {
        const savedTheme = localStorage.getItem('theme') || 'light';
        const checkbox = document.getElementById('input');
        if (savedTheme === 'dark') {
            checkbox.checked = true;
            document.body.classList.remove('light-mode');
            document.body.classList.add('dark-mode');
        }
    }

    // Initialisation au chargement du DOM
    document.addEventListener('DOMContentLoaded', function() {
        injectThemeSwitcher();
        // Petit délai pour s'assurer que le DOM est bien chargé
        setTimeout(loadSavedTheme, 0);
    });
})();
