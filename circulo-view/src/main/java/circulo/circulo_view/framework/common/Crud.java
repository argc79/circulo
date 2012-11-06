package circulo.circulo_view.framework.common;

public interface Crud {
	enum CrudType {
		NEW, EDIT, VIEW
	}

	CrudType getCrudType();
}
