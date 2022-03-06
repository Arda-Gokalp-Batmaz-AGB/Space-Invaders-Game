import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Game is playing in the board that created by this class. It contains
 * main timers and operations of the game.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class Game_Board {
	// Data fields of the class

	// Enemy spawn timer and timer task
	private Timer spawnTimer = new Timer();
	private TimerTask spawn;
	// Supply spawn timer and timer task
	private Timer supplyTimer = new Timer();
	private TimerTask supplySpawn;
	Player player;
	private ArrayList<Enemy> enemies; // Enemiy array
	private boolean timerIsRunning=false;
	public static ArrayList<Bullet> extraBullets; // Stores extrabullets
	public  ArrayList<Supplies> supplyList; // Stores supplies
	public Level currentLevel; // Mentiones current level of the game
	private Level[] levels; // level array
	private int level; // Indicates which level it is
	// Enemy Kill counters
	private int defaultEnemyCounter;
	private int eliteEnemyCounter;
	private int bossEnemyCounter;
	private Random rGen = new Random(); 
	public static boolean gameStopped; // Checks if game is stopped or not
	public static boolean gameFinished; // Checks if game is finished or not
	private ArrayList<Explosion> explosionList; // Array of explosion animations

	/**
	 * Constructor of the class, creates a game_board object.
	 * @param p Player object
	 */
	public Game_Board(Player p)
	{
		// Initiliazes the data fields
		player = p;
		enemies = new ArrayList<Enemy>();
		extraBullets = new ArrayList<Bullet>();
		supplyList = new ArrayList<Supplies>();
		createLevels(); // Creates levels
		currentLevel=levels[0]; // Set current level to level 1
		level=0;
		createSupplies(); // Creates supplies
		explosionList = new ArrayList<Explosion>();
		gameStopped=false;
		gameFinished=false;

		p.survivingCounter(); // Starts surviving counter
	}

	/**
	 * This method updates the graphics of the game
	 */
	public void canvas()
	{
		StdDraw.clear(); // Clears the board
		backGround(); // Draws background

		// Draws player's animations, textures and bullets
		player.draw();
		player.fire();
		player.updateBullets();

		updateSupplies(); // Updates supplies
		// Updates and spawns enemies
		spawnEnemies();
		updateEnemies();

		drawScore(); // Draws score
		drawExplosions(); // Draws explosion animations

		// Game stops if user clicked the space
		if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE))
		{
			// Pauses the game
			StdDraw.pause(10);
			waitTime(100);
			pause_Game();
		}

		StdDraw.show(); // Draws the graphics
	}

	/**
	 * Keeps the code sleep for the specified amount of time
	 * @param ms Waittime
	 */
	public static void waitTime(int ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Depending on the current phase of the game
	 * Spawns a new enemy in every 500 ms
	 */
	public void spawnEnemies()
	{
		// Checks if timer is started or not
		if(timerIsRunning==false)
		{
			spawn = new TimerTask() {
				@Override
				public void run() {

					// Checks if the game is stopped or not
					if(gameStopped==false)
					{
						// If in the currentLevel there are default enemies which doesn't spawned yet
						// spawn them.
						if(defaultEnemyCounter<currentLevel.getDefaultEnemyCount())
						{
							// Creates a new default enemy, adds it to enemies array
							// and increases it's counter.
							Enemy defaultEnemy = new DefaultEnemy("DefaultEnemy");
							enemies.add(defaultEnemy);
							defaultEnemyCounter++;
						}
						// If in the currentLevel there are elite enemies which doesn't spawned yet
						// spawn them.
						if(eliteEnemyCounter<currentLevel.getEliteEnemyCount())
						{
							// Creates a new elite enemy, adds it to enemies array
							// and increases it's counter.
							Enemy eliteEnemy = new EliteEnemy("EliteEnemy",player);
							enemies.add(eliteEnemy);
							eliteEnemyCounter++;
						}
						// If in the currentLevel there are boss enemies which doesn't spawned yet
						// spawn them
						if(bossEnemyCounter<currentLevel.getBossCount())
						{
							// Creates a new boss enemy, adds it to enemies array
							// and increases it's counter.
							Enemy BossEnemy = new BossEnemy("BossEnemy",player);
							enemies.add(BossEnemy);
							bossEnemyCounter++;
						}
						// Checks if the all of the enemies in the current level is dead. If so, the game starts to the new level.
						if(defaultEnemyCounter==currentLevel.getDefaultEnemyCount() && eliteEnemyCounter==currentLevel.getEliteEnemyCount() && bossEnemyCounter==currentLevel.getBossCount()&& enemies.size()==0)
						{
							// Begins the next level, if it's the last level of the game,
							// game starts from the first level.
							if(level+1<levels.length)
							{
								currentLevel = levels[level+1];
								level++;
								defaultEnemyCounter=0;
								eliteEnemyCounter=0;
								bossEnemyCounter=0;
							}
							else
							{
								currentLevel= levels[0];
								level = 0;
								defaultEnemyCounter=0;
								eliteEnemyCounter=0;
								bossEnemyCounter=0;
							}

						}
						// When game finish, code closes timers
						if(gameFinished==true)
						{
							spawnTimer.cancel();
							spawnTimer.purge();
						}			
					}
				}
			};
			spawnTimer.schedule(spawn, 0, 500);
		}
		timerIsRunning=true;

	}

	/**
	 * Method updates the enemies, checks if they are dead or not
	 * runs their methods.
	 */
	public void updateEnemies()
	{
		extraBulletsUpdate(); // Updates extra bullets.

		// Checks if there is at least one enemy.
		if(enemies.size()>0)
		{
			// Loop checks every enemy object in the array.
			for (int i = 0; i < enemies.size(); i++) 
			{
				// If structure checks if it's alive or not.
				if(enemies.get(i).isAlive(player)==true)
				{
					// If its alive, make it fire, move, update and draw it.
					enemies.get(i).moveEnemy();
					enemies.get(i).draw();
					enemies.get(i).fire();
					enemies.get(i).updateBullets();

				}
				else
				{
					// Otherwise remove it from the list and create a explosion animation.
					Explosion explosion = new Explosion(enemies.get(i));
					explosionList.add(explosion);

					enemies.remove(enemies.get(i));
				}

			}	
		}
	}

	/**
	 * Method updates extra bullets which comes
	 * from the dead enemies.
	 */
	public void extraBulletsUpdate()
	{
		// Checks if there is a bullet
		if(Game_Board.extraBullets.size()>0)
		{
			// Loop checks every extra bullet
			for (int i = 0; i < Game_Board.extraBullets.size(); i++) 
			{
				// Updates extrabullet's coordinates depending on their velocities
				Game_Board.extraBullets.get(i).setY(Game_Board.extraBullets.get(i).getY()+Game_Board.extraBullets.get(i).getVy());
				Game_Board.extraBullets.get(i).setX(Game_Board.extraBullets.get(i).getX()+Game_Board.extraBullets.get(i).getVx());

				// Program draws them.
				if(!(i >= Game_Board.extraBullets.size()))
					Game_Board.extraBullets.get(i).draw();

			}				
		}
	}

	/**
	 * This method creates random supplies in every 10 seconds
	 */
	public void createSupplies()
	{
		// Determines the supply types
		String[] SupplyTypes = new String[2];
		SupplyTypes[0]="Health_Supply";
		SupplyTypes[1]="Fire_Supply";

		supplySpawn = new TimerTask() {
			@Override
			public void run() 
			{
				// Checks if game is stopped
				if(gameStopped==false)
				{
					// Chooses a random supply
					int randomSupply=rGen.nextInt(SupplyTypes.length);

					// Checks if there are more than 2 supplies
					if(supplyList.size()<2)
					{
						// Creates a new supply and adds it to array.
						Supplies supply = new Supplies(SupplyTypes[randomSupply],player);
						supplyList.add(supply);

					}	
				}
				// Checks if game is finished
				if(gameFinished==true)
				{
					supplyTimer.cancel();
					supplyTimer.purge();
				}	
			}
		};
		supplyTimer.schedule(supplySpawn, 0, 10000);			
	}

	/**
	 * Updates supplies in the game.
	 */
	public void updateSupplies()
	{
		// Checks if there is a supply
		if(supplyList.size()>0)
		{
			// Loop checks every supply
			for (int i = 0; i < supplyList.size(); i++) 
			{
				// Removes the supply if it's in the corners of board.
				if(supplyList.get(i).getX()+supplyList.get(i).getVx()>1)
				{
					supplyList.remove(supplyList.get(i));
				}	

				// Checks if there is a supply or not
				if(supplyList.size()>0)
				{
					// If supply doesn't hit by the player, draw and update it.
					if(supplyList.get(i).isAlive(player)==true)
					{
						// Updates position and drawing of the supply.
						supplyList.get(i).setY(supplyList.get(i).getY()+supplyList.get(i).getVy());
						supplyList.get(i).setX(supplyList.get(i).getX()+supplyList.get(i).getVx());
						supplyList.get(i).draw();
					}
					else
					{
						// Otherwise remove it and give supply to player.
						supplyList.get(i).giveSupply();
						supplyList.remove(supplyList.get(i));

					}
				}

			}					
		}		
	}

	/**
	 * Draws background of the game
	 */
	public static void backGround()
	{
		StdDraw.picture(0, 0, "pictures/spaceBackGround.jpg",2,2);
	}

	/**
	 * Returns extra bullets array
	 * @return Extra bullets array
	 */
	public ArrayList<Bullet> getExtraBullets() {
		return extraBullets;
	}

	/**
	 * Returns that if player is dead or not, if so
	 * game stops.
	 * @return Game finished or not
	 */
	public boolean gameIsRunning()
	{
		// Checks if player is alive or not
		if(player.isAlive(enemies,extraBullets)==false)
		{
			// When player got hit, it's health decreases by one
			player.setHealth(player.getHealth()-1);
		}

		// If player's health is zero or less game finishes
		if(player.getHealth()<=0)
		{
			canvas();
			gameFinished=true;
			return false;
		}

		return true;
	}

	/**
	 * Draws the statistics of the game
	 */
	public void drawScore()
	{
		StdDraw.setPenColor(StdDraw.YELLOW);
		StdDraw.setFont( new Font("Helvetica", Font.BOLD, 30) );
		StdDraw.text(0.80, -0.85, "Level: " + String.valueOf(level+1));
		StdDraw.text(0.80, -0.95, "Score: " + String.valueOf(player.getScore()));
		StdDraw.text(0.80, -0.75, "Time: " + String.valueOf(player.getSurvivingTime()));
	}

	/**
	 * Creates specified amount of levels in a for loop
	 * depending on level formula.
	 */
	public void createLevels()
	{
		// Specifies amount of levels and
		// initiliazes the counts.
		levels = new Level[12];
		int defaultEnemy = 0;
		int eliteEnemy = 0;
		int bossEnemy = 0;

		// This loop builds all levels enemy count
		for (int i = 0; i < levels.length; i++) {

			// 2 Bosses appears every 12 round
			if(i%11==0 && i!=0)
			{
				bossEnemy=2;
			}

			// 1 Boss enemy appears every 5 round
			else if(i%4==0 && i!=0)
			{
				bossEnemy=1;
			}
			// Creates a level object
			levels[i]= new Level(defaultEnemy+4,eliteEnemy,bossEnemy);
			bossEnemy=0;

			// Increases counters
			defaultEnemy=defaultEnemy+2;
			eliteEnemy=eliteEnemy+1;
		}
	}

	/**
	 * Draws explosions of the enemies when they are killed by
	 * the player.
	 */
	public void drawExplosions()
	{
		// Loop checks every explosion element in the array
		for (int i = 0; i < explosionList.size(); i++) {

			explosionList.get(i).draw(); // Draws the explosion

			// Checks if the animation is over, if so removes it from the list
			if(explosionList.get(i).getFrameTexture()!=null && explosionList.get(i).getFrameTexture().equalsIgnoreCase(""))
			{
				explosionList.remove(i);
			}
		}
	}

	/**
	 * Method switches the game's situation when user click
	 * the space button. It pauses or starts the game.
	 */
	public void pause_Game()
	{
		// Updates canvas
		canvas();
		StdDraw.text(0, 0, "Game Paused");
		StdDraw.show();
		gameStopped=true; // Stop the game

		// Wait until the player presses the button again. 
		while(true)
		{
			if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) 
			{
				StdDraw.pause(500);
				gameStopped=false;
				break;
			}
		}
	}
}
