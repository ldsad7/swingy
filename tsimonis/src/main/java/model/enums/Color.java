package model.enums;

public enum Color {
    ENDC("\033[0m"),
    BOLD("\033[1m"),
    UNDERLINE("\033[4m"),
    RED("\033[91m"),
    GREEN("\033[92m"),
    YELLOW("\033[93m"),
    BLUE("\033[94m"),
    PINK("\033[95m"),
    CYAN("\033[96m");

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
