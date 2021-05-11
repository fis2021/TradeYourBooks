package org.loose.tyb.model;

import org.dizitart.no2.objects.Id;

public class Book {
    @Id
    private String Bookname;
    private String Author;
    private int Year;
    private String Publisher;
    private int numbEx;
    public Book(){}


    public Book(String book, String autor, int an, String editura, int ne)
    {
        this.Bookname = book;
        this.Author = autor;
        this.Year = an;
        this.Publisher = editura;
        this.numbEx = ne;
    }


    public String getBookname(){return Bookname;}
    public String getAuthor(){return Author;}
    public int getYear(){return Year;}
    public String getPublisher(){return Publisher;}
    public int getNoEx(){return numbEx;}

    public void setBookname(String bookname){this.Bookname = bookname;}
    public void setAuthor(String autor){this.Author = autor;}
    public void setYear(int an){this.Year = an;}
    public void setPublisher(String editura){this.Publisher = editura;}
    public void setNoEx(int ne){this.numbEx = ne;}
}
