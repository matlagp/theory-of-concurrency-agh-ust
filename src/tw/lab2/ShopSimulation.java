package tw.lab2;

public class ShopSimulation {
    private static final int numberOfShoppers = 10;
    private static final int numberOfCarts = 4;

    public static void main(String[] args) {
        Shop sharedShop = new Shop(numberOfCarts);
        Shopper[] shoppers = new Shopper[numberOfShoppers];
        Thread[] threads = new Thread[numberOfShoppers];
        for (int i = 0; i < numberOfShoppers; i++) {
            shoppers[i] = new Shopper(sharedShop);
            threads[i] = new Thread(shoppers[i]);
        }

        for (Thread t: threads) {
            t.start();
            try {
                Thread.sleep((long) (Math.random() * 400));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Thread t: threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
