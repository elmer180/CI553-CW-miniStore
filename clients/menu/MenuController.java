package clients.menu;


/**
 * The Menu Controller
 */

public class MenuController
{

    private MenuModel model = null;
    private MenuView  view  = null;

    /**
     * Constructor
     * @param model The model 
     * @param view  The view from which the interaction came
     */
    public MenuController(MenuModel model, MenuView view )
    {
        this.view  = view;
        this.model = model;
    }

    public void openBackdoor(){

    }

}

