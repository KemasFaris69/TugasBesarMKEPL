package com.studentapp.service;

import com.studentapp.model.Mahasiswa;

/**
 * Kelas untuk mengelola data mahasiswa menggunakan array statis (bukan dinamis)
 * Implementasi menggunakan array 1 dimensi dan 2 dimensi
 */
public class PengelolaArrayMahasiswa {
    // Array statis dengan kapasitas tetap untuk menyimpan data mahasiswa (maksimal 100)
    // Array 1 dimensi
    private static final int KAPASITAS_MAKSIMAL = 100;
    private Mahasiswa[] daftarMahasiswa;
    private int jumlahMahasiswa;
    
    // Array 2 dimensi untuk statistik jumlah mahasiswa per jurusan dan angkatan
    // [jurusan][angkatan] - maksimal 10 jurusan dan angkatan dari 2018-2023
    private int[][] statistikJurusanAngkatan;
    private static final int JUMLAH_JURUSAN = 10;
    private static final int TAHUN_AWAL = 2018;
    private static final int TAHUN_AKHIR = 2024;
    
    // Array untuk menyimpan nama-nama jurusan
    private String[] daftarJurusan;
    private int jumlahJurusan;
    
    /**
     * Constructor untuk membuat pengelola array mahasiswa baru
     */
    public PengelolaArrayMahasiswa() {
        // Inisialisasi array 1 dimensi mahasiswa
        this.daftarMahasiswa = new Mahasiswa[KAPASITAS_MAKSIMAL];
        this.jumlahMahasiswa = 0;
        
        // Inisialisasi array 2 dimensi untuk statistik
        this.statistikJurusanAngkatan = new int[JUMLAH_JURUSAN][TAHUN_AKHIR - TAHUN_AWAL + 1];
        
        // Inisialisasi array nama jurusan
        this.daftarJurusan = new String[JUMLAH_JURUSAN];
        this.jumlahJurusan = 0;
    }
    
    /**
     * Procedure untuk menambahkan mahasiswa baru ke array
     * @param mahasiswa Objek mahasiswa yang akan ditambahkan
     * @return true jika berhasil ditambahkan, false jika gagal (array penuh atau NIM sudah ada)
     */
    public boolean tambahMahasiswa(Mahasiswa mahasiswa) {
        // Cek apakah array sudah penuh
        if (jumlahMahasiswa >= KAPASITAS_MAKSIMAL) {
            return false; // Array penuh, tidak bisa menambah lagi
        }
        
        // Cek apakah NIM sudah ada
        for (int i = 0; i < jumlahMahasiswa; i++) {
            if (daftarMahasiswa[i].getNim().equals(mahasiswa.getNim())) {
                return false; // NIM sudah ada
            }
        }
        
        // Tambahkan mahasiswa ke array
        daftarMahasiswa[jumlahMahasiswa] = mahasiswa;
        jumlahMahasiswa++;
        
        // Update statistik jurusan dan angkatan
        updateStatistik(mahasiswa, true);
        
        return true;
    }
    
    /**
     * Function untuk mendapatkan semua mahasiswa dalam array
     * @return Array berisi objek mahasiswa
     */
    public Mahasiswa[] getAllMahasiswa() {
        // Buat array baru untuk mengembalikan salinan data
        // (Agar data asli tidak dimodifikasi langsung)
        Mahasiswa[] hasil = new Mahasiswa[jumlahMahasiswa];
        
        // Salin data mahasiswa
        for (int i = 0; i < jumlahMahasiswa; i++) {
            hasil[i] = daftarMahasiswa[i];
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mencari mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang dicari
     * @return Objek mahasiswa jika ditemukan, null jika tidak
     */
    public Mahasiswa getMahasiswaByNim(String nim) {
        for (int i = 0; i < jumlahMahasiswa; i++) {
            if (daftarMahasiswa[i].getNim().equals(nim)) {
                return daftarMahasiswa[i];
            }
        }
        return null;
    }
    
    /**
     * Procedure untuk memperbarui data mahasiswa
     * @param mahasiswaBaru Objek mahasiswa dengan data yang baru
     * @return true jika berhasil diperbarui, false jika gagal (mahasiswa tidak ditemukan)
     */
    public boolean updateMahasiswa(Mahasiswa mahasiswaBaru) {
        for (int i = 0; i < jumlahMahasiswa; i++) {
            if (daftarMahasiswa[i].getNim().equals(mahasiswaBaru.getNim())) {
                // Update statistik: kurangi statistik lama
                updateStatistik(daftarMahasiswa[i], false);
                
                // Perbarui data mahasiswa
                daftarMahasiswa[i] = mahasiswaBaru;
                
                // Update statistik baru
                updateStatistik(mahasiswaBaru, true);
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Procedure untuk menghapus mahasiswa berdasarkan NIM
     * @param nim NIM mahasiswa yang akan dihapus
     * @return true jika berhasil dihapus, false jika gagal (mahasiswa tidak ditemukan)
     */
    public boolean hapusMahasiswa(String nim) {
        for (int i = 0; i < jumlahMahasiswa; i++) {
            if (daftarMahasiswa[i].getNim().equals(nim)) {
                // Update statistik: kurangi statistik untuk mahasiswa yang dihapus
                updateStatistik(daftarMahasiswa[i], false);
                
                // Geser semua elemen setelah posisi i satu langkah ke kiri
                for (int j = i; j < jumlahMahasiswa - 1; j++) {
                    daftarMahasiswa[j] = daftarMahasiswa[j + 1];
                }
                
                // Set elemen terakhir menjadi null dan kurangi jumlah mahasiswa
                daftarMahasiswa[jumlahMahasiswa - 1] = null;
                jumlahMahasiswa--;
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * Function untuk mencari mahasiswa berdasarkan kata kunci
     * @param keyword Kata kunci pencarian (NIM, nama, atau jurusan)
     * @return Array berisi mahasiswa yang memenuhi kriteria pencarian
     */
    public Mahasiswa[] cariMahasiswa(String keyword) {
        // Pertama, hitung jumlah mahasiswa yang cocok dengan kata kunci
        int jumlahCocok = 0;
        String keywordLower = keyword.toLowerCase();
        
        for (int i = 0; i < jumlahMahasiswa; i++) {
            Mahasiswa m = daftarMahasiswa[i];
            if (m.getNim().toLowerCase().contains(keywordLower) || 
                m.getNama().toLowerCase().contains(keywordLower) ||
                m.getJurusan().toLowerCase().contains(keywordLower)) {
                jumlahCocok++;
            }
        }
        
        // Buat array hasil dengan ukuran yang tepat
        Mahasiswa[] hasil = new Mahasiswa[jumlahCocok];
        
        // Isi array hasil dengan mahasiswa yang cocok
        int indeksHasil = 0;
        for (int i = 0; i < jumlahMahasiswa; i++) {
            Mahasiswa m = daftarMahasiswa[i];
            if (m.getNim().toLowerCase().contains(keywordLower) || 
                m.getNama().toLowerCase().contains(keywordLower) ||
                m.getJurusan().toLowerCase().contains(keywordLower)) {
                hasil[indeksHasil] = m;
                indeksHasil++;
            }
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mengurutkan mahasiswa berdasarkan NIM
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return Array berisi mahasiswa yang sudah diurutkan
     */
    public Mahasiswa[] urutkanByNim(boolean ascending) {
        // Buat salinan array untuk diurutkan
        Mahasiswa[] hasil = getAllMahasiswa();
        
        // Algoritma pengurutan Bubble Sort
        for (int i = 0; i < hasil.length - 1; i++) {
            for (int j = 0; j < hasil.length - i - 1; j++) {
                if (ascending) {
                    if (hasil[j].getNim().compareTo(hasil[j + 1].getNim()) > 0) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                } else {
                    if (hasil[j].getNim().compareTo(hasil[j + 1].getNim()) < 0) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                }
            }
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mengurutkan mahasiswa berdasarkan nama
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return Array berisi mahasiswa yang sudah diurutkan
     */
    public Mahasiswa[] urutkanByNama(boolean ascending) {
        // Buat salinan array untuk diurutkan
        Mahasiswa[] hasil = getAllMahasiswa();
        
        // Algoritma pengurutan Bubble Sort
        for (int i = 0; i < hasil.length - 1; i++) {
            for (int j = 0; j < hasil.length - i - 1; j++) {
                if (ascending) {
                    if (hasil[j].getNama().compareTo(hasil[j + 1].getNama()) > 0) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                } else {
                    if (hasil[j].getNama().compareTo(hasil[j + 1].getNama()) < 0) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                }
            }
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mengurutkan mahasiswa berdasarkan angkatan
     * @param ascending true untuk urutan naik, false untuk urutan turun
     * @return Array berisi mahasiswa yang sudah diurutkan
     */
    public Mahasiswa[] urutkanByAngkatan(boolean ascending) {
        // Buat salinan array untuk diurutkan
        Mahasiswa[] hasil = getAllMahasiswa();
        
        // Algoritma pengurutan Bubble Sort
        for (int i = 0; i < hasil.length - 1; i++) {
            for (int j = 0; j < hasil.length - i - 1; j++) {
                if (ascending) {
                    if (hasil[j].getAngkatan() > hasil[j + 1].getAngkatan()) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                } else {
                    if (hasil[j].getAngkatan() < hasil[j + 1].getAngkatan()) {
                        // Tukar posisi
                        Mahasiswa temp = hasil[j];
                        hasil[j] = hasil[j + 1];
                        hasil[j + 1] = temp;
                    }
                }
            }
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mendapatkan jumlah mahasiswa yang terdaftar
     * @return Jumlah mahasiswa
     */
    public int getJumlahMahasiswa() {
        return jumlahMahasiswa;
    }
    
    /**
     * Procedure untuk memperbarui statistik jurusan dan angkatan
     * @param mahasiswa Mahasiswa yang akan diperbarui statistiknya
     * @param tambah true untuk menambah statistik, false untuk mengurangi
     */
    private void updateStatistik(Mahasiswa mahasiswa, boolean tambah) {
        // Cari indeks jurusan dalam daftarJurusan
        int indeksJurusan = -1;
        for (int i = 0; i < jumlahJurusan; i++) {
            if (daftarJurusan[i].equalsIgnoreCase(mahasiswa.getJurusan())) {
                indeksJurusan = i;
                break;
            }
        }
        
        // Jika jurusan belum ada dalam daftar, tambahkan
        if (indeksJurusan == -1 && tambah) {
            if (jumlahJurusan < JUMLAH_JURUSAN) {
                daftarJurusan[jumlahJurusan] = mahasiswa.getJurusan();
                indeksJurusan = jumlahJurusan;
                jumlahJurusan++;
            }
        }
        
        // Update statistik jika jurusan ada dan angkatan valid
        if (indeksJurusan != -1 && mahasiswa.getAngkatan() >= TAHUN_AWAL && mahasiswa.getAngkatan() <= TAHUN_AKHIR) {
            int indeksAngkatan = mahasiswa.getAngkatan() - TAHUN_AWAL;
            if (tambah) {
                statistikJurusanAngkatan[indeksJurusan][indeksAngkatan]++;
            } else {
                if (statistikJurusanAngkatan[indeksJurusan][indeksAngkatan] > 0) {
                    statistikJurusanAngkatan[indeksJurusan][indeksAngkatan]--;
                }
            }
        }
    }
    
    /**
     * Function untuk mendapatkan statistik mahasiswa berdasarkan jurusan dan angkatan
     * @return Array 2 dimensi berisi jumlah mahasiswa per jurusan dan angkatan
     */
    public int[][] getStatistikJurusanAngkatan() {
        return statistikJurusanAngkatan;
    }
    
    /**
     * Function untuk mendapatkan daftar nama jurusan
     * @return Array berisi nama-nama jurusan
     */
    public String[] getDaftarJurusan() {
        // Buat array baru dengan ukuran yang tepat
        String[] hasil = new String[jumlahJurusan];
        
        // Salin data jurusan
        for (int i = 0; i < jumlahJurusan; i++) {
            hasil[i] = daftarJurusan[i];
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mendapatkan jumlah jurusan
     * @return Jumlah jurusan yang tercatat
     */
    public int getJumlahJurusan() {
        return jumlahJurusan;
    }
    
    /**
     * Function untuk mendapatkan tahun awal untuk statistik angkatan
     * @return Tahun awal
     */
    public int getTahunAwal() {
        return TAHUN_AWAL;
    }
    
    /**
     * Function untuk mendapatkan tahun akhir untuk statistik angkatan
     * @return Tahun akhir
     */
    public int getTahunAkhir() {
        return TAHUN_AKHIR;
    }
}