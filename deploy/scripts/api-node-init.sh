#!/bin/bash
sudo yum -y update
sudo yum -y install java-1.8.0-openjdk
sudo useradd spring
sudo mkdir /var/spring
sudo chown spring:spring /var/spring
sudo mv spring.service /etc/systemd/system
sudo mv gcp-creds.json /var/spring
sudo systemctl start spring
sudo systemctl enable spring
sudo firewall-cmd --add-forward-port=port=80:proto=tcp:toport=8080
sudo firewall-cmd --add-forward-port=port=80:proto=tcp:toport=8080 --permanent