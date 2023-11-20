setup:
	./gradlew wrapper --gradle-version 8.3

clean:
	./gradlew clean

build:
	./gradlew clean build

test:
	./gradlew test

report:
	./gradlew jacocoTestReport

lint:
	./gradlew checkstyleMain

check-deps:
	./gradlew dependencyUpdates -Drevision=release


.PHONY: build
