package com.studentapp;

import com.studentapp.service.MahasiswaService;
import com.studentapp.util.MenuUtil;
import com.studentapp.util.DisplayUtil;
import com.studentapp.util.InputUtil;

/**
 * Kelas utama untuk menjalankan aplikasi Pendaftaran Mahasiswa
 */
public class Main {
    public static void main(String[] args) {
        MahasiswaService mahasiswaService = new MahasiswaService();
        MenuUtil menuUtil = new MenuUtil(mahasiswaService);
        
        // Tampilkan animasi loading saat aplikasi dimulai
        DisplayUtil.clearScreen();
        DisplayUtil.showLoadingAnimation("Memulai aplikasi", 1000);
        
        // Tampilkan pesan selamat datang dengan dekorasi
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("APLIKASI PENDAFTARAN MAHASISWA", 60));
        System.out.println();
        System.out.println(DisplayUtil.infoMessage("Selamat datang di Sistem Pendaftaran Mahasiswa"));
        System.out.println(DisplayUtil.infoMessage("Versi 1.0 - Dikembangkan dengan ♥"));
        System.out.println();
        
        // Tanyakan kepada pengguna apakah ingin menambahkan data sampel
        boolean tambahSampel = InputUtil.bacaYaTidak(DisplayUtil.BOLD + "Apakah Anda ingin menambahkan data sampel untuk testing? " + DisplayUtil.RESET);
        
        if (tambahSampel) {
            DisplayUtil.showLoadingAnimation("Menambahkan data sampel", 1000);
            menuUtil.tambahDataSampel();
            System.out.println(DisplayUtil.successMessage("Data sampel berhasil ditambahkan!"));
            System.out.println(DisplayUtil.infoMessage("5 data mahasiswa telah ditambahkan sebagai contoh"));
            InputUtil.tunggutEnter("\nTekan ENTER untuk melanjutkan...");
        }
        
        // Jalankan menu utama
        menuUtil.tampilkanMenuUtama();
    }
}
