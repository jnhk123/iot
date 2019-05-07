package board;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAO {
	@Autowired @Qualifier("iot") SqlSession sql;

	public boolean comment_update(CommentVO vo) {
		return sql.update("board-mapper.comment_update", vo)>0 ? true : false;
	}

	public List<CommentVO> comment_list(int board_id) {
		return sql.selectList("board-mapper.comment_list", board_id);
	}
	
	public boolean insert(BoardVO vo) {
		return sql.insert("board-mapper.insert", vo)>0 ? true : false;
	}

	public BoardPage list(BoardPage page) {
		page.setTotalList((Integer)sql.selectOne("board-mapper.total", page));
		List<BoardVO> list = sql.selectList("board-mapper.list", page);
		page.setList(list);
		return page;
	}

	public BoardVO detail(int id) {
		return sql.selectOne("board-mapper.detail", id);
	}

	public void readcnt(int id) {
		sql.update("board-mapper.readcnt", id);
	}

	public boolean update(BoardVO vo) {
		return sql.update("board-mapper.update", vo)>0? true: false;
	}

	public boolean delete(int id) {
		return sql.delete("board-mapper.delete", id)>0 ? true: false;
	}
	
	public boolean comment_insert(HashMap<String, Object> map) {
		return sql.insert("board-mapper.comment_insert", map)>0 ? true : false;
	}
	
	public boolean comment_delete(int id) {
		return sql.delete("board-mapper.comment_delete",id) > 0 ? true : false;
	}
}













