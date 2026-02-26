package com.arknights.priestess.service;

import com.arknights.priestess.config.AppConfig;
import com.arknights.priestess.util.ResourceUtils;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Service for managing the system tray functionality.
 * Handles minimizing to tray and restoring the window.
 */
public class TrayService {

    private final Image appIcon;
    private TrayIcon trayIcon;
    private Stage stage;
    private Runnable onRestoreCallback;

    /**
     * Create a new TrayService instance.
     *
     * @param appIcon the application icon image
     */
    public TrayService(Image appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * Set the stage for this service.
     *
     * @param stage the JavaFX Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Set the callback to run when the window is restored from tray.
     *
     * @param callback the callback to run
     */
    public void setOnRestoreCallback(Runnable callback) {
        this.onRestoreCallback = callback;
    }

    /**
     * Minimize the window to the system tray.
     */
    public void minimizeToTray() {
        if (stage == null) {
            throw new IllegalStateException("Stage not set. Call setStage() first.");
        }

        Platform.setImplicitExit(false);
        stage.hide();

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(appIcon, null);
        trayIcon = createTrayIcon(bufferedImage);
        trayIcon.setImageAutoSize(true);

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("Failed to add tray icon: " + e.getMessage());
            }
        }
    }

    /**
     * Create a TrayIcon with the specified image.
     *
     * @param bufferedImage the image for the tray icon
     * @return the created TrayIcon
     */
    private TrayIcon createTrayIcon(BufferedImage bufferedImage) {
        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        popup.add(exitItem);

        TrayIcon icon = new TrayIcon(bufferedImage, AppConfig.APP_TITLE, popup);
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    restoreFromTray();
                }
            }
        });

        return icon;
    }

    /**
     * Restore the window from the system tray.
     */
    public void restoreFromTray() {
        if (trayIcon != null && SystemTray.isSupported()) {
            SystemTray.getSystemTray().remove(trayIcon);
            trayIcon = null;
        }

        if (stage != null) {
            Platform.runLater(() -> {
                stage.show();
                if (onRestoreCallback != null) {
                    onRestoreCallback.run();
                }
            });
        }
    }

    /**
     * Check if the application is currently minimized to tray.
     *
     * @return true if minimized to tray, false otherwise
     */
    public boolean isMinimizedToTray() {
        return trayIcon != null;
    }

    /**
     * Remove the tray icon if present.
     */
    public void cleanup() {
        if (trayIcon != null && SystemTray.isSupported()) {
            SystemTray.getSystemTray().remove(trayIcon);
            trayIcon = null;
        }
    }
}
