public interface CartItem {

    public void AddStock(int x);

    public void setStock(int x);
    public int getStock();

    public int getPrice(NormalCustomer s);

    public int calculateDiscount(NormalCustomer s);

    public String toString(NormalCustomer s);

}
