import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Contains game's buttons, guides, different draw methods.
 * Main loop of the game and repetition of the game is depending on
 * this class. Class controls the game.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class SpaceShip_Game {

	// Data fields
	// Canvas sizes
	private final static int CANVAS_HEIGHT = 900;
	private final static int CANVAS_WIDTH = 1500;
	private static boolean gameStarted = false;

	// Button attributes
	private double buttonx;
	private double buttony;
	private double halfWidth;
	private double halfHeight;
	private int startingCounter=-1;
	private static boolean countingFinished;
	public static boolean gameMuted;

	// Score table attributes
	private static double score_x= -0.6;
	private static double score_y = 0.3;
	private static double score_dif = 0.15;
	public static void main(String[] args) {

		// Creates the canvas
		StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		StdDraw.setXscale(-1.0, 1.0);
		StdDraw.setYscale(-1.0, 1.0);
		StdDraw.enableDoubleBuffering();
		gameMuted=false; // Game is not muted

		// Loop of the game
		while(true)
		{
			// Calls datas from the database
			DataBase.getDataBase();
			// Creates a spaceGame object and calls mainMenu method
			SpaceShip_Game spaceGame = new SpaceShip_Game();
			spaceGame.mainMenu();

			// Checks if game is started
			if(gameStarted==true)
			{
				// Creates the object which are necessary for the game
				Player player = new Player();
				Game_Board board = new Game_Board(player);
				spaceGame.countingBeforeStart(board,player); // Creates a 3 second countdown

				// Game starts after counting is finished
				while(countingFinished==false)
				{
					StdDraw.clear();
					Game_Board.backGround();
					copyRights();
					player.draw();
					spaceGame.drawCounting();
					StdDraw.show();
				}

				// When counting is finished, mainGameLoop starts.
				if(countingFinished==true)
					spaceGame.mainGameLoop(board,player);	

				// After the game is finished updates database and resets objects.
				DataBase.setDataBase(player.getScore(),player.getSurvivingTime());
				player=null;		
				board=null;
			}

			StdDraw.show(); 
		}
	}
	/**
	 * Main loop of the game. While game is running, this method
	 * is continue to work.
	 * @param board Board object of the game.
	 * @param player Player object of the game.
	 */
	public void mainGameLoop(Game_Board board , Player player) 
	{
		// While game is running call the canvas method to update everything
		while(board.gameIsRunning())
		{
			board.canvas();
		}

		// When game finish, stop timers and put the game over screen
		StdDraw.clear();
		player.stopTimers();
		Game_Board.backGround();
		copyRights();
		gameOver(player);

	}

	/**
	 * Draws the score table of the game.
	 * Takes values from the database.
	 */
	private void drawScores()
	{
		StdDraw.setPenColor(new Color(255, 102, 0));
		StdDraw.filledRectangle(score_x,score_y,halfWidth,halfHeight/3.2);
		StdDraw.setPenColor(StdDraw.MAGENTA);
		StdDraw.filledRectangle(score_x,score_y-score_dif,halfWidth,halfHeight/3.2);
		StdDraw.setPenColor(new Color(153, 0, 204));
		StdDraw.filledRectangle(score_x,score_y-2*score_dif,halfWidth,halfHeight/3.2);
		StdDraw.setPenColor(StdDraw.ORANGE);
		StdDraw.filledRectangle(score_x,score_y-3*score_dif,halfWidth,halfHeight/3.2);
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.filledRectangle(score_x,score_y-4*score_dif,halfWidth,halfHeight/3.2);
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 36) );
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(score_x,score_y+score_dif, "Records: ");
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 24) );
		StdDraw.text(score_x,score_y, "Best Score: " + DataBase.personalBestScore);
		StdDraw.text(score_x,score_y-score_dif, "Default Enemy Kills: " + DataBase.defaultEnemyKillCount);
		StdDraw.text(score_x,score_y-2*score_dif, "Elite Enemy Kills: " + DataBase.eliteEnemyKillCount);
		StdDraw.text(score_x,score_y-3*score_dif, "Boss Enemy Kills: " + DataBase.bossEnemyKillCount);
		StdDraw.text(score_x,score_y-4*score_dif, "Best Time: " + DataBase.personalBestSurvivingTime/60 + " Min. and " + DataBase.personalBestSurvivingTime%60 + " Sec.");
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.filledRectangle(score_x,score_y-5.2*score_dif,halfWidth,halfHeight/2);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(score_x,score_y-5.2*score_dif, "Reset Records");
		guideLines(score_x,score_y);
	}
	/**
	 * Draws the guidelines of the game
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	private void guideLines(double x,double y)
	{
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 36) );
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.text(-x,score_y+score_dif, "GuideLines: ");
		StdDraw.picture(-x, -0.11, "pictures/icons/Guide.png",0.5,0.9);
	}
	/**
	 * Draws a game over screen
	 * @param p Player Object
	 */
	private void gameOver(Player p)
	{
		Explosion exp = new Explosion(p);
		exp.draw();
		Game_Board.waitTime(500);

		// Countdown until the player is transferred to the main menu
		for (int i = 5; i > 0; i--) 
		{
			// Draws the gameOver screen
			Game_Board.backGround();
			copyRights();
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.setFont( new Font("Helvetica", Font.BOLD, 70) );	
			StdDraw.picture(0, 0.5, "pictures/icons/GameOver.png",0.5,0.4);
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.setFont( new Font("Helvetica", Font.BOLD, 60) );
			StdDraw.text(0, 0.2, "Score: " + String.valueOf(p.getScore()));//0.5 0.2 -0.2
			StdDraw.setFont( new Font("Helvetica", Font.BOLD, 40) );
			StdDraw.setPenColor(StdDraw.MAGENTA);
			StdDraw.text(0, 0, "Surviving Time: " + String.valueOf(p.getSurvivingTime()/60) + " Minutes and " + String.valueOf(p.getSurvivingTime()%60) + " Seconds");
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.text(0, -0.2,"You will be in mainmenu in 5 seconds");	
			StdDraw.setFont( new Font("Helvetica", Font.BOLD, 55) );
			StdDraw.setPenColor(StdDraw.RED);
			StdDraw.text(0, -0.4,String.valueOf(i));
			StdDraw.show();	
			Game_Board.waitTime(1000);
			StdDraw.clear();
		}
		Game_Board.backGround();
		copyRights();
	}

	/**
	 * Initiliazes the button attributes,
	 * draws them and wait for user to start
	 * the game.
	 */
	private void mainMenu()
	{
		// Draw background
		Game_Board.backGround();
		copyRights();

		// Initiliazes the variables
		buttonx = 0;
		buttony = -0.25;
		halfWidth = 0.24;
		halfHeight = 0.15;

		gameStarted=false;
		countingFinished=false;

		// Draws buttons and waits for a selection from the player
		drawButtons();
		StdDraw.show();
		clickEvent();
	}

	/**
	 * Draws buttons of the game. Also calls
	 * drawScore and drawIcon methods to draw all of the
	 * objects.
	 */
	private void drawButtons()
	{
		// Draws a start button
		StdDraw.setPenColor(StdDraw.GREEN);
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 80) );
		StdDraw.picture(buttonx, buttony, "pictures/icons/StartIcon.png",0.5,0.4);
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 20) );

		// Draws scores and icons
		drawScores();
		drawIcons();
	}

	/**
	 * Draws the icon textures and 
	 * headings of the game
	 */
	private void drawIcons()
	{
		// Draws a boss image
		StdDraw.picture(buttonx,buttony+0.4, "pictures/enemy_3.png",0.4,0.4);
		//Writes the headers
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 80) );
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.text(0,0.8, "Space Invaders");
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 60) );
		StdDraw.setPenColor(StdDraw.CYAN);
		StdDraw.text(0,0.6, "Mission Mars");
		// Draws spaceship
		StdDraw.picture(0, -0.6, "pictures/spaceShip.PNG",0.2,0.3);
	}
	
	/**
	 * This method runs until player press the start button.
	 * Follows where the player is pressing.
	 */
	private void clickEvent()
	{
		double x = 0, y = 0;
		boolean playerChoosed = false;
		// Loop continues until player start the game
		while (playerChoosed==false) 
		{
			drawMute(); // Draws mute icon
			StdDraw.show(); // Shows drawings
			if (StdDraw.isMousePressed()) // Checks if mouse pressed or not
			{ 
				x = StdDraw.mouseX(); // Get x coordinate of point
				y = StdDraw.mouseY(); // Get y coordinate of point

				// Checks if the player press the start button or not
				// If so game is starting after a countdown
				if(x<=buttonx+halfWidth && x>=buttonx-halfWidth && y<=buttony+halfHeight && y>=buttony-halfHeight)
				{
					playerChoosed=true;
					gameStarted=true;
				}
				
				// Checks if the player pressed the mute button or not
				if (x<=0.8+0.1 && x>=0.8-0.1 && y<=0.8+0.1 && y>=0.8-0.1)
				{
					// Switches the game's sound situation
					Game_Board.waitTime(200);
					if(SpaceShip_Game.gameMuted==false)
						SpaceShip_Game.gameMuted=true;
					else if (SpaceShip_Game.gameMuted=true)
						SpaceShip_Game.gameMuted=false;
				}
				
				// Checks if the player pressed the reset records button or not
				if (x<=score_x+halfWidth && x>=score_x-halfWidth && y<=score_y-5.2*score_dif+halfHeight/2 && y>=score_y-5.2*score_dif-halfHeight/2)//x,y-5.2*dif		 -0.6; 0.3; 0.2; 0.3-5.2*dif
				{
					Game_Board.waitTime(200);
					resetRecords();
					drawScores();
				}
			}

		}
		Game_Board.waitTime(200);		
	}
	/**
	 * Draws the counting before the game starts.
	 * @param b Board Object
	 * @param p Player Object
	 */
	public void countingBeforeStart(Game_Board b,Player p)
	{
		// Counting Timer and task
		Timer countingTimer = new Timer();
		TimerTask counting;
		// Starts the countdown sound effect
		SoundCall sound = new SoundCall();
		sound.playSound("CountDown");		
		counting = new TimerTask() {

			@Override
			public void run() {
				startingCounter++;
				// Every seconds count down timer occurs,
				// after 3 seconds timer stopes and game starts.
				if(startingCounter>3)
				{
					countingFinished=true;
					startingCounter=0;
					countingTimer.cancel();
					countingTimer.purge();

				}			

			}
		};
		countingTimer.schedule(counting, 0, 1000); 
	}
	/**
	 * Draws the counting until the game starts
	 */
	private void drawCounting()
	{
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 100) );
		StdDraw.setPenColor(StdDraw.RED);
		// After the startingCounter finishes draws "GO"
		if(3-startingCounter==0)
		{
			StdDraw.text(0,0, "GO");	
		}
		else
		{
			StdDraw.text(0,0, String.valueOf(3-startingCounter));	
		}
	}
	/**
	 * Draws the mute button depending on the game's sound
	 * preference.
	 */
	public void drawMute()
	{
		// If game is muted draw mute, if not draw unmute
		if(SpaceShip_Game.gameMuted==false)
			StdDraw.picture(0.8,0.8, "pictures/icons/unmute.png",0.1,0.1);
		if(SpaceShip_Game.gameMuted==true)
			StdDraw.picture(0.8,0.8, "pictures/icons/mute.png",0.1,0.1);
	}
	
	/**
	 * Resets records of the players.
	 */
	public void resetRecords()
	{
		DataBase.personalBestScore=0;
		DataBase.defaultEnemyKillCount=0;
		DataBase.eliteEnemyKillCount=0;
		DataBase.bossEnemyKillCount=0;
		DataBase.personalBestSurvivingTime=0;
		DataBase.setDataBase(0, 0);
	}
	
	/**
	 * Draws copyrights
	 */
	public static void copyRights()
	{
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.filledRectangle(0.94, -0.97,0.06, 0.03);
		StdDraw.filledRectangle(-0.94, -0.97,0.06, 0.03);
		StdDraw.setFont( new Font("Arial", Font.BOLD, 20) );
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.text(0.94, -0.97, "V 1.0.0");
		StdDraw.text(-0.94, -0.97, "By AGB");
	}
}
