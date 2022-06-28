package board.boardtest.service;

import board.boardtest.domain.Board;
import board.boardtest.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 통합 test
 * Spring container와 DB까지 연결한 test
 * BoardServiceTest의 경우, DB연결 없이 순수한 JAVA 코드만을 가지고 테스트했음
 */
@SpringBootTest
@Transactional
class BoardServiceIntegrationTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;

    @Test
    public void 새_게시글_작성() {
        //given
        Board board = new Board();
        board.setTitle("title!");
        board.setContent("it's content!");

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
}