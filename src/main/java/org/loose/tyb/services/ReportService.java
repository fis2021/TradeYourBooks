package org.loose.tyb.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.model.Report;

import static org.loose.tyb.services.FileSystemService.getPathToFile;

public class ReportService {
    private static ObjectRepository<Report> reportsRepository;
    private static Nitrite database;
    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("TYourBReports.db").toFile())
                .openOrCreate("test", "test");

        reportsRepository = database.getRepository(Report.class);
    }

    public static ObservableList<Report> ReportList() {
        ObservableList<Report> list = FXCollections.observableArrayList();
        for (Report rep : reportsRepository.find()) {
            list.add(rep);
        }
        return list;
    }

    public static void ReportBook(String Owner, String Bookname, String reason) throws AlreadyReported {
        try {
            reportsRepository.insert(new Report(Owner, Bookname, reason));
        } catch (UniqueConstraintException e) {
            throw new AlreadyReported();
        }
    }

    public static void deleteReport(String owner, String Bookname, String reason){
        for (Report rep : reportsRepository.find()) {
            if (rep.getBookname().equals(Bookname) && rep.getOwner().equals(owner) && rep.getReason().equals(reason))
                reportsRepository.remove(rep);

        }
    }

    public static void deleteBookReport(String owner, String bookname) {
        for(Report rep : reportsRepository.find()){
            if(rep.getBookname().equals(bookname) && rep.getOwner().equals(owner)){
                reportsRepository.remove(rep);
            }
        }
        BookService.adminDeleteBook(owner, bookname);
    }

    public static Nitrite getDatabase(){return database;}
}
