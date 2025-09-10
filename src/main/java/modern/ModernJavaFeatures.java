package modern;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Demonstrates modern Java features including Lambda expressions, 
 * Stream API, Optional, and functional programming concepts.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class ModernJavaFeatures {
    
    // Functional interfaces for demonstration
    @FunctionalInterface
    interface Calculator {
        double calculate(double a, double b);
    }
    
    record Product(String name, String category, double price, int stock) {
        public boolean isInStock() {
            return stock > 0;
        }
        
        public boolean isExpensive() {
            return price > 100.0;
        }
    }

    public static void main(String[] args) {
        demonstrateLambdaExpressions();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateStreamAPI();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateOptional();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateFunctionalInterfaces();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateMethodReferences();
    }

    /**
     * Demonstrates Lambda expressions and their usage
     */
    private static void demonstrateLambdaExpressions() {
        System.out.println("=== LAMBDA EXPRESSIONS ===");
        
        List<String> names = List.of("Ali", "Ayşe", "Mehmet", "Fatma", "Ahmet");
        
        // Traditional approach vs Lambda
        System.out.println("Original list: " + names);
        
        // Sorting with lambda
        var sortedNames = names.stream()
            .sorted((a, b) -> a.compareToIgnoreCase(b))
            .collect(Collectors.toList());
        System.out.println("Sorted: " + sortedNames);
        
        // Filtering with lambda
        var filteredNames = names.stream()
            .filter(name -> name.startsWith("A"))
            .collect(Collectors.toList());
        System.out.println("Names starting with 'A': " + filteredNames);
        
        // Custom calculator using lambda
        Calculator add = (a, b) -> a + b;
        Calculator multiply = (a, b) -> a * b;
        Calculator divide = (a, b) -> b != 0 ? a / b : 0;
        
        System.out.println("10 + 5 = " + add.calculate(10, 5));
        System.out.println("10 * 5 = " + multiply.calculate(10, 5));
        System.out.println("10 / 5 = " + divide.calculate(10, 5));
        
        // Lambda with multiple statements
        Consumer<String> printer = message -> {
            System.out.println("Processing: " + message);
            System.out.println("Length: " + message.length());
        };
        
        printer.accept("Hello Lambda!");
    }

    /**
     * Demonstrates Stream API operations
     */
    private static void demonstrateStreamAPI() {
        System.out.println("=== STREAM API ===");
        
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 899.99, 5),
            new Product("Phone", "Electronics", 699.99, 12),
            new Product("Book", "Education", 29.99, 50),
            new Product("Headphones", "Electronics", 149.99, 0),
            new Product("Notebook", "Education", 5.99, 100),
            new Product("Tablet", "Electronics", 399.99, 8)
        );
        
        System.out.println("All products:");
        products.forEach(System.out::println);
        
        // Filter and map operations
        var expensiveElectronics = products.stream()
            .filter(p -> "Electronics".equals(p.category()))
            .filter(Product::isExpensive)
            .filter(Product::isInStock)
            .map(p -> p.name() + " ($" + p.price() + ")")
            .collect(Collectors.toList());
        
        System.out.println("\nExpensive Electronics in stock:");
        expensiveElectronics.forEach(System.out::println);
        
        // Grouping operations
        var productsByCategory = products.stream()
            .collect(Collectors.groupingBy(Product::category));
        
        System.out.println("\nProducts by category:");
        productsByCategory.forEach((category, productList) -> {
            System.out.println(category + ":");
            productList.forEach(p -> System.out.println("  " + p.name()));
        });
        
        // Statistical operations
        var priceStats = products.stream()
            .mapToDouble(Product::price)
            .summaryStatistics();
        
        System.out.println("\nPrice Statistics:");
        System.out.printf("  Count: %d%n", priceStats.getCount());
        System.out.printf("  Average: $%.2f%n", priceStats.getAverage());
        System.out.printf("  Min: $%.2f%n", priceStats.getMin());
        System.out.printf("  Max: $%.2f%n", priceStats.getMax());
        System.out.printf("  Sum: $%.2f%n", priceStats.getSum());
        
        // Parallel stream for performance
        var totalValue = products.parallelStream()
            .mapToDouble(p -> p.price() * p.stock())
            .sum();
        
        System.out.printf("\nTotal inventory value: $%.2f%n", totalValue);
        
        // Infinite streams
        System.out.println("\nFirst 10 even numbers:");
        IntStream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(n -> System.out.print(n + " "));
        System.out.println();
    }

    /**
     * Demonstrates Optional class usage
     */
    private static void demonstrateOptional() {
        System.out.println("=== OPTIONAL CLASS ===");
        
        List<Product> products = List.of(
            new Product("Laptop", "Electronics", 899.99, 5),
            new Product("Book", "Education", 29.99, 50)
        );
        
        // Finding elements safely
        Optional<Product> expensiveProduct = products.stream()
            .filter(Product::isExpensive)
            .findFirst();
        
        // Different ways to handle Optional
        if (expensiveProduct.isPresent()) {
            System.out.println("Found expensive product: " + expensiveProduct.get().name());
        }
        
        // Using ifPresent
        expensiveProduct.ifPresent(p -> 
            System.out.println("Expensive product price: $" + p.price()));
        
        // Using orElse
        Product cheapProduct = products.stream()
            .filter(p -> p.price() < 10)
            .findFirst()
            .orElse(new Product("Default", "Unknown", 0, 0));
        
        System.out.println("Cheap product: " + cheapProduct.name());
        
        // Using orElseThrow
        try {
            Product phone = products.stream()
                .filter(p -> "Phone".equals(p.name()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Phone not found"));
            System.out.println("Found phone: " + phone.name());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        // Creating Optionals
        Optional<String> emptyOptional = Optional.empty();
        Optional<String> nullableOptional = Optional.ofNullable(null);
        Optional<String> valueOptional = Optional.of("Hello");
        
        System.out.println("Empty optional is present: " + emptyOptional.isPresent());
        System.out.println("Nullable optional is present: " + nullableOptional.isPresent());
        System.out.println("Value optional: " + valueOptional.orElse("default"));
        
        // Chaining operations
        String result = Optional.of("  hello world  ")
            .map(String::trim)
            .map(String::toUpperCase)
            .filter(s -> s.length() > 5)
            .orElse("default");
        
        System.out.println("Chained result: " + result);
    }

    /**
     * Demonstrates built-in functional interfaces
     */
    private static void demonstrateFunctionalInterfaces() {
        System.out.println("=== FUNCTIONAL INTERFACES ===");
        
        // Predicate - tests a condition
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Predicate<Integer> isPositive = n -> n > 0;
        
        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 4 positive and even? " + 
            isPositive.and(isEven).test(4));
        
        // Function - transforms input to output
        Function<String, Integer> stringLength = String::length;
        Function<Integer, String> intToString = Object::toString;
        
        System.out.println("Length of 'Hello': " + stringLength.apply("Hello"));
        
        // Chaining functions
        Function<String, String> lengthAsString = stringLength.andThen(intToString);
        System.out.println("Length as string: " + lengthAsString.apply("World"));
        
        // Consumer - accepts input, returns nothing
        Consumer<String> printer = System.out::println;
        Consumer<String> upperPrinter = s -> System.out.println(s.toUpperCase());
        
        printer.accept("Normal text");
        upperPrinter.accept("Upper text");
        
        // Chaining consumers
        Consumer<String> combinedPrinter = printer.andThen(upperPrinter);
        combinedPrinter.accept("Both versions");
        
        // Supplier - provides a value
        Supplier<String> randomGreeting = () -> {
            String[] greetings = {"Hello", "Hi", "Hey", "Greetings"};
            return greetings[new Random().nextInt(greetings.length)];
        };
        
        System.out.println("Random greeting: " + randomGreeting.get());
        
        // BiFunction - two inputs, one output
        BiFunction<Integer, Integer, Integer> max = Integer::max;
        System.out.println("Max of 10 and 20: " + max.apply(10, 20));
    }

    /**
     * Demonstrates method references
     */
    private static void demonstrateMethodReferences() {
        System.out.println("=== METHOD REFERENCES ===");
        
        List<String> names = List.of("alice", "bob", "charlie", "diana");
        
        // Static method reference
        names.stream()
            .map(String::toUpperCase)  // equivalent to s -> s.toUpperCase()
            .forEach(System.out::println);  // equivalent to s -> System.out.println(s)
        
        // Instance method reference
        String prefix = "Name: ";
        Function<String, String> addPrefix = prefix::concat;  // equivalent to s -> prefix.concat(s)
        
        names.stream()
            .map(addPrefix)
            .forEach(System.out::println);
        
        // Constructor reference
        Supplier<List<String>> listSupplier = ArrayList::new;  // equivalent to () -> new ArrayList<>()
        List<String> newList = listSupplier.get();
        
        // Method reference with instance method of arbitrary object
        names.stream()
            .sorted(String::compareToIgnoreCase)  // equivalent to (a, b) -> a.compareToIgnoreCase(b)
            .forEach(System.out::println);
    }
}