import java.util.Random;

/**
 * A class which provides player to health or ammo supplies.
 * It appears in a certain period of time and when player hit
 * it with it's bullet, it gives supply to the player.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class Supplies extends Enemy {
	
	String type; // Type of the supply
	Player p; // Player object
	Random rGen = new Random(); 
	
	/**
	 * Creates a supply which has a pre determined
	 * type, sets it's direction and velocity.
	 * @param type Type of the supply
	 * @param p Player object
	 */
	public Supplies(String type, Player p)
	{
		super(type); // Calls Enemy constructor
		
		// Initializes the data fields.
		this.type=type;
		this.p=p;
		x=-0.5;
		y=p.getY()+p.getHeight()/2;
		y = (rGen.nextInt(9 - 3 + 1) + 3)/10.0;
		this.vy=0;
		this.vx=0.01;
		height=0.1;
		width=0.1;
		enemyType="Supply";
	}
	
	/**
	 * Draws the supply
	 */
	@Override
	public void draw() {
		if(type.equals("Health_Supply"))
		StdDraw.picture(this.getX(), this.getY(), "pictures/powerup1.PNG",this.getWidth(),this.getHeight());
		if(type.equals("Fire_Supply"))
			StdDraw.picture(this.getX(), this.getY(), "pictures/powerup2.PNG",this.getWidth(),this.getHeight());
	}
	/**
	 * Returns type of the supply.
	 * @return Supply type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gives player a boost depending on
	 * type of the supply.
	 */
	public void giveSupply()
	{
		// If it's a health supply, gives +1 health.
		// If it's a fire supply, It improves player's bullets.
		if(type.equalsIgnoreCase("Health_Supply"))
		{
			p.setHealth(p.getHealth()+1);
		}
		if(type.equalsIgnoreCase("Fire_Supply"))
		{
			p.setFireType("multi");
		}
	}
	// The automatically generated override methods,
	@Override
	public void fire() {
		// TODO Auto-generated method stub

	}
	@Override
	public void updateBullets() {
		// TODO Auto-generated method stub

	}
	@Override
	public void moveEnemy() {
		// TODO Auto-generated method stub

	}
}
