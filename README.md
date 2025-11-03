# DemoBritishSpokenTime
Interactive terminal application built with Java Maven, following TDD, DDD, and clean code principles.

## Quick Start

```bash
# Compile and run application
mvn compile exec:java

# Run tests
mvn test

# Package and run JAR
mvn package
java -jar target/demoBritishSpokenTime-1.0-SNAPSHOT.jar
```

## Usage
Once running, the application accepts the following commands:
- `help` - Display available commands
- `exit` / `quit` / `q` - Exit the application
- Any other input will be echoed back

## Features
- Interactive command-line interface
- User input processing
- Multiple exit commands (exit, quit, q)
- Built-in help system

## Requirements
- Java 17+
- Maven 3.6+
