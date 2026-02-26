package com.arknights.priestess.view.component;

import com.arknights.priestess.config.AppConfig;
import com.arknights.priestess.util.ResourceUtils;
import com.arknights.priestess.view.factory.UIFactory;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

/**
 * Title bar component for the application window.
 * Contains the application title and window control buttons (close, maximize, minimize).
 */
public class TitleBar extends HBox {

    private final Button closeButton;
    private final Button maxButton;
    private final Button minButton;

    // Event handlers
    private Consumer<MouseEvent> onMousePressedHandler;
    private Consumer<MouseEvent> onMouseDraggedHandler;

    /**
     * Create a new TitleBar instance.
     */
    public TitleBar() {
        this.closeButton = UIFactory.createImageButton(AppConfig.CLOSE_BUTTON_PATH);
        this.maxButton = UIFactory.createImageButton(AppConfig.MAX_BUTTON_PATH);
        this.minButton = UIFactory.createImageButton(AppConfig.MIN_BUTTON_PATH);

        initialize();
    }

    /**
     * Initialize the title bar components.
     */
    private void initialize() {
        // Create the grid pane for the title bar layout
        GridPane gridPane = createGridPane();

        // Add left tool box (empty for now, can be extended)
        HBox toolBox = UIFactory.createToolBox(2);
        gridPane.add(toolBox, 0, 0, 1, 1);
        GridPane.setHalignment(toolBox, HPos.LEFT);

        // Add title label in the center
        Label titleLabel = UIFactory.createTitleLabel(
                AppConfig.APP_TITLE,
                AppConfig.TITLE_FONT,
                AppConfig.TITLE_FONT_SIZE
        );
        gridPane.add(titleLabel, 1, 0, 1, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(titleLabel, VPos.CENTER);

        // Add button box on the right
        HBox buttonBox = UIFactory.createButtonBox(closeButton, maxButton, minButton);
        gridPane.add(buttonBox, 2, 0, 1, 1);
        GridPane.setHalignment(buttonBox, HPos.RIGHT);
        GridPane.setValignment(buttonBox, VPos.CENTER);

        // Configure the title bar
        this.getChildren().add(gridPane);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(0, 0, 0, 0));
        this.setSpacing(0);
        this.setBackground(ResourceUtils.createBackground(AppConfig.TITLE_BAR_BG_COLOR));

        // Setup drag events for window movement
        setupDragEvents();
    }

    /**
     * Create and configure the grid pane for the title bar layout.
     *
     * @return a configured GridPane
     */
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(0, 0, 0, 0));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Create three columns with equal width
        ColumnConstraints column1 = new ColumnConstraints(
                Region.USE_COMPUTED_SIZE, 33.3, Double.MAX_VALUE
        );
        column1.setHgrow(Priority.ALWAYS);
        column1.setPercentWidth(33.3);

        ColumnConstraints column2 = new ColumnConstraints(
                Region.USE_COMPUTED_SIZE, 33.4, Double.MAX_VALUE
        );
        column2.setHgrow(Priority.ALWAYS);
        column2.setPercentWidth(33.4);

        ColumnConstraints column3 = new ColumnConstraints(
                Region.USE_COMPUTED_SIZE, 33.3, Double.MAX_VALUE
        );
        column3.setHgrow(Priority.ALWAYS);
        column3.setPercentWidth(33.3);

        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        HBox.setHgrow(gridPane, Priority.ALWAYS);

        return gridPane;
    }

    /**
     * Setup drag events for window movement.
     */
    private void setupDragEvents() {
        this.setOnMousePressed(event -> {
            if (onMousePressedHandler != null) {
                onMousePressedHandler.accept(event);
            }
        });

        this.setOnMouseDragged(event -> {
            if (onMouseDraggedHandler != null) {
                onMouseDraggedHandler.accept(event);
            }
        });
    }

    /**
     * Set the close button click handler.
     *
     * @param handler the click handler
     */
    public void setOnClose(Runnable handler) {
        closeButton.setOnAction(e -> handler.run());
    }

    /**
     * Set the maximize button click handler.
     *
     * @param handler the click handler
     */
    public void setOnMaximize(Runnable handler) {
        maxButton.setOnAction(e -> {
            e.consume();
            handler.run();
        });
    }

    /**
     * Set the minimize button click handler.
     *
     * @param handler the click handler
     */
    public void setOnMinimize(Runnable handler) {
        minButton.setOnAction(e -> handler.run());
    }

    /**
     * Set the mouse pressed handler for window dragging.
     *
     * @param handler the mouse event handler
     */
    public void setOnMousePressedHandler(Consumer<MouseEvent> handler) {
        this.onMousePressedHandler = handler;
    }

    /**
     * Set the mouse dragged handler for window dragging.
     *
     * @param handler the mouse event handler
     */
    public void setOnMouseDraggedHandler(Consumer<MouseEvent> handler) {
        this.onMouseDraggedHandler = handler;
    }

    /**
     * Get the close button.
     *
     * @return the close button
     */
    public Button getCloseButton() {
        return closeButton;
    }

    /**
     * Get the maximize button.
     *
     * @return the maximize button
     */
    public Button getMaxButton() {
        return maxButton;
    }

    /**
     * Get the minimize button.
     *
     * @return the minimize button
     */
    public Button getMinButton() {
        return minButton;
    }
}
