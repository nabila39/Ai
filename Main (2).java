package application;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Alert;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Main extends Application {
    static MiniMax adsTicTacToe = new MiniMax();
    Pane pane = new Pane();

    int userInput;


    String[] board = {"0", "1", "2", "3", "4", "5", "6", "7", "8"};
    State state = new State(0, board);

    private final Button[][] buttons = new Button[3][3];
    int playerCounter = 0;
    int aiCounter = 0;
    int drawCounter = 0;

    Label result = new Label();
    Label emojy = new Label();
    Label player = new Label("Player :");
    Label playerC = new Label("0");
    Label draw = new Label("Draw :");
    Label drawC = new Label("0");
    Label ai = new Label("AI :");
    Label aiC = new Label("0");
    int c =0;
    Button start = new Button("Start");
    Label turn = new Label();
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game instructions");
        alert.setHeaderText("Follow the instructions");
        alert.setContentText("Click the \"Start\" button to initiate the game, which comprises five rounds.\n" +
                "\n" +
                "After each round, press the \"Next\" button.\n" +
                "\n" +
                "Wait for five seconds between each round to allow time for the results to be displayed.\n" +
                "\n" +
                "Repeat steps 2-3 for all five rounds.\n" +
                "\n" +
                "The winner is determined by the player who wins three or more out of the five rounds.");
        alert.showAndWait();
        VBox vb1 = new VBox(10);
        int count = 0;

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            HBox hb = new HBox(10);
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinSize(100, 100);
                buttons[i][j].setLayoutX(i * 100); // Set X position based on column
                buttons[i][j].setLayoutY(j * 100);// Set Y position based on row
                buttons[i][j].setStyle("-fx-background-color: #DA2169FF;");
                hb.getChildren().add(buttons[i][j]);
            }
            vb1.getChildren().add(hb);
        }
        Label title = new Label("TIC TAC TOE");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 33));
        title.setTextFill(Color.rgb(182, 8, 87));
        title.setLayoutX(195);
        title.setLayoutY(40);
        vb1.setLayoutX(150);
        vb1.setLayoutY(100);
        pane.setStyle("-fx-background-color: #1F1F1FFF;");
        result.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        result.setLayoutX(195);
        result.setLayoutY(450);
        result.setTextFill(Color.rgb(182, 8, 87));
        emojy.setLayoutX(405);
        emojy.setLayoutY(450);
        emojy.setFont(Font.font("Times New Roman", FontWeight.BOLD, 40));
        emojy.setTextFill(Color.rgb(255, 255, 0));
        /////////////////////////////////////////////////////////////
        HBox playerHbox = new HBox(10);
        playerHbox.getChildren().addAll(player, playerC);
        HBox drawHbox = new HBox(10);
        drawHbox.getChildren().addAll(draw, drawC);
        HBox aiHbox = new HBox(10);
        aiHbox.getChildren().addAll(ai, aiC);
        VBox enttites = new VBox(120);
        enttites.setLayoutY(100);
        enttites.setLayoutX(10);
        player.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        player.setTextFill(Color.rgb(182, 8, 87));
        playerC.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        playerC.setTextFill(Color.rgb(182, 8, 87));
        draw.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        draw.setTextFill(Color.rgb(182, 8, 87));
        drawC.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        drawC.setTextFill(Color.rgb(182, 8, 87));
        ai.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        ai.setTextFill(Color.rgb(182, 8, 87));
        aiC.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        aiC.setTextFill(Color.rgb(182, 8, 87));
        Button reset = new Button("Reset");
        start.setMaxSize(100,100);
        start.setStyle("-fx-background-color: #00FF00FF;");
        start.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        start.setTextFill(Color.rgb(255, 255, 255));
        reset.setMaxSize(100,100);
        reset.setStyle("-fx-background-color: #FF0000;");
        reset.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        reset.setTextFill(Color.rgb(255, 255, 255));
        HBox bts = new HBox(330);
        bts.getChildren().addAll(start,reset);
        bts.setLayoutX(70);
        bts.setLayoutY(500);
        turn.setTextFill(Color.rgb(72, 191, 234));
        turn.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        turn.setLayoutX(500);
        turn.setLayoutY(100);


        enttites.getChildren().addAll(playerHbox, drawHbox, aiHbox);
        pane.getChildren().addAll(vb1, title, result, emojy, enttites,bts,turn);
        Scene scene = new Scene(pane, 700, 600);
        stage.setTitle("Tic Tac Toe");
        stage.setScene(scene);
        stage.show();

            start.setOnAction(e->{
                int randomValue = random.nextInt(2);
                System.out.println(randomValue);
                if (randomValue == 0) {
                    turn.setText("First Turn For User");
                    setClickHandlersForUser("X");
                } else {
                    turn.setText("First Turn For AI");
                    aiPlay2("X");
                }
            });

            reset.setOnAction(t->{
                resetAllGame();
            });

        }




    private void updateUI(int row, int col, String symbol) {
        buttons[row][col].setText(symbol);
        buttons[row][col].setDisable(true);
        buttons[row][col].setFont(Font.font("Arial", FontWeight.BOLD, 33));
        buttons[row][col].setTextFill(Color.rgb(255, 255, 255));
    }
    ///////////////////////////////////////////////////////user start
    private void setClickHandlersForUser(String value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnMouseClicked(e -> {
                    if (!adsTicTacToe.isTerminal(state)) {
                        buttons[finalI][finalJ].setText(value);
                        buttons[finalI][finalJ].setFont(Font.font("Arial", FontWeight.BOLD, 33));
                        buttons[finalI][finalJ].setTextFill(Color.rgb(255, 255, 255));
                        userInput = finalI * 3 + finalJ;
                        buttons[finalI][finalJ].setDisable(true);
                        state.changeState(userInput, value);
                            aiPlay("O");
                    }
                });
            }
        }
    }

    private void aiPlay(String value ) {
        if (!adsTicTacToe.isTerminal(state)) {
            char value1 = value.charAt(0) ;
            int aiMove = adsTicTacToe.Decision(state,value1);
            int aiRow = aiMove / 3;//1
            int aiCol = aiMove % 3;//2
            board[aiMove] =value;
            updateUI(aiRow, aiCol, value);
        }
        if (adsTicTacToe.isTerminal(state)) {
            start.setDisable(true);
            result.setText("Game Over");
            emojy.setText("🙂");
            int res = adsTicTacToe.Autilityfunction(state);
            if(res == 1){
                playerCounter++;
                playerC.setText(playerCounter+"");
            } else if (res == -1) {
                aiCounter++;
                aiC.setText(aiCounter+"");
            }else{
                drawCounter++;
                drawC.setText(drawCounter+"");
            }
            checkForWin();
            start.setText("Next Game");
            c++;
            if(c ==5){
                start.setDisable(true);
                if(aiCounter>=3){
                    result.setText("AI Win");
                    emojy.setText("💪");
                }
            }else{
                scheduler.schedule(this::resetGame, 5, TimeUnit.SECONDS);
            }

        } else {
            setClickHandlersForUser("X");
        }
    }
    /////////////////////////////////////////////////////////////if ai start
    private void setClickHandlersForUser2(String value) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j].setOnMouseClicked(e -> {
                    if (!adsTicTacToe.isTerminal(state)) {
                        buttons[finalI][finalJ].setText(value);
                        userInput = finalI * 3 + finalJ;
                        buttons[finalI][finalJ].setFont(Font.font("Arial", FontWeight.BOLD, 33));
                        buttons[finalI][finalJ].setTextFill(Color.rgb(255, 255, 255));
                        buttons[finalI][finalJ].setDisable(true);
                        state.changeState(userInput, value);
                        // userMadeMove = true;
                        aiPlay2("X");
                    }
                });
            }
        }
    }

    public void aiPlay2(String value ) {
        if (!adsTicTacToe.isTerminal(state)) {
            char value1 = value.charAt(0) ;
            int aiMove = adsTicTacToe.Decision(state,value1);
            int aiRow = aiMove / 3;//1
            int aiCol = aiMove % 3;//2
            board[aiMove] =value;
            updateUI(aiRow, aiCol, value);
        }
        if (adsTicTacToe.isTerminal(state)) {
            start.setDisable(true);
            result.setText("Game Over");
            emojy.setText("ًں™‚");
            int res = adsTicTacToe.Autilityfunction(state);
            if(res == 1){
                aiCounter++;
                aiC.setText(aiCounter+"");
            } else if (res == -1) {
                playerCounter++;
                playerC.setText(playerCounter+"");
            }else{
                drawCounter++;
                drawC.setText(drawCounter+"");
            }
            checkForWin();
            start.setText("Next Game");
            c++;
            if(c ==5){
                start.setDisable(true);
                if(aiCounter>=3){
                    result.setText("AI Win");
                    emojy.setText("💪");

                }
            }else {
                scheduler.schedule(this::resetGame, 5, TimeUnit.SECONDS);
            }



        } else {
            setClickHandlersForUser2("O");
        }
    }

    private void resetGame() {
        Platform.runLater(() -> {
        board = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        state = new State(0, board);
        start.setDisable(false);
        result.setText("");
        emojy.setText("");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle("-fx-background-color: #DA2169FF;");

            }
        }
        });
    }
    private void resetAllGame() {
        board = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8"};
        state = new State(0, board);
        c=0;
        playerCounter=0;
        drawCounter=0;
        aiCounter=0;
        result.setText("");
        start.setText("Start");
        emojy.setText("");
        aiC.setText("0");
        drawC.setText("0");
        playerC.setText("0");
        result.setText("");
        emojy.setText("");
        turn.setText("");
        start.setDisable(false);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setDisable(false);
                buttons[i][j].setStyle("-fx-background-color: #DA2169FF;");
            }
        }
    }
    private void checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(buttons[i][1].getText()) &&
                    buttons[i][1].getText().equals(buttons[i][2].getText()) &&
                    !buttons[i][0].getText().isEmpty()) {
                buttons[i][0].setStyle("-fx-background-color: #00FF00FF;");
                buttons[i][1].setStyle("-fx-background-color: #00FF00FF;");
                buttons[i][2].setStyle("-fx-background-color: #00FF00FF;");


            }
        }

        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().equals(buttons[1][j].getText()) &&
                    buttons[1][j].getText().equals(buttons[2][j].getText()) &&
                    !buttons[0][j].getText().isEmpty()) {

                buttons[0][j].setStyle("-fx-background-color: #00FF00FF;");
                buttons[1][j].setStyle("-fx-background-color: #00FF00FF;");
                buttons[2][j].setStyle("-fx-background-color: #00FF00FF;");


            }
        }
        if (buttons[0][0].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][2].getText()) &&
                !buttons[0][0].getText().isEmpty()) {

            buttons[0][0].setStyle("-fx-background-color: #00FF00FF;");
            buttons[1][1].setStyle("-fx-background-color: #00FF00FF;");
            buttons[2][2].setStyle("-fx-background-color: #00FF00FF;");

        }
        if (buttons[0][2].getText().equals(buttons[1][1].getText()) &&
                buttons[1][1].getText().equals(buttons[2][0].getText()) &&
                !buttons[0][2].getText().isEmpty()) {
            buttons[0][2].setStyle("-fx-background-color: #00FF00FF;");
            buttons[1][1].setStyle("-fx-background-color: #00FF00FF;");
            buttons[2][0].setStyle("-fx-background-color: #00FF00FF;");


        }

    }
}

