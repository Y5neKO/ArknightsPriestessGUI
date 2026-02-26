/**
 * Legacy Script - UI Event Bindings
 * This file provides backward compatibility and binds UI events
 */

(function() {
    'use strict';

    // Wait for DOM to be ready
    document.addEventListener('DOMContentLoaded', function() {
        bindNavigationEvents();
        bindButtonEvents();
    });

    /**
     * Bind navigation link events
     */
    function bindNavigationEvents() {
        const navLinks = document.querySelectorAll('.nav-link a[data-page]');

        navLinks.forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                const pageId = this.getAttribute('data-page');
                if (pageId && typeof navigate === 'function') {
                    navigate(pageId);
                }
            });
        });
    }

    /**
     * Bind button events (for buttons not handled by UIController)
     */
    function bindButtonEvents() {
        // Clear log button
        const clearLogBtn = document.getElementById('btn-clear-log');
        if (clearLogBtn) {
            clearLogBtn.addEventListener('click', function() {
                if (typeof ApiService !== 'undefined') {
                    ApiService.clearLogs();
                }
            });
        }

        // Test console button
        const testConsoleBtn = document.getElementById('btn-test-console');
        if (testConsoleBtn) {
            testConsoleBtn.addEventListener('click', function() {
                if (typeof ApiService !== 'undefined') {
                    ApiService.testMainConsole();
                }
            });
        }

        // Auto scroll button - delegate to UIController if available
        const autoScrollBtn = document.getElementById('btn-auto-scroll');
        if (autoScrollBtn && typeof UIController !== 'undefined') {
            // Button is already handled by UIController, but ensure ID matches
            autoScrollBtn.classList.add('log-setting-button-2');
        }
    }
})();
