Bu depo için Copilot/AI ajan talimatları

Kısa özet
- Proje: Saf Java (JDK) kullanılarak yazılmış basit bir HTTP sunucusu.
- Ana dosya: `src/SimpleWebServer.java` — tek-dosyalık, tek-thread, socket tabanlı sunucu.
- Port: 1989

Hedefler (AI için)
- Kodun amacı: gelen HTTP isteklerini kabul edip basit bir HTML sayfa ile yanıt vermek. Üç ana çıktı öğesi: H1 (Ad Soyad), H2 (Öğrenci No) ve stil uygulanmış kısa biyografi.
- Değişiklik yaparken: üçüncü taraf kütüphane eklemeyin (kural). Sadece JDK sınıfları kullanılmalıdır.

Önemli dosyalar
- `src/SimpleWebServer.java` — Sunucunun tüm davranışı burada. İstek okuma, başlıkları atlama, UTF-8 içerik ile HTTP/1.0 yanıt oluşturma yapılır.
- `README.md` — Derleme ve çalıştırma talimatları (Windows PowerShell örneği).
- `start-server.ps1` — Windows için derleme+çalıştır scripti.

Kod ve mimari notları (nedenleriyle)
- Tek-thread, bloklayıcı yaklaşım: Kod öğretici ve minimal bağımlılıklar için basit tutulmuş. Çoklu istemci veya yüksek performans gerekliyse, AI önerileri "thread pool" veya NIO (java.nio) ile sınırlı ve açıkça yeni dosya/örnek ekleyerek yapılmalı.
- HTTP/1.0 ile cevap: Basitlik ve uyumluluk için HTTP/1.0 yanıt başlıkları kullanılır. Keep-alive desteklenmez.
- HTML gövdesi UTF-8 olarak gönderilir; Content-Length doğru hesaplanır.

Proje kuralları ve tarz
- Hiçbir 3. parti bağımlılığı eklemeyin.
- Değişiklikler `src/` altında toplanmalı. Yeni yardımcı sınıflar gerekiyorsa `src/` içinde yeni dosyalar oluşturun.
- Kullanıcı bilgileri (ad, öğrenci no, biyografi) `SimpleWebServer.java` içinde sabitler halinde saklanır — AI değişiklik yaparken bu sabitleri güncelleme veya dıştan yapılandırma (örn. env veya args) ekleme seçeneklerini teklif edebilir.

Yönlendirme örnekleri (kod tamamlama / PR önerileri)
- "Kısa ve güvenli" değişiklik: `FULL_NAME`, `STUDENT_ID`, `BIO_HTML` sabitlerini güncelle. Bu en düşük riskli değişiklik.
- "Geliştirme" önerisi: Çoklu istemci desteği eklemek için `ExecutorService` kullanarak `serverSocket.accept()` döngüsünde yeni görevler oluşturun. Bu değişiklik PR'da test ve küçük örnekle sunulmalı.
- "Opsiyonel" yapı: `BIO_HTML` içinde inline CSS kullanılmaktadır. Alternatif olarak küçük bir `style.css` eklenip sunulabilir; ancak yeni dosya eklenmesi gerekiyorsa README ve run script güncellensin.

Test ve çalıştırma
- Windows PowerShell için README talimatlarını takip edin.
- Temel doğrulama: tarayıcıdan http://localhost:1989 adresine gidildiğinde sayfa dönecek.

Sınırlamalar (AI'nin bilmesi gerekenler)
- Bu repo küçük ve öğretici amaçlıdır; güvenlik, path traversal, büyük istek gövdeleri, concurrency, veya HTTPS ile ilgili önlemler uygulanmamıştır.
- İstek satırlarını sadece ilk satır okunup geri kalan başlıklar atlanır; büyük veya yavaş istemciler için zaman aşımı eklenmesi gerekebilir.

PR hazırlama notu
- Açıklayıcı commit mesajları kullanın (ne, neden). Örnek: "Add ExecutorService to handle multiple clients concurrently" veya "Make FULL_NAME configurable via env var".
- Yeni dosya eklenirse README.md ve start-server.ps1 güncellensin.

Eğer bu yönergede eksik veya belirsiz bir bölüm varsa, lütfen hangi senaryoları kapsamak istediğinizi söyleyin; ajanın daha ayrıntılı kurallar üretmesini sağlayacağım.