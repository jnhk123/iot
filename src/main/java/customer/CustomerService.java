package customer;

import java.util.List;

public interface CustomerService {
	
	//CRUD: 저장create, 조회read, 변경update, 삭제delete
	//저장
	void insert(CustomerVO vo);
	//조회
	List<CustomerVO> list();
	CustomerVO detail(int id);
	//변경
	void update(CustomerVO vo);
	//삭제
	void delete(int id);
}
