package board;

import java.util.HashMap;
import java.util.List;

public interface BoardService {
	boolean insert(BoardVO vo);
	BoardPage list(BoardPage page);
	BoardVO detail(int id);
	void readcnt(int id);
	boolean update(BoardVO vo);
	boolean delete(int id);
	boolean comment_insert(HashMap<String, Object> map);
	List<CommentVO> comment_list(int board_id);
	boolean comment_update(CommentVO vo);
	boolean comment_delete(int id);
	
}
