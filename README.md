Basit Java Web Sunucusu

Bu proje öğretici amaçlı, saf Java (JDK) kullanarak yazılmış basit bir HTTP sunucusudur.

Özellikler
- Port: 1989
- Sadece JDK sınıfları kullanılır (Socket, ServerSocket vb.)
- Gelen her isteğe basit bir HTML sayfa döner: H1 (Ad Soyad), H2 (Öğrenci No) ve stil uygulanmış kısa bir biyografi

Dosyalar
- `src/SimpleWebServer.java` - Sunucu kaynağı.
- `start-server.ps1` - Windows PowerShell üzerinde derleyip çalıştırmak için yardımcı script.

Derleme ve çalıştırma (Windows PowerShell)

```powershell
# Derle
javac -d out src/SimpleWebServer.java

# Çalıştır
java -cp out SimpleWebServer
```

Alternatif: hazırladığım `start-server.ps1` betiğini çalıştırabilirsiniz:

```powershell
powershell -ExecutionPolicy Bypass -File start-server.ps1
```

Sunucuya erişim
- Tarayıcınızı açın ve şu adrese gidin: http://localhost:1989

Kişisel bilgileri değiştirme
- `src/SimpleWebServer.java` içindeki `FULL_NAME`, `STUDENT_ID` ve `BIO_HTML` sabitlerini güncelleyin.

Notlar
- Bu sunucu eğitim amaçlıdır. Üretim için uygun değildir (tek-thread, sınırlandırılmış hata yönetimi, güvenlik eksiklikleri).
