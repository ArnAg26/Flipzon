import java.util.*;

public class Flipzon {
    private static ArrayList<Deals> Deals=new ArrayList<>();
    private static ArrayList<NormalCustomer> Users=new ArrayList<>();
    private static ArrayList<Category> Categories=new ArrayList<>();
    public static void addCategory(Category c){
        Categories.add(c);
    }
    public static void deleteCategory(int id,String name){
        Flipzon.Categories.removeIf(t->t.getname().equals(name) && t.getid()==id);
        for(int i=0;i<Deals.size();i++){
            Deals.removeIf(t->t.getP1().getc().getname().equals(name) || t.getP2().getc().getname().equals(name));
        }
    }

    public static void AddDeal(Deals d){
        Deals.add(d);
    }

    public static ArrayList<Category> getCategories(){
        return Categories;

    }

    public static ArrayList<Deals> getDeals(){
        return Deals;
    }
    public static void AddUser(NormalCustomer s){
        Users.add(s);
    }
    public static void RemoveUser(NormalCustomer s){
        Users.remove(s);
    }

    public static ArrayList<NormalCustomer> getUsers(){
        return Users;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to Flipzon");
        System.out.println("1) Enter as admin");
        System.out.println("2) Explore the product catalog");
        System.out.println("3 Show available deals");
        System.out.println("4) Enter as customer");
        System.out.println("5) Exit the application");
        Scanner sc=new Scanner(System.in);
        int d=sc.nextInt();
        if(d==5){
            System.out.println("Thank you for using Flipzon");
            System.exit(1);
        }
        if(d==1){
            sc=new Scanner(System.in);
            System.out.println("Enter your username");
            String usr=sc.nextLine();
            System.out.println("Enter your password");
            sc=new Scanner(System.in);
            String pwd=sc.nextLine();
            if(Admin.verify(usr,pwd,Admin.getUsername(),Admin.getPassword())==1){
                System.out.println("Thank you for logging in");
                System.out.println("Welcome "+Admin.getUsername()+"!!!!");
                Admin.AdminMode(null);
            }
            else{
                System.out.println("The username and password you entered is incorrect. We can't let you through");
                main(null);
            }
        }
        if(d==2){
            for(int i=0;i<Flipzon.Categories.size();i++){
                Category c=Flipzon.Categories.get(i);
                for(int j=0;j<c.getProductList().size();j++){
                    Product p=c.getProductList().get(j);
                    System.out.println(p);
                    System.out.println();
                }
            }

        }
        if(d==3){
            if(Deals.size()==0){
                System.out.println("Dear user, there are no deals for now!!! Please check regualrly for such deals.");
            }
            else{
                for(int i=0;i<Deals.size();i++){
                    System.out.println(Deals.get(i));
                }
            }
        }
        if(d==4){
            Flipzon.CustomerMode(null);
        }
        main(null);
    }
    public static void CustomerMode(String[] args){
        System.out.println("1) Sign up");
        System.out.println("2) Log in");
        System.out.println("3) Back");
        Scanner sc=new Scanner(System.in);
        int d=sc.nextInt();
        if(d==3){
            Flipzon.main(null);
        }

        if(d==2){
            System.out.println("Enter username of customer");
            sc=new Scanner(System.in);
            String S=sc.nextLine();
            System.out.println("Enter passwrod");
            sc=new Scanner(System.in);
            String pwd=sc.nextLine();
            NormalCustomer.Login(S,pwd);
        }
        if(d==1){
            NormalCustomer.Signup();
        }
        if(d!=3){
            CustomerMode(null);
        }
    }
}