package com.example.testTask.services.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	List<Path> loadAll();

	void deleteAll();

}
