cd ./etcd-v3.1.9-windows-amd64
start /b etcd.exe
timeout /t 3
cd ./../
service-center.exe
