package com.arknights.priestess.service;

import com.arknights.priestess.config.AppConfig;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Objects;

/**
 * Service for managing the WebView component.
 * Handles page loading and JavaScript interactions.
 * Loads resources from the classpath (resources/web/).
 */
public class WebViewService {

    private final WebView webView;
    private final WebEngine webEngine;

    /**
     * Create a new WebViewService instance.
     */
    public WebViewService() {
        this.webView = new WebView();
        this.webEngine = webView.getEngine();

        initialize();
    }

    /**
     * Initialize the WebView service.
     */
    private void initialize() {
        setupErrorHandlers();
        loadIndexPage();
    }

    /**
     * Setup error and alert handlers for the WebEngine.
     */
    private void setupErrorHandlers() {
        webEngine.setOnError(event ->
                System.err.println("WebView Error: " + event.getMessage())
        );
        webEngine.setOnAlert(event ->
                System.out.println("WebView Alert: " + event.getData())
        );
    }

    /**
     * Load the index page from classpath resources.
     */
    public void loadIndexPage() {
        loadPageFromResources(AppConfig.INDEX_PAGE_PATH);
    }

    /**
     * Load a page from the classpath resources.
     *
     * @param resourcePath the path to the resource (e.g., "/web/index.html")
     */
    public void loadPageFromResources(String resourcePath) {
        URL resource = getClass().getResource(resourcePath);
        if (resource != null) {
            webEngine.load(resource.toExternalForm());
        } else {
            System.err.println("Resource not found: " + resourcePath);
        }
    }

    /**
     * Load a URL directly.
     *
     * @param url the URL to load
     */
    public void loadUrl(String url) {
        webEngine.load(url);
    }

    /**
     * Execute JavaScript code in the WebView.
     *
     * @param script the JavaScript code to execute
     * @return the result of the script execution
     */
    public Object executeScript(String script) {
        return webEngine.executeScript(script);
    }

    /**
     * Get the WebView component.
     *
     * @return the WebView instance
     */
    public WebView getWebView() {
        return webView;
    }

    /**
     * Get the WebEngine.
     *
     * @return the WebEngine instance
     */
    public WebEngine getWebEngine() {
        return webEngine;
    }

    /**
     * Reload the current page.
     */
    public void reload() {
        webEngine.reload();
    }

    /**
     * Get the current page URL.
     *
     * @return the current URL
     */
    public String getCurrentUrl() {
        return webEngine.getLocation();
    }
}
