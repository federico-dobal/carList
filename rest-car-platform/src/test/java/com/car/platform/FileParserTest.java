package com.car.platform;

import junit.framework.TestCase;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileParserTest extends TestCase {

    @Test
    public void testFileParserFileNotEmpty() throws java.io.IOException {
        File file = new File(FileParserTest.class.getClassLoader().getResource("listing.csv").getFile());

        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));

        List<Listing> listings = FileParser.parseFile(multipartFile, 1L);
        assertEquals(3, listings.size());

        Listing currentListing = listings.get(0);
        assertEquals("1", currentListing.getCode());
        assertEquals("mercedes", currentListing.getMake());
        assertEquals("a 180", currentListing.getModel());
        assertEquals(123, currentListing.getPowerInPs().intValue());
        assertEquals(2014, currentListing.getYear().intValue());
        assertEquals("black", currentListing.getColor());
        assertEquals(15950L, currentListing.getPrice().longValue());

        currentListing = listings.get(1);
        assertEquals("2", currentListing.getCode());
        assertEquals("audi", currentListing.getMake());
        assertEquals("a3", currentListing.getModel());
        assertEquals(111, currentListing.getPowerInPs().intValue());
        assertEquals(2016, currentListing.getYear().intValue());
        assertEquals("white", currentListing.getColor());
        assertEquals(17210L, currentListing.getPrice().longValue());

        currentListing = listings.get(2);
        assertEquals("3", currentListing.getCode());
        assertEquals("vw", currentListing.getMake());
        assertEquals("golf", currentListing.getModel());
        assertEquals(86, currentListing.getPowerInPs().intValue());
        assertEquals(2018, currentListing.getYear().intValue());
        assertEquals("green", currentListing.getColor());
        assertEquals(14980L, currentListing.getPrice().longValue());
    }

    @Test
    public void testFileParserFileEmpty() throws java.io.IOException {
        File file = new File(FileParserTest.class.getClassLoader().getResource("listing_empty.csv").getFile());

        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));

        List<Listing> listings = FileParser.parseFile(multipartFile, 1L);
        assertEquals(0, listings.size());

    }

    @Test
    public void testFileParserFileIsNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            FileParser.parseFile(null, 1L);
        });

    }

    @Test
    public void testFileParserDealerIdIsNull() throws java.io.IOException {

        File file = new File(FileParserTest.class.getClassLoader().getResource("listing.csv").getFile());
        MultipartFile multipartFile = new MockMultipartFile("file", new FileInputStream(file));

        assertThrows(IllegalArgumentException.class, () -> {
            FileParser.parseFile(multipartFile, null);
        });

    }

}