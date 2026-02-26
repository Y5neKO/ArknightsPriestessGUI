package com.arknights.priestess.util;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.File;
import java.util.Objects;

/**
 * Utility class for loading resources (images, stylesheets, etc.).
 */
public final class ResourceUtils {

    // Private constructor to prevent instantiation
    private ResourceUtils() {
    }

    /**
     * Load an image from the classpath resources.
     *
     * @param path the path to the image resource
     * @return the loaded Image object
     * @throws NullPointerException if the image cannot be found
     */
    public static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(
                ResourceUtils.class.getResourceAsStream(path),
                "Image not found: " + path
        ));
    }

    /**
     * Load an image from an external file path.
     *
     * @param file the file to load the image from
     * @return the loaded Image object
     * @throws IllegalArgumentException if the file does not exist
     */
    public static Image loadImageFromFile(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found: " + file.getAbsolutePath());
        }
        return new Image(file.toURI().toString());
    }

    /**
     * Get the URL string for a local file.
     *
     * @param file the file to get the URL for
     * @return the URL string representation of the file
     */
    public static String getFileUrl(File file) {
        return file.toURI().toString();
    }

    /**
     * Create a transparent background.
     *
     * @return a transparent Background object
     */
    public static Background createTransparentBackground() {
        return new Background(new BackgroundFill(
                Color.TRANSPARENT,
                CornerRadii.EMPTY,
                javafx.geometry.Insets.EMPTY
        ));
    }

    /**
     * Create a background with a specific color.
     *
     * @param color the color string (hex format, e.g., "#f4f4f4")
     * @return a Background object with the specified color
     */
    public static Background createBackground(String color) {
        return new Background(new BackgroundFill(
                Color.web(color),
                CornerRadii.EMPTY,
                javafx.geometry.Insets.EMPTY
        ));
    }

    /**
     * Create a background with a specific color and corner radius.
     *
     * @param color  the color string (hex format)
     * @param radius the corner radius
     * @return a Background object with the specified color and radius
     */
    public static Background createBackground(String color, double radius) {
        return new Background(new BackgroundFill(
                Color.web(color),
                new CornerRadii(radius),
                javafx.geometry.Insets.EMPTY
        ));
    }
}
