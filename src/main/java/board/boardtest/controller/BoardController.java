package board.boardtest.controller;

import board.boardtest.domain.Board;
import board.boardtest.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model) {
        List<Board> board = boardService.findBoard();
        model.addAttribute("board", board);
        return "home";
    }

    @GetMapping("/board/new")
    public String createFrom() {
        return "board/createBoardForm";
    }

    @PostMapping("/board/new")
    public String createBoard(BoardForm form) {
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());

//        System.out.println("title = " + board.getTitle());
//        System.out.println("content = " + board.getContent());

        boardService.create(board);

        return "redirect:/";
    }

//    @GetMapping("/board/")
}
