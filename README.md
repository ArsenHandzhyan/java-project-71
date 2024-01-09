# Java-Project-71 

## Overview
java-project-71 is an innovative Java library for comparing two JSON or YAML files. This tool is beneficial for developers who need to spot changes across different versions of a file.

## Library
This library allows the detection of differences between two input files and controls the output's formatting. It's created to be integrated with other software packages or utilities, giving them the capability to compare JSON or YAML files.

## How It Works
Our library functions primarily through two classes. The `public class App implements Callable<Integer>` acts as an entry point to the comparison mechanism. It processes command-line arguments, feeding them into the primary comparison logic. The `Differ` class houses the main logic to process and distinguish variances between two JSON or YAML files.

## Usage
To integrate this library within another Java application, ensure that the environment has both Java and Gradle installed. We provide a `Makefile` that supervises the project lifecycle, offering main commands such as setup, clean, build, install, and run.
To showcase the functionality of the library, you can compare two files by executing the run command with the paths to the two files to be compared as parameters:

```bash
./gradlew run --args='filepath1 filepath2'
```
This command outputs a stylized illustration of the differences between the two JSON or YAML files. If you prefer a plain text or JSON-formatted display, use the `-f` or `--format` option followed by `stylish`, `plain` or `json`:

```bash
./gradlew run --args='filepath1 filepath2 --format stylish'
./gradlew run --args='filepath1 filepath2 --format plain'
./gradlew run --args='filepath1 filepath2 --format json'
```

## Testing
We employ JUnit for library testing and Jacoco for generating coverage reports, offering insights into our test performance and coverage areas.

## Linting
This project maintains code quality and standards using the Checkstyle tool, integrated via Gradle. This ensures that the codebase remains clean, organized, and follows consistent coding standards.

Please consider that the above description provides a general understanding of this library's functions and use cases. The actual functionality and application may vary based on the specifications and requirements of the individual projects integrating it.


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
