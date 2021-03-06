package de.fhb.projects.Twitchess.controller.chesscommands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;

import de.fhb.projects.Twitchess.controller.chesscommands.CancelGameChessCommand;
import de.fhb.projects.Twitchess.data.ChessStateDAOInterface;
import de.fhb.projects.Twitchess.data.ChessStateVO;
import de.fhb.projects.Twitchess.data.ResultType;
import de.fhb.projects.Twitchess.exception.ChessManagerException;

public class CancelGameChessCommandTest {

	private ChessStateDAOInterface dao;
	private CancelGameChessCommand chessCommand;
	private ChessStateVO chessState;
	private List<ChessStateVO> state;

	@Before
	public void init() {
		dao = EasyMock.createStrictMock(ChessStateDAOInterface.class);
		chessCommand = new CancelGameChessCommand(dao);
		state = new ArrayList<ChessStateVO>();
		chessState = new ChessStateVO();
		chessState.setId(1);
		chessState
				.setFen("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
		chessState.setPlayerName("player1");
		state.add(chessState);
	}

	@Test(expected = ChessManagerException.class)
	public void TestProcessInputNoGame() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				new ArrayList<ChessStateVO>());
		EasyMock.replay(dao);
		chessCommand.setDao(dao);
		chessCommand.processInput("player1", new ArrayList<String>());
	}

	@Test
	public void testProcessInput() throws SQLException, ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		ChessStateVO vo = new ChessStateVO();

		vo.setId(1);
		dao.updateTable(vo);
		EasyMock.expectLastCall().andAnswer(new IAnswer<Object>() {
			public Object answer() {
				ChessStateVO arg1 = (ChessStateVO) EasyMock
						.getCurrentArguments()[0];
				assertEquals(ResultType.ABORTED.getNumber(), arg1.getResult()
						.intValue());
				return null;
			}
		});
		EasyMock.replay(dao);

		chessCommand.setDao(dao);
		assertEquals("Game has been successfully aborted!",
				chessCommand.processInput("player1", new ArrayList<String>()));

		EasyMock.verify(dao);
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputManyGamesAreOpen() throws SQLException,
			ChessManagerException {
		state.add(state.get(0));
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.setDao(dao);
		assertNotNull(chessCommand.processInput("player1",
				new ArrayList<String>()));
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputEmptyParam() throws SQLException, ChessManagerException {
		List<String> parameter = new ArrayList<String>();
		parameter.add("");
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		EasyMock.replay(dao);
		chessCommand.setDao(dao);
		assertNotNull(chessCommand.processInput("player1", parameter));
	}

	@Test(expected = ChessManagerException.class)
	public void testProcessInputSQLException() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andThrow(
				new SQLException());
		ChessStateVO vo = new ChessStateVO();

		vo.setId(1);
		dao.updateTable(vo);
		EasyMock.replay(dao);

		chessCommand.setDao(dao);
		assertNotNull(chessCommand.processInput("player1",
				new ArrayList<String>()));

	}

	@Test
	public void testProcessInputNullParameter() throws SQLException,
			ChessManagerException {
		EasyMock.expect(dao.findNotFinishedGameByPlayer("player1")).andReturn(
				state);
		ChessStateVO vo = new ChessStateVO();

		vo.setId(1);
		dao.updateTable(vo);
		EasyMock.replay(dao);

		chessCommand.setDao(dao);
		assertEquals("Game has been successfully aborted!",
				chessCommand.processInput("player1", null));

		EasyMock.verify(dao);
	}

}
