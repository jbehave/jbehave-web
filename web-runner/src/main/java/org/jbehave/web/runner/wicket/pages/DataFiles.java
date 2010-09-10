package org.jbehave.web.runner.wicket.pages;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.file.Files;
import org.jbehave.web.io.FileManager;

import com.google.inject.Inject;

public class DataFiles extends Template {

    @Inject
    private FileManager manager;

    public DataFiles() {
        setPageTitle("Data Files");
        add(new FilesContainer("files", manager.list()));
        add(new FileUploadForm("uploadForm"));

    }

    @SuppressWarnings("serial")
    public class FilesContainer extends WebMarkupContainer {

        public FilesContainer(String id, List<File> files) {
            super(id);

            // generate a markup-id so the contents can be
            // updated through an AJAX call.
            setOutputMarkupId(true);

            add(new ListView<File>("file", files) {

                @Override
                protected void populateItem(ListItem<File> item) {
                    final File file = (File) item.getModelObject();
                    // display the file path
                    item.add(new Label("path", new PropertyModel<File>(item.getDefaultModel(), "path")));
                    item.add(new Link<File>("show") {
                        @Override
                        public void onClick() {
                            setResponsePage(new ShowFile(file));
                        }
                    });
                    item.add(new Link<File>("delete") {
                        @Override
                        public void onClick() {
                            manager.delete(asList(file.getPath()));
                            setResponsePage(DataFiles.class);
                        }
                    });

                }
            });
        }
    }

    @SuppressWarnings("serial")
    private class FileUploadForm extends Form<Void> {
        private final Collection<FileUpload> uploads = new ArrayList<FileUpload>();

        public FileUploadForm(String name) {
            super(name);

            // multi-part uploads
            setMultiPart(true);

            // multi-file upload field
            add(new MultiFileUploadField("fileInput", new PropertyModel<Collection<FileUpload>>(this, "uploads"), 5));

        }

        protected void onSubmit() {
            for (FileUpload upload : uploads) {
                uploadFile(upload);
            }
            setResponsePage(DataFiles.class);
        }

        private void uploadFile(FileUpload upload) {
            File file = new File(manager.getUploadDirectory(), upload.getClientFileName());

            if (file.exists()) {
                // Try to delete the existing file
                if (!Files.remove(file)) {
                    throw new IllegalStateException("Failed to overwrite file " + file);
                }
            }
            try {
                file.createNewFile();
                upload.writeTo(file);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to write file " + file);
            }
        }
    }

}
