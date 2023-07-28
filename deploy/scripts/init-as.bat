@echo off
call gcloud compute scp as-node-init.sh cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "chmod +x as-node-init.sh; ./as-node-init.sh"
pause
call .\as-build-and-deploy.bat %1