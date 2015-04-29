package game;

import graphics.CharacterAnimation;
import graphics.SwordAnimation;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Character {
	protected CharacterState charState;
	protected SwordState swordState;
	protected CharacterAnimation charAnimation;
	protected SwordAnimation swordAnimation;
	private String playerName = "Default name";
	protected Color nameColour = Color.BLACK;

	public Character(String playerName, String baseImageName) {
		this.playerName = playerName;
		this.charAnimation = new CharacterAnimation(baseImageName);
		charState = new CharacterState(Action.STOPPING,
				charAnimation.getCurrentImageName());
		swordState = new SwordState(charState.x, charState.y);
		swordAnimation = new SwordAnimation(SwordAnimation.BASENAMES[0]);
	}

	public void draw(Graphics2D g2d, int offset) {
		int intx = (int) Math.round(charState.x);
		int inty = (int) Math.round(charState.y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = CharacterAnimation.getImage(charState.currentImage)
				.getHeight(null);
		g2d.drawImage(CharacterAnimation.getImage(charState.currentImage),
				intx-offset, inty - height, null);

		g2d.setFont(new Font("Names", Font.BOLD, 12));
		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(playerName, g2d);
		int nameCoordX = (int) (intx
				+ CharacterAnimation.getImage(charState.currentImage).getWidth(
						null) / 2 - rect.getWidth() / 2) - offset;
		int nameCoordY = inty - height - 10;

		g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
		g2d.fill(new Rectangle2D.Double(nameCoordX - 3, nameCoordY
				- rect.getHeight() + 2, rect.getWidth() + 6,
				rect.getHeight() + 2));

		g2d.setColor(nameColour);
		g2d.drawString(playerName, nameCoordX, nameCoordY);

		intx = (int) Math.round(swordState.getX());
		inty = (int) Math.round(swordState.getY());
		height = SwordAnimation.getImage(swordState.currentImage()).getHeight(
				null);
		g2d.drawImage(SwordAnimation.getImage(swordState.currentImage()), intx-offset,
				inty - height, null);
	}

	protected void doGravity() {
		charState.yspeed -= -.8;
	}

	protected void doMovement() {
		charState.x += charState.xspeed;
		charState.y += charState.yspeed;
		if (charState.y >= HakkStage.GROUNDLEVEL
				+ CharacterAnimation.GROUND_OFFSET) {
			charState.y = HakkStage.GROUNDLEVEL
					+ CharacterAnimation.GROUND_OFFSET;
			charState.yspeed = 0;
		}
		swordState.move(charState.x, charState.y);
	}

	public void setState(CharacterState value) {
		charState = value;
	}

	public void doPhysics(ArrayList<Platform> platforms) {
		doAction();
		doGravity();
		doMovement();
	}

//	protected boolean hitLeftWall() {
//		return charState.x < 0;
//	}
//
//	protected boolean hitRightWall() {
//		return charState.x > 846;
//	}

	protected abstract void doAction();

	public void rename(String name) {
		this.playerName = name;
	}

	public SwordState getSwordState() {
		return swordState;
	}
}
