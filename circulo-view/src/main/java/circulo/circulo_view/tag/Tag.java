package circulo.circulo_view.tag;

import org.apache.log4j.Logger;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import circulo.circulo_controller.ServiceException;
import circulo.circulo_controller.TagController;
import circulo.circulo_view.framework.common.CirculoCrudPage;
import circulo.circulo_view.framework.common.CirculoForm;
import circulo.circulo_view.framework.common.CirculoPage;
import circulo.circulo_view.framework.common.Crud;
import circulo.circulo_view.framework.common.MessagePanel;

public class Tag extends CirculoCrudPage {

	private static final long serialVersionUID = 1L;

	public Tag(final PageReference modalWindowPage,
			circulo.circulo_model.Tag tag, final ModalWindow window) {
		super(CrudType.EDIT);
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		add(new TagForm("TagForm",
				new CompoundPropertyModel<circulo.circulo_model.Tag>(tag),
				this, window));

	}

	public Tag(final PageReference modalWindowPage, final ModalWindow window) {
		super(CrudType.NEW);
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		add(new TagForm("TagForm",
				new CompoundPropertyModel<circulo.circulo_model.Tag>(
						new circulo.circulo_model.Tag()), this, window));
	}

	public static final class TagForm extends
			CirculoForm<circulo.circulo_model.Tag> {
		private static final long serialVersionUID = 1L;
		private final IModel<circulo.circulo_model.Tag> model;
		private final FeedbackPanel feedback;

		// private final Label duplicateTag = new Label("duplicateTag",
		// "<ul><li>test</li><li>test</li><li>test</li><li>test</li><li>test</li></ul>");

		public TagForm(String id,
				final IModel<circulo.circulo_model.Tag> model,
				CirculoPage parentPage, final ModalWindow window) {
			super(id, model, parentPage, window);
			feedback = new FeedbackPanel("feedback");
			this.model = model;
			feedback.setOutputMarkupId(true);
			// duplicateTag.setOutputMarkupId(true);
			// duplicateTag.setVisible(false);
			// duplicateTag.setEscapeModelStrings(false);

			TextField<String> name = getNameTextField();
			Button saveButton = getSaveButton();
			Button cancelButton = getCancelButton();

			add(feedback);
			add(name);
			add(saveButton);
			add(cancelButton);
			// add(duplicateTag);
		}

		private TextField<String> getNameTextField() {
			TextField<String> retval = new TextField<String>("name");
			retval.setOutputMarkupId(true);
			retval.setRequired(true);
			return retval;
		}

		private Button getSaveButton() {
			Button retval = new Button("btnSave") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					TagController tagController = new TagController();
					circulo.circulo_model.Tag tag = model.getObject();

					try {
						if (CrudType.NEW.equals(((Crud) getParentPage())
								.getCrudType())) {
							if (tagController.create(tag) > 0) {
								Logger.getLogger(this.getClass()).info(
										"tag created!!!");

								MessagePanel.showMessage(this, String.format(
										"The tag %s has been created",
										tag.getName()), "Close", getWindow());
							} else {
								Logger.getLogger(this.getClass()).error(
										"error creating user");
							}
						} else if (CrudType.EDIT
								.equals(((Crud) getParentPage()).getCrudType())) {
							tag = tagController.findByPrimaryKey(tag.getId());
							if (tagController.update(tag) > 0) {
								Logger.getLogger(this.getClass()).info(
										"tag updated!!!");

								MessagePanel.showMessage(this, String.format(
										"The tag %s has been updated",
										tag.getName()), "Close", getWindow());
							} else {
								Logger.getLogger(this.getClass()).error(
										"error creating user");
							}
						}

					} catch (ServiceException e) {
						e.printStackTrace();
					} catch (Exception exception) {
						error(String
								.format("The tag %s already exist. Please, create a tag with a different name",
										tag.getName()));
					}
				}
			};

			return retval;
		}

		private Button getCancelButton() {
			Button retval = new AjaxButton("btnCancel") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					getWindow().close(target);
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
				}
			};
			retval.setDefaultFormProcessing(false);
			return retval;
		}
	}

}