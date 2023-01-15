import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class EliteCustomer extends NormalCustomer {
    private ArrayList<Integer> Coupons;
    public EliteCustomer(NormalCustomer s) {
        super(s.getName(),s.getPwd(),s.getWallet(),s.getCart());
        this.setStatus("Elite");
        this.Coupons=new ArrayList<>();
    }
    @Override
    public ArrayList<Integer> getCoupons(){
        return this.Coupons;
    }
    public void AddCoupons(){
        Random rand=new Random();
        for(int i=0;i<3;i++){
            int discount=rand.nextInt(5)+10;
            this.Coupons.add(discount);
            System.out.println("You have won coupon of "+discount+"%");
        }
    }

    @Override
    public void CheckoutCart() {
        int tot = 0;
        int totDisc = 0;
        CartItem p = null;
        int maxCoupon=0;
        int usedCoupon=0;
        int IndexOfUsedCoupon=-1;
        if(Coupons.size()>0){
            maxCoupon=Collections.max(Coupons);
            IndexOfUsedCoupon=Coupons.indexOf(maxCoupon);
            usedCoupon=0;
        }

        for (int i = 0; i < this.getCart().size(); i++) {
            p = this.getCart().get(i);
            if (p instanceof Deals) {
                System.out.println(p.toString(this));
                if(p.getStock()==0){
                    System.out.println("Insufficient stock of "+p);
                    for(int j=0;j<i;j++){
                        this.getCart().get(j).AddStock(1);
                    }
                    return;
                }
                p.setStock(1);
                tot += p.getPrice(this);
            } else {
                System.out.println(p.toString(this));
                if(p.getStock()<=0){
                    System.out.println("Insufficient stock of "+p);
                    for(int j=0;j<i;j++){
                        this.getCart().get(j).AddStock(1);
                    }
                    return;

                }
                p.setStock(1);
                int price = p.getPrice(this);
                int dis = Math.max(p.calculateDiscount(this), 10);
                if(Coupons.size()>0 && dis<maxCoupon){
                    dis=maxCoupon;
                    usedCoupon=1;
                }
                totDisc += (dis * p.getPrice(this)) / 100;
                tot += price;
            }
        }
            int deliveryfee = 100;

            tot -= totDisc;
            tot = tot + deliveryfee;

            if (WalletDeductions(tot) == 1) {
                if (tot >= 5000) {
                    this.AddCoupons();
                }

                System.out.println("Thank you for shopping with us.Order has been placed successfully");
                System.out.println("Total delivery fee " + deliveryfee);
                System.out.println("Total discount " + totDisc);
                System.out.println("Total price " + tot);
                Random rand = new Random();
                int deltime = rand.nextInt(2);
                System.out.println("Delivery will be made in " + deltime + " days");
                int surprise = rand.nextInt(3);
                if (surprise == 2) {
                    System.out.println("Congratulations you get a surprise gift");
                    System.out.println("Your surprise gift is\n " + p);
                    p.setStock(1);
                }
                if(usedCoupon==1){
                    Coupons.remove(IndexOfUsedCoupon);
                }
                this.EmptyCart();
            } else {
                for(int j=0;j<this.getCart().size();j++){
                    this.getCart().get(j).AddStock(1);
                }
                System.out.println("Insufficient funds, please either remove some items from cart or add funds");
            }

        }
    }

