package com.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.Arrays;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class RestAPI {

    private static final String UPLOAD_DIR = "uploads/";

    @PutMapping("/uploader")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        // try {
        //     // Ensure the upload directory exists
        //     File uploadDir = new File(UPLOAD_DIR);
        //     if (!uploadDir.exists()) {
        //         uploadDir.mkdirs();
        //     }

        //     // Save the file to the upload directory
        //     File uploadedFile = new File(UPLOAD_DIR + fileName);
        //     try (FileOutputStream fos = new FileOutputStream(uploadedFile)) {
        //         fos.write(file.getBytes());
        //     }

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.CREATED);
        // } catch (IOException e) {
        //     return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        // }
    }

    @GetMapping("/downloader")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) {
        try {
        //     // Load the file from the upload directory
        //     Path filePath = Paths.get(UPLOAD_DIR + fileName);
        //     byte[] fileBytes = Files.readAllBytes(filePath);

            byte[] fileBytes = new byte[1024]; // Placeholder for file bytes
            Arrays.fill(fileBytes, (byte) 0xFF);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}