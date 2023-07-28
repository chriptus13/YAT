@echo off
cd ..\OpenID-Connect-Java-Spring-Server\openid-connect-server-webapp
call mvn package
call gcloud compute scp .\target\openid-connect-server-webapp.war cn-61d-g06@%1:
call gcloud compute ssh cn-61d-g06@%1 --command "sudo mv openid-connect-server-webapp.war /usr/share/tomcat/webapps; sudo systemctl restart tomcat"
cd ..\..\scripts