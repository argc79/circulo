package circulo.circulo_resource_controller.resource;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import circulo.circulo_controller.ControllerProvider;

abstract class Resource<T> {
	ControllerProvider controller;

	public void setController(ControllerProvider controller) {
		this.controller = controller;
	}

	// public Resource() {
	// ApplicationContext ctx = new ClassPathXmlApplicationContext(
	// "applicationContext.xml");
	// System.out.println("El controller tiene este valor=" + ctx);
	// controller = (ControllerProvider) ctx.getBean("Controller");
	// }

	public abstract List<T> findAll(@Context SecurityContext sec);

	public abstract List<T> findByName(@PathParam("query") String query);

	public abstract T findById(@PathParam("id") String id);

	public abstract T create(@Context SecurityContext sec, T t);

	public abstract T update(T t);

	public abstract void remove(@PathParam("id") int id);
}
