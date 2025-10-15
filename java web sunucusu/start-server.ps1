# PowerShell helper: derle ve çalıştır
# Çalıştırma izni sorunları için: powershell -ExecutionPolicy Bypass -File start-server.ps1

$src = "src/SimpleWebServer.java"
$outDir = "out"
if (-not (Test-Path $outDir)) { New-Item -ItemType Directory -Path $outDir | Out-Null }

Write-Host "Derleniyor..."
javac -d $outDir $src
if ($LASTEXITCODE -ne 0) {
    Write-Host "Derleme başarısız" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "Çalıştırılıyor..." -ForegroundColor Green
java -cp $outDir SimpleWebServer
