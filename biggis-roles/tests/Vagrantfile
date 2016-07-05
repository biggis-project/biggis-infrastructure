# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant.configure(2) do |config|

  config.vm.box = "bento/centos-7.1"

  # Use NFS for shared folders for better performance
  # config.vm.synced_folder '.', '/vagrant', nfs: true

  config.vm.provider "virtualbox" do |v|
    host = RbConfig::CONFIG['host_os']
    # Give VM 1/4 system memory
    if host =~ /darwin/
      # sysctl returns Bytes and we need to convert to MB
      mem = `sysctl -n hw.memsize`.to_i / 1024
    elsif host =~ /linux/
      # meminfo shows KB and we need to convert to MB
      mem = `grep 'MemTotal' /proc/meminfo | sed -e 's/MemTotal://' -e 's/ kB//'`.to_i
    elsif host =~ /mswin|mingw|cygwin/
      # Windows code via https://github.com/rdsubhas/vagrant-faster
      mem = `wmic computersystem Get TotalPhysicalMemory`.split[1].to_i / 1024
    end

    mem = mem / 1024 / 4

    v.memory = mem
    v.cpus = 1
  end

  config.vm.define "node1" do |node1|
    node1.vm.network "private_network", ip: "192.168.33.10"
    node1.vm.hostname = "node1"
    node1.vm.provision "shell" do |s|
      s.inline = "
      sudo sed -i 's/node1 //' /etc/hosts;
      sudo sed -i '\$a192.168.33.10 biggis.dev.net.node1' /etc/hosts;
      sudo sed -i '\$a192.168.33.11 biggis.dev.net.node2' /etc/hosts;
      sudo sed -i '\$a192.168.33.12 biggis.dev.net.node3' /etc/hosts;
      sudo sed -i 's/node1/biggis.dev.net.node1/g' /etc/hostname
      sudo hostname biggis.dev.net.node1;
      "
      s.privileged = true
    end
  end

  config.vm.define "node2" do |node2|
    node2.vm.network "private_network", ip: "192.168.33.11"
    node2.vm.hostname = "node2"
    node2.vm.provision "shell" do |s|
      s.inline = "
      sudo sed -i 's/node2 //' /etc/hosts;
      sudo sed -i '\$a192.168.33.10 biggis.dev.net.node1' /etc/hosts;
      sudo sed -i '\$a192.168.33.11 biggis.dev.net.node2' /etc/hosts;
      sudo sed -i '\$a192.168.33.12 biggis.dev.net.node3' /etc/hosts;
      sudo sed -i 's/node1/biggis.dev.net.node2/g' /etc/hostname
      sudo hostname biggis.dev.net.node2;
      "
      s.privileged = true
    end
  end

  config.vm.define "node3" do |node3|
    node3.vm.network "private_network", ip: "192.168.33.12"
    node3.vm.hostname = "node3"
    node3.vm.provision "shell" do |s|
      s.inline = "
      sudo sed -i 's/node3 //' /etc/hosts;
      sudo sed -i '\$a192.168.33.10 biggis.dev.net.node1' /etc/hosts;
      sudo sed -i '\$a192.168.33.11 biggis.dev.net.node2' /etc/hosts;
      sudo sed -i '\$a192.168.33.12 biggis.dev.net.node3' /etc/hosts;
      sudo sed -i 's/node1/biggis.dev.net.node3/g' /etc/hostname
      sudo hostname biggis.dev.net.node3;
      "
      s.privileged = true
    end
  end

end
