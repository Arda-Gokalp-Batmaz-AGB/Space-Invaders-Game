import java.awt.Color;

/**
 * An abstract class that provides an object's datafields
 * and necessary methods. It is using to create objects.
 * @author Arda Gokalp Batmaz
 * @since Date:04.05.2021
 *
 */
public abstract class Objects {
	/**
	 * Data fields of the object
	 */
	protected double x;
	protected double y;
	protected double height;
	protected double width;
	protected double vx;
	protected double vy;
	protected Color c;
	
	/**
	 * Returns color of the object
	 * @return color
	 */
	protected Color getC() {
		return c;
	}
	
	/**
	 * Changes the color of the object
	 * @param c New color
	 */
	protected void setC(Color c) {
		this.c = c;
	}
	
	/**
	 * Signature of the draw method which is
	 * using for drawing the objects.
	 */
	protected abstract void draw();
	
	/**
	 * Returns x value of the object
	 * @return X 
	 */
	protected double getX() {
		return x;
	}
	
	/**
	 * Changes the x value of the object
	 * @param New x
	 */
	protected void setX(double x) {
		this.x = x;
	}
	
	/**
	 * Returns y value of the object
	 * @return Y
	 */
	protected double getY() {
		return y;
	}
	
	/**
	 * Changes the y value of the object
	 * @param New y
	 */
	protected void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Returns height of the object
	 * @return height
	 */
	protected double getHeight() {
		return height;
	}
	
	/**
	 * Changes the height of the object
	 * @param New height
	 */
	protected void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * Returns width of the object
	 * @return width
	 */
	protected double getWidth() {
		return width;
	}

	/**
	 * Changes the width of the object
	 * @param New width
	 */
	protected void setWidth(double width) {
		this.width = width;
	}
	
	/**
	 * Returns the velocity in X axis of the object
	 * @return Velocity in X axis
	 */
	protected double getVx() {
		return vx;
	}

	/**
	 * Changes the velocity in X axis of the object
	 * @param New x velocity
	 */
	protected void setVx(double vx) {
		this.vx = vx;
	}

	/**
	 * Returns the velocity in Y axis of the object
	 * @return Velocity in Y axis
	 */
	protected double getVy() {
		return vy;
	}

	/**
	 * Changes the velocity in Y axis of the object
	 * @param New y velocity
	 */
	protected void setVy(double vy) {
		this.vy = vy;
	}
}
