package circulo.circulo_view.dummy;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import circulo.circulo_view.framework.common.CirculoPage;

public class PageOk extends CirculoPage {

	private static final long serialVersionUID = 1L;

	public PageOk(PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
	}
}
