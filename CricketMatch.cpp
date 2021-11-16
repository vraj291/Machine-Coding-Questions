#include <bits/stdc++.h>
using namespace std;

class Batsman{

    int score;
    int balls;
    int sixes;
    int fours;
    bool isPlaying;

    public:

    Batsman():score(0),balls(0),fours(0),sixes(0),isPlaying(false){}

    void updateScore(int runs){
        score += runs;
        balls++;
        if(runs == 4){
            fours++;
        }else if(runs == 6){
            sixes++;
        }
    }

    void setPlaying(){
        isPlaying = true;
    }

    void out(){
        balls++;
        isPlaying = false;
    }

    void displayBatting(){
        if(isPlaying) cout << "*";
        cout << " " << score << " " << balls << " " << fours << " " << sixes << " ";
        if(balls == 0){
            cout << 0;
        }else{
            cout << fixed <<setprecision(2) << (float)score/balls*100;
        }
        cout << "\n";
    }
};

class Player : public Batsman {

    string name;

    public:

    Player(string name):Batsman(){
        this->name = name;
    }

    void display(int type = 0){
        cout << name;
        if(type == 0){
            displayBatting();
        }else{

        }
    }
};

class BattingTeam{

    int score;
    int wickets;
    int extras;
    int onStrike;
    int otherEnd;
    int ballCount;
    int playerCount;

    public:

    BattingTeam() {}

    BattingTeam(int playerCount):score(0),wickets(0),extras(0),ballCount(0),onStrike(0),otherEnd(1){
        this->playerCount = playerCount;
    }

    void changeStrike(){
        int temp = onStrike;
        onStrike = otherEnd;
        otherEnd = temp;
    }

    void updateBattingScore(int runs){
        score += runs;
    }

    bool updateBattingWickets(){
        wickets++;
        if(wickets >= playerCount-1){
            return true;
        }
        return false;
    }

    int getOnStrike(){
        return onStrike;
    }

    void updateOnStrike(){
        onStrike = max(onStrike+1,otherEnd+1);
    }

    void updateBallCount(){
        ballCount++;
    }

    bool checkForOver(){
        if(ballCount == 6){
            ballCount = 0;
            return true;
        }else{
            return false;
        }
    }

    void checkExtraRuns(string input){
        int in = input.find("+");
        if(in == -1){
            extras++;
            score++;
        }else{
            in = stoi(input.substr(in+1));
            extras += in;
            score += in;
        }
    }

    int getScore(){
        return score;
    }

    void displayExtraBattingInfo(){
        cout << "Total : " << score << "/" << wickets << "\n";
        cout << "Extras : " << extras << "\n\n";
    }

};

class Team : public BattingTeam{

    vector<Player> players;

    public:

    Team():BattingTeam(){}

    Team(vector<string> players):BattingTeam(players.size()){
        for(int i=0;i<players.size();i++){
            this->players.push_back(Player(players[i]));
        }
    }

    void gameOver(){

    }

    void initializeBatting(){
        players[0].setPlaying();
        players[1].setPlaying();
    }

    void addBattingScore(int runs){
        updateBattingScore(runs);
        players[getOnStrike()].updateScore(runs);
    }

    bool changeBattingPlayer(){
        players[getOnStrike()].out();
        if(updateBattingWickets()){
            return false;
        }
        updateOnStrike();
        players[getOnStrike()].setPlaying();
        return true;
    }

    int processBattingBall(string ball){
        if(ball == "W"){
            updateBallCount();
            if(!changeBattingPlayer()){
                return 0;
            }
        }else if(ball.find("Wd") == 0 || ball.find("Nb") == 0){
            checkExtraRuns(ball);
        }else{
            updateBallCount();
            int runs = stoi(ball);
            addBattingScore(runs);
            if(runs%2 == 1){
                changeStrike();
            }
        }
        if(checkForOver()){
            changeStrike();
            displayBatting();
            return 2;
        }
        return 1;
    }

     void displayBatting(){
        cout << "Name Score Balls 4s 6s SR\n";
        for(int i=0;i<players.size();i++){
            players[i].display();
        }
        displayExtraBattingInfo();
    }

};

class BowlingTeam {

};

class Game{

    int overs;
    int playerCount;
    Team teams[2];
    int score;
    int innings = 0;

    public:

    Game(int overs,int playerCount,vector<string> team1,vector<string> team2){
        this->overs = overs;
        this->playerCount = playerCount;
        teams[0] = Team(team1);
        teams[1] = Team(team2);
        score = INT_MAX;
    }

    void changeInnings(){
        innings = !innings;
        playInnings();
    }

    void playInnings(){
        int overNo = 0,temp;
        string input;
        cout << "Over " << overNo+1 << " : \n";
        teams[innings].initializeBatting();
        while(true){
            cin >> input;
            temp = teams[innings].processBattingBall(input);
            if(temp == 2){
                overNo++;
                if(overNo == overs){
                    cout << "Innings Over.\n";
                    break;
                }
                cout << "Over " << overNo+1 << " : \n";
            }else if(temp == 0){
                teams[innings].displayBatting();
                cout << "No Wickets Left.\n";
                break;
            }else if(innings == 1 && score < teams[innings].getScore()){
                teams[innings].displayBatting();
                cout << "Team 2 won.";
                return;
            }
        }
        if(innings == 1){
           if(score < teams[innings].getScore()){
                cout << "Team 2 won.";
           }else{
                cout << "Team 1 won.";
           }
           return;
        }else{
            score = teams[innings].getScore();
        }
        changeInnings();
    }
};

int main(){

    vector<string> names1 = {"p1","p2","p3","p4","p5"};
    vector<string> names2 = {"p6","p7","p8","p9","p10"};
    Game g1(2,5,names1,names2);
    g1.playInnings();
}
