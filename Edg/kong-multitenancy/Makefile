kong-postgres:
	COMPOSE_PROFILES=database KONG_DATABASE=postgres docker compose up

kong-dbless:
	docker compose up

clean:
	docker compose kill
	docker compose rm -f
