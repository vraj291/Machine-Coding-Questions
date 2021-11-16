#include <bits/stdc++.h>
using namespace std;

class User{

    string name;
    string uid;
    string email;

    public:

    User(string a,string b,string c) : name(b),uid(a),email(c){}

    string to_string(){
        string temp = "{ ";
        temp += "\"id\" : \"";
        temp += uid;
        temp += "\", \"name\" : \"";
        temp += name;
        temp += "\", \"email\" : \"";
        temp += email;
        temp += "\" }";
        return temp;
    }
};

string createId(){
    string temp;
    srand(time(0));
    string chars = "abcdefghijklmnopqrstuvwxyz1234567890";
    for(int i=0;i<24;i++){
        temp += chars.at((rand()%chars.length()));
    }
    return temp;
}

class Card{

    string name;
    string id;
    string description;
    string assignedTo;
    string listId;

    public:

    Card(string a,string b) : name(a),listId(b),description(""),assignedTo(""){
        id = createId();
    }

    string getListId(){
        return listId;
    }

    string getId(){
        return id;
    }

    void setName(string n){
        name = n;
    }

    void setDesc(string n){
        description = n;
    }

    void setAssignee(string n){
        assignedTo = n;
    }

    string to_string(){
        string temp = "{ ";
        temp += "\"id\" : \"";
        temp += id;
        temp += "\", \"name\" : \"";
        temp += name;
        if(description != ""){
            temp += "\", \"description\" : \"";
            temp += description;
        }
        if(assignedTo != ""){
            temp += "\", \"assignedTo\" : \"";
            temp += assignedTo;
        }
        temp += "\" }";
        return temp;
    }

};

class CardList{

    vector<Card> cards;

    public:

    CardList(){}

    void addCard(string n,string id){
        cards.push_back(Card(n,id));
    }

    Card getCardbyId(string id){
        for(int i=0;i<cards.size();i++){
            if(cards[i].getId() == id){
                return cards[i];
            }
        }
        return NULL;
    }

    vector<string> getCardsbyListId(string listId){
        vector<string> result;
        for(int i=0;i<cards.size();i++){
            if(cards[i].getListId() == listId){
                result.push_back(cards[i].to_string());
            }
        }
        return result;
    }

};

class Board{

    string name;
    string id;
    string privacy = "PUBLIC";

    public:

    Board(string a) : name(a){
        id = createId();
    }

    void setName(string n){
        name = n;
    }

    void setPrivacy(string n){
        privacy = n;
    }

    string to_string(){
        string temp = "{ ";
        temp += "\"id\" : \"";
        temp += id;
        temp += "\", \"name\" : \"";
        temp += name;
        temp += "\", \"privacy\" : \"";
        temp += privacy;
        temp += "\" }";
        return temp;
    }
};

class List{

    string name;
    string id;
    vector<string> cards;

    public:

    List(string a) : name(a){
        id = createId();
    }

    void setName(string n){
        name = n;
    }

    void addCard(string n){
        cards.push_back(n);
    }

    string to_string(vector<string> cards){
        string temp = "{ ";
        temp += "\"id\" : \"";
        temp += id;
        temp += "\", \"name\" : \"";
        temp += name;
        temp += "\", \"cards\" : [";
        for(int i=0;i<cards.size();i++){
            temp += cards[i];
        }
        temp += "] }";
        return temp;
    }
};



int main(){
    User u1("user1","Random","@email.com");
    cout << u1.to_string() << "\n";
    Board b1("work");
    cout << b1.to_string();
}
