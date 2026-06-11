package com.studentapp.util;

import com.studentapp.model.Mahasiswa;
import com.studentapp.service.MahasiswaService;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Kelas untuk menangani tampilan menu dan interaksi pengguna
 */
public class MenuUtil {
    private MahasiswaService mahasiswaService;
    private static final int MENU_WIDTH = 60; // Lebar menu untuk tampilan konsisten
    private static final Scanner scanner = new Scanner(System.in);
    
    // Regex patterns untuk validasi pada edit data
    private static final Pattern NAMA_PATTERN = Pattern.compile("^[A-Za-z .'\\-]{2,50}$");
    private static final Pattern TELEPON_PATTERN = Pattern.compile("^[0-9+\\-]{6,15}$");
    
    // Constructor
    public MenuUtil(MahasiswaService mahasiswaService) {
        this.mahasiswaService = mahasiswaService;
    }
    
    /**
     * Menampilkan menu utama aplikasi
     */
    public void tampilkanMenuUtama() {
        boolean lanjut = true;
        
        while (lanjut) {
            DisplayUtil.clearScreen();
            
            // Tampilkan header
            System.out.println(DisplayUtil.createHeader("APLIKASI PENDAFTARAN MAHASISWA", MENU_WIDTH));
            System.out.println();
            
            // Tampilkan menu dengan format menarik
            String[] menuItems = {
                DisplayUtil.BOLD + "1. " + DisplayUtil.RESET + DisplayUtil.YELLOW + "➕ Tambah Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "2. " + DisplayUtil.RESET + DisplayUtil.CYAN + "📋 Lihat Daftar Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "3. " + DisplayUtil.RESET + DisplayUtil.BLUE + "🔍 Cari Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "4. " + DisplayUtil.RESET + DisplayUtil.GREEN + "📝 Perbarui Data Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "5. " + DisplayUtil.RESET + DisplayUtil.RED + "❌ Hapus Data Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "6. " + DisplayUtil.RESET + DisplayUtil.PURPLE + "🔄 Urutkan Data Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "7. " + DisplayUtil.RESET + DisplayUtil.YELLOW + "📊 Statistik Mahasiswa" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "0. " + DisplayUtil.RESET + "🚪 Keluar"
            };
            System.out.println(DisplayUtil.createMenu(menuItems, MENU_WIDTH));
            System.out.println();
            
            int pilihan = InputUtil.bacaIntegerRange(DisplayUtil.BOLD + "Pilih menu (0-7): " + DisplayUtil.RESET, 0, 7);
            
            switch (pilihan) {
                case 0:
                    lanjut = false;
                    DisplayUtil.clearScreen();
                    System.out.println(DisplayUtil.createHeader("TERIMA KASIH", MENU_WIDTH));
                    System.out.println("\n" + DisplayUtil.successMessage("Terima kasih telah menggunakan Aplikasi Pendaftaran Mahasiswa!"));
                    System.out.println(DisplayUtil.infoMessage("Sampai jumpa kembali! 👋"));
                    System.out.println();
                    break;
                case 1:
                    // Menampilkan animasi sederhana saat beralih menu
                    DisplayUtil.showLoadingAnimation("Membuka menu Tambah Mahasiswa", 500);
                    menuTambahMahasiswa();
                    break;
                case 2:
                    DisplayUtil.showLoadingAnimation("Memuat daftar mahasiswa", 500);
                    menuLihatDaftarMahasiswa();
                    break;
                case 3:
                    DisplayUtil.showLoadingAnimation("Membuka menu Pencarian", 500);
                    menuCariMahasiswa();
                    break;
                case 4:
                    DisplayUtil.showLoadingAnimation("Membuka menu Perbarui Data", 500);
                    menuPerbaruiMahasiswa();
                    break;
                case 5:
                    DisplayUtil.showLoadingAnimation("Membuka menu Hapus Data", 500);
                    menuHapusMahasiswa();
                    break;
                case 6:
                    DisplayUtil.showLoadingAnimation("Membuka menu Pengurutan", 500);
                    menuUrutkanMahasiswa();
                    break;
                case 7:
                    DisplayUtil.showLoadingAnimation("Membuka menu Statistik", 500);
                    menuStatistikMahasiswa();
                    break;
            }
        }
    }
    
    /**
     * Menu untuk menambahkan mahasiswa baru
     */
    private void menuTambahMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("TAMBAH DATA MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        // Tampilkan informasi formulir
        System.out.println(DisplayUtil.infoMessage("Silahkan isi data mahasiswa baru"));
        System.out.println(DisplayUtil.infoMessage("Isian dengan tanda (*) wajib diisi"));
        System.out.println();
        
        // Gunakan metode validasi yang baru untuk memastikan input yang valid
        String nim = InputUtil.bacaNIM(DisplayUtil.CYAN + DisplayUtil.BOLD + "Masukkan NIM (*): " + DisplayUtil.RESET);
        
        // Cek apakah NIM sudah terdaftar
        if (mahasiswaService.getMahasiswaByNim(nim) != null) {
            System.out.println("\n" + DisplayUtil.errorMessage("NIM '" + nim + "' sudah terdaftar!"));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        // Validasi input nama
        String nama = InputUtil.bacaNama(DisplayUtil.CYAN + DisplayUtil.BOLD + "Masukkan Nama (*): " + DisplayUtil.RESET);
        
        // Validasi input jurusan
        String jurusan = InputUtil.bacaJurusan(DisplayUtil.CYAN + DisplayUtil.BOLD + "Masukkan Jurusan (*): " + DisplayUtil.RESET);
        
        // Validasi input angkatan (menggunakan metode yang sudah ada)
        int angkatan = InputUtil.bacaIntegerRange(DisplayUtil.CYAN + DisplayUtil.BOLD + "Masukkan Angkatan (1980-2023) (*): " + DisplayUtil.RESET, 1980, 2023);
        
        // Alamat tidak wajib, tapi jika diisi, harus valid
        System.out.println(DisplayUtil.infoMessage("Alamat dapat dikosongkan (opsional)"));
        String alamat = InputUtil.bacaAlamat(DisplayUtil.CYAN + "Masukkan Alamat: " + DisplayUtil.RESET, false);
        
        // Nomor telepon tidak wajib, tapi jika diisi, harus valid
        System.out.println(DisplayUtil.infoMessage("Nomor telepon dapat dikosongkan (opsional)"));
        String noTelepon = InputUtil.bacaTelepon(DisplayUtil.CYAN + "Masukkan Nomor Telepon: " + DisplayUtil.RESET, false);
        
        // Tampilkan preview data sebelum disimpan
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("KONFIRMASI DATA", MENU_WIDTH));
        System.out.println();
        System.out.println(DisplayUtil.infoMessage("Periksa kembali data yang akan disimpan:"));
        System.out.println();
        
        // Tampilkan data dalam bentuk tabel untuk konfirmasi
        String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
        int[] columnWidths = {10, 20, 15, 8, 20, 15};
        
        String[][] data = new String[1][6];
        data[0][0] = nim;
        data[0][1] = nama;
        data[0][2] = jurusan;
        data[0][3] = String.valueOf(angkatan);
        data[0][4] = alamat.isEmpty() ? "-" : alamat;
        data[0][5] = noTelepon.isEmpty() ? "-" : noTelepon;
        
        System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
        System.out.println();
        
        // Konfirmasi penyimpanan data
        boolean konfirmasi = InputUtil.bacaYaTidak(DisplayUtil.BOLD + "Apakah data sudah benar dan siap disimpan?" + DisplayUtil.RESET);
        
        if (!konfirmasi) {
            System.out.println("\n" + DisplayUtil.infoMessage("Penyimpanan data dibatalkan."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        // Tampilkan animasi proses
        DisplayUtil.showLoadingAnimation("Menyimpan data mahasiswa", 800);
        
        // Buat objek mahasiswa baru
        Mahasiswa mahasiswaBaru = new Mahasiswa(nim, nama, jurusan, angkatan, alamat, noTelepon);
        
        // Tambahkan ke daftar
        boolean berhasil = mahasiswaService.tambahMahasiswa(mahasiswaBaru);
        
        if (berhasil) {
            System.out.println("\n" + DisplayUtil.successMessage("Data mahasiswa berhasil ditambahkan!"));
            
            // Tampilkan ringkasan data yang ditambahkan
            System.out.println("\n" + DisplayUtil.BOLD + "Ringkasan Data Mahasiswa:" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.BLUE + "NIM       : " + DisplayUtil.RESET + nim);
            System.out.println(DisplayUtil.BLUE + "Nama      : " + DisplayUtil.RESET + nama);
            System.out.println(DisplayUtil.BLUE + "Jurusan   : " + DisplayUtil.RESET + jurusan);
            System.out.println(DisplayUtil.BLUE + "Angkatan  : " + DisplayUtil.RESET + angkatan);
            if (!alamat.isEmpty()) {
                System.out.println(DisplayUtil.BLUE + "Alamat    : " + DisplayUtil.RESET + alamat);
            }
            if (!noTelepon.isEmpty()) {
                System.out.println(DisplayUtil.BLUE + "No Telepon: " + DisplayUtil.RESET + noTelepon);
            }
        } else {
            System.out.println("\n" + DisplayUtil.errorMessage("Gagal menambahkan data mahasiswa!"));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
    }
    
    /**
     * Menu untuk menampilkan daftar mahasiswa
     */
    private void menuLihatDaftarMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("DAFTAR MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        List<Mahasiswa> daftarMahasiswa = mahasiswaService.getAllMahasiswa();
        
        if (daftarMahasiswa.isEmpty()) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
        } else {
            // Konversi data mahasiswa ke format tabel yang lebih menarik
            String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
            int[] columnWidths = {10, 20, 15, 8, 20, 15};
            
            String[][] data = new String[daftarMahasiswa.size()][6];
            for (int i = 0; i < daftarMahasiswa.size(); i++) {
                Mahasiswa m = daftarMahasiswa.get(i);
                data[i][0] = m.getNim();
                data[i][1] = m.getNama();
                data[i][2] = m.getJurusan();
                data[i][3] = String.valueOf(m.getAngkatan());
                data[i][4] = m.getAlamat();
                data[i][5] = m.getNoTelepon();
            }
            
            // Tampilkan tabel dengan format yang lebih menarik
            System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
            System.out.println();
            System.out.println(DisplayUtil.infoMessage("Total Mahasiswa: " + daftarMahasiswa.size()));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
    }
    
    /**
     * Menu untuk mencari mahasiswa
     */
    private void menuCariMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("PENCARIAN MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        System.out.println(DisplayUtil.infoMessage("Anda dapat mencari mahasiswa berdasarkan NIM, Nama, atau Jurusan"));
        System.out.println();
        
        // Validasi keyword pencarian, harus minimal 2 karakter
        String keyword = "";
        boolean validKeyword = false;
        
        while (!validKeyword) {
            keyword = InputUtil.bacaString(DisplayUtil.CYAN + DisplayUtil.BOLD + "Masukkan kata kunci pencarian: " + DisplayUtil.RESET);
            
            if (keyword.trim().isEmpty()) {
                System.out.println(DisplayUtil.errorMessage("Kata kunci pencarian tidak boleh kosong!"));
            } else if (keyword.trim().length() < 2) {
                System.out.println(DisplayUtil.errorMessage("Kata kunci pencarian minimal 2 karakter!"));
            } else {
                validKeyword = true;
            }
        }
        
        DisplayUtil.showLoadingAnimation("Mencari data mahasiswa", 800);
        
        List<Mahasiswa> hasilPencarian = mahasiswaService.cariMahasiswa(keyword);
        
        if (hasilPencarian.isEmpty()) {
            System.out.println("\n" + DisplayUtil.warningMessage("Tidak ditemukan mahasiswa dengan kata kunci '" + keyword + "'"));
            System.out.println(DisplayUtil.infoMessage("Pastikan kata kunci yang dimasukkan benar dan coba lagi."));
        } else {
            // Konversi hasil pencarian ke format tabel
            String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
            int[] columnWidths = {10, 20, 15, 8, 20, 15};
            
            String[][] data = new String[hasilPencarian.size()][6];
            for (int i = 0; i < hasilPencarian.size(); i++) {
                Mahasiswa m = hasilPencarian.get(i);
                data[i][0] = m.getNim();
                data[i][1] = m.getNama();
                data[i][2] = m.getJurusan();
                data[i][3] = String.valueOf(m.getAngkatan());
                data[i][4] = m.getAlamat();
                data[i][5] = m.getNoTelepon();
            }
            
            System.out.println("\n" + DisplayUtil.successMessage("Ditemukan " + hasilPencarian.size() + 
                " mahasiswa dengan kata kunci '" + keyword + "'"));
            System.out.println();
            
            // Tampilkan hasil pencarian dalam bentuk tabel
            System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
    }
    
    /**
     * Menu untuk memperbarui data mahasiswa
     */
    private void menuPerbaruiMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("PERBARUI DATA MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        System.out.println(DisplayUtil.infoMessage("Masukkan NIM mahasiswa yang ingin diperbarui datanya"));
        String nim = InputUtil.bacaString(DisplayUtil.CYAN + DisplayUtil.BOLD + "NIM: " + DisplayUtil.RESET);
        
        DisplayUtil.showLoadingAnimation("Mencari data mahasiswa", 500);
        
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswaByNim(nim);
        
        if (mahasiswa == null) {
            System.out.println("\n" + DisplayUtil.errorMessage("Mahasiswa dengan NIM '" + nim + "' tidak ditemukan!"));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        System.out.println("\n" + DisplayUtil.BOLD + DisplayUtil.CYAN + "DATA MAHASISWA SAAT INI:" + DisplayUtil.RESET);
        
        // Tampilkan data mahasiswa saat ini dengan format yang lebih baik
        String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
        int[] columnWidths = {10, 20, 15, 8, 20, 15};
        
        String[][] data = new String[1][6];
        data[0][0] = mahasiswa.getNim();
        data[0][1] = mahasiswa.getNama();
        data[0][2] = mahasiswa.getJurusan();
        data[0][3] = String.valueOf(mahasiswa.getAngkatan());
        data[0][4] = mahasiswa.getAlamat();
        data[0][5] = mahasiswa.getNoTelepon();
        
        System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
        
        System.out.println("\n" + DisplayUtil.infoMessage("Masukkan data baru (biarkan kosong jika tidak ada perubahan):"));
        System.out.println();
        
        // Untuk update, kita perlu pendekatan khusus di mana nilai kosong berarti tidak ada perubahan
        System.out.print(DisplayUtil.CYAN + "Nama (" + mahasiswa.getNama() + "): " + DisplayUtil.RESET);
        String namaBaru = scanner.nextLine().trim();
        
        if (!namaBaru.isEmpty()) {
            // Validasi nama jika ada perubahan
            try {
                if (!NAMA_PATTERN.matcher(namaBaru).matches()) {
                    System.out.println(DisplayUtil.errorMessage("Format nama tidak valid! Menggunakan nilai lama."));
                    namaBaru = mahasiswa.getNama();
                }
            } catch (Exception e) {
                System.out.println(DisplayUtil.errorMessage("Terjadi kesalahan validasi. Menggunakan nilai lama."));
                namaBaru = mahasiswa.getNama();
            }
        } else {
            namaBaru = mahasiswa.getNama();
        }
        
        // Validasi jurusan jika diubah
        System.out.print(DisplayUtil.CYAN + "Jurusan (" + mahasiswa.getJurusan() + "): " + DisplayUtil.RESET);
        String jurusanBaru = scanner.nextLine().trim();
        
        if (!jurusanBaru.isEmpty()) {
            if (jurusanBaru.length() < 3) {
                System.out.println(DisplayUtil.errorMessage("Nama jurusan terlalu pendek! Menggunakan nilai lama."));
                jurusanBaru = mahasiswa.getJurusan();
            }
        } else {
            jurusanBaru = mahasiswa.getJurusan();
        }
        
        // Validasi angkatan dengan metode yang lebih robust
        String angkatanInput = InputUtil.bacaString(DisplayUtil.CYAN + "Angkatan (" + mahasiswa.getAngkatan() + "): " + DisplayUtil.RESET);
        int angkatanBaru = mahasiswa.getAngkatan();
        
        if (!angkatanInput.trim().isEmpty()) {
            try {
                angkatanBaru = Integer.parseInt(angkatanInput);
                if (angkatanBaru < 1980 || angkatanBaru > 2023) {
                    System.out.println(DisplayUtil.warningMessage("Angkatan harus antara 1980-2023. Menggunakan nilai lama."));
                    angkatanBaru = mahasiswa.getAngkatan();
                }
            } catch (NumberFormatException e) {
                System.out.println(DisplayUtil.errorMessage("Format angkatan tidak valid. Menggunakan nilai lama."));
            }
        }
        
        // Validasi alamat jika diubah
        System.out.println(DisplayUtil.infoMessage("Alamat dapat dikosongkan untuk tetap menggunakan nilai lama"));
        System.out.print(DisplayUtil.CYAN + "Alamat (" + mahasiswa.getAlamat() + "): " + DisplayUtil.RESET);
        String alamatBaru = scanner.nextLine().trim();
        
        if (!alamatBaru.isEmpty()) {
            if (alamatBaru.length() < 5) {
                System.out.println(DisplayUtil.errorMessage("Alamat terlalu pendek! Menggunakan nilai lama."));
                alamatBaru = mahasiswa.getAlamat();
            }
        } else {
            alamatBaru = mahasiswa.getAlamat();
        }
        
        // Validasi nomor telepon jika diubah
        System.out.println(DisplayUtil.infoMessage("Nomor telepon dapat dikosongkan untuk tetap menggunakan nilai lama"));
        System.out.print(DisplayUtil.CYAN + "No. Telepon (" + mahasiswa.getNoTelepon() + "): " + DisplayUtil.RESET);
        String noTeleponBaru = scanner.nextLine().trim();
        
        if (!noTeleponBaru.isEmpty()) {
            try {
                if (!TELEPON_PATTERN.matcher(noTeleponBaru).matches()) {
                    System.out.println(DisplayUtil.errorMessage("Format nomor telepon tidak valid! Menggunakan nilai lama."));
                    noTeleponBaru = mahasiswa.getNoTelepon();
                }
            } catch (Exception e) {
                System.out.println(DisplayUtil.errorMessage("Terjadi kesalahan validasi. Menggunakan nilai lama."));
                noTeleponBaru = mahasiswa.getNoTelepon();
            }
        } else {
            noTeleponBaru = mahasiswa.getNoTelepon();
        }
        
        DisplayUtil.showLoadingAnimation("Memperbarui data mahasiswa", 800);
        
        // Buat objek mahasiswa dengan data baru
        Mahasiswa mahasiswaBaru = new Mahasiswa(nim, namaBaru, jurusanBaru, angkatanBaru, alamatBaru, noTeleponBaru);
        
        boolean berhasil = mahasiswaService.updateMahasiswa(mahasiswaBaru);
        
        if (berhasil) {
            System.out.println("\n" + DisplayUtil.successMessage("Data mahasiswa berhasil diperbarui!"));
            
            // Tampilkan perbandingan data lama dan baru
            System.out.println("\n" + DisplayUtil.BOLD + "PERBANDINGAN DATA:" + DisplayUtil.RESET);
            
            String[][] perbandingan = new String[2][6];
            perbandingan[0][0] = mahasiswa.getNim();
            perbandingan[0][1] = mahasiswa.getNama();
            perbandingan[0][2] = mahasiswa.getJurusan();
            perbandingan[0][3] = String.valueOf(mahasiswa.getAngkatan());
            perbandingan[0][4] = mahasiswa.getAlamat();
            perbandingan[0][5] = mahasiswa.getNoTelepon();
            
            perbandingan[1][0] = mahasiswaBaru.getNim();
            perbandingan[1][1] = mahasiswaBaru.getNama();
            perbandingan[1][2] = mahasiswaBaru.getJurusan();
            perbandingan[1][3] = String.valueOf(mahasiswaBaru.getAngkatan());
            perbandingan[1][4] = mahasiswaBaru.getAlamat();
            perbandingan[1][5] = mahasiswaBaru.getNoTelepon();
            
            String[] rowLabels = {"Data Lama", "Data Baru"};
            System.out.println(DisplayUtil.YELLOW + "Data Lama" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.createTable(headers, new String[][]{perbandingan[0]}, columnWidths));
            System.out.println(DisplayUtil.GREEN + "Data Baru" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.createTable(headers, new String[][]{perbandingan[1]}, columnWidths));
            
        } else {
            System.out.println("\n" + DisplayUtil.errorMessage("Gagal memperbarui data mahasiswa!"));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
    }
    
    /**
     * Menu untuk menghapus data mahasiswa
     */
    private void menuHapusMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("HAPUS DATA MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        System.out.println(DisplayUtil.infoMessage("Masukkan NIM mahasiswa yang ingin dihapus datanya"));
        String nim = InputUtil.bacaNIM(DisplayUtil.CYAN + DisplayUtil.BOLD + "NIM: " + DisplayUtil.RESET);
        
        DisplayUtil.showLoadingAnimation("Mencari data mahasiswa", 500);
        
        Mahasiswa mahasiswa = mahasiswaService.getMahasiswaByNim(nim);
        
        if (mahasiswa == null) {
            System.out.println("\n" + DisplayUtil.errorMessage("Mahasiswa dengan NIM '" + nim + "' tidak ditemukan!"));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        System.out.println("\n" + DisplayUtil.BOLD + DisplayUtil.RED + "DATA MAHASISWA YANG AKAN DIHAPUS:" + DisplayUtil.RESET);
        
        // Tampilkan data mahasiswa yang akan dihapus dengan format yang lebih baik
        String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
        int[] columnWidths = {10, 20, 15, 8, 20, 15};
        
        String[][] data = new String[1][6];
        data[0][0] = mahasiswa.getNim();
        data[0][1] = mahasiswa.getNama();
        data[0][2] = mahasiswa.getJurusan();
        data[0][3] = String.valueOf(mahasiswa.getAngkatan());
        data[0][4] = mahasiswa.getAlamat();
        data[0][5] = mahasiswa.getNoTelepon();
        
        System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
        
        System.out.println("\n" + DisplayUtil.warningMessage("Peringatan: Data yang dihapus tidak dapat dikembalikan!"));
        boolean konfirmasi = InputUtil.bacaYaTidak(DisplayUtil.BOLD + DisplayUtil.RED + "Anda yakin ingin menghapus data mahasiswa ini?" + DisplayUtil.RESET);
        
        if (konfirmasi) {
            DisplayUtil.showLoadingAnimation("Menghapus data mahasiswa", 800);
            
            boolean berhasil = mahasiswaService.hapusMahasiswa(nim);
            
            if (berhasil) {
                System.out.println("\n" + DisplayUtil.successMessage("Data mahasiswa berhasil dihapus!"));
                System.out.println(DisplayUtil.infoMessage("NIM: " + nim + " | Nama: " + mahasiswa.getNama()));
            } else {
                System.out.println("\n" + DisplayUtil.errorMessage("Gagal menghapus data mahasiswa!"));
            }
        } else {
            System.out.println("\n" + DisplayUtil.infoMessage("Penghapusan dibatalkan."));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
    }
    
    /**
     * Menu untuk mengurutkan data mahasiswa
     */
    private void menuUrutkanMahasiswa() {
        DisplayUtil.clearScreen();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.createHeader("URUTKAN DATA MAHASISWA", MENU_WIDTH));
            System.out.println();
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        boolean kembali = false;
        
        while (!kembali) {
            DisplayUtil.clearScreen();
            System.out.println(DisplayUtil.createHeader("URUTKAN DATA MAHASISWA", MENU_WIDTH));
            System.out.println();
            
            // Tampilkan menu dengan format yang lebih menarik
            String[] menuItems = {
                DisplayUtil.BOLD + "1. " + DisplayUtil.RESET + DisplayUtil.BLUE + "🔢 Urutkan berdasarkan NIM" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "2. " + DisplayUtil.RESET + DisplayUtil.GREEN + "📝 Urutkan berdasarkan Nama" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "3. " + DisplayUtil.RESET + DisplayUtil.YELLOW + "📅 Urutkan berdasarkan Angkatan" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "0. " + DisplayUtil.RESET + "↩️ Kembali ke Menu Utama"
            };
            System.out.println(DisplayUtil.createMenu(menuItems, MENU_WIDTH));
            System.out.println();
            
            int pilihan = InputUtil.bacaIntegerRange(DisplayUtil.BOLD + "Pilih menu (0-3): " + DisplayUtil.RESET, 0, 3);
            
            if (pilihan == 0) {
                kembali = true;
            } else {
                DisplayUtil.clearScreen();
                System.out.println(DisplayUtil.createHeader("PILIH URUTAN", MENU_WIDTH));
                System.out.println();
                
                // Tampilkan opsi urutan dengan format yang lebih menarik
                String[] urutanItems = {
                    DisplayUtil.BOLD + "1. " + DisplayUtil.RESET + DisplayUtil.GREEN + "⬆️ Urutkan Naik (A-Z / 0-9)" + DisplayUtil.RESET,
                    DisplayUtil.BOLD + "2. " + DisplayUtil.RESET + DisplayUtil.RED + "⬇️ Urutkan Turun (Z-A / 9-0)" + DisplayUtil.RESET
                };
                System.out.println(DisplayUtil.createMenu(urutanItems, MENU_WIDTH));
                System.out.println();
                
                int urutanPilihan = InputUtil.bacaIntegerRange(DisplayUtil.BOLD + "Pilih urutan (1-2): " + DisplayUtil.RESET, 1, 2);
                
                // Tampilkan animasi proses
                DisplayUtil.showLoadingAnimation("Mengurutkan data mahasiswa", 800);
                
                boolean ascending = (urutanPilihan == 1);
                List<Mahasiswa> hasilUrutan = null;
                String jenisUrutan = "";
                
                switch (pilihan) {
                    case 1:
                        hasilUrutan = mahasiswaService.urutkanByNim(ascending);
                        jenisUrutan = "NIM";
                        break;
                    case 2:
                        hasilUrutan = mahasiswaService.urutkanByNama(ascending);
                        jenisUrutan = "Nama";
                        break;
                    case 3:
                        hasilUrutan = mahasiswaService.urutkanByAngkatan(ascending);
                        jenisUrutan = "Angkatan";
                        break;
                }
                
                DisplayUtil.clearScreen();
                System.out.println(DisplayUtil.createHeader("HASIL PENGURUTAN", MENU_WIDTH));
                System.out.println();
                
                System.out.println(DisplayUtil.infoMessage("Hasil pengurutan berdasarkan " + jenisUrutan + 
                        (ascending ? " (Naik)" : " (Turun)") + ":"));
                System.out.println();
                
                if (hasilUrutan.isEmpty()) {
                    System.out.println(DisplayUtil.warningMessage("Tidak ada data untuk ditampilkan!"));
                } else {
                    // Konversi data mahasiswa ke format tabel
                    String[] headers = {"NIM", "Nama", "Jurusan", "Angkatan", "Alamat", "No. Telepon"};
                    int[] columnWidths = {10, 20, 15, 8, 20, 15};
                    
                    String[][] data = new String[hasilUrutan.size()][6];
                    for (int i = 0; i < hasilUrutan.size(); i++) {
                        Mahasiswa m = hasilUrutan.get(i);
                        data[i][0] = m.getNim();
                        data[i][1] = m.getNama();
                        data[i][2] = m.getJurusan();
                        data[i][3] = String.valueOf(m.getAngkatan());
                        data[i][4] = m.getAlamat();
                        data[i][5] = m.getNoTelepon();
                    }
                    
                    System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
                    System.out.println();
                    System.out.println(DisplayUtil.infoMessage("Total Mahasiswa: " + hasilUrutan.size()));
                }
                
                InputUtil.tunggutEnter("\nTekan ENTER untuk melanjutkan...");
            }
        }
    }
    
    /**
     * Menambahkan data sampel mahasiswa
     * Method ini bisa dipanggil untuk mengisi data sampel/contoh
     */
    public void tambahDataSampel() {
        // Membuat beberapa data contoh mahasiswa
        Mahasiswa m1 = new Mahasiswa("A12345", "Budi Santoso", "Informatika", 2020, "Jakarta", "081234567890");
        Mahasiswa m2 = new Mahasiswa("B54321", "Dewi Lestari", "Akuntansi", 2021, "Bandung", "082345678901");
        Mahasiswa m3 = new Mahasiswa("C98765", "Ahmad Farhan", "Teknik Elektro", 2019, "Surabaya", "083456789012");
        Mahasiswa m4 = new Mahasiswa("D13579", "Siti Nurhaliza", "Manajemen", 2022, "Yogyakarta", "084567890123");
        Mahasiswa m5 = new Mahasiswa("E24680", "Eko Prasetyo", "Ilmu Komunikasi", 2021, "Semarang", "085678901234");
        
        // Menambahkan ke daftar
        mahasiswaService.tambahMahasiswa(m1);
        mahasiswaService.tambahMahasiswa(m2);
        mahasiswaService.tambahMahasiswa(m3);
        mahasiswaService.tambahMahasiswa(m4);
        mahasiswaService.tambahMahasiswa(m5);
    }
    
    /**
     * Menu untuk menampilkan statistik mahasiswa
     * Menampilkan statistik berdasarkan jurusan dan angkatan
     * Implementasi ini menggunakan array 2 dimensi
     */
    private void menuStatistikMahasiswa() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("STATISTIK MAHASISWA", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa yang terdaftar!"));
            System.out.println(DisplayUtil.infoMessage("Silahkan tambahkan data mahasiswa terlebih dahulu."));
            InputUtil.tunggutEnter("\nTekan ENTER untuk kembali ke menu utama...");
            return;
        }
        
        // Tampilkan menu statistik
        boolean kembali = false;
        
        while (!kembali) {
            DisplayUtil.clearScreen();
            System.out.println(DisplayUtil.createHeader("STATISTIK MAHASISWA", MENU_WIDTH));
            System.out.println();
            
            // Tampilkan menu dengan format yang menarik
            String[] menuItems = {
                DisplayUtil.BOLD + "1. " + DisplayUtil.RESET + DisplayUtil.BLUE + "📊 Statistik per Jurusan" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "2. " + DisplayUtil.RESET + DisplayUtil.GREEN + "📊 Statistik per Angkatan" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "3. " + DisplayUtil.RESET + DisplayUtil.YELLOW + "📊 Jurusan Terbanyak" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "4. " + DisplayUtil.RESET + DisplayUtil.PURPLE + "📊 Angkatan Terbanyak" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "5. " + DisplayUtil.RESET + DisplayUtil.CYAN + "📊 Tabel Statistik Lengkap" + DisplayUtil.RESET,
                DisplayUtil.BOLD + "0. " + DisplayUtil.RESET + "↩️ Kembali ke Menu Utama"
            };
            System.out.println(DisplayUtil.createMenu(menuItems, MENU_WIDTH));
            System.out.println();
            
            int pilihan = InputUtil.bacaIntegerRange(DisplayUtil.BOLD + "Pilih menu (0-5): " + DisplayUtil.RESET, 0, 5);
            
            switch (pilihan) {
                case 0:
                    kembali = true;
                    break;
                case 1:
                    tampilkanStatistikPerJurusan();
                    break;
                case 2:
                    tampilkanStatistikPerAngkatan();
                    break;
                case 3:
                    tampilkanJurusanTerbanyak();
                    break;
                case 4:
                    tampilkanAngkatanTerbanyak();
                    break;
                case 5:
                    tampilkanTabelStatistikLengkap();
                    break;
            }
        }
    }
    
    /**
     * Menampilkan statistik mahasiswa per jurusan
     * Menggunakan array 1 dimensi
     */
    private void tampilkanStatistikPerJurusan() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("STATISTIK PER JURUSAN", MENU_WIDTH));
        System.out.println();
        
        String[] daftarJurusan = mahasiswaService.getDaftarJurusan();
        int[] statistikJurusan = mahasiswaService.getStatistikPerJurusan();
        
        if (daftarJurusan.length == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data jurusan!"));
        } else {
            // Konversi data ke format tabel
            String[] headers = {"No", "Jurusan", "Jumlah Mahasiswa"};
            int[] columnWidths = {5, 30, 20};
            
            String[][] data = new String[daftarJurusan.length][3];
            for (int i = 0; i < daftarJurusan.length; i++) {
                data[i][0] = String.valueOf(i + 1);
                data[i][1] = daftarJurusan[i];
                data[i][2] = String.valueOf(statistikJurusan[i]);
            }
            
            System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
            System.out.println();
            System.out.println(DisplayUtil.infoMessage("Total Jurusan: " + daftarJurusan.length));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali...");
    }
    
    /**
     * Menampilkan statistik mahasiswa per angkatan
     * Menggunakan array 1 dimensi
     */
    private void tampilkanStatistikPerAngkatan() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("STATISTIK PER ANGKATAN", MENU_WIDTH));
        System.out.println();
        
        int[] statistikAngkatan = mahasiswaService.getStatistikPerAngkatan();
        
        if (statistikAngkatan.length == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data angkatan!"));
        } else {
            // Konversi data ke format tabel
            String[] headers = {"No", "Tahun Angkatan", "Jumlah Mahasiswa"};
            int[] columnWidths = {5, 20, 20};
            
            String[][] data = new String[statistikAngkatan.length][3];
            for (int i = 0; i < statistikAngkatan.length; i++) {
                data[i][0] = String.valueOf(i + 1);
                data[i][1] = String.valueOf(2018 + i); // Asumsi tahun awal 2018
                data[i][2] = String.valueOf(statistikAngkatan[i]);
            }
            
            System.out.println(DisplayUtil.createTable(headers, data, columnWidths));
            System.out.println();
            
            // Hitung total mahasiswa
            int totalMahasiswa = 0;
            for (int i = 0; i < statistikAngkatan.length; i++) {
                totalMahasiswa += statistikAngkatan[i];
            }
            
            System.out.println(DisplayUtil.infoMessage("Total Mahasiswa: " + totalMahasiswa));
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali...");
    }
    
    /**
     * Menampilkan jurusan dengan mahasiswa terbanyak
     */
    private void tampilkanJurusanTerbanyak() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("JURUSAN TERBANYAK", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa!"));
        } else {
            String jurusanTerbanyak = mahasiswaService.getJurusanTerbanyak();
            
            System.out.println(DisplayUtil.BOLD + DisplayUtil.YELLOW + "Jurusan dengan Mahasiswa Terbanyak:" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.BLUE + jurusanTerbanyak + DisplayUtil.RESET);
            System.out.println();
            
            // Tampilkan rata-rata angkatan mahasiswa
            double rataRataAngkatan = mahasiswaService.getRataRataAngkatan();
            System.out.println(DisplayUtil.BOLD + DisplayUtil.GREEN + "Rata-rata Angkatan Mahasiswa:" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.BLUE + String.format("%.2f", rataRataAngkatan) + DisplayUtil.RESET);
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali...");
    }
    
    /**
     * Menampilkan angkatan dengan mahasiswa terbanyak
     */
    private void tampilkanAngkatanTerbanyak() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("ANGKATAN TERBANYAK", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa!"));
        } else {
            int angkatanTerbanyak = mahasiswaService.getAngkatanTerbanyak();
            
            System.out.println(DisplayUtil.BOLD + DisplayUtil.YELLOW + "Angkatan dengan Mahasiswa Terbanyak:" + DisplayUtil.RESET);
            System.out.println(DisplayUtil.BLUE + angkatanTerbanyak + DisplayUtil.RESET);
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali...");
    }
    
    /**
     * Menampilkan tabel statistik lengkap
     * Menggunakan array 2 dimensi
     */
    private void tampilkanTabelStatistikLengkap() {
        DisplayUtil.clearScreen();
        System.out.println(DisplayUtil.createHeader("TABEL STATISTIK LENGKAP", MENU_WIDTH));
        System.out.println();
        
        if (mahasiswaService.getJumlahMahasiswa() == 0) {
            System.out.println(DisplayUtil.warningMessage("Belum ada data mahasiswa!"));
        } else {
            String[][] dataStatistik = mahasiswaService.getDataStatistikString();
            
            // Temukan ukuran masing-masing kolom
            int[] columnWidths = new int[dataStatistik[0].length];
            for (int j = 0; j < dataStatistik[0].length; j++) {
                int maxWidth = 0;
                for (int i = 0; i < dataStatistik.length; i++) {
                    if (dataStatistik[i][j].length() > maxWidth) {
                        maxWidth = dataStatistik[i][j].length();
                    }
                }
                columnWidths[j] = maxWidth + 2; // tambah padding
            }
            
            // Tampilkan tabel statistik
            System.out.println(DisplayUtil.BOLD + "Jumlah Mahasiswa per Jurusan dan Angkatan:" + DisplayUtil.RESET);
            System.out.println();
            
            // Buat header tabel
            System.out.print(DisplayUtil.BLUE + "┌");
            for (int j = 0; j < columnWidths.length; j++) {
                System.out.print("─".repeat(columnWidths[j]));
                if (j < columnWidths.length - 1) {
                    System.out.print("┬");
                }
            }
            System.out.println("┐" + DisplayUtil.RESET);
            
            // Tampilkan baris header
            System.out.print(DisplayUtil.BLUE + "│");
            for (int j = 0; j < dataStatistik[0].length; j++) {
                String paddedValue = centerText(dataStatistik[0][j], columnWidths[j]);
                System.out.print(DisplayUtil.YELLOW + paddedValue + DisplayUtil.BLUE + "│");
            }
            System.out.println(DisplayUtil.RESET);
            
            // Tampilkan pemisah setelah header
            System.out.print(DisplayUtil.BLUE + "├");
            for (int j = 0; j < columnWidths.length; j++) {
                System.out.print("─".repeat(columnWidths[j]));
                if (j < columnWidths.length - 1) {
                    System.out.print("┼");
                }
            }
            System.out.println("┤" + DisplayUtil.RESET);
            
            // Tampilkan data
            for (int i = 1; i < dataStatistik.length; i++) {
                System.out.print(DisplayUtil.BLUE + "│");
                for (int j = 0; j < dataStatistik[i].length; j++) {
                    String paddedValue = j == 0 ? 
                        padText(dataStatistik[i][j], columnWidths[j]) : 
                        centerText(dataStatistik[i][j], columnWidths[j]);
                    System.out.print(paddedValue + DisplayUtil.BLUE + "│");
                }
                System.out.println(DisplayUtil.RESET);
            }
            
            // Tampilkan footer tabel
            System.out.print(DisplayUtil.BLUE + "└");
            for (int j = 0; j < columnWidths.length; j++) {
                System.out.print("─".repeat(columnWidths[j]));
                if (j < columnWidths.length - 1) {
                    System.out.print("┴");
                }
            }
            System.out.println("┘" + DisplayUtil.RESET);
        }
        
        InputUtil.tunggutEnter("\nTekan ENTER untuk kembali...");
    }
 
    
    /**
     * Helper function untuk memusatkan teks dalam kolom dengan lebar tertentu
     * @param text Teks yang akan diformat
     * @param width Lebar kolom
     * @return Teks yang sudah diformat
     */
    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        int leftPad = (width - text.length()) / 2;
        int rightPad = width - text.length() - leftPad;
        
        return " ".repeat(leftPad) + text + " ".repeat(rightPad);
    }
    
    /**
     * Helper function untuk menambahkan padding ke teks
     * @param text Teks yang akan diformat
     * @param width Lebar kolom
     * @return Teks yang sudah diformat
     */
    private String padText(String text, int width) {
        if (text.length() >= width) {
            return text.substring(0, width);
        }
        
        return text + " ".repeat(width - text.length());
    }
}
