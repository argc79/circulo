package circulo.circulo_view.framework.common;

import java.io.Serializable;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

public abstract class CirculoModalWindow implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ModalWindow modalWindow;

	public CirculoModalWindow(ModalWindow modalWindow, String title) {
		this.modalWindow = modalWindow;
		initialize(title);
	}

	private void initialize(String title) {
		modalWindow.setCookieName("modal-1");

		modalWindow.setTitle(title);
		modalWindow
				.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {
					private static final long serialVersionUID = 1L;

					public boolean onCloseButtonClicked(AjaxRequestTarget target) {
						return true;
					}
				});
		modalWindow
				.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
					private static final long serialVersionUID = 1L;

					public void onClose(AjaxRequestTarget target) {
						CirculoModalWindow.this.onClose(target);
					}
				});
	}

	public ModalWindow getModalWindow() {
		return modalWindow;
	}

	private void setPageCreator(final Page page) {
		modalWindow.setPageCreator(new ModalWindow.PageCreator() {
			private static final long serialVersionUID = 1L;

			public Page createPage() {
				return page;
			}
		});
	}

	public void show(Page page, AjaxRequestTarget target)
			throws CirculoException {
		if (page instanceof Crud) {
			setPageCreator(page);
			modalWindow.show(target);
			return;
		}
		throw new CirculoException.CirculoNotCrudTypeDefinedException();
	}

	public void show(AjaxRequestTarget target) throws CirculoException {
		modalWindow.show(target);
	}

	public abstract void onClose(AjaxRequestTarget target);
}
