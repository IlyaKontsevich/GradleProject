package com.internship.controller;

import com.internship.service.interfaces.IFileService;
import com.internship.service.interfaces.IInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/save")
public class FileController {
    @Autowired
    IFileService fileService;
    @Autowired
    private IInfoService infoService;

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/pdf")
    public String saveInfoToPdfFile() {
        fileService.saveToPdfFile("pdfFile");
        return "redirect:/user/" + infoService.getUserUrl();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/txt")
    public String saveInfoToTxtFile() {
        fileService.saveToTxtFile("txtFile");
        return "redirect:/user/" + infoService.getUserUrl();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/csv")
    public String saveInfoToCsvFile() {
        fileService.saveToCsvFile("csvFile");
        return "redirect:/user/" + infoService.getUserUrl();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @RequestMapping(value = "/xlsx")
    public String saveInfoToXlsxFile() {
        fileService.saveToXlsxFile("xlsxFile");
        return "redirect:/user/" + infoService.getUserUrl();
    }

}
