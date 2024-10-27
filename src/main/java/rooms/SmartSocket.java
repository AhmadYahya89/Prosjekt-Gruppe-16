package rooms;

public class SmartSocket {

    private final int number; // Stikkontaktnummer
    private String name; // Stikkontaktnavn
    private boolean statusOn; // Status på stikkontakt (av eller på)

    // Konstruktør
    public SmartSocket(int number, String name) {
        this.number = number;
        this.name = name;
        this.statusOn = false; // Standard status av
    }

    // Gettere og settere
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
    }
        this.name = name;
    }

    public boolean isStatusOn() {
        return statusOn;
    }

    public void setStatusOn(boolean statusOn) {
        this.statusOn = statusOn;
    }

    // Skru stikkontakt av/på
    public void toggleSmartSocketState() {
        this.statusOn = !this.statusOn;
    }
}
