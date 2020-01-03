package com.internship.service.interfaces;

import java.io.OutputStream;

public interface IFileService {
    void saveToPdfFile(OutputStream outputStream);

    void saveToTxtFile(OutputStream outputStream);

    void saveToCsvFile(OutputStream outputStream);

    void saveToXlsxFile(OutputStream outputStream);
}
