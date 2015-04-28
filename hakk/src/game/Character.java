package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public abstract class Character {
	protected CharacterState state;
	protected CharacterAnimation animation;
	private String playerName = "Default name";
	protected Color nameColour = Color.BLACK;

	public Character(String playerName, String baseImageName) {
		this.playerName = playerName;
		this.animation = new CharacterAnimation(baseImageName);
		state = new CharacterState(Action.STOPPING,
				animation.getCurrentImageName());
	}

	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(state.x);
		int inty = (int) Math.round(state.y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = animation.getImage(state.currentImage).getHeight(null);
		g2d.drawImage(animation.getImage(state.currentImage), intx, inty
				- height, null);

		g2d.setFont(new Font("Names", Font.BOLD, 14));

		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(playerName, g2d);
		int nameCoordX = (int) (intx
				+ animation.getImage(state.currentImage).getWidth(null) / 2 - rect
				.getWidth() / 2);
		int nameCoordY = inty - height - 10;

		g2d.setPaint(nameColour);
		g2d.fill(new Rectangle2D.Double(nameCoordX, nameCoordY-12, 100, 14));

		g2d.setColor(Color.GREEN);
		g2d.drawString(playerName, nameCoordX, nameCoordY);
	}

	protected void doGravity() {
		state.yspeed -= -.5;
	}

	protected void doMovement() {
		state.x += state.xspeed;
		state.y += state.yspeed;
		if (state.y >= HakkStage.GROUNDLEVEL + CharacterAnimation.GROUND_OFFSET) {
			state.y = HakkStage.GROUNDLEVEL + CharacterAnimation.GROUND_OFFSET;
			state.yspeed = 0;
		}
	}

	public void setState(CharacterState value) {
		state = value;
	}

	public void doPhysics() {
		doAction();
		doGravity();
		doMovement();
	}

	protected boolean hitLeftWall() {
		return state.x < 0;
	}

	protected boolean hitRightWall() {
		return state.x > 846;
	}

	protected abstract void doAction();

	public void rename(String name) {
		this.playerName = name;
	}

}
