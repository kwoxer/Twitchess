package de.fhb.projects.Twitchess.integrationtests.chess.figureMoves;

import static de.fhb.projects.Twitchess.games.chess.player.Color.BLACK;
import static de.fhb.projects.Twitchess.games.chess.player.Color.WHITE;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.exception.FigureCannotMoveIntoDirectionException;
import de.fhb.projects.Twitchess.exception.WrongColorException;
import de.fhb.projects.Twitchess.games.chess.ChessLogic;
import de.fhb.projects.Twitchess.games.chess.GameState;
import de.fhb.projects.Twitchess.games.chess.Position;
import de.fhb.projects.Twitchess.games.chess.figures.Pawn;
import de.fhb.projects.Twitchess.games.chess.move.Move;
import de.fhb.projects.Twitchess.games.chess.player.Player;

public class PawnMovesTest {
	private GameState state;
	private Pawn whitePawn, blackPawn;
	private Player white, black;

	@Before
	public void initPawnTests() {
		white = new Player(WHITE);
		black = new Player(BLACK);
		whitePawn = new Pawn(new Position(3, 3), WHITE);
		blackPawn = new Pawn(new Position(4, 4), BLACK);
		white.add(whitePawn);
		black.add(blackPawn);
		state = new GameState(white, black);
	}

	@Test
	public void whiteUp() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(whitePawn.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteTwoStepsUp() {
		ChessLogic.isValidMove(state, Move.up(whitePawn.getPosition(), 2));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDown() {
		ChessLogic.isValidMove(state, Move.down(whitePawn.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteLeft() {
		ChessLogic.isValidMove(state, Move.left(whitePawn.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteRight() {
		ChessLogic.isValidMove(state, Move.right(whitePawn.getPosition(), 1));
	}

	@Test
	public void whiteUpRightHit() {
		assertTrue(ChessLogic.isValidMove(state,
				Move.upRight(whitePawn.getPosition(), 1)));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDownRight() {
		ChessLogic.isValidMove(state,
				Move.downRight(whitePawn.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteDownLeft() {
		ChessLogic
				.isValidMove(state, Move.downLeft(whitePawn.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void whiteUpLeft() {
		ChessLogic.isValidMove(state, Move.upLeft(whitePawn.getPosition(), 1));
	}

	@Test
	public void blackForward() {
		GameState blackTurn = new GameState(state, Move.NO_MOVE);
		assertTrue(ChessLogic.isValidMove(blackTurn,
				Move.down(blackPawn.getPosition(), 1)));
	}

	@Test(expected = WrongColorException.class)
	public void blackForwardButWrongTurn() {
		ChessLogic.isValidMove(state, Move.down(blackPawn.getPosition(), 1));
	}

	@Test
	public void blackDownLeftHit() {
		GameState blackTurn = new GameState(state, Move.NO_MOVE);
		ChessLogic.isValidMove(blackTurn,
				Move.downLeft(blackPawn.getPosition(), 1));
	}

	@Test(expected = FigureCannotMoveIntoDirectionException.class)
	public void blackBackward() {
		GameState blackTurn = new GameState(state, Move.NO_MOVE);
		ChessLogic.isValidMove(blackTurn, Move.up(blackPawn.getPosition(), 1));
	}

	@Test
	public void initialPawn2StepsForward() {
		white = new Player(WHITE);
		whitePawn = new Pawn(new Position(0, 1), WHITE);
		white.add(whitePawn);
		state = new GameState(white, black);
		assertTrue(ChessLogic.isValidMove(state,
				Move.up(whitePawn.getPosition(), 2)));
	}
}
