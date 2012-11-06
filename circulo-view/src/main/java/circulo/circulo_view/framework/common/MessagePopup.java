package circulo.circulo_view.framework.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;

public class MessagePopup extends WebPage {

	private static final long serialVersionUID = 1L;

	/**
	 * @param window
	 */
	public MessagePopup(final ModalWindow window) {
		add(new AjaxLink<Void>("close") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.close(target);
			}
		});

	}

}