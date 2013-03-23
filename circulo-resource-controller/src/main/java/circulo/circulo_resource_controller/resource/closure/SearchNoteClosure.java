package circulo.circulo_resource_controller.resource.closure;

import java.util.List;

import org.apache.commons.collections.Closure;

import circulo.circulo_resource_controller.resource.dto.SearchBean;

public class SearchNoteClosure implements Closure {
	private final List<SearchBean> searchBeanList;

	public SearchNoteClosure(List<SearchBean> searchBeanList) {
		this.searchBeanList = searchBeanList;
	}

	public void execute(Object arg0) {
		Object[] line = (Object[]) arg0;
		searchBeanList.add(new SearchBean(String.valueOf(line[0]), String
				.valueOf(line[1]), SearchBean.NOTE));
	}

}
