package customer;

import java.util.List;

public interface CustomerService {
	
	//CRUD: ����create, ��ȸread, ����update, ����delete
	//����
	void insert(CustomerVO vo);
	//��ȸ
	List<CustomerVO> list();
	CustomerVO detail(int id);
	//����
	void update(CustomerVO vo);
	//����
	void delete(int id);
}
