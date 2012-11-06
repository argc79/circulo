package circulo.circulo_view.framework.common;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.spring.injection.annot.SpringBean;

import circulo.circulo_controller.Controller;
import circulo.circulo_controller.ControllerProvider;

public class CirculoPage extends WebPage {

	private static final long serialVersionUID = 1L;
	@SpringBean
	private ControllerProvider controller;

	public Controller getController() {
		return controller;
	}
}
