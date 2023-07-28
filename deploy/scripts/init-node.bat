@echo off
call gcloud compute scp ..\spring.service cn-61d-g06@%1:
call gcloud compute scp ..\gcp-creds.json cn-61d-g06@%1:
call gcloud compute scp api-node-init.sh cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "chmod +x api-node-init.sh; ./api-node-init.sh"
call .\api-build-and-deploy.bat %1