# au-property-mgmt-rest

```
mvn clean package docker:build
```

```
docker run --name au-property-mgmt-rest -p 8080:8080 -d au-property-mgmt-rest:latest
```

```
docker network connect au-network au-property-mgmt-rest
```
