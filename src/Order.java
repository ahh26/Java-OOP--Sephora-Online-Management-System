import java.util.*;
public class Order {
    static int orderID=0; //the id number increases by 1 each time a new order is created
    Customer customer;
    double totalAmount;
    ArrayList<Product> products;
    int status=0; 
    static String[] orderStat={"InProgress","Shipped","Delivered","Cancelled"}; 
    String id;
    Store store;
    static Scanner in=new Scanner(System.in);

    public Order(Customer customer,ArrayList<Product> products){
        this.customer=customer;
        this.products=products;
        totalAmount=calculateTotalAmount();
        id="orderid"+String.valueOf(orderID);
        orderID+=1;
        status=0;
    }
    public Customer getCustomer(){
        return customer;
    }
    private double calculateTotalAmount() {
        double total=0;
        for (Product product:products) {
            total+=product.getPrice();
        }
        return total;
    }
    public String getID(){
        return id;
    }
    public ArrayList<Product> getOrderProducts(){
        return products;
    }
    public int getProductNum(){
        return products.size();
    }
    public double getTotalAmount(){
        return totalAmount;
    }
    public void applyLPDiscount(double discount){ 
        totalAmount=Math.max(totalAmount-discount,0); //prevent making the total amount a negative number
    }
    public void redeemBDGift(){ //add a new product to order if redeeming BDgift
        products.add(new Product("BDGIFT","Lip & Brow Birthday set","Kosas","makeup",0,0.0,1)); 
        customer.setGiftRedeemed(true);
    }
    public void checkOut(){
        customer.addLoyaltyPoints((int)totalAmount);
        for(Product product:products) { //remove stock of products
            product.removeStock(1); 
        }
    }

    public void setStatus(int newStat) {
        status = newStat;
    }
    public int getStatus(){ //get the status as a integer
        return status;
    }
    public String showStatus(){ //get the status as a string, used when displaying status of order
        return orderStat[status];
    }
    public void cancelOrder() { 
        status=3; //set status to 3, which refers to cancelled in orderStat array
        for(Product product:products) {
            customer.removeLoyaltyPoints(product.getPoints()); //remove the loyaltypoints added
            product.addStock(1);  // add the stock back
        }  
    }
}

