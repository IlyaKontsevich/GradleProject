package com.internship.service.implementation;

import com.internship.service.interfaces.IFileService;
import com.internship.service.interfaces.IInfoService;
import com.internship.service.interfaces.IMessageService;
import com.internship.service.interfaces.ITaskService;
import com.internship.service.interfaces.IUserService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_PATH + fileName));
            document.open();
            document.add(new Paragraph(getAllInfo().toString()));
            document.close();
            writer.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToTxtFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + fileName + ".pdf"));
            writer.write(getAllInfo().toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToCsvFile(String fileName) {
        File csvOutputFile = new File(FILE_PATH + fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            Stream.of(getAllInfo())
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveToXlsxFile(String fileName) {
        //TODO added oportunity save file to Xlsx format
    }


    private List<String> getAllInfo() {
        List<String> allInfo = new ArrayList<>();
        iInfoService.getAll().forEach(info -> allInfo.add(info.toString()));
        userService.getAll().forEach(user -> allInfo.add(user.toString()));
        taskService.getAll().forEach(task -> allInfo.add(task.toString()));
        messageService.getAll().forEach(message -> allInfo.add(message.toString()));
        return allInfo;
    }

    private String convertToCSV(List<String> data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }

    private String escapeSpecialCharacters(List<String> allInfo) {
        return allInfo
                .stream()
                .map(data -> {
                    String escapedData = data.replaceAll("\\R", " ");
                    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                        data = data.replace("\"", "\"\"");
                        escapedData = "\"" + data + "\"";
                    }
                    return escapedData;
                })
                .collect(Collectors.toList())
                .toString();
    }
}
