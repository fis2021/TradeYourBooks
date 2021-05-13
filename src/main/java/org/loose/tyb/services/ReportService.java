package org.loose.tyb.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.exceptions.UniqueConstraintException;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.tyb.controllers.LoginController;
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Book;
import org.loose.tyb.model.Report;

import static org.loose.tyb.services.FileSystemService.getPathToFile;

public class ReportService {
    private static ObjectRepository<Report> reportsRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("TYBReports.db").toFile())
                .openOrCreate("test", "test");

        reportsRepository = database.getRepository(Report.class);
    }

    public static ObservableList<Report> Reps()
    {
        ObservableList<Report>list= FXCollections.observableArrayList();

        for (Report rep : reportsRepository.find()) {
                list.add(rep);
        }
        return list;
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
}
