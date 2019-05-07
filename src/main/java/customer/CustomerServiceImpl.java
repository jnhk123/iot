package customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired private CustomerDAO dao;
	
	@Override
	public void insert(CustomerVO vo) {
		dao.insert(vo);
	}

	@Override
	public List<CustomerVO> list() {
		return dao.list();
	}

	@Override
	public CustomerVO detail(int id) {
		return dao.detail(id);
	}

	@Override
	public void update(CustomerVO vo) {
		dao.update(vo);
	}

	@Override
	public void delete(int id) {
		dao.delete(id);
	}

}
