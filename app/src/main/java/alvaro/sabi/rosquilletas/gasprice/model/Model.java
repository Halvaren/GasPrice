package alvaro.sabi.rosquilletas.gasprice.model;

import android.content.Context;
import android.content.res.Resources;

public class Model {

    private static Model instance;
    private final Resources resources;

    private GasQueries gasQueries;

    private Model(Context context){
        gasQueries = new GasQueries(context);
        resources = context.getResources();
    }

    public static Model getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Model(context);
        }
        return instance;
    }
}
