package com.internship.service.implementation;

import com.internship.model.entity.User;
import com.internship.service.interfaces.IFileService;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
import com.internship.service.interfaces.ITaskService;
import com.internship.service.interfaces.IUserService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FileService implements IFileService {
    private final static String FILE_PATH = "/home/kontsevich/projects/GradleProject/utils/";
    private final IInfoService iInfoService;
    private final IUserService userService;
    private final ITaskService taskService;
    private final IMessageService messageService;

    public FileService(IInfoService iInfoService, IUserService userService, ITaskService taskService, IMessageService messageService) {
        this.iInfoService = iInfoService;
        this.userService = userService;
        this.taskService = taskService;
        this.messageService = messageService;
    }

    @Override
    public void saveToPdfFile(String fileName) {
        try {
            Document document = new Document();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH + fileName + ".pdf"));
            document.open();

            addChunkToDocument("Users", font, document);
            document.add(new Paragraph(userService.getAll().toString()));
            addChunkToDocument("Tasks", font, document);
            document.add(new Paragraph(taskService.getAll().toString()));
            addChunkToDocument("Messages", font, document);
            document.add(new Paragraph(messageService.getAll().toString()));
            addChunkToDocument("Info", font, document);
            document.add(new Paragraph(iInfoService.getAll().toString()));

            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToTxtFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + fileName));
            writer.write(getAllInfo().toString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToCsvFile(String fileName) {
        File csvOutputFile = new File(FILE_PATH + fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println(getAllInfo().stream()
                    .map(escapeSpecialCharacters())
                    .collect(Collectors.joining(",")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveToXlsxFile(String fileName) {
        List<String> columns = Arrays.asList("Id", "Age", "Name", "Email", "Password");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employee");

        prepareTable(columns, workbook, sheet);

        for (User user : userService.getAll()) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);

            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getAge());
            row.createCell(2).setCellValue(user.getName());
            row.createCell(3).setCellValue(user.getEmail());
            row.createCell(4).setCellValue(user.getPassword());
        }

        columns.forEach(column -> sheet.autoSizeColumn(columns.indexOf(column)));

        try {
            FileOutputStream fileOut = new FileOutputStream(FILE_PATH + fileName + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareTable(List<String> columns, Workbook workbook, Sheet sheet) {
        org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setColor(IndexedColors.RED.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        columns.forEach(column -> {
            Cell cell = headerRow.createCell(columns.indexOf(column));
            cell.setCellValue(column);
            cell.setCellStyle(headerCellStyle);
        });
    }

    private Function<String, String> escapeSpecialCharacters() {
        return data -> {
            String escapedData = data.replaceAll("\\R", " ");
            if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                data = data.replace("\"", "\"\"");
                escapedData = "\"" + data + "\"";
            }
            return escapedData;
        };
    }


    private void addChunkToDocument(String name, Font font, Document document) {
        try {
            Chunk chunk = new Chunk(name, font);
            document.add(chunk);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getAllInfo() {
        List<String> allInfo = new ArrayList<>();
        iInfoService.getAll().forEach(info -> allInfo.add(info.toString()));
        userService.getAll().forEach(user -> allInfo.add(user.toString()));
        taskService.getAll().forEach(task -> allInfo.add(task.toString()));
        messageService.getAll().forEach(message -> allInfo.add(message.toString()));
        return allInfo;
    }

}
