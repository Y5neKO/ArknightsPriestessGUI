/**
 * Main Application Entry Point
 * Initializes all modules and starts the application
 */
const App = {
    /**
     * Initialize the application
     */
    init() {
        console.log('Initializing PRTS Terminal System...');

        // Initialize modules in order
        UIController.init();
        Router.init();

        // Start data polling
        Poller.start();

        // Bind global button actions
        this.bindGlobalActions();

        console.log('PRTS Terminal System initialized successfully');
    },

    /**
     * Bind global button actions
     */
    bindGlobalActions() {
        // Clear log button
        const clearLogBtn = document.querySelector('.log_setting_button');
        if (clearLogBtn) {
            clearLogBtn.addEventListener('click', () => {
                ApiService.clearLogs();
            });
        }

        // Main console test button
        const testConsoleBtn = document.querySelector('.current_task_box button');
        if (testConsoleBtn) {
            testConsoleBtn.addEventListener('click', () => {
                ApiService.testMainConsole();
            });
        }
    }
};

// Start application when DOM is ready
document.addEventListener('DOMContentLoaded', () => {
    App.init();
});
