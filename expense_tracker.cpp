#include <bits/stdc++.h>
using namespace std;

class User{

    string id;

    public:

    User() : id("") {}
    User(string n) : id(n){}

    void setId(string n){
        id = n;
    }

    string getId(){
        return id;
    }
};

class UserList{

    User* users;
    int length;
    map<pair<string,string>,double> amount;

    public:

    UserList(int len,string names[]):length(len){
        users = new User[len];
        for(int i=0;i<len;i++){
            users[i].setId(names[i]);
        }
        for(int i=0;i<len;i++){
            for(int j=i+1;j<len;j++){
                amount[{users[i].getId(),users[j].getId()}] = 0;
            }
        }
    }

    void display_allusers(){
        bool temp = false;
        for(auto const& pair : amount){
            if(pair.second != 0){
                temp = true;
                if(pair.second < 0)
                    cout << pair.first.second << " owes " << pair.first.first << " : " << -pair.second << "\n";
                else
                    cout << pair.first.first << " owes " << pair.first.second << " : " << pair.second << "\n";
            }
        }
        if(!(temp)){
            cout << "No Balances.\n";
        }
    }

    void display_user(string id){
        int in = getUserIndex(id);
        if(in == -1){
            cout << "User does not exist.\n";
            return;
        }
        bool temp = false;
        for(auto const& pair : amount){
            if(pair.second != 0 && (pair.first.first == id || pair.first.second == id)){
                temp = true;
                if(pair.second < 0)
                    cout << pair.first.second << " owes " << pair.first.first << " : " << -pair.second << "\n";
                else
                    cout << pair.first.first << " owes " << pair.first.second << " : " << pair.second << "\n";
            }
        }
        if(!(temp)){
            cout << "No Balances.\n";
        }
    }

    int getUserIndex(string id){
        int i;
        for(i=0;i<length;i++){
            if(users[i].getId() == id){
                return i;
            }
        }
        return -1;
    }

    void updateAmount(string user1,string user2,double amt){
        if(user1 != user2){
            if(amount.find({user1,user2}) == amount.end()){
                amount[{user2,user1}] += amt;
            }else{
                amount[{user1,user2}] -= amt;
            }
        }
    }

    void calcExpenses(string user,int len,string *split_users,double *non_eq_vals = NULL){
        int i;
        for(i = 0;i<len;i++){
            updateAmount(user,split_users[i],non_eq_vals[i]);
        }
    }
};

double round(double value, int decimal_places) {
    const double multiplier = std::pow(10.0, decimal_places);
    return std::ceil(value * multiplier) / multiplier;
}

int main(){

    string names[] = {"u1","u2","u3","u4"};
    UserList users(4,names);

    string input,temp,user,type;
    string *split;
    double *non_eq_vals;
    int index,len,i;
    double amount;

    while(true){
        getline(cin,input);
        index = input.find(" ");
        if(index != string::npos){
           temp = input.substr(0,index);
           if(temp == "SHOW"){
                temp = input.substr(index+1);
                users.display_user(temp);
           }else if(temp == "EXPENSE"){
                try{
                    input = input.substr(index+1);
                    index = input.find(" ");
                    user = input.substr(0,index);
                    input = input.substr(index+1);
                    index = input.find(" ");
                    amount = stod(input.substr(0,index));
                    input = input.substr(index+1);
                    index = input.find(" ");
                    len = stoi(input.substr(0,index));
                    split = new string[len];
                    for(i=0;i<len;i++){
                        input = input.substr(index+1);
                        index = input.find(" ");
                        split[i] = input.substr(0,index);
                    }
                    input = input.substr(index+1);
                    index = input.find(" ");
                    type = input.substr(0,index);
                    non_eq_vals = new double[len];
                    if(type != "EQUAL"){
                        for(i=0;i<len;i++){
                            input = input.substr(index+1);
                            index = input.find(" ");
                            if(type == "EXACT")
                                non_eq_vals[i] = stod(input.substr(0,index));
                            else
                                non_eq_vals[i] = round(stod(input.substr(0,index))/100*amount,2);
                        }
                    }else{
                        for(i=0;i<len;i++){
                            non_eq_vals[i] = round(amount/len,2);
                        }
                    }
                    users.calcExpenses(user,len,split,non_eq_vals);
                }catch(...){
                    cout << "Wrong Input.\n";
                }
           }else{
                cout << "Wrong Input.\n";
           }
        }else{
            if(input == "SHOW"){
                users.display_allusers();
            }else{
                cout << "Wrong Input.\n";
            }
        }
    }
}
