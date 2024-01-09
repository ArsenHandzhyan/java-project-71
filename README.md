# Java-Project-71

## Overview
java-project-71 is an innovative Java library designed for comparing two JSON or YAML files. It serves as a valuable tool for developers seeking to identify changes across different versions of a file.

## Library
This library facilitates the detection of differences between two input files and provides control over the output's formatting. It is built to seamlessly integrate with other software packages or utilities, empowering them to compare JSON or YAML files effectively.

## How It Works
The library primarily operates through two classes. The public class `App` implements `Callable<Integer>` and acts as the entry point to the comparison mechanism. It processes command-line arguments, directing them to the primary comparison logic. The `Differ` class contains the main logic to process and distinguish variations between two JSON or YAML files.

## Usage
To integrate this library within another Java application, ensure Java and Gradle are installed. A Makefile supervises the project lifecycle, providing commands such as setup, clean, build, install, and run.

To showcase the library's functionality, you can compare two files by executing the `run` command with the paths to the two files:

```bash
./gradlew run --args='filepath1 filepath2'
This command outputs a stylized illustration of the differences. For a plain text or JSON-formatted display, use the -f or --format option followed by stylish, plain, or json:

bash
Copy code
./gradlew run --args='filepath1 filepath2 --format stylish'
./gradlew run --args='filepath1 filepath2 --format plain'
./gradlew run --args='filepath1 filepath2 --format json'
Usage with Help Flag
For detailed information on using the library and its options, you can utilize the --help flag:

bash
Copy code
./gradlew run --args='--help'

## Testing
We employ JUnit for library testing and Jacoco for generating coverage reports, offering insights into our test performance and coverage areas.

## Linting
This project maintains code quality and standards using the Checkstyle tool, integrated via Gradle. This ensures that the codebase remains clean, organized, and follows consistent coding standards.

### Hexlet tests and linter status:
[![Actions Status](https://github.com/ArsenHandzhyan/java-project-71/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/ArsenHandzhyan/java-project-71/actions)
[![Java CI](https://github.com/ArsenHandzhyan/java-project-71/actions/workflows/main.yml/badge.svg)](https://github.com/ArsenHandzhyan/java-project-71/actions/workflows/main.yml)
[![Maintainability](https://api.codeclimate.com/v1/badges/825bb9f7e56f423fd834/maintainability)](https://codeclimate.com/github/ArsenHandzhyan/java-project-71/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/825bb9f7e56f423fd834/test_coverage)](https://codeclimate.com/github/ArsenHandzhyan/java-project-71/test_coverage)
# Asciinema 
[![asciicast](https://asciinema.org/a/Z56GUpiTQyTxtCX4DkVKtDfQs.svg)](https://asciinema.org/a/Z56GUpiTQyTxtCX4DkVKtDfQs)
[![asciicast](https://asciinema.org/a/IwqbjoI41sXeFt2B6q9LSuCOp.svg)](https://asciinema.org/a/IwqbjoI41sXeFt2B6q9LSuCOp)
[![asciicast](https://asciinema.org/a/64PIVbCTrUCj5tIQM4Z1FyaQa.svg)](https://asciinema.org/a/64PIVbCTrUCj5tIQM4Z1FyaQa)
[![asciicast](https://asciinema.org/a/5TdAbCncurpC5l28Lyc2oZvxu.svg)](https://asciinema.org/a/5TdAbCncurpC5l28Lyc2oZvxu)
