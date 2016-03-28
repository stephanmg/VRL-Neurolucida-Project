/// package's name
package edu.gcsc.vrl.neurolucida;

/// imports
import edu.gcsc.vrl.ug.api.I_Neurolucida;
import edu.gcsc.vrl.ug.api.Neurolucida;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.FilenameFilter;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @brief Neurolucida geometry (XML) to UGX/OBJ converter
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "NeurolucidaUtility", category = "/UG4/VRL-Plugins/Neuro/Neurolucida/")
public final class NeurolucidaUtility implements Serializable {
	/// default sVUID - demanded for serialization
	private static final long serialVersionUID = 1L;

	/**
	 * @brief converts given Neurolucida XML file 
	 * @param file location of the Neurolucida folder
	 * @param separator for formatting of subset names (default: empty)
	 * @param rescale recales geometry (default 1e-6)
	 * @param cleanSubsets VRL-Studio compatible subset names (default: true)
	 * @param ugx output UGX (default: true)
	 * @param obj output OBJ (default: false)
	 */
    	@MethodInfo(name="convert file", valueName="Convert a file", hide=false)
	public void convertFile(
		/// FILE
		@ParamInfo(
			name = "Input file",
			typeName = "Location of the Neurolucida file subject to conversion",
			style = "load-dialog",
			options = "endings=[\"xml\"]; description=\"XML (Neurolucida) files\""
		) File file,
		/// SEPARATOR
		@ParamInfo(
			name = "Subset name separator",
			typeName = "Separator used for formatting of subset names (default: non)",
			options = "value=\"\""
		) String separator,
		/// RESCALE
		@ParamInfo(
			name = "Scale parameter",
			typeName = "Utilize to re-scale geometry to appropriate dimensions (default: 1.0e-6)",
			options = "value=1.0e-6"
		) double rescale,
		/// CLEANSUBSETS
		@ParamInfo(
			name = "Clean subset names",
			typeName = "Utilize to support VRL-Studio compatible subset names (default: true)",
			options = "value=true"
		) boolean cleanSubsets,
		/// UGX
		@ParamInfo(
			name = "UGX",
			typeName = "Convert given file to UGX (default: true)",
			options = "value=true"
		) boolean ugx,
		/// OBJ
		@ParamInfo(
			name = "OBJ",
			typeName = "Convert given file to OBJ (default: false)",
			options = "value=false"
		) boolean obj
	) {
		convert(file, cleanSubsets, rescale, separator, ugx, obj);
	}

	/**
	 * @brief converts given Neurolucida XML file folder
	 * @param folder location of the Neurolucida folder
	 * @param separator for formatting of subset names (default: empty)
	 * @param rescale recales geometry (default 1e-6)
	 * @param cleanSubsets VRL-Studio compatible subset names (default: true)
	 * @param ugx output UGX (default: true)
	 * @param obj output OBJ (default: false)
	 */
    	@MethodInfo(name="convert folder", valueName="Convert a folder", hide=true)
	public void convertFolder(
		/// FOLDER
		@ParamInfo(
			name = "Input file folder",
			typeName = "Location of the Neurolucida files subject to conversion",
			style = "load-folder-dialog",
			options = "endings=[\"xml\"]; description=\"XML (Neurolucida) files\""
		) File folder,
		/// SEPARATOR
		@ParamInfo(
			name = "Subset name separator",
			typeName = "Separator used for formatting of subset names (default: non)",
			options = "value=\"\""
		) String separator,
		/// RESCALE
		@ParamInfo(
			name = "Scale parameter",
			typeName = "Utilize to re-scale geometry to appropriate dimensions (default: 1.0e-6)",
			options = "value=1.0e-6"
		) double rescale,
		/// CLEANSUBSETS
		@ParamInfo(
			name = "Clean subset names",
			typeName = "Utilize to support VRL-Studio compatible subset names (default: true)",
			options = "value=true"
		) boolean cleanSubsets,
		/// UGX
		@ParamInfo(
			name = "UGX",
			typeName = "Convert given file to UGX (default: true)",
			options = "value=true"
		) boolean ugx,
		/// OBJ
		@ParamInfo(
			name = "OBJ",
			typeName = "Convert given file to OBJ (default: false)",
			options = "value=false"
		) boolean obj
	) {
		convert(folder, cleanSubsets, rescale, separator, ugx, obj);
	}

	/**
	 * @brief convert function - calls UG-API to convert files
	 * @param folder
	 * @param cleanSubsets
	 * @param rescaling
	 * @param separator
	 * @param ugx
	 * @param obj
	 */
	private void convert(
		File folder, 
		boolean cleanSubsets, 
		double rescale, 
		String separator, 
		boolean ugx, 
		boolean obj) {
		for (File f : Arrays.asList(folder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		}))) {
			I_Neurolucida converter = new Neurolucida();
			converter.set_VRLOutputNames(cleanSubsets);
			converter.set_scaling(rescale);
			converter.set_separator(separator);
			converter.set_ugx_output(ugx);
			converter.set_obj_output(obj);
			converter.convert(f.getAbsoluteFile().toString());
		}
	}
}
