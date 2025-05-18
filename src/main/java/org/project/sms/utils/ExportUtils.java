package org.project.sms.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class ExportUtils {

    public static <T> void exportToExcel(TableView<T> table, Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.setInitialFileName("default_filename.xlsx");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        File file = fileChooser.showSaveDialog(owner);

        if (file == null) return;

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = new FileOutputStream(file)) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Write headers
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumns().size(); i++) {
                headerRow.createCell(i).setCellValue(table.getColumns().get(i).getText());
            }

            // Write rows
            ObservableList<T> items = table.getItems();
            for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
                Row row = sheet.createRow(rowIndex + 1);
                for (int colIndex = 0; colIndex < table.getColumns().size(); colIndex++) {
                    TableColumn<T, ?> col = table.getColumns().get(colIndex);
                    Object cellValue = col.getCellObservableValue(items.get(rowIndex)).getValue();
                    row.createCell(colIndex).setCellValue(cellValue != null ? cellValue.toString() : "");
                }
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void exportToPDF(TableView<T> table, Window owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF File");
        fileChooser.setInitialFileName("default_filename.pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showSaveDialog(owner);

        if (file == null) return;

        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            PdfPTable pdfTable = new PdfPTable(table.getColumns().size());
            pdfTable.setWidthPercentage(100);

            // Headers
            for (TableColumn<T, ?> column : table.getColumns()) {
                PdfPCell header = new PdfPCell(new Phrase(column.getText()));
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfTable.addCell(header);
            }

            // Rows
            ObservableList<T> items = table.getItems();
            for (T item : items) {
                for (TableColumn<T, ?> column : table.getColumns()) {
                    Object cell = column.getCellObservableValue(item).getValue();
                    pdfTable.addCell(cell != null ? cell.toString() : "");
                }
            }

            document.add(pdfTable);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
