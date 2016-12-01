## Ansible roles
Various Ansible playbooks for BigGIS infrastructure on CentOS7

## Currently available roles
| Role       | Description                                                 | Purpose                                                                                                 |
|------------|-------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| common     | Installs common system tools                                | Provision Vagrant-based VM's for BigGIS infrastructure with general purpose packages                    |
| docker     | Installs [Docker](https://www.docker.com/)                  | Provision Vagrant-based Mesos slaves (VM) with Docker to run containerized Docker applications          |
| java8      | Installs Oracle Java 8                                      | Provision Vagrant-based VM's for BigGIS infrastructure with Oracle JRE 8                                |
| marathon   | Installs [Marathon](https://mesosphere.github.io/marathon/) | Provision Vagrant-based Mesos masters (VM) with Marathon                                                |
| mesos      | Installs [Mesos](http://mesos.apache.org/)                  | Provision Vagrant-based Mesos Cluster                                                                   |
| vagrant    | Installs [Vagrant](https://www.vagrantup.com/)              | Provision BigGIS infrastructure with Vagrant to simulate distributed multi-node setup (cluster of VM's) |
| virtualbox | Installs [VirtualBox](https://www.virtualbox.org/)          | Provision BigGIS infrastructure with VirtualBox as virtualization provider for Vagrant                  |
| zookeeper  | Installs [Zookeeper](https://zookeeper.apache.org/)         | Provision Vagrant-based Mesos masters (VM) with Zookeeper for High-Availability (HA)                    |
