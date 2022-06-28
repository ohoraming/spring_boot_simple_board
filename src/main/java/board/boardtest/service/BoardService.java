package board.boardtest.service;

import board.boardtest.domain.Board;
import board.boardtest.repository.BoardRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /**
     * 게시글 작성
     */
    public long create(Board board) {
        validateDuplicateTitle(board); // 중복 title 검증
        boardRepository.save(board);
        return board.getId(); // 임의로 새 게시글을 쓰면 게시글 아이디를 반환하기로 정함
    }

    private void validateDuplicateTitle(Board board) {
        boardRepository.findByTitle(board.getTitle())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 제목입니다.");
                });
    }

    /**
     * 전체 게시글 조회
     */
    public List<Board> findBoard() {
        return boardRepository.findAll();
    }

    public Optional<Board> findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }
}
