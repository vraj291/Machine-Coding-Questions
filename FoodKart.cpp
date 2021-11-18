#include <bits/stdc++.h>
using namespace std;

vector<string> processQuery(string input,string delimiter = " "){
    vector<string> result;
    int start = 0;
    int end = input.find(delimiter);
    while(end != string::npos){
        result.push_back(input.substr(start,end-start));
        start = end+1;
        end = input.find(delimiter,end+1);
    }
    result.push_back(input.substr(start));
    return result;
}

class Order{

    string restaurantName;
    int quantity;
    int price;

    public:

    Order(string restaurantName,int quantity,int price){
        this->restaurantName = restaurantName;
        this->quantity = quantity;
        this->price = price;
    }

    void display(){
        cout << "\t{\n";
        cout << "\t\tRestaurant: " << restaurantName << "\n";
        cout << "\t\tQuantity: " << quantity << "\n";
        cout << "\t\tAmount: " << price << "\n\t},";
    }

};

class User{

    string name;
    string phoneNo;
    string gender;
    string pincode;
    vector<Order*> orders;

    public:

    User(string name,string gender,string phoneNo,string pincode){
        this->name = name;
        this->gender = gender;
        this->phoneNo = phoneNo;
        this->pincode = pincode;
    }

    bool isCurrent(string phoneNo){
        return this->phoneNo == phoneNo;
    }

    string getPinCode(){
        return pincode;
    }

    void addOrders(string restaurantName,int quantity,int price){
        orders.push_back(new Order(restaurantName,quantity,price));
    }

    void display(){
        cout << "Name: " << name << "\n";
        cout << "Gender: " << gender << "\n";
        cout << "Phone Number: " << phoneNo << "\n";
        cout << "Pincode: " << pincode << "\n";
        cout << "Orders: [";
        if(orders.size() != 0){
            cout << "\n";
            for(int i=0;i<orders.size();i++){
                orders[i]->display();
            }
        }
        cout << "]\n\n";
    }
};


class Review{

    User* user;
    int rating;
    string comment;

    public:

    Review(User* user,int rating,string comment = ""){
        this->user = user;
        this->rating = rating;
        this->comment = comment;
    }
};

class Restaurant{

    string name;
    vector<string> pincodes;
    string dishName;
    int dishPrice;
    int dishQuantity;
    User* user;
    vector<Review*> reviews;
    double avgRating;

    public:

    Restaurant(User* user,string name,string pincodes,string dishName,int dishPrice,int dishQuantity):avgRating(0){
        this->user = user;
        this->name = name;
        this->dishName = dishName;
        this->dishPrice = dishPrice;
        this->dishQuantity = dishQuantity;
        this->pincodes = processQuery(pincodes,"/");
    }

    string getName(){
        return name;
    }

    int getPrice(){
        return dishPrice;
    }

    int getRating(){
        return avgRating;
    }

    bool verifyUser(User* user){
        return this->user == user;
    }

    bool verifyName(string name){
        return this->name == name;
    }

    bool isServicable(string pincode){
        vector<string>::iterator it = find(pincodes.begin(),pincodes.end(),pincode);
        if(it != pincodes.end()){
            return true;
        }
        return false;
    }

    void updateQuantity(int quantity){
        dishQuantity += quantity;
    }

    bool placeOrder(int quantity){
        if(dishQuantity- quantity < 0){
            return false;
        }
        dishQuantity -= quantity;
        return true;
    }

    void addLocation(string pincodes){
        vector<string> result = processQuery(pincodes,"/");
        for(int i=0;i<result.size();i++){
            this->pincodes.push_back(result[i]);
        }
    }

    void removeLocation(string pincodes){
        vector<string> result = processQuery(pincodes,"/");
        vector<string>::iterator it;
        for(int i=0;i<result.size();i++){
            it = find(this->pincodes.begin(),this->pincodes.end(),result[i]);
            if(it != this->pincodes.end()){
                this->pincodes.erase(it);
            }
        }
    }

    void addReview(User* user,int rating,string comment){
        reviews.push_back(new Review(user,rating,comment));
        avgRating = ((avgRating*(reviews.size()-1)) + rating)/reviews.size();
    }

    void display(){
        cout << "Name: " << name << "\n";
        cout << "Pincodes : [ ";
        if(pincodes.size() != 0){
            for(int i=0;i<pincodes.size()-1;i++){
                cout << pincodes[i] << ", ";
            }
            cout << pincodes[pincodes.size()-1];
        }
        cout << " ]\n";
        cout << "Dish: " << dishName << "\n";
        cout << "Price: " << dishPrice << "\n";
        cout << "Quantity: " << dishQuantity << "\n";
        cout << "\n";
    }

};

class Application{

    User* currentUser;
    vector<User*> users;
    vector<Restaurant*> restaurants;

    Restaurant* getRestaurant(string name){
        for(int i=0;i<restaurants.size();i++){
            if(restaurants[i]->verifyName(name)){
                return restaurants[i];
            }
        }
        return NULL;
    }

    vector<Restaurant*> getNearbyRestaurants(){
        vector<Restaurant*> result;
        string pincode = currentUser->getPinCode();
        for(int i=0;i<restaurants.size();i++){
            if(restaurants[i]->isServicable(pincode)){
                result.push_back(restaurants[i]);
            }
        }
        return result;
    }

    public:

    Application(){}

    void registerUser(string name,string gender,string phoneNo,string pincode){
        users.push_back(new User(name,gender,phoneNo,pincode));
    }

    void loginUser(string phoneNo){
        for(int i=0;i<users.size();i++){
            if(users[i]->isCurrent(phoneNo)){
                currentUser = users[i];
                return;
            }
        }
    }

    void createRestaurant(string name,string pincodes,string dishName,int dishPrice,int dishQuantity){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = new Restaurant(currentUser,name,pincodes,dishName,dishPrice,dishQuantity);
        restaurants.push_back(restaurant);
    }

    void updateQuantity(string name,int quantity){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = getRestaurant(name);
        if(restaurant == NULL || (!restaurant->verifyUser(currentUser))){
            cout << "Restaurant doesn't exist.\n";
            return;
        }
        restaurant->updateQuantity(quantity);
        cout << "Quantity updated successfully.\n";
    }

    void addLocation(string name,string pincodes){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = getRestaurant(name);
        if(restaurant == NULL || (!restaurant->verifyUser(currentUser))){
            cout << "Restaurant doesn't exist.\n";
            return;
        }
        restaurant->addLocation(pincodes);
        cout << "Locations added successfully.\n";
    }

    void removeLocation(string name,string pincodes){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = getRestaurant(name);
        if(restaurant == NULL || (!restaurant->verifyUser(currentUser))){
            cout << "Restaurant doesn't exist.\n";
            return;
        }
        restaurant->removeLocation(pincodes);
        cout << "Locations removed successfully.\n";
    }

    void placeOrder(string name,int quantity){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = getRestaurant(name);
        if(restaurant == NULL){
            cout << "Restaurant doesn't exist.\n";
            return;
        }
        if(restaurant->placeOrder(quantity)){
            currentUser->addOrders(restaurant->getName(),quantity,(restaurant->getPrice()*quantity));
            cout << "Placed Order Successfully.\n";
        }else{
            cout << "Failed to place order.\n";
        }
    }

    void addReview(string name,int rating,string comment = ""){
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return;
        }
        Restaurant* restaurant = getRestaurant(name);
        if(restaurant == NULL){
            cout << "Restaurant doesn't exist.\n";
            return;
        }
        restaurant->addReview(currentUser,rating,comment);
    }

    vector<Restaurant*> showRestaurants(string parameter = ""){
        vector<Restaurant*> result;
        if(currentUser == NULL) {
            cout << "Not Logged In.\n";
            return result;
        }
        result = getNearbyRestaurants();
        if(parameter == "price"){
            sort(result.begin(),result.end(),[](Restaurant* a,Restaurant* b){
                return a->getPrice() < b->getPrice();
            });
        }else if(parameter == "rating"){
            sort(result.begin(),result.end(),[](Restaurant* a,Restaurant* b){
                return a->getRating() < b->getRating();
            });
        }
        return result;
    }

    void displayUsers(){
        for(int i=0;i<users.size();i++){
            users[i]->display();
        }
    }

    void displayRestaurants(vector<Restaurant*> result){
        for(int i=0;i<result.size();i++){
            result[i]->display();
        }
    }

    void displayAll(){
        for(int i=0;i<restaurants.size();i++){
            restaurants[i]->display();
        }
    }
};

int main(){

    Application app;

    app.registerUser("Pralove", "M", "phoneNumber-1", "HSR");
    app.registerUser("Nitesh", "M", "phoneNumber-2", "BTM");
    app.registerUser("Vatsal", "M",  "phoneNumber-3", "BTM");

    app.displayUsers();

    app.loginUser("phoneNumber-1");
    app.createRestaurant("Food Court-1", "BTM/HSR", "NI Thali", 100, 5);
    app.createRestaurant("Food Court-2", "BTM", "Burger", 120, 3);

    app.loginUser("phoneNumber-2");
    app.createRestaurant("Food Court-3", "HSR", "SI Thali", 150, 1);

    app.loginUser("phoneNumber-3");

    app.displayRestaurants(app.showRestaurants("price"));

    app.placeOrder("Food Court-1", 2);
    app.placeOrder("Food Court-2", 7);

    app.addReview("Food Court-2", 3, "Good Food");
    app.addReview("Food Court-1", 5, "nICE Food");

    app.displayRestaurants(app.showRestaurants("rating"));

    app.loginUser("phoneNumber-1");
    app.updateQuantity("Food Court-2", 5);
    app.addLocation("Food Court-2", "HSR");

    app.displayAll();
}
