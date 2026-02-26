package com.arknights.priestess.view.factory;

import com.arknights.priestess.util.ResourceUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.function.Consumer;

/**
 * Factory class for creating UI components.
 * Provides reusable methods for creating common UI elements.
 */
public final class UIFactory {

    // Default button image size
    private static final double DEFAULT_BUTTON_IMAGE_HEIGHT = 20;

    // Private constructor to prevent instantiation
    private UIFactory() {
    }

    /**
     * Create a button with an image icon.
     *
     * @param imagePath the path to the image resource
     * @return a Button with the image as its graphic
     */
    public static Button createImageButton(String imagePath) {
        return createImageButton(imagePath, DEFAULT_BUTTON_IMAGE_HEIGHT);
    }

    /**
     * Create a button with an image icon at a specified height.
     *
     * @param imagePath the path to the image resource
     * @param height    the height of the image
     * @return a Button with the image as its graphic
     */
    public static Button createImageButton(String imagePath, double height) {
        Button button = new Button();
        Image image = ResourceUtils.loadImage(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setBackground(ResourceUtils.createTransparentBackground());
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return button;
    }

    /**
     * Create a button with an image icon and click handler.
     *
     * @param imagePath     the path to the image resource
     * @param clickHandler  the click event handler
     * @return a Button with the image and click handler
     */
    public static Button createImageButton(String imagePath, Consumer<javafx.event.ActionEvent> clickHandler) {
        Button button = createImageButton(imagePath);
        button.setOnAction(clickHandler::accept);
        return button;
    }

    /**
     * Create a horizontal button container with default spacing and alignment.
     *
     * @param buttons the buttons to add to the container
     * @return an HBox containing the buttons
     */
    public static HBox createButtonBox(Button... buttons) {
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(buttons);
        return buttonBox;
    }

    /**
     * Create a horizontal button container with custom spacing.
     *
     * @param spacing the spacing between buttons
     * @param buttons the buttons to add to the container
     * @return an HBox containing the buttons
     */
    public static HBox createButtonBox(double spacing, Button... buttons) {
        HBox buttonBox = createButtonBox(buttons);
        buttonBox.setSpacing(spacing);
        return buttonBox;
    }

    /**
     * Create a title label with the application title.
     *
     * @param title     the title text
     * @param fontName  the font name
     * @param fontSize  the font size
     * @return a Label with the title styling
     */
    public static Label createTitleLabel(String title, String fontName, int fontSize) {
        Label label = new Label(title);
        label.setFont(new Font(fontName, fontSize));
        return label;
    }

    /**
     * Create a tool box container with default padding and spacing.
     *
     * @param spacing the spacing between elements
     * @return an HBox configured as a tool box
     */
    public static HBox createToolBox(double spacing) {
        HBox toolBox = new HBox();
        toolBox.setSpacing(spacing);
        toolBox.setPadding(new Insets(3, 0, 2, 5));
        return toolBox;
    }
}
