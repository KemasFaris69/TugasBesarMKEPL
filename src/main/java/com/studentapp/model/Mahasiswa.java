package com.studentapp.model;

/**
 * Model data untuk menyimpan informasi Mahasiswa
 */
public class Mahasiswa {
    private String nim;
    private String nama;
    private String jurusan;
    private int angkatan;
    private String alamat;
    private String noTelepon;
    
    // Constructor
    public Mahasiswa(String nim, String nama, String jurusan, int angkatan, String alamat, String noTelepon) {
        this.nim = nim;
        this.nama = nama;
        this.jurusan = jurusan;
        this.angkatan = angkatan;
        this.alamat = alamat;
        this.noTelepon = noTelepon;
    }
    
    // Getters
    public String getNim() {
        return nim;
    }
    
    public String getNama() {
        return nama;
    }
    
    public String getJurusan() {
        return jurusan;
    }
    
    public int getAngkatan() {
        return angkatan;
    }
    
    public String getAlamat() {
        return alamat;
    }
    
    public String getNoTelepon() {
        return noTelepon;
    }
    
    // Setters
    public void setNim(String nim) {
        this.nim = nim;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }
    
    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }
    
    public void setAngkatan(int angkatan) {
        this.angkatan = angkatan;
    }
    
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
    
    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
    
    @Override
    public String toString() {
        return String.format("| %-10s | %-20s | %-15s | %-8d | %-20s | %-15s |", 
                nim, nama, jurusan, angkatan, alamat, noTelepon);
    }
}
