/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package org.eclipse.xtext.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import com.google.common.collect.Lists;

/**
 * @author Sven Efftinge - Initial contribution and API
 * @author Jan Koehnlein - merging of parameterized entries
 * @since 2.2 moved to org.eclipse.xtext.util
 */
public class MergeableManifest extends Manifest {

	public static final Attributes.Name BUNDLE_NAME = new Attributes.Name("Bundle-Name");
	public static final Attributes.Name BUNDLE_SYMBOLIC_NAME = new Attributes.Name("Bundle-SymbolicName");
	public static final Attributes.Name BUNDLE_VERSION = new Attributes.Name("Bundle-Version");
	public static final Attributes.Name BUNDLE_CLASSPATH = new Attributes.Name("Bundle-ClassPath");
	public static final Attributes.Name BUNDLE_VENDOR = new Attributes.Name("Bundle-Vendor");
	public static final Attributes.Name BUNDLE_REQUIRED_EXECUTION_ENV = new Attributes.Name(
			"Bundle-RequiredExecutionEnvironment");
	public static final Attributes.Name EXPORT_PACKAGE = new Attributes.Name("Export-Package");
	public static final Attributes.Name IMPORT_PACKAGE = new Attributes.Name("Import-Package");
	public static final Attributes.Name REQUIRE_BUNDLE = new Attributes.Name("Require-Bundle");
	public static final Attributes.Name BUNDLE_ACTIVATION_POLICY = new Attributes.Name("Bundle-ActivationPolicy");
	public static final Attributes.Name BUNDLE_LOCALIZATION = new Attributes.Name("Bundle-Localization");
	public static final Attributes.Name BUNDLE_ACTIVATOR = new Attributes.Name("Bundle-Activator");

	private static final String LINEBREAK = "\r\n";

	/*
	 * java.util.Manifest throws an exception if line exceeds 512 chars
	 */
    static String make512Safe(StringBuffer lines) {
        if (lines.length() > 512) {
        	StringBuilder result = new StringBuilder(lines.length());
        	String[] splitted = lines.toString().split("\\r\\n");
        	for(String string: splitted) {
        		if (string.length() > 512) {
        			int idx = 510;
        			StringBuilder temp = new StringBuilder(string);
        			int length = temp.length();
        			while (idx < length - 2) {
                        temp.insert(idx, "\r\n ");
                        idx += 512;
                        length += 3;
                    }
        			result.append(temp.toString());
        		} else {
        			result.append(string);
        		}
        		result.append(LINEBREAK);
        	}
        	return result.toString();
        }
        return lines.toString();
    }

	public class OrderAwareAttributes extends Attributes {

		public OrderAwareAttributes() {
			try {
				Field field = Attributes.class.getDeclaredField("map");
				field.setAccessible(true);
				field.set(this, new LinkedHashMap<Object, Object>());
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}

		@Override
		public Object put(Object name, Object value) {
			final Object result = super.put(name, value);
			if (result != null && !result.equals(value))
				modified = true;
			return result;
		}

		/*
		 * Copied from base class, but replaced call to make72Safe(buffer) with make512Safe(buffer)
         * and does not write empty values
		 */
		@SuppressWarnings("deprecation")
		public void myWriteMain(DataOutputStream out) throws IOException {
			// write out the *-Version header first, if it exists
			String vername = Name.MANIFEST_VERSION.toString();
			String version = getValue(vername);
			if (version == null) {
				vername = Name.SIGNATURE_VERSION.toString();
				version = getValue(vername);
			}

			if (version != null) {
				out.writeBytes(vername + ": " + version + LINEBREAK);
			}

			// write out all attributes except for the version
			// we wrote out earlier
			Iterator<Map.Entry<Object, Object>> it = entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> e = it.next();
				String value = (String) e.getValue();
				if (!Strings.isEmpty(value)) {
					String name = ((Name) e.getKey()).toString();
					if ((version != null) && !(name.equalsIgnoreCase(vername))) {
						StringBuffer buffer = new StringBuffer(name);
						buffer.append(": ");

						byte[] vb = value.trim().getBytes("UTF8");
						value = new String(vb, 0, 0, vb.length);
						buffer.append(value);
						if (it.hasNext())
							buffer.append(LINEBREAK);
						out.writeBytes(make512Safe(buffer));
					}
				}
			}
			out.writeBytes(LINEBREAK);
		}

		/*
		 * Copied from base class, but omitted call to make72Safe(buffer)
         * and does not write empty values
		 */
		@SuppressWarnings("deprecation")
		public void myWrite(DataOutputStream out) throws IOException {
			Iterator<Map.Entry<Object, Object>> it = entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> e = it.next();
				String value = (String) e.getValue();
				if (Strings.isEmpty(value)) {
					StringBuffer buffer = new StringBuffer(((Name) e.getKey()).toString());
					buffer.append(": ");

					byte[] vb = value.trim().getBytes("UTF8");
					value = new String(vb, 0, 0, vb.length);
					buffer.append(value);
					if (it.hasNext())
						buffer.append(LINEBREAK);
					out.writeBytes(make512Safe(buffer));
				}
			}
			out.writeBytes(LINEBREAK);
		}
	}

	private boolean modified = false;

	private String projectName;

	public MergeableManifest(InputStream in) throws IOException {
		try {
			Field field = Manifest.class.getDeclaredField("attr");
			field.setAccessible(true);
			field.set(this, new OrderAwareAttributes());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		read(in);
		// hack: reconstruct linebreaks
		addRequiredBundles(Collections.<String>emptySet());
		addExportedPackages(Collections.<String>emptySet());
		addImportedPackages(Collections.<String>emptySet());
		modified = false;
	}

	public MergeableManifest(InputStream in, String projectName) throws IOException {
		this(in);
		this.projectName = projectName;
	}

	/**
	 * adds the qualified names to the require-bundle attribute, if not already
	 * present.
	 *
	 * @param bundles - passing parameterized bundled (e.g. versions, etc.) is not supported
	 */
	public void addRequiredBundles(Set<String> bundles) {
		// TODO manage transitive dependencies
		// don't require self
		Set<String> bundlesToMerge;
		String bundleName = (String) getMainAttributes().get(BUNDLE_NAME);
		if (bundleName != null && bundles.contains(bundleName) || projectName != null && bundles.contains(projectName)) {
			bundlesToMerge = new LinkedHashSet<String>(bundles);
			bundlesToMerge.remove(bundleName);
			bundlesToMerge.remove(projectName);
		} else {
			bundlesToMerge = bundles;
		}
		String s = (String) getMainAttributes().get(REQUIRE_BUNDLE);
		Wrapper<Boolean> modified = Wrapper.wrap(this.modified);
		String result = mergeIntoCommaSeparatedList(s, bundlesToMerge, modified);
		this.modified = modified.get();
		getMainAttributes().put(REQUIRE_BUNDLE, result);
	}

	public boolean isModified() {
		return modified;
	}

	/*
	 * Copied from base class to omit the call to make72Safe(..).
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void write(OutputStream out) throws IOException {
		DataOutputStream dos = new DataOutputStream(out);
		// Write out the main attributes for the manifest
		((OrderAwareAttributes) getMainAttributes()).myWriteMain(dos);
		// Now write out the pre-entry attributes
		Iterator<Map.Entry<String, Attributes>> it = getEntries().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Attributes> e = it.next();
			StringBuffer buffer = new StringBuffer("Name: ");
			String value = e.getKey();
			if (value != null) {
				byte[] vb = value.getBytes("UTF8");
				value = new String(vb, 0, 0, vb.length);
			}
			buffer.append(value);
			buffer.append("\r\n");
			dos.writeBytes(make512Safe(buffer));
			((OrderAwareAttributes) e.getValue()).myWrite(dos);
		}
		dos.flush();
	}

	/**
	 * adds the qualified names to the export-package attribute, if not already
	 * present.
	 *
	 * @param packages - passing parameterized packages is not supported
	 */
	public void addExportedPackages(Set<String> packages) {
		String s = (String) getMainAttributes().get(EXPORT_PACKAGE);
		Wrapper<Boolean> modified = Wrapper.wrap(this.modified);
		String result = mergeIntoCommaSeparatedList(s, packages, modified);
		this.modified = modified.get();
		getMainAttributes().put(EXPORT_PACKAGE, result);
	}

	public void addImportedPackages(Set<String> packages) {
		String s = (String) getMainAttributes().get(IMPORT_PACKAGE);
		Wrapper<Boolean> modified = Wrapper.wrap(this.modified);
		String result = mergeIntoCommaSeparatedList(s, packages, modified);
		this.modified = modified.get();
		getMainAttributes().put(IMPORT_PACKAGE, result);
	}
	
	/**
	 * @since 2.0
	 */
	protected static String[] splitQuoteAware(String string) {
		List<StringBuilder> result = Lists.newArrayList(new StringBuilder());
		boolean inQuote = false;
		for (int i = 0; i < string.length(); i++)
			switch (string.charAt(i)) {
				case ',':
					if (inQuote)
						result.get(result.size() - 1).append(string.charAt(i));
					else
						result.add(new StringBuilder());
					break;
				case '"':
					inQuote = !inQuote;
				default:
					result.get(result.size() - 1).append(string.charAt(i));
			}
		String[] resultArray = new String[result.size()];
		for (int i = 0; i < result.size(); i++)
			resultArray[i] = result.get(i).toString();
		return resultArray;
	}

	public static String mergeIntoCommaSeparatedList(String currentString, Set<String> toMergeIn, Wrapper<Boolean> modified) {
		String string = currentString == null ? "" : currentString;
		String[] split = splitQuoteAware(string);
		Map<String, String> name2parameters = new LinkedHashMap<String, String>();
		for (int i = 0; i < split.length; i++) {
			String value = split[i].trim();
			if (!value.equals("")) {
				Pair<String, String> nameParameter = getSplitEntry(value);
				name2parameters.put(nameParameter.getFirst(), nameParameter.getSecond());
			}
		}

		for (String value : toMergeIn) {
			if (!value.trim().equals("")) {
				Pair<String, String> nameParameter = getSplitEntry(value.trim());
				String existingParameter = name2parameters.get(nameParameter.getFirst());
				if(existingParameter != null) {
					continue;
				}
				boolean newModified = modified.get();
				newModified |= name2parameters.put(nameParameter.getFirst(), nameParameter.getSecond()) != null;
				modified.set(newModified);
			}
		}

		StringBuffer buff = new StringBuffer(string.length());
		Iterator<Map.Entry<String, String>> iterator = name2parameters.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			buff.append(entry.getKey());
			if(entry.getValue() != null) {
				buff.append(";").append(entry.getValue());
			}
			if (iterator.hasNext())
				buff.append(",\r\n ");
		}
		String result = buff.toString();
		return result;
	}
	
	/**
	 * @since 2.3
	 */
	protected static Pair<String,String> getSplitEntry(String entry) {
		int semicolon = entry.indexOf(';');
		String name;
		String parameter;
		if(semicolon == -1) {
			name=entry;
			parameter = null;
		} else { 
			name = entry.substring(0, semicolon);
			parameter = entry.substring(semicolon + 1);
		}
		return Tuples.create(name, parameter);
	}
}
