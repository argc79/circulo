package circulo.circulo_view;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import circulo.circulo_controller.UserController;
import circulo.circulo_model.Person;
import circulo.circulo_view.framework.common.MessagePanel;
import circulo.circulo_view.notes.Notes;
import circulo.circulo_view.user.NewUser;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	public HomePage(final PageParameters parameters) {
		add(new Label("version", getApplication().getFrameworkSettings()
				.getVersion()));
		// User needs to be retrieved from Spring???(or factory, see binaryit)
		add(new UserLoginForm("UserLoginForm",
				new CompoundPropertyModel<Person>(new Person())));
	}

	public static final class UserLoginForm extends Form<Person> {
		private TextField<String> userName;
		private PasswordTextField password;
		private Button btnLogin;
		private Button btnNewUser;

		public UserLoginForm(String id, final IModel<Person> model) {
			super(id, model);
			final FeedbackPanel feedback = new FeedbackPanel("feedback");
			feedback.setOutputMarkupId(true);

			userName = new TextField<String>("userName");
			userName.setOutputMarkupId(true);
			userName.setRequired(true);

			password = new PasswordTextField("password");
			password.setRequired(true);
			password.setRequired(true);
			btnLogin = new Button("btnLogin") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					UserController userController = new UserController();
					Person user = model.getObject();
					if (userController.login(user)) {
						Logger.getLogger(this.getClass()).info("login ok!!!");
						// setResponsePage(PageOk.class);
						setResponsePage(Notes.class);
					} else {
						Logger.getLogger(this.getClass()).error("login error");
						MessagePanel.showMessage(this, String.format(
								"The user %s does not exist",
								user.getUserName()), "Back to login",
								HomePage.class.getName());
					}
				}
			};

			btnNewUser = new Button("btnNewUser") {
				private static final long serialVersionUID = 1L;

				@Override
				public void onSubmit() {
					setResponsePage(NewUser.class);
				}
			};
			btnNewUser.setDefaultFormProcessing(false);

			add(feedback);
			add(userName);
			add(password);
			add(btnLogin);
			add(btnNewUser);
		}

		private static final long serialVersionUID = 1L;

	}
}
