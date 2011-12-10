package de.fhb.projects.chesstwitterbot.games.chess.figures;

import de.fhb.projects.chesstwitterbot.games.chess.Position;
import de.fhb.projects.chesstwitterbot.games.chess.move.DirectionType;
import de.fhb.projects.chesstwitterbot.games.chess.move.OneStepDirection;

public final class King extends Figure {
	public King(final Position position) {
		super(position);
		directions.add(new OneStepDirection(DirectionType.UP));
		directions.add(new OneStepDirection(DirectionType.DOWN));
		directions.add(new OneStepDirection(DirectionType.LEFT));
		directions.add(new OneStepDirection(DirectionType.RIGHT));
		directions.add(new OneStepDirection(DirectionType.UPRIGHT));
		directions.add(new OneStepDirection(DirectionType.UPLEFT));
		directions.add(new OneStepDirection(DirectionType.DOWNRIGHT));
		directions.add(new OneStepDirection(DirectionType.DOWNLEFT));
	}

	@Override
	public String toString() {
		return "King [directions=" + directions + ", color=" + color
				+ ", position=" + position + "]";
	}
}