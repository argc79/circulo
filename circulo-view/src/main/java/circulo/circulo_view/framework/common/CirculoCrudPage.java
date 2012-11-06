package circulo.circulo_view.framework.common;

public abstract class CirculoCrudPage extends CirculoSubPage implements Crud {

	private static final long serialVersionUID = 1L;

	private final CrudType crudType;

	public CrudType getCrudType() {
		return crudType;
	}

	protected CirculoCrudPage(CrudType crudType) {
		this.crudType = crudType;
	}
}
