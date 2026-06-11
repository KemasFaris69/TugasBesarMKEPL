package com.studentapp.service;

import static org.junit.Assert.*;

import com.studentapp.model.Mahasiswa;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test untuk StatistikMahasiswa.
 *
 * Catatan: nilai ekspektasi dihitung manual dari logika kode.
 * Data uji: 3 mahasiswa -> Informatika(2), Sistem Informasi(1),
 * angkatan: 2021, 2022, 2021.
 */
public class StatistikMahasiswaTest {

    private PengelolaArrayMahasiswa pengelola;
    private StatistikMahasiswa statistik;

    @Before
    public void setUp() {
        pengelola = new PengelolaArrayMahasiswa();
        pengelola.tambahMahasiswa(new Mahasiswa("101", "Andi", "Informatika", 2021, "Bandung", "0811"));
        pengelola.tambahMahasiswa(new Mahasiswa("102", "Budi", "Informatika", 2022, "Bandung", "0822"));
        pengelola.tambahMahasiswa(new Mahasiswa("103", "Citra", "Sistem Informasi", 2021, "Jakarta", "0833"));
        statistik = new StatistikMahasiswa(pengelola);
    }

    @Test
    public void testRataRataAngkatan() {
        // (2021 + 2022 + 2021) / 3 = 2021.333...
        double rata = statistik.getRataRataAngkatan();
        assertEquals(2021.333, rata, 0.01);
    }

    @Test
    public void testRataRataAngkatanKosong() {
        StatistikMahasiswa kosong = new StatistikMahasiswa(new PengelolaArrayMahasiswa());
        assertEquals(0.0, kosong.getRataRataAngkatan(), 0.0001);
    }

    @Test
    public void testJumlahMahasiswaPerJurusan() {
        int[] perJurusan = statistik.getJumlahMahasiswaPerJurusan();
        assertEquals(2, perJurusan.length);
        // urutan jurusan sesuai urutan pertama kali muncul: Informatika dulu
        assertEquals(2, perJurusan[0]); // Informatika
        assertEquals(1, perJurusan[1]); // Sistem Informasi
    }

    @Test
    public void testJurusanTerbanyak() {
        assertEquals("Informatika", statistik.getJurusanTerbanyak());
    }

    @Test
    public void testAngkatanTerbanyak() {
        // 2021 muncul 2x, 2022 muncul 1x -> terbanyak 2021
        assertEquals(2021, statistik.getAngkatanTerbanyak());
    }
}
