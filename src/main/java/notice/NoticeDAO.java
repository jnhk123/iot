package notice;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class NoticeDAO {
	@Autowired @Qualifier("iot") private SqlSession sql;
	
	public void insert(NoticeVO vo) {
		sql.insert("notice-mapper.insert", vo);
	}
	
	public void reply_insert(NoticeVO vo) {
		sql.insert("notice-mapper.reply_insert", vo);
	}

	public List<NoticeVO> list() {
		return sql.selectList("notice-mapper.list");
	}
	
	public List<NoticeVO> list(HashMap<String, String> map) {
		return sql.selectList("notice-mapper.list", map);
	}
	
	public NoticePage list(NoticePage page) {
		//총 목록수 조회
		page.setTotalList( 
			(Integer)sql.selectOne("notice-mapper.total", page) );
		List<NoticeVO> list = sql.selectList("notice-mapper.list", page);
		page.setList(list);
		return page;
	}

	public NoticeVO detail(int id) {
		return sql.selectOne("notice-mapper.detail", id);
	}

	public void update(NoticeVO vo) {
		sql.update("notice-mapper.update", vo);
	}
	
	public void read(int id) {
		sql.update("notice-mapper.read", id);
	}

	public void delete(int id) {
		sql.delete("notice-mapper.delete", id);
	}

}
