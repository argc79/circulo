package circulo.circulo_model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@NamedQuery(name = "findAllNotes", query = "select n from Note n")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String subject;
	private Set<Tag> tags = new HashSet<Tag>();
	private String content;
	private Date createdOn;
	private Date modifiedOn;

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null || !arg0.getClass().equals(getClass()))
			return false;

		return id == ((Note) arg0).getId();
	}

	@Column(nullable = false)
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(nullable = false)
	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@ManyToMany(cascade = CascadeType.ALL)
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return subject;
	}
}
