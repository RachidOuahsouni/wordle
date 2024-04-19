package application
;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.util.Duration;
import java.time.Instant;

public class Controller  implements Initializable {
	// declare all Textfield , label and Button on our Interface 
    @FXML
    Label resultLabel;
    @FXML
    TextField lettre1;
    @FXML
    TextField lettre2;
    @FXML
    TextField lettre3;
    @FXML
    TextField lettre4;
    @FXML
    TextField lettre5;
    @FXML
    TextField lettre6;
    @FXML
    TextField lettre7;
    @FXML
    TextField lettre8;
    @FXML
    TextField lettre9;
    @FXML
    TextField lettre10;
    @FXML
    TextField lettre11;
    @FXML
    TextField lettre12;
    @FXML
    TextField lettre13;
    @FXML
    TextField lettre14;
    @FXML
    TextField lettre15;
    @FXML
    TextField lettre16;
    @FXML
    TextField lettre17;
    @FXML
    TextField lettre18;
    @FXML
    TextField lettre19;
    @FXML
    TextField lettre20;
    @FXML
    Button buttonA;
    @FXML
    Button buttonZ;
    @FXML
    Button buttonE;
    @FXML
    Button buttonR;
    @FXML
    Button buttonT;
    @FXML
    Button buttonY;
    @FXML
    Button buttonU;
    @FXML
    Button buttonI;
    @FXML
    Button buttonO;
    @FXML
    Button buttonP;
    @FXML
    Button buttonQ;
    @FXML
    Button buttonS;
    @FXML
    Button buttonD;
    @FXML
    Button buttonF;
    @FXML
    Button buttonG;
    @FXML
    Button buttonH;
    @FXML
    Button buttonJ;
    @FXML
    Button buttonK;
    @FXML
    Button buttonL;
    @FXML
    Button buttonM;
    @FXML
    Button buttonW;
    @FXML
    Button buttonX;
    @FXML
    Button buttonC;
    @FXML
    Button buttonV;
    @FXML
    Button buttonB;
    @FXML
    Button buttonN;
    @FXML
    Button buttonBAC;
    @FXML
    Button buttonAide;
    @FXML
    Button buttonRE;
    
    String mot;
    
    @FXML
     Label temps;

    Instant startTime;
    int seriactuelle = 0;
    int elapsedSeconds =0;
    MonMenu monMenus = new MonMenu();
    ParserMot parserMot = new ParserMot();
    Score scores = new Score();
    
    List<TextField> letterFields = Arrays.asList(
            this.lettre1, this.lettre2, this.lettre3, this.lettre4, this.lettre5,
    this.lettre6, this.lettre7, this.lettre8, this.lettre9, this.lettre10,
            this.lettre11, this.lettre12, this.lettre13, this.lettre14, this.lettre15,
             this.lettre16 , this.lettre17 , this.lettre18 , this.lettre19 , this.lettre20
    );

   // handle the insert and the delete of lettre in the Textfield 
    public void addAutoFocus(TextField current, TextField next, TextField previous, TextField firstOfNextRow) {
        current.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 1) {
                if (next != null) {
                    next.requestFocus(); // Move to the next text field
                }
            } else if (newValue.length() > 1) {
                current.setText(newValue.substring(0, 1)); // Limit input to one character
            }
        });

        current.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.BACK_SPACE && current.getText().isEmpty()) {
                if (previous != null) {
                    previous.requestFocus(); // Move back to the previous text field
                }
                event.consume();
            }
        });
    }
    // init the timer
    public void startTimeline() {scores.startTimelines(temps);}

   // handle the insert of the letter keybord
    public void insertlettre(String letter){
        List<TextField> letterFields = Arrays.asList(
                lettre1, lettre2, lettre3, lettre4, lettre5,
                lettre6, lettre7, lettre8, lettre9, lettre10,
                lettre11, lettre12, lettre13, lettre14, lettre15,
                lettre16 , lettre17 , lettre18 , lettre19 , lettre20
        );
                // Find the first empty TextField and insert the letter
                for (TextField textField : letterFields) {
                    if (textField.getText().isEmpty()) {
                        textField.setText(letter);
                        textField.requestFocus();
                        break;  // Exit the loop after inserting the, letter
                    }
                }
        }

    // handle the insert of the letter with the Button 
    public void creeALF(){
        List<Button> buttons = Arrays.asList(
                buttonA,buttonB,buttonC,buttonD,buttonE,buttonF,buttonG,buttonH,buttonI
                ,buttonJ,buttonK,buttonL,buttonM,buttonN,buttonO,buttonP,buttonQ,buttonR,
                buttonS,buttonT,buttonU,buttonV,buttonW,buttonX,buttonY,buttonZ
        );
        List<TextField> letterFields = Arrays.asList(
                lettre1, lettre2, lettre3, lettre4, lettre5,
                lettre6, lettre7, lettre8, lettre9, lettre10,
                lettre11, lettre12, lettre13, lettre14, lettre15,
                lettre16 , lettre17 , lettre18 , lettre19 , lettre20
        );

        // Iterate through buttons to add event handlers
        for (int i = 0; i < 26; i++) {
            char letter = (char) ('A' + i);
                buttons.get(i).setOnAction(event -> {
                    // Find the first empty TextField and insert the letter
                        for (TextField textField : letterFields) {
                            if (textField.getText().isEmpty()) {
                                textField.setText(String.valueOf(letter));
                                textField.requestFocus();
                                break;  // Exit the loop after inserting the letter
                            }
                        }
                });
        }
    }
    // delete a letter with the button backspace
    @FXML
    public void backsp(){
         List<TextField> letterField = Arrays.asList(
                 lettre1, lettre2, lettre3, lettre4, lettre5,
                 lettre6, lettre7, lettre8, lettre9, lettre10,
                 lettre11, lettre12, lettre13, lettre14, lettre15
         );
        for (int i = letterField.size() - 1; i >= 0; i--) {
            TextField textField = letterField.get(i);
            String text = textField.getText();
            if (!text.isEmpty()) {
                textField.setText(text.substring(0, text.length() - 1));
                break;  // Exit the loop after removing the character
            }
        }
    }
   
    public void switchToMain(ActionEvent event) throws IOException {
    	monMenus.switchToMain(event);}
    
   public void rejouers(){
	   Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle("Recommencer");
       alert.setHeaderText("Voulez-vous rejouer?");

       ButtonType buttonNewGame = new ButtonType("Recommencer");
       ButtonType buttonReturn = new ButtonType("Retour à la page");

       alert.getButtonTypes().setAll(buttonNewGame, buttonReturn);
       alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

       alert.showAndWait().ifPresent(buttonType -> {
           if (buttonType == buttonNewGame) {
           	System.out.println("eeeeeeeeeeee");
               scores.initsecond();
               scores.initseriactuelle();
               mot = parserMot.slectMot(5,motsFiltre);
               // Call a method to reset the game (clear text fields)
               resetGame();
               lettre1.requestFocus();
           }
       });
       
   }
   // show help
    public void afficherAide(){ monMenus.afficherAide(); }
    public void afficherScore(){ monMenus.afficherScores(); }
    // show indice
    public void afficherIndice(){ monMenus.afficherIndi(mot); }
    
    @FXML
    public void passerAuRow5(){searchWord(lettre1, lettre2,lettre3, lettre4,lettre5,lettre6); }
    @FXML
    public void passerAuRow10(){ searchWord(lettre6, lettre7,lettre8, lettre9,lettre10,lettre11);}
    @FXML
    public void passerAuRow15(){searchWord(lettre11, lettre12,lettre13, lettre14,lettre15,lettre16);}
    @FXML
    public void passerAuRow20(){searchWord(lettre16, lettre17,lettre18, lettre19,lettre20,lettre20);}

    
    private void addRotateAnimation2(TextField textField) {
    	RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), textField);
        rotateTransition.setByAngle(360); 
        rotateTransition.setCycleCount(1); 
        rotateTransition.setAxis(new Point3D(1, 0, 0)); 
        rotateTransition.play();
    }
    
    List<String> motsFiltre = parserMot.par();
    //List<String> motsFiltres = parserMot.parplus("https://raw.githubusercontent.com/LouanBen/wordle-fr/main/mots.txt",motsFiltre);
    public void searchWord(TextField letter1, TextField letter2, TextField letter3, TextField letter4, TextField letter5, TextField letter6) {
        String inputWord = letter1.getText() + letter2.getText() + letter3.getText() + letter4.getText() + letter5.getText();
        inputWord = inputWord.toUpperCase();
        System.out.println(inputWord);
        TextField[] tab= {letter1, letter2, letter3, letter4,letter5};
        if (inputWord.length() == 5) {
            devinerMot(mot,inputWord,tab,letter6,resultLabel,this.lettre1,this.lettre20);
        }
        
        for (int i = 0; i < tab.length; i++) {
            if (tab[i] != null && i < inputWord.length()) {
                addRotateAnimation2(tab[i]);
            }
        }


    }
    
    private void addRotateAnimation(TextField textField) {
    	RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), textField);
        rotateTransition.setByAngle(360); 
        rotateTransition.setCycleCount(1); 
        rotateTransition.setAxis(new Point3D(0, 1, 0)); 
        rotateTransition.play();
    }
    // fonction to guess the word
    public void devinerMot(String randomWord, String inpWord, TextField[] textFields, TextField lettre, Label resultLabel,TextField premierlettre,TextField dernierlettre) {
        if(motsFiltre.contains(inpWord)) {
            resultLabel.setText("entre un mot de 5 lettre");
            boolean x=true;
            char ora=' ';
            int j;
            for (int i = 0; i < randomWord.length(); i++) {
                char lettreAttendue = randomWord.charAt(i);
                char lettreDevinee = (i < inpWord.length()) ? inpWord.charAt(i) : ' ';
                boolean lettreCorrecte = false;
                if (lettreDevinee == lettreAttendue) {
                    textFields[i].setStyle("-fx-background-color: green;-fx-background-radius: 15;");
                    addRotateAnimation(textFields[i]);
                    lettreCorrecte = true;
                } else if (randomWord.contains(String.valueOf(lettreDevinee))) {
                    for ( j = 0; j < randomWord.length(); j++){
                        char lettreAttendues = randomWord.charAt(j);
                        char lettreDevinees = (j < inpWord.length()) ? inpWord.charAt(j) : ' ';
                        System.out.println("tttttt  " +textFields[i].getText().toUpperCase().charAt(0));
                        System.out.println("rrrrrr  " +lettreDevinees);
                        if (lettreDevinees == lettreAttendues && textFields[i].getText().toUpperCase().charAt(0) == lettreDevinees) {
                        	System.out.println("aaaaa " +lettreDevinees);
                        	x=false;
                        	j = randomWord.length();
                        }
                    }
                    if(x){
                       if(ora!=textFields[i].getText().toUpperCase().charAt(0)) {
                       textFields[i].setStyle("-fx-background-color: ORANGE; -fx-background-radius: 15;");
                       ora=textFields[i].getText().toUpperCase().charAt(0);}
                    }
                }
                x=true;
            }
            //if he/she find the word
            if (inpWord.equals(randomWord)) {
            	scores.augseriactuelle();
            	
            	 File file = new File("scores.txt");
                if(file.exists()){
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt"));
                        String line,lines;
                        int numeroLigneCourante =1;
                        while ((line = reader.readLine()) != null) {
                            int indexOfSpace = line.indexOf(" ");
                            String extractedPart = line.substring(0, indexOfSpace);
                            System.out.println(extractedPart + " " + scores.getserieactuelle());
                            if(Integer.parseInt(extractedPart) < scores.getserieactuelle()){
                                System.out.println("hhhhhh");
                                reader.close();
                                reader = new BufferedReader(new FileReader(file));
                                while ((lines = reader.readLine()) != null) {
                                    System.out.println("jjjjjjj");
                                    if (numeroLigneCourante != 3) {
                                        writer.write(lines);
                                        writer.newLine();
                                    }
                                    numeroLigneCourante++;
                                }
                                writer.write(scores.getserieactuelle() + " temps : " + scores.getelapsedSeconds());
                                writer.newLine();
                                reader.close();
                                writer.close();
                                reader = new BufferedReader(new FileReader("score.txt"));
                                writer = new BufferedWriter(new FileWriter(file));
                                while ((line = reader.readLine()) != null) {
                                    System.out.println("lllllllll");
                                    writer.write(line);
                                    writer.newLine();
                                }
                        }
                    }
                        reader.close();
                        writer.close();
                } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    FileWriter fileWriter = null; // Append to the file
                    try {
                        System.out.println("ffffffff");
                        fileWriter = new FileWriter("scores.txt", true);
                        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                        bufferedWriter.write(scores.getserieactuelle() + " temps : " + scores.getelapsedSeconds());
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        System.out.println("ggggggggggg");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }  
                //}
                // Display an alert when the word is correctly guessed
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bravo !");
                alert.setHeaderText("Vous avez deviné le mot."); 
                alert.setContentText("Voulez-vous rejouer?");

                ButtonType buttonNewGame = new ButtonType("Nouvelle partie");
                ButtonType buttonReturn = new ButtonType("Retour à la page");
                
                alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                alert.getDialogPane().getStyleClass().add("positioned-alert");


                alert.getButtonTypes().setAll(buttonNewGame, buttonReturn);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == buttonNewGame) {
                        // Call a method to reset the game (clear text fields)
                        resetGame();
                        premierlettre.requestFocus();
                    }
                });
                mot=parserMot.slectMot(5,motsFiltre);
                resultLabel.setText("Bravo ! Vous avez deviné le mot.");
                System.out.println("Bravo ! Vous avez deviné le mot.");
            } else{
                if(dernierlettre == lettre){
                	Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ouups !");
                    alert.setHeaderText("Vous n'avez pas deviné le mot.");
                    alert.setContentText("Voulez-vous rejouer?");

                    ButtonType buttonNewGame = new ButtonType("Nouvelle partie");
                    ButtonType buttonReturn = new ButtonType("Retour à la page");
                    
                    alert.getDialogPane().getStylesheets().add(getClass().getResource("application.css").toExternalForm());

                    alert.getButtonTypes().setAll(buttonNewGame, buttonReturn);

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType == buttonNewGame) {
                            // Call a method to reset the game (clear text fields)
                            mot=parserMot.slectMot(5,motsFiltre);
                            resetGame();
                            premierlettre.requestFocus();
                        }
                    });
                    scores.initseriactuelle();
                }else lettre.requestFocus();
            }
        }else {
            resultLabel.setText("ce mot n'exsiste pas dans la list des mots");
        }
    }
    
    
    // to reset the Game
    public void resetGame() {
        lettre1.clear();
        lettre2.clear();
        lettre3.clear();
        lettre4.clear();
        lettre5.clear();
        lettre6.clear();
        lettre7.clear();
        lettre8.clear();
        lettre9.clear();
        lettre10.clear();
        lettre11.clear();
        lettre12.clear();
        lettre13.clear();
        lettre14.clear();
        lettre15.clear();
        lettre16.clear();
        lettre17.clear();
        lettre18.clear();
        lettre19.clear();
        lettre20.clear();

        resetTextFieldBackground(lettre1);
        resetTextFieldBackground(lettre2);
        resetTextFieldBackground(lettre3);
        resetTextFieldBackground(lettre4);
        resetTextFieldBackground(lettre5);
        resetTextFieldBackground(lettre6);
        resetTextFieldBackground(lettre7);
        resetTextFieldBackground(lettre8);
        resetTextFieldBackground(lettre9);
        resetTextFieldBackground(lettre10);
        resetTextFieldBackground(lettre11);
        resetTextFieldBackground(lettre12);
        resetTextFieldBackground(lettre13);
        resetTextFieldBackground(lettre14);
        resetTextFieldBackground(lettre15);
        resetTextFieldBackground(lettre16);
        resetTextFieldBackground(lettre17);
        resetTextFieldBackground(lettre18);
        resetTextFieldBackground(lettre19);
        resetTextFieldBackground(lettre20);
        // Reset any other game-related variables or timers
        scores.initsecond();
        scores.updateElapsedTime(temps);
    }
    public void resetTextFieldBackground(TextField textField) {
        // Reset the background color of the given text field to black
        textField.setStyle("-fx-background-color: gray; -fx-background-radius: 15;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) throws NullPointerException {
       // Platform.runLater(() -> {
            if (startTime == null) {
                startTime = Instant.now(); // Set the start time only if it's not set
            }
        creeALF();
        addAutoFocus(lettre1, lettre2, null, null);
        addAutoFocus(lettre2, lettre3, lettre1, null);
        addAutoFocus(lettre3, lettre4, lettre2, null);
        addAutoFocus(lettre4, lettre5, lettre3, null);
        addAutoFocus(lettre5, null, lettre4, lettre6);

        addAutoFocus(lettre6, lettre7, null, null);
        addAutoFocus(lettre7, lettre8, lettre6, null);
        addAutoFocus(lettre8, lettre9, lettre7, null);
        addAutoFocus(lettre9, lettre10, lettre8, null);
        addAutoFocus(lettre10, null, lettre9, lettre11);

        addAutoFocus(lettre11, lettre12, null, null);
        addAutoFocus(lettre12, lettre13, lettre11, null);
        addAutoFocus(lettre13, lettre14, lettre12, null);
        addAutoFocus(lettre14, lettre15, lettre13, null);
        addAutoFocus(lettre15, null, lettre14, lettre16);
        
        
        addAutoFocus(lettre16, lettre17, null, null);
        addAutoFocus(lettre17, lettre18, lettre16, null);
        addAutoFocus(lettre18, lettre19, lettre17, null);
        addAutoFocus(lettre19, lettre20, lettre18, null);
        addAutoFocus(lettre20, null, lettre19, null);
        
            mot =parserMot.slectMot(5,motsFiltre);
        startTimeline();
        //});
    }
}