#include <bits/stdc++.h>
using namespace std;

class Player{

    string name;
    char token;
    int moves;

    public:

    Player() : moves(0){}
    Player(string n, char c) : name(n),token(c),moves(0){}

    string getName(){
        return name;
    }

    void setName(string n){
        name = n;
    }

    void incMoves(){
        moves++;
    }

    int getMoves(){
        return moves;
    }

    void setToken(char n){
        token = n;
    }

    char getToken(){
        return token;
    }

};

class Board{

    char board[3][3];
    Player p1;
    Player p2;
    int point = 1;
    bool over = false;

    public:

    Board(string a,string b){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                board[i][j] = '-';
            }
        }
        p1.setName(a);
        p1.setToken('X');
        p2.setName(b);
        p2.setToken('O');
    }

    void displayBoard(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                cout << board[i][j] << " ";
            }
            cout << "\n";
        }
    }

    void playMove(int a,int b){
        if(over){
            cout << "Game is already over. Enter exit.\n";
        }else if(a > 3 || a < 1 || b > 3 || b < 1){
            cout << "Wrong Input.\n";
        }else if(board[a-1][b-1] != '-'){
             cout << "Invalid Move.\n";
        }else{
            if(point){
                board[a-1][b-1] = p1.getToken();
                p1.incMoves();
            }else{
                board[a-1][b-1] = p2.getToken();
                p2.incMoves();
            }
            displayBoard();
            checkBoard();
            point = !point;
        }
    }

    void checkBoard(){
        if(p1.getMoves() + p2.getMoves() == 9){
            cout << "Game Over.\n";
            cout << "It has ended in a draw.\n";
            over = true;
        }
        if(point){
            if(p1.getMoves() < 3){
                return;
            }else{
                if(evaluateWin(p1.getToken())){
                    cout << "Game Over.\n";
                    cout << p1.getName() << " has won the game.\n";
                    over = true;
                }
            }
        }else{
            if(p2.getMoves() < 3){
                return;
            }else{
                if(evaluateWin(p2.getToken())){
                    cout << "Game Over.\n";
                    cout << p2.getName() << " has won the game.\n";
                    over = true;
                }
            }
        }
    }

    bool evaluateWin(char c){
        int i;
        if(board[0][0] == board[1][1] &&  board[1][1] == board[2][2] && board[2][2] == c){
            return true;
        }
        if(board[2][0] == board[1][1] &&  board[1][1] == board[0][2] && board[0][2] == c){
            return true;
        }
        for(i=0;i<3;i++){
            if(board[i][0] == board[i][1] &&  board[i][1] == board[i][2] && board[i][2] == c){
                return true;
            }
            if(board[0][i] == board[1][i] &&  board[1][i] == board[2][i] && board[2][i] == c){
                return true;
            }
        }
        return false;
    }
};

int main(){
    string input;
    getline(cin,input);
    string p1 = input.substr(2);
    getline(cin,input);
    string p2 = input.substr(2);
    int point = 0;
    char token;
    Board board(p1,p2);
    board.displayBoard();
    while(true){
        getline(cin,input);
        if(input == "exit"){
            break;
        }else{
            board.playMove((int)(input.at(0))-48,(int)(input.at(2))-48);
        }
    }
}
