package com.arknights.priestess.view;

import com.arknights.priestess.config.AppConfig;
import com.arknights.priestess.view.component.TitleBar;
import com.arknights.priestess.service.WebViewService;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Main view of the application.
 * Assembles the title bar and WebView into a cohesive layout.
 */
public class MainView {

    private final BorderPane root;
    private final TitleBar titleBar;
    private final WebViewService webViewService;
    private final Rectangle clipRectangle;

    /**
     * Create a new MainView instance.
     *
     * @param webViewService the WebView service to use
     */
    public MainView(WebViewService webViewService) {
        this.root = new BorderPane();
        this.titleBar = new TitleBar();
        this.webViewService = webViewService;
        this.clipRectangle = new Rectangle();

        initialize();
    }

    /**
     * Initialize the main view components.
     */
    private void initialize() {
        // Setup the layout
        root.setTop(titleBar);
        root.setCenter(webViewService.getWebView());

        // Setup the clip rectangle for rounded corners
        setupClipRectangle();
        root.setClip(clipRectangle);
    }

    /**
     * Setup the clip rectangle for rounded corners.
     */
    private void setupClipRectangle() {
        clipRectangle.setArcWidth(AppConfig.WINDOW_CORNER_RADIUS);
        clipRectangle.setArcHeight(AppConfig.WINDOW_CORNER_RADIUS);
        clipRectangle.setWidth(AppConfig.WINDOW_WIDTH);
        clipRectangle.setHeight(AppConfig.WINDOW_HEIGHT);
    }

    /**
     * Update the clip rectangle size based on new dimensions.
     *
     * @param width  the new width
     * @param height the new height
     */
    public void updateClipSize(double width, double height) {
        clipRectangle.setWidth(width);
        clipRectangle.setHeight(height);
    }

    /**
     * Get the root node of the view.
     *
     * @return the root BorderPane
     */
    public Node getNode() {
        return root;
    }

    /**
     * Get the root BorderPane.
     *
     * @return the root BorderPane
     */
    public BorderPane getRoot() {
        return root;
    }

    /**
     * Get the title bar component.
     *
     * @return the TitleBar instance
     */
    public TitleBar getTitleBar() {
        return titleBar;
    }

    /**
     * Get the WebView service.
     *
     * @return the WebViewService instance
     */
    public WebViewService getWebViewService() {
        return webViewService;
    }

    /**
     * Get the clip rectangle.
     *
     * @return the clip Rectangle
     */
    public Rectangle getClipRectangle() {
        return clipRectangle;
    }
}
