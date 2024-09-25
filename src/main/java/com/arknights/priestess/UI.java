package com.arknights.priestess;

import com.arknights.priestess.tools.Tools;
import com.sun.istack.internal.NotNull;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

public class UI extends Application {
    public static Image icon = new Image("img/icon.jpg");

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        // ====================设置一个网格视图作为模拟顶部栏的内容====================
        GridPane gridPaneToolBar = new GridPane();gridPaneToolBar.setPadding(new Insets(0, 0, 0, 0));gridPaneToolBar.setHgap(10);gridPaneToolBar.setVgap(10); // 行之间的垂直间距
        // 为GridPane添加三列，并设置它们的百分比宽度
        ColumnConstraints columnToolBar1 = new ColumnConstraints(Region.USE_COMPUTED_SIZE, 33.3, Double.MAX_VALUE);columnToolBar1.setHgrow(Priority.ALWAYS);columnToolBar1.setPercentWidth(33.3);
        ColumnConstraints columnToolBar2 = new ColumnConstraints(Region.USE_COMPUTED_SIZE, 33.4, Double.MAX_VALUE);columnToolBar2.setHgrow(Priority.ALWAYS);columnToolBar2.setPercentWidth(33.4);
        ColumnConstraints columnToolBar3 = new ColumnConstraints(Region.USE_COMPUTED_SIZE, 33.3, Double.MAX_VALUE);columnToolBar3.setHgrow(Priority.ALWAYS);columnToolBar3.setPercentWidth(33.3);
        gridPaneToolBar.getColumnConstraints().addAll(columnToolBar1, columnToolBar2, columnToolBar3);
        HBox.setHgrow(gridPaneToolBar, Priority.ALWAYS);
        // 设置第一个网格为标题栏
        HBox toolBox = new HBox();
        toolBox.setSpacing(2);
        toolBox.setPadding(new Insets(3, 0, 2, 5));
        gridPaneToolBar.add(toolBox, 0, 0, 1, 1);
        GridPane.setHalignment(toolBox, HPos.LEFT);
        // 设置第二个网格为标题
        Label titleLabel = new Label("Arknights Priestess");
        titleLabel.setFont(new Font("Consolas Bold", 20));
        gridPaneToolBar.add(titleLabel, 1, 0, 1, 1);
        GridPane.setHalignment(titleLabel, HPos.CENTER);
        GridPane.setValignment(titleLabel, VPos.CENTER);
        // 设置第三个网格为窗口操作按钮
        HBox buttonBox = new HBox();
        // 关闭按钮
        Button buttonClose = Tools.getImgButton("img/CloseButton.png");
        buttonClose.setOnAction(e -> {
            stage.close();
            System.exit(0);
        });
        // 最小化按钮
        Button buttonMin = Tools.getImgButton("img/MinButton.png");
        buttonMin.setOnAction(e -> stage.setIconified(true));
        // 最大化按钮
        Button buttonMax = Tools.getImgButton("img/MaxButton.png");
        buttonMax.setOnAction(e -> {
            e.consume();
            minimizeToTray(stage);
        });
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(buttonClose, buttonMax, buttonMin);
        gridPaneToolBar.add(buttonBox, 2, 0, 1, 1);GridPane.setHalignment(buttonBox, HPos.RIGHT);GridPane.setValignment(buttonBox, VPos.CENTER);

        // ====================创建一个顶部模拟状态栏====================
        HBox titleBar = new HBox();
        // 绑定拖拽事件
        titleBar.setOnMousePressed(this::handleMousePressed);
        titleBar.setOnMouseDragged(this::handleMouseDragged);

        // 添加一个网格视图
        titleBar.getChildren().add(gridPaneToolBar);
        titleBar.setAlignment(Pos.CENTER); // 居中布局
        titleBar.setPadding(new Insets(0, 0, 0, 0));
        titleBar.setSpacing(0);   // 设置标题栏内间距
        titleBar.setBackground(new Background(new BackgroundFill(Color.web("#f4f4f4"), null, null)));

        // ====================创建WebView====================
        // 创建 WebView 和 WebEngine
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        File file = new File("ui/index.html");
        String localUrl = file.toURI().toString();
        // 加载一个网页
        webEngine.load(localUrl);

        // ==================WebView交互处理================
//        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue == Worker.State.SUCCEEDED) {
//                JSObject window = (JSObject) webEngine.executeScript("window");
//                window.setMember("app", new Console());
//            }
//        });

        webEngine.setOnError(event -> System.out.println("Error: " + event.getMessage()));
        webEngine.setOnAlert(event -> System.out.println("Alert: " + event.getData()));


        // ================================================
        // 设置布局并将 WebView 添加到场景中
        BorderPane root = new BorderPane();
        root.setTop(titleBar);
        root.setCenter(webView);

        Rectangle clipRectangle = new Rectangle();
        clipRectangle.setArcWidth(20);
        clipRectangle.setArcHeight(20);
        clipRectangle.setWidth(1400);
        clipRectangle.setHeight(900);
        root.setClip(clipRectangle);

        Scene scene = new Scene(root, 1400, 900);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add("css/Style.css");

                                // 实时监听 Scene 宽度和高度的变化，并动态调整裁剪矩形的大小
                                scene.widthProperty().addListener((obs, oldVal, newVal) -> {
                                    clipRectangle.setWidth(newVal.doubleValue()); // 更新裁剪矩形的宽度
                                });

                                scene.heightProperty().addListener((obs, oldVal, newVal) -> {
                                    clipRectangle.setHeight(newVal.doubleValue()); // 更新裁剪矩形的高度
                                });

        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(icon);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Arknights Priestess");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("com.sun.webkit.WebInspector.enabled", "true");
        launch(args);
    }

    /**
     * 将JavaFX Stage最小化到系统托盘
     * @param primaryStage JavaFX Stage对象
     */
    private void minimizeToTray(Stage primaryStage) {
        Platform.setImplicitExit(false);
        // 隐藏JavaFX Stage
        primaryStage.hide();

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(icon, null);

        TrayIcon trayIcon = getTrayIcon(primaryStage, bufferedImage);
        trayIcon.setImageAutoSize(true);

        // 检查系统托盘是否可用并添加图标
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * 获取TrayIcon对象
     * @param primaryStage JavaFX Stage对象
     * @param bufferedImage BufferedImage对象
     * @return TrayIcon对象
     */
    private static @NotNull TrayIcon getTrayIcon(Stage primaryStage, BufferedImage bufferedImage) {
        PopupMenu popup = new PopupMenu();
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        popup.add(exitItem);

        // 创建TrayIcon并添加事件监听器
        TrayIcon trayIcon = new TrayIcon(bufferedImage, "RadiantKnightExploit", popup);
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // 双击托盘图标时恢复窗口
                    Platform.runLater(primaryStage::show);
                    SystemTray.getSystemTray().remove(trayIcon);
                }
            }
        });
        return trayIcon;
    }

    /**
     * 处理鼠标按下事件
     */
    private void handleMousePressed(MouseEvent event) {
        // 获取鼠标相对于窗口的坐标
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    /**
     * 处理鼠标拖动事件
     * @param event 鼠标事件
     */
    private void handleMouseDragged(MouseEvent event) {
        // 计算窗口的新位置（基于鼠标移动的距离）
        double newX = event.getScreenX() - xOffset;
        double newY = event.getScreenY() - yOffset;

        // 移动窗口到新位置
        Stage stage = (Stage) ((event.getSource() instanceof Node) ? ((Node) event.getSource()).getScene().getWindow() : null);
        if (stage != null) {
            stage.setX(newX);
            stage.setY(newY);
        }
    }
}
