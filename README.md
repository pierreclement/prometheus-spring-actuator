# Example Spring Boot Actuator app with Prometheus on Kubernetes

inspired by

- https://reflectoring.io/monitoring-spring-boot-with-prometheus/
- https://github.com/prometheus/client_java
- https://github.com/microservices-demo/carts
- http://blog.monkey.codes/actuator-and-prometheus/
- https://github.com/f-lopes/spring-boot-docker
- https://github.com/spotify/dockerfile-maven

does not work well with Spring Boot 2.x (e.g. 2.0.0.M6)

brew install grafana

grafana-server --config=/usr/local/etc/grafana/grafana.ini --homepath /usr/local/share/grafana cfg:default.paths.logs=/usr/local/var/log/grafana cfg:default.paths.data=/usr/local/var/lib/grafana cfg:default.paths.plugins=/usr/local/var/lib/grafana/plugins

https://prometheus.io/docs/visualization/grafana/

visit http://0.0.0.0:3000/login with admin / admin

visit http://localhost:8080/prometheus


custom metrics
- gc
- other JMX stats?
- error rate
- transaction rate (MVC calls, ...)


# Lightweight Spring Boot Docker image based on Alpine + Docker Compose file

Features:
- Spring profiles
- Custom JAVA_OPTS
- Healthcheck
- Wrapper script that sets (or not) debug and jmx java options
- Debug mode

## Available environment variables

Name                    | Default   | Description
------------------------|-----------|------------------------------------
DEBUG                   | false | Enable or disable debug mode
DEBUG_PORT              | 8000  | Debug port
JMX                   | false | Enable or disable jmx mode
JMX_PORT              | 9010  | Debug port
SPRING_PROFILES_ACTIVE  | dev   | Active Spring profiles
JAVA_OPTS               |       | JAVA_OPTS
**SERVER_PORT**         | 8080  | Spring Boot application server port (server.port)

## How to use ?

1. Build the image with `mvn
package` and push it with `mvn deploy`.  Of course you can also say
`mvn dockerfile:build` explicitly. This uses [dockerfile-maven](https://github.com/spotify/dockerfile-maven/).

2. Start your application:
    - Using Docker CLI

    - Using the provided `docker-compose.yml` in this repository:

```
docker-compose -f compose/docker-compose.yml up
```

### Inject environment variables:

```
docker run -d -p 8080:8080 -e JAVA_OPTS=-Xms256m -Xmx512m spring-boot-image
```

## Using Docker Compose

The `docker-compose.yml` file picks its configuration from the `.env` one.

Using this file, you can set the desired properties:
```
# Docker
IMAGE_NAME=stevenacoffman/prometheus-spring-actuator


# Application
SERVER_PROTOCOL=http
SERVER_PORT=8080
DEBUG_PORT=8000
JMX_PORT=9010
SPRING_PROFILES_ACTIVE=dev
DEBUG=false
JMX=false
# -Dprop=... -Dparameter=...
JAVA_OPTS=

# Host
HOST_SERVER_PORT=8080
HOST_DEBUG_PORT=8000
HOST_JMX_PORT=9010
```

## Debug mode
To run the application in debug mode, simply set the `DEBUG` variable to true in the `.env` file.

Launch the application:
`docker-compose -f compose/docker-compose.yml up`

By default, the debug port used is the `8000`.

## JMX mode
To run the application in JMX mode, simply set the `JMX` variable to true in the `.env` file.

Launch the application:
`docker-compose -f compose/docker-compose.yml up`

By default, the debug port used is the `9010`.

## Spring profile(s)
By default, the application will run with `dev` Spring profile

To run the application with a specific Spring profile, set the desired one in the `.env` file:
```
SPRING_PROFILES_ACTIVE=dev
```

## Running the application via Kubernetes

```
$ kubectl apply -f ./k8s
```

## Making Prometheus In Kubernetes Automatically pick up endpoint

Add the proper annotations to your deployment to inform prometheus what and where to scrape:

```
spec:
  replicas: 1 # tells deployment to run 2 pods matching the template
  template: # create pods using pod definition in this template
    metadata:
      labels:
        run: prometheus-spring-actuator
      annotations:
        prometheus.io/scrape: "true"
        prometheus.io/port: "8888"
```
