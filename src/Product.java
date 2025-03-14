public class Product {
    static int productId=0;
    String id;
    String name;
    String brand;
    String category;
    int points;
    int stock;
    double price;
    public Product(String name,String brand,String category,int points,double price){
        this.name=name;
        this.brand=brand;
        this.category=category;
        this.points=points;
        this.price=price;
        id="PID"+String.valueOf(productId);
        stock=0;
        productId+=1;
    }
    public Product(String id,String name,String brand,String category,int points,double price,int stock){ //for birthdayGift
        this.name=name;
        this.brand=brand;
        this.category=category;
        this.points=points;
        this.price=price;
        this.id=id;
        this.stock=stock;
    }
    public String getName(){
        return name;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double amount){
        price=amount;
    }
    public String getID(){
        return id;
    }
    public int getStock(){
        return stock;
    }
    public int getPoints(){
        return points;
    }
    public void setPoints(int newPoints){
        points=newPoints;
    }
    public String getBrand(){
        return brand;
    }
    public String getCategory(){
        return category;
    }
    public void addStock(int amount){
       stock+=amount;
    }
    public void removeStock(int amount){
        stock-=amount;
    }
    public boolean outOfStock(){ //return if out of stock
        if(getStock()==0)return true;
        return false;
    }
    public boolean enoughStock(int amount){ //return if there's enough stock to remove "amount" of stock
        if(stock<amount)return false;
        return true;
    }



}