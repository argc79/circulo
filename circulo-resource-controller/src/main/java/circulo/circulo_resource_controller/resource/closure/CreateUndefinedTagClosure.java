package circulo.circulo_resource_controller.resource.closure;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.Closure;

import circulo.circulo_controller.ControllerProvider;
import circulo.circulo_controller.ServiceException;
import circulo.circulo_controller.TagController;
import circulo.circulo_model.Tag;

public class CreateUndefinedTagClosure implements Closure {
	private final Logger logger = Logger.getLogger("circulo");
	private final ControllerProvider controller;
	private final String userName;

	public CreateUndefinedTagClosure(ControllerProvider controller,
			String userName) {
		this.controller = controller;
		this.userName = userName;
	}

	public void execute(Object t) {
		final Tag tag = (Tag) t;
		try {
			if (tag.getId() == 0) {
				final TagController tagController = controller
						.getTagController();
				tagController.create(tag);
			}
			tag.setPerson(controller.getUserController().findByName(userName));
		} catch (ServiceException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

}
