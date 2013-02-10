package circulo.circulo_model;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 * Tag model class
 * 
 * @author Anibal
 * 
 */
@XmlRootElement
@Entity
@NamedQuery(name = "findAllTags", query = "select t from Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set<Note> notes = new HashSet<Note>();

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == null || !arg0.getClass().equals(getClass()))
			return false;

		return id == ((Tag) arg0).getId();
	}

	@ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
	@XmlTransient
	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@Column(unique = true, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return name;
	}
}
