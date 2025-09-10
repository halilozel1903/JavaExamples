package oop;

/**
 * Demonstrates constructor usage in Java with a square area calculation example.
 * Shows how constructors initialize object state and how to use them effectively.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class ConstructorExample {
    private final double side;

    /**
     * Constructor for ConstructorExample class
     * @param side The side length of the square (must be positive)
     * @throws IllegalArgumentException if side is not positive
     */
    public ConstructorExample(double side) {
        if (side <= 0) {
            throw new IllegalArgumentException("Side length must be positive");
        }
        this.side = side;
    }

    /**
     * Calculates the area of the square
     * @return The area of the square
     */
    public double calculateSquareArea() {
        return side * side;
    }

    /**
     * Calculates the perimeter of the square
     * @return The perimeter of the square
     */
    public double calculatePerimeter() {
        return 4 * side;
    }

    /**
     * Gets the side length of the square
     * @return The side length
     */
    public double getSide() {
        return side;
    }

    @Override
    public String toString() {
        return String.format("Square[side=%.2f, area=%.2f, perimeter=%.2f]", 
                           side, calculateSquareArea(), calculatePerimeter());
    }

    public static void main(String[] args) {
        try {
            // Create square objects with different side lengths
            var square1 = new ConstructorExample(9.0);
            var square2 = new ConstructorExample(5.5);
            
            System.out.println("Square 1: " + square1);
            System.out.println("Square 2: " + square2);
            
            // Demonstrate error handling
            try {
                var invalidSquare = new ConstructorExample(-5);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}