.PHONY: run build

run: build
	@echo "Running docker-compose up -d"
	@docker-compose up -d

build:
	@echo "Running ./gradlew clean build -x test"
	@./gradlew clean build -x test