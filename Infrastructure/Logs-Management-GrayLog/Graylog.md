## Graylog :Spring boot Centralized Microservices Logging with Graylog.

## Get Started

Graylog is basically an open source log aggregation service,
which is capable of collecting millions of logs from multiple sources and
display on a single interface. It also provides numerous features like dashboards,
real time alerts and so much more.
Graylog supports multiple types of inputs such as AMQP, UDP, TCP, Kafka and syslogs. Here we’ll see how to collect logs
from a
spring application(or any java application) via UDP.

## Setup a Graylog instance

add the file logback-spring in the package resources in our project

## Network protocol used

Here we’ll see how to collect logs from a spring application
(or any java application) via UDP:12201

## Ip address

127.0.0.1

## Build With(image version in docker compose file )

* Mongodb
* Elasticsearch
* Graylog

## Docker compose file

add docker-compose file in project structure<br>
Run docker-compose file with command

```
docker-compose up -d  
```

## Admin interface

When import finish, go to http://127.0.0.1:9000 and log in with user admin/admin
