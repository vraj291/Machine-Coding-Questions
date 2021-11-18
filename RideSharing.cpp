#include <bits/stdc++.h>
using namespace std;

class Vehicle{

    string regNo;
    string type;

    public:

    Vehicle(string regNo,string type){
        this->regNo = regNo;
        this->type = type;
    }

    bool isEqual(string regNo,string type){
        return (this->type == type && this->regNo == regNo);
    }

    string getType(){
        return type;
    }
};

class User{

    string name;
    string gender;
    int age;
    int fuelSaved;
    int takenCount;
    int offerCount;
    vector<Vehicle*> vehicles;

    public:

    User(string name,string gender,int age):fuelSaved(0),offerCount(0),takenCount(0){
        this->name = name;
        this->gender = gender;
        this->age = age;
    }

    void addVehicle(string regNo,string type){
        vehicles.push_back(new Vehicle(regNo,type));
    }

    bool compareName(string name){
        return this->name == name;
    }

    Vehicle* getVehicle(string regNo,string type){
        for(int i=0;i<vehicles.size();i++){
            if(vehicles[i]->isEqual(regNo,type)){
                return vehicles[i];
            }
        }
        return NULL;
    }

    void updateSavedFuel(int duration){
        fuelSaved += duration;
    }

    void addRideOffer(){
        offerCount++;
    }

    void removeRideOffer(){
        offerCount--;
    }

    void addRideTaken(){
        takenCount++;
    }

    void removeRideTaken(){
        takenCount--;
    }

    void display(){
        cout << "Name : " << name << "\n";
        cout << "Taken : " << takenCount << "\n";
        cout << "Offered : " << offerCount << "\n";
        cout << "Fuel Saved : " << fuelSaved << "\n";
    }
};

class Ride{

    User* user;
    Vehicle* vehicle;
    string origin;
    string startTime;
    string destination;
    int availableSeats;
    int occupiedSeats;
    int duration;
    bool active;
    vector<User*> passengers;

    public:

    Ride(User* user,Vehicle* vehicle,string origin,string startTime,string destination,int seats,int duration):active(true),occupiedSeats(0),availableSeats(seats){
        this->user = user;
        this->vehicle = vehicle;
        this->origin = origin;
        this->startTime = startTime;
        this->destination = destination;
        this->duration = duration;
    }

    bool compare(User* user,Vehicle* vehicle){
        return (this->user == user && this->vehicle == vehicle && this->active);
    }

    void setUnactive(){
        active = false;
    }

    int getVacancy(){
        return availableSeats;
    }

    int getDuration(){
        return duration;
    }

    string getVehicleType(){
        return vehicle->getType();
    }

    bool canBook(string origin,string destination,int seats){
        return (this->origin == origin && this->destination == destination && this->availableSeats >= seats);
    }

    void book(User* user,int seats){
        passengers.push_back(user);
        user->addRideTaken();
        user->updateSavedFuel(duration);
        availableSeats -= seats;
        occupiedSeats += seats;
    }
};

class Application{

    vector<User*> users;
    vector<Ride*> rides;

    User* getUser(string name){
        for(int i=0;i<users.size();i++){
            if(users[i]->compareName(name)){
                return users[i];
            }
        }
        return NULL;
    }

    bool isValidRide(User* user,Vehicle* vehicle){
        for(int i = 0;i<rides.size();i++){
            if(rides[i]->compare(user,vehicle)){
                return false;
            }
        }
        return true;
    }

    Ride* selectMostVacantRide(string origin,string destination,int seats){
        int max = INT_MIN,temp;
        Ride* result = NULL;
        for(int i = 0;i<rides.size();i++){
            temp = rides[i]->getVacancy();
            if(rides[i]->canBook(origin,destination,seats) && temp > max){
                max = temp;
                result = rides[i];
            }
        }
        return result;
    }

    Ride* selectFastestRide(string origin,string destination,int seats){
        int min = INT_MAX,temp;
        Ride* result = NULL;
        for(int i = 0;i<rides.size();i++){
            temp = rides[i]->getDuration();
            if(rides[i]->canBook(origin,destination,seats) && temp < min){
                min = temp;
                result = rides[i];
            }
        }
        return result;
    }

     Ride* selectPreferredVehicleRide(string origin,string destination,int seats,string type){
        string temp;
        for(int i = 0;i<rides.size();i++){
            temp = rides[i]->getVehicleType();
            if(rides[i]->canBook(origin,destination,seats) && temp == type){
                return rides[i];
            }
        }
        return NULL;
    }

    public:

    Application(){}

    void addUser(string name,string gender,int age){
        users.push_back(new User(name,gender,age));
    }

    void addVehicle(string name,string regNo,string type){
        User* user = getUser(name);
        if(user != NULL){
            user->addVehicle(regNo,type);
        }
    }

    void offerRide(string name,string regNo,string type,string origin,string startTime,string destination,int seats,int duration){
        User* user = getUser(name);
        if(user == NULL) return;
        Vehicle* vehicle = user->getVehicle(regNo,type);
        if(vehicle == NULL) return;
        if(isValidRide(user,vehicle)){
            rides.push_back(new Ride(user,vehicle,origin,startTime,destination,seats,duration));
            user->addRideOffer();
        }
    }

    void endRide(string name,string regNo,string type){
        User* user = getUser(name);
        if(user == NULL) return;
        Vehicle* vehicle = user->getVehicle(regNo,type);
        if(vehicle == NULL) return;
        for(auto it = rides.begin(); it != rides.end(); ++it){
            if((*it)->compare(user,vehicle)){
                (*it)->setUnactive();
                return;
            }
        }
    }

    void selectRide(string name,string origin,string destination,int seats,string strategy,string type = ""){
        User* user = getUser(name);
        if(user == NULL) return;
        Ride* result = NULL;
        if(strategy == "Most Vacant"){
            result = selectMostVacantRide(origin,destination,seats);
        }else if(strategy == "Fastest Ride"){
            result = selectFastestRide(origin,destination,seats);
        }else if(strategy == "Preferred Vehicle"){
            result = selectPreferredVehicleRide(origin,destination,seats,type);
        }
        if(result != NULL){
            result->book(user,seats);
        }
    }

    void displayUsers(){
        for(int i=0;i<users.size();i++){
            users[i]->display();
            cout << "\n";
        }
        cout << "\n";
    }

};

int main(){

    Application app;

    app.addUser("Rohan", "M", 36);
    app.addVehicle("Rohan","KA-01-12345","Swift");
    app.addUser("Shashank", "M", 29);
    app.addVehicle("Shashank","TS-05-62395","Baleno");
    app.addUser("Nandini", "F", 36);
    app.addUser("Shipra", "F", 36);
    app.addVehicle("Shipra","TS-05-62395","Polo");
    app.addVehicle("Shipra","TS-05-62395","Activa");
    app.addUser("Gaurav", "M", 36);
    app.addUser("Rahul", "M", 36);
    app.addVehicle("Rahul","KA-05-1234","XUV");

    app.offerRide("Rohan","KA-01-12345","Swift","Hyderabad","Sat 25 Jun 08:20 AM","Bangalore",1,13);
    app.offerRide("Shipra","TS-05-62395","Activa","Bangalore","Sat 25 Jun 08:20 AM","Mysore",1,10);
    app.offerRide("Shipra","TS-05-62395","Polo","Bangalore","Sat 25 Jun 08:20 AM","Mysore",2,4);
    app.offerRide("Shashank","TS-05-62395","Baleno","Hyderabad","Sat 25 Jun 08:20 AM","Bangalore",2,14);
    app.offerRide("Rahul","KA-05-1234","XUV","Hyderabad","Sat 25 Jun 08:20 AM","Bangalore",5,10);

    app.endRide("Rohan","KA-01-12345","Swift");
    app.offerRide("Rohan","KA-01-12345","Swift","Bangalore","Sat 25 Jun 08:20 AM","Pune",1,13);

    app.selectRide("Nandini","Bangalore", "Mysore", 1, "Most Vacant");
    app.selectRide("Gaurav","Bangalore", "Mysore", 1, "Preferred Vehicle","Activa");
    app.selectRide("Shashank","Mumbai", "Bangalore", 1, "Preferred Vehicle","Baleno");
    app.selectRide("Rohan","Hyderabad", "Bangalore", 1, "Fastest Ride");
    app.selectRide("Shashank","Hyderabad", "Bangalore", 1, "Preferred Vehicle","Polo");

    app.displayUsers();
}
