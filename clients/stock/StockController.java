package clients.stock;

/**
 * The Stock Controller
 */

public class StockController
{
    private StockModel model = null;
    private StockView  view  = null;
    /**
     * Constructor
     * @param model The model 
     * @param view  The view from which the interaction came
     */
    public StockController( StockModel model, StockView view )
    {
        this.view  = view;
        this.model = model;
    }

    /**
     * Picked interaction from view
     */


}

