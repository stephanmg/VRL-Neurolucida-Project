/// package's name
package edu.gcsc.vrl.neurolucida;

/// imports
import eu.mihosoft.vrl.io.IOUtil;
import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.ProjectTemplate;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import eu.mihosoft.vrl.system.VRLPlugin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * @brief Plugin configurator of Neurolucida Utility 
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class NeurolucidaUtilityPluginConfigurator extends VPluginConfigurator {
	private File templateProjectSrc;
	private final String templateProjectName = "neurolucida_template.vrlp";
	private final String templateDataName    = "template.xml";

	/**
	 * @brief ctor
	 */
	public NeurolucidaUtilityPluginConfigurator() {
		//s pecify the plugin name and version
		setIdentifier(new PluginIdentifier("VRL-Neurolucida-Plugin", "0.1"));

		// export some packages
		exportPackage("edu.gcsc.vrl.neurolucida");

		// describe the plugin
		setDescription("Utility to convert Neurolucida (XML) to ug4 files (UGX/OBJ)");

		// get the license text
		String license_str = "";
		try {
			license_str = IOUtils.toString(new FileInputStream(new File(getClass().getClassLoader().getResource("/edu/gcsc/vrl/neurolucida/lgpl-3.0.txt").getFile())));
		} catch (IOException ioe) {
			System.err.println(ioe);
		} catch (Exception e) {
			System.err.println(e);
		}

		// copyright info
		setCopyrightInfo("VRL-Neurolucida-Plugin",
			"(c) stephanmg",
			"www.syntaktischer-zucker.de", "LGPLv3", license_str);

		// specify dependencies
		addDependency(new PluginDependency("VRL", "0.4.1", "0.4.3"));
		addDependency(new PluginDependency("VRL-UG", "0.2", "0.2"));
		addDependency(new PluginDependency("VRL-UG-API", "0.2", "0.2"));
	}

	@Override
	public void register(PluginAPI api) {
		// register plugin with canvas
		if (api instanceof VPluginAPI) {
			VPluginAPI vapi = (VPluginAPI) api;
			vapi.addComponent(NeurolucidaUtility.class);
		}
	}

	@Override
	public void install(InitPluginAPI iApi) {
		new File(iApi.getResourceFolder(), templateProjectName).delete();
	}

	@Override
	public void unregister(PluginAPI api) {
		// nothing to unregister
	}

	@Override
	public void init(InitPluginAPI iApi) {
		CompletionUtil.registerClassesFromJar(VJarUtil.getClassLocation(NeurolucidaUtilityPluginConfigurator.class));

		initTemplateProject(iApi);
		initTemplateData(iApi);
	}

	private void initTemplateData(InitPluginAPI iAPI) {
		final File templateData = new File(iAPI.getResourceFolder(), templateDataName);
		if (!templateData.exists()) {
			try {
				InputStream in = NeurolucidaUtilityPluginConfigurator.class.getResourceAsStream(
					File.separator + getClass().getPackage().getName().replace(".", File.separator) + File.separator + templateDataName);
				IOUtil.saveStreamToFile(in, templateData);
			} catch (FileNotFoundException ex) {
				Logger.getLogger(VRLPlugin.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(VRLPlugin.class.getName()).
					log(Level.SEVERE, null, ex);
			}
		}
	}

	private void initTemplateProject(InitPluginAPI iApi) {
		System.err.println(getClass().getPackage());
		templateProjectSrc = new File(iApi.getResourceFolder(), templateProjectName);

		if (!templateProjectSrc.exists()) {
			saveProjectTemplate();
		}

		iApi.addProjectTemplate(new ProjectTemplate() {

			@Override
			public String getName() {
				return "Neurolucida - Template #1";
			}

			@Override
			public File getSource() {
				return templateProjectSrc;
			}

			@Override
			public String getDescription() {
				return "Neurolucida - Template #1";
			}

			@Override
			public BufferedImage getIcon() {
				return null;
			}
		});
	}

	private void saveProjectTemplate() {
		try {
			InputStream in = NeurolucidaUtilityPluginConfigurator.class.getResourceAsStream(
				File.separator + getClass().getPackage().getName().replace(".", File.separator) + File.separator + templateProjectName);
			IOUtil.saveStreamToFile(in, templateProjectSrc);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}
	}
}

