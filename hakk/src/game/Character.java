package game;

import graphics.CharacterAnimation;
import graphics.Level;
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

	public void draw(Graphics2D g2d, int xOffset, int yOffset) {
		int intx = (int) Math.round(charState.x);
		int inty = (int) Math.round(charState.y+ yOffset);
		int height = CharacterAnimation.getImage(charState.currentImage)
				.getHeight(null);
		g2d.drawImage(CharacterAnimation.getImage(charState.currentImage), intx
				- xOffset, inty - height , null);

		FontMetrics fm = g2d.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(playerName, g2d);
		int nameCoordX = (int) (intx
				+ CharacterAnimation.getImage(charState.currentImage).getWidth(
						null) / 2 - rect.getWidth() / 2)
				- xOffset;
		int nameCoordY = inty - height - 10;

		g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
		g2d.fill(new Rectangle2D.Double(nameCoordX - 3, nameCoordY
				- rect.getHeight() + 2, rect.getWidth() + 6,
				rect.getHeight() + 2));

		g2d.setColor(nameColour);
		g2d.drawString(playerName, nameCoordX, nameCoordY);
		if (intx < HakkStage.WIDTH) {
			g2d.drawImage(CharacterAnimation.getImage(charState.currentImage),
					intx - xOffset + HakkStage.LEVEL_WIDTH, inty - height, null);
			g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
			g2d.fill(new Rectangle2D.Double(nameCoordX - 3
					+ HakkStage.LEVEL_WIDTH, nameCoordY - rect.getHeight() + 2,
					rect.getWidth() + 6, rect.getHeight() + 2));

			g2d.setColor(nameColour);
			g2d.drawString(playerName, nameCoordX + HakkStage.LEVEL_WIDTH,
					nameCoordY);
		} else if (intx > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(CharacterAnimation.getImage(charState.currentImage),
					intx - xOffset - HakkStage.LEVEL_WIDTH, inty - height, null);
			g2d.setPaint(new Color(0.0f, 0.0f, 0.0f, 0.6f));
			g2d.fill(new Rectangle2D.Double(nameCoordX - 3
					- HakkStage.LEVEL_WIDTH, nameCoordY - rect.getHeight() + 2,
					rect.getWidth() + 6, rect.getHeight() + 2));

			g2d.setColor(nameColour);
			g2d.drawString(playerName, nameCoordX - HakkStage.LEVEL_WIDTH,
					nameCoordY);

		}

		intx = (int) Math.round(swordState.getX());
		inty = (int) Math.round(swordState.getY())+ yOffset;
		height = SwordAnimation.getImage(swordState.currentImage()).getHeight(
				null);
		g2d.drawImage(SwordAnimation.getImage(swordState.currentImage()), intx
				- xOffset, inty - height, null);
		if (intx < HakkStage.WIDTH) {
			g2d.drawImage(SwordAnimation.getImage(swordState.currentImage()),
					intx - xOffset + HakkStage.LEVEL_WIDTH, inty - height, null);
		} else if (intx > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(SwordAnimation.getImage(swordState.currentImage()),
					intx - xOffset - HakkStage.LEVEL_WIDTH, inty - height, null);
		}
	}

	protected void doGravity() {
		charState.yspeed -= -.8;
	}

	protected void doMovement(Level level) {
		charState.x += charState.xspeed;
		if (charState.x < 0){
			charState.x += HakkStage.LEVEL_WIDTH;
		} else if (charState.x > HakkStage.LEVEL_WIDTH){
			charState.x -= HakkStage.LEVEL_WIDTH;
		}	
		charState.y += charState.yspeed;
		
		int platformHitY = 0;
		if(charState.yspeed >=0){
			platformHitY =level.hitPlatform(charState.x, charState.y, CharacterAnimation.getImage(charState.currentImage).getWidth(null));
		}
		
		if (charState.y >= HakkStage.GROUNDLEVEL + CharacterAnimation.GROUND_OFFSET) {
			charState.y = HakkStage.GROUNDLEVEL + CharacterAnimation.GROUND_OFFSET;
			charState.yspeed = 0;
		} else if(platformHitY != 0){
			charState.yspeed = 0;
			charState.y = platformHitY;
			if(charState.action==Action.IN_AIR){
				charState.action=Action.STOPPING;
			}
		}
		swordState.move(charState.x, charState.y);
	}

	public void setState(CharacterState value) {
		charState = value;
	}

	public void doPhysics(Level level) {
		doAction(level);
		doGravity();
		doMovement(level);
	}

	protected abstract void doAction(Level level);

	public void rename(String name) {
		this.playerName = name;
	}

	public SwordState getSwordState() {
		return swordState;
	}
}
