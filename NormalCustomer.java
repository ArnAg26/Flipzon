import java.util.Scanner;
import java.util.*;

public class NormalCustomer {
    private String name;
    private String pwd;
    private String status;
    private int wallet;


    private ArrayList<CartItem> Items;

    public String getName(){
        return this.name;
    }
    public String getPwd(){
        return this.pwd;
    }
    public int getWallet(){
        return this.wallet;
    }
    public ArrayList<CartItem> getCart(){
        return this.Items;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String st){
        this.status=st;
    }
    public NormalCustomer(String name, String password, int wallet, ArrayList<CartItem> Items){
        this.name=name;
        this.pwd=password;
        this.Items=Items;
        this.wallet=wallet;
    }

    public NormalCustomer(String name, String pwd) {
        super();
        this.name=name;

        this.pwd=pwd;
        this.status="Normal";
        Items=new ArrayList<>();
        this.wallet=1000;

    }

    public static void Signup(){
        Scanner sc=new Scanner(System.in);
        String name=sc.nextLine();
        sc=new Scanner(System.in);
        String pwd=sc.nextLine();
        NormalCustomer cust=new NormalCustomer(name,pwd);
        Flipzon.AddUser(cust);
        System.out.println("Thanks for registering");
    }
    private static int verify(String email1,String pwd1,String email2,String pwd2) {
        if(email1.equals(email2) && pwd1.equals(pwd2)){
            return 1;
        }
        else{
            return 0;
        }
    }

    public static void Login(String name,String pwd){
        int flag=0;
        for(int i=0;i<Flipzon.getUsers().size();i++){

            NormalCustomer s=Flipzon.getUsers().get(i);
            if(name.equals(s.name)){
                int r=verify(name,pwd,s.name,s.pwd);
                if(r==1){
                    flag=1;
                    NormalCustomer.main(s);
                    System.out.println("Welcome "+s.name+"\n");
                    break;

                }
                flag=0;
                break;
            }
        }
        if(flag==0){
            System.out.println("Invalid credentials entered, such a customer is not in our database");
        }
    }
    public void BrowseProducts(){
        for(int i=0;i<Flipzon.getCategories().size();i++){
            Category c=Flipzon.getCategories().get(i);
            for(int j=0;j<c.getProductList().size();j++){
                System.out.println(c.getProductList().get(j).toString(this));
            }
        }
    }

    public ArrayList<Integer> getCoupons(){
        System.out.println("Normal users don't get coupons whatsoever");
        return null;
    }
    public int WalletDeductions(int x){
        if((this.wallet-x)>=0){
            this.wallet-=x;
            //System.out.println(this.wallet);
            return 1;
        }
        else{
            System.out.println("Request can't be completed due to insufficient balance");
            return 0;
        }
    }

    public NormalCustomer UpgradeStatus(){
        System.out.println("Your current status is: "+this.status);
        Scanner sc=new Scanner(System.in);
        System.out.print("Choose new status ");
        String newstat=sc.next();
        if(this.status.equals("Prime") && newstat.equals("Elite")){
            if(this.WalletDeductions(100)==1){
                //this.status="Elite";
                EliteCustomer cust=new EliteCustomer(this);
                Flipzon.AddUser(cust);
                Flipzon.RemoveUser(this);
                return cust;
            }

        }
        if(newstat.equals("Elite")){
            if(this.WalletDeductions(300)==1){
                //this.status="Elite";

                EliteCustomer cust=new EliteCustomer(this);
                Flipzon.AddUser(cust);
                Flipzon.RemoveUser(this);
                return cust;
            }

        }
        if(newstat.equals("Prime")){
            //this.status="Prime";
            if(this.WalletDeductions(200)==1){
                PrimeCustomer cust=new PrimeCustomer(this);
                Flipzon.AddUser(cust);
                Flipzon.RemoveUser(this);
                return cust;
            }

        }
        return this;
    }

    private void AddtoCart(CartItem c){
        Items.add(c);
    }


    public static Category getCat(int catid){
        Category c=null;
        for(int i=0;i<Flipzon.getCategories().size();i++){
            c=Flipzon.getCategories().get(i);
            if(c.getid()==catid){
                return c;
            }
        }
        return null;
    }
    public void AddtoWallet(int x){
        this.wallet+=x;
    }
    public int checkAccountBalance(){
        return this.wallet;
    }

    public static Product getProduct(Category c,String prodid){
        for(int i=0;i<c.getProductList().size();i++){
            Product p=c.getProductList().get(i);
            if(p.getid().equals(prodid)){
                return p;
            }
        }
        return null;

    }

    public void ViewCart(){
        if(Items.size()==0){
            System.out.println("Your cart is empty");
            return;
        }
        System.out.println("This is your cart");
        for(int i=0;i<Items.size();i++){
            System.out.println(Items.get(i).toString(this));
        }

    }

    public void EmptyCart(){
        Items.clear();
    }
    public void CheckoutCart() {
        int tot = 0;
        int totDisc = 0;

        for (int i = 0; i < this.Items.size(); i++) {
            if (this.Items.get(i) instanceof Deals) {
                CartItem d = this.Items.get(i);
                if(d.getStock()==0){
                    System.out.println("Insufficient Stock of: "+d);
                    for(int j=0;j<i;j++){
                        this.getCart().get(j).AddStock(1);
                    }
                    return;
                }
                d.setStock(1);
                System.out.println(d.toString(this));
                tot += d.getPrice(this);
            } else {
                CartItem p = this.Items.get(i);
                if(p.getStock()<=0){
                    System.out.println("Insufficient stock of: "+p);
                    for(int j=0;j<i;j++){
                        this.getCart().get(j).AddStock(1);
                    }
                    return;
                }
                p.setStock(1);
                System.out.println(p.toString(this));
                int price = p.getPrice(this) ;
                int dis=Math.max(p.calculateDiscount(this), 0);
                totDisc += (dis * p.getPrice(this)) / 100;
                tot += price;
            }
        }
            int deliveryfee = 100 + (tot * 5) / 100;
            tot = tot + deliveryfee;
            tot-=totDisc;
            if (WalletDeductions(tot) == 1) {
                System.out.println("Thank you for shopping with us.Order has been placed successfully");
                System.out.println("Total delivery fee " + deliveryfee);
                System.out.println("Total discount " + totDisc);
                System.out.println("Total price " + tot);
                Random rand = new Random();
                int deltime = rand.nextInt(3) + 7;
                System.out.println("Delivery will be made in " + deltime + " days");
                this.EmptyCart();
            } else {
                for(int j=0;j<this.getCart().size();j++){
                    this.getCart().get(j).AddStock(1);
                }
                System.out.println("Insufficient funds, please either remove some items from cart or add funds");
            }


    }

    public static void main(NormalCustomer s){
        System.out.println("1) Browse products");
        System.out.println("2) Browse deals");
        System.out.println("3) Add a product to cart");
        System.out.println("4) Add products in deal to cart");
        System.out.println("5) View Coupons");
        System.out.println("6) Check account balance");
        System.out.println("7) View Cart");
        System.out.println("8) Empty Cart");
        System.out.println("9) Checkout Cart");
        System.out.println("10) Upgrade customer status");
        System.out.println("11) Add amount to wallet");
        System.out.println("12) Back");
        Scanner sc=new Scanner(System.in);
        int d=sc.nextInt();
        if(d==1){
            s.BrowseProducts();
        }
        if(d==2){
            System.out.println(Flipzon.getDeals());
        }
        if(d==3){
            sc=new Scanner(System.in);
            System.out.println("Enter the product id");
            String S=sc.next();
            int j=S.indexOf(".");
            int catid=Integer.parseInt(S.substring(0,j));
            System.out.println("Enter quantity of product");
            int quant=sc.nextInt();
            Category c=getCat(catid);
            if(c==null){
                System.out.println("The given catid is incorrect");
                main(s);
            }
            Product p=getProduct(c,S);
            if(p==null){
                System.out.println("The given product id is incorrect");
                main(s);
            }
            for(int i=0;i<quant;i++) {
                s.AddtoCart(p);
            }

        }
        if(d==4){
            sc=new Scanner(System.in);
            System.out.println("Enter deal id");
            d=sc.nextInt();
            Deals dl=null;
            for(int i=0;i<Flipzon.getDeals().size();i++){
                if(Flipzon.getDeals().get(i).getid()==d){
                    dl=Flipzon.getDeals().get(i);
                }
            }
            if(dl==null){
                System.out.println("The deal id entered by you is invalid");
                main(s);
            }

                s.AddtoCart(dl);


        }
        if(d==5){
            ArrayList<Integer> r=s.getCoupons();
            if(r!=null){
                System.out.println("These are the coupons");
                System.out.println(r);
            }

        }
        if(d==6){
            System.out.println("The current account balance is "+s.checkAccountBalance());
        }
        if(d==7){
            s.ViewCart();
        }
        if(d==8){
            s.EmptyCart();
            System.out.println("Your cart has been cleared");
        }
        if(d==9){
            s.CheckoutCart();
            System.out.println("Thanks for shopping with Flipzon");
        }
        if(d==10){
            s=s.UpgradeStatus();
        }
        if(d==11){
            System.out.println("How much do you want to add to wallet");
            sc=new Scanner(System.in);
            int ad=sc.nextInt();
            s.AddtoWallet(ad);
            System.out.println(s.wallet);
        }
        if(d==12){
            Flipzon.CustomerMode(null);
        }
        if(d!=12){
            NormalCustomer.main(s);
        }


    }



}
