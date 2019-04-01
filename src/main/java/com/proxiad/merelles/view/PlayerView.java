package com.proxiad.merelles.view;

import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Text;
import com.proxiad.merelles.game.PlayerColor;

public class PlayerView {

	private GraphicEntityModule entityModule;
	private Player player;
	private Text text;

	public PlayerView(GraphicEntityModule entityModule, Player player) {
		this.entityModule = entityModule;
		this.player = player;
		createText();
	}

	public void createText() {
		PlayerColor color = player.getData().getColor();
		int x = color == PlayerColor.BLACK ? 30 : 1510;
		int y = 130;

		entityModule.createText(color == PlayerColor.BLACK ? "Black Player" : "White Player")
				.setX(x)
				.setY(y - 50)
				.setZIndex(2)
				.setFontSize(50)
				.setFillColor(0x000000)
				.setFontFamily("Arial")
				.setVisible(true)
				//aligned to the left
				.setAnchorX(0)
				.setAnchorY(1);

		text = entityModule.createText("0")
				.setX(x)
				.setY(y)
				.setZIndex(2)
				.setFontSize(50)
				.setFillColor(0x000000)
				.setFontFamily("Arial")
				.setVisible(true)
				//aligned to the left
				.setAnchorX(0)
				.setAnchorY(1);
	}

	public void updateView(int scoreOfPlayer) {
		text.setText(Integer.toString(scoreOfPlayer));
		entityModule.commitEntityState(1.0, text);
	}
}
