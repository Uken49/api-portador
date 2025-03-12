local-env-create:
	docker-compose -f stack.yml up -d
	sleep 3
	docker cp data/data.sql postgres_portador:/var/lib/postgresql/data.sql
	docker exec postgres_portador psql -h localhost -U admin -d postgres -a -f ./var/lib/postgresql/data.sql

local-env-destroy:
	docker-compose -f stack.yml down