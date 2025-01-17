package clients;

import clients.backDoor.BackDoorController;
import clients.backDoor.BackDoorModel;
import clients.backDoor.BackDoorView;
import clients.cashier.CashierController;
import clients.cashier.CashierModel;
import clients.cashier.CashierView;
import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;
import clients.menu.MenuController;
import clients.menu.MenuModel;
import clients.menu.MenuView;
import clients.packing.PackingController;
import clients.packing.PackingModel;
import clients.packing.PackingView;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import javax.swing.*;
import java.awt.*;

/**
 * Starts all the clients (user interface)  as a single application.
 * Good for testing the system using a single application.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @author  Shine University of Brighton
 * @version year-2024
 * @author Ronnie elmer
 * @version year-2025
 */

public class Main
{
  public static void main (String args[])
  {
    new Main().begin();
  }

  /**
   * Starts the system
   */
  public void begin()
  {
    //DEBUG.set(true); /* Lots of debug info */
    MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
    startMenuGUI_MVC(mlf);

  }

  
  /**
  * start the Customer client, -search product
  * @param mlf A factory to create objects to access the stock list
  */
  public static void startCustomerGUI_MVC(MiddleFactory mlf)
  {
    JFrame  window = new JFrame();
    window.setTitle( "Customer Client MVC");
    Dimension pos = PosOnScrn.getPos();
    
    CustomerModel model      = new CustomerModel(mlf);
    CustomerView view        = new CustomerView( window, mlf, 200+pos.width, 350+pos.height );
    CustomerController cont  = new CustomerController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model, ---view is observer, model is Observable
    window.setVisible(true);         // start Screen
  }

  /**
   * start the cashier client - customer check stock, buy product
   * @param mlf A factory to create objects to access the stock list
   */
  public static void startCashierGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();
    window.setTitle( "Cashier Client MVC");

    Dimension pos = PosOnScrn.getPos();
    
    CashierModel model      = new CashierModel(mlf);
    CashierView view        = new CashierView( window, mlf, 200+pos.width, 350+pos.height );
    CashierController cont  = new CashierController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
    model.askForUpdate();            // Initial display
  }

  /**
   * start the Packing client - for warehouse staff to pack the bought order for customer, one order at a time
   * @param mlf A factory to create objects to access the stock list
   */
  
  public static void startPackingGUI_MVC(MiddleFactory mlf)
  {
    JFrame  window = new JFrame();

    window.setTitle( "Packing Client MVC");
    Dimension pos = PosOnScrn.getPos();
    
    PackingModel model      = new PackingModel(mlf);
    PackingView view        = new PackingView( window, mlf, 200+pos.width, 350+pos.height );
    PackingController cont  = new PackingController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }
  
  /**
   * start the BackDoor client - store staff to check and update stock
   * @param mlf A factory to create objects to access the stock list
   */
  public static void startBackDoorGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "BackDoor Client MVC");
    Dimension pos = PosOnScrn.getPos();
    
    BackDoorModel model      = new BackDoorModel(mlf);
    BackDoorView view        = new BackDoorView( window, mlf, 200+pos.width, 350+pos.height );
    BackDoorController cont  = new BackDoorController( model, view );
    view.setController( cont );

    model.addObserver( view );       // Add observer to the model
    window.setVisible(true);         // Make window visible
  }

  /**
   * start the BackDoor client - Used to open 0pen other clients
   * @param mlf A factory to create objects to access the stock list
   */
  public void startMenuGUI_MVC(MiddleFactory mlf )
  {
    JFrame  window = new JFrame();

    window.setTitle( "Menu Client MVC");
    window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    MenuModel model      = new MenuModel(mlf);
    MenuView view        = new MenuView( window, mlf, 800, 10 );
    MenuController cont  = new MenuController( model, view );
    view.setController( cont );

    window.setVisible(true);         // Make window visible
  }
  
}
