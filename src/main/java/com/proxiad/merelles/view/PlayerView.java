package com.proxiad.merelles.view;

import com.codingame.game.Player;
import com.codingame.gameengine.module.entities.GraphicEntityModule;
import com.codingame.gameengine.module.entities.Sprite;
import com.codingame.gameengine.module.entities.Text;
import com.proxiad.merelles.game.PlayerColor;

public class PlayerView {

	private static final int AVATAR_SIZE = 128;
	
	private GraphicEntityModule entityModule;
	private Player player;
	private Text score;
	private Sprite avatar;
	private int top;
	private int left;

	public static class BinView {
		private static final int BIN_WIDTH = 300;
		private static final int BIN_HEIGHT = 300;
		private static final int PIECES_PER_ROW = 3;
		
		private int top;
		private int left;
		private int step;
		private int offset;
		
		public BinView(int x, int y) {
			left = x;
			top = y;
			int step = BIN_WIDTH / PIECES_PER_ROW;
			int margin = step - SpriteManager.PIECE_SIZE;
			offset = margin / 2;
		}
		
		public int xForPiece(int pieceRank) {
			int col = pieceRank % PIECES_PER_ROW;
			return left + offset + step * col;
		}
		
		public int yForPiece(int pieceRank) {
			int row = pieceRank / PIECES_PER_ROW;
			return top + offset + step * row; 
		}
	}
	
	private BinView stock;
	private BinView cemetary;

	public PlayerView(GraphicEntityModule entityModule, Player player) {
		this.entityModule = entityModule;
		this.player = player;

		PlayerColor color = player.getData().getColor();
		
		left = color == PlayerColor.BLACK ? 0 : 1500;
		top = 0;
		
		stock = new BinView(left + 50, top + 250);
		cemetary = new BinView(left + 50, top + 600);
		
		createAvatar();
		createText();
	}
	
	public void createAvatar() {
		avatar = entityModule.createSprite()
                .setX(left + 30 + AVATAR_SIZE / 2)
                .setY(top + 43 + AVATAR_SIZE / 2)
                .setZIndex(1)
                .setImage(player.getAvatarToken())
                .setBaseWidth(AVATAR_SIZE)
                .setBaseHeight(AVATAR_SIZE)
                .setAnchor(0.5);
	}

	public void createText() {
		entityModule.createText(player.getNicknameToken())
				.setX(left + 180)
				.setY(top + 50)
				.setZIndex(2)
				.setFontSize(32)
				.setFillColor(0x000000)
				.setFontFamily("Arial")
				.setVisible(true)
				//aligned to the left
				.setAnchorX(0)
				.setAnchorY(0);

		score = entityModule.createText("0")
				.setX(left + 180)
				.setY(top + 120)
				.setZIndex(2)
				.setFontSize(32)
				.setFillColor(0xCCCC44)
				.setFontFamily("Arial")
				.setVisible(true)
				//aligned to the left
				.setAnchorX(0)
				.setAnchorY(0);
	}

	public void updateView(int scoreOfPlayer) {
		score.setText(Integer.toString(scoreOfPlayer));
		entityModule.commitEntityState(1.0, score);
	}
}
