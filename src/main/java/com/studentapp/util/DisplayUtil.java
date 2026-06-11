package com.studentapp.util;

/**
 * Kelas utilitas untuk mengelola tampilan warna dan dekorasi
 * pada antarmuka command-line
 */
public class DisplayUtil {
    // Kode warna ANSI untuk text
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    
    // Kode ANSI untuk style text
    public static final String BOLD = "\u001B[1m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    
    // Background colors
    public static final String BG_BLACK = "\u001B[40m";
    public static final String BG_RED = "\u001B[41m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String BG_YELLOW = "\u001B[43m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_PURPLE = "\u001B[45m";
    public static final String BG_CYAN = "\u001B[46m";
    public static final String BG_WHITE = "\u001B[47m";
    
    /**
     * Dekorasi untuk header (bisa digunakan sebagai banner)
     * @param title Judul yang akan ditampilkan
     * @param width Lebar banner
     * @return String berisi banner dengan dekorasi
     */
    public static String createHeader(String title, int width) {
        StringBuilder header = new StringBuilder();
        String border = "═".repeat(width);
        
        header.append(BOLD).append(BLUE);
        header.append("╔").append(border).append("╗\n");
        
        // Menambahkan judul di tengah
        int padding = (width - title.length()) / 2;
        String leftPad = " ".repeat(padding);
        String rightPad = " ".repeat(width - title.length() - padding);
        
        header.append("║").append(YELLOW).append(leftPad).append(title).append(rightPad).append(BLUE).append("║\n");
        header.append("╚").append(border).append("╝").append(RESET);
        
        return header.toString();
    }
    
    /**
     * Dekorasi untuk menu
     * @param items Array berisi item menu
     * @param width Lebar menu
     * @return String berisi menu dengan dekorasi
     */
    public static String createMenu(String[] items, int width) {
        StringBuilder menu = new StringBuilder();
        String border = "─".repeat(width - 2);
        
        menu.append(CYAN).append("┌").append(border).append("┐\n");
        
        for (int i = 0; i < items.length; i++) {
            menu.append("│ ").append(PURPLE).append(items[i]);
            int padding = width - items[i].length() - 3;
            menu.append(" ".repeat(padding)).append(CYAN).append("│\n");
        }
        
        menu.append("└").append(border).append("┘").append(RESET);
        
        return menu.toString();
    }
    
    /**
     * Membuat pesan sukses
     * @param message Pesan yang akan ditampilkan
     * @return String berisi pesan dengan format sukses
     */
    public static String successMessage(String message) {
        return GREEN + BOLD + "✓ " + message + RESET;
    }
    
    /**
     * Membuat pesan error
     * @param message Pesan yang akan ditampilkan
     * @return String berisi pesan dengan format error
     */
    public static String errorMessage(String message) {
        return RED + BOLD + "✗ " + message + RESET;
    }
    
    /**
     * Membuat pesan informasi
     * @param message Pesan yang akan ditampilkan
     * @return String berisi pesan dengan format informasi
     */
    public static String infoMessage(String message) {
        return BLUE + "ℹ " + message + RESET;
    }
    
    /**
     * Membuat pesan peringatan
     * @param message Pesan yang akan ditampilkan
     * @return String berisi pesan dengan format peringatan
     */
    public static String warningMessage(String message) {
        return YELLOW + "⚠ " + message + RESET;
    }
    
    /**
     * Membuat animasi loading
     * @param message Pesan yang akan ditampilkan
     * @param duration Durasi dalam milidetik
     */
    public static void showLoadingAnimation(String message, int duration) {
        String[] frames = {"|", "/", "-", "\\"};
        
        try {
            for (int i = 0; i < 8; i++) {
                System.out.print("\r" + CYAN + frames[i % 4] + " " + message + "..." + RESET);
                Thread.sleep(duration / 8);
            }
            System.out.println();
        } catch (InterruptedException e) {
            // Tangani jika terjadi interupsi
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Membersihkan layar konsol
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Membuat tabel untuk menampilkan data
     * @param headers Array header tabel
     * @param data Array data tabel (2D)
     * @param columnsWidth Array lebar kolom
     * @return String berisi tabel
     */
    public static String createTable(String[] headers, String[][] data, int[] columnsWidth) {
        StringBuilder table = new StringBuilder();
        
        // Header tabel
        table.append(createTableBorder("╔", "╦", "╗", columnsWidth));
        
        // Menambahkan header
        table.append("║");
        for (int i = 0; i < headers.length; i++) {
            String centered = centerText(headers[i], columnsWidth[i]);
            table.append(BOLD).append(WHITE).append(centered).append(RESET).append("║");
        }
        table.append("\n");
        
        // Batas antara header dan data
        table.append(createTableBorder("╠", "╬", "╣", columnsWidth));
        
        // Menambahkan data
        for (int i = 0; i < data.length; i++) {
            table.append("║");
            for (int j = 0; j < data[i].length; j++) {
                String padded = padText(data[i][j], columnsWidth[j]);
                table.append(padded).append("║");
            }
            table.append("\n");
            
            // Menambahkan garis pemisah setiap baris data
            if (i < data.length - 1) {
                table.append(createTableBorder("╠", "╬", "╣", columnsWidth));
            }
        }
        
        // Footer tabel
        table.append(createTableBorder("╚", "╩", "╝", columnsWidth));
        
        return table.toString();
    }
    
    /**
     * Membuat batas tabel
     * @param left Karakter sudut kiri
     * @param middle Karakter tengah
     * @param right Karakter sudut kanan
     * @param columnsWidth Array lebar kolom
     * @return String berisi batas tabel
     */
    private static String createTableBorder(String left, String middle, String right, int[] columnsWidth) {
        StringBuilder border = new StringBuilder();
        border.append(left);
        
        for (int i = 0; i < columnsWidth.length; i++) {
            border.append("═".repeat(columnsWidth[i]));
            if (i < columnsWidth.length - 1) {
                border.append(middle);
            }
        }
        
        border.append(right).append("\n");
        return border.toString();
    }
    
    /**
     * Membuat teks di tengah dengan padding
     * @param text Teks
     * @param width Lebar
     * @return String berisi teks di tengah
     */
    private static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int leftPad = (width - text.length()) / 2;
        int rightPad = width - text.length() - leftPad;
        
        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }
    
    /**
     * Menambahkan padding ke teks
     * @param text Teks
     * @param width Lebar
     * @return String berisi teks dengan padding
     */
    private static String padText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        return text + " ".repeat(width - text.length());
    }
}