package com.naprednoprogramiranje.aes.service.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class StorageServiceImplTest {

    StorageServiceImpl storageServiceImpl;
    StorageProperties storageProps = new StorageProperties("upload-dir");
    String testFilePath = "src/test/resources/testPdf.pdf";
    String emptyFilePath = "src/test/resources/emptyFile.txt";

    @BeforeEach
    public void setUp() {
        storageServiceImpl = new StorageServiceImpl(storageProps);
        storageServiceImpl.init();
    }

    @AfterEach
    public void tearDown() throws IOException {
        File folderToBeDeleted = new File(storageProps.getLocation());
        FileSystemUtils.deleteRecursively(folderToBeDeleted);
        storageServiceImpl = null;
    }

    @Test
    public void testConstructor() {
        assertNotNull(storageServiceImpl);
        assertEquals(storageServiceImpl.getRootLocation(), Paths.get(storageProps.getLocation()));
    }

    @Test
    public void testInit() {
        assertTrue(Files.exists(storageServiceImpl.getRootLocation()));

        StorageProperties storagePropsInvalid = new StorageProperties("M://upload-dir");
        StorageServiceImpl storageServiceImplInvalid = new StorageServiceImpl(storagePropsInvalid);
        assertThrows(RuntimeException.class, () -> storageServiceImplInvalid.init());
    }

    @Test
    public void testStoreValid() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(testFilePath, "testPdf.pdf", "application/pdf", new FileInputStream(new File(testFilePath)));
        Path destPath = storageServiceImpl.store(multipartFile);
        assertNotNull(destPath);
        assertTrue(Files.exists(destPath));
        assertEquals(Files.mismatch(Paths.get(testFilePath), destPath), -1); // -1 if there is no mismatch in the content of the files
    }

    @Test
    public void testStoreInvalid() throws IOException {
        // Cannot store file outside current directory.
        MultipartFile multipartFile = new MockMultipartFile(testFilePath, "/../../testPdf.pdf", "application/pdf", new FileInputStream(new File(testFilePath)));
        RuntimeException ex1 = assertThrows(RuntimeException.class, () -> storageServiceImpl.store(multipartFile));
        assertEquals(ex1.getMessage(), "Cannot store file outside current directory.");
        // Failed to store empty file.
        MultipartFile multipartFileEmpty = new MockMultipartFile(emptyFilePath, "emptyFile.txt", "application/pdf", new FileInputStream(new File(emptyFilePath)));
        RuntimeException ex2 = assertThrows(RuntimeException.class, () -> storageServiceImpl.store(multipartFileEmpty));
        assertEquals(ex2.getMessage(), "Failed to store empty file.");
    }

    @Test
    public void testLoad() {
        Path resolvedCmp = Paths.get(storageProps.getLocation()).resolve("TEST").normalize();
        Path loaded = storageServiceImpl.load("TEST");
        assertEquals(resolvedCmp, loaded);
    }

    @Test
    public void testDeleteAll() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile(testFilePath, "testPdf.pdf", "application/pdf", new FileInputStream(new File(testFilePath)));
        Path destPath = storageServiceImpl.store(multipartFile);
        assertTrue(destPath.toFile().exists());
        storageServiceImpl.deleteAll();
        assertFalse(Paths.get(storageProps.getLocation()).toFile().exists());
    }

}
