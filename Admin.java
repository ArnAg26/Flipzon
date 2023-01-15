import java.util.InputMismatchException;
import java.util.*;

public class Admin{
    private static String username="Beff Jezo";
    private static String password="pwd";

    public static String getUsername(){
        return username;
    }
    public static String getPassword(){
        return password;
    }
    public static int verify(String username1,String pwd1,String username2,String pwd2){
        if(username1.equals(username2) && pwd1.equals(pwd2)){
            return 1;
        }
        return 0;
    }

    private static void AddCategory(int id,String name){
        Category c=new Category(id,name);
        Flipzon.addCategory(c);
        System.out.println("You must add a product to this category");
        addProduct(id);
    }

    private static Category getCategory(String prodid){
        int x=prodid.indexOf(".");
        String cat=prodid.substring(0,x);
        int catid=Integer.parseInt(cat);
        Category c=null;
        for(int i=0;i<Flipzon.getCategories().size();i++){
            if(catid==Flipzon.getCategories().get(i).getid()){
                c=Flipzon.getCategories().get(i);
            }
        }
        if(c==null) {
            System.out.println("Your category id is incorrect");
            return null;
        }
        return c;
    }
    private static void DeleteCategory(int id,String name){
        try{
        Flipzon.deleteCategory(id,name);}
        catch(InputMismatchException inp){
            System.out.println("invalid category id and name");
        }
    }
    private static void addProduct(int id){
        Category c=null;
        for(int i=0;i<Flipzon.getCategories().size();i++){
            if(Flipzon.getCategories().get(i).getid()==id){
                c=Flipzon.getCategories().get(i);
                break;
            }
        }
        if(c==null){
            System.out.println("The product id you entered is wrong");
        }
        System.out.println("Enter name of product");
        Scanner sc=new Scanner(System.in);
        String name=sc.nextLine();
        System.out.println("Enter the product id");
        sc=new Scanner(System.in);
        String prodid=sc.next();
        for(int i=0;i<c.getProductList().size();i++){
            if(c.getProductList().get(i).getid().equals(prodid)){
                System.out.println("This is product id is taken");
                return;
            }
        }
        System.out.println("Enter general details(space separated in a single line)");
        sc=new Scanner(System.in);
        String deets=sc.nextLine();
        System.out.println("Enter price of product");
        sc=new Scanner(System.in);
        int price=sc.nextInt();
        sc=new Scanner(System.in);
        System.out.println("Enter stock of product");
        int stock=sc.nextInt();
        Product p=new Product(name,prodid,deets,price,c,stock);
        c.AddProduct(p);
    }

    private static void AddStock(String prodid,int Stock){
        Category c=getCategory(prodid);
        if(c==null) {
            System.out.println("Your category id is incorrect");
            return;
        }
        Product p=null;
        for(int i=0;i<c.getProductList().size();i++){
            if(c.getProductList().get(i).getid().equals(prodid)){
                p=c.getProductList().get(i);
            }
        }
        if(p==null){
            System.out.println("No product of given id the given category");
            return;
        }
        p.AddStock(Stock);
    }
    private static void AddDiscount(String prodid){
        Category c=getCategory(prodid);
        if(c==null) {
            System.out.println("Your category id is incorrect");
            return;
        }
        Product p=null;
        for(int i=0;i<c.getProductList().size();i++){
            if(c.getProductList().get(i).getid().equals(prodid)){
                p=c.getProductList().get(i);
            }
        }
        if(p==null){
            System.out.println("No product of given id the given category");
            return;
        }
        System.out.println("Enter discount for elite,prime and normal users respectively");
        Scanner sc=new Scanner(System.in);
        int elite=sc.nextInt();
        sc=new Scanner(System.in);
        int prime=sc.nextInt();
        sc=new Scanner(System.in);
        int norm=sc.nextInt();
        p.setDiscount(norm,prime,elite);
    }
    private static void GiveawayDeal(String prodid1,String prodid2){
        Category c1=getCategory(prodid1);
        Category c2=getCategory(prodid2);
        if(c1==null || c2==null) {
            System.out.println("Your category ids are incorrect");
            return;
        }
        Product p1=null;
        for(int i=0;i<c1.getProductList().size();i++){
            if(c1.getProductList().get(i).getid().equals(prodid1)){
                p1=c1.getProductList().get(i);
            }
        }
        if(p1==null){
            System.out.println("No product of given id the given category"+c1.getname());
            return;
        }
        Product p2=null;
        for(int i=0;i<c2.getProductList().size();i++){
            if(c2.getProductList().get(i).getid().equals(prodid2)){
                p2=c2.getProductList().get(i);
            }
        }
        if(p2==null){
            System.out.println("No product of given id the given category");
            return;
        }
        System.out.println("Enter combined of the two products(must be less than the price of two products) for normal customers");
        Scanner sc=new Scanner(System.in);
        int p5=sc.nextInt();
        int x=p1.getPrice();
        int y=p2.getPrice();
        x=x-(p1.getDiscount().get(0)*x)/100;
        y=y-(p2.getDiscount().get(0)*x)/100;
        if(p5>=x+y){
            System.out.println("The price in the deal must be less than the combined price");
            return;
        }
        System.out.println("Enter combined of the two products(must be less than the price of two products) for prime customers");
        int p3=sc.nextInt();
        x=p1.getPrice();
        y=p2.getPrice();
        x=x-(p1.getDiscount().get(1)*x)/100;
        y=y-(p2.getDiscount().get(1)*y)/100;
        if(p3>=x+y){
            System.out.println("The price in the deal must be less than the combined price");
            return;
        }
        System.out.println("Enter combined of the two products(must be less than the price of two products) for elite customers");
        int p4=sc.nextInt();
        x=p1.getPrice();
        y=p2.getPrice();
        x=x-(p1.getDiscount().get(2)*x)/100;
        y=y-(p2.getDiscount().get(2)*y)/100;
        if(p4>=x+y){
            System.out.println("The price in the deal must be less than the combined price");
            return;
        }
        Deals d1=new Deals(p1,p2,p5,p3,p4);
        Flipzon.AddDeal(d1);
    }
    private static void deleteProduct(String prodid){
        Category c=getCategory(prodid);
        if(c==null) {
            //System.out.println("Your category id is incorrect");
            return;
        }
        Product p=null;
        for(int i=0;i<c.getProductList().size();i++){
            if(c.getProductList().get(i).getid().equals(prodid)){
                p=c.getProductList().get(i);
            }
        }
        if(p==null){
            System.out.println("No product of given id the given category");
            return;

        }
        if(c.getProductList().size()==1){
            System.out.println("You need to add another product first because every category must have at least one product");
            Admin.addProduct(c.getid());
        }
        c.DeleteProduct(p);
        c.getProductList().removeIf(t->t.getid().equals(prodid));
        Flipzon.getDeals().removeIf(t->t.getP2().getid().equals(prodid) || t.getP1().getid().equals(prodid));
    }
    public static void AdminMode(String[] args){

        System.out.println("Please choose any one of the following actions");
        System.out.println("1) Add category");
        System.out.println("2) Delete category");
        System.out.println("3) Add Product");
        System.out.println("4) Delete Product");
        System.out.println("5) Set discount on product");
        System.out.println("6) Add giveaway deal");
        System.out.println("7) Add stock for a product");
        System.out.println("8) Back");
        Scanner sc=new Scanner(System.in);
        int x=sc.nextInt();
        if(x==1){
            System.out.println("Enter the category id");
            sc=new Scanner(System.in);
            int id=sc.nextInt();
            for(int i=0;i<Flipzon.getCategories().size();i++){
                if(Flipzon.getCategories().get(i).getid()==id){
                    System.out.println("This id is already taken. Please allot another one");
                    Admin.AdminMode(null);
                    break;
                }
            }
            System.out.println("Enter name of category");
            sc=new Scanner(System.in);
            String name=sc.nextLine();
            AddCategory(id,name);
        }
        if(x==2){
            sc=new Scanner(System.in);
            System.out.println("Enter category id");
            int id=sc.nextInt();
            System.out.println("Enter category name");
            sc=new Scanner(System.in);
            String name=sc.nextLine();
            DeleteCategory(id,name);
        }
        if(x==3){
            System.out.println("Enter a category id");
            sc=new Scanner(System.in);
            int id=sc.nextInt();
            System.out.println("Add a Product:");
            addProduct(id);
        }
        if(x==4){
            sc=new Scanner(System.in);
            String st=sc.next();
            deleteProduct(st);
        }
        if(x==5){
            sc=new Scanner(System.in);
            System.out.println("Dear Admin, give the product id you want to add the discount for");
            String st=sc.next();
            AddDiscount(st);

        }
        if(x==6){
            System.out.println("Dear Admin give product ids of the products you want to combine and give giveaway deal");
            sc=new Scanner(System.in);
            String prodid1=sc.next();
            sc=new Scanner(System.in);
            String prodid2=sc.next();
            GiveawayDeal(prodid1,prodid2);
        }
        if(x==7){
            System.out.println("Enter product id of product you want to increase stock for");
            sc=new Scanner(System.in);
            String pid=sc.nextLine();
            sc=new Scanner(System.in);
            int y=sc.nextInt();
            AddStock(pid,y);
        }
        if(x==8){
            System.out.println("See you again "+Admin.username);
            Flipzon.main(null);
        }
        if(x!=8){
            Admin.AdminMode(null);
        }


    }

}
