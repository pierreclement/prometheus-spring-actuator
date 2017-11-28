inspired by 

- https://reflectoring.io/monitoring-spring-boot-with-prometheus/
- https://github.com/prometheus/client_java
- https://github.com/microservices-demo/carts
- http://blog.monkey.codes/actuator-and-prometheus/

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