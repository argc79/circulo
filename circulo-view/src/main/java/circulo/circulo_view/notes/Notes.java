package circulo.circulo_view.notes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import circulo.circulo_controller.NoteController;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_controller.TagController;
import circulo.circulo_model.Note;
import circulo.circulo_model.Tag;
import circulo.circulo_view.framework.common.CirculoException;
import circulo.circulo_view.framework.common.CirculoForm;
import circulo.circulo_view.framework.common.CirculoModalWindow;
import circulo.circulo_view.framework.common.CirculoPage;

import com.visural.wicket.component.nicedit.RichTextEditor;
import com.visural.wicket.component.nicedit.RichTextEditorFormBehavior;
import com.visural.wicket.component.submitters.IndicateRefreshAjaxSubmitLink;

public class Notes extends CirculoPage {

	private static final long serialVersionUID = 1L;

	public Notes() {
		add(new NotesMainForm("NotesMainForm", this));
	}

	public static final class NotesMainForm extends CirculoForm<Void> {
		private static final long serialVersionUID = 1L;
		private Tag selectedTag;
		private Note selectedNote;

		private IModel<List<? extends Note>> notesModel;
		private IModel<List<? extends Tag>> tagsModel;

		private final CirculoModalWindow tagModelWindow;
		private final CirculoModalWindow noteModelWindow;
		private final DropDownChoice<Tag> tagDDC;
		private final DropDownChoice<Note> noteDDC;
		private final AjaxLink<Void> newTagButton;
		private final AjaxLink<Void> editTagButton;
		private final AjaxLink<Void> deleteTagButton;
		private final AjaxLink<Void> newNoteButton;
		private final AjaxLink<Void> editNoteButton;
		private final AjaxLink<Void> deleteNoteButton;
		private final RichTextEditor<String> noteContent;

		final IndicateRefreshAjaxSubmitLink saveNote;

		public NotesMainForm(String id, CirculoPage parent) {
			super(id, parent);
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);

			tagDDC = getTagDDC(getTags());
			noteDDC = getNoteDDC(getNotes(false));

			newTagButton = getNewTagButton();
			editTagButton = getEditTagButton();
			deleteTagButton = getDeleteTagButton();
			tagModelWindow = getTagModelWindow();

			newNoteButton = getNewNoteButton();
			editNoteButton = getEditNoteButton();
			deleteNoteButton = getDeleteNoteButton();
			noteModelWindow = getNoteModelWindow();

			saveNote = getSaveNoteButton();
			noteContent = getNoteContentEditor();

			add(feedback);
			add(tagDDC);
			add(tagModelWindow.getModalWindow());
			add(noteModelWindow.getModalWindow());
			add(newTagButton);
			add(editTagButton);
			add(deleteTagButton);
			add(newNoteButton);
			add(editNoteButton);
			add(deleteNoteButton);
			add(noteDDC);
			add(new RichTextEditorFormBehavior());
			add(noteContent.setOutputMarkupId(true));
			add(saveNote);
		}

		private List<Note> getNotes(boolean refresh) {
			List<Note> notes = new ArrayList<Note>();
			if (selectedTag != null) {
				if (refresh) {
					List<Tag> tags = getTags();
					for (Tag selected : tags) {
						if (selectedTag.getId() == selected.getId()) {
							selectedTag(selected);
							break;
						}
					}
				}
				notes = Arrays.asList(selectedTag.getNotes().toArray(
						new Note[] {}));
			}
			return notes;
		}

		private List<Tag> getTags() {
			List<Tag> tags = new ArrayList<Tag>();
			try {
				tags = getController().getTagController().findAll();
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}
			return tags;
		}

		private AjaxLink<Void> getDeleteTagButton() {
			AjaxLink<Void> link = new AjaxLink<Void>("btnDeleteTag") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						TagController controller = getController()
								.getTagController();
						Tag tagObject = tagDDC.getModel().getObject();
						tagObject = controller.findByPrimaryKey(tagObject
								.getId());
						controller.remove(tagObject);
						tagDDC.setChoices(controller.findAll());
						target.add(tagDDC);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private AjaxLink<Void> getDeleteNoteButton() {
			AjaxLink<Void> link = new AjaxLink<Void>("btnDeleteNote") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						NoteController controller = getController()
								.getNoteController();
						Note tagObject = noteDDC.getModel().getObject();
						tagObject = controller.findByPrimaryKey(tagObject
								.getId());
						controller.remove(tagObject);
						noteDDC.setChoices(controller.findAll());
						target.add(noteDDC);
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private AjaxLink<Void> getNewTagButton() {

			AjaxLink<Void> link = new AjaxLink<Void>("btnNewTag") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						Page page = new circulo.circulo_view.tag.Tag(
								tagModelWindow.getModalWindow());
						tagModelWindow.show(page, target);
					} catch (CirculoException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private AjaxLink<Void> getNewNoteButton() {

			AjaxLink<Void> link = new AjaxLink<Void>("btnNewNote") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						Page page = new circulo.circulo_view.note.Note(
								selectedTag, noteModelWindow.getModalWindow());
						noteModelWindow.show(page, target);
					} catch (CirculoException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private AjaxLink<Void> getEditTagButton() {

			AjaxLink<Void> link = new AjaxLink<Void>("btnEditTag") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						Page page = new circulo.circulo_view.tag.Tag(

						selectedTag, tagModelWindow.getModalWindow());
						tagModelWindow.show(page, target);
					} catch (CirculoException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private AjaxLink<Void> getEditNoteButton() {

			AjaxLink<Void> link = new AjaxLink<Void>("btnEditNote") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					try {
						Page page = new circulo.circulo_view.note.Note(
								selectedTag, selectedNote,
								noteModelWindow.getModalWindow());
						noteModelWindow.show(page, target);
					} catch (CirculoException e) {
						e.printStackTrace();
					}
				}
			};
			return link;
		}

		private CirculoModalWindow getTagModelWindow() {
			final ModalWindow modalWindow = new ModalWindow("tagForm");
			return new CirculoModalWindow(modalWindow, "Tag Form") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClose(AjaxRequestTarget target) {
					try {
						TagController tagController = new TagController();
						tagDDC.setChoices(tagController.findAll());
						target.add(tagDDC);
					} catch (Exception e) {
						Logger.getLogger(this.getClass()).error(
								"error creating user");
					}
				}
			};
		}

		private CirculoModalWindow getNoteModelWindow() {
			final ModalWindow modalWindow = new ModalWindow("noteForm");
			return new CirculoModalWindow(modalWindow, "Note Form") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClose(AjaxRequestTarget target) {
					try {
						noteDDC.setChoices(getNotes(true));
						target.add(noteDDC);
					} catch (Exception e) {
						Logger.getLogger(this.getClass()).error(
								"error creating note");
					}
				}
			};
		}

		private IndicateRefreshAjaxSubmitLink getSaveNoteButton() {

			IndicateRefreshAjaxSubmitLink link = new IndicateRefreshAjaxSubmitLink(
					"saveNote") {
				private static final long serialVersionUID = 1L;

				@Override
				protected Collection<? extends Component> getIndicateRefreshContainers() {
					return null;
				}

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					try {
						NoteController controller = getController()
								.getNoteController();
						Note note = controller.findByPrimaryKey(selectedNote
								.getId());
						String content = noteContent.getModelObject();
						selectedNote.setContent(content);
						note.setContent(content);
						controller.update(note);
					} catch (ServiceException e) {
						e.printStackTrace();
					}

				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
				}
			};
			return link;
		}

		public void selectedTag(Tag tag) {
			selectedTag = tag;
		}

		public void selectedNote(Note note) {
			selectedNote = note;
		}

		private DropDownChoice<Tag> getTagDDC(final List<Tag> tags) {
			tagsModel = new AbstractReadOnlyModel<List<? extends Tag>>() {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Tag> getObject() {
					return tags;
				}

			};
			DropDownChoice<Tag> tagDDC = new DropDownChoice<Tag>("tag",
					new PropertyModel<Tag>(this, "selectedTag"), tagsModel);
			tagDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
				private static final long serialVersionUID = 1L;

				protected void onUpdate(AjaxRequestTarget target) {
					noteDDC.setChoices(getNotes(true));
					target.add(noteDDC);
				}
			});
			return tagDDC;
		}

		private DropDownChoice<Note> getNoteDDC(final List<Note> notes) {
			notesModel = new AbstractReadOnlyModel<List<? extends Note>>() {
				private static final long serialVersionUID = 1L;

				@Override
				public List<Note> getObject() {
					return getNotes(true);
				}

			};
			DropDownChoice<Note> noteDDC = new DropDownChoice<Note>("note",
					new PropertyModel<Note>(this, "selectedNote"), notesModel);
			noteDDC.setOutputMarkupId(true);
			noteDDC.add(new AjaxFormComponentUpdatingBehavior("onchange") {
				private static final long serialVersionUID = 1L;

				protected void onUpdate(AjaxRequestTarget target) {
					noteContent.setDefaultModelObject(selectedNote.getContent());
					target.add(noteContent, noteContent.getMarkupId());
					target.focusComponent(noteContent);
					target.appendJavaScript("document.forms[0].submit();");
				}
			});
			return noteDDC;
		}

		private RichTextEditor<String> getNoteContentEditor() {
			RichTextEditor<String> retval = new RichTextEditor<String>(
					"noteContent", Model.of(""));
			retval.setMarkupId("noteContent");
			return retval;
		}
	}
}