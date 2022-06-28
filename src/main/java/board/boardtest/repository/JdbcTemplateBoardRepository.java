package board.boardtest.repository;

import board.boardtest.domain.Board;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateBoardRepository implements BoardRepository {

    private final JdbcTemplate jdbcTemplate;

//    @Autowired

    /**
     * 생성자가 하나면 @Autowired생략 가능
     */
    public JdbcTemplateBoardRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Board save(Board board) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("board").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", board.getTitle());
        parameters.put("content", board.getContent());
        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        board.setId(key.longValue());
        return board;
    }

    @Override
    public Optional<Board> findById(Long id) {
        List<Board> result = jdbcTemplate.query("select * from board where id = ?", boardRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Board> findByTitle(String title) {
        List<Board> result = jdbcTemplate.query("select * from board where title = ?", boardRowMapper(), title);
        return result.stream().findAny();
    }

    @Override
    public List<Board> findAll() {
        return jdbcTemplate.query("select * from board", boardRowMapper());
    }

    private RowMapper<Board> boardRowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board();
            board.setId(rs.getLong("id"));
            board.setTitle(rs.getString("title"));
            board.setContent(rs.getString("content"));
            return board;
        };
    }
}
