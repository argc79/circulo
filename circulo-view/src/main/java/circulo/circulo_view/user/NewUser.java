package circulo.circulo_view.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import circulo.circulo_controller.ControllerProvider;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_controller.UserController;
import circulo.circulo_model.Person;
import circulo.circulo_model.Role;
import circulo.circulo_view.HomePage;
import circulo.circulo_view.framework.common.CirculoPage;
import circulo.circulo_view.framework.common.MessagePanel;

public class NewUser extends CirculoPage {

	private static final long serialVersionUID = 1L;

	public NewUser(PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		// User needs to be retrieved from Spring???(or factory, see binaryit)
		add(new NewUserForm("NewUserForm", new CompoundPropertyModel<Person>(
				new Person())));
	}

	public static final class NewUserForm extends Form<Person> {
		private static final long serialVersionUID = 1L;
		private TextField<String> userName;
		private PasswordTextField password;
		private TextField<String> email;
		private DropDownChoice<Role> role;
		private Button btnSave;
		private Button btnCancel;
		@SpringBean
		private ControllerProvider controller;

		public NewUserForm(String id, final IModel<Person> model) {
			super(id, model);
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);
			List<Role> roles = null;
			try {
				// roles = ControllerProvider.createRoleController().findAll();
				roles = controller.getRoleController().findAll();
			} catch (ServiceException e1) {
				e1.printStackTrace();
			}

			userName = new TextField<String>("userName");
			userName.setOutputMarkupId(true);
			userName.setRequired(true);

			password = new PasswordTextField("password");
			password.setRequired(true);
			password.setRequired(true);

			email = new TextField<String>("email");
			email.setRequired(true);
			email.setRequired(true);

			role = new DropDownChoice<Role>("role", roles,
					new IChoiceRenderer<Role>() {
						private static final long serialVersionUID = 1L;

						public Object getDisplayValue(Role object) {
							return object.getName();
						}

						public String getIdValue(Role object, int index) {
							return String.valueOf(((Role) object).getId());
						}
					});

			btnSave = new Button("btnSave") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					UserController userController = new UserController();
					Person user = model.getObject();
					try {
						if (userController.create(user) > 0) {
							Logger.getLogger(this.getClass()).info(
									"user created!!!");
							MessagePanel.showMessage(this, String.format(
									"The user %s has been created",
									user.getUserName()), "Go to login",
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
			add(userName);
			add(password);
			add(email);
			add(role);
			add(btnSave);
			add(btnCancel);
		}
	}
}