import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Class of the elite enemy type, inherited from the Enemy
 * abstract class. This enemy type moves in upper board and
 * uses multiple bullets. Also sometimes dodges the bullets of
 * the player.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class EliteEnemy extends Enemy{
	Player p; // Holds player object

	/**
	 * Constructor of the class, creates a elite enemy
	 * with pre determined data fields.
	 * @param type Type of Enemy
	 * @param p Player object
	 */
	public EliteEnemy(String type,Player p) {
		super(type); // Constructor of Enemy class

		// Chooses random x and y coordinates that
		// enemy will spawn
		x = (rGen.nextInt(10 + 10 + 1) + -10)/10.0;
		y = (rGen.nextInt(9 - 3 + 1) + 3)/10.0;

		// Initializes the datafields of object.
		height = 0.1;
		width = 0.1;
		vx = 0.01*0.5;
		vy = 0.01;
		enemyType=type;

		// Chooses a random move direction
		direction_X = rGen.nextInt(2);
		direction_Y = rGen.nextInt(2);

		bullets = new ArrayList<Bullet>();
		texture = "enemy_2";
		this.p=p;
		health = 1;
		value=20; // Value of the enemy in terms of score

	}

	/**
	 * Enemy fires depending on the timer in this method
	 */
	@Override
	public void fire() {
		Enemy e = this;
		// Checks if it started to fire or not
		if(timerIsRunning==false)
		{
			// Timer to firing every 3500 ms
			fire = new TimerTask() {
				@Override
				public void run() {
					// Creates a new multiplebullet and adds to bullet array
					MultipleBullet bullet = new MultipleBullet(e,StdDraw.RED,-0.02,-0.02,3);

					// Adds multiple bullets to it's bullet array
					Bullet[] b = bullet.getBullets();
					for (int i = 0; i < b.length; i++) {
						bullets.add(b[i]);
					}

					// Checks if game is finished or not
					if(Game_Board.gameFinished==true)
					{
						// If game is finished, cancels the timer.
						fireTimer.cancel();
						fireTimer.purge();
					}	
				}
			};
			fireTimer.schedule(fire, 0, 3500);
		}
		timerIsRunning=true; // Sets that timer is running

	}

	/**
	 * Enemy moves depending on it's coordinates
	 */
	@Override
	public void moveEnemy() {

		// If enemy is in corners changes it's direction
		// to the opposite sides.
		if(x>=1)
			direction_X=0;
		else if(x<=-1)
			direction_X=1;

		// Changes it's velocity in x depending on
		// the new direction
		if(direction_X==1)
			x = x + vx;
		if(direction_X==0)
			x = x - vx;

		for (int i = 0; i < p.getBullets().size(); i++) {
			if(p.getBullets().get(i).getX()<=x+width/2 && p.getBullets().get(i).getX()>=x-width/2)
			{
				if(direction_X==1)
					x = x + vx*3;
				if(direction_X==0)
					x = x - vx*3;
			}
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
	 * Draws the enemy
	 */
	@Override
	public void draw() {
		StdDraw.picture(x, y, "pictures/" + texture + ".PNG",width,height);

	}

}