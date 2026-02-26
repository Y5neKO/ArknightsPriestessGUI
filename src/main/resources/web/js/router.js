/**
 * Router Module
 * Handles single-page application navigation
 */
const Router = {
    // Current page
    currentPage: null,

    // Page elements cache
    pages: null,

    /**
     * Initialize router
     */
    init() {
        this.cachePages();
        this.bindEvents();
    },

    /**
     * Cache all page elements
     */
    cachePages() {
        this.pages = document.querySelectorAll('.page');
    },

    /**
     * Bind navigation events
     */
    bindEvents() {
        // Handle hash change
        window.addEventListener('hashchange', () => {
            const hash = window.location.hash.slice(1);
            if (hash) {
                this.navigate(hash);
            }
        });

        // Handle initial page load
        document.addEventListener('DOMContentLoaded', () => {
            const hash = window.location.hash.slice(1);
            if (hash) {
                this.navigate(hash);
            }
        });
    },

    /**
     * Navigate to a specific page
     * @param {string} pageId - The page ID to navigate to
     */
    navigate(pageId) {
        if (!this.pages) {
            this.cachePages();
        }

        // Hide all pages
        this.pages.forEach(page => {
            page.classList.remove('active');
        });

        // Show target page
        const targetPage = document.getElementById(pageId);
        if (targetPage) {
            targetPage.classList.add('active');
            this.currentPage = pageId;
        } else {
            console.warn(`Page not found: ${pageId}`);
        }
    },

    /**
     * Get current page ID
     * @returns {string|null} Current page ID
     */
    getCurrentPage() {
        return this.currentPage;
    }
};

/**
 * Global navigate function for onclick handlers
 * @param {string} pageId - The page ID to navigate to
 */
function navigate(pageId) {
    Router.navigate(pageId);
}
