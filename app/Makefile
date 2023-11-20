.DEFAULT_GOAL := build-run

setup:
	./gradlew wrapper --gradle-version 8.3

clean:
	./gradlew clean

build:
	./gradlew clean build check

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

.PHONY: build