PageToXJava
===========

REST-API for converting html or a website to an image.

# Uses
* JAX-RS for REST-API
* PhantomJS / Selenium for making Screenshots of a website
* GlassFish 4.0 as server

## API

* POST: ```http://127.0.0.1:8080/PageToXJava/makeScreenshotPost```
* GET: ```http://127.0.0.1:8080/PageToXJava/makeScreenshot/<url>```

### POST Params
```html``` : html source code
