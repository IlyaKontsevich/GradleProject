package com.internship.service.interfaces;

public interface IFileService {
    void saveToPdfFile(String fileName);

    void saveToTxtFile(String fileName);

    void saveToCsvFile(String fileName);

    void saveToXlsxFile(String fileName);
}
