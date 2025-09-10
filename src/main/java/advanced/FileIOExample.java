package advanced;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Demonstrates comprehensive file I/O operations in Java including
 * traditional File API, modern NIO.2, and various reading/writing techniques.
 * 
 * @author Halil OZEL
 * @version 2.0
 * @since 2024
 */
public class FileIOExample {
    
    private static final String TEMP_DIR = "/tmp/java-io-examples";
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    record LogEntry(LocalDateTime timestamp, String level, String message) {
        @Override
        public String toString() {
            return String.format("%s [%s] %s", 
                timestamp.format(TIME_FORMATTER), level, message);
        }
        
        public static LogEntry fromString(String line) {
            String[] parts = line.split(" \\[|\\] ", 3);
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid log format: " + line);
            }
            return new LogEntry(
                LocalDateTime.parse(parts[0], TIME_FORMATTER),
                parts[1],
                parts[2]
            );
        }
    }

    public static void main(String[] args) {
        try {
            setupDirectories();
            
            demonstrateBasicFileOperations();
            System.out.println("\n" + "=".repeat(60) + "\n");
            
            demonstrateTextFileOperations();
            System.out.println("\n" + "=".repeat(60) + "\n");
            
            demonstrateNIOOperations();
            System.out.println("\n" + "=".repeat(60) + "\n");
            
            demonstrateDirectoryOperations();
            System.out.println("\n" + "=".repeat(60) + "\n");
            
            demonstrateLogFileProcessing();
            
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup();
        }
    }
    
    private static void setupDirectories() throws IOException {
        Path tempPath = Paths.get(TEMP_DIR);
        if (Files.exists(tempPath)) {
            // Clean existing directory
            try (Stream<Path> paths = Files.walk(tempPath)) {
                paths.sorted(Comparator.reverseOrder())
                     .forEach(path -> {
                         try {
                             Files.delete(path);
                         } catch (IOException e) {
                             System.err.println("Failed to delete: " + path);
                         }
                     });
            }
        }
        Files.createDirectories(tempPath);
        System.out.println("Created temp directory: " + tempPath);
    }
    
    /**
     * Demonstrates basic file creation, writing, and reading
     */
    private static void demonstrateBasicFileOperations() throws IOException {
        System.out.println("=== BASIC FILE OPERATIONS ===");
        
        Path filePath = Paths.get(TEMP_DIR, "basic-example.txt");
        String content = "Hello, World!\nThis is a Java file I/O example.\nCreated at: " + 
                        LocalDateTime.now().format(TIME_FORMATTER);
        
        // Writing to file
        Files.write(filePath, content.getBytes(), StandardOpenOption.CREATE);
        System.out.println("File created: " + filePath);
        
        // Reading from file
        String readContent = Files.readString(filePath);
        System.out.println("File content:");
        System.out.println(readContent);
        
        // File properties
        System.out.println("\nFile properties:");
        System.out.println("  Size: " + Files.size(filePath) + " bytes");
        System.out.println("  Readable: " + Files.isReadable(filePath));
        System.out.println("  Writable: " + Files.isWritable(filePath));
        System.out.println("  Executable: " + Files.isExecutable(filePath));
        System.out.println("  Last modified: " + Files.getLastModifiedTime(filePath));
    }
    
    /**
     * Demonstrates various text file reading and writing techniques
     */
    private static void demonstrateTextFileOperations() throws IOException {
        System.out.println("=== TEXT FILE OPERATIONS ===");
        
        Path filePath = Paths.get(TEMP_DIR, "text-operations.txt");
        
        // Writing lines using different methods
        List<String> lines = List.of(
            "Line 1: Basic text",
            "Line 2: With special characters: üöşğçı",
            "Line 3: Numbers and symbols: 123 !@#$%",
            "Line 4: Unicode: 🌟 ✨ 🚀",
            "Line 5: Final line"
        );
        
        // Method 1: Files.write with lines
        Files.write(filePath, lines);
        System.out.println("Written " + lines.size() + " lines to file");
        
        // Method 2: Using BufferedWriter
        Path bufferedFile = Paths.get(TEMP_DIR, "buffered-write.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(bufferedFile)) {
            for (int i = 1; i <= 10; i++) {
                writer.write("Buffered line " + i);
                writer.newLine();
            }
        }
        System.out.println("Created buffered write file");
        
        // Reading files using different methods
        System.out.println("\nReading methods:");
        
        // Method 1: Read all lines
        List<String> allLines = Files.readAllLines(filePath);
        System.out.println("Read " + allLines.size() + " lines using readAllLines()");
        
        // Method 2: Using BufferedReader
        System.out.println("Lines read with BufferedReader:");
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                System.out.printf("  %d: %s%n", lineNumber++, line);
            }
        }
        
        // Method 3: Using Stream API
        System.out.println("Processing with Stream API:");
        try (Stream<String> lineStream = Files.lines(filePath)) {
            lineStream.filter(line -> line.contains("Line"))
                     .map(String::toUpperCase)
                     .forEach(line -> System.out.println("  " + line));
        }
    }
    
    /**
     * Demonstrates modern NIO.2 operations
     */
    private static void demonstrateNIOOperations() throws IOException {
        System.out.println("=== NIO.2 OPERATIONS ===");
        
        Path sourceFile = Paths.get(TEMP_DIR, "source.txt");
        Path targetFile = Paths.get(TEMP_DIR, "target.txt");
        Path linkFile = Paths.get(TEMP_DIR, "link.txt");
        
        // Create source file
        String content = "This is the source file content.\nIt will be copied and moved.";
        Files.write(sourceFile, content.getBytes());
        
        // Copy file
        Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File copied from " + sourceFile.getFileName() + 
                          " to " + targetFile.getFileName());
        
        // Create symbolic link (if supported)
        try {
            Files.createSymbolicLink(linkFile, sourceFile);
            System.out.println("Symbolic link created: " + linkFile.getFileName());
            System.out.println("Link points to: " + Files.readSymbolicLink(linkFile));
        } catch (UnsupportedOperationException | IOException e) {
            System.out.println("Symbolic links not supported or failed: " + e.getMessage());
        }
        
        // Move file
        Path movedFile = Paths.get(TEMP_DIR, "moved.txt");
        Files.move(targetFile, movedFile, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File moved to: " + movedFile.getFileName());
        
        // Watch for file changes (demonstration only)
        System.out.println("File watching capability available: " + 
                          (FileSystems.getDefault().supportedFileAttributeViews().contains("posix") ||
                           FileSystems.getDefault().supportedFileAttributeViews().contains("dos")));
    }
    
    /**
     * Demonstrates directory operations and file tree walking
     */
    private static void demonstrateDirectoryOperations() throws IOException {
        System.out.println("=== DIRECTORY OPERATIONS ===");
        
        // Create directory structure
        Path subDir1 = Paths.get(TEMP_DIR, "subdir1");
        Path subDir2 = Paths.get(TEMP_DIR, "subdir2");
        Path deepDir = Paths.get(TEMP_DIR, "subdir1", "deep");
        
        Files.createDirectories(deepDir);
        Files.createDirectories(subDir2);
        
        // Create some files
        Files.write(Paths.get(subDir1.toString(), "file1.txt"), "Content 1".getBytes());
        Files.write(Paths.get(subDir1.toString(), "file2.log"), "Log content".getBytes());
        Files.write(Paths.get(subDir2.toString(), "file3.txt"), "Content 3".getBytes());
        Files.write(Paths.get(deepDir.toString(), "deep-file.txt"), "Deep content".getBytes());
        
        System.out.println("Created directory structure");
        
        // List directory contents
        System.out.println("\nDirectory contents:");
        try (Stream<Path> paths = Files.list(Paths.get(TEMP_DIR))) {
            paths.forEach(path -> {
                try {
                    String type = Files.isDirectory(path) ? "DIR " : "FILE";
                    long size = Files.isDirectory(path) ? 0 : Files.size(path);
                    System.out.printf("  %s %s (%d bytes)%n", type, path.getFileName(), size);
                } catch (IOException e) {
                    System.err.println("Error reading: " + path);
                }
            });
        }
        
        // Walk file tree
        System.out.println("\nWalking file tree:");
        try (Stream<Path> paths = Files.walk(Paths.get(TEMP_DIR))) {
            paths.filter(Files::isRegularFile)
                 .forEach(path -> {
                     try {
                         Path relativePath = Paths.get(TEMP_DIR).relativize(path);
                         System.out.printf("  %s (%d bytes)%n", relativePath, Files.size(path));
                     } catch (IOException e) {
                         System.err.println("Error reading: " + path);
                     }
                 });
        }
        
        // Find files by extension
        System.out.println("\nText files (.txt):");
        try (Stream<Path> paths = Files.find(Paths.get(TEMP_DIR), 10,
                (path, attrs) -> path.toString().endsWith(".txt"))) {
            paths.forEach(path -> System.out.println("  " + 
                Paths.get(TEMP_DIR).relativize(path)));
        }
    }
    
    /**
     * Demonstrates log file processing with filtering and analysis
     */
    private static void demonstrateLogFileProcessing() throws IOException {
        System.out.println("=== LOG FILE PROCESSING ===");
        
        Path logFile = Paths.get(TEMP_DIR, "application.log");
        
        // Generate sample log entries
        List<LogEntry> logEntries = List.of(
            new LogEntry(LocalDateTime.now().minusHours(2), "INFO", "Application started"),
            new LogEntry(LocalDateTime.now().minusHours(2).plusMinutes(5), "DEBUG", "Loading configuration"),
            new LogEntry(LocalDateTime.now().minusHours(1), "WARN", "Connection timeout, retrying"),
            new LogEntry(LocalDateTime.now().minusHours(1).plusMinutes(30), "ERROR", "Database connection failed"),
            new LogEntry(LocalDateTime.now().minusMinutes(45), "INFO", "Processing user request"),
            new LogEntry(LocalDateTime.now().minusMinutes(30), "ERROR", "Null pointer exception in module X"),
            new LogEntry(LocalDateTime.now().minusMinutes(15), "INFO", "Request completed successfully"),
            new LogEntry(LocalDateTime.now().minusMinutes(5), "DEBUG", "Cleaning up resources")
        );
        
        // Write log entries
        List<String> logLines = logEntries.stream()
                                         .map(LogEntry::toString)
                                         .collect(Collectors.toList());
        Files.write(logFile, logLines);
        System.out.println("Created log file with " + logLines.size() + " entries");
        
        // Process log file
        System.out.println("\nLog analysis:");
        
        // Count entries by level
        Map<String, Long> levelCounts = Files.lines(logFile)
            .map(LogEntry::fromString)
            .collect(Collectors.groupingBy(
                LogEntry::level,
                Collectors.counting()
            ));
        
        System.out.println("Entries by level:");
        levelCounts.forEach((level, count) -> 
            System.out.printf("  %s: %d%n", level, count));
        
        // Find recent errors
        System.out.println("\nRecent errors (last hour):");
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
        
        Files.lines(logFile)
             .map(LogEntry::fromString)
             .filter(entry -> "ERROR".equals(entry.level()))
             .filter(entry -> entry.timestamp().isAfter(oneHourAgo))
             .forEach(entry -> System.out.println("  " + entry));
        
        // Export filtered logs
        Path errorLogFile = Paths.get(TEMP_DIR, "errors-only.log");
        List<String> errorLines = Files.lines(logFile)
            .map(LogEntry::fromString)
            .filter(entry -> "ERROR".equals(entry.level()))
            .map(LogEntry::toString)
            .collect(Collectors.toList());
        
        Files.write(errorLogFile, errorLines);
        System.out.println("\nExported " + errorLines.size() + " error entries to errors-only.log");
    }
    
    private static void cleanup() {
        try {
            Path tempPath = Paths.get(TEMP_DIR);
            if (Files.exists(tempPath)) {
                try (Stream<Path> paths = Files.walk(tempPath)) {
                    paths.sorted(Comparator.reverseOrder())
                         .forEach(path -> {
                             try {
                                 Files.delete(path);
                             } catch (IOException e) {
                                 // Ignore cleanup errors
                             }
                         });
                }
            }
            System.out.println("\nCleaned up temporary files");
        } catch (IOException e) {
            System.err.println("Cleanup failed: " + e.getMessage());
        }
    }
}