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

class BookCopy{

    string copyId;
    int rackNo;
    User* user = NULL;
    string dueDate = "";

    public:

    BookCopy(string copyId,int rackNo){
        this->copyId = copyId;
        this->rackNo = rackNo;
    }

    string getCopyId(){
        return copyId;
    }

    string getRackNo(){
        return rackNo;
    }

    void borrow(User* user,string dueDate){
        this->user = user;
        this->dueDate = dueDate;
    }

    bool isAvailable(){
        return user == NULL;
    }
};

class Book{

    string bookId;
    string name;
    vector<string> authors;
    vector<string> publishers;
    vector<BookCopy> copies;

    public:

    Book(string id,string name,string authors,string publishers,string copies):bookId(id){
        this->name = name;
        this->authors = processQuery(authors,",");
        this->publishers = processQuery(publishers,",");
        vector<string> bookCopies = processQuery(copies);
        for(int i=0;i<bookCopies.size();i++){
            this->copies.push_back(BookCopy(bookCopies[i],i+1));
        }
    }

    void addBookCopy(string copies){
        vector<string> bookCopies = processQuery(copies);
        int newPointer = 0,copyPointer = 0;
        while(cnt < bookCopies.size()){
            while(copyPointer < copies.size() && copies[copyPointer] != NULL){
                copyPointer++;
            }
            if(copyPointer == copies.size()){

            }
            cnt++;
        }
    }

    int borrowBook(User* user,string dueDate){
        for(int i=0;i<copies.size();i++){
            if(copies[i].isAvailable()){
                copies[i].borrow(user,dueDate);
                return copies[i].getRackNo();
            }
        }
        return -1;
    }

    int returnBook(User* user,string dueDate){
        for(int i=0;i<copies.size();i++){
            if(copies[i].isAvailable()){
                copies[i].borrow(user,dueDate);
                return copies[i].getRackNo();
            }
        }
        return -1;
    }
};



class Rack{

    vector<BookCopy> books;

    public:

    Rack(){}

    bool addBook(BookCopy book){
        if(hasBookCopy(book.getCopyId())){
            return false;
        }
        this.books.push_back(book);
        return true;
    }

    bool hasBookCopy(string copyId){
        for(int i=0;i<books.size();i++){
            if(books[i].getCopyId() == copyId){
                return true;
            }
        }
        return false;
    }
};

class Library{

    string name;
    vector<Book> books;
    vector<Rack> racks;

    public:

    Library(string name,int racks){
        this->name = name;
        for(int i=0;i<racks;i++){
            racks.push_back(Rack());
        }
    }

    void addBook(string bookId,string name,string authors,string publishers,string bookCopies){
        Book temp(bookId,name,authors,publishers);
        books.push_back(temp);
        vector<string> copies = processQuery(bookCopies);
        for()
    }
};
