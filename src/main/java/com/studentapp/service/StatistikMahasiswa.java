package com.studentapp.service;

import com.studentapp.model.Mahasiswa;

/**
 * Kelas untuk mengelola statistik data mahasiswa
 * Menyediakan fungsi-fungsi analisis statistik
 */
public class StatistikMahasiswa {
    // Referensi ke pengelola array mahasiswa
    private PengelolaArrayMahasiswa pengelolaArray;
    
    /**
     * Constructor
     * @param pengelolaArray Objek PengelolaArrayMahasiswa yang akan dianalisis
     */
    public StatistikMahasiswa(PengelolaArrayMahasiswa pengelolaArray) {
        this.pengelolaArray = pengelolaArray;
    }
    
    /**
     * Function untuk menghasilkan statistik jumlah mahasiswa per jurusan
     * @return Array 1 dimensi berisi jumlah mahasiswa per jurusan
     */
    public int[] getJumlahMahasiswaPerJurusan() {
        String[] daftarJurusan = pengelolaArray.getDaftarJurusan();
        int jumlahJurusan = pengelolaArray.getJumlahJurusan();
        int[][] statistik = pengelolaArray.getStatistikJurusanAngkatan();
        
        // Inisialisasi array hasil
        int[] hasil = new int[jumlahJurusan];
        
        // Hitung jumlah mahasiswa untuk setiap jurusan
        for (int i = 0; i < jumlahJurusan; i++) {
            int jumlah = 0;
            for (int j = 0; j < (pengelolaArray.getTahunAkhir() - pengelolaArray.getTahunAwal() + 1); j++) {
                jumlah += statistik[i][j];
            }
            hasil[i] = jumlah;
        }
        
        return hasil;
    }
    
    /**
     * Function untuk menghasilkan statistik jumlah mahasiswa per angkatan
     * @return Array 1 dimensi berisi jumlah mahasiswa per angkatan
     */
    public int[] getJumlahMahasiswaPerAngkatan() {
        int jumlahJurusan = pengelolaArray.getJumlahJurusan();
        int tahunAwal = pengelolaArray.getTahunAwal();
        int tahunAkhir = pengelolaArray.getTahunAkhir();
        int[][] statistik = pengelolaArray.getStatistikJurusanAngkatan();
        
        // Inisialisasi array hasil
        int[] hasil = new int[tahunAkhir - tahunAwal + 1];
        
        // Hitung jumlah mahasiswa untuk setiap angkatan
        for (int j = 0; j < hasil.length; j++) {
            int jumlah = 0;
            for (int i = 0; i < jumlahJurusan; i++) {
                jumlah += statistik[i][j];
            }
            hasil[j] = jumlah;
        }
        
        return hasil;
    }
    
    /**
     * Function untuk mendapatkan rata-rata angkatan mahasiswa
     * @return Rata-rata angkatan
     */
    public double getRataRataAngkatan() {
        Mahasiswa[] daftarMahasiswa = pengelolaArray.getAllMahasiswa();
        int jumlahMahasiswa = pengelolaArray.getJumlahMahasiswa();
        
        if (jumlahMahasiswa == 0) {
            return 0.0;
        }
        
        int totalAngkatan = 0;
        for (int i = 0; i < jumlahMahasiswa; i++) {
            totalAngkatan += daftarMahasiswa[i].getAngkatan();
        }
        
        return (double) totalAngkatan / jumlahMahasiswa;
    }
    
    /**
     * Function untuk mendapatkan jurusan dengan mahasiswa terbanyak
     * @return Nama jurusan dengan mahasiswa terbanyak
     */
    public String getJurusanTerbanyak() {
        String[] daftarJurusan = pengelolaArray.getDaftarJurusan();
        int[] jumlahPerJurusan = getJumlahMahasiswaPerJurusan();
        
        if (daftarJurusan.length == 0) {
            return "Belum ada jurusan";
        }
        
        int indeksTerbanyak = 0;
        for (int i = 1; i < jumlahPerJurusan.length; i++) {
            if (jumlahPerJurusan[i] > jumlahPerJurusan[indeksTerbanyak]) {
                indeksTerbanyak = i;
            }
        }
        
        return daftarJurusan[indeksTerbanyak];
    }
    
    /**
     * Function untuk mendapatkan angkatan dengan mahasiswa terbanyak
     * @return Tahun angkatan dengan mahasiswa terbanyak
     */
    public int getAngkatanTerbanyak() {
        int[] jumlahPerAngkatan = getJumlahMahasiswaPerAngkatan();
        int tahunAwal = pengelolaArray.getTahunAwal();
        
        if (jumlahPerAngkatan.length == 0) {
            return 0;
        }
        
        int indeksTerbanyak = 0;
        for (int i = 1; i < jumlahPerAngkatan.length; i++) {
            if (jumlahPerAngkatan[i] > jumlahPerAngkatan[indeksTerbanyak]) {
                indeksTerbanyak = i;
            }
        }
        
        return tahunAwal + indeksTerbanyak;
    }
    
    /**
     * Function untuk mendapatkan data statistik dalam format string 2D
     * @return Array string 2D berisi statistik jurusan dan angkatan
     */
    public String[][] getDataStatistikString() {
        String[] daftarJurusan = pengelolaArray.getDaftarJurusan();
        int jumlahJurusan = pengelolaArray.getJumlahJurusan();
        int tahunAwal = pengelolaArray.getTahunAwal();
        int tahunAkhir = pengelolaArray.getTahunAkhir();
        int[][] statistik = pengelolaArray.getStatistikJurusanAngkatan();
        
        // Buat array hasil dengan ukuran yang sesuai
        // +1 baris untuk header tahun, +1 kolom untuk nama jurusan
        String[][] hasil = new String[jumlahJurusan + 1][tahunAkhir - tahunAwal + 2];
        
        // Isi header tahun
        hasil[0][0] = "Jurusan/Angkatan";
        for (int j = 0; j < tahunAkhir - tahunAwal + 1; j++) {
            hasil[0][j + 1] = String.valueOf(tahunAwal + j);
        }
        
        // Isi data statistik
        for (int i = 0; i < jumlahJurusan; i++) {
            hasil[i + 1][0] = daftarJurusan[i];
            for (int j = 0; j < tahunAkhir - tahunAwal + 1; j++) {
                hasil[i + 1][j + 1] = String.valueOf(statistik[i][j]);
            }
        }
        
        return hasil;
    }
}