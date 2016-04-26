# BigGIS Infrastructure

[![Build Status](https://travis-ci.org/biggis-project/biggis-infrastructure.svg?branch=master)][Travis]
[![](https://img.shields.io/docker/stars/biggis/biggis-infrastructure.svg)][Dockerhub]
[![](https://img.shields.io/docker/pulls/biggis/biggis-infrastructure.svg)][Dockerhub]
[![](https://badge.imagelayers.io/biggis/biggis-infrastructure:latest.svg)][ImageLayers]

[Dockerhub]: https://hub.docker.com/r/biggis/biggis-infrastructure/
[Travis]: https://travis-ci.org/biggis-project/biggis-infrastructure
[ImageLayers]: https://imagelayers.io/?images=biggis/biggis-infrastructure:latest

# Notes

## Tagging scheme
- Tagging scheme makes use of immutable infrastructure pattern:
  - `<travis-build-#> - <github-branch> - <committer> . <first-8-chars-github-commit-hash>`

## Building docker images

When building docker images for a service it's usually quite common to start out from a base image like ubuntu (~188MB) or centos (~172MB).

However these base images are considered to be 'fat' as they contain various things your application/service might not need but increases your image size significantly.

So like in development when stripping down your code in order to be more efficient, start off from a minimal base image (e.g. Busybox ~2MB, Alpine ~5MB, Debian ~125MB) in order to make the deployment of your application/service more efficient.

see:
- https://www.brianchristner.io/docker-image-base-os-size-comparison/
- http://www.iron.io/microcontainers-tiny-portable-containers/
- https://github.com/iron-io/dockers

Additionally, there are some other important things one has to consider when building a docker image as pointed out below:

see:
- http://phusion.github.io/baseimage-docker/

So the ```phusion/baseimage:<VERSION>``` is a perfect example of a good docker base image.
