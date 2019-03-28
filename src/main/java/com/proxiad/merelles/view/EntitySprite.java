package com.proxiad.merelles.view;

import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;

/**
 * A proxy for entity sprites.
 * It encapsulates a reference to the entity module.
 * It enforces homogeneity among the behaviors such as showing, moving, hiding.  
 * @author ydekercadio
 *
 */
public class EntitySprite {

	private GraphicEntityModule entityModule;
	private Sprite sprite;

	private int x = 0;
	private int y = 0;
	private SpriteVisibility visibility = SpriteVisibility.HIDDEN;

	public EntitySprite(GraphicEntityModule entityModule, Sprite sprite) {
		this.entityModule = entityModule;
		this.sprite = sprite;
	}

	/**
	 * Change the state of the sprite
	 * @param x New x
	 * @param y New y
	 * @param visibility New visibility
	 * @param time When the change should happen (0.0 = beginning of frame, 1.0 = end of frame)
	 */
	public void setState(int x, int y, SpriteVisibility visibility, double time) {
		this.x = x;
		this.y = y;
		this.visibility = visibility;
		sprite.setX(this.x).setY(this.y);

		switch (this.visibility) {
		case VISIBLE:
		case HILIGHTED:
			sprite.setVisible(true).setAlpha(1.0);
			break;

		case HIDDEN:
		default:
			sprite.setVisible(false).setAlpha(0.0);
			break;			
		}
		
		commitState(time);
	}

	/**
	 * Sends the state of the sprite to the user's browser.
	 * @param time Time of this state (0.0 = beginning of the frame, 1.0 = end of the frame)
	 */
	private void commitState(double time) {
		entityModule.commitEntityState(time, sprite);
	}
}
