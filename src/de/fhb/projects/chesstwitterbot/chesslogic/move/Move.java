package de.fhb.projects.chesstwitterbot.chesslogic.move;

import de.fhb.projects.chesstwitterbot.chesslogic.ChessboardPositionToArrayPosition;
import de.fhb.projects.chesstwitterbot.chesslogic.Position;

public final class Move {
	public static final Move NO_MOVE = new Move();
	private Direction direction;
	private Position start, destination;

	private Move() {
		direction = new Direction(DirectionType.NODIRECTION);
		start = new Position(0, 0);
		destination = start;
	}

	public Move(final Position start, final Position destination) {
		this.start = start;
		this.destination = destination;
		setDirection(start, destination);
	}

	private void setDirection(final Position start, final Position destination) {
		DirectionType directionType = DirectionType.getDirectionType(start,
				destination);

		if (start.calculateXDistance(destination) <= 1
				&& start.calculateYDistance(destination) <= 1
				|| directionType.equals(DirectionType.KNIGHT)) {
			this.direction = new OneStepDirection(directionType);
		} else {
			this.direction = new InfiniteDirection(directionType);
		}
	}

	public static Move up(final Position position, final int steps) {
		return new Move(position, new Position(position.x, position.y + steps));
	}

	public static Move down(final Position position, final int steps) {
		return new Move(position, new Position(position.x, position.y - steps));
	}

	public static Move left(final Position position, final int steps) {
		return new Move(position, new Position(position.x - steps, position.y));
	}

	public static Move right(final Position position, final int steps) {
		return new Move(position, new Position(position.x + steps, position.y));
	}

	public static Move upRight(final Position position, final int steps) {
		return new Move(position, new Position(position.x + 1, position.y
				+ steps));
	}

	public static Move upLeft(final Position position, final int steps) {
		return new Move(position, new Position(position.x - 1, position.y
				+ steps));
	}

	public static Move downRight(final Position position, final int steps) {
		return new Move(position, new Position(position.x + 1, position.y
				- steps));
	}

	public static Move downLeft(final Position position, final int steps) {
		return new Move(position, new Position(position.x - 1, position.y
				- steps));
	}

	public Direction getDirection() {
		return direction;
	}

	public DirectionType getDirectionType() {
		return direction.getType();
	}

	public Position getStart() {
		return start;
	}

	public Position getDestination() {
		return destination;
	}

	@Override
	public String toString() {
		return ChessboardPositionToArrayPosition.parseArrayPostion(start)
				+ ChessboardPositionToArrayPosition
						.parseArrayPostion(destination);
	}
}