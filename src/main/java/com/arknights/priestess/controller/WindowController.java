package com.arknights.priestess.controller;

import com.arknights.priestess.model.WindowState;
import com.arknights.priestess.service.TrayService;
import com.arknights.priestess.view.MainView;
import com.arknights.priestess.view.component.TitleBar;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Controller for window operations.
 * Handles window movement, minimize, maximize, and close actions.
 */
public class WindowController {

    private final Stage stage;
    private final MainView mainView;
    private final TrayService trayService;

    private WindowState windowState = WindowState.NORMAL;
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Create a new WindowController instance.
     *
     * @param stage       the JavaFX Stage
     * @param mainView    the main view
     * @param trayService the tray service
     */
    public WindowController(Stage stage, MainView mainView, TrayService trayService) {
        this.stage = stage;
        this.mainView = mainView;
        this.trayService = trayService;

        initialize();
    }

    /**
     * Initialize the controller and bind event handlers.
     */
    private void initialize() {
        TitleBar titleBar = mainView.getTitleBar();

        // Bind title bar button actions
        titleBar.setOnClose(this::handleClose);
        titleBar.setOnMaximize(this::handleMaximize);
        titleBar.setOnMinimize(this::handleMinimize);

        // Bind drag events
        titleBar.setOnMousePressedHandler(this::handleMousePressed);
        titleBar.setOnMouseDraggedHandler(this::handleMouseDragged);
    }

    /**
     * Handle the close button action.
     */
    private void handleClose() {
        trayService.cleanup();
        stage.close();
        System.exit(0);
    }

    /**
     * Handle the maximize/tray button action.
     * Minimizes to system tray instead of maximizing.
     */
    private void handleMaximize() {
        windowState = WindowState.MINIMIZED_TO_TRAY;
        trayService.minimizeToTray();
    }

    /**
     * Handle the minimize button action.
     */
    private void handleMinimize() {
        windowState = WindowState.NORMAL;
        stage.setIconified(true);
    }

    /**
     * Handle mouse pressed event for window dragging.
     *
     * @param event the mouse event
     */
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * Handle mouse dragged event for window movement.
     *
     * @param event the mouse event
     */
    private void handleMouseDragged(MouseEvent event) {
        double newX = event.getScreenX() - xOffset;
        double newY = event.getScreenY() - yOffset;

        stage.setX(newX);
        stage.setY(newY);
    }

    /**
     * Setup scene resize listener to update clip rectangle.
     *
     * @param scene the scene to monitor
     */
    public void setupResizeListener(Scene scene) {
        scene.widthProperty().addListener((obs, oldVal, newVal) ->
                mainView.updateClipSize(newVal.doubleValue(), scene.getHeight())
        );

        scene.heightProperty().addListener((obs, oldVal, newVal) ->
                mainView.updateClipSize(scene.getWidth(), newVal.doubleValue())
        );
    }

    /**
     * Get the current window state.
     *
     * @return the current WindowState
     */
    public WindowState getWindowState() {
        return windowState;
    }

    /**
     * Set the window state.
     *
     * @param state the new WindowState
     */
    public void setWindowState(WindowState state) {
        this.windowState = state;
    }
}
