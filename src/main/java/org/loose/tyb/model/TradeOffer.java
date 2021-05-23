package org.loose.tyb.model;

public class TradeOffer {
    String persoana1;
    String persoana2;
    String book1;
    String book2;
    int pending; // -1 = declined, 0 = pending, 1 = accepted

    public TradeOffer(){

    }

    public TradeOffer(String p1, String p2, String b1, String b2, int pending){
        this.persoana1 = p1;
        this.persoana2 = p2;
        this.book1 = b1;
        this.book2 = b2;
        this.pending = pending;
    }

    public TradeOffer(String p1, String p2, String b1, String b2){
        this.persoana1 = p1;
        this.persoana2 = p2;
        this.book1 = b1;
        this.book2 = b2;
        this.pending = 0;
    }

    public String getPersoana1(){return persoana1;}
    public String getPersoana2(){return persoana2;}
    public String getBook1(){return book1;}
    public String getBook2(){return book2;}
    public int getPending(){return pending;}

    public void setPersoana1(String p1){this.persoana1 = p1;}
    public void setPersoana2(String p2){this.persoana2 = p2;}
    public void setBook1(String b1){this.book1 = b1;}
    public void setBook2(String b2){this.book2 = b2;}
    public void setPending(int pending){this.pending = pending;}
}
