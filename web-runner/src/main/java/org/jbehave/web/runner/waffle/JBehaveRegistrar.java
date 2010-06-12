package org.jbehave.web.runner.waffle;

import static java.util.Arrays.asList;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.codehaus.waffle.io.RequestFileUploader;
import org.codehaus.waffle.menu.Menu;
import org.codehaus.waffle.menu.MenuAwareController;
import org.codehaus.waffle.registrar.AbstractRegistrar;
import org.codehaus.waffle.registrar.Registrar;
import org.codehaus.waffle.view.ViewResolver;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.embedder.StoryRunner;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.steps.DefaultStepdocGenerator;
import org.jbehave.core.steps.Steps;
import org.jbehave.web.io.ArchivingFileManager;
import org.jbehave.web.io.SilentFileMonitor;
import org.jbehave.web.io.ZipFileArchiver;
import org.jbehave.web.runner.waffle.controllers.FileController;
import org.jbehave.web.runner.waffle.controllers.FileUploadController;
import org.jbehave.web.runner.waffle.controllers.FilesController;
import org.jbehave.web.runner.waffle.controllers.StepdocController;
import org.jbehave.web.runner.waffle.controllers.StoryController;

public class JBehaveRegistrar extends AbstractRegistrar {

	public JBehaveRegistrar(Registrar delegate) {
		super(delegate);
	}

	@Override
	public void application() {
		registerMenu();
		registerConfiguration();
		registerStoryParser();
		registerStoryRunner();
		registerSteps();
		registerStepdocGenerator();
		registerFileMonitor();
		registerFileManager();
		register("data/file", FileController.class);
		register("data/files", FilesController.class);
		configureViews();
	}

	@Override
	public void session() {
		register("story/story", StoryController.class);
		register("story/stepdoc", StepdocController.class);
	}
	
	@Override
	public void request() {
		register(DiskFileItemFactory.class);
		register(RequestFileUploader.class);
		register("data/upload", FileUploadController.class);
	}

	protected void registerMenu() {
		register("home", MenuAwareController.class);
		registerInstance("menu", createMenu());
	}

	protected Menu createMenu() {
		Map<String, List<String>> content = new HashMap<String, List<String>>();
		content.put("Menu", asList("Home:home", "Data Files:data/files", "Data Upload:data/upload", "Run Story:story/story", "Stepdoc:story/stepdoc"));
		return new Menu(content);
	}

	protected void configureViews() {
		ViewResolver viewResolver = getComponentRegistry().locateByKey(ViewResolver.class);
		viewResolver.configureView("home", "ftl/home");
		viewResolver.configureView("data/file", "ftl/data/file");
		viewResolver.configureView("data/files", "ftl/data/files");
		viewResolver.configureView("data/upload", "ftl/data/upload");
		viewResolver.configureView("story/story", "ftl/story/story");
		viewResolver.configureView("story/stepdoc", "ftl/story/stepdoc");
	}

	protected void registerConfiguration() {
		register(MostUsefulConfiguration.class);
	}

	protected void registerStoryParser() {
		register(RegexStoryParser.class);
	}

	protected void registerStoryRunner() {
		register(StoryRunner.class);
	}

	protected void registerSteps() {
		registerInstance(new Steps());
	}

	protected void registerStepdocGenerator() {
		register(DefaultStepdocGenerator.class);
	}

	protected void registerFileMonitor() {
		register(SilentFileMonitor.class);
	}

	protected void registerFileManager() {
		register(ZipFileArchiver.class);
		register(ArchivingFileManager.class);
		registerInstance("uploadDirectory", uploadDirectory());
	}

	protected File uploadDirectory() {
		return new File(System.getProperty("java.io.tmpdir"), "upload");
	}

}
