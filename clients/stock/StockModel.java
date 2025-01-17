package clients.stock;


import catalogue.Basket;
import debug.DEBUG;
import middle.MiddleFactory;
import middle.OrderException;
import middle.OrderProcessing;
import middle.StockReadWriter;

import java.util.Observable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Implements the Model of the warehouse packing client
 */
public class StockModel extends Observable
{

    private StockReadWriter theStock   = null;


    /*
     * Construct the model of the warehouse Stock client
     * @param mf The factory to create the connection objects
     */
    public StockModel(MiddleFactory mf)
    {
        try                                     // 
        {
            theStock = mf.makeStockReadWriter();  // Database access
        } catch ( Exception e )
        {
            DEBUG.error("CustomerModel.constructor\n%s", e.getMessage() );
        }

        // Start a background check to see when a new order can be packed
        new Thread( () -> upateStock() ).start();
    }





    private void upateStock()
    {
        while ( true )
        {
            try
            {
                setChanged(); notifyObservers();
                Thread.sleep(2000);                  // idle
            } catch ( Exception e )
            {
                DEBUG.error("%s\n%s",                // Eek!
                        "BackGroundCheck.run()\n%s",
                        e.getMessage() );
            }
        }
    }
}





