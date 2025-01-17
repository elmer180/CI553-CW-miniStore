package clients.stock;

import catalogue.Basket;
import catalogue.Product;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockException;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.Observable;
import java.util.Observer;

/**
 * Implements the Stock view.

 */

public class StockView implements Observer
{

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private final JLabel      pageTitle  = new JLabel();
    private final JLabel      theAction  = new JLabel();
    private final JTextArea   theOutput  = new JTextArea();
    private final JScrollPane theSP      = new JScrollPane();

    private String[] stockList={"0001","0002","0003","0004","0005","0006","0007"};
    private StockReadWriter theStock    = null;

    private StockController cont= null;

    /**
     * Construct the view
     * @param rpc   Window in which to construct
     * @param mf    Factor to deliver order and stock objects
     * @param x     x-cordinate of position of window on screen
     * @param y     y-cordinate of position of window on screen
     */
    public StockView(  RootPaneContainer rpc, MiddleFactory mf, int x, int y )
    {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Process stock
        } catch ( Exception e )
        {
            System.out.println("Exception: " + e.getMessage() );
        }
        Container cp         = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize( W, H );                     // Size of Window
        rootWindow.setLocation( x, y );

        Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

        pageTitle.setBounds( 110, 0 , 270, 20 );
        pageTitle.setText( "Stock Bought Order" );
        pageTitle.setFont(f);
        cp.add( pageTitle );


        theAction.setBounds( 65, 25 , 270, 20 );       // Message area
        theAction.setText( "Current Stock:" );                        // Blank
        cp.add( theAction );                            //  Add to canvas

        theSP.setBounds( 65, 55, 270, 205 );           // Scrolling pane
        theOutput.setText( "" );                        //  Blank
        theOutput.setFont( f );                         //  Uses font
        cp.add( theSP );                                //  Add to canvas
        theSP.getViewport().add( theOutput );           //  In TextArea
        rootWindow.setVisible( true );                  // Make visible
    }

    public void setController( StockController c )
    {
        cont = c;
    }

    /**
     * Update the view
     * @param modelC   The observed model
     * @param arg      Specific args
     */
    @Override
    public void update( Observable modelC, Object arg )
    {
        try {
            Product pr; //  Product
            String stockItems="";
            for (String i:stockList) {
                pr = theStock.getDetails(i);
                String stockItem =                             //   Display
                        String.format("%s : %7.2f (%2d) ",   //
                                pr.getDescription(),                  //    description
                                pr.getPrice(),                        //    price
                                pr.getQuantity());
                stockItems=stockItems+stockItem+"\n";
            }
            theOutput.setText(stockItems);
        } catch (StockException e) {
            throw new RuntimeException(e);
        }
    }

}

