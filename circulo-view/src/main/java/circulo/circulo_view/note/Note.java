package circulo.circulo_view.note;

import java.util.Date;

import org.apache.log4j.Logger;
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

import circulo.circulo_controller.NoteController;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_controller.TagController;
import circulo.circulo_model.Tag;
import circulo.circulo_view.framework.common.CirculoCrudPage;
import circulo.circulo_view.framework.common.CirculoForm;
import circulo.circulo_view.framework.common.CirculoPage;
import circulo.circulo_view.framework.common.Crud;
import circulo.circulo_view.framework.common.MessagePanel;

public class Note extends CirculoCrudPage {

	private static final long serialVersionUID = 1L;

	public Note(circulo.circulo_model.Tag selectedTag,
			circulo.circulo_model.Note selectedNote, final ModalWindow window) {
		super(CrudType.EDIT);
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		add(new NoteForm("NoteForm",
				new CompoundPropertyModel<circulo.circulo_model.Note>(
						selectedNote), selectedTag, this, window));

	}

	public Note(circulo.circulo_model.Tag selectedTag, final ModalWindow window) {
		super(CrudType.NEW);
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		add(new NoteForm("NoteForm",
				new CompoundPropertyModel<circulo.circulo_model.Note>(
						new circulo.circulo_model.Note()), selectedTag, this,
				window));
	}

	public static final class NoteForm extends
			CirculoForm<circulo.circulo_model.Note> {
		private static final long serialVersionUID = 1L;
		private final IModel<circulo.circulo_model.Note> model;
		private final Tag selectedTag;
		private final FeedbackPanel feedback;

		// private final Label duplicateTag = new Label("duplicateTag",
		// "<ul><li>test</li><li>test</li><li>test</li><li>test</li><li>test</li></ul>");

		public NoteForm(String id,
				final IModel<circulo.circulo_model.Note> model,
				final Tag selectedTag, CirculoPage parentPage,
				final ModalWindow window) {
			super(id, model, parentPage, window);
			this.selectedTag = selectedTag;
			feedback = new FeedbackPanel("feedback");
			this.model = model;
			feedback.setOutputMarkupId(true);
			// duplicateTag.setOutputMarkupId(true);
			// duplicateTag.setVisible(false);
			// duplicateTag.setEscapeModelStrings(false);

			TextField<String> subject = getNameTextField();
			Button saveButton = getSaveButton();
			Button cancelButton = getCancelButton();

			add(feedback);
			add(subject);
			add(saveButton);
			add(cancelButton);
			// add(duplicateTag);
		}

		private TextField<String> getNameTextField() {
			TextField<String> retval = new TextField<String>("subject");
			retval.setOutputMarkupId(true);
			retval.setRequired(true);
			return retval;
		}

		private Button getSaveButton() {
			Button retval = new Button("btnSave") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					NoteController noteController = new NoteController();
					circulo.circulo_model.Note note = model.getObject();

					try {
						if (CrudType.NEW.equals(((Crud) getParentPage())
								.getCrudType())) {
							note.setCreatedOn(new Date());
							note.setModifiedOn(new Date());
							TagController tagController = new TagController();
							Tag tag = tagController
									.findByPrimaryKey(selectedTag.getId());
							tag.getNotes().add(note);
							note.getTags().add(tag);
							if (noteController.create(note) > 0) {
								Logger.getLogger(this.getClass()).info(
										"note created!!!");

								MessagePanel.showMessage(this, String.format(
										"The note %s has been created",
										note.getSubject()), "Close",
										getWindow());
							} else {
								Logger.getLogger(this.getClass()).error(
										"error creating note");
							}
						} else if (CrudType.EDIT
								.equals(((Crud) getParentPage()).getCrudType())) {
							note = noteController
									.findByPrimaryKey(note.getId());
							note.setModifiedOn(new Date());
							if (noteController.update(note) > 0) {
								Logger.getLogger(this.getClass()).info(
										"note updated!!!");

								MessagePanel.showMessage(this, String.format(
										"The note %s has been updated",
										note.getSubject()), "Close",
										getWindow());
							} else {
								Logger.getLogger(this.getClass()).error(
										"error creating note");
							}
						}

					} catch (ServiceException e) {
						e.printStackTrace();
					} catch (Exception exception) {
						error(String
								.format("The note %s already exist. Please, create a note with a different name",
										note.getSubject()));
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