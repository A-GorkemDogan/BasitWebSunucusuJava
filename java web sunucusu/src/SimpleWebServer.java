import java.io.*; // InputStream, OutputStream, Reader, Writer ve BuffredReader sınıflarını içerir
import java.net.*; // Socket ve ServerSocket sınıflarını içerir
import java.nio.charset.StandardCharsets; 

/**
 * - Dinleme portu: 1989
 * - Bağlanan her istemciye basit bir HTTP/1.0 yanıtı gönderir
 * - Üç satırlık HTML çıktısı: H1 (Ad Soyad), H2 (Öğrenci No), ve stil uygulanmış kısa biyografi
 */
public class SimpleWebServer {
    private static final int PORT = 1989;

    private static final String FULL_NAME = "Azem Görkem Aygören";
    private static final String STUDENT_ID = "1240505069";
    private static final String BIO_HTML = "<p style=\"color:#5b03ff;font-family:Arial,\'Helvetica Neue\',Helvetica,sans-serif;line-height:1.4\">2006 Yılında İstanbul Kartal'da Doğdu. Memleketi Rize'dir. İlkokul, ortaokul ve lise eğitimini Kocaeli'de sürdüren Görkem, günümüzde Kırklareli Üniversitesi'nde Yazılım Mühendisliği Öğrencisidir. </p>";

    public static void main(String[] args) {
        System.out.println("Basit Web Sunucusu başlatılıyor, port=" + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Sunucu " + PORT + " portunda başlatıldı. Tarayıcıdan http://localhost:" + PORT + " adresine bağlanabilirsiniz.");
            while (true) {
                try (Socket client = serverSocket.accept()) {
                    handleClient(client);
                } catch (IOException e) {
                    System.err.println("İstemci işlenirken hata: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Sunucu başlatılamadı: " + e.getMessage());
        }
    }

    private static void handleClient(Socket client) throws IOException {
        // Okuma (istek başlıklarını tüket)
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream(), StandardCharsets.US_ASCII)); // istemciden gelen byte akışını alır, okur ve karakter akışına dönüştürür
        OutputStream out = client.getOutputStream(); // istemciye veri göndermek için kullanılan çıkış akışını alır

        // Basitçe ilk satırı oku (örn: GET / HTTP/1.1)
        String requestLine = in.readLine(); // tarayıcıdan gelen http isteğinin ilk satırını okur
        if (requestLine == null || requestLine.isEmpty()) { 
            return;
        }
        System.out.println("Gelen istek: " + requestLine + " from " + client.getRemoteSocketAddress());

        // İstek başlıklarını atla
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) { // tarayıcı ilk satırdan sonra birçok başlık gönderir, bu döngü boş satır gelene kadar devam eder.
            // Başlıkları tüket
        }

        String body = buildHtmlBody(); // HTML içeriğini oluşturur
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        String response = "HTTP/1.0 200 OK\r\n"
                + "Content-Type: text/html; charset=utf-8\r\n"
                + "Content-Length: " + bodyBytes.length + "\r\n"
                + "Connection: close\r\n"
                + "\r\n";

        out.write(response.getBytes(StandardCharsets.US_ASCII)); 
        out.write(bodyBytes);
        out.flush();
    }

    private static String buildHtmlBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("<!doctype html>\n");
        sb.append("<html lang=\"tr\">\n");
        sb.append("<head>\n");
        sb.append("  <meta charset=\"utf-8\">\n");
        sb.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        sb.append("  <title>Basit Sunucu</title>\n");
        // Inline CSS örneği
        sb.append("  <style>\n");
        sb.append("    body { background: linear-gradient(180deg,#f8fafc,#ffffff); padding:40px; }\n");
        sb.append("    .card { max-width:800px; margin:0 auto; background:#fff; border-radius:10px; box-shadow:0 6px 18px rgba(0, 0, 0, 0.08); padding:24px; }\n");
        sb.append("    h1 { color:#0f0f0f; font-family:'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }\n");
        sb.append("    h2 { color:#ed0707; font-family:Georgia, 'Times New Roman', Times, serif; }\n");
        sb.append("  </style>\n");
        sb.append("</head>\n");
        sb.append("<body>\n");
        sb.append("  <div class=\"card\">\n");
        sb.append("    <h1>").append(escapeHtml(FULL_NAME)).append("</h1>\n");
        sb.append("    <h2>").append(escapeHtml(STUDENT_ID)).append("</h2>\n");
        sb.append("    ").append(BIO_HTML).append("\n");
        sb.append("  </div>\n");
        sb.append("</body>\n");
        sb.append("</html>\n");
        return sb.toString();
    }

    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }
}
