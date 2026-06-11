package com.studentapp.service;

import com.studentapp.model.Mahasiswa;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Kelas untuk mengelola data Mahasiswa (CRUD, pencarian, pengurutan)
 * Versi baru: Menggunakan array statis melalui kelas PengelolaArrayMahasiswa
 */
public class MahasiswaService {
    // Atribut pengelola array untuk penyimpanan data dengan array statis
    private PengelolaArrayMahasiswa pengelolaArray;
    
    // Objek untuk analisis statistik
    private StatistikMahasiswa statistik;
    
    /**
     * Constructor
     * Menginisialisasi pengelola array dan statistik
     */
    public MahasiswaService() {
        // Inisialisasi pengelola array
        this.pengelolaArray = new PengelolaArrayMahasiswa();
        // Inisialisasi statistik
        this.statistik = new StatistikMahasiswa(pengelolaArray);
    }
    
    /**
     * Menambahkan mahasiswa baru ke daftar
     * @param mahasiswa Objek mahasiswa yang akan ditambahkan
     * @return true jika berhasil ditambahkan
     */
    public boolean tambahMahasiswa(Mahasiswa mahasiswa) {
        // Delegasikan ke pengelola array
        return pengelolaArray.tambahMahasiswa(mahasiswa);
    }
    
    /**
     * Mendapatkan daftar semua mahasiswa
     * @return List yang berisi seluruh mahasiswa
     */
    public List<Mahasiswa> getAllMahasiswa() {
        // Konversi array ke List untuk kompatibilitas dengan kode yang sudah ada
        return Arrays.asList(pengelolaArray.getAllMahasiswa());
    }
    
    /**
     * Mendapatkan mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang dicari
     * @return Objek mahasiswa jika ditemukan, null jika tidak
     */
    public Mahasiswa getMahasiswaByNim(String nim) {
        // Delegasikan ke pengelola array
        return pengelolaArray.getMahasiswaByNim(nim);
    }
    
    /**
     * Memperbarui data mahasiswa
     * @param mahasiswaBaru Objek mahasiswa dengan data yang baru
     * @return true jika berhasil diperbarui
     */
    public boolean updateMahasiswa(Mahasiswa mahasiswaBaru) {
        // Delegasikan ke pengelola array
        return pengelolaArray.updateMahasiswa(mahasiswaBaru);
    }
    
    /**
     * Menghapus mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang akan dihapus
     * @return true jika berhasil dihapus
     */
    public boolean hapusMahasiswa(String nim) {
        // Delegasikan ke pengelola array
        return pengelolaArray.hapusMahasiswa(nim);
    }
    
    /**
     * Mencari mahasiswa berdasarkan kata kunci pada nama, NIM, atau jurusan
     * @param keyword Kata kunci pencarian
     * @return List mahasiswa yang memenuhi kriteria pencarian
     */
    public List<Mahasiswa> cariMahasiswa(String keyword) {
        // Delegasikan ke pengelola array dan konversi hasilnya ke List
        return Arrays.asList(pengelolaArray.cariMahasiswa(keyword));
    }
    
    /**
     * Mengurutkan mahasiswa berdasarkan NIM
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return List mahasiswa yang sudah diurutkan
     */
    public List<Mahasiswa> urutkanByNim(boolean ascending) {
        // Delegasikan ke pengelola array dan konversi hasilnya ke List
        return Arrays.asList(pengelolaArray.urutkanByNim(ascending));
    }
    
    /**
     * Mengurutkan mahasiswa berdasarkan nama
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return List mahasiswa yang sudah diurutkan
     */
    public List<Mahasiswa> urutkanByNama(boolean ascending) {
        // Delegasikan ke pengelola array dan konversi hasilnya ke List
        return Arrays.asList(pengelolaArray.urutkanByNama(ascending));
    }
    
    /**
     * Mengurutkan mahasiswa berdasarkan Angkatan
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return List mahasiswa yang sudah diurutkan
     */
    public List<Mahasiswa> urutkanByAngkatan(boolean ascending) {
        // Delegasikan ke pengelola array dan konversi hasilnya ke List
        return Arrays.asList(pengelolaArray.urutkanByAngkatan(ascending));
    }
    
    /**
     * Mendapatkan jumlah mahasiswa yang terdaftar
     * @return Jumlah mahasiswa
     */
    public int getJumlahMahasiswa() {
        // Delegasikan ke pengelola array
        return pengelolaArray.getJumlahMahasiswa();
    }
    
    /**
     * Mendapatkan statistik jumlah mahasiswa per jurusan
     * @return Array berisi jumlah mahasiswa per jurusan
     */
    public int[] getStatistikPerJurusan() {
        // Delegasikan ke objek statistik
        return statistik.getJumlahMahasiswaPerJurusan();
    }
    
    /**
     * Mendapatkan statistik jumlah mahasiswa per angkatan
     * @return Array berisi jumlah mahasiswa per angkatan
     */
    public int[] getStatistikPerAngkatan() {
        // Delegasikan ke objek statistik
        return statistik.getJumlahMahasiswaPerAngkatan();
    }
    
    /**
     * Mendapatkan daftar jurusan
     * @return Array berisi nama-nama jurusan yang ada
     */
    public String[] getDaftarJurusan() {
        // Delegasikan ke pengelola array
        return pengelolaArray.getDaftarJurusan();
    }
    
    /**
     * Mendapatkan data statistik dalam format string 2D
     * @return Array string 2D berisi statistik jurusan dan angkatan
     */
    public String[][] getDataStatistikString() {
        // Delegasikan ke objek statistik
        return statistik.getDataStatistikString();
    }
    
    /**
     * Mendapatkan jurusan dengan mahasiswa terbanyak
     * @return Nama jurusan dengan mahasiswa terbanyak
     */
    public String getJurusanTerbanyak() {
        // Delegasikan ke objek statistik
        return statistik.getJurusanTerbanyak();
    }
    
    /**
     * Mendapatkan angkatan dengan mahasiswa terbanyak
     * @return Tahun angkatan dengan mahasiswa terbanyak
     */
    public int getAngkatanTerbanyak() {
        // Delegasikan ke objek statistik
        return statistik.getAngkatanTerbanyak();
    }
    
    /**
     * Mendapatkan rata-rata angkatan mahasiswa
     * @return Rata-rata angkatan
     */
    public double getRataRataAngkatan() {
        // Delegasikan ke objek statistik
        return statistik.getRataRataAngkatan();
    }
  
}
