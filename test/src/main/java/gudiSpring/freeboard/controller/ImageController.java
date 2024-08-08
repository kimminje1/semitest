package gudiSpring.freeboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/images")
public class ImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
            throws ServletException, IOException {
        // 요청된 이미지 파일 경로를 가져옵니다.
        String filePath = req.getParameter("filePath");
        File imageFile = new File(filePath);

        // 이미지 파일이 존재하지 않는 경우 404 에러를 반환합니다.
        if (!imageFile.exists()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 이미지 다운로드 처리
        String action = req.getParameter("action");
        if ("download".equals(action)) {
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment; filename=\"" + imageFile.getName() + "\"");
        } else {
            res.setContentType(getServletContext().getMimeType(imageFile.getName()));
        }

        // 이미지 파일을 클라이언트로 전송합니다.
        try (FileInputStream fis = new FileInputStream(imageFile);
             OutputStream os = res.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
