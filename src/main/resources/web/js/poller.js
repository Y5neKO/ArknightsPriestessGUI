/**
 * Poller Module
 * Handles periodic data fetching
 */
const Poller = {
    // Timer IDs
    timers: {
        log: null,
        screenshot: null,
        heartbeat: null
    },

    // Running state
    isRunning: false,

    /**
     * Start all pollers
     */
    start() {
        if (this.isRunning) return;
        this.isRunning = true;

        this.startLogPoller();
        this.startScreenshotPoller();
        this.startHeartbeat();
    },

    /**
     * Stop all pollers
     */
    stop() {
        this.isRunning = false;

        Object.values(this.timers).forEach(timerId => {
            if (timerId) {
                clearInterval(timerId);
            }
        });

        this.timers = { log: null, screenshot: null, heartbeat: null };
    },

    /**
     * Start log poller
     */
    startLogPoller() {
        const fetchLogs = async () => {
            const html = await ApiService.fetchLogs();
            if (html) {
                UIController.updateLogs(html);
            }
        };

        // Initial fetch
        fetchLogs();

        // Start polling
        this.timers.log = setInterval(fetchLogs, AppConfig.intervals.log);
    },

    /**
     * Start screenshot poller
     */
    startScreenshotPoller() {
        const fetchScreenshot = () => {
            const imageUrl = ApiService.getScreenshotUrl();
            UIController.updateScreenshot(imageUrl);
        };

        // Initial fetch
        fetchScreenshot();

        // Start polling
        this.timers.screenshot = setInterval(fetchScreenshot, AppConfig.intervals.screenshot);
    },

    /**
     * Start heartbeat
     */
    startHeartbeat() {
        const sendHeartbeat = async () => {
            const success = await ApiService.sendHeartbeat();
            if (!success) {
                console.warn('Heartbeat failed');
            }
        };

        // Initial send
        sendHeartbeat();

        // Start polling
        this.timers.heartbeat = setInterval(sendHeartbeat, AppConfig.intervals.heartbeat);
    }
};
