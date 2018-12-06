@Echo off
cd /D "%~dp0"

echo "installiere Chocolatey Paket-Manager..."
@"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"

echo "installiere Zubehör für Chocolatey..."
choco feature enable -n allowGlobalConfirmation
choco install chocolateygui
choco install chocolateypackageupdater

echo "installiere Chrome Browser..."
choco install googlechrome
choco install adblockpluschrome

echo "installiere Firefox Browser..."
choco install firefox
choco install adblockplus-firefox

echo "installiere 7-ZIP..."
choco install 7zip.install

echo "installiere Java Development Kit..."
choco install jdk8

echo "installiere Git..."
choco install git.install

echo "installiere NodeJS..."
choco install nodejs.install

echo "installiere yarn..."
choco install yarn

echo "installiere maven..."
choco install maven

echo "installiere PuTTY"
choco install putty

echo "installiere cUrl..."
choco install curl

echo "installiere wget..."
choco install wget

echo "installiere process explorer..."
choco install procexp

echo "installiere SublimeText..."
choco install sublimetext3

echo "installiere jhipster..."
choco install yo
choco install jhipster

echo "Installiere docker for windows..."
choco install docker-for-windows

echo "Installation abgeschlossen"
pause
