/**
 * 
 */
package alalca3.metaIoT.rest.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping("/")
public class DeviceController {
	// Endpoint for updating the device
	@PostMapping("/update")
	public ResponseEntity<String> updateDevice() {
		try {
			// Execute 'apt update' and 'apt upgrade' commands
			Process process = Runtime.getRuntime().exec("apk update");
			process.waitFor();
			process = Runtime.getRuntime().exec("apk upgrade -y");
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			// Handle any exceptions that occur during the command execution
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device update failed");
		}

		return ResponseEntity.ok("Device updated successfully");
	}
}
