package org.openmrs.module.resourcesapp;

import org.apache.commons.io.FilenameUtils;
import org.openmrs.util.OpenmrsUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;

public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        File resourcesFile = OpenmrsUtil.getDirectoryInApplicationDataDirectory("mpd");

        String fileName = request.getParameter("fileName");

        File fileToDownload = null;


//        reads input file from an absolute path
        for (File f : resourcesFile.listFiles()) {
            if (f.getName().startsWith(fileName)) {
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

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) fileToDownload.length());


            // forces download
            String toSend =  fileName + "."+ FilenameUtils.getExtension(fileToDownload.getName());

            String fn = URLEncoder.encode(toSend, "UTF-8");
            fn = URLDecoder.decode(fn, "ISO8859_1");
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");

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
