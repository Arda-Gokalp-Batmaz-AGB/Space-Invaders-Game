import java.util.Timer;
import java.util.TimerTask;

/**
 * A class which is to be used for creating explosions
 * when an enemy is killed. It creates a explosion animation.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 */
public class Explosion extends Objects {
	
	private int currentFrame; // Current frame
	private String frameTexture; // Texture of current frame
	
	/**
	 * Creates new explosion object when a enemy is killed
	 * @param c Enemy
	 */
	public Explosion(Creature c)
	{
		// Initiliazes datafields depending on Enemy's attributes
		x = c.getX();
		y = c.getY();
		width = c.getWidth();
		height = c.getHeight();
		
		animation(); // Calls the explosion animation

	}
	
	/**
	 * Draws the explosion.
	 */
	@Override
	public void draw() {
		//System.out.println(frameTexture);
		if(frameTexture!=null && !frameTexture.equalsIgnoreCase(""))
		StdDraw.picture(this.getX(), this.getY(), "pictures/explosions/"+ frameTexture +".PNG",this.getWidth(),this.getHeight());	
	}
	
	/**
	 * This method creates explosion animation in 
	 * 60 ms per frame speed.
	 */
	public void animation()
	{
		// Creates a animation timer and task
		Timer animationTimer = new Timer();
		TimerTask animation;		
		animation = new TimerTask() {

			@Override
			public void run() {
				// Checks if explosion in the last frame or not
				if(currentFrame>=21)
				{
					// If it is, stops the animation and deletes it
					frameTexture="";
					currentFrame=0;
					animationTimer.cancel();
					animationTimer.purge();
				}
				else
				{
					//Otherwise the animation continues
					currentFrame++;
					frameTexture="exp_"+currentFrame;
				}				
			}
		};
		animationTimer.schedule(animation, 0, 60); 	
	}

	/**
	 * Returns frame's current texture
	 * @return frameTexture
	 */
	public String getFrameTexture() {
		return frameTexture;
	}
}
