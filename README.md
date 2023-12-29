To deploy your socle project to a Docker infrastructure, follow these steps:

1. Create a new file named .env in the root of your project.

2. Log in to the Docker oga registry from your terminal:

   docker login 

3. Pull images from the Docker registry. Make sure you are in the root of your project directory:

   docker-compose -f Infrastructure/docker-compose.override.yml -f Infrastructure/config-server/docker-compose.yml -f Infrastructure/discovrey-server/docker-compose.yml -f Infrastructure/gateway/docker-compose.yml -f Infrastructure/postgres/docker-compose.yml  -f Infrastructure/mongodb/docker-compose.yml -f Infrastructure/kafka/docker-compose.yml -f Infrastructure/cqrs/docker-compose.yml -f Infrastructure/zipkini/docker-compose.yml --env-file ".env" pull

4. Deploy the Docker images as Docker containers:

   docker-compose -f Infrastructure/docker-compose.override.yml -f Infrastructure/config-server/docker-compose.yml -f Infrastructure/discovrey-server/docker-compose.yml -f Infrastructure/gateway/docker-compose.yml -f Infrastructure/postgres/docker-compose.yml  -f Infrastructure/mongodb/docker-compose.yml -f Infrastructure/kafka/docker-compose.yml -f Infrastructure/cqrs/docker-compose.yml -f Infrastructure/zipkini/docker-compose.yml --env-file ".env"  up -d

NB: Do not commit and push ".env" to the GitLab repository. Add it to the ".gitignore" file instead.
