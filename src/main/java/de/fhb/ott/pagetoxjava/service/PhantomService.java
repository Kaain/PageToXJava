package de.fhb.ott.pagetoxjava.service;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.server.SeleniumServer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Christoph Ott
 */
public class PhantomService {

	protected DesiredCapabilities dCaps;
	private int counter;
	private int max;
	private List<PhantomJSDriver> drivers;
	private List<PhantomJSDriverService> services;

	private PhantomService() {
		dCaps = DesiredCapabilities.phantomjs();
		dCaps.setJavascriptEnabled(true);
		dCaps.setCapability("takesScreenshot", true);
		max = Integer.parseInt(System.getProperty("phantomjs.workercount"));
		services = new ArrayList<>();
		drivers = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			PhantomJSDriverService phantomService;
			try {
				phantomService = PhantomJSDriverService.createDefaultService();
				phantomService.start();
				services.add(phantomService);
				drivers.add(new PhantomJSDriver(phantomService, dCaps));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		counter = 0;
	}

	public File takeScreenShotWebsite(String url) {
		PhantomJSDriver driver = getNextDriver();
		driver.get("http://" + url);
		return driver.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
	}

	public File takeScreenShotHtml(String html) throws IOException {
		File file = new File(UUID.randomUUID() + ".html");
		FileUtils.write(file, html);

		PhantomJSDriver driver = getNextDriver();
		File screenshotAs;
		try {
			driver.get("file://" + file.getAbsolutePath());
			screenshotAs = driver.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		} finally {
			FileUtils.deleteQuietly(file);
		}
		return screenshotAs;
	}

	public void quitDriversAndServices() {
		for (PhantomJSDriver driver : drivers) {
			driver.quit();
		}
		for (PhantomJSDriverService service : services) {
			service.stop();
		}
	}

	private PhantomJSDriver getNextDriver() {
		PhantomJSDriver driver = drivers.get(counter);
		if (counter < max - 1) {
			counter++;
		} else {
			counter = 0;
		}
		return driver;
	}

	private static class SingletonHolder {
		public static final PhantomService INSTANCE = new PhantomService();
	}

	public static PhantomService getInstance() {
		return SingletonHolder.INSTANCE;
	}
}
