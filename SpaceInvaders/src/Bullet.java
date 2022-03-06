import java.awt.Color;
import java.util.ArrayList;

/**
 * An abstract class that contains signatures of the methods
 * which are going to be used in different Bullet classes and objects
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public abstract class Bullet extends Objects {
	protected String type;// Bullet Type
	
	/**
	 * Default Constructor
	 */
	public Bullet()
	{
		
	}
	
	/**
	 * Creates a bullet for the player or enemy
	 * @param p A creature which is a Player or Enemy
	 * @param c Color of the bullet
	 */
	public Bullet(Creature p, Color c)
	{
	}
	/**
	 * Signutare of the draw method
	 */
	public abstract void draw();
	
	/**
	 * Checks if a bullet is alive or not
	 * @return The vitality of the bullet
	 */
	public abstract boolean isAlive();
	

}
