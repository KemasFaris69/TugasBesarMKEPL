package com.studentapp.service;

import static org.junit.Assert.*;

import com.studentapp.model.Mahasiswa;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test untuk PengelolaArrayMahasiswa.
 *
 * Catatan untuk Faris:
 * Nilai-nilai ekspektasi di sini disusun dari membaca logika kode sumber.
 * Saat pertama kali menjalankan `mvn test`, pastikan semuanya hijau.
 * Kalau ada yang merah, kemungkinan ekspektasi perlu disesuaikan -- itu
 * justru bahan bagus untuk demonstrasi "test gagal" di Week 11.
 */
public class PengelolaArrayMahasiswaTest {

    private PengelolaArrayMahasiswa pengelola;

    @Before
    public void setUp() {
        pengelola = new PengelolaArrayMahasiswa();
        pengelola.tambahMahasiswa(new Mahasiswa("101", "Andi", "Informatika", 2021, "Bandung", "0811"));
        pengelola.tambahMahasiswa(new Mahasiswa("102", "Budi", "Informatika", 2022, "Bandung", "0822"));
        pengelola.tambahMahasiswa(new Mahasiswa("103", "Citra", "Sistem Informasi", 2021, "Jakarta", "0833"));
    }

    @Test
    public void testJumlahMahasiswaSetelahTigaPenambahan() {
        assertEquals(3, pengelola.getJumlahMahasiswa());
    }

    @Test
    public void testJumlahJurusanUnik() {
        // Ada 2 jurusan unik: Informatika dan Sistem Informasi
        assertEquals(2, pengelola.getJumlahJurusan());
    }

    @Test
    public void testNimDuplikatDitolak() {
        boolean hasil = pengelola.tambahMahasiswa(
                new Mahasiswa("101", "Duplikat", "Informatika", 2020, "X", "0"));
        assertFalse(hasil);
        // Jumlah tidak boleh bertambah
        assertEquals(3, pengelola.getJumlahMahasiswa());
    }

    @Test
    public void testCariMahasiswaByNim() {
        Mahasiswa m = pengelola.getMahasiswaByNim("102");
        assertNotNull(m);
        assertEquals("Budi", m.getNama());
    }

    @Test
    public void testCariNimTidakAdaMengembalikanNull() {
        assertNull(pengelola.getMahasiswaByNim("999"));
    }

    @Test
    public void testHapusMahasiswa() {
        boolean hasil = pengelola.hapusMahasiswa("102");
        assertTrue(hasil);
        assertEquals(2, pengelola.getJumlahMahasiswa());
        assertNull(pengelola.getMahasiswaByNim("102"));
    }

    @Test
    public void testCariMahasiswaByKeyword() {
        // keyword "informatika" cocok dengan 2 mahasiswa (Andi & Budi)
        Mahasiswa[] hasil = pengelola.cariMahasiswa("informatika");
        assertEquals(2, hasil.length);
    }
}
