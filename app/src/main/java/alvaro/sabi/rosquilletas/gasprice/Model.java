package alvaro.sabi.rosquilletas.gasprice;

public class Model {

    private Presenter presenter;
    private GasQueries gasQueries;

    public Model(Presenter param){
        presenter = param;
        gasQueries = new GasQueries(presenter.context);
    }
}
