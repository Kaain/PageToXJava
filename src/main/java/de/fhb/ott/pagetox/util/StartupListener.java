package de.fhb.ott.pagetox.util;

import de.fhb.ott.pagetoxjava.service.PhantomService;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

/**
 * @author Christoph Ott
 */
@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		try {
			PropertyLoader.loadPropertyToSystemProp("properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		PhantomService.getInstance();
	}

	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {
		PhantomService.getInstance().quitDriversAndServices();
	}
}
