.PHONY: run

run:
	@echo "Running ./gradlew docker"
	@./gradlew
	@echo "Running docker-compose up -d"
	@docker-compose up -d
