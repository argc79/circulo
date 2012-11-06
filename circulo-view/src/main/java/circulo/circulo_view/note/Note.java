package circulo.circulo_view.note;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import circulo.circulo_controller.ControllerProvider;
import circulo.circulo_controller.NoteController;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_model.Tag;
import circulo.circulo_view.HomePage;
import circulo.circulo_view.framework.common.CirculoPage;
import circulo.circulo_view.framework.common.MessagePanel;

public class Note extends CirculoPage {

	private static final long serialVersionUID = 1L;

	public Note(PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		// User needs to be retrieved from Spring???(or factory, see binaryit)
		add(new NoteForm("NoteForm",
				new CompoundPropertyModel<circulo.circulo_model.Note>(
						new circulo.circulo_model.Note())));
	}

	public static final class NoteForm extends Form<circulo.circulo_model.Note> {
		private static final long serialVersionUID = 1L;
		private TextField<String> subject;
		private DropDownChoice<Tag> tag;
		private Button btnSave;
		private Button btnCancel;
		@SpringBean
		private ControllerProvider controller;

		public NoteForm(String id,
				final IModel<circulo.circulo_model.Note> model) {
			super(id, model);
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);
			List<Tag> tags = null;
			try {
				// tags = ControllerProvider.createTagController().findAll();
				tags = controller.getTagController().findAll();
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}

			subject = new TextField<String>("subject");
			subject.setOutputMarkupId(true);
			subject.setRequired(true);

			tag = new DropDownChoice<Tag>("tag", tags,
					new IChoiceRenderer<Tag>() {
						private static final long serialVersionUID = 1L;

						public Object getDisplayValue(Tag object) {
							return object.getName();
						}

						public String getIdValue(Tag object, int index) {
							return String.valueOf(((Tag) object).getId());
						}
					});

			btnSave = new Button("btnSave") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					// NoteController userController = ControllerProvider
					// .createNoteController();
					NoteController userController = controller
							.getNoteController();
					circulo.circulo_model.Note note = model.getObject();
					try {
						if (userController.create(note) > 0) {
							Logger.getLogger(this.getClass()).info(
									"note created!!!");
							MessagePanel.showMessage(this, String.format(
									"The note %s has been created",
									note.getSubject()), "Go to login",
									HomePage.class.getName());
						} else {
							Logger.getLogger(this.getClass()).error(
									"error creating user");
						}
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
			btnCancel = new Button("btnCancel") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					setResponsePage(HomePage.class);
				}
			};
			btnCancel.setDefaultFormProcessing(false);

			add(feedback);
			add(subject);
			add(tag);
			add(btnSave);
			add(btnCancel);
		}
	}
}