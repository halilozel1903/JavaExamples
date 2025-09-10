package basic;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Demonstrates comprehensive enum usage in Java including
 * basic enums, enums with fields and methods, and advanced patterns.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class EnumExample {

    // Basic enum
    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    // Enum with fields and methods
    enum Planet {
        MERCURY(3.303e+23, 2.4397e6),
        VENUS(4.869e+24, 6.0518e6),
        EARTH(5.976e+24, 6.37814e6),
        MARS(6.421e+23, 3.3972e6),
        JUPITER(1.9e+27, 7.1492e7),
        SATURN(5.688e+26, 6.0268e7),
        URANUS(8.686e+25, 2.5559e7),
        NEPTUNE(1.024e+26, 2.4746e7);

        private final double mass;   // in kilograms
        private final double radius; // in meters

        Planet(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
        }

        public double getMass() { return mass; }
        public double getRadius() { return radius; }

        // Universal gravitational constant (m3 kg-1 s-2)
        public static final double G = 6.67300E-11;

        public double surfaceGravity() {
            return G * mass / (radius * radius);
        }

        public double surfaceWeight(double otherMass) {
            return otherMass * surfaceGravity();
        }
    }

    // Enum with abstract methods
    enum Operation {
        PLUS("+") {
            @Override
            public double apply(double x, double y) {
                return x + y;
            }
        },
        MINUS("-") {
            @Override
            public double apply(double x, double y) {
                return x - y;
            }
        },
        MULTIPLY("*") {
            @Override
            public double apply(double x, double y) {
                return x * y;
            }
        },
        DIVIDE("/") {
            @Override
            public double apply(double x, double y) {
                if (y == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return x / y;
            }
        },
        POWER("^") {
            @Override
            public double apply(double x, double y) {
                return Math.pow(x, y);
            }
        };

        private final String symbol;

        Operation(String symbol) {
            this.symbol = symbol;
        }

        public String getSymbol() {
            return symbol;
        }

        public abstract double apply(double x, double y);

        @Override
        public String toString() {
            return symbol;
        }

        // Static method to get operation by symbol
        public static Operation fromSymbol(String symbol) {
            return Arrays.stream(values())
                    .filter(op -> op.symbol.equals(symbol))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown operation: " + symbol));
        }
    }

    // Enum representing different priority levels
    enum Priority {
        LOW(1, "Low Priority", "#28a745"),
        MEDIUM(2, "Medium Priority", "#ffc107"),
        HIGH(3, "High Priority", "#fd7e14"),
        CRITICAL(4, "Critical Priority", "#dc3545");

        private final int level;
        private final String description;
        private final String color;

        Priority(int level, String description, String color) {
            this.level = level;
            this.description = description;
            this.color = color;
        }

        public int getLevel() { return level; }
        public String getDescription() { return description; }
        public String getColor() { return color; }

        public boolean isHigherThan(Priority other) {
            return this.level > other.level;
        }

        public static Priority fromLevel(int level) {
            return Arrays.stream(values())
                    .filter(p -> p.level == level)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid priority level: " + level));
        }
    }

    // Enum implementing interface
    interface Describable {
        String getDescription();
    }

    enum Status implements Describable {
        PENDING("Task is waiting to be processed"),
        IN_PROGRESS("Task is currently being worked on"),
        COMPLETED("Task has been finished successfully"),
        CANCELLED("Task has been cancelled"),
        FAILED("Task has failed during execution");

        private final String description;

        Status(String description) {
            this.description = description;
        }

        @Override
        public String getDescription() {
            return description;
        }

        public boolean isActive() {
            return this == PENDING || this == IN_PROGRESS;
        }

        public boolean isTerminal() {
            return this == COMPLETED || this == CANCELLED || this == FAILED;
        }
    }

    // Example class using enums
    static class Task {
        private final String title;
        private Status status;
        private Priority priority;

        public Task(String title, Priority priority) {
            this.title = title;
            this.priority = priority;
            this.status = Status.PENDING;
        }

        public void start() {
            if (status == Status.PENDING) {
                status = Status.IN_PROGRESS;
                System.out.println("Task '" + title + "' started");
            } else {
                System.out.println("Cannot start task in " + status + " status");
            }
        }

        public void complete() {
            if (status == Status.IN_PROGRESS) {
                status = Status.COMPLETED;
                System.out.println("Task '" + title + "' completed");
            } else {
                System.out.println("Cannot complete task in " + status + " status");
            }
        }

        public void cancel() {
            if (status.isActive()) {
                status = Status.CANCELLED;
                System.out.println("Task '" + title + "' cancelled");
            } else {
                System.out.println("Cannot cancel task in " + status + " status");
            }
        }

        @Override
        public String toString() {
            return String.format("Task[title='%s', status=%s, priority=%s]", 
                               title, status, priority);
        }

        public Status getStatus() { return status; }
        public Priority getPriority() { return priority; }
        public String getTitle() { return title; }
    }

    public static void main(String[] args) {
        demonstrateBasicEnums();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateEnumsWithFields();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateEnumsWithMethods();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateEnumCollections();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateEnumInSwitchStatements();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateTaskManagement();
    }

    private static void demonstrateBasicEnums() {
        System.out.println("=== BASIC ENUM USAGE ===");

        // Basic enum usage
        Day today = Day.FRIDAY;
        System.out.println("Today is: " + today);
        System.out.println("Ordinal (position): " + today.ordinal());
        System.out.println("Name: " + today.name());

        // Enum comparison
        if (today == Day.FRIDAY) {
            System.out.println("It's Friday!");
        }

        // All enum values
        System.out.println("All days:");
        for (Day day : Day.values()) {
            System.out.println("  " + day + " (ordinal: " + day.ordinal() + ")");
        }

        // Enum.valueOf()
        try {
            Day parsed = Day.valueOf("MONDAY");
            System.out.println("Parsed day: " + parsed);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid day name");
        }
    }

    private static void demonstrateEnumsWithFields() {
        System.out.println("=== ENUMS WITH FIELDS AND METHODS ===");

        double earthWeight = 70.0; // kg

        System.out.printf("Weight on Earth: %.2f kg%n", earthWeight);
        System.out.println("Weight on other planets:");

        for (Planet planet : Planet.values()) {
            double weight = planet.surfaceWeight(earthWeight);
            System.out.printf("  %-10s: %6.2f kg (gravity: %.2f m/s²)%n", 
                            planet, weight, planet.surfaceGravity());
        }

        // Planet with highest gravity
        Planet maxGravityPlanet = Arrays.stream(Planet.values())
                .max(Comparator.comparing(Planet::surfaceGravity))
                .orElse(null);

        if (maxGravityPlanet != null) {
            System.out.printf("Planet with highest gravity: %s (%.2f m/s²)%n", 
                            maxGravityPlanet, maxGravityPlanet.surfaceGravity());
        }
    }

    private static void demonstrateEnumsWithMethods() {
        System.out.println("=== ENUMS WITH ABSTRACT METHODS ===");

        double x = 10.0;
        double y = 3.0;

        System.out.printf("Calculations with x=%.1f and y=%.1f:%n", x, y);

        for (Operation op : Operation.values()) {
            try {
                double result = op.apply(x, y);
                System.out.printf("  %.1f %s %.1f = %.2f%n", x, op.getSymbol(), y, result);
            } catch (ArithmeticException e) {
                System.out.printf("  %.1f %s %.1f = Error: %s%n", x, op.getSymbol(), y, e.getMessage());
            }
        }

        // Using operation from symbol
        try {
            Operation multiply = Operation.fromSymbol("*");
            System.out.printf("Operation for '*': %s%n", multiply);
            System.out.printf("5 * 8 = %.1f%n", multiply.apply(5, 8));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void demonstrateEnumCollections() {
        System.out.println("=== ENUM COLLECTIONS ===");

        // EnumSet - efficient set implementation for enums
        EnumSet<Day> weekdays = EnumSet.range(Day.MONDAY, Day.FRIDAY);
        EnumSet<Day> weekend = EnumSet.of(Day.SATURDAY, Day.SUNDAY);
        EnumSet<Day> allDays = EnumSet.allOf(Day.class);

        System.out.println("Weekdays: " + weekdays);
        System.out.println("Weekend: " + weekend);
        System.out.println("All days: " + allDays);

        // EnumMap - efficient map implementation for enums
        EnumMap<Priority, Integer> taskCounts = new EnumMap<>(Priority.class);
        taskCounts.put(Priority.LOW, 5);
        taskCounts.put(Priority.MEDIUM, 3);
        taskCounts.put(Priority.HIGH, 2);
        taskCounts.put(Priority.CRITICAL, 1);

        System.out.println("Task counts by priority:");
        taskCounts.forEach((priority, count) -> 
            System.out.printf("  %s: %d tasks%n", priority.getDescription(), count));

        // Statistics
        int totalTasks = taskCounts.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total tasks: " + totalTasks);
    }

    private static void demonstrateEnumInSwitchStatements() {
        System.out.println("=== ENUM IN SWITCH STATEMENTS ===");

        Priority[] priorities = {Priority.LOW, Priority.MEDIUM, Priority.HIGH, Priority.CRITICAL};

        for (Priority priority : priorities) {
            String action = switch (priority) {
                case LOW -> "Schedule for later";
                case MEDIUM -> "Add to regular queue";
                case HIGH -> "Prioritize in queue";
                case CRITICAL -> "Handle immediately";
            };

            System.out.printf("%s: %s%n", priority.getDescription(), action);
        }

        // Modern switch expressions with enums
        Status[] statuses = Status.values();
        for (Status status : statuses) {
            String icon = switch (status) {
                case PENDING -> "⏳";
                case IN_PROGRESS -> "🔄";
                case COMPLETED -> "✅";
                case CANCELLED -> "❌";
                case FAILED -> "💥";
            };

            System.out.printf("%s %s: %s%n", icon, status, status.getDescription());
        }
    }

    private static void demonstrateTaskManagement() {
        System.out.println("=== TASK MANAGEMENT WITH ENUMS ===");

        List<Task> tasks = List.of(
            new Task("Fix critical bug", Priority.CRITICAL),
            new Task("Update documentation", Priority.LOW),
            new Task("Implement new feature", Priority.HIGH),
            new Task("Code review", Priority.MEDIUM),
            new Task("Deploy to production", Priority.CRITICAL)
        );

        System.out.println("Initial tasks:");
        tasks.forEach(System.out::println);

        // Simulate task processing
        System.out.println("\nProcessing tasks...");
        for (Task task : tasks) {
            task.start();
            if (task.getPriority() == Priority.CRITICAL) {
                task.complete();
            } else if (task.getPriority() == Priority.LOW) {
                task.cancel();
            }
        }

        // Group tasks by status
        Map<Status, List<Task>> tasksByStatus = tasks.stream()
            .collect(Collectors.groupingBy(Task::getStatus));

        System.out.println("\nTasks by status:");
        tasksByStatus.forEach((status, taskList) -> {
            System.out.printf("%s (%d tasks):%n", status.getDescription(), taskList.size());
            taskList.forEach(task -> System.out.println("  " + task.getTitle()));
        });

        // Count active vs terminal tasks
        long activeCount = tasks.stream().filter(task -> task.getStatus().isActive()).count();
        long terminalCount = tasks.stream().filter(task -> task.getStatus().isTerminal()).count();

        System.out.printf("\nActive tasks: %d, Terminal tasks: %d%n", activeCount, terminalCount);
    }
}