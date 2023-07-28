#!/bin/bash
sudo yum -y update
sudo yum -y install tomcat
sudo firewall-cmd --zone="trusted" --add-forward-port=port=80:proto=tcp:toport=8080
sudo firewall-cmd --zone="trusted" --add-forward-port=port=80:proto=tcp:toport=8080 --permanent
sudo systemctl start tomcat
sudo systemctl enable tomcat
sudo sed -i 's/^SELINUX=.*$/SELINUX=disabled/' /etc/selinux/config
sudo shutdown -r now