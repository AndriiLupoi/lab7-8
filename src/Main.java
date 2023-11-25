import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static String[] availableProducts = new String[]{"egg", "bread", "bread", "egg", "meat", "cocaine", "tomato", "apple", "slave", "M4A3E2 american medium tank"};
    public static Store store;
    public static ArrayList<Customer> customers;

    public static ArrayList<Thread> customerThreads = new ArrayList<>();

    public static Semaphore semaphore;
    public static Runnable buyProductTask = () -> {
        try {
            while (true) {
                semaphore.acquire();

                Customer customer;
                String product;
                synchronized (store) {
                    customer = customers.get(r(customers.size()));
                    product = store.getProduct(r(store.getGoods().size()));

                    customer.addGoodToCart(product);
                }

                System.out.println(customer.getName() + " added " + product + " to cart");

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    };
    public static Runnable checkIfStoreIsEmpty = () -> {
        while (true) {
            if (store.getGoods().isEmpty()) {
                synchronized (store) {
                    try {
                        if (store.getGoods().isEmpty()) {
                            System.out.println("Store is empty, renewing products...");
                            store.renewGoods(delivery(r(availableProducts.length)));
                            Thread.sleep(3000);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    };


    public static void main(String[] args){
        store = new Store(delivery(10));
        customers = new ArrayList<Customer>(List.of(new Customer("Ivan"),
                                                    new Customer("John"),
                                                    new Customer("Victor"),
                                                    new Customer("Oleg"),
                                                    new Customer("Daryna"),
                                                    new Customer("Vitalina"),
                                                    new Customer("Vitaliy"),
                                                    new Customer("Ira"),
                                                    new Customer("Paul"),
                                                    new Customer("Jack"),
                                                    new Customer("Mike"),
                                                    new Customer("Andrew"),
                                                    new Customer("Tanya")
                                            ));

        Thread storeThread = new Thread(checkIfStoreIsEmpty);
        storeThread.start();

        semaphore = new Semaphore(2);
        for (Customer customer : customers) {
            Thread threadToAdd = new Thread(buyProductTask);
            customerThreads.add(threadToAdd);
            threadToAdd.start();
        }
    }

    public static int r(int max) {
        return (new Random()).nextInt(max);
    }

    public static ArrayList<String> delivery(int amount) {
        ArrayList<String> products = new ArrayList<>();
        for(int i = 0; i < amount; i++) {
            products.add(availableProducts[r(availableProducts.length)]);
        }
        return products;
    }
}