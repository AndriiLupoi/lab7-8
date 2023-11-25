import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private ArrayList<String> cart;

    public Customer(String name) {
        this.name = name;
        cart = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getCart() {
        return cart;
    }

    public void addGoodToCart(String good) {
        synchronized (cart) {
            cart.add(good);
        }
    }

    public void buyGoods() {
        cart.clear();
    }
}
