package circulo.circulo_view.framework.common;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

import circulo.circulo_controller.Controller;

public class CirculoForm<T> extends Form<T> {

	private static final long serialVersionUID = 1L;
	private final CirculoPage parentPage;
	private ModalWindow window;

	public CirculoForm(String id, CirculoPage parentPage) {
		super(id);
		this.parentPage = parentPage;
	}

	public CirculoForm(String id, IModel<T> model, CirculoPage parentPage,
			ModalWindow windowContainer) {
		this(id, model, parentPage);
		this.window = windowContainer;
	}

	public CirculoForm(String id, IModel<T> model, CirculoPage parentPage) {
		super(id, model);
		this.parentPage = parentPage;
	}

	protected ModalWindow getWindow() {
		return window;
	}

	protected WebMarkupContainer findComponent(
			Class<? extends WebMarkupContainer> clz) {
		if (window == null) {
			return null;
		}
		return window.findParent(clz);
	}

	public Controller getController() {
		return parentPage.getController();
	}

	public CirculoPage getParentPage() {
		return parentPage;
	}

}
