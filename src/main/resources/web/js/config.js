/**
 * Application Configuration
 * Centralized configuration for API endpoints and settings
 */
const AppConfig = {
    // API Server Configuration
    api: {
        host: '127.0.0.1',
        port: 14513,
        baseUrl: 'http://127.0.0.1:14513',
        endpoints: {
            consoleLog: '/api/console_log',
            mainConsole: '/api/main_console',
            clearLog: '/api/clear_log',
            screenshot: '/api/eyes-of-priestess',
            heartbeat: '/heartbeat',
            configSet: '/api/config_set'
        }
    },

    // Polling Intervals (in milliseconds)
    intervals: {
        log: 1000,
        screenshot: 2000,
        heartbeat: 5000
    },

    // UI Settings
    ui: {
        headerHeight: 60,
        logBoxHeight: 250,
        logSettingHeight: 62,
        marginHeight: 60
    },

    // Get full API URL
    getApiUrl(endpoint) {
        const path = this.api.endpoints[endpoint];
        if (!path) {
            console.error(`Unknown endpoint: ${endpoint}`);
            return null;
        }
        return this.api.baseUrl + path;
    }
};

// Freeze configuration to prevent modifications
Object.freeze(AppConfig);
Object.freeze(AppConfig.api);
Object.freeze(AppConfig.api.endpoints);
Object.freeze(AppConfig.intervals);
Object.freeze(AppConfig.ui);
