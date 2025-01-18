package clients.cashier;

import catalogue.Basket;
import catalogue.Product;
import com.sun.jdi.IntegerValue;
import debug.DEBUG;
import middle.*;

import java.util.Observable;

/**
 * Implements the Model of the cashier client
 */
public class CashierModel extends Observable {


    private Product theProduct = null;            // Current product
    private Basket theBasket = null;            // Bought items

    private String pn = "";                      // Product being processed

    private StockReadWriter theStock = null;
    private OrderProcessing theOrder = null;

    /**
     * Construct the model of the Cashier
     *
     * @param mf The factory to create the connection objects
     */

    public CashierModel(MiddleFactory mf) {
        try                                           //
        {
            theStock = mf.makeStockReadWriter();        // Database access
            theOrder = mf.makeOrderProcessing();        // Process order
        } catch (Exception e) {
            DEBUG.error("CashierModel.constructor\n%s", e.getMessage());
        }

    }

    /**
     * Get the Basket of products
     *
     * @return basket
     */
    public Basket getBasket() {
        return theBasket;
    }

    /**
     * Check if the product is in Stock
     *
     * @param productNum The product number
     */
    public boolean doCheck(String productNum, String amount) {
        boolean stock = false;
        String theAction = "";

        pn = productNum.trim();                    // Product no.
        boolean valid = false;
        try {
            if(Integer.parseInt(amount)>0){
                 valid =true;
            }else { valid = false;}
        }catch (Exception e){
             valid= false;
        }

        try {

            if (theStock.exists(pn))              // Stock Exists?
            {
                if (valid) {                                     // T
                    Product pr = theStock.getDetails(pn);   //  Get details
                    if (pr.getQuantity() >= Integer.parseInt(amount))       //  In stock?
                    {                                       //  T
                        theAction =                           //   Display
                                String.format("%s : %7.2f (%2d) ", //
                                        pr.getDescription(),              //    description
                                        pr.getPrice(),                    //    price
                                        pr.getQuantity());               //    quantity
                        theProduct = pr;                      //   Remember prod.
                        theProduct.setQuantity(Integer.parseInt(amount));     //    & quantity

                        stock = true;

                    } else {                                //  F
                        theAction =                           //   Not in Stock
                                pr.getDescription() + " not enough in stock";
                    }
                } else {
                    theAction= "Invalid quantity";
                }
            } else {                                  // F Stock exists
                theAction =                             //  Unknown
                        "Invalid product number";       //  product no.
            }
        } catch (StockException e) {
            DEBUG.error("%s\n%s",
                    "CashierModel.doCheck", e.getMessage());
            theAction = e.getMessage();
        }
        setChanged();
        notifyObservers(theAction);
        return stock;
    }


    /**
     * Buy the product
     */
    public void doBuy(String productNo, String amount) {
        String theAction = "";

        try {
            if (!doCheck(productNo, amount))          // Not checked
            {                                         //  with customer
                theAction = "Please check its availablity";
            } else if (!(Integer.parseInt(amount) > 0)) {
                theAction = "Invalid value";
            } else {
                boolean stockBought =                   // Buy
                        theStock.buyStock(                    //  however
                                theProduct.getProductNum(),         //  may fail
                                theProduct.getQuantity());         //
                if (stockBought)                      // Stock bought
                {                                       // T
                    makeBasketIfReq();                    //  new Basket ?
                    theBasket.add(theProduct);          //  Add to bought
                    theAction = "Purchased " +            //    details
                            theProduct.getDescription();  //
                } else {                                // F
                    theAction = "!!! Not in stock";       //  Now no stock
                }
            }
        } catch (StockException e) {
            DEBUG.error("%s\n%s",
                    "CashierModel.doBuy", e.getMessage());
            theAction = e.getMessage();
        }

        setChanged();
        notifyObservers(theAction);
    }

    /**
     * Customer pays for the contents of the basket
     */
    public void doBought() {
        String theAction = "";
        int amount = 1;                       //  & quantity
        try {
            if (theBasket != null &&
                    theBasket.size() >= 1)            // items > 1
            {                                       // T
                theOrder.newOrder(theBasket);       //  Process order
                theBasket = null;                     //  reset
            }                                       //
            theAction = "Start New Order";            // New order

            theBasket = null;
        } catch (OrderException e) {
            DEBUG.error("%s\n%s",
                    "CashierModel.doCancel", e.getMessage());
            theAction = e.getMessage();
        }
        theBasket = null;
        setChanged();
        notifyObservers(theAction); // Notify
    }

    /**
     * ask for update of view callled at start of day
     * or after system reset
     */
    public void askForUpdate() {
        setChanged();
        notifyObservers("Welcome");
    }

    /**
     * make a Basket when required
     */
    private void makeBasketIfReq() {
        if (theBasket == null) {
            try {
                int uon = theOrder.uniqueNumber();     // Unique order num.
                theBasket = makeBasket();                //  basket list
                theBasket.setOrderNum(uon);            // Add an order number
            } catch (OrderException e) {
                DEBUG.error("Comms failure\n" +
                        "CashierModel.makeBasket()\n%s", e.getMessage());
            }
        }
    }

    /**
     * return an instance of a new Basket
     *
     * @return an instance of a new Basket
     */
    protected Basket makeBasket() {
        return new Basket();
    }
}
  
