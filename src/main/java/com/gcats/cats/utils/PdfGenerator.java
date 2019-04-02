package com.gcats.cats.utils;

import java.io.ByteArrayOutputStream;

import com.gcats.cats.model.Lesson;
import com.gcats.cats.model.LessonsAttributes.AuthorsNotes;
import com.gcats.cats.model.LessonsAttributes.ReflectionPrompts;
import com.gcats.cats.model.LessonsAttributes.TeacherTask;
import com.gcats.cats.model.Resource;
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
import java.util.HashMap;
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

    private Context setContext(Lesson lesson){
        Context ctx = new Context();

        ctx.setVariable("author", lesson.getAuthor());
        ctx.setVariable("goal", lesson.getGoal());
        ctx.setVariable("name", lesson.getName());
        ctx.setVariable("timeInterval", lesson.getTimeInterval());
        ctx.setVariable("background", lesson.getBackground());

        Map<String, Object> lessonInfo = new HashMap<>();
        Map<String, Object> resources = new HashMap<>();
        for(Resource resource : lesson.getResources())
            resources.put(resource.getLink(), resource.getDescription());

        Map<String, Object> notes = new HashMap<>();
        for(AuthorsNotes note:lesson.getAuthorsNotes())
            notes.put(note.getNote(), note);

        Map<String, Object> reflectionPrompts = new HashMap<>();
        for(ReflectionPrompts prompt:lesson.getReflectionPrompts())
            notes.put(prompt.getRPrompt(), prompt);

        Map<String, Object> tasks = new HashMap<>();
        for(TeacherTask task:lesson.getTeacherTasks())
            notes.put(task.getTask(), task);

        lessonInfo.put("resources", resources);
        lessonInfo.put("notes", notes);
        //lessonInfo.put("reflection", resources);
        ctx.setVariables(lessonInfo);
        return ctx;
    }

    public void createPdf(String templateName, String fileName, Lesson lesson) throws Exception {
        Assert.notNull(templateName, "The templateName can not be null");

        Context ctx = setContext(lesson);

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
