import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An abstract class which is inherited from Creature class.
 * It's the fundemantal class of the enemies. It is used for
 * creating new enemy types.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public abstract class Enemy extends Creature{

	// Data fields of Enemy class
	protected String enemyType;
	protected String texture;
	protected double direction_X;
	protected double direction_Y;
	protected int value;

	protected  Random rGen = new Random(); 

	/**
	 * Constructor of the enemy
	 * @param type
	 */
	public Enemy(String type)
	{
	}

	/**
	 * Signature of the fire method.
	 */
	public abstract void fire();

	/**
	 * Signature of the moveEnemy method.
	 */
	public abstract void moveEnemy();

	/**
	 * Signature of the draw method.
	 */
	public abstract void draw();

	/**
	 * This method checks if the enemy is alive or not
	 * If it was killed by the player, an explosion animation and
	 * sound appears.
	 * @param p Player object
	 * @return Is alive or not
	 */
	protected boolean isAlive(Player p)
	{
		// In this loop, method checks player's every bullet to check
		// any of it's bullets hit the enemy.
		for (int i = 0; i < p.getBullets().size(); i++) 
		{
			// Checks if the player's bullet is in the same y value with the enemy.
			if(y-height/2<=p.getBullets().get(i).getY()+p.getBullets().get(i).getHeight()/2+p.getBullets().get(i).getVy())
			{
				// Checks if the player's bullet is in the same x value with the enemy.
				if(x-width/2<=p.getBullets().get(i).getX()+p.getBullets().get(i).getWidth()/2 && x+width/2>=p.getBullets().get(i).getX()+p.getBullets().get(i).getWidth()/2)
				{
					// If enemy get hits, remove player's bullet
					// and decrease enemy's health.
					health--;
					p.getBullets().remove(i);

					// Checks If health of the enemy is zero , that means it's dead,
					if(health<=0)
					{
						// Before killing the enemy, program adds it's bullets to extraBullet array
						// to store the bullets until bullets die.
						if(bullets!=null)
							Game_Board.extraBullets.addAll(bullets);

						// Increases score of the player depending on enemy type
						p.setScore(p.getScore()+value);

						// Creates a sound object and depending on the enemy type
						// calls a different sound effect.
						SoundCall sound = new SoundCall();
						if(enemyType!=null && enemyType.equalsIgnoreCase("BossEnemy"))
						{

							sound.playSound("Explosion");
						}
						else if(enemyType!=null && enemyType.equalsIgnoreCase("Supply"))
						{

							sound.playSound("supply");
						}
						else 
						{

							sound.playSound("MiniExplosion");
						}

						enemyKillCounter(); // Increases player's kill counter
						// Cancels timers of the enemy
						fireTimer.cancel();
						fireTimer.purge();
						return false; // Returns enemy is dead
					}
				}

			}
		}
		return true; // Returns enemy is alive

	}
	/**
	 * Updates bullets of the enemy
	 */
	protected void updateBullets()
	{
		// Checks if the enemy has bullets or not
		if(bullets.size()>0)
		{
			// In this loop method updates bullet's position and draws them
			for (int i = 0; i < bullets.size(); i++) 
			{
				// Updates position of the bullets depending on their velocity
				bullets.get(i).setY(bullets.get(i).getY()+bullets.get(i).getVy());
				bullets.get(i).setX(bullets.get(i).getX()+bullets.get(i).getVx());

				// Draws the bullets
				if(!(i >= bullets.size()))
					bullets.get(i).draw();
			}	
		}

	}
	/**
	 * A counter method which works depending on the killed enemy
	 * and adds it to database.
	 */
	protected void enemyKillCounter()
	{
		// Adds a enemy to counters depending on it's type
		if(enemyType!=null)
		{
			if(enemyType.equalsIgnoreCase("DefaultEnemy"))
				DataBase.defaultEnemyKillCount++;
			if(enemyType.equalsIgnoreCase("EliteEnemy"))
				DataBase.eliteEnemyKillCount++;
			if(enemyType.equalsIgnoreCase("BossEnemy"))
				DataBase.bossEnemyKillCount++;
		}
	}
}
