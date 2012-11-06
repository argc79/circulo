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

@Entity
@NamedQuery(name = "findAllTags", query = "select t from Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Set<Note> notes = new HashSet<Note>();

	@ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
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
