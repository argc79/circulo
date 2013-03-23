package circulo.circulo_resource_controller.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;

import circulo.circulo_controller.ServiceException;
import circulo.circulo_resource_controller.resource.closure.SearchNoteClosure;
import circulo.circulo_resource_controller.resource.closure.SearchTagClosure;
import circulo.circulo_resource_controller.resource.dto.SearchBean;

@Path("search")
public class SearchResource extends Resource {
	private final Logger logger = Logger.getLogger("circulo");

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response find(@QueryParam("query") String query,
			@Context SecurityContext sec, @Context Request request) {
		final String userName = sec.getUserPrincipal().getName();
		try {
			final List<SearchBean> searchBeanList = new ArrayList<SearchBean>();
			List<?> notes = controller.getNoteController().findNotesList(
					userName, query);
			List<?> tags = controller.getTagController().findTagsList(userName,
					query);
			CollectionUtils.forAllDo(notes, new SearchNoteClosure(
					searchBeanList));
			CollectionUtils
					.forAllDo(tags, new SearchTagClosure(searchBeanList));

			EntityTag tag = new EntityTag(Integer.toString(searchBeanList
					.hashCode()));
			CacheControl cc = getCache(1000);
			ResponseBuilder builder = request.evaluatePreconditions(tag);
			if (builder != null) {
				builder.cacheControl(cc);
				return builder.build();
			}
			return getResponseOk(searchBeanList, tag, cc);
		} catch (ServiceException e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}
}
