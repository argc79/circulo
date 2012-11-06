package circulo.circulo_view.framework.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

public abstract class ConfirmationDeletePanel extends Panel {
	private static final long serialVersionUID = 1L;

	public ConfirmationDeletePanel(String id, String message) {
		super(id);
		add(new Label("message", message));
		add(new Link<Void>("confirm") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				onConfirm();
			}
		});
		add(new Link<Void>("cancel") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				onCancel();
			}
		});
	}

	protected abstract void onCancel();

	protected abstract void onConfirm();
}