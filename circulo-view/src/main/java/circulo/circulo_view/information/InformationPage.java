package circulo.circulo_view.information;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import circulo.circulo_view.framework.common.CirculoSubPage;

public class InformationPage extends CirculoSubPage {

	private static final long serialVersionUID = 1L;

	public InformationPage(final PageParameters parameters,
			boolean backButtonVisible) {
		add(new InformationPageForm("InformationPageForm", parameters,
				backButtonVisible));
	}

	public InformationPage(final PageParameters parameters,
			boolean backButtonVisible, ModalWindow window) {
		add(new InformationPageForm("InformationPageForm", parameters,
				backButtonVisible, window));
	}

	private static class InformationPageForm extends Form<String> {

		private static final long serialVersionUID = 1L;
		// final WebMarkupContainer historyBack = new WebMarkupContainer(
		// "historyBack");

		private final PageParameters parameters;

		public InformationPageForm(String id, PageParameters parameters,
				boolean backButtonVisible, final ModalWindow window) {
			super(id);
			this.parameters = parameters;

			Button backButton = new AjaxButton("backButton",
					new IModel<String>() {

						private static final long serialVersionUID = 1L;

						public void detach() {
						}

						public String getObject() {
							return InformationPageForm.this.parameters.get(
									"textBackButton").toString();
						}

						public void setObject(String object) {

						}
					}) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					window.close(target);
					// NotesMainForm f = (NotesMainForm) window
					// .findParent(NotesMainForm.class);
					// f.setResponsePage(Notes.class);
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
				}
			};

			// historyBack.setEscapeModelStrings(false);
			// historyBack.setVisible(!backButtonVisible);
			backButton.setDefaultFormProcessing(false);
			add(new Label("information", parameters.get("message").toString()));
			backButton.setVisible(backButtonVisible);
			add(backButton);
			// add(historyBack);
		}

		public InformationPageForm(String id, PageParameters parameters,
				boolean backButtonVisible) {
			super(id);
			this.parameters = parameters;

			Button backButton = new Button("backButton", new IModel<String>() {

				private static final long serialVersionUID = 1L;

				public void detach() {

				}

				public void setObject(String object) {
					System.out.println("test");
				}

				public String getObject() {
					return InformationPageForm.this.parameters.get(
							"textBackButton").toString();
				}
			}) {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					try {
						Class clazz = Class
								.forName(InformationPageForm.this.parameters
										.get("backButtonClass").toString());
						setResponsePage(clazz);
					} catch (Exception e) {

					}
				}
			};

			backButton.setDefaultFormProcessing(false);
			add(new Label("information", parameters.get("message").toString()));
			backButton.setVisible(backButtonVisible);
			add(backButton);
		}

	}
}
