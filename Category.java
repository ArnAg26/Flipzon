import java.util.ArrayList;

public class Category {
    private int id;
    private String name;
    private ArrayList<Product> Products;
    public Category(int id,String name){
        this.id=id;
        this.name=name;
        Products=new ArrayList<>();
    }

    public int getid(){
        return id;
    }

    public void AddProduct(Product p){
        this.Products.add(p);
    }

    public void DeleteProduct(Product p){
        this.Products.remove(p);
    }

    public ArrayList<Product> getProductList(){
        return Products;
    }
    public String getname(){
        return name;
    }
}
