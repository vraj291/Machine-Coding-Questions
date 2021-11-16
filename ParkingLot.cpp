#include <bits/stdc++.h>
using namespace std;

enum vehicleType {TRUCK,BIKE,CAR};
string vehicleTypeNames[] = {"TRUCK","BIKE","CAR"};
map<string,vehicleType> vehicleTypeMap = {{"TRUCK",TRUCK},{"BIKE",BIKE},{"CAR",CAR}};

class Ticket{

    string id;

    public:

    Ticket(string id){
        this->id = id;
    }

    string getId(){
        return id;
    }

    int getFloorNo(){
        try{
            int index1 = id.find('_');
            int index2 = id.find('_',index1+1);
            return stoi(id.substr(index1+1,index2-index1-1));
        }catch(...){
            return -1;
        }
    }

    int getSlotNo(){
        try{
            int index1 = id.find('_');
            int index2 = id.find('_',index1+1);
            return stoi(id.substr(index2+1));
        }catch(...){
            return -1;
        }
    }

};

class Vehicle{

    string regNo,color;
    vehicleType type;

    public:

    Vehicle(vehicleType type,string regNo,string color){
        this->regNo = regNo;
        this->color = color;
        this->type = type;
    }

    string getRegNo(){
        return regNo;
    }

    string getColor(){
        return color;
    }

    vehicleType getType(){
        return type;
    }
};

class ParkingSlot{

    vehicleType type;
    int slotNo;
    Vehicle* vehicle = NULL;

    public:

    ParkingSlot(){}

    ParkingSlot(int slotNo,vehicleType type){
        this->slotNo = slotNo;
        this->type = type;
    }

    void parkVehicle(Vehicle* vehicle){
        this->vehicle = vehicle;
    }

    Vehicle* unparkVehicle(){
        Vehicle* temp = vehicle;
        vehicle = NULL;
        return temp;
    }

    vehicleType getType(){
        return type;
    }

    int getSlotNo(){
        return slotNo;
    }

    bool isEmpty(){
        return vehicle == NULL;
    }
};

class ParkingFloor{

    int floorNo;
    int slotCount;
    vector<priority_queue<int, vector<int>, greater<int>>> available;
    vector<vector<int>> occupied;
    map<int,ParkingSlot> slots;

    void createParkingSlots(int vehicleCount,int typeCount[]){
        int cnt = 1;
        for(int i=0;i<vehicleCount;i++){
            occupied.push_back(vector<int>());
            priority_queue<int, vector<int>, greater<int>> temp;
            for(int j=1;j<=typeCount[i];j++){
                slots[cnt]= ParkingSlot(cnt,vehicleType(i));
                temp.push(cnt);
                cnt++;
            }
            available.push_back(temp);
        }
    }

    public:

    ParkingFloor(int floorNo,int slotCount,int vehicleCount,int typeCount[]){
        this->floorNo = floorNo;
        this->slotCount = slotCount;
        createParkingSlots(vehicleCount,typeCount);
    }

    int getAvailableSlot(int type){
        if(available[type].size() == 0) return -1;
        int slotNo = available[type].top();
        available[type].pop();
        occupied[type].push_back(slotNo);
        return slotNo;
    }

    int parkVehicle(Vehicle* vehicle){
        int currType = (int)vehicle->getType();
        int allotedSlot = getAvailableSlot(currType);
        if(allotedSlot != -1){
            slots[allotedSlot].parkVehicle(vehicle);
            return allotedSlot;
        }
        return -1;
    }

    Vehicle* unparkVehicle(int slotNo){
        if(slots[slotNo].isEmpty()) return NULL;
        Vehicle* vehicle = slots[slotNo].unparkVehicle();
        int currType = (int)vehicle->getType();
        available[currType].push(slotNo);
        occupied[currType].erase(remove(occupied[currType].begin(),occupied[currType].end(),slotNo));
        return vehicle;
    }

    void printFreeSlotsCount(vehicleType type){
        int i = (int)type;
        cout << "No. of free slots for " <<vehicleTypeNames[i] <<  " on Floor " << floorNo << " : " << available[i].size() << "\n";
    }

    void printFreeSlots(vehicleType type){
        int i = (int)type;
        cout << "Free slots for " <<vehicleTypeNames[i] <<  " on Floor " << floorNo << " : " ;
        if(available[i].size() == 0){
            cout << " - \n";
        }else{
            priority_queue<int, vector<int>, greater<int>> temp =  available[i];
            while (temp.size() > 1) {
                cout << temp.top() << ", ";
                temp.pop();
            }
            cout << temp.top() << "\n";
        }
    }

    void printOccupiedSlots(vehicleType type){
        int i = (int)type;
        cout << "Occupied slots for " << vehicleTypeNames[i] <<  " on Floor " << floorNo << " : " ;
        if(occupied[i].size() == 0){
            cout << " - \n";
        }else{
            for(int j=0;j<occupied[i].size()-1;j++){
                cout << occupied[i][j] << ", ";
            }
            cout << occupied[i][occupied[i].size()-1] << "\n";
        }
    }
};

class ParkingLot{

    string name;
    int floorCount;
    vector<ParkingFloor> floors;

    public:

    ParkingLot(){}

    ParkingLot(string name,int floorCount,int slotCount,int vehicleCount,int typeCount[]){
        this->name = name;
        this->floorCount = floorCount;
        for(int i=1;i<=floorCount;i++){
            floors.push_back(ParkingFloor(i,slotCount,vehicleCount,typeCount));
        }
        cout << "Created parking lot with " << floorCount << " floors and " << slotCount << " slots per floor.\n";
    }

    Ticket* parkVehicle(Vehicle* vehicle){
        int temp;
        for(int i=0;i<floorCount;i++){
            temp = floors[i].parkVehicle(vehicle);
            if(temp != -1){
                string result = name + '_' + to_string(i+1) + '_' + to_string(temp);
                return new Ticket(result);
            }
        }
        return NULL;
    }

    Vehicle* unparkVehicle(Ticket* ticket){
        int floorNo = ticket->getFloorNo();
        int slotNo = ticket->getSlotNo();
        if(floorNo == -1 || slotNo == -1) return NULL;
        Vehicle* vehicle = floors[floorNo-1].unparkVehicle(slotNo);
        return vehicle;
    }

    void printFreeSlotsCount(vehicleType type){
        for(int i=0;i<floors.size();i++){
            floors[i].printFreeSlotsCount(type);
        }
    }

    void printFreeSlots(vehicleType type){
        for(int i=0;i<floors.size();i++){
            floors[i].printFreeSlots(type);
        }
    }

    void printOccupiedSlots(vehicleType type){
        for(int i=0;i<floors.size();i++){
            floors[i].printOccupiedSlots(type);
        }
    }
};

vector<string> processQuery(string input){
    vector<string> result;
    int start = 0;
    int end = input.find(" ");
    while(end != string::npos){
        result.push_back(input.substr(start,end-start));
        start = end+1;
        end = input.find(" ",end+1);
    }
    result.push_back(input.substr(start));
    return result;
}


int main(){

    int typeCount[] = {1,2,3};
    string input;
    ParkingLot* p1;
    while(true){
        getline(cin,input);
        vector<string> parameters = processQuery(input);
        if(parameters[0] == "create_parking_lot"){
            p1 = new ParkingLot(parameters[1],stoi(parameters[2]),stoi(parameters[3]),3,typeCount);
        }else if(parameters[0] == "display"){
            vehicleType type = vehicleTypeMap[parameters[2]];
            if(parameters[1] == "free_count"){
                p1->printFreeSlotsCount(type);
            }else if(parameters[1] == "free_slots"){
                p1->printFreeSlots(type);
            }else if(parameters[1] == "occupied_slots"){
                p1->printOccupiedSlots(type);
            }
        }else if(parameters[0] == "park_vehicle"){
            vehicleType type = vehicleTypeMap[parameters[1]];
            Vehicle vehicle(type,parameters[2],parameters[3]);
            Ticket* t1 = p1->parkVehicle(&vehicle);
            if(t1 == NULL){
                cout << " Parking Lot Full.\n";
            }else{
                cout << " Parked vehicle. Ticket ID: " << t1->getId() << "\n";
            }
        }else if(parameters[0] == "unpark_vehicle"){
            Ticket t1(parameters[1]);
            Vehicle* vehicle = p1->unparkVehicle(&t1);
            if(vehicle == NULL){
                cout << "Invalid Ticket.\n";
            }else{
                cout << "Unparked vehicle with Registration Number: " << vehicle->getRegNo() << " and Color: " << vehicle->getColor() << "\n";
            }
        }else if(parameters[0] == "exit"){
            break;
        }else{
            cout << "Wrong Input.\n";
        }
    }

}
