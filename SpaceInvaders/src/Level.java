
/**
 * This class holds level attributes. Levels has
 * different number of enemies and they are in different types. 
 * Game's situation depends on levels.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class Level {
	
	// Data fields of the class
	protected int defaultEnemyCount;
	protected int eliteEnemyCount;
	protected int bossCount;
	
	/**
	 * Creates a new level object with given count parameters
	 * @param defaultEnemyCount
	 * @param eliteEnemyCount
	 * @param bossCount
	 */
	public Level(int defaultEnemyCount , int eliteEnemyCount, int bossCount )
	{
		// Initializes data fields
		this.defaultEnemyCount=defaultEnemyCount;
		this.eliteEnemyCount=eliteEnemyCount;
		this.bossCount=bossCount;
	}
	
	/**
	 * Returns count of the default typed enemies.
	 * @return Default enemy count
	 */
	public int getDefaultEnemyCount() {
		return defaultEnemyCount;
	}
	
	/**
	 * Returns count of the elite typed enemies.
	 * @return Elite enemy count
	 */
	public int getEliteEnemyCount() {
		return eliteEnemyCount;
	}
	
	/**
	 * Returns count of the boss typed enemies.
	 * @return Boss enemy count
	 */
	public int getBossCount() {
		return bossCount;
	}
}
