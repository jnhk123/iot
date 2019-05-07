package board;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired BoardDAO dao;
	

	@Override
	public boolean comment_update(CommentVO vo) {
		return dao.comment_update(vo);
	}

	@Override
	public boolean comment_insert(HashMap<String, Object> map) {
		return dao.comment_insert(map);
	}

	@Override
	public List<CommentVO> comment_list(int board_id) {
		return dao.comment_list(board_id);
	}
	
	@Override
	public boolean insert(BoardVO vo) {
		return dao.insert(vo);
	}

	@Override
	public BoardPage list(BoardPage page) {
		return dao.list(page);
	}

	@Override
	public BoardVO detail(int id) {
		return dao.detail(id);
	}

	@Override
	public void readcnt(int id) {
		dao.readcnt(id);
	}

	@Override
	public boolean update(BoardVO vo) {
		return dao.update(vo);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public boolean comment_delete(int id) {
		return dao.comment_delete(id);
	}

}
