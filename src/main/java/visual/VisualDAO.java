package visual;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class VisualDAO {
	
	@Autowired @Qualifier("hr") private SqlSession sql;
	
	
	public List<EmployeeVO> department_count() {
		return sql.selectList("visual-mapper.department_count");
	}
	
	public List<HashMap<String, Object>> year_recruit() {
		return sql.selectList("visual-mapper.year_recruit");
	}

	public List<HashMap<String, Object>> month_recruit() {
		return sql.selectList("visual-mapper.month_recruit");
	}
	
	public List<HashMap<String, Object>> year_top3() {
		return sql.selectList("visual-mapper.year_top3");
	}

	public List<HashMap<String, Object>> month_top3() {
		return sql.selectList("visual-mapper.month_top3");
	}

}
