package notice;

import java.util.HashMap;
import java.util.List;

public interface NoticeService {
	//CRUD
	void insert(NoticeVO vo);
	void reply_insert(NoticeVO vo);
	List<NoticeVO> list();
	List<NoticeVO> list(HashMap<String, String> map);
	NoticePage list(NoticePage page);
	NoticeVO detail(int id);
	void update(NoticeVO vo);
	void read(int id);
	void delete(int id);
}
