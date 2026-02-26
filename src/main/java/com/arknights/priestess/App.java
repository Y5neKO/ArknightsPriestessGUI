package com.arknights.priestess;

import com.arknights.priestess.config.AppConfig;
import com.arknights.priestess.controller.WindowController;
import com.arknights.priestess.service.TrayService;
import com.arknights.priestess.service.WebViewService;
import com.arknights.priestess.util.ResourceUtils;
import com.arknights.priestess.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Main application entry point.
 * Assembles the MVC components and starts the application.
 */
public class App extends Application {

    private static Image appIcon;

    // Services
    private WebViewService webViewService;
    private TrayService trayService;

    // View
    private MainView mainView;

    // Controller
    private WindowController windowController;

    /**
     * Main entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Enable WebView WebInspector for debugging
        System.setProperty("com.sun.webkit.WebInspector.enabled", "true");
        launch(args);
    }

    /**
     * Get the application icon.
     *
     * @return the application icon image
     */
    public static Image getAppIcon() {
        return appIcon;
    }

    @Override
    public void start(Stage stage) {
        // Load the application icon
        appIcon = ResourceUtils.loadImage(AppConfig.ICON_PATH);

        // Initialize services
        webViewService = new WebViewService();
        trayService = new TrayService(appIcon);
        trayService.setStage(stage);

        // Initialize view
        mainView = new MainView(webViewService);

        // Initialize controller
        windowController = new WindowController(stage, mainView, trayService);

        // Create and configure the scene
        Scene scene = createScene();
        windowController.setupResizeListener(scene);

        // Configure the stage
        configureStage(stage, scene);

        // Show the stage
        stage.show();
    }

    /**
     * Create the main scene.
     *
     * @return the configured Scene
     */
    private Scene createScene() {
        Scene scene = new Scene(
                mainView.getRoot(),
                AppConfig.WINDOW_WIDTH,
                AppConfig.WINDOW_HEIGHT
        );
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(AppConfig.MAIN_STYLE_PATH);
        return scene;
    }

    /**
     * Configure the stage settings.
     *
     * @param stage the stage to configure
     * @param scene the scene to set
     */
    private void configureStage(Stage stage, Scene scene) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(appIcon);
        stage.setTitle(AppConfig.APP_TITLE);
        stage.setScene(scene);
    }

    @Override
    public void stop() {
        // Cleanup resources
        if (trayService != null) {
            trayService.cleanup();
        }
    }
}
