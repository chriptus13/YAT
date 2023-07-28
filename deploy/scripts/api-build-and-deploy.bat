@echo off
cd ..\..\backend
call .\gradlew bootJar
call gcloud compute scp build\libs\daw_g06-0.0.1-SNAPSHOT.jar cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "sudo mv daw_g06-0.0.1-SNAPSHOT.jar /var/spring; sudo systemctl restart spring"
cd ..\deploy\scripts