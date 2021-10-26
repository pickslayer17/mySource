
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VideoSalon {
    File videoTapesFile;
    private String salonName;
    List<VideoTape> videoTapeList = new ArrayList<>();

    public String getSalonName() {
        return salonName;
    }

    public VideoSalon(String videoTapesFileName, String salonName)  throws FileNotFoundException {
        videoTapesFile = new File(videoTapesFileName);
        this.salonName = salonName;
        readTapesFromFile();
    }

    private void readTapesFromFile() throws FileNotFoundException{
        Scanner videoTapesFileScanner = new Scanner(videoTapesFile);
        while(videoTapesFileScanner.hasNextLine()){
            String line = videoTapesFileScanner.nextLine();
            String[] lineElements = line.split("\\*",4);

            String filmName = lineElements[0].trim();
            boolean isAvailable = Boolean.parseBoolean(lineElements[1].trim());
            String clientName = lineElements[2].trim();
            LocalDateTime rentDate;
            try{
                rentDate = LocalDateTime.parse(lineElements[3].trim());
            } catch (DateTimeParseException exception) {
                exception.printStackTrace();
                rentDate = LocalDateTime.of(2012, 12,12,00,00);
            }

            videoTapeList.add(new VideoTape(filmName,isAvailable, clientName, rentDate));
        }
    }


    public void showAvailableVideoTapes() {
        for(VideoTape videoTape : videoTapeList){
            if(videoTape.isAvailable()) {
                System.out.println(videoTape);
            }
        }
    }

    public void showRentVideoTapes() {
        for(VideoTape videoTape : videoTapeList){
            if(!videoTape.isAvailable()) {
                System.out.println(videoTape);
            }
        }
    }

    public void rentVideoTape(String videoTapeName, String clientName) {
        boolean isRentSuccess = false;
        for(VideoTape videoTape : videoTapeList){
           if(videoTape.getFilmName().equalsIgnoreCase(videoTapeName) && videoTape.isAvailable()) {
               videoTape.setAvailable(false);
               videoTape.setClientName(clientName);
               videoTape.setRentDate(LocalDateTime.now());
               isRentSuccess = true;
               break;
           }
        }
        if(isRentSuccess){
            System.out.println("Thank you, " + clientName + ", for using our video salon. ");
            System.out.println("You take the \"" + videoTapeName + "\" video tape.");
            System.out.println("We will wait for tape back for a month. Have a good watching!");
            updateFile();
        } else {
            System.out.println("Sorry, we don't have this tape this time");

        }
    }

    private void updateFile() {
        try (FileWriter videoTapesWriter = new FileWriter(videoTapesFile)) {
            for(VideoTape videoTape : videoTapeList) {
                videoTapesWriter.write(videoTape.getFilmName() + "*");
                videoTapesWriter.write(videoTape.isAvailable() + "*");
                videoTapesWriter.write(videoTape.getClientName() + "*");
                videoTapesWriter.write(videoTape.getRendDateString());
                videoTapesWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backVideoTape(String videoTapeName, String clientName) {
        boolean isBackSuccess = false;
        for(VideoTape videoTape : videoTapeList){
            if(videoTape.getFilmName().equalsIgnoreCase(videoTapeName) && !videoTape.isAvailable() && videoTape.getClientName().equalsIgnoreCase(clientName)) {
                videoTape.setAvailable(true);
                videoTape.setRentDate(LocalDateTime.now());
                isBackSuccess = true;
                break;
            }
        }
        if(isBackSuccess){
            System.out.println("Thank you for tape back, " + clientName + ". Here is your passport :)");
            updateFile();
        } else {
            System.out.println("Sorry, it's not our video tape.");
        }

    }
}
