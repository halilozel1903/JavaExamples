package oop;

/**
 * Demonstrates object creation, property assignment, and modern Java features.
 * Shows evolution from basic objects to modern record-based approach.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class PhoneExample {
    
    /**
     * Traditional class-based approach for Phone representation
     */
    static class Phone {
        private String model;
        private String brand;
        private double price;
        private int year;

        public Phone() {}

        public Phone(String brand, String model, int year, double price) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.price = price;
        }

        // Getters and setters
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }

        @Override
        public String toString() {
            return String.format("%s %s (%d) - $%.2f", brand, model, year, price);
        }
    }

    /**
     * Modern record-based approach (Java 14+)
     * Records provide automatic constructor, getters, equals, hashCode, and toString
     */
    record ModernPhone(String brand, String model, int year, double price) {
        
        // Compact constructor for validation
        public ModernPhone {
            if (brand == null || brand.isBlank()) {
                throw new IllegalArgumentException("Brand cannot be null or empty");
            }
            if (model == null || model.isBlank()) {
                throw new IllegalArgumentException("Model cannot be null or empty");
            }
            if (year < 1900 || year > 2030) {
                throw new IllegalArgumentException("Year must be between 1900 and 2030");
            }
            if (price < 0) {
                throw new IllegalArgumentException("Price cannot be negative");
            }
        }
        
        // Additional methods can be added to records
        public boolean isVintage() {
            return year < 2010;
        }
        
        public String getDisplayName() {
            return String.format("%s %s", brand, model);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Traditional Class-based Approach ===");
        
        // Traditional object creation with separate property assignment
        var phone1 = new Phone();
        phone1.setBrand("Samsung");
        phone1.setModel("Galaxy S23");
        phone1.setYear(2023);
        phone1.setPrice(899.99);
        
        // Using constructor
        var phone2 = new Phone("Apple", "iPhone 15", 2023, 999.99);
        
        System.out.println("Phone 1: " + phone1);
        System.out.println("Phone 2: " + phone2);
        
        System.out.println("\n=== Modern Record-based Approach ===");
        
        // Modern approach using records
        var modernPhone1 = new ModernPhone("Google", "Pixel 8", 2023, 699.99);
        var modernPhone2 = new ModernPhone("OnePlus", "11T", 2023, 549.99);
        var vintagePhone = new ModernPhone("Nokia", "3310", 2000, 50.00);
        
        System.out.println("Modern Phone 1: " + modernPhone1);
        System.out.println("Modern Phone 2: " + modernPhone2);
        System.out.println("Vintage Phone: " + vintagePhone);
        
        // Demonstrate record methods
        System.out.println("\n=== Record Features ===");
        System.out.println("Brand: " + modernPhone1.brand());
        System.out.println("Display Name: " + modernPhone1.getDisplayName());
        System.out.println("Is Vintage: " + vintagePhone.isVintage());
        
        // Demonstrate validation
        try {
            var invalidPhone = new ModernPhone("", "Invalid", 2023, -100);
        } catch (IllegalArgumentException e) {
            System.err.println("Validation Error: " + e.getMessage());
        }
        
        // Demonstrate equality (records provide automatic equals)
        var phone3 = new ModernPhone("Google", "Pixel 8", 2023, 699.99);
        System.out.println("Phone equality: " + modernPhone1.equals(phone3));
    }
}