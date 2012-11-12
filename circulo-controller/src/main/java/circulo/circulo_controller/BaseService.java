package circulo.circulo_controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public abstract class BaseService<T, X> {

	private static final Logger log = Logger.getAnonymousLogger();

	private static final ThreadLocal<EntityManager> em = new ThreadLocal<EntityManager>();

	protected BaseService() {
	}

	public static EntityManager getEntityManager() {
		if (em.get() == null) {
			EntityManagerFactory emf = Persistence
					.createEntityManagerFactory("circuloEntityManager");
			em.set(emf.createEntityManager());
		}
		return em.get();
	}

	protected final X persist(X object) throws ServiceException {
		try {
			begin();
			getEntityManager().persist(object);
			commit();
			return object;
		} catch (PersistenceException e) {
			rollback();
			throw new ServiceException(
					"The was an error while persisting the entity", e);
		}
	}

	protected final void delete(X object) {
		begin();
		getEntityManager().remove(object);
		commit();
	}

	protected final X merge(X object) {
		begin();
		getEntityManager().merge(object);
		commit();
		return object;
	}

	protected Query getQuery(String queryName) {
		return getEntityManager().createNamedQuery(queryName);
	}

	protected void begin() {
		getEntityManager().getTransaction().begin();
	}

	protected void commit() {
		getEntityManager().getTransaction().commit();
	}

	protected void rollback() {
		try {
			getEntityManager().getTransaction().rollback();
		} catch (PersistenceException e) {
			log.log(Level.WARNING, "Cannot rollback", e);
		}

		try {
			close();
		} catch (PersistenceException e) {
			log.log(Level.WARNING, "Cannot close", e);
		}
	}

	public static void close() {
		getEntityManager().close();
		em.set(null);
	}

	public abstract T create(X arg) throws ServiceException;

	public abstract T remove(X arg) throws ServiceException;

	public abstract T update(X arg) throws ServiceException;

	public abstract X findByPrimaryKey(T pk) throws ServiceException;

	public abstract List<X> findAll() throws ServiceException;

	public final void flush() {
		getEntityManager().flush();
	}

}