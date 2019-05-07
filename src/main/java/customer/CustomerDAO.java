package customer;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO {
	@Autowired @Qualifier("iot")  private SqlSession sql;
	
	//CRUD
	public void insert(CustomerVO vo) {
		sql.insert("customer-mapper.insert", vo);
	}
	
	public List<CustomerVO> list(){
		return sql.selectList("customer-mapper.list");
	}
	
	public CustomerVO detail(int id) {
		return sql.selectOne("customer-mapper.detail", id);
	}
	
	public void update(CustomerVO vo) {
		sql.update("customer-mapper.update", vo);
	}
	
	public void delete(int id) {
		sql.delete("customer-mapper.delete", id);
	}
	
}
