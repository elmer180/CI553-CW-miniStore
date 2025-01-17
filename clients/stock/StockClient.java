package clients.stock;


import middle.MiddleFactory;
import middle.Names;
import middle.RemoteMiddleFactory;

import javax.swing.*;

/**
 * The standalone warehouse Stock Client. warehouse staff to pack the bought order
 * @author  Mike Smith University of Brighton
 * @version 2.0
 * @author  Shine University of Brighton
 * @version year 2024
 */
public class StockClient
{
    public static void main (String args[])
    {
        String stockURL = args.length < 1     // URL of stock RW
                ? Names.STOCK_RW      //  default  location
                : args[0];            //  supplied location
        String orderURL = args.length < 2     // URL of order
                ? Names.ORDER         //  default  location
                : args[1];            //  supplied location

        RemoteMiddleFactory mrf = new RemoteMiddleFactory();
        mrf.setStockRWInfo( stockURL );
        mrf.setOrderInfo  ( orderURL );        //
        displayGUI(mrf);                       // Create GUI
    }

    public static void displayGUI(MiddleFactory mf)
    {
        JFrame  window = new JFrame();

        window.setTitle( "Stock Client (RMI MVC)");
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        StockModel      model = new StockModel(mf);
        StockView       view  = new StockView( window, mf, 0, 0 );
        StockController cont  = new StockController( model, view );
        view.setController( cont );

        model.addObserver( view );       // Add observer to the model
        window.setVisible(true);         // Display Screen
    }
}

