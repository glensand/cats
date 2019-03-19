package com.gcats.cats.utils;

import java.io.ByteArrayOutputStream;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

import java.io.File;
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
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

//TODO: explore exception handling, move nameless strings to application.properties file
@Component
public class PdfGenerator {

    private static final String CHARSET = "UTF-8";

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
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        HtmlCleaner cleaner = new HtmlCleaner();
        CleanerProperties props = cleaner.getProperties();
        props.setCharset(CHARSET);
        TagNode node = cleaner.clean(processedHtml);
        new PrettyXmlSerializer(props).writeToStream(node, out);

        ITextRenderer renderer = new ITextRenderer();
//        Path fontPath = Files.(Paths.get("src", "main", "webapp", "pdf"));
        String fullPath = new File(".").getCanonicalPath();
        renderer.getFontResolver().addFont(fullPath + "\\src\\main\\" +
                        "resources\\static\\fonts\\tahoma.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        renderer.setDocumentFromString(new String(out.toByteArray(), CHARSET));

        OutputStream os = null;
        System.out.println(processedHtml);
        try {

            Path dir = Files.createDirectories(Paths.get("src", "main", "webapp", "pdf"));

            os = Files.newOutputStream(dir.resolve(fileName + ".pdf"));

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
