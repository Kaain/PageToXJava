package de.fhb.ott.pagetoxjava.rest;

import de.fhb.ott.pagetoxjava.service.PhantomService;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;

/**
 * @author Christoph Ott
 */
@Path("/")
@Stateless
public class PageToXRest {

	@POST
	@Path("/makeScreenshotPost/")
	@Produces("image/png")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Asynchronous
	public void makeScreenshotHtml(@FormParam("html") String html, @Suspended final AsyncResponse res) {
		File file = null;
		try {
			file = PhantomService.getInstance().takeScreenShotHtml(html);
		} catch (IOException e) {
			e.printStackTrace();
			Response.serverError().build();
		}
		Response.ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition", "attachment; filename=\"image.png\"");
		res.resume(response.build());
	}

	@GET
	@Path("/makeScreenshot/{url : .+}")
	@Produces("image/png")
	@Asynchronous
	public void makeScreenshotWebsite(@PathParam("url") String url, @Suspended final AsyncResponse res) {
		File file = null;
		file = PhantomService.getInstance().takeScreenShotWebsite(url);
		Response.ResponseBuilder response = Response.ok(file);
		response.header("Content-Disposition", "attachment; filename=\"screenshot.png\"");
		res.resume(response.build());
	}
}
