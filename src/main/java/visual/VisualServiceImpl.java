package visual;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisualServiceImpl implements VisualService {
	
	@Autowired private VisualDAO dao;

	@Override
	public List<EmployeeVO> department_count() {
		return dao.department_count();
	}

	@Override
	public List<HashMap<String, Object>> year_recruit() {
		return dao.year_recruit();
	}

	@Override
	public List<HashMap<String, Object>> month_recruit() {
		return dao.month_recruit();
	}

	@Override
	public List<HashMap<String, Object>> year_top3() {
		return dao.year_top3();
	}

	@Override
	public List<HashMap<String, Object>> month_top3() {
		return dao.month_top3();
	}

}
