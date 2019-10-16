package tw.lab2;

public class Shopper implements Runnable {
    private Shop shop;

    public Shopper(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        try {
            shop.doShopping();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
