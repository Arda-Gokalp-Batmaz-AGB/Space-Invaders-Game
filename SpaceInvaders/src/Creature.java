import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An abstract parent class that is using for creating
 * Player and Enemy objects. It has health, bullets, fire timer
 * and some methods to control the creature.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public abstract class Creature extends Objects {

	protected double health; // Health of the creature
	protected ArrayList<Bullet> bullets; // List of bullets
	// Fire timer and fire task
	protected Timer fireTimer = new Timer(); 
	protected TimerTask fire;
	//Fire timer is running or not
	protected boolean timerIsRunning=false;

	/**
	 * Signature of the fire method.
	 */
	protected abstract void fire();

	/**
	 * Signature of the updateBullets method
	 * It is using for updating bullets of the creature.
	 */
	protected abstract void updateBullets();

	/**
	 * Returns health of the creature.
	 * @return health
	 */
	protected double getHealth() {
		return health;
	}
	/**
	 * Changes the health of the creature.
	 * @param health New health
	 */
	protected void setHealth(double health) {
		this.health = health;
	}

	/**
	 * Returns bullets of Creature.
	 * @return Bullet Array
	 */
	protected ArrayList<Bullet> getBullets() {
		return bullets;
	}

}
