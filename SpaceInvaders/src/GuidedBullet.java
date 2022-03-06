import java.awt.Color;

/**
 * Creates a bullet which is follow the player to kill player.
 * Bullet changes direction depending on the position of player,
 * and has data fields.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class GuidedBullet extends Bullet {

	Player player; // Holds player object

	/**
	 * Creates a guided bullet
	 * @param p Enemy
	 * @param player
	 * @param c Color
	 * @param vx Velocity x
	 * @param vy Velocity y
	 */
	public GuidedBullet(Creature p,Player player, Color c, double vx , double vy) {
		super(p, c); // Calls the constructor of inherited Bullet class.
		// Initializes the data fields with using related parameters.
		this.c=c;
		x=p.getX();
		y=p.getY()+p.getHeight()/2;
		this.vy=vy;
		this.vx=vx;
		this.player=player;
		height=0.015;
		width=height*(9/15.0);
	}

	/**
	 * Draws the bullet.
	 */
	@Override
	public void draw() {
		StdDraw.setPenColor(c);
		StdDraw.filledRectangle(x, y, width, height);
	}

	/**
	 * Checks if the bullet is in the out of the board or not
	 * If it's out of the board, kills it.
	 */
	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * Changes the direction of the bullet depending on
	 * player's position.
	 */
	public void setDirection()
	{
		// This If structure changes direction of the bullet
		// depending on player's position.
		if(player.getX()<=x)
		{
			vx = -1*Math.abs(vx);
		}
		else if (player.getX()>x)
		{
			vx = Math.abs(vx);
		}
		if(player.getY()<=y)
		{
			vy = -1*Math.abs(vy);
		}
	}
}
