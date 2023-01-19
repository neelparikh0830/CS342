import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;

// import java.nio.file.Paths;
// import java.net.MalformedURLException;
// import javax.print.attribute.standard.Media;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;

/**
 * Connect four application by
 *  - Cayas, Von Vic
 *  - Parikh, Neel
 */
public class JavaFXTemplate extends Application {
	/** 
	 * =========================================================================
	 * Window attributes
	 * =========================================================================
	 */
	/** The window width and height. **/
	private final int WIDTH = 800, HEIGHT = 760;
	/** The main stage that handles everything. */
	private Stage primaryStage; 

	/** 
	 * =========================================================================
	 * Gameboard attributes
	 * =========================================================================
	 */

	/** ROW_COUNT: Number of rows on the connect four board. **/
	/** COLUMN_COUNT: Number of columns on the connect four board. **/
	private final int ROW_COUNT = 6, COLUMN_COUNT = 7;

	/** A 2D Matrix of the gamebuttons, synced with the gridpane**/
	private GameButton[][] buttonMatrix;

	/** Denotes the current player: 1 or 2 **/
	private Integer currentPlayer;

	/** Root of the game scene, mainly for switching backgrounds. **/
	BorderPane mainGameBP;

	private class Move {
		public int x;
		public int y;

		Move(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	Stack<Move> moveStack;
	ListView<Text> moveHistory;

	private int buttonsPressed;

	/** 
	 * =========================================================================
	 * Themeboard attributes
	 * =========================================================================
	 */

	/** Holds the colors for player one and player two. **/
	private class Theme {
		public Color playerOne;
		public Color playerTwo;
		public Color winner;
	}

	/** Total number of themes **/
	private final int numberOfThemes = 3;

	/** Array for all themes. **/
	private Theme[] themes;

	/** Index of the current theme from this.themes. **/
	private int currentThemeIndex;

	/** 
	 * *************************************************************************
	 * Method Definitions
	 * *************************************************************************
	 */

	/**
	 * =========================================================================
	 * Splash Scene Functionsx
	 * =========================================================================
	 */

	/**
	 * createSplashScene
	 * Creates a splash scene that displays the game title in the center, with a
	 * 		Play button right below it.
	 * @returns the splash scene.
	 */
	private Scene createSplashScene() {
		Button playButton = createPlayButton("Play");

		Button exitButton = new Button("Exit");

		// Text gameTitle = new Text();
		// gameTitle.setText("Connect 4");
		// gameTitle.setFont(Font.font ("Arial", 50));
		// gameTitle.setFill(Color.RED);

		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);

		Reflection r = new Reflection();
		r.setFraction(0.4f);
		
		//t.setEffect(r);

		Text tile = new Text();
		tile.setEffect(is);
		tile.setEffect(r);
		tile.setText("Connect 4");
		tile.setFill(Color.YELLOW);
		tile.setFont(Font.font(null, FontWeight.BOLD, 80));

		Image image = new Image("Connect_4.jpg");
		ImageView connect = new ImageView();
		connect.setImage(image);
		connect.setFitWidth(350);
		connect.setFitWidth(350);

		HBox title = new HBox (tile);
		HBox button = new HBox(playButton, exitButton);
		button.setSpacing(100);
		title.setAlignment(Pos.CENTER);
		button.setAlignment(Pos.BOTTOM_CENTER);

		VBox vbox1 = new VBox(title, connect, button);
		vbox1.setSpacing(50);
		vbox1.setAlignment(Pos.CENTER);
		

		StackPane centeredLayout = new StackPane();
		centeredLayout.getChildren().add(vbox1);
		centeredLayout.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(5), Insets.EMPTY)));

		// String musicName = "Back_to_School.mp3";
		// Media h = new Media(Paths.get(musicName).toUri().toString());
		// mediaPlayer mp = new mediaPlayer(h);
		// mp.play();

		final Duration Sec_1 = Duration.millis(2000);
		final Duration Sec_2 = Duration.millis(3000);

		FadeTransition ft = new FadeTransition(Sec_2);
		ft.setFromValue(1.0f);
		ft.setToValue(0.3f);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);

		TranslateTransition tt = new TranslateTransition(Sec_1);
		tt.setFromX(0f);
		tt.setToX(100f);
		tt.setCycleCount(2);
		tt.setAutoReverse(true);

		RotateTransition rt = new RotateTransition(Sec_2);
		rt.setByAngle(360);
		rt.setCycleCount(1);
		rt.setAutoReverse(true);

		ScaleTransition st = new ScaleTransition(Sec_1);
		st.setByX(1.5f);
		st.setByY(1.5f);
		st.setCycleCount(2);
		st.setAutoReverse(true);

		ParallelTransition sq = new ParallelTransition(tile, ft,tt,rt,st);
		sq.play();

		exitButton.setOnAction(e->{
			Platform.exit();
		});

		Scene scene = new Scene(centeredLayout, WIDTH, HEIGHT);
		scene.setFill(new RadialGradient(0, 0, 0, 0, 1, true,
										CycleMethod.NO_CYCLE,
										new Stop (0, Color.web("#81c483")),
										new Stop (1, Color.web("#fcc200"))));
		scene.getRoot().requestFocus();
		return scene;
	}

	/**
	 * =========================================================================
	 * Game Scene Functions
	 * =========================================================================
	 */

	/**
	 * Creates the scene where the main game play will take place. There'll be
	 * 		an menu bar with "Game Play", "Themes" and "Option" menu locate at
	 * 		the top, then the main gameboard located right in the middle, and
	 * 		a game move history.
	 * @return the game scene.
	 */
	private Scene createGameScene() {	
		buttonsPressed = 0;

		GridPane gameboard = createConnectFour();

		BorderPane bp = new BorderPane(gameboard);
		bp.setPadding(new Insets(10, 10, 10, 10));

		VBox vbox = new VBox(createGameMenuBar(), bp, this.moveHistory);

		vbox.setAlignment(Pos.TOP_CENTER);

		this.mainGameBP = new BorderPane(vbox);
		setMainBPBackgroundToCurrentPlayer();

		Scene scene = new Scene(this.mainGameBP, WIDTH, HEIGHT);

		scene.setFill(getPlayerColor(currentPlayer));
		return scene;
	}

	/**
	 * createConnectFour
	 * 
	 * Initializes the this.buttonMatrix and declares and initializes a gridpane
	 * 		of same dimensions. For each and every element in the matrix,
	 * 		insert a new game button in both the matrix (and the same exact
	 * 		button in the gridpane).
	 * 
	 * After, beautify the board.
	 * 
	 * @returns the connect four board.
	 */
	private GridPane createConnectFour() {
		buttonMatrix = new GameButton[ROW_COUNT][COLUMN_COUNT];

		GridPane ticTacToeBoard = new GridPane();

		// Fill up each and every element in the matrix.
		for (int currentRowIndex = 0; currentRowIndex < this.ROW_COUNT; ++currentRowIndex) {
			for (int currentColumnIndex = 0; currentColumnIndex < this.COLUMN_COUNT; ++currentColumnIndex) {
				GameButton button = createGameButton(currentColumnIndex, currentRowIndex);

				// Insert both in matrix and gameboard.
				// We will use the matrix to perform specific button operations
				//		e.g. checking if a valid button.
				buttonMatrix[currentRowIndex][currentColumnIndex] = button;
				ticTacToeBoard.add(button, currentColumnIndex, currentRowIndex);
			}
		}

		// Beautify the board
		ticTacToeBoard.setAlignment(Pos.CENTER);
		ticTacToeBoard.setBackground(new Background(new BackgroundFill(
			new Color(1,1,1,0.5),  // Faint white background.
			new CornerRadii(30),
			Insets.EMPTY
		)));
		ticTacToeBoard.setPadding(new Insets(10, 20, 10, 20));

		// Sets spacing in between each button.
		ticTacToeBoard.setHgap(10);
		ticTacToeBoard.setVgap(10);

		return ticTacToeBoard;
	}

	/**
	 * createGameMenuBar
	 * @returns the game menu bar with menus "Game Play", "Themes" and "Options"
	 * 	menu
	 */
	private MenuBar createGameMenuBar() {
		MenuBar mb = new MenuBar(
			createGamePlayMenu(),
			createThemesMenu(),
			createOptionsMenu()
		);
		return mb;
	}

	/**
	 * createGamePlayMenu
	 * @returns the game play menu with menu item "Reverse Move", where that
	 * 		reverses the latest move.
	 */
	private Menu createGamePlayMenu() {
		MenuItem reverseMoveMenuItem = createReverseMoveMenuItem();
		Menu gamePlayMenu = new Menu("Game Play");
		gamePlayMenu.getItems().add(reverseMoveMenuItem);

		return gamePlayMenu;
	}

	private MenuItem createReverseMoveMenuItem() {
		MenuItem reverseMoveMenuItem = new MenuItem("Reverse Move");

		reverseMoveMenuItem.setOnAction(e->{
			if (this.buttonsPressed > 0) {
				Move lastMove = moveStack.pop();

				resetButton(buttonMatrix[lastMove.y][lastMove.x]);

				--this.buttonsPressed;
				currentPlayer = (this.currentPlayer % 2) + 1;
				setMainBPBackgroundToCurrentPlayer();
			}
		});
		
		return reverseMoveMenuItem;
	}

	/**
	 * createThemesMenu
	 * @return a menu of themes that the user can select. The themes affect the
	 * 		background and the connect four button colors.
	 */
	private Menu createThemesMenu() {
		MenuItem originalThemeOption = createThemeMenuItem("Original Theme", 0);
		MenuItem themeOneOption = createThemeMenuItem("Theme One", 1);
		MenuItem themeTwoOption = createThemeMenuItem("Theme Two", 2);
		Menu themeMenu = new Menu("Themes");
		themeMenu.getItems().addAll(
			originalThemeOption,
			themeOneOption,
			themeTwoOption
		);

		return themeMenu;
	}

	/**
	 * createThemeMenuItem
	 * @param name -> The menu item label.
	 * @param themeIndex -> The index of theme used for this.themes.
	 * @return A menu item that when clicked, changes the colors of the
	 * 		connect four board, based on the colors of the theme at
	 * 		`themeIndex`.
	 */
	private MenuItem createThemeMenuItem(String name, int themeIndex) {
		MenuItem originalThemeOption = new MenuItem(name);

		originalThemeOption.setOnAction(e->{
			if (currentThemeIndex != themeIndex) {
				changeTheme(themeIndex);
			}
		});

		return originalThemeOption;
	}

	/**
	 * changeTheme
	 * Changes the color for each and every button in the connect four board.
	 * 		Then, it changes the background to match the color of the current
	 * 		player from the new theme.
	 * @param themeIndex -> used to access the theme in this.themes.
	 */
	private void changeTheme(int themeIndex) {
		currentThemeIndex = themeIndex;

		Color playerOneColor = themes[currentThemeIndex].playerOne;
		Color playerTwoColor = themes[currentThemeIndex].playerTwo;

		/**
		 * Iterates through every single button on the button matrix to set the
		 * 		appropriate color of its player occupancy state.
		 */
		for (int row = 0; row < ROW_COUNT; ++row) {
			for (int col = 0; col < COLUMN_COUNT; ++col) {
				int playerOccupies = buttonMatrix[row][col].getPlayerOccupies();

				// Sets the theme based on button's occupancy state.
				if (playerOccupies == 1) {
					buttonMatrix[row][col].setStyle(
						"-fx-background-color: " +
						colorHexToCSS(playerOneColor.toString())
					);
				} else if (playerOccupies == 2) {
					buttonMatrix[row][col].setStyle(
						"-fx-background-color: " +
						colorHexToCSS(playerTwoColor.toString())
					);
				}
			}
		}

		setMainBPBackgroundToCurrentPlayer();
	}

	/**
	 * createOptionsMenu
	 * @return a menu of options with title "Options":
	 * 	- How To Play
	 *  - New Game
	 *  - Exit
	 */
	private Menu createOptionsMenu() {
		MenuItem howToPlayItem = createHowToPlayItem();
		MenuItem newGameItem = createNewGameMenuItem();
		MenuItem exitItem = createExitMenuItem();
		Menu optionsMenu = new Menu("Options");
		optionsMenu.getItems().addAll(
			howToPlayItem,
			newGameItem,
			exitItem
		);

		return optionsMenu;
	}
	
	/**
	 * createHowToPlayItem
	 * @return A menu item labeled "How To Play". When clicked, the pop up of
	 * 		the Connect Four instructions will be found.
	 */
	private MenuItem createHowToPlayItem() {
		MenuItem howToPlayItem = new MenuItem("How To Play");
		howToPlayItem.setOnAction(e->{
			Popup popup = new Popup();
			popup.setAutoHide(true);
			popup.setAutoFix(true);
			
			InputStream inputStream = getClass()
			.getClassLoader().getResourceAsStream("howtoplay.txt");

			Label popupLabel = new Label();
			try {
				popupLabel.setText(new String(inputStream.readAllBytes()));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Beautify label
			popupLabel.setStyle(
				"-fx-font-family: 'arial';" +
				"-fx-background-color: rgba(255,255,255,0.8);"
			);
			popupLabel.setTextAlignment(TextAlignment.CENTER);
			popupLabel.setPadding(new Insets(10, 10, 10, 10));
			popup.getContent().add(popupLabel);

			popup.show(primaryStage);
		});
		return howToPlayItem;
	}

	/**
	 * createNewGameMenuItem
	 * @returns A menu item labelled "New Game". When clicked, the connect four
	 * 		game is reset to the default set.
	 */
	private MenuItem createNewGameMenuItem() {
		MenuItem newGameItem = new MenuItem("New Game");

		newGameItem.setOnAction(e->{
			switchScene(createGameScene());
		});

		return newGameItem;
	}

	/**
	 * createExitMenuItem
	 * @returns A menu item labelled "Exit". When clicked, the application is
	 * 		exitted.
	 */
	private MenuItem createExitMenuItem() {
		MenuItem exitItem = new MenuItem("Exit");

		exitItem.setOnAction(e->{
			Platform.exit();
		});

		return exitItem;
	}

	/**
	 * createGameButton
	 * @param columnIndex -> the column to insert button.
	 * @param rowIndex -> the row to insert button.
	 * @returns the game button created. When clicked:
	 * 		- First, the button's validity is examined.
	 * 		- Then, it's color is filled to the corresponding player's color.
	 *  	- Finally, the button is checked if it is support of a winning move
	 * 			sequence on the board. If it is, winner is shown and game's
	 * 			over. Else, continue game.
	 * Lastly, the game button is beautified.
	 */
	private GameButton createGameButton(int columnIndex, int rowIndex) {
		GameButton button = new GameButton(columnIndex, rowIndex);

		button.setOnAction(e->{
			GameButton b = (GameButton) e.getSource();

			// Checks if invalid button selection.
			if (!isValidButton(b.getX(), b.getY())) {
				Text invalidMoveText = new Text(
					getCurrentPlayerAsStr() + " made invalid move at " + b.getPosAsStr()
				);

				Text goAgainText = new Text(
					getCurrentPlayerAsStr() + " go again."
				);

				moveHistory.getItems().addAll(invalidMoveText, goAgainText);
				moveHistory.scrollTo(goAgainText);
				return;
			}

			++buttonsPressed;

			Text validMoveText = new Text(
				getCurrentPlayerAsStr() + " placed a piece at " + button.getPosAsStr()
			);
			moveHistory.getItems().add(validMoveText);
			moveHistory.scrollTo(validMoveText);

			// Insert move into move set.
			moveStack.push(new Move(b.getX(), b.getY()));

			// Update button occupancy state and color.
			b.setPlayerOccupies(currentPlayer);
			button.setStyle(
				"-fx-focus-color: transparent; " +
				"-fx-background-color: " +
				colorHexToCSS(getCurrentPlayerColor().toString())
			);

			// If button clicked is part of the winning move sequence, then
			// 		switch to winner scene.
			if(checkWinner.checkWinner(
				buttonMatrix,
				columnIndex,
				rowIndex,
				themes[currentThemeIndex].winner
			))
			{
				endGame(new String(getCurrentPlayerAsStr() + " won!"), new Text(getCurrentPlayerAsStr() + " won!"));
			}

			if (buttonsPressed == ROW_COUNT * COLUMN_COUNT) {
				endGame(new String("It's a tie!"), new Text("It's a tie!"));
			}

			// Updates current player
			this.currentPlayer = (this.currentPlayer % 2) + 1;

			// Sets background to new current player.
			setMainBPBackgroundToCurrentPlayer();
		});

		// Beautify button.
		int r = 45;
		button.setShape(new Circle(r));
		button.setMinSize(2*r, 2*r);
		button.setMaxSize(2*r, 2*r);
		button.setStyle(
			"-fx-focus-color: transparent; " +
			"-fx-background-color: " +
			colorHexToCSS(Color.rgb(255, 255, 255, 0.6).toString())
		);
		return button;
	}

	/**
	 * endGame
	 * Ends the game and transitions to the winner scene.
	 * @param text Text to display in the move history.
	 */
	private void endGame(String won, Text text) {
		this.moveHistory.getItems().add(text);

		disableAllButtons();
		PauseTransition pause = new PauseTransition(Duration.seconds(3));

		System.out.println(text);
		
		pause.setOnFinished(event->{
			switchScene(createWinner(won));
		});
		pause.play();
	}

	/**
	 * isValidButton
	 * @param x -> Column position of the button.
	 * @param y -> Row position of the button.
	 * @return True if button is valid. False if otherwise.
	 */
	private boolean isValidButton(int x, int y) {
		// Return false if button is already occupied.
		if (buttonMatrix[y][x].getPlayerOccupies() != 0) {
			return false;
		}

		/**
		 * If button is not in the bottom row, then check if it's lowest button
		 * 		in its column to be occupied. If it isn't the lowest to be
		 * 		occupied, then return false.
		 */
		if (y < (ROW_COUNT - 1)) {
			if (buttonMatrix[y+1][x].getPlayerOccupies() == 0) {
				return false;
			}
		}

		return true;
	}

	private void resetButton(GameButton button) {
		button.setPlayerOccupies(0);
		button.setStyle(
			"-fx-focus-color: transparent; " +
			"-fx-background-color: " +
			colorHexToCSS(Color.rgb(255, 255, 255, 0.6).toString())
		);
		button.getBackground();
	}

	private void disableAllButtons() {
		for (int rowIndex = 0; rowIndex < this.ROW_COUNT; ++rowIndex) {
			for (int colIndex = 0; colIndex < this.COLUMN_COUNT; ++colIndex) {
				this.buttonMatrix[rowIndex][colIndex].setDisable(true);
			}
		}
	}

	/**
	 * =========================================================================
	 * Winner Scene Functions
	 * =========================================================================
	 */

	/**
	 * createWinner
	 * @return The winner scene. In the winner scene, the winner is displayed,
	 * 		and the displays two buttons: Play Again and Exit.
	 */
	private Scene createWinner(String text) {
		Button playAgainButton = createPlayButton("Play again");

		Button exitButton = createExitButton();

		Text winnerText = new Text();
		// String s = text.getText(text);
		winnerText.setText(text);
		winnerText.setFont(Font.font ("Arial", 50));
		winnerText.setFill(Color.DARKGOLDENROD);

		Reflection r = new Reflection();
		r.setFraction(0.7f);

		winnerText.setEffect(r);

		Image image = new Image("winner.jpg");
		ImageView winner = new ImageView();
		winner.setImage(image);
		winner.setFitWidth(350);
		winner.setFitHeight(350);

		HBox wtext = new HBox (winnerText);
		wtext.setAlignment(Pos.CENTER);

		HBox buttonRow = new HBox(playAgainButton, exitButton);
		buttonRow.setSpacing(100);
		buttonRow.setAlignment(Pos.BOTTOM_CENTER);
		
		VBox vbox = new VBox(winner, wtext, buttonRow);
		vbox.setSpacing(100);
		vbox.setAlignment(Pos.CENTER);
		
		vbox.setMinHeight(HEIGHT);
		vbox.setPrefHeight(HEIGHT);
		
		StackPane centeredLayout = new StackPane();
		centeredLayout.getChildren().add(vbox);
		centeredLayout.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, new CornerRadii(5), Insets.EMPTY)));

		final Duration Sec_1 = Duration.millis(2000);
		final Duration Sec_2 = Duration.millis(3000);

		FadeTransition ft = new FadeTransition(Sec_2);
		ft.setFromValue(1.0f);
		ft.setToValue(0.3f);
		ft.setCycleCount(2);
		ft.setAutoReverse(true);

		TranslateTransition tt = new TranslateTransition(Sec_1);
		tt.setFromX(0f);
		tt.setToX(100f);
		tt.setCycleCount(2);
		tt.setAutoReverse(true);

		RotateTransition rt = new RotateTransition(Sec_2);
		rt.setByAngle(360);
		rt.setCycleCount(1);
		rt.setAutoReverse(true);

		ScaleTransition st = new ScaleTransition(Sec_1);
		st.setByX(1.5f);
		st.setByY(1.5f);
		st.setCycleCount(2);
		st.setAutoReverse(true);

		ParallelTransition sq = new ParallelTransition(winnerText, ft,tt,rt,st);
		sq.play();


		Scene scene = new Scene(centeredLayout, WIDTH, HEIGHT);
		return scene;
	}
	


	/**
	 * =========================================================================
	 * Other Functions
	 * =========================================================================
	 */

	/**
	 * createPlayButton
	 * @param text -> Label to be used for the play button.
	 * @returns a button that returns
	 */
	private Button createPlayButton(String text) {
		Button playButton = new Button(text);

		playButton.setOnAction(e->{
			Scene gameScene = createGameScene();
			switchScene(gameScene);
		});

		return playButton;
	}
	
	/**
	 * createExitButton
	 * @returns The exit button, which exits the program when clicked.
	 */
	private Button createExitButton() {
		Button exitButton = new Button("Exit");

		exitButton.setOnAction(e->{
			Platform.exit();
		});

		return exitButton;
	}

	/**
	 * initThemes
	 * Initializes `this.themes` to have size of `this.numberOfThemes`. The
	 * 		theme colors are also set here as well.
	 */
	private void initThemes() {
		themes = new Theme[numberOfThemes];

		for (int i = 0; i < numberOfThemes; ++i) {
			themes[i] = new Theme();
		}

		themes[0].playerOne = Color.web("0x87AAAA");
		themes[0].playerTwo = Color.web("0xF6D7A7");
		themes[0].winner = Color.web("0xC37B89");

		themes[1].playerOne = Color.web("0x852747");
		themes[1].playerTwo = Color.web("0xFF7777");
		themes[1].winner = Color.web("0xFFFEB7");

		themes[2].playerOne = Color.web("0xA2CDCD");
		themes[2].playerTwo = Color.web("0xD57E7E");
		themes[2].winner = Color.web("0xC490E4");
	}

	/**
	 * colorHexToCSS
	 * @param c -> in format "0x??????".
	 * @return `c` but in format "#??????".
	 */
	private String colorHexToCSS(String c) {
		return ("#" + c.substring(2));
	}

	/**
	 * getPlayerColor
	 * @param playerNumber -> 1 or 2
	 * @returns the Color from the current theme corresponding to
	 * 		`playerNumber`.
	 */
	private Color getPlayerColor(int playerNumber) {
		if (playerNumber == 1)
			return themes[currentThemeIndex].playerOne;
		else if (playerNumber == 2)
			return themes[currentThemeIndex].playerTwo;
		return Color.BLACK;
	}

	/**
	 * getCurrentPlayerColor
	 * @returns the current player color.
	 */
	private Color getCurrentPlayerColor() {
		return getPlayerColor(currentPlayer);
	}

	/**
	 * setMainBPBackgroundToCurrentPlayer
	 * Sets the main game border pane to be equal to current player's color.
	 */
	private void setMainBPBackgroundToCurrentPlayer() {
		this.mainGameBP.setBackground(
			new Background(
				new BackgroundFill(
					getPlayerColor(currentPlayer),
					CornerRadii.EMPTY,
					Insets.EMPTY
				)
			)
		);
	}

	/**
	 * switchScene
	 * Switches the current scene to `sceneToSwitch` on the stage.
	 * 
	 * @param sceneToSwitch
	 */
	private void switchScene(Scene sceneToSwitch) {
		sceneToSwitch.getRoot().setStyle("-fx-font-family: 'arial'");
		sceneToSwitch.getRoot().requestFocus();
		this.primaryStage.setScene(sceneToSwitch);
	}

	private String getCurrentPlayerAsStr() {
		return (currentPlayer % 2 == 1)
			? "Player One"
			: "Player Two";
	}

	/**
	 * =========================================================================
	 * Public
	 * =========================================================================
	 */

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;

		primaryStage.setTitle("Welcome to Connect 4!");
		
		initThemes();
		currentPlayer = 1;
		currentThemeIndex = 0; 

		moveStack = new Stack<Move>();
		moveHistory = new ListView<Text>();
		moveHistory.setMaxSize(WIDTH/2, 60);

		switchScene(createSplashScene());
		primaryStage.show();
	}
}
