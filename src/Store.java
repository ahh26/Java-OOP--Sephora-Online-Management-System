import java.util.*;
public class Store {
    ArrayList<Product> products=new ArrayList<>();
    ArrayList<Customer> customers=new ArrayList<>();
    ArrayList<Order> orders=new ArrayList<>();
    int currentMonth;
    public Store(int currentMonth){
        this.currentMonth=currentMonth;
    }
    public ArrayList<Product> getProductList(){
        return products;
    }
    public ArrayList<Customer> getCustomerList(){
        return customers;
    }
    public ArrayList<Order> getOrderList(){
        return orders;
    }
    public int getCurrentMonth(){
        return currentMonth;
    }
    public void setCurrentMonth(int month){
        currentMonth=month;
    }
    public void addCustomer(Customer customer){ 
        customers.add(customer);
    }
    public void removeCustomer(Customer customer){
        customers.remove(customer);
    }
    public void addProduct(Product product){
        products.add(product);
    }
    public void removeProduct(Product product){
        products.remove(product);
    }
    public void editProduct(Product product,double newPrice){ //edit the price and points of product
        product.setPrice(newPrice);
        product.setPoints((int)newPrice);
    }
    public void addOrder(Order order){ //add an new order to the order list
        orders.add(0,order);
    }
    public void removeOrder(Order order){
        if(order.getStatus()==3)orders.remove(order); //only can remove order when the status is cancelled
    }

    //remove all spaces and change all letters to lowercase when comparing
    public ArrayList<Product> searchByCategory(String categoryWanted){ //by category
        ArrayList<Product> productByCategory=new ArrayList<Product>();
        for(Product p:products){
            if(p.getCategory().replaceAll(" ","").toLowerCase().contains(categoryWanted.replaceAll(" ","").toLowerCase()))productByCategory.add(p);
        }
        return productByCategory;
    }
    public ArrayList<Product> searchByBrand(String brandWanted){ //by brand
        ArrayList<Product> productByBrand=new ArrayList<Product>();
        for(Product p:products){
            if(p.getBrand().replaceAll(" ","").toLowerCase().contains(brandWanted.replaceAll(" ","").toLowerCase()))productByBrand.add(p);
        }
        return productByBrand;
    }
    public ArrayList<Product> searchByName(String nameWanted){ //by name
        ArrayList<Product> productByName=new ArrayList<Product>();
        for(Product p:products){
            if(p.getName().replaceAll(" ","").toLowerCase().contains(nameWanted.replaceAll(" ","").toLowerCase()))productByName.add(p);
        }
        return productByName;
    }
    public void processOrder(Order order,int stat){ //change the status of the order
        order.setStatus(stat);
    }
}

