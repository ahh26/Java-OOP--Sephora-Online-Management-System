import java.util.*;
public class Customer {
    static int userID; //the id number increases by 1 each time a new customer registered
    String name;
    int loyaltyPoints;
    int birthdayMonth;
    String email;
    String password;
    String id;
    boolean giftRedeemed; //used to determine whether the customer has redeemed the gift or not
    ArrayList<Product> cart=new ArrayList<>();
    ArrayList<Order> orderList=new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public Customer(String name,int birthdayMonth,String email,String password){
        this.name=name;
        this.birthdayMonth=birthdayMonth;
        this.email=email;
        this.password=password;
        id="CUS"+String.valueOf(userID);
        userID+=1;
        loyaltyPoints=0;
        giftRedeemed=false;
    }
    public boolean isBDMonth(Store store){ //return whether it's customer's birthday month or not
        if(birthdayMonth==store.getCurrentMonth())return true;
        return false;
    }
    public boolean isGiftRedeemed(){ 
        return giftRedeemed;
    }
    public void setGiftRedeemed(boolean ans){  
        giftRedeemed=ans;
    }
    public int getLoyaltyPoints(){
        return loyaltyPoints;
    }
    public String getName(){
        return name;
    }
    public String getID(){
        return id;
    }
    public int getBDMonth(){
        return birthdayMonth;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public ArrayList<Order> getOrderList(){
        return orderList;
    }
    public void addOrder(Order order){
        orderList.add(0,order); //add to the start of the list, so later when displaying all orders, the latest order come up first
    }
    public void addLoyaltyPoints(int points){
        loyaltyPoints+=points;
    }
    public void removeLoyaltyPoints(int points){
        loyaltyPoints-=points;
    }
    public int productsInCart(Product product){ //count the number of products in cart
        int num=0;
        for(Product p:cart){
            if(p.equals(product))num+=1;
        }
        return num;
    }
    public void addToCart(Product product,int amount){
        for(int i=0;i<amount;i++)cart.add(product);
    }
    public void removeFromCart(Product product){
        if(cart.contains(product))cart.remove(product);
    }
    public void clearCart(){
        cart.clear();
    }
    public void sortCart(){ //sort the cart based on the productID in ascending order
        cart.sort(Comparator.comparing(Product::getID));
    }
    public ArrayList<Product> viewCart(){
        sortCart();
        return cart;
    }
    public boolean emptyCart(){ //return whether the cart is empty
        if(cart.isEmpty())return true;
        else return false;
    }
}   

    

    

