@echo off
call gcloud compute scp ..\yat.pt.conf cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "sudo mv yat.pt.conf /etc/nginx/conf.d; sudo systemctl restart nginx"