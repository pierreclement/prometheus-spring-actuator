## Bringing up the web application, along with prometheus

The [docker-compse.yml](docker-compose.yml) brings up the `webapp` service which is our web application
using the image `stevenacoffman/flask_kubernetes_example` we built above. The [docker-compose-infra.yml](docker-compose-infra.yml)
file brings up the `prometheus` service and also starts the `grafana` service which
is available on port 3000. The config directory contains a `prometheus.yml` file
which sets up the targets for prometheus to scrape. The scrape configuration
looks as follows:

```
# A scrape configuration containing exactly one endpoint to scrape:
# Here it's Prometheus itself.
scrape_configs:
  # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
  - job_name: 'prometheus'

    # Override the global default and scrape targets from this job every 5 seconds.
    scrape_interval: 5s

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.

    static_configs:
         - targets: ['localhost:9090']
  - job_name: 'webapp'

    # Override the global default and scrape targets from this job every 5 seconds.
    scrape_interval: 5s

    # metrics_path defaults to '/metrics'
    # scheme defaults to 'http'.
    static_configs:
        - targets: ['webapp:8888']
```

Prometheus scrapes itself, which is the first target above. The second target
is the our web application on port 8888.
Since these services are running via `docker-compose`, `webapp` automatically resolves to the IP of the webapp container.

To bring up all the services:

```
$ docker-compose -f docker-compose.yml -f docker-compose-infra.yml up
```
