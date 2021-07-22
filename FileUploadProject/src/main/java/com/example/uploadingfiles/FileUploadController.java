package com.example.uploadingfiles;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uploadingfiles.db.JdbcSQLServerConnection;
import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;

@Controller
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		
		  List<FileDetail> fileList=JdbcSQLServerConnection.loadFromDB();
		  model.addAttribute("fileDetails", fileList); 
		  return "uploadForm2";

		 
		
		/*
		 * model.addAttribute("files", storageService.loadAll().map( path ->
		 * MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
		 * "serveFile", path.getFileName().toString()).build().toUri().toString())
		 * .collect(Collectors.toList()));
		 * 
		 * return "uploadForm";
		 */
	}

	
	@GetMapping("/loadUploadedFiles")
	public String loadUploadedFiles(Model model) throws IOException {

		List<FileDetail> fileList=JdbcSQLServerConnection.loadFromDB();

		model.addAttribute("fileDetails", fileList);
		return "uploadForm2";
	}
	
	
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/uploadFiles")
	public String handleFileUpload(@RequestParam("customerListfile") MultipartFile file,
			@RequestParam("FyiMsgfile") MultipartFile file2,@RequestParam("submitter") String submitter,
			@RequestParam("description")String description,
			@RequestParam("application") String application,  
			@RequestParam("incident") String incident,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		storageService.store(file2);
		System.out.println("submitter :"+submitter);
		System.out.println("description :"+description );
		System.out.println("application :"+application);
		System.out.println("incident :"+incident);
		String formatedCustFileName="CUSTOMERLIST_"+application+"_"+incident+".csv";
		String formatedMsgFileName="FYIMSG_"+application+"_"+incident+".csv";
		String status="Received";
		JdbcSQLServerConnection.uploadToDB(file.getOriginalFilename(),
				                           formatedCustFileName,
				                           file2.getOriginalFilename(),
				                           formatedMsgFileName,
				                           application,
				                           description,
				                           incident,
				                           submitter,
				                           status);
	

		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
