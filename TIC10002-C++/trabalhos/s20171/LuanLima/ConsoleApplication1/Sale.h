//
// Created by luan on 5/22/17.
//


#include <iostream>


class Sale {




public:

    Sale(std::string branch, std::string date, int saler_code, int total_sold) :
            branch(branch), date(date), seller_code(saler_code), total_sold(total_sold){
        year = parseYear(this->date);
        month = parseMonth(this->date);
    }

    Sale() : branch(""), date(""), seller_code(0), total_sold(0){}

    std::string getBranch() const{
        return branch;
    }

    int getYear() const{
        return year;
    }

    int getMonth() const{
        return month;
    }

    int getSeller_code() const{
        return seller_code;
    }

    int getTotal_sold() const{
        return total_sold;
    }


    std::string getDate(){
        return date;
    }

    void print(){
        std::cout << branch << ", " << date << ", " << seller_code << ", " << total_sold << std::endl;
    }


    int parseYear(std::string date) {
        std::string::iterator it = date.begin();
        std::string year_str = "";
        while(it != date.end() && *it != '/'){
            year_str += *it;
            ++it;
        }
        return std::stoi(year_str);

    }

    int parseMonth(std::string date){

        std::string::iterator it = date.end()-1;
        std::string month_str = "";
        while(it != date.end() && *it != '/'){
            month_str = char(*it) + month_str;
            --it;
        }

        if(month_str == "jan"){
            return 1;
        }else if(month_str == "fev"){
            return 2;
        }else if(month_str == "mar"){
            return 3;
        }else if(month_str == "abr") {
            return 4;
        }else if(month_str == "mai") {
            return 5;
        }else if(month_str == "jun") {
            return 6;
        }else if(month_str == "jul") {
            return 7;
        }else if(month_str == "ago") {
            return 8;
        }else if(month_str == "set") {
            return 9;
        }else if(month_str == "out") {
            return 10;
        }else if(month_str == "nov") {
            return 11;
        }else if(month_str == "dez") {
            return 12;
        }else{
            return 0; //INPUT ERROR
        }


    }

private:
    std::string branch, date;
    int year, month, seller_code, total_sold;


};


