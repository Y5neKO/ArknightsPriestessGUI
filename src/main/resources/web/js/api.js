/**
 * API Service Module
 * Handles all HTTP requests to the backend server
 */
const ApiService = {
    /**
     * Fetch log data from server
     * @returns {Promise<string>} Log content HTML
     */
    async fetchLogs() {
        try {
            const response = await fetch(AppConfig.getApiUrl('consoleLog'));
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            return await response.text();
        } catch (error) {
            console.error('Failed to fetch logs:', error);
            return null;
        }
    },

    /**
     * Fetch screenshot image
     * @returns {Promise<string>} Image URL with timestamp
     */
    getScreenshotUrl() {
        const timestamp = new Date().getTime();
        return `${AppConfig.getApiUrl('screenshot')}?timestamp=${timestamp}`;
    },

    /**
     * Send heartbeat to server
     * @returns {Promise<boolean>} Success status
     */
    async sendHeartbeat() {
        try {
            const response = await fetch(AppConfig.getApiUrl('heartbeat'));
            return response.ok;
        } catch (error) {
            console.error('Heartbeat failed:', error);
            return false;
        }
    },

    /**
     * Clear server logs
     * @returns {Promise<object>} Response data
     */
    async clearLogs() {
        return await this.get('clearLog');
    },

    /**
     * Test main console
     * @returns {Promise<object>} Response data
     */
    async testMainConsole() {
        return await this.get('mainConsole');
    },

    /**
     * Submit settings form
     * @param {FormData} formData - Form data to submit
     * @returns {Promise<object>} Response data
     */
    async submitSettings(formData) {
        try {
            const response = await fetch(AppConfig.getApiUrl('configSet'), {
                method: 'POST',
                body: formData
            });
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error('Failed to submit settings:', error);
            return null;
        }
    },

    /**
     * Generic GET request
     * @param {string} endpoint - Endpoint name
     * @returns {Promise<object>} Response data
     */
    async get(endpoint) {
        try {
            const response = await fetch(AppConfig.getApiUrl(endpoint));
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            return await response.json();
        } catch (error) {
            console.error(`GET ${endpoint} failed:`, error);
            return null;
        }
    }
};
