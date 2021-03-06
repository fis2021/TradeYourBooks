package org.loose.tyb.model;

import org.dizitart.no2.objects.Id;

public class Report {
    private String owner;
    private String Bookname;
    private String reason;
    @Id
    private String obr;

    public Report(){}
    public Report(String owner, String book, String reason) {
        this.owner = owner;
        this.Bookname = book;
        this.reason = reason;
        this.obr = owner + book + reason;
    }

    public String getBookname(){return this.Bookname;}
    public String getOwner(){return this.owner;}
    public String getReason(){return this.reason;}
}
