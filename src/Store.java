import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Store {
    private ArrayList<String> goods;

    public Store(ArrayList<String> goods) {
        this.goods = goods;
    }

    public synchronized String getProduct(int index) {
        String product = goods.get(index);
        goods.remove(index);

        return product;
    }

    public ArrayList<String> getGoods() {
        return goods;
    }

    public int amount() {
        return goods.size();
    }

    public void renewGoods(ArrayList<String> goods) {
        this.goods.addAll(goods);
    }
}
