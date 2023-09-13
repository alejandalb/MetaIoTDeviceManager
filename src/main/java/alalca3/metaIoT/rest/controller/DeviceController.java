/**
 * 
 */
package alalca3.metaIoT.rest.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 */
@RestController
@RequestMapping("/")
public class DeviceController {
	private static class StreamGobbler implements Runnable {
		private InputStream inputStream;
		private Consumer<String> consumer;

		public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
			this.inputStream = inputStream;
			this.consumer = consumer;
		}

		@Override
		public void run() {
			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(consumer);
		}
	}

	// Endpoint for updating the device
	@GetMapping("/update")
	@PostMapping("/update")
	public ResponseEntity<String> updateDevice() {
		try {
			boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
			ProcessBuilder builder = new ProcessBuilder();
			if (isWindows) {
				builder.command("cmd.exe", "/c", "dir");
			} else {
				builder.command("sh", "-c", "apk update");
			}

			builder.directory(new File(System.getProperty("user.home")));
			Process process = builder.start();
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
			ExecutorService executorService = Executors.newFixedThreadPool(10);
			Future<?> future = executorService.submit(streamGobbler);
			int exitCode = process.waitFor();

		} catch (IOException | InterruptedException e) {
			// Handle any exceptions that occur during the command execution
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Device update failed");
		}

		return ResponseEntity.ok("Device updated successfully");
	}
}
