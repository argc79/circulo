package circulo.circulo_view.framework.common;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import circulo.circulo_controller.Controller;
import circulo.circulo_controller.ControllerProvider;

import com.jquery.JQueryResourceReference;

public class CirculoPage extends WebPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private ControllerProvider controller;

	public Controller getController() {
		return controller;
	}

	@Override
	public void renderHead(IHeaderResponse response) {
		response.renderJavaScriptReference(new JQueryResourceReference());
	}
}
