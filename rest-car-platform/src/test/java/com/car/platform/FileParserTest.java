package com.car.platform;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class FileParserTest extends TestCase {

    File file = new File(FileParserTest.class.getClassLoader().getResource("listing.csv").getFile());

    @Test
    public void testCostEmptyListIsZero() throws java.io.IOException {
        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));

        List listings = FileParser.parseFile(multipartFile, 1L);
        assertEquals(3, listings.size());

    }
}