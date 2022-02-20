package com.example.taxreports.controller.Reports;

import org.apache.log4j.Logger;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@MultipartConfig
@WebServlet("/download")
public class FileDownloadServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(FileDownloadServlet.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/plain");
        String filePath = req.getParameter("filePath");
        String filename = filePath.substring(filePath.lastIndexOf("/"));
        resp.setHeader("Content-disposition", "attachment; filename=" +filename);
        log.info("Download file = " + filePath);
        try (InputStream in = req.getServletContext()
                .getResourceAsStream(filePath);
             OutputStream out = resp.getOutputStream()) {

            byte[] buffer = new byte[100];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }
}

