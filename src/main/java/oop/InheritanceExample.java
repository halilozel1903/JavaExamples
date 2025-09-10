package oop;

/**
 * Demonstrates inheritance, polymorphism, abstract classes, and interfaces.
 * Shows different types of inheritance and polymorphic behavior.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class InheritanceExample {

    // Abstract base class
    abstract static class Vehicle {
        protected String brand;
        protected String model;
        protected int year;
        protected double price;

        public Vehicle(String brand, String model, int year, double price) {
            this.brand = brand;
            this.model = model;
            this.year = year;
            this.price = price;
        }

        // Abstract method - must be implemented by subclasses
        public abstract void start();
        public abstract void stop();
        public abstract double getFuelEfficiency();

        // Concrete method - inherited by all subclasses
        public void displayInfo() {
            System.out.printf("%s %s (%d) - $%.2f%n", brand, model, year, price);
        }

        public int getAge() {
            return 2024 - year;
        }

        // Getters
        public String getBrand() { return brand; }
        public String getModel() { return model; }
        public int getYear() { return year; }
        public double getPrice() { return price; }
    }

    // Interface for vehicles that can fly
    interface Flyable {
        void takeOff();
        void land();
        double getMaxAltitude();
    }

    // Interface for vehicles that can float
    interface Floatable {
        void dock();
        void undock();
        double getMaxDepth();
    }

    // Car class - inherits from Vehicle
    static class Car extends Vehicle {
        private int doors;
        private String fuelType;

        public Car(String brand, String model, int year, double price, int doors, String fuelType) {
            super(brand, model, year, price);
            this.doors = doors;
            this.fuelType = fuelType;
        }

        @Override
        public void start() {
            System.out.println("Car engine started with key/button");
        }

        @Override
        public void stop() {
            System.out.println("Car engine stopped");
        }

        @Override
        public double getFuelEfficiency() {
            return switch (fuelType.toLowerCase()) {
                case "electric" -> 120.0; // MPGe
                case "hybrid" -> 50.0;
                case "diesel" -> 35.0;
                default -> 25.0; // gasoline
            };
        }

        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.printf("  Doors: %d, Fuel: %s, Efficiency: %.1f MPG%n", 
                doors, fuelType, getFuelEfficiency());
        }
    }

    // Motorcycle class - inherits from Vehicle
    static class Motorcycle extends Vehicle {
        private int engineSize;

        public Motorcycle(String brand, String model, int year, double price, int engineSize) {
            super(brand, model, year, price);
            this.engineSize = engineSize;
        }

        @Override
        public void start() {
            System.out.println("Motorcycle started with kick/electric start");
        }

        @Override
        public void stop() {
            System.out.println("Motorcycle engine stopped");
        }

        @Override
        public double getFuelEfficiency() {
            return engineSize < 500 ? 60.0 : engineSize < 1000 ? 45.0 : 35.0;
        }

        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.printf("  Engine: %dcc, Efficiency: %.1f MPG%n", 
                engineSize, getFuelEfficiency());
        }
    }

    // Airplane class - implements multiple interfaces
    static class Airplane extends Vehicle implements Flyable {
        private int passengerCapacity;
        private double maxAltitude;

        public Airplane(String brand, String model, int year, double price, 
                       int passengerCapacity, double maxAltitude) {
            super(brand, model, year, price);
            this.passengerCapacity = passengerCapacity;
            this.maxAltitude = maxAltitude;
        }

        @Override
        public void start() {
            System.out.println("Aircraft engines started - preparing for flight");
        }

        @Override
        public void stop() {
            System.out.println("Aircraft engines shut down");
        }

        @Override
        public double getFuelEfficiency() {
            return 0.3; // miles per gallon (very low for aircraft)
        }

        @Override
        public void takeOff() {
            System.out.println("Aircraft taking off - ascending to cruising altitude");
        }

        @Override
        public void land() {
            System.out.println("Aircraft landing - touching down on runway");
        }

        @Override
        public double getMaxAltitude() {
            return maxAltitude;
        }

        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.printf("  Capacity: %d passengers, Max Altitude: %.0f ft%n", 
                passengerCapacity, maxAltitude);
        }
    }

    // Boat class - implements Floatable
    static class Boat extends Vehicle implements Floatable {
        private double length;
        private String propulsionType;

        public Boat(String brand, String model, int year, double price, 
                   double length, String propulsionType) {
            super(brand, model, year, price);
            this.length = length;
            this.propulsionType = propulsionType;
        }

        @Override
        public void start() {
            System.out.println("Boat engine started - ready to navigate");
        }

        @Override
        public void stop() {
            System.out.println("Boat engine stopped");
        }

        @Override
        public double getFuelEfficiency() {
            return propulsionType.equalsIgnoreCase("sail") ? Double.MAX_VALUE : 3.0;
        }

        @Override
        public void dock() {
            System.out.println("Boat docking at marina");
        }

        @Override
        public void undock() {
            System.out.println("Boat leaving dock - heading to open water");
        }

        @Override
        public double getMaxDepth() {
            return 0; // Surface vessel
        }

        @Override
        public void displayInfo() {
            super.displayInfo();
            System.out.printf("  Length: %.1f ft, Propulsion: %s%n", 
                length, propulsionType);
        }
    }

    // Amphibious vehicle - demonstrates multiple inheritance through interfaces
    static class AmphibiousVehicle extends Vehicle implements Floatable {
        private boolean isWaterMode;

        public AmphibiousVehicle(String brand, String model, int year, double price) {
            super(brand, model, year, price);
            this.isWaterMode = false;
        }

        @Override
        public void start() {
            String mode = isWaterMode ? "water" : "land";
            System.out.println("Amphibious vehicle started in " + mode + " mode");
        }

        @Override
        public void stop() {
            System.out.println("Amphibious vehicle stopped");
        }

        @Override
        public double getFuelEfficiency() {
            return isWaterMode ? 5.0 : 15.0;
        }

        @Override
        public void dock() {
            System.out.println("Amphibious vehicle docking");
            isWaterMode = false;
        }

        @Override
        public void undock() {
            System.out.println("Amphibious vehicle entering water");
            isWaterMode = true;
        }

        @Override
        public double getMaxDepth() {
            return 10.0; // Can go slightly underwater
        }

        public void switchMode() {
            isWaterMode = !isWaterMode;
            String newMode = isWaterMode ? "water" : "land";
            System.out.println("Switched to " + newMode + " mode");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== INHERITANCE AND POLYMORPHISM DEMO ===\n");

        // Create different types of vehicles
        Vehicle[] vehicles = {
            new Car("Toyota", "Camry", 2023, 28000, 4, "Hybrid"),
            new Car("Tesla", "Model S", 2023, 89000, 4, "Electric"),
            new Motorcycle("Yamaha", "R1", 2023, 17000, 998),
            new Airplane("Boeing", "737", 2020, 89000000, 180, 35000),
            new Boat("Sea Ray", "Sundancer", 2022, 150000, 35.0, "Motor"),
            new AmphibiousVehicle("DUKW", "Duck", 2021, 75000)
        };

        // Demonstrate polymorphism - same method call, different behavior
        System.out.println("=== POLYMORPHIC BEHAVIOR ===");
        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
            vehicle.start();
            System.out.printf("Fuel efficiency: %.1f MPG%n", vehicle.getFuelEfficiency());
            vehicle.stop();
            System.out.println();
        }

        // Demonstrate interface-specific behavior
        System.out.println("=== INTERFACE-SPECIFIC BEHAVIOR ===");
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof Flyable flyable) {
                System.out.println(vehicle.getBrand() + " " + vehicle.getModel() + " can fly!");
                flyable.takeOff();
                System.out.println("Max altitude: " + flyable.getMaxAltitude() + " ft");
                flyable.land();
                System.out.println();
            }
            
            if (vehicle instanceof Floatable floatable) {
                System.out.println(vehicle.getBrand() + " " + vehicle.getModel() + " can float!");
                floatable.undock();
                System.out.println("Max depth: " + floatable.getMaxDepth() + " ft");
                floatable.dock();
                System.out.println();
            }
        }

        // Demonstrate special behavior for amphibious vehicle
        System.out.println("=== AMPHIBIOUS VEHICLE DEMO ===");
        for (Vehicle vehicle : vehicles) {
            if (vehicle instanceof AmphibiousVehicle amphibious) {
                amphibious.switchMode();
                amphibious.start();
                amphibious.undock();
                amphibious.switchMode();
                amphibious.start();
                System.out.println();
            }
        }

        // Demonstrate method overriding and super calls
        System.out.println("=== VEHICLE AGES ===");
        for (Vehicle vehicle : vehicles) {
            System.out.printf("%s %s is %d years old%n", 
                vehicle.getBrand(), vehicle.getModel(), vehicle.getAge());
        }
    }
}