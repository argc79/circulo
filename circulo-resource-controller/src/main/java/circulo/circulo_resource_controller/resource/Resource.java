package circulo.circulo_resource_controller.resource;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
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

	protected CacheControl getCache(int units) {
		CacheControl retval = new CacheControl();
		retval.setMaxAge(units);
		return retval;
	}

	protected Response getResponseOk(Object entity, EntityTag tag,
			CacheControl cc) {
		ResponseBuilder builder = Response.ok(entity);
		builder.cacheControl(cc);
		builder.tag(tag);
		return builder.build();
	}

	public abstract Response findAll(@Context SecurityContext sec,
			@Context Request request);

	public abstract List<T> findByName(@PathParam("query") String query);

	public abstract T findById(@PathParam("id") String id);

	public abstract T create(@Context SecurityContext sec, T t);

	public abstract T update(@Context SecurityContext sec, T t);

	public abstract void remove(@PathParam("id") int id);
}
