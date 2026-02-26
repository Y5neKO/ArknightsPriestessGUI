/**
 * UI Controller Module
 * Handles all UI interactions and updates
 */
const UIController = {
    // DOM Elements cache
    elements: {
        body: null,
        sidebar: null,
        toggle: null,
        modeSwitch: null,
        modeText: null,
        logContainer: null,
        logSettingBox: null,
        taskBox: null,
        screenshotBox: null,
        autoScrollBtn: null,
        settingInBox: null,
        settingForm: null
    },

    // State
    state: {
        isDarkMode: false,
        isAutoScrollEnabled: true,
        previousLogData: ''
    },

    /**
     * Initialize UI controller
     */
    init() {
        this.cacheElements();
        this.bindEvents();
        this.updateLayout();
        window.addEventListener('resize', () => this.updateLayout());
    },

    /**
     * Cache DOM elements for better performance
     */
    cacheElements() {
        this.elements.body = document.querySelector('body');
        this.elements.sidebar = document.querySelector('nav.sidebar');
        this.elements.toggle = document.querySelector('.toggle');
        this.elements.modeSwitch = document.querySelector('.toggle-switch');
        this.elements.modeText = document.querySelector('.mode-text');
        this.elements.logContainer = document.getElementById('log_data_container');
        this.elements.logSettingBox = document.getElementById('log_setting_box');
        this.elements.taskBox = document.querySelector('.current-task-box');
        this.elements.screenshotBox = document.getElementById('screenshot');
        this.elements.autoScrollBtn = document.getElementById('btn-auto-scroll');
        this.elements.settingInBox = document.getElementById('setting_in_box');
        this.elements.settingForm = document.getElementById('settingForm');
    },

    /**
     * Bind event listeners
     */
    bindEvents() {
        // Sidebar toggle
        this.elements.toggle?.addEventListener('click', () => {
            this.elements.sidebar.classList.toggle('close');
        });

        // Dark mode toggle
        this.elements.modeSwitch?.addEventListener('click', () => {
            this.toggleDarkMode();
        });

        // Auto scroll toggle
        this.elements.autoScrollBtn?.addEventListener('click', () => {
            this.toggleAutoScroll();
        });

        // Settings form submission
        this.elements.settingForm?.addEventListener('submit', (e) => {
            e.preventDefault();
            this.handleSettingsSubmit(e);
        });
    },

    /**
     * Toggle dark mode
     */
    toggleDarkMode() {
        this.state.isDarkMode = !this.state.isDarkMode;

        const darkElements = [
            this.elements.body,
            this.elements.logContainer,
            this.elements.logSettingBox,
            this.elements.taskBox
        ];

        darkElements.forEach(el => el?.classList.toggle('dark'));

        this.elements.modeText.textContent = this.state.isDarkMode ? '白' : '夜';
    },

    /**
     * Toggle auto scroll
     */
    toggleAutoScroll() {
        this.state.isAutoScrollEnabled = !this.state.isAutoScrollEnabled;

        this.elements.autoScrollBtn.classList.toggle('off');

        if (this.state.isAutoScrollEnabled) {
            this.elements.autoScrollBtn.textContent = '自动滚动: 开';
        } else {
            this.elements.autoScrollBtn.textContent = '自动滚动: 关';
        }
    },

    /**
     * Update layout dimensions
     * Note: Main layout is now handled by CSS flexbox.
     * This method only handles settings page height.
     */
    updateLayout() {
        const { headerHeight, marginHeight } = AppConfig.ui;

        // Calculate settings box height (for settings page)
        const settingHeight = window.innerHeight - headerHeight - marginHeight;
        if (this.elements.settingInBox) {
            this.elements.settingInBox.style.height = `${settingHeight}px`;
        }
    },

    /**
     * Update log content
     * @param {string} html - Log HTML content
     */
    updateLogs(html) {
        if (!this.elements.logContainer || html === this.state.previousLogData) {
            return;
        }

        this.elements.logContainer.innerHTML = html;
        this.state.previousLogData = html;

        // Auto scroll if enabled
        if (this.state.isAutoScrollEnabled) {
            const lastElement = this.elements.logContainer.lastElementChild;
            lastElement?.scrollIntoView();
        }
    },

    /**
     * Update screenshot image
     * @param {string} imageUrl - Image URL
     */
    updateScreenshot(imageUrl) {
        if (!this.elements.screenshotBox) return;

        const img = new Image();
        img.src = imageUrl;
        img.onload = () => {
            this.elements.screenshotBox.style.backgroundImage = `url('${img.src}')`;
        };
        img.onerror = () => {
            console.error('Failed to load screenshot');
        };
    },

    /**
     * Handle settings form submission
     * @param {Event} event - Form submit event
     */
    async handleSettingsSubmit(event) {
        const formData = new FormData(event.target);
        const result = await ApiService.submitSettings(formData);
        if (result) {
            console.log('Settings saved:', result);
        }
    }
};
