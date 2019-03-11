package com.gcats.cats.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

//TODO: explore exception handling, move nameless strings to application.properties file
@Component
public class PdfGenerator {

    @Autowired
    private TemplateEngine templateEngine;

    public void createPdf(String templateName, String fileName, Map map) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");
        Context ctx = new Context();
        if (map != null) {
            Iterator itMap = map.entrySet().iterator();
            while (itMap.hasNext()) {
                Map.Entry pair = (Map.Entry) itMap.next();
                ctx.setVariable(pair.getKey().toString(), pair.getValue());
            }
        }

        String processedHtml = templateEngine.process("/lesson/" + templateName, ctx);
        OutputStream os = null;
        try {

            Path dir = Files.createDirectories(Paths.get("src", "main", "webapp", "pdf"));

            os = Files.newOutputStream(dir.resolve(fileName + ".pdf"));
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(processedHtml);

            renderer.layout();
            renderer.createPDF(os, false);
            renderer.finishPDF();
            System.out.println("PDF created successfully");
        }
        finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) { /*ignore*/ }
            }
        }
    }
}
