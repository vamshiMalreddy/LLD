/* 
 * Stock exchange - list of stocks
 * multiple stocks of same company
 * price of stock, price at which ordered is placed.
 * Place, buy/sell, execute trades
 * Order history
 * Cancel order that is not yet executed
 * Trade history
 * Wallet configured - amount and number of stocks
 */

// Notifying users, validating trades based on users, partial orders.


User
Stock
Trade
Order
Wallet

User{
    name,
    email,
    Wallet wallet,
}

Stock{
    name,
    currentPrice,
}


// Do you think we need quantity here? I'm having one Order for one stock unit, this will help 
// in simplyfing matching partial orders
public abstract class Order{
    User placedBy,
    Stock stock,
    Float price,
    DateTime createdAt,
    OrderStatusEnum status
}

public class BuyOrder extends Order{

}

public class SellOrder extends Order{

}

public enum OrderStatusEnum{
    PLACED,
    EXCECUTED,
    CANCELLED;
}

public Trade{
    Order buyOrder,
    Order sellOrder,
    User buyer,
    User seller,
    Wallet buyerWallet,
    Wallet sellerWallet,
    DateTime createdAt,
}

Wallet{
    User user,
    Float balance,
    Map<Stock,int> holdings,

    Map<Stock,int> getHoldings();
}

OrderBook{
    Map<Stock, List<BuyOrder>> buyOrders;
    Map<Stock, List<SellOrder>> sellOrders;
}

StockExchangeApplication{
    OrderBook orderBook;
    OrderMatchingAlgorithm orderMatchingAlgorithm;

    Order placeOrder(Stock stock, User user, int price, boolean isBuy){
        OrderService.placeOrder();

        OrderBook.add(order);
        // while loop?
        matchOrdersForTrade();
    }

    Trade matchOrdersForTrade(){
        trade = orderMatchingAlgorithm.matchOrders(buyOrders.get(stock),sellOrders.getStock());
        verifyTrade(trade);
        updateWallets(trade);
    }
}

WalletService{
    public void updateWallets(Trade trade);
}

OrderService{
    // Place buy order and place sell order
    BuyOrder placeBuyOrder(Stock stock, User user, int price)

    SellOrder placeSellOrder(Stock stock, User user, int price)

    List<Order> getOrderHistory(User user);

    boolean cancelOrder(Order order);
}

OrderVerificationService{
    boolean verifyOrder(Order order, Wallet wallet)
}

public class TradeVerificationService{
    public boolean verifyTrade(Trade trade);
}

public interface OrderMatchingAlgorithm{
    Optional<Trade> matchOrders(List<BuyOrder> buyOrders, List<SellOrder> sellOrders);
}

TradeHistoryService{
    List<Trade> getTradeHistory(User user);
}

