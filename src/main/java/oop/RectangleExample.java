package oop;

/**
 * Demonstrates basic class structure and encapsulation with a Rectangle example.
 * Shows proper use of private fields, getters, setters, and validation.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class RectangleExample {
    private double width;
    private double height;

    /**
     * Default constructor creating a unit rectangle
     */
    public RectangleExample() {
        this(1.0, 1.0);
    }

    /**
     * Parameterized constructor
     * @param width The width of the rectangle
     * @param height The height of the rectangle
     */
    public RectangleExample(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Gets the width of the rectangle
     * @return The width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the rectangle
     * @param width The width (must be positive)
     * @throws IllegalArgumentException if width is not positive
     */
    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        this.width = width;
    }

    /**
     * Gets the height of the rectangle
     * @return The height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle
     * @param height The height (must be positive)
     * @throws IllegalArgumentException if height is not positive
     */
    public void setHeight(double height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }
        this.height = height;
    }

    /**
     * Calculates the area of the rectangle
     * @return The area
     */
    public double calculateArea() {
        return width * height;
    }

    /**
     * Calculates the perimeter of the rectangle
     * @return The perimeter
     */
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    /**
     * Checks if this rectangle is a square
     * @return true if width equals height
     */
    public boolean isSquare() {
        return Math.abs(width - height) < 0.001; // Using epsilon for double comparison
    }

    @Override
    public String toString() {
        return String.format("Rectangle[width=%.2f, height=%.2f, area=%.2f, perimeter=%.2f%s]", 
                           width, height, calculateArea(), calculatePerimeter(),
                           isSquare() ? ", square=true" : "");
    }

    public static void main(String[] args) {
        // Create rectangles using different constructors
        var rectangle1 = new RectangleExample(6.0, 9.0);
        var rectangle2 = new RectangleExample(5.0, 10.0);
        var square = new RectangleExample(4.0, 4.0);

        System.out.println("Rectangle 1: " + rectangle1);
        System.out.println("Rectangle 2: " + rectangle2);
        System.out.println("Square: " + square);

        // Demonstrate setters with validation
        try {
            rectangle1.setWidth(8.0);
            rectangle1.setHeight(12.0);
            System.out.println("Updated Rectangle 1: " + rectangle1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Test error handling
        try {
            var invalidRectangle = new RectangleExample(-5, 3);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating invalid rectangle: " + e.getMessage());
        }
    }
}