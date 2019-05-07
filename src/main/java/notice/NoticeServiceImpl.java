package notice;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired private NoticeDAO dao;
	
	@Override
	public void insert(NoticeVO vo) {
		dao.insert(vo);
	}

	@Override
	public List<NoticeVO> list() {
		return dao.list();
	}
	
	@Override
	public List<NoticeVO> list(HashMap<String, String> map) {
		return dao.list(map);
	}
	
	@Override
	public NoticePage list(NoticePage page) {
		return dao.list(page);
	}


	@Override
	public NoticeVO detail(int id) {
		return dao.detail(id);
	}

	@Override
	public void update(NoticeVO vo) {
		dao.update(vo);
	}

	@Override
	public void delete(int id) {
		dao.delete(id);
	}

	@Override
	public void read(int id) {
		dao.read(id);
	}

	@Override
	public void reply_insert(NoticeVO vo) {
		dao.reply_insert(vo);
	}

	
}
