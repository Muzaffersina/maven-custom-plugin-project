package com.msa.customPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.model.ActivationProperty;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Used to generate file summary information
 * 
 * 
 * @author Muzaffer Sina Acikgoz
 * @see projectVersiyon
 * @see groupId
 * @see artifactId
 * @see developers
 * @see releaseDate
 * @see dependecies
 * @seeplugins
 * 
 * 
 * 
 */
@Mojo(name = "summarize", defaultPhase = LifecyclePhase.INSTALL)
public class CustomPlugin extends AbstractMojo {

	@Parameter(defaultValue = "${project}", required = true)
	private MavenProject project;

	@Parameter(defaultValue = "${project.build.directory}\\outPuts", required = true)
	private File outputDirectory;

	@Parameter(defaultValue = "${releaseDate}", required = false)
	private String releaseDate;

	private String projectVersiyon;
	private String groupId;
	private String artifactId;

	public void execute() throws MojoExecutionException, MojoFailureException {

		File f = outputDirectory;
		if (!f.exists()) {
			f.mkdirs();
		}
		File touch = new File(f, "outPutFile.txt");
		FileWriter w = null;

		projectVersiyon = project.getVersion();
		groupId = project.getGroupId();
		artifactId = project.getArtifactId();
		writeFile("Project Info : " + groupId + "." + artifactId + "." + projectVersiyon, w, touch);

		List<Developer> developers = project.getDevelopers();
		writeFile(" Developers ", w, touch);
		for (Developer developer : developers) {
			writeFile("Developer " + developer.getId() + ": " + developer.getName() + " ", w, touch);
		}

		writeFile("Release Date: " + releaseDate, w, touch);

		List<Dependency> dependecies = project.getDependencies();
		writeFile(" Dependencies ", w, touch);
		for (Dependency dependency : dependecies) {
			writeFile("Dependency: " + dependency.getGroupId() + dependency.getArtifactId() + " ", w, touch);
		}

		List<Plugin> plugins = project.getBuildPlugins();
		writeFile(" Plugins ", w, touch);
		for (Plugin plugin : plugins) {
			writeFile("Plugin: " + plugin.getArtifactId() + " ", w, touch);
		}

		getLog().info("***** Created outPutFile *****");
	}

	public void writeFile(String text, FileWriter w, File touch) throws MojoExecutionException {

		try {
			w = new FileWriter(touch, true);
			w.write(text);
		} catch (IOException e) {
			throw new MojoExecutionException("Error creating file " + touch, e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}
}
