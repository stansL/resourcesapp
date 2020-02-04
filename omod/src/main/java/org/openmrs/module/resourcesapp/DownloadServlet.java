package org.openmrs.module.resourcesapp;

import org.openmrs.util.OpenmrsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        File resourcesFile = OpenmrsUtil.getDirectoryInApplicationDataDirectory("mpd");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String filename = "IPD notice Form_ used for patient discharge_1.0.0_discharge.docx";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");

        FileInputStream fileInputStream = new FileInputStream(new File(resourcesFile,filename));

        int i;
        while ((i=fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
        out.close();
    }

}
