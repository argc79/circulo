package circulo.circulo_resource_controller.resource;

import java.util.List;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

public interface ResourceInterface<T> {
	public abstract Response findAll(@Context SecurityContext sec,
			@Context Request request);

	public abstract List<T> findByName(@PathParam("query") String query);

	public abstract T findById(@PathParam("id") String id);

	public abstract T create(@Context SecurityContext sec, T t);

	public abstract T update(@Context SecurityContext sec, T t);

	public abstract void remove(@PathParam("id") int id);
}
