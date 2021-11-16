#include <bits/stdc++.h>
using namespace std;

class Player{

    int pos;
    string name;

    public:

    Player() : name(""),pos(0){}
    Player(string n) : name(n),pos(0){}

    bool moveForward(int roll){
        if(pos+roll > 100){
            cout <<"Invalid roll (" << roll << "). " << name << " needs a perfect roll to win the game.\n";
        }else{
            pos += roll;
            cout << name << " rolled a " << roll << " to move from " << pos-roll << " to " << pos << ".\n";
            if(pos == 100){
                cout << "\n\nCongrats. " << name << " has won the game.\n";
                return true;
            }
        }
        return false;
    }

    int getPos(){
        return pos;
    }

    bool setPos(int p,int type){
        if(type){
            cout << name << " slid down a snake at " << pos << " to get to " << p << ".\n";
        }else{
            cout << name << " climbed a ladder at " << pos << " to get to " << p << ".\n";
        }
        pos = p;
        if(p == 100){
            return true;
        }
        return false;
    }

    void setName(string n){
        name = n;
    }

};

class Snakes{

    map<int,int> snakes;

    public:

    void insertSnake(int start,int stop){
        snakes[start] = stop;
    }

    void printSnakes(){
        for(auto const& pair : snakes){
            cout << pair.first  << " : " << pair.second << "\n";
        }
    }

    int isSnakeStart(int pos){
        if(snakes.find(pos) == snakes.end()){
            return -1;
        }
        return snakes[pos];
    }

};

class Ladders{

    map<int,int> ladders;

    public:

    void insertLadder(int start,int stop){
        ladders[start] = stop;
    }

    void printLadders(){
        for(auto const& pair : ladders){
            cout << pair.first  << " : " << pair.second << "\n";
        }
    }

    int isLadderStart(int pos){
        if(ladders.find(pos) == ladders.end()){
            return -1;
        }
        return ladders[pos];
    }

};

int main(){
    int i,num,roll,start,stop;
    string temp;
    srand(time(0));
    cout << "Enter number of snakes: ";
    cin >> num;
    Snakes snake;
    for(i=0;i<num;i++){
        cin >> start;
        cin >> stop;
        snake.insertSnake(start,stop);
    }
    cout << "Enter number of ladders: ";
    cin >> num;
    Ladders ladder;
    for(i=0;i<num;i++){
        cin >> start;
        cin >> stop;
        ladder.insertLadder(start,stop);
    }
    cout << "Enter number of players: ";
    cin >> num;
    Player *players = new Player[num];
    for(i=0;i<num;i++){
        cout << "Enter name for Player " << i+1 <<" : ";
        cin >> temp;
        players[i].setName(temp);
    }
    cout << "\nThe game has now started. \n";
    i=0;
    while(true){
        int roll = rand()%6 + 1;
        if(players[i%num].moveForward(roll)){
            break;
        }
        start = players[i%num].getPos();
        stop = ladder.isLadderStart(start);
        if(stop != -1){
            if(players[i%num].setPos(stop,0)){
                break;
            }
        }
        start = players[i%num].getPos();
        stop = snake.isSnakeStart(start);
        if(stop != -1){
            if(players[i%num].setPos(stop,1)){
                break;
            }
        }
        i++;
    }
}
