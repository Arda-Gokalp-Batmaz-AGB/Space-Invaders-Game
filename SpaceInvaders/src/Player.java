import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class holds a player's all attributes, it's inherited
 * from the Creature class and has all of the methods and data fields
 * in the creature class. Player is represented by a spaceship. The
 * player object is creating from this class.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class Player extends Creature{

	// Data fields of the player class
	private boolean invincibility; // Is ship is invincible or not
	private int score; // Score of the player
	private int invincibleTimeCounter; // Counts invincible time of the player
	private String fireType; // Type of fire
	private int supplyTime; // Supply's time
	private int currentExplosionFrame; // Current explosion frame in the explosion animation
	private int currentfireFrame; // Current frame of the exhaust of ship
	private String explosionFrameTexture; // Texture of the current explosion frame
	private String fireFrameTexture; // Texture of the current fire frame
	private int survivingTime; // Surviving time of the player in seconds
	// Surviving timer and timer task
	private Timer survivingTimer = new Timer(); 
	private TimerTask survive;	
	public Player()
	{
		// Initializes the data fields
		// Starting coordinates
		x=0;
		y=0;
		health=3; // Default health
		width=0.17;
		height=0.3;
		fireType="default";
		supplyTime=0;

		bullets = new ArrayList<Bullet>();
		invincibility = false; // When game starts, ship is not invincible.
		score=0;

		// Initializes animation frames.
		currentExplosionFrame=0;
		currentfireFrame=1;
		explosionFrameTexture="";
		fireFrameTexture="";
		survivingTime=-3;
		fireAnimation(); // Starts the exhaust animation of ship.

	}
	/**
	 * Method moves the player depening on the user's mouse
	 * coordinates.
	 */
	public void movePlayer()
	{
		// Ship moves depending on the user's mouse moves 
		// and it can not exceed the pre determined borders
		if(!(-(StdDraw.mouseY()) > 1.0 - this.getHeight()/2)) 
		{
			if((StdDraw.mouseY()<0))
				y = StdDraw.mouseY(); // Get y coordinate of point
		}

		if (!(Math.abs(StdDraw.mouseX()) > 1.0 - this.getWidth()/2)) 
			x = StdDraw.mouseX(); // Get x coordinate of point
	}

	/**
	 * Draws the spaceship, and depending on the situation it also 
	 * draws animations.
	 */
	@Override
	public void draw() {
		this.movePlayer(); // Calls moveplayer method
		StdDraw.pause(10);
		// Draws the spaceship
		StdDraw.picture(this.getX(), this.getY(), "pictures/spaceShip.PNG",this.getWidth(),this.getHeight());

		// Draws the animation of the exhaust
		if(!fireFrameTexture.equalsIgnoreCase(""))
			StdDraw.picture(this.getX(), this.getY()-0.17, "pictures/flames/"+ fireFrameTexture +".png",0.04,0.1);

		// Draws explosion if the player got hit by the enemy
		if(!explosionFrameTexture.equalsIgnoreCase(""))
			StdDraw.picture(this.getX(), this.getY(), "pictures/explosions/"+ explosionFrameTexture +".PNG",this.getWidth()*1.3,this.getHeight()*1.3);
		// Draws a shield to the player for 3 seconds if it got hit by the enemy
		if(invincibility==true)
			StdDraw.picture(this.getX(), this.getY(), "pictures/shield.PNG",this.getWidth()*1.3,this.getHeight()*1.3);

		healthBar(); // Draws health bar	

	}
	/**
	 * This method allows player to fire, fire types
	 * changes if player take a bullet supply. Player fires
	 * ever 700ms.
	 */
	public void fire()
	{
		Player p = this;
		// Checks if timer is running or not
		if(timerIsRunning==false)
		{
			fire = new TimerTask() {

				@Override
				public void run() {

					// If game is not stopped timer runs
					if(Game_Board.gameStopped==false)
					{

						SoundCall sound = new SoundCall(); // Creates sound object

						// If fire type is default it will fire 1 default bullet
						if(fireType.equalsIgnoreCase("default"))
						{
							// Creates a default bullet and adds it to bullets array
							Bullet bullet = new DefaultBullet(p,StdDraw.BLUE,0,0.02);
							bullets.add(bullet);	
							sound.playSound("shoot"); // Calls a shoot sound effect
						}
						// If fire type is multi it will fire 3 default bullet
						else if(fireType.equalsIgnoreCase("multi"))
						{
							// Creates multiple bullets and adds them to bullets array
							MultipleBullet bullet = new MultipleBullet(p,StdDraw.BLUE,0.02,0.02,3);
							Bullet[] b = bullet.getBullets();
							for (int i = 0; i < b.length; i++) {
								bullets.add(b[i]);
							}
							supplyTime++;
							sound.playSound("shoot"); // Calls a shoot sound effect
						}

						// If player took a bullet supply, bullets are
						// Multiplies for 10 seconds
						if(supplyTime>=10)
						{
							supplyTime=0;
							fireType="default";
						}
					}
				}
			};
			fireTimer.schedule(fire, 0, 700); 
		}
		timerIsRunning=true;

	}
	/**
	 * Updates player's bullets
	 */
	public void updateBullets()
	{
		// Checks if player has a bullet or not
		if(this.getBullets().size()>0)
		{
			// This for loop calls every bullet of the player in the bullet array.
			for (int i = 0; i < this.getBullets().size(); i++) 
			{
				// Updates bullet's positions
				this.getBullets().get(i).setY(this.getBullets().get(i).getY()+this.getBullets().get(i).getVy());
				this.getBullets().get(i).setX(this.getBullets().get(i).getX()+this.getBullets().get(i).getVx());

				// If bullet is dead, removes it from the bullet list
				if(this.getBullets().get(i).isAlive()== false)
				{
					this.getBullets().remove(this.getBullets().get(i));
				}
				// Otherwise draws it
				if(!(i >= this.getBullets().size()))
				{
					this.getBullets().get(i).draw();
				}
			}	
		}
	}

	/**
	 * Method checks if player got hit by the enemy and is it
	 * alive or not.
	 * @param enemies Array of the enemies
	 * @param extraBullets Extra bullet which is come from death enemies
	 * @return Player is got hit or not
	 */
	public boolean isAlive(ArrayList<Enemy> enemies, ArrayList<Bullet> extraBullets)
	{
		// Checks every enemy in the array list.
		for (int i = 0; i <enemies.size(); i++) 
		{
			// Checks if player got hit by the enemy bullets
			// It uses helper bulletHitPlayer method.
			if(bulletHitPlayer(enemies.get(i).getBullets())==false)
			{
				return false;
			}			
		}
		// Checks if player got hit by the extra bullets
		// It uses helper bulletHitPlayer method.
		if(bulletHitPlayer(extraBullets)==false)
		{
			return false;
		}
		return true;
	}
	/**
	 * Helper method of the isAlive method. It checks
	 * if player got hit by the enemy bullets or not
	 * if it's got hit, returns true otherwise false
	 * @param bullets List of the bullets
	 * @return Got hit or not
	 */
	private boolean bulletHitPlayer(ArrayList<Bullet> bullets)
	{
		// Checks is there any bullet and player is invincible or not
		if(bullets.size()>0  && invincibility==false)
		{
			// Loop checks every bullet
			for (int j = 0; j < bullets.size(); j++) 
			{
				// Calls hitBoxGotHit method of the player,
				// if any bullet hit the any hitbox of the player,
				// Program applies the if structure.
				if(hitBoxGotHit(bullets.get(j))==true)
				{
					// This structure works when player is got hit
					bullets.remove(j); // removes bullet
					invincibility=true; // Makes player invincible for 3 sec
					invincibilityCounter(); // Starts invincibilityCounter method
					hitAnimation(); // Creates a hit animation

					SoundCall sound = new SoundCall(); // Creates a sound object
					sound.playSound("Explosion"); // Plays explosion sound
					return false;
				}
			}
		}
		return true;	
	}
	/**
	 * Player has multiple hitboxes due to
	 * it's shape. This method checks if a bullet
	 * hit any hitbox of the player.
	 * @param b Bullet
	 * @return Got hit or not
	 */
	private boolean hitBoxGotHit(Bullet b)
	{
		// Checks if bullet is hit the first hitbox or not
		if(y+height/2.5>=b.getY()+b.getHeight()+b.getVy() && y-height/2.5<=b.getY()+b.getHeight()+b.getVy())
			if ((b.getX() + vx > x - width/8) && (b.getX() + vx < x + width/8))
				return true;

		// Checks if bullet is hit the second hitbox or not
		if(y-0.06+height/6>=b.getY()+b.getHeight()+b.getVy() && y-0.06-height/6<=b.getY()+b.getHeight()+b.getVy())
			if ((b.getX() + vx > x - width/3.2) && (b.getX() + vx < x + width/3.2))
				return true;
		// If there is no hits returns true
		return false; 
	}
	/**
	 * This method draws the health bar of the player.
	 * Health bar is formed by ship's texture.
	 */
	private void healthBar()
	{
		// Coordinates of health bar
		double x = -0.9;
		double y = -0.9;
		double w = width/2;
		double h = height/2;

		if(health!=0)
		{
			// Draws health bar, 1 health stands for one spaceship texture
			for (int i = 0; i <health; i++) 
			{
				StdDraw.picture(x, y, "pictures/spaceShip.PNG",w,h);
				x = x + 0.1;
			}
		}
	}
	/**
	 * Returns the score of player
	 * @return Score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Changes score of the player
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * This method has a timer which is counting
	 * the time that spent in invincibility
	 * 3 seconds pass until invincibility ends.
	 */
	public void invincibilityCounter()
	{
		// Invincibility timer and timer task
		Timer invincibleTimer = new Timer();
		TimerTask makeInvincible;
		if(invincibility==true)
		{
			makeInvincible = new TimerTask() {

				@Override
				public void run() {
					// Timer makes player invincible for 3 seconds
					invincibleTimeCounter++;
					// After 3 seconds timer closes itself and removes
					//  player's invincibility.
					if(invincibleTimeCounter>1)
					{
						invincibility=false;
						invincibleTimeCounter=0;
						invincibleTimer.cancel();
						invincibleTimer.purge();
					}


				}
			};
			invincibleTimer.schedule(makeInvincible, 0, 3000); 
		}
	}

	/**
	 * Returns fire type of the player
	 * @return
	 */
	public String getFireType() {
		return fireType;
	}

	/**
	 * Changes firetype of the player
	 * @param fireType
	 */
	public void setFireType(String fireType) {
		this.fireType = fireType;
	}

	/**
	 * When player is got hit by the enemy bullets.
	 * The explosion animation timer starts and it creates
	 * an explosion animation. Animation has 21 frames and
	 * every frame is displaying for 60ms
	 */
	public void hitAnimation()
	{
		// Animation timer and timer task
		Timer animationTimer = new Timer();
		TimerTask animation;		
		animation = new TimerTask() {

			@Override
			public void run() {
				// Checks game is stopped or not
				if(Game_Board.gameStopped==false)
				{
					// Checks if explosion animation is finished or not
					if(currentExplosionFrame>=21)
					{
						explosionFrameTexture="";
						currentExplosionFrame=0;
						animationTimer.cancel();
						animationTimer.purge();
					}
					else
					{
						// Otherwise continue to explosion animation.
						currentExplosionFrame++;
						explosionFrameTexture="exp_"+currentExplosionFrame;
					}
				}
			}
		};
		animationTimer.schedule(animation, 0, 60); 	
	}
	/**
	 * Exhause animation of the player. It continue
	 * until game end.
	 */
	public void fireAnimation()
	{
		// Animation timer and timer task
		Timer animationTimer = new Timer();
		TimerTask animation;		
		animation = new TimerTask() {

			@Override
			public void run() {
				// Checks game is stopped or not
				if(Game_Board.gameStopped==false)
				{
					// Checks if the animation is it last frame
					// if so returns the first frame.
					if(currentfireFrame>=10)
					{
						fireFrameTexture="";
						currentfireFrame=1;
					}
					else
					{
						currentfireFrame++;
					}
					fireFrameTexture="flame_"+currentfireFrame;
				}
			}
		};
		animationTimer.schedule(animation, 0, 100); 	
	}
	
	/**
	 * Method has a timer that stores the survival time
	 * of the player in seconds.
	 */
	public void survivingCounter()
	{

		survive = new TimerTask() {

			@Override
			public void run() {
				// If game is not stopped increase surviving time by one every 1 sec
				if(Game_Board.gameStopped==false)
				{
					survivingTime++;
				}
			}
		};
		survivingTimer.schedule(survive, 0, 1000); 	
	}
	
	/**
	 * Returns surviving time of the player
	 * @return survivingTime
	 */
	public int getSurvivingTime() {
		return survivingTime;
	}
	
	/**
	 * Stops player's all timers
	 */
	public void stopTimers()
	{
		fireTimer.cancel();
		fireTimer.purge();
		survivingTimer.cancel();
		survivingTimer.purge();
	}
}