package com.example.testTask.controller;

import com.example.testTask.services.calculator.Calculator;
import com.example.testTask.services.storage.StorageFileNotFoundException;
import com.example.testTask.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

	private final StorageService storageService;
	private final Calculator calculator;

	@Autowired
	public FileUploadController(StorageService storageService, Calculator calculator) {
		this.storageService = storageService;
		this.calculator = calculator;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) {

		model.addAttribute("files", storageService.loadAll());

		return "uploadForm";
	}

	@GetMapping("/{filename}")
	public String serveFile(@PathVariable String filename, RedirectAttributes redirectAttributes) {
		System.out.println(calculator.getDistance(filename));
        redirectAttributes.addFlashAttribute("dist", "dist = " + calculator.getDistance(filename) + "m");
		return "redirect:/";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"Файл " + file.getOriginalFilename() + " успешно загружен!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
