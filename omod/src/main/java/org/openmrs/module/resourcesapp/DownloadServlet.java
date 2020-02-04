package org.openmrs.module.resourcesapp;

import org.openmrs.util.OpenmrsUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;

public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        System.out.println(request.getParameterMap());
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            System.out.println(parameterNames.nextElement().toString());
        }
        File resourcesFile = OpenmrsUtil.getDirectoryInApplicationDataDirectory("mpd");

        System.out.println("=======================================");
        String name = request.getParameter("fileName");
        File fileToDownload = null;


//        reads input file from an absolute path
        for (File f : resourcesFile.listFiles()) {
            if (f.getName().startsWith(name)) {
                fileToDownload = f;
                break;
            }
        }


        if (fileToDownload != null) {
            FileInputStream fileInputStream = new FileInputStream(fileToDownload);
            // obtains ServletContext
            ServletContext context = getServletContext();

            // gets MIME type of the file
            String mimeType = context.getMimeType(fileToDownload.getAbsolutePath());
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) fileToDownload.length());

            // forces download
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileToDownload.getName() + "\"");

            // obtains response's output stream
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outStream.close();
        } else {
            System.out.println("No File to Download");

        }


    }

}
