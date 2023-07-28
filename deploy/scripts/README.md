# TP3 Scripts

## Host initializing scripts

All this scripts take one argument which is the target VM instance created in the GCP project.

### [`init-as.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/init-as.bat)

This script initializes the VM with the needed configuration for the MITREid Authorization Server and deploys the `.war` application into it.

### [`init-nginx.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/init-nginx.bat)

This script initializes the VM for the Load Balancer and Static resource server and deploys both the `yat.pt` config and static resources such as `index.html`, `main.js`, etc.

### [`init-node.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/init-node.bat)

This script initializes the VM with the needed configuration for the API and deploys the Spring Boot application into it.

## Guest initializing scripts

All this scripts are executed in the target VMs to initialize them with the basic configuration needed for the right purposes.

### [`as-node-init.sh`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/as-node-init.sh)

* This script installs Tomcat to run the `.war` MITREid application; 
* Defines a firewall rule to forward all input communications from port 80/TCP to 8080/TCP, basically because only super users can launch applications listening on ports lesser than 1024 and we don't want the Tomcat user to be super user (security reasons); 
* Start and enable Tomcat service;
* Disables SELinux.

### [`nginx-node-init.sh`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/nginx-node-init.sh)

* This script installs Nginx and its dependency Epel-release;
* Start and enable Nginx service;
* Create the directory for static resource deploy and the directory for Nginx to server those resources;
* Disables SELinux.

### [`api-node-init.sh`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/api-node-init.sh)

* This script installs Java to run the Spring Boot application;
* Defines a firewall rule to forward all input communications from port 80/TCP to 8080/TCP, basically because only super users can launch applications listening on ports lesser than 1024 and we don't want the Tomcat user to be super user (security reasons);
* Sets the needed key for API to access Google Cloud SQL Database;
* Creates Spring user to run the application without super user privileges;
* Configure, start and enable Spring service.

## Deploy scripts

This scripts allow futher builds and deploys into each corresponding machine. All this scripts take one argument which is the target VM instance created on the GCP project.

### [`as-build-and-deploy.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/as-build-and-deploy.bat)

This script builds and deploys the MITREid Authorization Server application `.war`.

### [`update-nginx-conf.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/update-nginx-conf.bat)

This script simply updates the `yat.pt` configuration into the VM hosting the Nginx.

### [`update-nginx-static-content.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/update-nginx-static-content.bat)

This script builds and deploys the static resources and application into the VM hosting the Nginx.

### [`api-build-and-deploy.bat`](https://github.com/isel-leic-daw/S1819V-LI61N-G06/blob/master/TP3/scripts/api-build-and-deploy.bat)

This script builds and deploys the API Spring Boot application.
