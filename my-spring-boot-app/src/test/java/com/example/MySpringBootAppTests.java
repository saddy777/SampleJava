package com.example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MySpringBootApp.class)
class MySpringBootAppTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFileUpload() {
        // Prepare the file to upload
        ClassPathResource file = new ClassPathResource("test-file.txt");

        // Create a MultiValueMap to hold the file and additional parameters
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file);
        body.add("fileName", "test-file.txt");

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Create the HttpEntity with the body and headers
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Send PUT request to upload endpoint
        ResponseEntity<String> response = restTemplate.exchange(
                "/uploader", HttpMethod.PUT, requestEntity, String.class);

        // Assert the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("File uploaded successfully");
    }

    @Test
    void testFileDownload() {
        // Send GET request to download endpoint
        ResponseEntity<byte[]> response = restTemplate.exchange("/downloader?fileName=test-file.txt", HttpMethod.GET, null, byte[].class);

        // Assert the response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getHeaders().getContentType().toString()).isEqualTo("application/octet-stream");
    }
}