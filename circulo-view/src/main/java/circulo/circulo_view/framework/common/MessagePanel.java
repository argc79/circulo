package circulo.circulo_view.framework.common;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import circulo.circulo_view.information.InformationPage;

public final class MessagePanel {

	public static void showMessage(Component parent, String message,
			String textBackButton, String backButtonClass) {
		PageParameters parameters = new PageParameters();
		parameters.add("message", message);
		parameters.add("textBackButton", textBackButton);
		parameters.add("backButtonClass", backButtonClass);
		parent.setResponsePage(new InformationPage(parameters, true));
	}

	public static void showMessage(Component parent, String message,
			String textBackButton) {
		PageParameters parameters = new PageParameters();
		parameters.add("message", message);
		parameters.add("textBackButton", textBackButton);
		parent.setResponsePage(new InformationPage(parameters, false));
	}

	public static void showMessage(Component parent, String message,
			String textBackButton, ModalWindow window) {
		PageParameters parameters = new PageParameters();
		parameters.add("message", message);
		parameters.add("textBackButton", textBackButton);
		parent.setResponsePage(new InformationPage(parameters, true, window));
	}
}
