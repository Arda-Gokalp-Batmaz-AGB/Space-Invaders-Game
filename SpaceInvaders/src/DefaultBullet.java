import java.awt.Color;

/**
 * Creates a bullet which is in default type. Bullet moves
 * in the determined direction, velocity and color. This class
 * is the fundamental of all bullet classes.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 *
 */
public class DefaultBullet extends Bullet {

	/**
	 * Creates a new bullet which has a determined velocities and color.
	 * Also it specifies that bullet is belong to who.
	 * @param p Player or Enemy
	 * @param c Color of the bullet
	 * @param vx Velocity in X axis
	 * @param vy Velocity in Y axis
	 */
	public DefaultBullet(Creature p, Color c, double vx , double vy) {
		super(p, c); // Calls the constructor of inherited Bullet class.
		// Initializes the data fields with using related parameters.
		this.c=c;
		x=p.getX();
		y=p.getY()+p.getHeight()/2;
		this.vy=vy;
		this.vx=vx;
		height=0.015;
		width=0.005;
	}

	/**
	 * Draws the bullet.
	 */
	public void draw() {
		StdDraw.setPenColor(c); 
		StdDraw.filledRectangle(x, y, width, height);
	}

	/**
	 * Checks if the bullet is in the out of the board or not
	 * If it's out of the board, kills it.
	 */
	public boolean isAlive()
	{
		// Checks if the bullet is in the board or not
		if (Math.abs(this.getY() + this.getVy()) > 1.0 - this.getHeight()) 
		{
			return false; // If not, kills it
		}
		return true; // Otherwise don't kill it
	}

}
