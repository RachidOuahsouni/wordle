package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.List;

public class Score{
    Timeline timeline;
    private int elapsedSeconds = 0;
    private int seriactuelle = 0;


    public void initseriactuelle(){
        this.seriactuelle=0;
    }
    public void initsecond(){
        this.elapsedSeconds=0;
    }
    public void augseriactuelle(){
        this.seriactuelle++;
    }
    public int getserieactuelle(){
       return this.seriactuelle;
    }
    public int getelapsedSeconds(){
        return this.elapsedSeconds;
    }
    public void startTimelines(Label temps) {
        // Create a timeline that triggers every second
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Update the label with the elapsed time
                updateElapsedTime(temps);
            }
        }));

        // Set the timeline to repeat indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the timeline
        timeline.play();
    }

    public void updateElapsedTime(Label temps) {
        elapsedSeconds++;
        // Update the label with the elapsed time
        temps.setText(elapsedSeconds + " seconds" + "  - la serie actuelle " + seriactuelle);
    }

    private void resetTextFieldBackground(TextField textField) {
        // Reset the background color of the given text field to black
        textField.setStyle("-fx-background-color: black;");
    }
}
