//C:\Users\AntheaHuang\Desktop\G11\12 Com Sci\Customer.txt
import java.io.*;
import java.util.*;
public class Main {
    static Scanner input=new Scanner(System.in);
    static Store store=new Store(11); //Current month is 11
    public static void main(String[] args) {
        File customerList=new File("/workspaces/ics4u-robotics-classes-project-anthea111/src/customerList.txt");
        File productList=new File("/workspaces/ics4u-robotics-classes-project-anthea111/src/productList.txt");
        FileReader in;
        BufferedReader readFile;
        String line;
        //Read in customer List
        try{
            in=new FileReader(customerList);
            readFile=new BufferedReader(in);
            while((line=readFile.readLine())!=null){
                String[] temp=line.split(",");
                store.addCustomer(new Customer(temp[0],Integer.parseInt(temp[1]),temp[2],temp[3]));
            }
        }catch(IOException e){
            System.err.println("IOException: "+e.getMessage());
        }

        // Read in product List
        try{
            in=new FileReader(productList);
            readFile=new BufferedReader(in);
            while((line=readFile.readLine())!=null){
                String[] temp=line.split(",");
                store.addProduct(new Product(temp[0],temp[1],temp[2],Integer.parseInt(temp[3]),Double.parseDouble(temp[4])));
            }
        }catch(IOException e){
            System.err.println("IOException: "+e.getMessage());
        }
        //Add 50 stock for all products read in from the file as 50(the default amount Product Class gave is 0)
        for(Product p:store.getProductList()){
            p.addStock(50);
        }

       // Main code
        boolean running=true;
        while(running){
            //main menu
            System.out.println("\nWelcome to Sephora!");
            System.out.println("----------------------");
            System.out.println("1-Browse as Guest");
            System.out.println("2-Login as Admin");
            System.out.println("3-Login as Customer");
            System.out.println("4-Sign-up as Customer");
            System.out.println("5-Exit");
        
            int choice=getUserChoice(); //the method for asking int input and avoid numberformatexception

            switch(choice){
                case 1:
                    browse(); //method at line113 //only able to look at the products
                    break;
                case 2: 
                    adminLogin(); //method at line 165//login as admin(admin code:admin2024) and access admin menu
                    break;
                case 3:
                    customerLogin(); //method at line 540//login as customer and access customer menu
                    break;
                case 4:
                    customerSignup(); //method at line 469//register as a new customer and add to the store's customer list
                    break;
                case 5:
                    running=false; //set running as false so that the entire while loop ends
                    System.out.println("Thank you for visiting Sephora! Bye:D");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
        //methods of writing to files
        writeToProduct();
        writeToCustomer();
        writeToOrder();

    }

    //Browsing page as guest
    public static void browse(){
        System.out.println("\nBrowse products as guest: ");
        System.out.println("--------------------------------");
        System.out.println("1-View all products in store");
        System.out.println("2-Search by brands");
        System.out.println("3-Search by category");
        System.out.println("4-Search by name");
        int choice=getUserChoice();
        switch (choice) {
            case 1:
                displayProducts(store.getProductList()); //method at line 774//display all the products in store based on format
                break;
            case 2:
                System.out.println("Enter the brand: ");
                String brand=input.nextLine().trim(); //remove white spaces before and after input to make the search more accurate
                displayProducts(store.searchByBrand(brand));
                break;
            case 3:
                System.out.println("Enter the category(e.g.makeup/skincare/fragrance/hair): ");
                String category=input.nextLine().trim();
                displayProducts(store.searchByCategory(category));
                break;
            case 4:
                System.out.println("Enter the name: ");
                String name=input.nextLine().trim();
                displayProducts(store.searchByName(name));
                break;

            default:
                System.out.println("Invalid input");
                break;
        }
    }

    //Product Searching method when logged in (for customers)
    public static void browse(Customer customer){
        System.out.println("\n1-View all products in store");
        System.out.println("2-Search by brands");
        System.out.println("3-Search by category");
        System.out.println("4-Search by name");
        int choice=getUserChoice();
        ArrayList<Product> resultProducts;
        switch (choice) { //all cases 1-4 have the same logic
            case 1:
                resultProducts=store.getProductList(); 
                displayProducts(resultProducts); 
                if(resultProducts.isEmpty())break; 
                customerAddToCart(resultProducts,customer); 
                break;
            case 2:
                System.out.println("Enter the brand: "); 
                String brand=input.nextLine().trim();  //ask user for the brand he/she wants to search for
                resultProducts=store.searchByBrand(brand); 
                displayProducts(resultProducts);  //display the searching result based on format
                if(resultProducts.isEmpty())break; //break before customerAddToCart method if the searching result is empty
                customerAddToCart(resultProducts,customer); //method at line656// ask customers if they want to add any product to cart
                break;
            case 3:
                System.out.println("Enter the category(e.g.makeup/skincare/fragrance/hair): ");
                String category=input.nextLine().trim();
                resultProducts=store.searchByCategory(category);
                displayProducts(resultProducts);
                if(resultProducts.isEmpty())break;
                customerAddToCart(resultProducts,customer);
                break;
            case 4:
                System.out.println("Enter the name: ");
                String name=input.nextLine().trim();
                resultProducts=store.searchByName(name);
                displayProducts(resultProducts);
                if(resultProducts.isEmpty())break;
                customerAddToCart(resultProducts,customer);
                break;

            default:
                System.out.println("Invalid input");
                break;
        }
    }

    //Admin methods
    public static void adminLogin(){ //only one admin, code is admin2024
        System.out.println("Enter admin code: ");
		String code = input.nextLine();
		if(code.equals("admin2024"))adminMenu(); //method at line171//access admin menu if the code is correct 
        else System.out.println("Incorrect code");
    }
    public static void adminMenu(){
        boolean adminRunning = true;
        while (adminRunning) { //keep running until admin logout
            System.out.println("\nWelcome admin01");
            System.out.println("---------------------------");
            System.out.println("1-Manage products");
            System.out.println("2-Manage customers");
            System.out.println("3-Manage orders");
            System.out.println("4-Logout");

            int choice = getUserChoice();
            switch(choice){
                case 1:
                    manageProducts(); //method at line 359//access products list and manage products
                    break;
                case 2:
                    manageCustomers(); //method at line282//access customers list and manage customers
                    break;
                case 3:
                    manageOrders(); //method at line201//access orders list and manage orders
                    break;
                case 4:
                    adminRunning=false;
                    System.out.println("Admin logged out");
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }
    public static void manageOrders(){
        boolean running=true;
        while(running){
            System.out.println();
            System.out.println("\nManage Orders");
            System.out.println("\nOrder List");
            System.out.println("-------------------------");
            for(Order o:store.getOrderList()){
                System.out.println(o.getID()+" "+o.getProductNum()+"products"+" "+o.showStatus()); //always display all orders first
            }
            System.out.println("\n1-View order details");
            System.out.println("2-Change order status");
            System.out.println("3-Remove order");
            System.out.println("4-Back to Main Menu");
            System.out.println();
            int choice=getUserChoice();
            switch(choice){
                case 1: //view details
                    while(true){
                        System.out.println("Enter the orderID of the order you wan to check details(Enter 0 to quit)");
                        String id=input.nextLine();
                        if(id.equals("0"))break; //quit if input is 0
                        for(Order o:store.getOrderList()){
                            if(id.equals(o.getID())){ //iterate through store's order list and display details if orderif matches
                                displayOrder(o.getOrderProducts(),o); //display order based on format
                                System.out.println("Customer: "+o.getCustomer().getID()+" - "+o.getCustomer().getName()); //display which customer owns the order
                                System.out.println();
                            }
                        }
                    }
                    break;
                case 2: //change status
                    while(true){
                        System.out.println("Enter the orderID of the order you want to change status(Enter 0 to quit)");
                        String id=input.nextLine();
                        if(id.equals("0"))break;
                        for(Order o:store.getOrderList()){
                            if(id.equals(o.getID())){ //if the orderid matches with one of the orders in store's order lit
                                System.out.println("Current status: "+o.showStatus()); //show current status and ask for the status admin wants to change to
                                System.out.println("You want to change the status to:\n1-In Progress 2-Shipped 3-Delivered 4-Cancelled 5-Quit");
                                int stat;
                                while(true){ //keep getting user input until the input is an integer from 1-5
                                    stat=getUserChoice();
                                    if(stat>=1&&stat<=4){
                                        o.setStatus(stat-1); //-1 so the number matches with the index of the status in the array //see Order Class//
                                        System.out.println("Order status changed to "+o.showStatus()+" successfully!");
                                        break;
                                    }else if(stat==5){ //quit if input is 5
                                        break;
                                    }
                                } 

                            }
                        }
                    }
                    break;
                case 3:
                     while(true){
                        System.out.println("Enter the orderID of the order you want to remove(Enter 0 to quit)");
                        String id=input.nextLine();
                        if(id.equals("0"))break;
                        for(Order o:store.getOrderList()){
                            if(id.equals(o.getID())&&o.getStatus()==3){
                                System.out.println("Remove order "+o.getID()+"?\n1-Yes 2-No");
                                if(getUserChoice()==1)store.removeOrder(o);
                                break;
                            }else{
                                System.out.println("Order not found/can't be removed");
                                break;
                            }
                        }
                    }
                    break;
                case 4:
                    running=false;
                    break;
                default:
                 System.out.println("Invalid input");
            }

        }
    }
    public static void manageCustomers(){
        boolean running=true;
        while(running){
            System.out.println();
            System.out.println("\nManage Customers");
            System.out.println("\nCustomer List");
            System.out.println("-------------------------");
            for(Customer c:store.getCustomerList()){ //display sll customers first
                System.out.println(c.getID()+" "+c.getName()+" "+c.getEmail()+" "+c.getLoyaltyPoints()+" ");
            }
            System.out.println("\n1-Add customer");
            System.out.println("2-Remove customer");
            System.out.println("3-Back to Main Menu");
            System.out.println();
            int choice=getUserChoice();
            switch(choice){
                case 1: //add customer
                    System.out.println("Enter name:"); //getting name
                    String name=input.nextLine();
                    System.out.println("Enter BDMonth:");
                    int month;
                    while(true){ //keeping looping until getting valid birthdaymonth
                        month=getUserChoice();
                        if(month>=1&&month<=12)break;
                        System.out.println("Invalid BDMonth");
                    }
                    System.out.println("Enter email:"); //getting email
                    String email;
                    while(true){ //consider valid email if includes @ and .com
                        email=input.nextLine().trim();
                        if(email.contains("@")&&email.contains(".com"))break;
                        System.out.println("Invalid email");
                    }
                    boolean skip=false;
                    for(Customer c:store.getCustomerList()){ //determine whether the email is registered or not
                        if(c.getEmail().equals(email)){
                            System.out.println("Email already registered");
                            skip=true;
                            break;
                        }
                    }
                    if(skip)break; //break if email alreay registered
                    String password="00000000"; //if the customer is added by admin, the default password is 00000000
                    System.out.println("Add customer "+name+" "+email+"?\n1-Yes 2-No"); 
                    if(getUserChoice()==1){ //confirm if adding new customer
                        store.addCustomer(new Customer(name,month,email,password));
                        System.out.println("Customer added!");
                    }
                    break;
                case 2: //remove customer
                     while(true){
                        System.out.println("Enter the customerID of the customer you want to remove(enter 0 to quit):");
                        String id=input.nextLine().trim(); //get customerID of the customers wanting to remove
                        if(id.equals("0"))break; //quit if input is 0,otherwise keep asking
                        else{
                            for(Customer c:store.getCustomerList()){
                                if(c.getID().equals(id)){ //if found matching customerID
                                    System.out.println("Remove customer "+c.getID()+" "+c.getName()+"?\n1-Yes 2-No"); //confirm if removing customer
                                    int ans=getUserChoice();
                                    if(ans==1){
                                        store.removeCustomer(c);
                                        System.out.println("Customer removed from store!");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 3: //quit the page
                    running=false;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }

    }
    public static void manageProducts(){
        boolean running=true;
        while(running){
            System.out.println();
            System.out.println("\nManage Products");
            System.out.println("\nProduct List");
            System.out.println("-------------------------");
            displayProducts(store.getProductList());
            System.out.println("\n1-Add product");
            System.out.println("2-Remove product");
            System.out.println("3-Add Stock");
            System.out.println("4-Remove Stock");
            System.out.println("5-Back to Main Menu");
            System.out.println();
            int choice=getUserChoice();

            switch(choice){
                case 1:
                    System.out.print("Enter product name: ");
                    String name = input.nextLine();
                    System.out.print("Enter product brand: ");
                    String brand=input.nextLine();
                    System.out.print("Enter product category(makeup/skincare/fragrance/hair): ");
                    String category=input.nextLine();
                    System.out.println("Enter price: ");
                    double price = getUserDouble();
                    System.out.print("Enter initial stock: ");
                    int stock = getUserChoice();
                    System.out.println("Add new product "+brand+" "+name+" $"+price+" to the store?\n1-Yes 2-No");
                    if(getUserChoice()==1){
                        store.addProduct(new Product(name,brand,category,(int)price,price));
                        store.getProductList().get(store.getProductList().size()-1).addStock(stock);
                        System.out.println("Product added successfully.");
                    }
                    break;
                case 2:
                    while(true){
                        System.out.println("Enter the productID of the product you want to remove(enter 0 to quit):");
                        String id=input.nextLine().trim();
                        if(id.equals("0"))break;
                        else{
                            for(Product p:store.getProductList()){
                                if(p.getID().equals(id)){
                                    System.out.println("Remove product "+p.getID()+" "+p.getBrand()+" "+p.getName()+"?\n1-Yes 2-No");
                                    int ans=getUserChoice();
                                    if(ans==1){
                                        store.removeProduct(p);
                                        System.out.println("Product removed from store!");
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 3:
                   while(true){
                        System.out.println("Enter the productID of the product you want to add stock(Enter 0 to quit):");
                        String id=input.nextLine().trim();
                        if(id.equals("0"))break;
                        else{
                            for(Product p:store.getProductList()){
                                if(p.getID().equals(id)){
                                        System.out.println("Amount?");
                                        int amount=getUserChoice();
                                        if(amount<=0){
                                            System.out.println("Invalid number");
                                            break;
                                        }
                                        p.addStock(amount);
                                        System.out.println("Stocks added!");
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    while(true){
                        System.out.println("Enter the productID of the product you want to remove stock(Enter 0 to quick):");
                        String id=input.nextLine().trim();
                        if(id.equals("0"))break;
                        else{
                            for(Product p:store.getProductList()){
                                if(p.getID().equals(id)){
                                    System.out.println("Amount?");
                                    int amount=getUserChoice();
                                    if(amount<=0){
                                        System.out.println("Invalid number");
                                        break;
                                    }
                                    if(!(p.enoughStock(amount))){
                                        System.out.println("Not enough stock");
                                        break;
                                    }
                                    p.removeStock(amount);
                                    System.out.println("Stocks removed!");
                                    break;
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    running=false;
                    break;
                default:
                    System.out.println("Invalid input");
                }
                
            }
    }

    //Customer methods
    public static void customerSignup(){//customer self sign-up method
        String name;
        int bdmonth;
        String email;
        String password;
        System.out.println("\nNew customer sign-up");
        System.out.println("-----------------------------");
        //getting name
        while(true){
            System.out.println("Enter your name:");
            name=input.nextLine();
            System.out.println("Confirm your name: "+name+"\n1-Yes 2-No"); //confirm name
            String ans=input.nextLine();
            if(ans.equals("1")||ans.equalsIgnoreCase("yes"))break;
        }
        //getting bdmonth
        while(true){
            while(true){ //keep looping until getting valid birthday month
                System.out.println("Enter your birthday month(1-12):");
                String month=input.nextLine();
                try{
                    bdmonth=Integer.parseInt(month);
                }catch(NumberFormatException e){
                    System.out.println("Invalid birthday month");
                    continue;
                }
                if(bdmonth>=1&&bdmonth<=12)break;
                System.out.println("Invalid birthday month");
            }
            System.out.println("Confirm your BirthdayMonth: "+bdmonth+"\n1-Yes 2-No"); //confirm birthday month
            String ans=input.nextLine();
            if(ans.equals("1")||ans.equalsIgnoreCase("yes"))break;
        }
        //getting email and password
        boolean emailExisted=false;
        while(true){
            while(true){ //keep looping until getting an email that contains @ and .com
                System.out.println("Enter your email:");
                email=input.nextLine();
                if(email.contains("@")&&email.contains(".com"))break;
                System.out.println("Invalid email");
            }
            for(Customer cus:store.getCustomerList()){ //determine whether the email is already registered or not
                if(cus.getEmail().equals(email)){
                    emailExisted=true;
                    break;
                }
            }
            while(true){ //keeping looping until getting a password thats at least 6 chars can doesnt include , and .
                System.out.println("Enter your password(at least 6 characters, cannot include ',' and '.'):");
                password=input.nextLine();
                if(password.contains(",")||password.contains(".")||password.length()<6){
                    System.out.println("Invalid password");
                    continue;
                }
                break;
            }
            if(emailExisted){ //break and go back to main page if email already registerd
                System.out.println("Email is already registered");
                break;
            }
            System.out.println("Confirm your email and password:\n"+email+"\n"+password+"\n1-Yes 2-No"); //confirm email and password
            String ans=input.nextLine();
            if(ans.equals("1")||ans.equalsIgnoreCase("yes"))break;
 
        }
        if(!emailExisted){
            store.addCustomer(new Customer(name,bdmonth,email,password));
            System.out.println("Sign-up successful! You can login now :D");
        } 
    }
    public static void customerLogin(){ 
        System.out.println("Enter customer email: ");
		String cusEmail = input.nextLine();
		System.out.println("Enter password: ");
		String cusPassword= input.nextLine();
        boolean cusRegistered=false;
        boolean isLoggingOut=false; //used to prevent the problem of outputing "incorrect password" when logging out
		for (Customer c: store.getCustomerList()) { 
            if(c.getEmail().equals(cusEmail)){ //determine whether the email exist
                cusRegistered=true;
                if(c.getPassword().equals(cusPassword)){ //determine if the registered email and its password matches with input
                    customerMenu(c); //access customer menu if correct
                    isLoggingOut=true;
                    break;
                }
            }	
		}
        if(!isLoggingOut){ //both incorrect password and logging out after accessing the menu will go through this code segment
            if(cusRegistered)System.out.println("Incorrect password");
            else System.out.println("Email does not exist");
        }
	}
    public static void customerMenu(Customer customer){
        boolean customerRunning = true;
        while (customerRunning) {
            System.out.println("\nWelcome "+customer.getName());
            System.out.println("---------------------------");
            System.out.println("1-Browse Products");
            System.out.println("2-View Cart");
            System.out.println("3-View Orders");
            System.out.println("4-View User Info");
            System.out.println("5-Logout");

            int choice = getUserChoice();
            switch(choice){
                case 1: //browse the products and add products to cart
                    browse(customer); //method at line119//
                    break;
                case 2: //view cart
                    ArrayList<Product> cart=customer.viewCart();
                    displayCart(cart,customer);
                    if(cart.isEmpty())break; //return to menu if cart is empty to prevent asking questions on next line
                    System.out.println("1-Proceed to check out\n2-Remove products from cart\n3-Clear cart\n4-Keep browsing");
                    int ans;
                    while(true){ //break until the input is an int between 1 and 4
                        ans=getUserChoice();
                        if(ans>=1&&ans<=4)break;
                    }
                    if(ans==1){ //proceed to check out
                        Order order = new Order(customer, new ArrayList<>(customer.viewCart())); 
                        customer.addOrder(order);  //add order to customer's order list
                        store.addOrder(order); //add order to store's order list
                        beforeCustomerCheckout(customer,order); //apply discount and birthdaygift if applicable// method at line697//
                        order.checkOut(); //checkout using method of class Order
                        System.out.println();
                        System.out.println("Order placed successfully!");
                        System.out.println("Order Summary");
                        System.out.println("--------------------------");
                        System.out.println("OrderID: "+order.getID());
                        displayOrder(order.getOrderProducts(),order);
                        customer.clearCart(); //clear the cart
                        break;
                    }else if(ans==2){ //remove products from cart
                        customerRemoveFromCart(cart,customer); //method at line 677//
                    }else if(ans==3){ //clear cart
                        customer.clearCart();
                    }
                    break;
                case 3: //view orders
                    ArrayList<Order>orders=customer.getOrderList();
                    if(orders.isEmpty()){ //break if order list is empty
                        System.out.println("No orders yet");
                        break;
                    }
                    System.out.println("Order list");
                    System.out.println("----------------------------");
                    for(Order o:orders){ //display order id,number of products, total amount and status
                        System.out.println();
                        System.out.println(o.getID());
                        System.out.println("Number of products: "+o.getOrderProducts().size());
                        System.out.println("Total amount: $"+o.getTotalAmount());
                        System.out.println("Status: "+o.showStatus());
                    }
                    while(true){ //display order details based on user inputted orderID
                        System.out.println("\nEnter the orderID of the order you want to check for details(Enter 0 to quit): ");
                        String checkID=input.nextLine().trim();
                        if(checkID.equals("0"))break;
                        else{
                            for(Order o:orders){
                                if(o.getID().equals(checkID)){
                                    System.out.println("\nOrderID: "+o.getID());
                                    displayOrder(o.getOrderProducts(),o);
                                }
                            }
                        }
                    }
                    break;
                case 4: //display customer's info
                    System.out.println("Your Info ");
                    System.out.println("-------------------------------");
                    System.out.println("UserID: "+customer.getID());
                    System.out.println("Name: "+customer.getName());
                    System.out.println("Loyalty points: "+customer.getLoyaltyPoints());
                    System.out.println("Birthday month: "+customer.getBDMonth());
                    System.out.println("Email: "+customer.getEmail());
                    System.out.println("Password: "+customer.getPassword());
                    break;
                case 5: //quite customer menu
                    customerRunning=false;
                    System.out.println("User logged out");
                    break;
                default:
                    System.out.println("Invalid input"); 
            }
        }
    }
    static void customerAddToCart(ArrayList<Product> products,Customer customer){ //add products to cart when browsing
        while(true){ //keep asking until 0 is inputted
            System.out.println("Enter the productID of the product you want to add into your cart(Enter 0 to quit):");
            String inputID=input.nextLine();
            if(inputID.equals("0"))break;
            for(Product p:products){
                if(p.getID().equals(inputID)){ //first check if the inputted productID matches with any of the products shown
                    System.out.println("Amount?"); //ask for amount user wants to add
                    int amount=getUserChoice();
                    if(amount<=0)System.out.println("Invalid input");
                    else if(p.outOfStock())System.out.println("Out of stock!"); //0 stock --> out of stock
                    else if(!(p.enoughStock(amount+customer.productsInCart(p))))System.out.println("Not enough amount of stock"); //stock < amount -->not enough stock
                    else{
                        customer.addToCart(p,amount); //add amount number of products to cart
                        System.out.printf("Product %s x%d added to cart!\n",inputID,amount);
                    } 
                    break;
                }
            }
        }

    }
    static void customerRemoveFromCart(ArrayList<Product> products,Customer customer){ //remove products from cart
        while(true){ //keeping asking until 0 is entered
            System.out.println("Enter the productID of the product you want to remove from your cart(Enter 0 to quit):");
            String inputID=input.nextLine();
            if(inputID.equals("0"))break;
            for(Product p:products){
                if(p.getID().equals(inputID)){ //check if the productID matches with any product
                    System.out.println("Amount?"); //ask for amount
                    int amount=getUserChoice();
                    if(customer.productsInCart(p)<amount||amount<=0)System.out.println("Invalid number"); //"invalid" if the amount<=0 or is bigger than the number of that product in cart
                    else{
                        for(int i=0;i<amount;i++)customer.removeFromCart(p); //remove amount number of products from cart
                        System.out.printf("Product %s x%d removed from cart!\n",inputID,amount);
                    }
                    break;
                }
            }
        }

    }
    public static void beforeCustomerCheckout(Customer customer,Order order){ //apply discount or birthdaygift if applicable before checking out
        if(customer.getLoyaltyPoints()>=2500){ //if customer has a loyaltypoint>2500
            int ans;
            while(true){//keep asking if user want to apply discount before user entered 1 or 2
                System.out.println("You've achieved 2500+ points, would you like to apply a $100 off discount?\n1-Yes 2-No");
                ans=getUserChoice();
                if(ans==1||ans==2)break; 
                System.out.println("Invalid input");
            }
            if(ans==1){ //apply 100 discount if input is 1
                order.applyLPDiscount(100); //apply discount
                customer.removeLoyaltyPoints(2500); //remove used loyalty points from customer's account
                System.out.println("Loyalty points applied! New total amount: $"+order.getTotalAmount()); //output new total
            }
        }else if(customer.getLoyaltyPoints()>=500){ //same logic as above
            int ans;
            while(true){
                System.out.println("You've achieved 500+ points, would you like to apply a $10 off discount?\n1-Yes 2-No");
                ans=getUserChoice();
                if(ans==1||ans==2)break;
                System.out.println("Invalid input");
            }
            if(ans==1){
                order.applyLPDiscount(10);
                customer.removeLoyaltyPoints(500);
                System.out.println("Loyalty points applied! New total amount: "+order.getTotalAmount());
            }
        }
        if(customer.isBDMonth(store) && order.getTotalAmount()>=25.0 && !customer.isGiftRedeemed()){ //apply birthdaygift if it's customer's bdmonth, the customer spent over $25, and customer hasn't redeemed gift yet
            int redeem;
            while(true){
                System.out.println("It's your birthday month and you have a total amount over $25! Would you like to redeem your birthday gift set?\n1-Yes 2-No");
                String ans=input.nextLine();
                if(ans.equals("1")||ans.equals("2")){
                    redeem=Integer.parseInt(ans);
                    break;
                }
            }
            if(redeem==1){ //reddem gift if user input 1
                System.out.println("Birthday gift redeemed!");
                order.redeemBDGift();
            }
        }

    }

    //General methods
    public static int getUserChoice(){ //receive user int input
        int choice;
        while(true){ //keep looping until getting an int input
            try {
                choice=input.nextInt();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
        }
        return choice;
    }
    public static double getUserDouble(){ //receive user double input
        double num;
        while(true){ //keep looping unti getting an double input
            try {
                num=input.nextDouble();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine();
            }
        }
        return num;
    }

    //Display format
    public static void displayProducts(ArrayList<Product> products){ //display result when searching
        System.out.println();
        if (products.isEmpty()) { //if product list is empty
            System.out.println("No products found");
        } else {
            for (Product p:products) {
                System.out.println(p.getID()+" "+p.getBrand()+", "+p.getName()+" $"+p.getPrice()+" - "+p.getStock()+" in stock");
            }
        }
        System.out.println();
    }
    public static void displayCart(ArrayList<Product> cart,Customer customer){
        displayProducts(cart); //use displayproducts method to display the products first
        if(!customer.emptyCart()){
            double sum=0;
            for (Product p:cart) {
                sum+=p.getPrice();
            }
            System.out.println("---------------------------------");
            System.out.println("Total Amount: $"+sum); //output the total cost of products in cart
        }else{
            System.out.println("Keep browsing and add products into your cart!");
        }
        System.out.println();
    }
    public static void displayOrder(ArrayList<Product> products,Order o){ //display the detailed order
        System.out.println();
        for (Product p:products) {
            System.out.println(p.getID()+" "+p.getBrand()+", "+p.getName()+" $"+p.getPrice());
        }
        System.out.println("---------------------------------");
        System.out.println("Total Amount: $"+o.getTotalAmount());
        System.out.println("Number of products: "+o.getOrderProducts().size());
        System.out.println("Status: "+o.showStatus());
    }
    

    //Write data to files:
    public static void writeToProduct(){ //write to a file the list of products in store
        File productFile=new File("/workspaces/ics4u-robotics-classes-project-anthea111/src/writeProduct.txt");
        FileWriter out;
        BufferedWriter writeFile;
        try{
            out=new FileWriter(productFile);
            writeFile=new BufferedWriter(out);
            writeFile.write("Product List\nID,Name,Brand,Category,Points,Price,Stock\n");
            writeFile.write("-------------------------------------------------------------");
            writeFile.newLine();
            for(Product p:store.getProductList()){
                writeFile.write(p.getID()+","+p.getName()+","+p.getBrand()+","+p.getCategory()+","+p.getPoints()+","+p.getPrice()+","+p.getStock());
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
        }catch(IOException e){
            System.err.println("IOException"+e.getMessage());

        }
    }
    public static void writeToCustomer(){ //write to a file the list of customers 
        File customerFile=new File("/workspaces/ics4u-robotics-classes-project-anthea111/src/writeCustomer.txt");
        FileWriter out;
        BufferedWriter writeFile;
        try{
            out=new FileWriter(customerFile);
            writeFile=new BufferedWriter(out);
            writeFile.write("Customer List\nID,Name,BDMonth,Email,Password,LoyaltyPoints\n");
            writeFile.write("-------------------------------------------------------------");
            writeFile.newLine();
            for(Customer c:store.getCustomerList()){
                writeFile.write(c.getID()+","+c.getName()+","+c.getBDMonth()+","+c.getEmail()+","+c.getPassword()+","+c.getLoyaltyPoints());
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
        }catch(IOException e){
            System.err.println("IOException"+e.getMessage());

        }
    }
    public static void writeToOrder(){ //write to a file the list of orders
        File orderFile=new File("/workspaces/ics4u-robotics-classes-project-anthea111/src/writeOrder.txt");
        FileWriter out;
        BufferedWriter writeFile;
        try{
            out=new FileWriter(orderFile);
            writeFile=new BufferedWriter(out);
            writeFile.write("Order List\nID,TotalAmount,ProductNum,Status(CustomerID,CustomerName)\n");
            writeFile.write("-------------------------------------------------------------");
            writeFile.newLine();
            for(Order o:store.getOrderList()){
                writeFile.write(o.getID()+","+o.getTotalAmount()+","+o.getProductNum()+","+o.showStatus()+"("+o.getCustomer().getID()+","+o.getCustomer().getName()+")");                
                writeFile.newLine();
            }
            writeFile.close();
            out.close();
        }catch(IOException e){
            System.err.println("IOException"+e.getMessage());

        }
    }
}
