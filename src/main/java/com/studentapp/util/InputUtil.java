package com.studentapp.util;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Utilitas untuk menangani input dari user
 */
public class InputUtil {
    private static final Scanner scanner = new Scanner(System.in);
    
    // Regex patterns untuk validasi
    private static final Pattern NIM_PATTERN = Pattern.compile("^[A-Z0-9]{5,15}$");
    private static final Pattern NAMA_PATTERN = Pattern.compile("^[A-Za-z .'\\-]{2,50}$");
    private static final Pattern TELEPON_PATTERN = Pattern.compile("^[0-9+\\-]{6,15}$");
    
    /**
     * Membaca input string
     * @param prompt Pesan yang ditampilkan ke user
     * @return String yang diinput user
     */
    public static String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    /**
     * Membaca input string dengan validasi tidak boleh kosong
     * @param prompt Pesan yang ditampilkan ke user
     * @return String yang valid dan tidak kosong
     */
    public static String bacaStringWajib(String prompt) {
        String input = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.out.println(DisplayUtil.errorMessage("Input tidak boleh kosong!"));
            } else {
                valid = true;
            }
        }
        
        return input;
    }
    
    /**
     * Membaca input NIM dengan validasi format
     * @param prompt Pesan yang ditampilkan ke user
     * @return NIM yang valid
     */
    public static String bacaNIM(String prompt) {
        String nim = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            nim = scanner.nextLine().trim().toUpperCase();
            
            if (nim.isEmpty()) {
                System.out.println(DisplayUtil.errorMessage("NIM tidak boleh kosong!"));
            } else if (!NIM_PATTERN.matcher(nim).matches()) {
                System.out.println(DisplayUtil.errorMessage("Format NIM tidak valid! NIM harus 5-15 karakter (huruf kapital dan angka)."));
            } else {
                valid = true;
            }
        }
        
        return nim;
    }
    
    /**
     * Membaca input nama dengan validasi format
     * @param prompt Pesan yang ditampilkan ke user
     * @return Nama yang valid
     */
    public static String bacaNama(String prompt) {
        String nama = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            nama = scanner.nextLine().trim();
            
            if (nama.isEmpty()) {
                System.out.println(DisplayUtil.errorMessage("Nama tidak boleh kosong!"));
            } else if (!NAMA_PATTERN.matcher(nama).matches()) {
                System.out.println(DisplayUtil.errorMessage("Format nama tidak valid! Nama hanya boleh mengandung huruf, spasi, titik, dan tanda hubung."));
            } else {
                valid = true;
            }
        }
        
        return nama;
    }
    
    /**
     * Membaca input nomor telepon dengan validasi format
     * @param prompt Pesan yang ditampilkan ke user
     * @param wajib Apakah wajib diisi
     * @return Nomor telepon yang valid
     */
    public static String bacaTelepon(String prompt, boolean wajib) {
        String telepon = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            telepon = scanner.nextLine().trim();
            
            if (telepon.isEmpty()) {
                if (wajib) {
                    System.out.println(DisplayUtil.errorMessage("Nomor telepon tidak boleh kosong!"));
                } else {
                    return ""; // Jika tidak wajib dan kosong, return string kosong
                }
            } else if (!TELEPON_PATTERN.matcher(telepon).matches()) {
                System.out.println(DisplayUtil.errorMessage("Format nomor telepon tidak valid! Gunakan hanya angka, tanda plus (+), dan tanda hubung (-)."));
            } else {
                valid = true;
            }
        }
        
        return telepon;
    }
    
    /**
     * Membaca input jurusan dengan validasi
     * @param prompt Pesan yang ditampilkan ke user
     * @return Jurusan yang valid
     */
    public static String bacaJurusan(String prompt) {
        String jurusan = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            jurusan = scanner.nextLine().trim();
            
            if (jurusan.isEmpty()) {
                System.out.println(DisplayUtil.errorMessage("Jurusan tidak boleh kosong!"));
            } else if (jurusan.length() < 3) {
                System.out.println(DisplayUtil.errorMessage("Nama jurusan terlalu pendek!"));
            } else {
                valid = true;
            }
        }
        
        return jurusan;
    }
    
    /**
     * Membaca input alamat dengan validasi panjang
     * @param prompt Pesan yang ditampilkan ke user
     * @param wajib Apakah wajib diisi
     * @return Alamat yang valid
     */
    public static String bacaAlamat(String prompt, boolean wajib) {
        String alamat = "";
        boolean valid = false;
        
        while (!valid) {
            System.out.print(prompt);
            alamat = scanner.nextLine().trim();
            
            if (alamat.isEmpty()) {
                if (wajib) {
                    System.out.println(DisplayUtil.errorMessage("Alamat tidak boleh kosong!"));
                } else {
                    return ""; // Jika tidak wajib dan kosong, return string kosong
                }
            } else if (alamat.length() < 5) {
                System.out.println(DisplayUtil.errorMessage("Alamat terlalu pendek! Minimal 5 karakter."));
            } else {
                valid = true;
            }
        }
        
        return alamat;
    }
    
    /**
     * Membaca input integer dengan validasi
     * @param prompt Pesan yang ditampilkan ke user
     * @return Integer yang valid
     */
    public static int bacaInteger(String prompt) {
        int nilai = 0;
        boolean valid = false;
        
        while (!valid) {
            try {
                System.out.print(prompt);
                nilai = Integer.parseInt(scanner.nextLine());
                valid = true;
            } catch (NumberFormatException e) {
                System.out.println(DisplayUtil.errorMessage("Input harus berupa angka!"));
            }
        }
        
        return nilai;
    }
    
    /**
     * Membaca input integer dengan batasan nilai
     * @param prompt Pesan yang ditampilkan ke user
     * @param min Nilai minimum yang diperbolehkan
     * @param max Nilai maksimum yang diperbolehkan
     * @return Integer yang valid dan berada dalam rentang
     */
    public static int bacaIntegerRange(String prompt, int min, int max) {
        int nilai = 0;
        boolean valid = false;
        
        while (!valid) {
            nilai = bacaInteger(prompt);
            if (nilai >= min && nilai <= max) {
                valid = true;
            } else {
                System.out.println(DisplayUtil.errorMessage(String.format("Input harus antara %d dan %d!", min, max)));
            }
        }
        
        return nilai;
    }
    
    /**
     * Membaca input Yes/No
     * @param prompt Pesan yang ditampilkan ke user
     * @return true jika user memilih Y/y, false jika N/n
     */
    public static boolean bacaYaTidak(String prompt) {
        String input;
        while (true) {
            input = bacaString(prompt + " (Y/N): ");
            if (input.equalsIgnoreCase("Y")) {
                return true;
            } else if (input.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println(DisplayUtil.errorMessage("Silakan masukkan Y atau N!"));
            }
        }
    }
    
    /**
     * Menjeda eksekusi sampai user menekan Enter
     * @param prompt Pesan yang ditampilkan ke user
     */
    public static void tunggutEnter(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }
}
