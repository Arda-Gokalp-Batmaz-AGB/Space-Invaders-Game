import java.awt.Color;

/**
 * Creates multiple bullets, bullet count can be changeable. Bullets
 * has to be odd numbered. First bullet moves in a constant x axis, and
 * others are spreading
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class MultipleBullet extends Bullet  {

	Bullet[] bullets; // Holds the bullets
	int bulletCount; // Counts the bullets

	/**
	 * Creates a multiplebullet object, bullet's amount, velocity and color
	 * can be changeable.
	 * @param p Player or Enemy which is going to fire multiple bullets.
	 * @param c Color of the bullet.
	 * @param vx Velocity in x axis.
	 * @param vy Velocity in y axis.
	 * @param bulletCount Multiple bullet count.
	 */
	public MultipleBullet(Creature p, Color c, double vx , double vy, int bulletCount) {
		super(p, c); // Calls the constructor of inherited Bullet class.
		// Initializes the data fields with using related parameters.
		bullets = new Bullet[bulletCount];
		this.c=c;
		x=p.getX();
		y=p.getY()+p.getHeight()/2;
		this.vx=vx;
		this.vy=vy;
		this.bulletCount=bulletCount;
		createBullets(p); //Calls createBullets method to create multiple bullets

		height=0.015;
		width=0.015;

	}
	/**
	 * Creates multiple bullet's depending on the bullet count and
	 * puts the bullets to the bullet array. Half of the bullets will
	 * move in negative x, half of them will be move in positive x directions.
	 * @param p Belongs to Enemy or Player
	 */
	private void createBullets(Creature p)
	{
		double tempvx=vx/4; // Temp vx
		bullets[0]=new DefaultBullet(p,c,0,vy); // Creates first bullet

		// This loop creates multiple bullets which is going to
		// move in positive x direction.
		for (int i = 1; i < (bulletCount+1)/2; i++) {
			bullets[i]=new DefaultBullet(p,c,tempvx,vy);
			tempvx=tempvx*1.5;
		}

		tempvx=vx/4; // Temp vx

		// This loop creates multiple bullets which is going to
		// move in negative x direction.
		for (int i = (bulletCount+1)/2; i < bulletCount; i++) {
			bullets[i]=new DefaultBullet(p,c,-tempvx,vy);
			tempvx=tempvx*1.5;
		}
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
		if (Math.abs(this.getY() + this.getVy()) > 1.0 - this.getHeight()) 
		{
			return false;
		}
		return true;
	}
	/**
	 * Returns list of the multiple bullets
	 * @return Bullet array
	 */
	public Bullet[] getBullets() {
		return bullets;
	}
	/**
	 * Set's the bullets array
	 * @param bullets
	 */
	public void setBullets(Bullet[] bullets) {
		this.bullets = bullets;
	}
}
