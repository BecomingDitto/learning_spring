package hello;

public class StockQuote {

    private final long id;
    private final String price;

    public StockQuote(long id, String price){
        this.id = id;
        this.price = price;
    }

    public long getId(){
        return id;
    }

    public String getPrice() {
        return price;
    }
}
