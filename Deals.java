import java.util.*;

public class Deals implements CartItem{
    private Product p1;
    private Product p2;
    private int price_normal;
    private int price_prime;
    private int price_elite;
    private int dealid;

    public Deals(Product p1,Product p2,int price_n,int price_prime,int price_elite){
        this.p1=p1;
        this.p2=p2;
        this.price_normal=price_n;
        this.price_prime=price_prime;
        this.price_elite=price_elite;
        this.dealid=Flipzon.getDeals().size()+1;
    }
    @Override
    public void AddStock(int x){
        this.p1.AddStock(1);
        this.p2.AddStock(1);
    }

    public Product getP1(){
        return this.p1;
    }

    public Product getP2(){
        return this.p2;
    }

    @Override
    public void setStock(int x){
        p1.setStock(1);
        p2.setStock(1);
    }
    @Override
    public int getStock(){
        if(p1.getStock()>0 && p2.getStock()>0) {
            return 1;
        }
        return 0;
    }
    @Override
    public int calculateDiscount(NormalCustomer s){
        return 0;
    }
    public int getid(){
        return this.dealid;
    }

    @Override
    public int getPrice(NormalCustomer s){
        if(s.getStatus().equals("Normal")){
            return this.price_normal;
        }
        else if(s.getStatus().equals("Prime")){
            return this.price_prime;
        }
        else if(s.getStatus().equals("Elite")){
            return this.price_elite;
        }
        return -1;
    }
    @Override
    public String toString(NormalCustomer s){
        return "Name of Product 1: "+this.p1.getName()+"\nName of Product 2: "+this.p2.getName()+"\nPrice for you: "+this.getPrice(s)+"\nDeal id: "+this.dealid+"\n";
    }
    public String toString(){
        return "Name of Product 1: "+this.p1.getName()+"\nName of Product 2: "+this.p2.getName()+"\nPrice for normal Customer: "+this.price_normal+"\nPrice of Prime Customer: "+this.price_prime+"\nPrice for Elite Customer: "+this.price_elite+"\nDeal id: "+this.dealid+"\n";


    }

}
