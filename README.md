# Student Registration System — Pipeline CI/CD

Tugas Besar **Manajemen Konfigurasi dan Evolusi Perangkat Lunak (MKEPL)**
Implementasi pipeline CI/CD dengan GitHub Actions pada aplikasi konsol Java.

---

## 1. Deskripsi Singkat Proyek

Aplikasi **Student Registration System** adalah aplikasi konsol (CLI) berbasis Java
untuk mengelola data pendaftaran mahasiswa: menambah, mencari, mengubah, menghapus,
mengurutkan, serta menghitung statistik mahasiswa per jurusan dan angkatan.

Proyek ini dibangun dengan **Java 17 + Maven** dan dilengkapi unit test (JUnit 4)
serta laporan coverage (JaCoCo) sebagai dasar inspeksi kualitas kode.

## 2. Arsitektur Pipeline CI/CD

Seluruh komponen diimplementasikan dalam **satu workflow** (`.github/workflows/ci-cd.yml`)
sebagai empat job berurutan. Setiap job bergantung pada keberhasilan job sebelumnya
(`needs`), sehingga kegagalan di tahap mana pun menghentikan tahap setelahnya.

```
  push / pull_request
          │
          ▼
   ┌─────────────┐    ┌─────────────┐    ┌──────────────────┐    ┌─────────────┐
   │  build (CI) │ ─▶ │  test (CT)  │ ─▶ │ inspect (Sonar)  │ ─▶ │ deploy (CD) │
   │  mvn compile│    │  mvn test   │    │ mvn verify+sonar │    │ mvn package │
   └─────────────┘    └─────────────┘    └──────────────────┘    └─────────────┘
       semua branch       semua branch        semua branch          hanya main
                                                                  (manual approval)
```

| Tahap | Job | Fungsi | Gagal jika |
|-------|-----|--------|-----------|
| Continuous Integration | `build` | Kompilasi proyek & resolusi dependensi Maven | Kode gagal di-compile |
| Continuous Testing | `test` | Menjalankan unit test JUnit otomatis | Ada test yang tidak lulus |
| Continuous Inspection | `inspect` | Analisis statis & Quality Gate SonarCloud | Quality Gate dilanggar |
| Continuous Delivery | `deploy` | Build JAR executable & publish artifact | Tahap sebelumnya gagal |

### Pilihan CD: Continuous **Delivery**

Kelompok memilih **Continuous Delivery**, bukan Continuous Deployment.
Job `deploy` memakai GitHub **Environment `production`** yang dikonfigurasi dengan
*required reviewer*, sehingga build artifact siap rilis dibuat otomatis tetapi
**perpindahan ke production membutuhkan persetujuan manual**. Alasannya: untuk
proyek akademik, kontrol rilis manual lebih aman dan lebih mudah didemonstrasikan
dibanding deploy penuh otomatis ke production.

## 3. Strategi Branching

| Branch | Peran | Pipeline yang berjalan |
|--------|-------|------------------------|
| `dev` | Tempat pengembangan & integrasi fitur | CI, CT, Inspection (tiap push & PR) |
| `main` | Branch rilis | CI, CT, Inspection, **+ Delivery** |

Branch `main` dilindungi **branch protection** (require PR + require status checks
to pass) sehingga kode hanya masuk ke `main` setelah seluruh tahap CI/CT/Inspection lulus.

## 4. Pembagian Tugas Anggota Kelompok

> Isi sesuai anggota kelompok kalian.

| Nama | NIM | Komponen Tanggung Jawab |
|------|-----|-------------------------|
| _______________ | ____________ | Continuous Integration (`build`) + struktur Maven/`pom.xml` |
| _______________ | ____________ | Continuous Testing (`test`) + unit test JUnit |
| _______________ | ____________ | Continuous Inspection (SonarCloud + Quality Gate) |
| _______________ | ____________ | Continuous Delivery (`deploy`) + branch protection & README |

## 5. Tools & Teknologi per Tahap

| Tahap | Tools / Teknologi |
|-------|-------------------|
| Bahasa & Build | Java 17 (Temurin), Apache Maven |
| Version Control & CI/CD Engine | Git, GitHub, GitHub Actions |
| Continuous Testing | JUnit 4, Maven Surefire |
| Coverage | JaCoCo |
| Continuous Inspection | SonarCloud (sonar-maven-plugin) |
| Continuous Delivery | Maven Shade Plugin (fat JAR), GitHub Actions Artifacts, GitHub Environments |

## 6. Cara Menjalankan Proyek Secara Lokal

Prasyarat: **JDK 17+** dan **Maven 3.8+** terpasang.

```bash
# Verifikasi tool
java -version
mvn -v

# Kompilasi
mvn compile

# Jalankan unit test
mvn test

# Test + laporan coverage JaCoCo (target/site/jacoco/jacoco.xml)
mvn verify

# Build JAR executable
mvn package

# Jalankan aplikasi
java -jar target/student-registration-system-1.0.0.jar
```

## 7. Setup SonarCloud (sekali, sebelum pipeline jalan penuh)

1. Daftar di https://sonarcloud.io (login dengan GitHub).
2. Buat **Organization**, lalu **import** repository ini.
3. Buat token analisis: *My Account → Security → Generate Token*.
4. Di repo GitHub: **Settings → Secrets and variables → Actions → New repository secret**,
   nama `SONAR_TOKEN`, isi dengan token tadi.
5. Ganti `sonar.organization` dan `sonar.projectKey` di `pom.xml` dengan nilai dari
   dashboard SonarCloud kalian.
6. Di SonarCloud, atur **Quality Gate** (mis. "Sonar way") agar pipeline gagal saat
   ambang kualitas dilanggar.

## 8. Setup Manual Approval (Continuous Delivery)

Di GitHub: **Settings → Environments → New environment** beri nama `production`,
lalu centang **Required reviewers** dan tambahkan satu anggota kelompok sebagai
reviewer. Dengan ini job `deploy` akan menunggu approval sebelum berjalan.

---

> **Catatan teknis kejujuran:** Nilai ekspektasi pada unit test disusun dari membaca
> logika kode sumber dan diverifikasi manual, **belum dieksekusi otomatis** pada
> lingkungan penyiapan dokumen (tidak tersedia Maven/JDK lengkap di sana). Jalankan
> `mvn test` sekali di mesin lokal/Actions untuk memastikan semua hijau sebelum
> perekaman video.
