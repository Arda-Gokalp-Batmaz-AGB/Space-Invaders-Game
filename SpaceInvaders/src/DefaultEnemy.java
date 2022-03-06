import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Class of the default enemy type, inherited from the Enemy
 * abstract class. This enemy type moves in upper board and
 * uses default bullets.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class DefaultEnemy extends Enemy{

	/**
	 * Constructor of the class, creates a default enemy
	 * with pre determined data fields.
	 * @param type Type of the enemy
	 */
	public DefaultEnemy(String type) {
		super(type); // Constructor of Enemy class
		// Initializes the datafields of object.
		// Chooses random x and y coordinates that
		// enemy will spawn
		x = (rGen.nextInt(10 + 10 + 1) + -10)/10.0;
		y = (rGen.nextInt(9 - 3 + 1) + 3)/10.0;
		height = 0.1;
		width = 0.1;
		vx = 0.01;
		vy = 0.01;
		enemyType=type;

		// Chooses a random move direction
		direction_X = rGen.nextInt(2);
		direction_Y = rGen.nextInt(2);

		bullets = new ArrayList<Bullet>();
		texture = "enemy_1";
		health = 1;
		value=10; // Value of the enemy in terms of score
	}

	/**
	 * Enemy moves depending on it's coordinates
	 */
	public void moveEnemy()
	{
		// If enemy is in corners changes it's direction
		// to the opposite sides.
		if(x>=1)
			direction_X=0;
		else if(x<=-1)
			direction_X=1;

		// Changes it's velocity in x depending on
		// the new direction
		if(direction_X==1)
		{
			x = x + vx;
		}
		else if (direction_X==0)
		{
			x = x - vx;
		}

		// If enemy is in upper side changes it's direction
		// to the opposite side.		
		if(y>=1)
			direction_Y=0;
		else if(y<=0.3)
			direction_Y=1;

		// Changes it's velocity in y depending on
		// the new direction		
		if(direction_Y==1)
		{
			y = y + vy;
		}
		else if (direction_Y==0)
		{
			y = y - vy;
		}
	}

	/**
	 * Enemy fires depending on the timer in this method
	 */
	public void fire()
	{
		Enemy e = this;
		// Checks if it started to fire or not
		if(timerIsRunning==false)
		{
			// Timer to firing every 2500 ms
			fire = new TimerTask() {
				@Override
				public void run() {
					// Creates a new default bullet and adds to bullet array
					Bullet bullet = new DefaultBullet(e,StdDraw.YELLOW,0,-0.02);
					bullets.add(bullet);

					// Checks if game is finished or not
					if(Game_Board.gameFinished==true)
					{
						// If game is finished, cancels the timer.
						fireTimer.cancel();
						fireTimer.purge();
					}	
				}
			};
			fireTimer.schedule(fire, 0, 2500);
		}
		timerIsRunning=true; // Sets that timer is running
	}

	/**
	 * Draws the enemy
	 */
	@Override
	public void draw() {
		StdDraw.picture(x, y, "pictures/" + texture + ".PNG",width,height);

	}
}
