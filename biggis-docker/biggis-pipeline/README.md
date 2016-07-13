## Work in progess (ToDo)
1. Integrate PostGIS in ```docker-compose.yml```.
2. Write ```startup.sh``` for exposing neccessary env variables (${USER_ID}).
3. Workaround for inotifywait inside ```biggis/collector:0.9.0.0``` needed. Reason is, that inotifywait only watches the Union FS inside the container for new files. It does not receive the hosts FS events when you would run ```touch file``` from the host inside the shared volume. However, adding a new file inside the container works. Which also works is the following:

```
$ docker cp tile.tiff biggispipeline_collector_1:/storage/rasteringest
```
