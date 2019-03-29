package com.proxiad.merelles.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Mill {
	
	private List<Piece> piecesInMill = new ArrayList<>(3);
	
	public Mill(Collection<? extends Piece> piecesInMill) {
		this.piecesInMill.addAll(piecesInMill);
	}

	public List<Piece> pieces() {
		return piecesInMill;
	}
}
