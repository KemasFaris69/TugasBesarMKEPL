# Panduan Demo Kasus Gagal (wajib untuk video, bobot 15%)

Brief meminta minimal SATU skenario kegagalan. Berikut dua opsi paling mudah,
pilih salah satu (atau tunjukkan keduanya untuk nilai maksimal).

## Opsi A — Continuous Testing GAGAL (paling cepat)

Ubah satu nilai assert agar tidak cocok dengan logika kode, di file:
`src/test/java/com/studentapp/service/StatistikMahasiswaTest.java`

Cari baris:
    assertEquals(2021, statistik.getAngkatanTerbanyak());
Ubah jadi:
    assertEquals(2099, statistik.getAngkatanTerbanyak());

Lalu:
    git checkout dev
    git add . && git commit -m "demo: test gagal" && git push

Hasil: job `test` MERAH di tab Actions, job `inspect` & `deploy` tidak jalan.
Narasikan: "test gagal karena ekspektasi 2099 tidak sesuai data, pipeline berhenti
di tahap CT dan tidak melanjutkan ke inspeksi maupun delivery."

JANGAN LUPA balikkan lagi (2099 -> 2021) dan push agar pipeline hijau kembali.

## Opsi B — Continuous Inspection GAGAL (Quality Gate)

Tambahkan code smell yang melanggar Quality Gate di salah satu kelas, contoh
variabel tidak terpakai + magic number, atau turunkan ambang coverage di SonarCloud.
Push ke `dev` -> job `inspect` gagal karena Quality Gate "Failed".
Narasikan penyebabnya dari dashboard SonarCloud.

## Urutan rekomendasi rekaman
1. Tunjukkan pipeline HIJAU penuh (push normal ke dev, lalu PR & merge ke main + approval).
2. Tunjukkan satu kasus GAGAL (Opsi A atau B) dan jelaskan penyebabnya.
3. Perbaiki -> hijau lagi.
