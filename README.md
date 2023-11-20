# Project Overview
This is the repository for the "java-project-71" which includes an library to compare two JSON or YAML files. The project is developed with Java programming language.

## Library
The application can be utilized to find the difference between two input files while allowing for the formatting of the output to be controlled. It turns out to be beneficial in scenarios where developers need to detect changes between various versions of the same file or to merge pieces of software.

## How it works
The function `public class App implements Callable<Integer>` compares two JSON or YAML files based on the command line arguments and outputs the differences in the console. In contrast, the `public final class Differ` includes the main logic which is used to process and distinguish variations between two JSON or YAML files.

## Running the Library
To use the application, you need to have Gradle and Java installed on your system. Then, you can use the provided `Makefile` to manage the project lifecycle. Here are the primary commands available:

- `make setup`: Sets up the Gradle wrapper.
- `make clean`: Cleans the build folder.
- `make build`: Builds the project after cleaning it.
- `make install`: Installs the app into a local directory for testing.
- `make run-dist`: Runs the app from its installed directory.
- `make run`: Runs the app directly from Gradle.
- `make test`: Runs the test suites.
- `make report`: Generates a Jacoco test report.
- `make lint`: Runs Checkstyle linter.
- `make build-run`: Builds the project and runs it afterwards.

To compare two files, run the application with the paths to the two files to compare as parameters:

```bash
./gradlew run --args='filepath1 filepath2'
```

This will display the differences between the two JSON or YAML files in the console in a stylish format. If you want the differences in a plain or json format, you can use the `-f` or `--format` option followed by `plain` or `json`, like this:

```bash
./gradlew run --args='filepath1 filepath2 --format plain'
./gradlew run --args='filepath1 filepath2 --format json'
```

## Testing
The project utilizes JUnit for testing and uses Jacoco as a tool for generating test coverage reports.

## Linting
For enforcing and maintaining the coding standards, we have integrated the Checkstyle tool into our project through Gradle.

Please note that the interpretations provided above are generic and based on the common use cases of the tool. The actual functionality and utilization may differ based on the specifications and requirements of the individual projects.

### Hexlet tests and linter status:
[![Actions Status](https://github.com/ArsenHandzhyan/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/ArsenHandzhyan/java-project-71/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/825bb9f7e56f423fd834/maintainability)](https://codeclimate.com/github/ArsenHandzhyan/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/825bb9f7e56f423fd834/test_coverage)](https://codeclimate.com/github/ArsenHandzhyan/java-project-71/test_coverage)
# Asciinema 
[![asciicast](https://asciinema.org/a/Z56GUpiTQyTxtCX4DkVKtDfQs.svg)](https://asciinema.org/a/Z56GUpiTQyTxtCX4DkVKtDfQs)
[![asciicast](https://asciinema.org/a/IwqbjoI41sXeFt2B6q9LSuCOp.svg)](https://asciinema.org/a/IwqbjoI41sXeFt2B6q9LSuCOp)
[![asciicast](https://asciinema.org/a/64PIVbCTrUCj5tIQM4Z1FyaQa.svg)](https://asciinema.org/a/64PIVbCTrUCj5tIQM4Z1FyaQa)
[![asciicast](https://asciinema.org/a/5TdAbCncurpC5l28Lyc2oZvxu.svg)](https://asciinema.org/a/5TdAbCncurpC5l28Lyc2oZvxu)

