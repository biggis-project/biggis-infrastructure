Ansible roles for setting up and provisioning a fully running Mesos cluster.

## Prerequisite

Edit ```inventory/hosts```file to your cluster specific ip addresses. Edit ```remote_user``` or ```become_user``` in ```common.yml, mesos-master.yml, mesos-slaves.yml, roles/mesos/tasks/mesos-dns.yml``` to your sudo user.

## Getting started

```
ansible -i inventory/hosts all -m ping
```

```
ansible-playbook -i inventory/hosts playbook.yml
```
