/// package's name
package edu.gcsc.vrl.neurolucida;

/// imports
import edu.gcsc.vrl.ug.api.I_Neurolucida;
import edu.gcsc.vrl.ug.api.Neurolucida;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @brief NeuroLucidaConverter component
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "NeurolucidaConverter", category = "/UG4/VRL-Plugins/Neuro/Neurolucida/")
public class NeurolucidaConverter implements Serializable {
	/// serial ID
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief converts a given Neurolucida XML file to the corresponding UGX and OBJ files
	 * @param folder location of the Neurolucida files subject to conversion
	 * @param separator for formatting of subset names (default: non)
	 * @param rescale utilized to re-scale geometry to appropriate dimensions (default: 1.0e-6)
	 * @param cleanSubsets utilized to support VRL-Studio compatible subset names (default: true)
	 * @param ugx output (default: true)
	 * @param obj output (default: false)
	 */
	public void convert(
		/// FOLDER
		@ParamInfo(
			   name     = "Input file folder", 
			   typeName = "Location of the Neurolucida files subject to conversion",
			   style    = "load-folder-dialog", 
			   options  = "endings=[\"xml\"]; description=\"XML (Neurolucida) files\""
		) File folder,
		
		/// SEPARATOR
		@ParamInfo(
			name     = "Subset name separator", 
		 	typeName = "Separator used for formatting of subset names (default: non)",
		  	options  = "value=\"\""
		) String separator,
			
		/// RESCALE
		@ParamInfo(
			name     = "Scale parameter", 
			typeName = "Utilize to re-scale geometry to appropriate dimensions (default: 1.0e-6)", 
			options  = "value=1.0e-6"
		) double rescale,
		
		/// CLEANSUBSETS
		@ParamInfo(
			name     = "Clean subset names", 
			typeName = "Utilize to support VRL-Studio compatible subset names (default: true)",
			options  = "value=true"
		) boolean cleanSubsets,

		/// UGX
		@ParamInfo(
			name     = "UGX",
			typeName = "Convert given file to UGX (default: true)",
			options  = "value=true"
		) boolean ugx,

		/// OBJ
		@ParamInfo(
			name     = "OBJ",
			typeName = "Convert given file to OBJ (default: false)",
			options  = "value=false"
		) boolean obj
		){
		for (File f : Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}}))) {
			I_Neurolucida converter = new Neurolucida();
			converter.set_VRLOutputNames(cleanSubsets);
			converter.set_scaling(rescale);
			converter.set_separator(separator);
			/// converter.set_ugx_output(ugx);
			/// converter.set_obj_output(obj);
			converter.convert(f.getAbsoluteFile().toString());
		}
	}
}