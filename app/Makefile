.DEFAULT_GOAL := build

setup:
	./gradlew wrapper --gradle-version 8.3

clean:
	./gradlew clean

build-ci:
	./gradlew clean build check

build:
	./gradlew build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

.PHONY: build