@echo off
rem Setup machine
call gcloud compute scp nginx-node-init.sh cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "chmod +x nginx-node-init.sh; ./nginx-node-init.sh"
pause
rem setup files
call .\update-nginx-conf.bat %1
call .\update-nginx-static-content.bat %1