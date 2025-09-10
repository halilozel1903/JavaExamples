package collections;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Demonstrates Java Collections Framework with practical examples.
 * Shows usage of List, Set, Map and modern operations.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class CollectionsExample {
    
    record Student(int id, String name, double grade, String department) {
        public Student {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            if (grade < 0 || grade > 100) {
                throw new IllegalArgumentException("Grade must be between 0 and 100");
            }
        }
        
        public boolean isPassing() {
            return grade >= 60;
        }
    }

    public static void main(String[] args) {
        demonstrateLists();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateSets();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateMaps();
        System.out.println("\n" + "=".repeat(60) + "\n");
        demonstrateAdvancedOperations();
    }

    /**
     * Demonstrates List operations (ArrayList, LinkedList)
     */
    private static void demonstrateLists() {
        System.out.println("=== LIST OPERATIONS ===");
        
        // ArrayList - good for random access
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Java");
        arrayList.add("Python");
        arrayList.add("JavaScript");
        arrayList.add("Python"); // Duplicates allowed
        
        System.out.println("ArrayList: " + arrayList);
        System.out.println("Size: " + arrayList.size());
        System.out.println("Contains Python: " + arrayList.contains("Python"));
        System.out.println("Index of Python: " + arrayList.indexOf("Python"));
        System.out.println("Last index of Python: " + arrayList.lastIndexOf("Python"));
        
        // LinkedList - good for insertion/deletion
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.addFirst(1);
        linkedList.addLast(3);
        linkedList.add(1, 2); // Insert at index 1
        
        System.out.println("LinkedList: " + linkedList);
        System.out.println("First: " + linkedList.getFirst());
        System.out.println("Last: " + linkedList.getLast());
        
        // Modern iteration using enhanced for loop and forEach
        System.out.print("Iteration using forEach: ");
        arrayList.forEach(item -> System.out.print(item + " "));
        System.out.println();
    }

    /**
     * Demonstrates Set operations (HashSet, TreeSet, LinkedHashSet)
     */
    private static void demonstrateSets() {
        System.out.println("=== SET OPERATIONS ===");
        
        // HashSet - no order, fastest lookup
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // Duplicate ignored
        
        System.out.println("HashSet: " + hashSet);
        
        // TreeSet - sorted order
        Set<String> treeSet = new TreeSet<>();
        treeSet.add("Zebra");
        treeSet.add("Apple");
        treeSet.add("Banana");
        
        System.out.println("TreeSet (sorted): " + treeSet);
        
        // LinkedHashSet - maintains insertion order
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("First");
        linkedHashSet.add("Second");
        linkedHashSet.add("Third");
        
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);
        
        // Set operations
        Set<String> set1 = Set.of("A", "B", "C");
        Set<String> set2 = Set.of("B", "C", "D");
        
        // Union
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println("Union: " + union);
        
        // Intersection
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println("Intersection: " + intersection);
    }

    /**
     * Demonstrates Map operations (HashMap, TreeMap, LinkedHashMap)
     */
    private static void demonstrateMaps() {
        System.out.println("=== MAP OPERATIONS ===");
        
        // HashMap - no order, fastest lookup
        Map<String, Integer> populationMap = new HashMap<>();
        populationMap.put("Turkey", 84_000_000);
        populationMap.put("Germany", 83_000_000);
        populationMap.put("France", 67_000_000);
        populationMap.put("Italy", 60_000_000);
        
        System.out.println("Population Map: " + populationMap);
        System.out.println("Turkey population: " + populationMap.get("Turkey"));
        System.out.println("Contains Spain: " + populationMap.containsKey("Spain"));
        
        // TreeMap - sorted by keys
        Map<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("C", "Charlie");
        sortedMap.put("A", "Alpha");
        sortedMap.put("B", "Beta");
        
        System.out.println("TreeMap (sorted by keys): " + sortedMap);
        
        // Modern iteration
        System.out.println("Iterating with forEach:");
        populationMap.forEach((country, population) -> 
            System.out.printf("  %s: %,d%n", country, population));
        
        // Working with entries
        System.out.println("Countries with population > 70M:");
        populationMap.entrySet().stream()
            .filter(entry -> entry.getValue() > 70_000_000)
            .forEach(entry -> System.out.printf("  %s: %,d%n", 
                entry.getKey(), entry.getValue()));
    }

    /**
     * Demonstrates advanced collection operations with Stream API
     */
    private static void demonstrateAdvancedOperations() {
        System.out.println("=== ADVANCED OPERATIONS WITH STREAMS ===");
        
        List<Student> students = List.of(
            new Student(1, "Ali", 85.5, "Computer Science"),
            new Student(2, "Ayşe", 92.0, "Mathematics"),
            new Student(3, "Mehmet", 78.5, "Computer Science"),
            new Student(4, "Fatma", 65.0, "Physics"),
            new Student(5, "Ahmet", 45.0, "Mathematics"),
            new Student(6, "Zeynep", 88.0, "Physics")
        );
        
        // Filter and collect
        var passingStudents = students.stream()
            .filter(Student::isPassing)
            .collect(Collectors.toList());
        
        System.out.println("Passing students:");
        passingStudents.forEach(System.out::println);
        
        // Group by department
        var studentsByDepartment = students.stream()
            .collect(Collectors.groupingBy(Student::department));
        
        System.out.println("\nStudents by department:");
        studentsByDepartment.forEach((dept, studentList) -> {
            System.out.println(dept + ": " + studentList.size() + " students");
            studentList.forEach(student -> System.out.println("  " + student.name()));
        });
        
        // Calculate average grade by department
        var avgGradeByDept = students.stream()
            .collect(Collectors.groupingBy(
                Student::department,
                Collectors.averagingDouble(Student::grade)
            ));
        
        System.out.println("\nAverage grade by department:");
        avgGradeByDept.forEach((dept, avg) -> 
            System.out.printf("  %s: %.2f%n", dept, avg));
        
        // Find top student
        var topStudent = students.stream()
            .max(Comparator.comparing(Student::grade));
        
        topStudent.ifPresent(student -> 
            System.out.println("\nTop student: " + student));
        
        // Count students by passing status
        var passingCount = students.stream()
            .collect(Collectors.partitioningBy(
                Student::isPassing,
                Collectors.counting()
            ));
        
        System.out.println("\nPassing status count:");
        System.out.println("  Passing: " + passingCount.get(true));
        System.out.println("  Failing: " + passingCount.get(false));
    }
}