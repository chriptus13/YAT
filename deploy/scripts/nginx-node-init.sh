#!/bin/bash
sudo yum -y update
sudo yum -y install epel-release nginx
sudo systemctl start nginx
sudo systemctl enable nginx
mkdir yat
sudo mkdir -p /var/www/yat
sudo sed -i 's/^SELINUX=.*$/SELINUX=disabled/' /etc/selinux/config
sudo shutdown -r now