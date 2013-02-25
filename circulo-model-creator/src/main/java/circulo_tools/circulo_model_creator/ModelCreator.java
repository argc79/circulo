package circulo_tools.circulo_model_creator;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import circulo.circulo_model.Note;
import circulo.circulo_model.Person;
import circulo.circulo_model.Role;
import circulo.circulo_model.Tag;

/**
 * Hello world!
 * 
 */
public class ModelCreator {
	public static void main(String[] args) {
		SchemaExport se = new SchemaExport(
				new AnnotationConfiguration().configure());
		se.create(true, true);
		SessionFactory sf = new AnnotationConfiguration().configure()
				.buildSessionFactory();
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Role role = new Role();
		role.setName("admin");

		Person user = new Person();
		user.setUserName("anibal");
		user.setPassword("1234");
		user.setEmail("argc79@gmail.com");
		user.setRole(role);

		Tag tag = new Tag();
		tag.setName("java");

		Note note = new Note();
		note.setSubject("wicket");
		note.setCreatedOn(new Date());
		note.setModifiedOn(new Date());
		note.setContent("");
		note.setPerson(user);

		Set<Note> notes = new HashSet<Note>();
		Set<Tag> tags = new HashSet<Tag>();
		tags.add(tag);
		notes.add(note);
		note.setTags(tags);
		tag.setNotes(notes);
		// session.save(role);
		session.save(user);
		session.save(note);
		tx.commit();

	}
}
