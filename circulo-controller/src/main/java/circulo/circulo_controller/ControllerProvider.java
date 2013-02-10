package circulo.circulo_controller;

public class ControllerProvider implements Controller {
	private RoleController roleController;
	private TagController tagController;
	private NoteController noteController;
	private UserController userController;

	public void setRoleController(RoleController roleController) {
		this.roleController = roleController;
	}

	public void setTagController(TagController tagController) {
		this.tagController = tagController;
	}

	public void setNoteController(NoteController noteController) {
		this.noteController = noteController;
	}

	public void setUserController(UserController userController) {
		this.userController = userController;
	}

	public RoleController getRoleController() {
		return roleController;
	}

	public TagController getTagController() {
		return tagController;
	}

	public NoteController getNoteController() {
		return noteController;
	}

	public UserController getUserController() {
		return userController;
	}
}
