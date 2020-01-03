package com.internship.controller;

import com.internship.service.interfaces.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.ALL_VALUE;

@Controller
@RequestMapping("/save")
public class FileController {
    @Autowired
    private IFileService fileService;

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/pdf", method = RequestMethod.GET)
    @ResponseBody
    public void saveInfoToPdfFile(HttpServletResponse response) throws IOException {
        response.setHeader("Content-disposition","attachment; filename=pdfFile.pdf");
        fileService.saveToPdfFile(response.getOutputStream());
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/txt", method = RequestMethod.GET)
    @ResponseBody
    public void saveInfoToTxtFile(HttpServletResponse response) throws IOException {
        response.setHeader("Content-disposition","attachment; filename=textFile.txt");
        fileService.saveToTxtFile(response.getOutputStream());
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/csv", method = RequestMethod.GET, produces = ALL_VALUE)
    @ResponseBody
    public void saveInfoToCsvFile(HttpServletResponse response) throws IOException {
        response.setHeader("Content-disposition","attachment; filename=csvFile.csv");
        fileService.saveToCsvFile(response.getOutputStream());
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/xlsx", method = RequestMethod.GET)
    @ResponseBody
    public void saveInfoToXlsxFile(HttpServletResponse response) throws IOException {
        response.setHeader("Content-disposition","attachment; filename=xlsxFile.xlsx");
        fileService.saveToXlsxFile(response.getOutputStream());
    }

}
