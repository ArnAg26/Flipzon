import java.util.ArrayList;

public class Product implements CartItem{
    private Category c;
    private String name;
    private String id;
    private String deets;
    private int price;
    private ArrayList<Integer> discount;

    private int Stock;
    public Category getc(){
        return c;
    }

    public ArrayList<Integer> getDiscount(){
        return discount;
    }

    public int getStock(){
        return this.Stock;
    }

    public void AddStock(int x){
        this.Stock+=x;
    }
    public void setStock(int x){
        this.Stock-=x;
    }
    public String getName(){
        return this.name;
    }
    public Product(String name,String id,String deets,int price,Category c,int Stock){
        this.name=name;
        this.deets=deets;
        this.id=id;
        this.price=price;
        this.c=c;
        discount=new ArrayList<>();
        this.Stock=Stock;
        this.setDiscount(0,5,10);
    }
    @Override
    public int getPrice(NormalCustomer s){
        return this.price;
    }

    public int getPrice(){
        return this.price;
    }
    public String getid(){
        return this.id;
    }
    public void setDiscount(int normal,int prime,int elite){
        this.discount=new ArrayList<>();
        this.discount.add(normal);
        this.discount.add(prime);
        this.discount.add(elite);
    }

    public String toString(NormalCustomer s){
        return "Name: "+this.name+"\nId: "+this.id+"\nDetails "+this.deets+"\nPrice "+this.price+"\nDiscount: "+this.calculateDiscount(s)+"\nStock: "+this.Stock+"\n";
    }
    public String toString(){
        return "Name: "+this.name+"\nId "+this.id+"\nDetails "+this.deets+"\nPrice "+this.price+"\nStock: "+this.Stock+"\n";
    }
    public int calculateDiscount(NormalCustomer s){
        if(s.getStatus().equals("Normal")){
            return this.discount.get(0);
        }
        else if(s.getStatus().equals("Prime")){
            return this.discount.get(1);
        }
        else if(s.getStatus().equals("Elite")){
            return this.discount.get(2);

        }
        else{
            return 0;
        }
    }
}
