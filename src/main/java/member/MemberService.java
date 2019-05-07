package member;

import java.util.HashMap;
import java.util.List;

public interface MemberService {
	//CRUD
	boolean insert(MemberVO vo);
	List<MemberVO> list();
	MemberVO detail(String userid);
	MemberVO login(HashMap<String, String> map);
	boolean id_check(String userid);
	boolean update(MemberVO vo);
	boolean delete(String userid);
}
