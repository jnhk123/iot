package visual;

import java.util.HashMap;
import java.util.List;

public interface VisualService {
	List<EmployeeVO> department_count();
	List<HashMap<String, Object>> year_recruit();
	List<HashMap<String, Object>> month_recruit();
	List<HashMap<String, Object>> year_top3();
	List<HashMap<String, Object>> month_top3();
	//2010�� 10��, 2012�� 5��
}
