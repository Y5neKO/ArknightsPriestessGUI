package com.arknights.priestess.config;

/**
 * Application configuration constants.
 * Centralizes all configurable values for the application.
 */
public final class AppConfig {

    // Private constructor to prevent instantiation
    private AppConfig() {
    }

    // ==================== Application Info ====================
    public static final String APP_TITLE = "Arknights Priestess";
    public static final String APP_VERSION = "1.0.0";

    // ==================== Window Settings ====================
    public static final int WINDOW_WIDTH = 1400;
    public static final int WINDOW_HEIGHT = 900;
    public static final int WINDOW_CORNER_RADIUS = 20;

    // ==================== API Settings ====================
    public static final String API_HOST = "127.0.0.1";
    public static final int API_PORT = 14513;
    public static final String API_BASE_URL = "http://" + API_HOST + ":" + API_PORT;

    // ==================== JavaFX Resource Paths ====================
    // Used for JavaFX UI components (window icons, title bar, etc.)
    public static final String RESOURCE_PATH = "/";
    public static final String IMAGE_PATH = RESOURCE_PATH + "img/";
    public static final String CSS_PATH = RESOURCE_PATH + "css/";

    // ==================== JavaFX Image Resources ====================
    public static final String ICON_PATH = IMAGE_PATH + "logo.png";
    public static final String CLOSE_BUTTON_PATH = IMAGE_PATH + "CloseButton.png";
    public static final String MIN_BUTTON_PATH = IMAGE_PATH + "MinButton.png";
    public static final String MAX_BUTTON_PATH = IMAGE_PATH + "MaxButton.png";

    // ==================== JavaFX Style Resources ====================
    public static final String MAIN_STYLE_PATH = CSS_PATH + "Style.css";

    // ==================== Web Frontend Resource Paths ====================
    // Used for WebView embedded web pages
    public static final String WEB_PATH = RESOURCE_PATH + "web/";
    public static final String WEB_CSS_PATH = WEB_PATH + "css/";
    public static final String WEB_JS_PATH = WEB_PATH + "js/";
    public static final String WEB_IMG_PATH = WEB_PATH + "img/";
    public static final String WEB_FONTS_PATH = WEB_PATH + "fonts/";

    // ==================== Web Page Resources ====================
    public static final String INDEX_PAGE_PATH = WEB_PATH + "index.html";

    // ==================== Font Settings ====================
    public static final String TITLE_FONT = "Consolas Bold";
    public static final int TITLE_FONT_SIZE = 20;

    // ==================== Title Bar Settings ====================
    public static final String TITLE_BAR_BG_COLOR = "#f4f4f4";
}
