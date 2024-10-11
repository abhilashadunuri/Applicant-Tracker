package com.example.ui.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.mail.*;
import javax.mail.internet.*;

import com.example.ui.DAOImpl.CandidateDAOImpl;
import com.example.ui.DAOImpl.UserDAOImpl;
import com.example.ui.domain.Candidate;
import com.example.ui.domain.User;
import com.example.ui.service.EmailProcessingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EmailProcessingServiceImpl extends RemoteServiceServlet implements EmailProcessingService {
    private UserDAOImpl userDAO;
    private static final String fromEmail = "abhiadunuri9949@gmail.com";
    private static final String password = System.getenv("EMAIL_PASSWORD"); // Use an environment variable for security
    private Map<String, String> emailMap = new HashMap<>();

    @Override
    public void uploadFile(String filePath) {
        System.out.println("Breakpoint.......");
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
             
            Sheet sheet = workbook.getSheetAt(0);
            boolean isFirstRow = true;
            for (Row row : sheet) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }
                Cell nameCell = row.getCell(1);
                Cell emailCell = row.getCell(2);
                if (nameCell != null && nameCell.getCellType() == CellType.STRING &&
                    emailCell != null && emailCell.getCellType() == CellType.STRING) {
                    String name = nameCell.getStringCellValue().trim();
                    String email = emailCell.getStringCellValue().trim();
                    System.out.println(name + " " + email);
                    emailMap.put(name, email);
                }
            }
            try {
				sendEmails();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmails() throws MessagingException {
        userDAO = (UserDAOImpl) ApplicationContextListener.applicationContext.getBean("userDao");
        
        for (Map.Entry<String, String> entry : emailMap.entrySet()) {
            String name = entry.getKey();
            String email = entry.getValue(); 
            String tempPassword = generateRandomPassword(8);
            
            User user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(tempPassword);
            userDAO.saveCandidate(user);
            System.out.println("Candidate details saved for: " + name);

            try {
				sendEmailToCandidate(email, tempPassword);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    private void sendEmailToCandidate(String email, String tempPassword) throws IOException {
        String subject = "Login Credentials for Application";
        String body = "Dear Candidate,\n\nYour temporary password is: " + tempPassword + 
                      "\nPlease log in and update your details.\n\nRegards,\nHR Team";

        try {
            com.google.appengine.api.mail.MailService mailService = com.google.appengine.api.mail.MailServiceFactory.getMailService();
            
            com.google.appengine.api.mail.MailService.Message message = new com.google.appengine.api.mail.MailService.Message();
            message.setSender(fromEmail);
            message.setTo(email);
            message.setSubject(subject);
            message.setTextBody(body);
            mailService.send(message);
            System.out.println("Sent message successfully to " + email);

        } catch (Exception e) {
            System.err.println("Error occurred while sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
