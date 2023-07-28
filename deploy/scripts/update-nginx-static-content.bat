@echo off
cd ..\..\webapp
call npm run-script build
call gcloud compute scp --recurse .\dist\ cn-61d-g06@%1:/home/cn-61d-g06/yat
call gcloud compute ssh cn-61d-g06@%1 --command "sudo mv ~/yat/* /var/www/yat"
cd ..\deploy\scripts