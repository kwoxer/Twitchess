package de.fhb.projects.Twitchess.games.chess;

import java.awt.Point;

import de.fhb.projects.Twitchess.games.chess.move.Move;

public final class Position extends Point {
	private static final long serialVersionUID = 1L;

	public Position(final int x, final int y) {
		super(x, y);
	}

	@Override
	public String toString() {
		return "" + (char) ('a' + x) + (y + 1);
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		} else if (o == null || o.getClass() != this.getClass()) {
			return false;
		}
		Position p = (Position) o;
		return x == p.x && y == p.y;
	}

	@Override
	public int hashCode() {
		long bits = x;
		bits ^= y * 31;
		return (((int) bits) ^ ((int) (bits >> 32)));
	}

	public int calculateXDistance(final Position destination) {
		return calculateXDistance(this, destination);
	}

	public int calculateYDistance(final Position destination) {
		return calculateYDistance(this, destination);
	}

	public static int calculateXDistance(final Move move) {
		return calculateXDistance(move.getStart(), move.getDestination());
	}

	public static int calculateXDistance(final Position start,
			final Position destination) {
		return Math.abs(destination.x - start.x);
	}

	public static int calculateYDistance(final Position start,
			final Position destination) {
		return Math.abs(destination.y - start.y);
	}

	public static int calculateYDistance(final Move move) {
		return calculateYDistance(move.getStart(), move.getDestination());
	}
}
