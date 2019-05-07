package member;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository	
public class MemberDAO {

	public boolean insert(MemberVO vo) {
		return sql.insert("member-mapper.insert", vo)>0 ? true : false;
	}

	public List<MemberVO> list() {
		return null;
	}

	public MemberVO detail(String userid) {
		return null;
	}
	
	public MemberVO login(HashMap<String, String> map) {
		return sql.selectOne("member-mapper.login", map);
	}
	
	@Autowired @Qualifier("iot") private SqlSession sql;

	public boolean id_check(String userid) {
		return (Integer)sql.selectOne(
				"member-mapper.id_check", userid) == 0 ? true : false;
	}

	public boolean update(MemberVO vo) {
		return false;
	}

	public boolean delete(String userid) {
		return false;
	}
}
