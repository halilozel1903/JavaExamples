# Java ☕️ Examples 📱

A comprehensive collection of Java examples demonstrating various programming concepts from basic to advanced level. This repository is designed to help developers learn Java programming through practical examples.

## About Java

`Java` is a high-level, class-based, Object-Oriented Programming (OOP) language that is designed to have as few implementation dependencies as possible. It follows the principle of "write once, run anywhere" (WORA).

## Repository Structure 📁

```
src/main/java/
├── basic/          # Basic Java concepts and syntax
├── oop/           # Object-Oriented Programming examples
├── collections/   # Java Collections Framework
├── modern/        # Modern Java features (Java 8+)
└── advanced/      # Advanced concepts and patterns
```

## Examples Categories 📚

### Basic Java Concepts (`basic` package)
- **EnumExample.java** - Comprehensive enum usage including basic enums, enums with fields/methods, abstract methods, and collections

### Object-Oriented Programming (`oop` package)
- **ConstructorExample.java** - Constructor usage with validation and modern features
- **RectangleExample.java** - Encapsulation, getters/setters, and proper validation
- **PhoneExample.java** - Traditional classes vs modern records, object creation patterns
- **InheritanceExample.java** - Inheritance, polymorphism, abstract classes, and interfaces

### Collections Framework (`collections` package)
- **CollectionsExample.java** - List, Set, Map operations with Stream API integration

### Modern Java Features (`modern` package)
- **ModernJavaFeatures.java** - Lambda expressions, Stream API, Optional, functional interfaces, method references

### Advanced Concepts (`advanced` package)
- **ExceptionHandlingExample.java** - Try-catch-finally, custom exceptions, try-with-resources, exception chaining
- **FileIOExample.java** - File operations, NIO.2, directory handling, text processing, log file analysis

### Legacy Examples (`legacy` directory)
- Original simple examples for comparison

## Getting Started 🚀

### Prerequisites
- Java Development Kit (JDK) 17 or higher
- Maven 3.6+ (optional, for dependency management)
- Any text editor or IDE (IntelliJ IDEA, Eclipse, VS Code)

### Quick Start
```bash
# Clone the repository
git clone https://github.com/halilozel1903/JavaExamples.git
cd JavaExamples

# Run all examples (interactive)
./run-examples.sh

# Or run individual examples
mvn compile
mvn exec:java -Dexec.mainClass="oop.ConstructorExample"
```

### Running Examples Manually
```bash
# Compile Java files
javac -d target/classes src/main/java/category/ExampleFile.java

# Run the compiled class
java -cp target/classes category.ExampleFile
```

### Using Maven
```bash
# Compile all examples
mvn compile

# Run a specific example
mvn exec:java -Dexec.mainClass="category.ExampleFile"

# Clean and rebuild
mvn clean compile
```

## Features Demonstrated 🎯

- **Modern Java Syntax** - Records, switch expressions, var keyword, text blocks
- **Best Practices** - Proper encapsulation, validation, error handling
- **Design Patterns** - Factory, Builder, Strategy patterns through examples
- **Functional Programming** - Lambdas, streams, functional interfaces
- **Collection Operations** - Modern collection manipulation with Stream API
- **Exception Safety** - Comprehensive error handling patterns

## Contributing 🤝

Feel free to contribute by adding new examples or improving existing ones. Please ensure your code:
- Follows Java naming conventions
- Includes proper comments and documentation
- Compiles and runs without errors
- Demonstrates the concept clearly

## License ℹ️
```
MIT License

Copyright (c) 2024 Halil OZEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
