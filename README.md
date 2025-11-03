# British Spoken Time Converter

Convert digital time (HH:MM format) to British English spoken words.

Built with Java 17, Maven, following TDD, DDD, and clean code principles with design patterns 

## What It Does

Converts time from digital format to British English spoken format:

- **0:00** → midnight
- **12:00** → noon
- **1:00** → one o'clock
- **4:15** → quarter past four
- **7:30** → half past seven
- **9:45** → quarter to ten
- **2:05** → five past two
- **8:40** → twenty to nine
- **6:32** → six thirty two

## Quick Start

### ▶️ Compile and Run

#### **`mvn compile exec:java`**

### Run Tests
```bash
mvn test
```

### Run with Coverage Report
```bash
mvn clean test jacoco:report
# View report at: target/site/jacoco/index.html
```

### Package JAR
```bash
mvn package
java -jar target/demoBritishSpokenTime-1.0-SNAPSHOT.jar
```

## How to Use

1. **Start the application**: **`mvn compile exec:java`**
2. **Enter time** in format `HH:MM` or `H:M` (e.g., `7:30` or `15:45`)
3. **Get the result** - application converts it to British English words
4. **Type `help`** to see examples
5. **Type `exit`, `quit`, or `q`** to quit

### Example Session
```
================================
  British Spoken Time Converter
================================
Enter time in format HH:MM or H:M
Time will be converted to words
Type 'help' for available commands
Type 'q | exit' to quit

> 7:30
half past seven

> 9:45
quarter to ten

> 0:00
midnight

> exit
Goodbye!
```

## Architecture

### Domain-Driven Design (DDD)
- **Domain Layer**: `Time` value object with validation
- **Service Layer**: Parsers and converters
- **Application Layer**: Terminal UI (`Main`)

### Design Patterns
- **Strategy Pattern**: `TimeConverterService` interface with multiple implementations
  - `BritishTimeConverterService` (default)
  - Future: `AmericanTimeConverterService`, etc.
- **Chain of Responsibility**: Service orchestrates multiple converters
  - `SpecialTimeConverter`: Handles multiples of 5 (midnight, noon, o'clock, quarter, half)
  - `GeneralTimeConverter`: Fallback for other times
- **Hybrid Map-based Strategy**: Easy extensibility without modifying code

### Key Classes
- `Time`: Immutable value object (0-23 hours, 0-59 minutes)
- `TimeParser`: Parses HH:MM or H:M format
- `TimeConverterService`: Interface for conversion strategies
- `BritishTimeConverterService`: British English implementation
- `SpecialTimeConverter`: Handles special British phrases
- `GeneralTimeConverter`: Default hour-minute conversion

## Requirements
- Java 17+
- Maven 3.6+

## Development Principles
- Test-Driven Development (TDD)
- Domain-Driven Design (DDD)
- SOLID Principles
- Clean Code
- 100% test coverage goal
