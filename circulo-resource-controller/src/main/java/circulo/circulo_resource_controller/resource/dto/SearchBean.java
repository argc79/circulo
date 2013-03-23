package circulo.circulo_resource_controller.resource.dto;

import java.io.Serializable;

public class SearchBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public final static String NOTE = "note";
	public final static String TAG = "tag";

	private final String id;
	private final String name;
	private final String type;

	public SearchBean(String id, String name, String type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

}
