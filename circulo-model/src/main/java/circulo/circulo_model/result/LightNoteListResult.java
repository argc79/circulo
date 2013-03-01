package circulo.circulo_model.result;

import java.io.Serializable;
import java.util.Date;

public class LightNoteListResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private final int id;
	private final String subject;
	private final Date createdOn;
	private final Date modifiedOn;

	public LightNoteListResult(int id, String subject, Date createdOn,
			Date modifiedOn) {
		this.id = id;
		this.subject = subject;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
	}

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

}
