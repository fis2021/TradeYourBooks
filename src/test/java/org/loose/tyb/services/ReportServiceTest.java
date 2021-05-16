package org.loose.tyb.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.loose.tyb.exceptions.AlreadyReported;
import org.loose.tyb.exceptions.BookExists;
import org.loose.tyb.model.Report;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReportServiceTest {

    public static final String a = "gigi";
    public static final String b = "fram ursul polar";
    public static final String r = "fak ke vreu";

    @AfterEach
    void tearDown() {
        ReportService.getDatabase().close();
        BookService.getDatabase().close();
    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        ReportService.initDatabase();

        FileSystemService.APPLICATION_FOLDER = ".testBook";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        BookService.initDatabase();
    }

    @Test
    void testTheReportIsAddedToDataBase() throws AlreadyReported {
        ReportService.ReportBook(a, b, r);
        assertThat(ReportService.ReportList()).isNotEmpty();
        assertThat(ReportService.ReportList()).size().isEqualTo(1);
        Report rep = ReportService.ReportList().get(0);
        assertThat(rep).isNotNull();
        assertThat(rep.getOwner()).isEqualTo(a);
        assertThat(rep.getBookname()).isEqualTo(b);
        assertThat(rep.getReason()).isEqualTo(r);
    }

    @Test
    void testTheReportIsDeletedFromDataBase() throws AlreadyReported {
        ReportService.ReportBook(a, b, r);
        ReportService.deleteReport(a,b,r);
        assertThat(ReportService.ReportList()).isEmpty();
    }

    @Test
    void testTheBookAndTheReportIsDeleted() throws BookExists, AlreadyReported {
        BookService.addBook(a,b,"marius", 123,"ares", 1);
        assertThat(BookService.allBooks()).isNotEmpty();
        ReportService.ReportBook(a, b, r);
        assertThat(ReportService.ReportList()).isNotEmpty();
        ReportService.deleteBookReport(a,b);
    }

    @Test
    void testCanNotAddTheSameReportForTheSameBook() {
        assertThrows(AlreadyReported.class, () -> {
            ReportService.ReportBook(a, b, r);
            ReportService.ReportBook(a, b, r);
        });
    }
}