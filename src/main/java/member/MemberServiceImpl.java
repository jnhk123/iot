package member;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired private MemberDAO dao;
	
	@Override
	public boolean insert(MemberVO vo) {
		return dao.insert(vo);
	}

	@Override
	public List<MemberVO> list() {
		return null;
	}

	@Override
	public MemberVO detail(String userid) {
		return null;
	}

	@Override
	public boolean id_check(String userid) {
		return dao.id_check(userid);
	}

	@Override
	public boolean update(MemberVO vo) {
		return false;
	}

	@Override
	public boolean delete(String userid) {
		return false;
	}

	@Override
	public MemberVO login(HashMap<String, String> map) {
		return dao.login(map);
	}

}
