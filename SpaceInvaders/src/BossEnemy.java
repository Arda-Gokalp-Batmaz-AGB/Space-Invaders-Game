import java.awt.Color;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Class of the boss enemy type, inherited from the Enemy
 * abstract class. This enemy type moves in upper board and
 * uses multiple bullets and guided bullets. Also it has much more
 * health than other enemies.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class BossEnemy extends Enemy{
	Player p; // Holds player object
	double maxHealth; // Max health of the boss

	/**
	 * Creates a Boss enemy from the constructor
	 * @param type Type of the enemy
	 * @param p Palyer object
	 */
	public BossEnemy(String type,Player p) {
		super(type); // Constructor of Enemy class

		// Chooses random x coordinates that
		// enemy will spawn
		x = (rGen.nextInt(4 + 4 + 1) + -4)/10.0;
		y = 0.5;

		// Initializes the datafields of object.
		height = 0.35;
		width = 0.35;
		vx = 0.01;
		vy = 0.01;
		enemyType=type;

		// Chooses a random move direction
		direction_X = rGen.nextInt(2);
		direction_Y = rGen.nextInt(2);

		bullets = new ArrayList<Bullet>();
		texture = "enemy_3";
		this.p=p;
		health =20;
		maxHealth = health;
		value=50; // Value of the enemy in terms of score
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
			// Timer to firing every 4000 ms
			fire = new TimerTask() {
				@Override
				public void run() {
					// Creates a new multiplebullet and adds to bullet array
					MultipleBullet bullet = new MultipleBullet(e,StdDraw.RED,-0.02,-0.02,7);

					// Adds multiple bullets to it's bullet array
					Bullet[] b = bullet.getBullets();
					for (int i = 0; i < b.length; i++) {
						bullets.add(b[i]);
					}

					// Creates new guided bullets and adds it to it's bullet array
					GuidedBullet guidedBullet = new GuidedBullet(e,p,new Color(255, 128, 255),-0.01,-0.02);
					bullets.add(guidedBullet);
					bullets.add(guidedBullet);

					// Checks if game is finished or not
					if(Game_Board.gameFinished==true)
					{
						// If game is finished, cancels the timer.
						fireTimer.cancel();
						fireTimer.purge();
					}	
				}
			};
			fireTimer.schedule(fire, 0, 4000);
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
		if(x>=0.5)
			direction_X=0;
		else if(x<=-0.5)
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

	}

	/**
	 * Draws the enemy
	 */
	@Override
	public void draw() {
		StdDraw.picture(x, y, "pictures/" + texture + ".PNG",width,height);	
		drawHealthBar();
	}

	/**
	 * Updates the bullets of the boss depending on
	 * bullet type.
	 */
	@Override
	public void updateBullets()
	{
		if(bullets.size()>0) // Checks if there is a bullet or not.
		{
			for (int i = 0; i < bullets.size(); i++) 
			{
				// Depending on the bullet type follows a different path and updates them.
				if(bullets.get(i).toString().substring(0, 12).equalsIgnoreCase("GuidedBullet"))
				{
					// If bullet is a guided bullet update it with following code.
					GuidedBullet guidedBullet = (GuidedBullet) bullets.get(i);
					guidedBullet.setDirection();
				}
				else
				{
					// If not, do nothing.
				}
				// Update x and y values of the bullet depending on it's velocity
				bullets.get(i).setY(bullets.get(i).getY()+bullets.get(i).getVy());
				bullets.get(i).setX(bullets.get(i).getX()+bullets.get(i).getVx());

				// Drawa the bullet
				if(!(i >= bullets.size()))
					bullets.get(i).draw();
			}	
		}
	}
	/**
	 * Draws the healthbar of boss.
	 * Every time boss take damage, it updates
	 * it's healthbar.
	 */
	public void drawHealthBar()
	{
		// Draws a white rectangle as a background of health bar
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.filledRectangle(this.x, this.y+height/2, width/2, height/20);
		double bar_w = width/(2*maxHealth);
		double bar_x = (this.x-width/2)+bar_w;

		// Loop draws red parts of the health, depending on the health the ratio
		// of red rectangles changes.
		if(health!=0)
		{
			for (int i = 0; i <health; i++) 
			{
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.filledRectangle(bar_x, this.y+height/2, bar_w, height/20);
				bar_x = bar_x + 2*bar_w;

			}
		}
	}
}