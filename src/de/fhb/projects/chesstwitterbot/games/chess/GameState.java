package de.fhb.projects.chesstwitterbot.games.chess;

import static de.fhb.projects.chesstwitterbot.games.chess.ChessLogic.CHESSBOARD_HEIGHT;
import static de.fhb.projects.chesstwitterbot.games.chess.ChessLogic.CHESSBOARD_WIDTH;
import static de.fhb.projects.chesstwitterbot.games.chess.figures.NoFigure.NO_FIGURE;
import de.fhb.projects.chesstwitterbot.games.chess.figures.Figure;
import de.fhb.projects.chesstwitterbot.games.chess.move.Move;
import de.fhb.projects.chesstwitterbot.games.chess.player.Color;
import de.fhb.projects.chesstwitterbot.games.chess.player.Player;

public final class GameState {
	private String playerName;
	private Figure[][] board;
	private Player currentTurnPlayer;
	private Player white, black;
	private Move lastMove;
	private GameState lastState;

	public GameState(final Player white, final Player black) {
		playerName = "";
		board = new Figure[CHESSBOARD_WIDTH][CHESSBOARD_HEIGHT];
		for (int x = 0; x < CHESSBOARD_WIDTH; x++) {
			for (int y = 0; y < CHESSBOARD_HEIGHT; y++) {
				board[x][y] = NO_FIGURE;
			}
		}
		this.white = white;
		this.black = black;
		currentTurnPlayer = white;
		lastMove = Move.NO_MOVE;
		updatePositions();
	}

	public GameState(final GameState oldState, final Move move) {
		lastState = oldState;
		playerName = oldState.playerName;
		board = oldState.board.clone();
		white = oldState.white;
		black = oldState.black;
		if(oldState.currentTurnPlayer.equals(oldState.white)) {
			currentTurnPlayer = black;
		} else {
			currentTurnPlayer = white;
		}
		lastMove = move;
		board[move.getStart().x][move.getStart().y].setPosition(new Position(
				move.getDestination().x, move.getDestination().y));
		updatePositions();
	}

	public void updatePositions() {
		for (int i = 0; i < white.getFiguresInGame().size(); i++) {
			board[white.getFiguresInGame().get(i).getPosition().x][white
					.getFiguresInGame().get(i).getPosition().y] = white
					.getFiguresInGame().get(i);
		}
		for (int i = 0; i < black.getFiguresInGame().size(); i++) {
			board[black.getFiguresInGame().get(i).getPosition().x][black
					.getFiguresInGame().get(i).getPosition().y] = black
					.getFiguresInGame().get(i);
		}
	}

	public Figure getMovingFigure(final Move absoluteMove) {
		return board[absoluteMove.getStart().x][absoluteMove.getStart().y];
	}

	public Color getCurrentColor() {
		return currentTurnPlayer.getColor();
	}

	public Player getCurrentPlayer() {
		return currentTurnPlayer;
	}
	
	public Player getOpponent(final Player player) {
		if(player.equals(white)) {
			return black;
		} else {
			return white;
		}
	}

	public Move getLastMove() {
		return lastMove;
	}

	public Figure getFigure(final int x, final int y) {
		return board[x][y];
	}

	public GameState getLastState() {
		return lastState;
	}
}