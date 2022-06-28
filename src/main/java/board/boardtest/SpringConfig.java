package board.boardtest;

import board.boardtest.repository.BoardRepository;
import board.boardtest.repository.JdbcTemplateBoardRepository;
import board.boardtest.repository.JpaBoardRepository;
import board.boardtest.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    //    private final DataSource dataSource;
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    @Bean
    public BoardService boardService() {
        return new BoardService(boardRepository());
    }

    @Bean
    public BoardRepository boardRepository() {
//        return new JdbcTemplateBoardRepository(dataSource);
        return new JpaBoardRepository(em);
    }

}
