import java.time.LocalDateTime;

public class VideoTape {
    private String filmName;
    private boolean isAvailable;
    private String clientName;
    private LocalDateTime rentDate;

    public VideoTape(String filmName, boolean isAvailable, String clientName, LocalDateTime rentDate) {
        this.filmName = filmName;
        this.isAvailable = isAvailable;
        this.clientName = clientName;
        this.rentDate = rentDate;
    }

    public String getFilmName() {
        return filmName;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getClientName() {
        return clientName;
    }

    public LocalDateTime getRentDate() {
        return rentDate;
    }

    public String getRendDateString(){
        return rentDate != null ? rentDate.toString() : "";
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setRentDate(LocalDateTime rentDate) {
        this.rentDate = rentDate;
    }

    public String toString() {
        return filmName + " - " +
                isAvailable + " - " +
                clientName + " - " +
                getRendDateString();

    }
}
