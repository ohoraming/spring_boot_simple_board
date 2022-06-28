package board.boardtest.repository;

import board.boardtest.domain.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryBoardRepositoryTest {
    MemoryBoardRepository repository = new MemoryBoardRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Board board = new Board();
        board.setTitle("첫 게시글!");

        repository.save(board);

        Board result = repository.findById(board.getId()).get();
        assertThat(board).isEqualTo(result);
    }

    @Test
    public void findByTitle() {
        Board board1 = new Board();
        board1.setTitle("title1");
        repository.save(board1);

        Board board2 = new Board();
        board2.setTitle("title2");
        repository.save(board2);

        Board result = repository.findByTitle("title2").get();

        assertThat(result).isEqualTo(board2);
    }

    @Test
    public void findAll() {
        Board board1 = new Board();
        board1.setTitle("title1");
        repository.save(board1);

        Board board2 = new Board();
        board2.setTitle("title2");
        repository.save(board2);

        List<Board> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}
