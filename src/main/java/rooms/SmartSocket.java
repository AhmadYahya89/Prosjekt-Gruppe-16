
package rooms;

public class SmartSocket {
    private int number;
    private String name;
    private boolean statusOn;

    public SmartSocket(int number, String name) {
        long startTime = System.nanoTime();

        setNumber(number); // Use setNumber to ensure validation
        setName(name); // Use setName to ensure validation
        this.statusOn = false;

        logResponseTime("SmartSocket Constructor", startTime);
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        if (!isValidNumber(number)) {
            throw new IllegalArgumentException("Socket number must be a positive integer.");
        }
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (name.length() < 3 || name.length() > 20) {
            throw new IllegalArgumentException("Socket name must be between 3 and 20 characters.");
        }
        if (!name.matches("^[a-zA-Z0-9 ]+$")) {
            throw new IllegalArgumentException("Socket name can only contain letters, numbers, and spaces.");
        }
        this.name = name;
    }

    public boolean isStatusOn() {
        return this.statusOn;
    }

    public void setStatusOn(boolean statusOn) {
        long startTime = System.nanoTime();

        this.statusOn = statusOn;

        logResponseTime("setStatusOn", startTime);
    }

    public void toggleSmartSocketState() {
        long startTime = System.nanoTime();

        this.statusOn = !this.statusOn;

        logResponseTime("toggleSmartSocketState", startTime);
    }

    // Validate socket number to ensure it is a positive integer
    private boolean isValidNumber(int number) {
        return number > 0;
    }

    // Log response time for methods
    private void logResponseTime(String methodName, long startTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Execution time for " + methodName + " (ns): " + duration);
    }
}
