package board.boardtest.service;

import board.boardtest.domain.Board;
import board.boardtest.repository.MemoryBoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class BoardServiceTest {

    BoardService boardService;
    MemoryBoardRepository boardRepository;

    @BeforeEach
    public void beforeEach() {
        boardRepository = new MemoryBoardRepository();
        boardService = new BoardService(boardRepository);
    }

    @AfterEach
    public void afterEach() {
        boardRepository.clearStore();
    }

    @Test
    public void 새_게시글_작성() {
        //given
        Board board = new Board();
        board.setTitle("title!");

        //when
        Long saveId = boardService.create(board);

        //then
        Board findBoard = boardService.findOne(saveId).get();
        assertThat(board.getTitle()).isEqualTo(findBoard.getTitle());
    }

    @Test
    public void 중복_제목_예외() {
        //given
        Board board1 = new Board();
        board1.setTitle("title1");
        
        Board board2 = new Board();
        board2.setTitle("title1");

        //when
        boardService.create(board1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> boardService.create(board2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 제목입니다.");
        //then
    }

    @Test
    public void findOne() {
    }
}