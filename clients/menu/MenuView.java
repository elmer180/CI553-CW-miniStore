package clients.menu;

import clients.Main;
import clients.Picture;
import middle.LocalMiddleFactory;
import middle.MiddleFactory;
import middle.StockReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Implements the Menu view.
 */

public class MenuView
{
    class Name                              // Names of buttons
    {
        public static final String BACKDOOR  = "Backdoor";
        public static final String CLIENT  = "Client";
        public static final String CASHIER  = "Cashier";
        public static final String PACKING  = "Packing";
    }

    private static final int H = 300;       // Height of window pixels
    private static final int W = 400;       // Width  of window pixels

    private final JLabel      pageTitle  = new JLabel();
    private final JLabel      theAction  = new JLabel();


    private final JButton     theBtDoor = new JButton( Name.BACKDOOR );
    private final JButton     theBtClient = new JButton( Name.CLIENT );
    private final JButton     theBtCashier = new JButton( Name.CASHIER );
    private final JButton     theBtPacking = new JButton( Name.PACKING );


    private StockReader theStock   = null;
    private MenuController cont= null;

    /**
     * Construct the view
     * @param rpc   Window in which to construct
     * @param mf    Factor to deliver order and stock objects
     * @param x     x-cordinate of position of window on screen 
     * @param y     y-cordinate of position of window on screen  
     */

    public MenuView( RootPaneContainer rpc, MiddleFactory mf, int x, int y )
    {
        try                                             //
        {
            theStock  = mf.makeStockReader();             // Database Access
        } catch ( Exception e )
        {
            System.out.println("Exception: " + e.getMessage() );
        }
        Container cp         = rpc.getContentPane();    // Content Pane
        Container rootWindow = (Container) rpc;         // Root Window
        cp.setLayout(null);                             // No layout manager
        rootWindow.setSize( W, H );                     // Size of Window
        rootWindow.setLocation( x, y );

        Font f = new Font("Monospaced",Font.PLAIN,24);  // Font f is

        pageTitle.setBounds( 120, 0 , 270, 20 );
        pageTitle.setText( "Ministore" );
        pageTitle.setFont(f);
        cp.add( pageTitle );

        theBtDoor.setBounds( 130, 25+60*0, 100, 40 );    // Backdoor button
       theBtDoor.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
               Main.startBackDoorGUI_MVC(mlf);
           }
       });
        cp.add( theBtDoor );                           //  Add to canvas

        theBtClient.setBounds( 130, 25+60*1, 100, 40 );    // Client button
        theBtClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
                Main.startCustomerGUI_MVC(mlf);
            }
        });
        cp.add( theBtClient );                           //  Add to canvas

        theBtCashier.setBounds( 130, 25+60*2, 100, 40 );    // Cashier button
        theBtCashier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
                Main.startCashierGUI_MVC(mlf);
            }
        });
        cp.add( theBtCashier );                           //  Add to canvas

        theBtPacking.setBounds( 130, 25+60*3, 100, 40 );    // Packing button
        theBtPacking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiddleFactory mlf = new LocalMiddleFactory();  // Direct access
                Main.startPackingGUI_MVC(mlf);
            }
        });
        cp.add( theBtPacking );                           //  Add to canvas

        theAction.setBounds( 110, 25 , 270, 20 );       // Message area
        theAction.setText( " " );                       // blank
        cp.add( theAction );                            //  Add to canvas


    }

    /**
     * The controller object, used so that an interaction can be passed to the controller
     * @param c   The controller
     */

    public void setController( MenuController c )
    {
        cont = c;
    }

    /**
     * Update the view
     * @param modelC   The observed model
     * @param arg      Specific args 
     */

}
