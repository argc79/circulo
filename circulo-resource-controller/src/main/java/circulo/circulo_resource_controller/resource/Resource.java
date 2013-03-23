package circulo.circulo_resource_controller.resource;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import circulo.circulo_controller.ControllerProvider;

abstract class Resource {
	ControllerProvider controller;

	public void setController(ControllerProvider controller) {
		this.controller = controller;
	}

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
}
