package com.ibm.wala.ide.util;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.jsdt.core.IIncludePathEntry;
import org.eclipse.wst.jsdt.core.IJavaScriptProject;
import org.eclipse.wst.jsdt.core.JavaScriptCore;
import org.eclipse.wst.jsdt.core.JavaScriptModelException;

import com.ibm.wala.cast.js.types.JavaScriptTypes;
import com.ibm.wala.types.ClassLoaderReference;

public class JavaScriptEclipseProjectPath extends EclipseProjectPath<IIncludePathEntry, IJavaScriptProject> {

	public enum JSLoader implements ILoader {
		JAVASCRIPT(JavaScriptTypes.jsLoader);
		
	    private ClassLoaderReference ref;

	    JSLoader(ClassLoaderReference ref) {
	      this.ref = ref;
	    }
	}
	
	protected JavaScriptEclipseProjectPath(IJavaScriptProject p) throws IOException,
			CoreException {
		super(AnalysisScopeType.SOURCE_FOR_PROJ_AND_LINKED_PROJS);
		// TODO Auto-generated constructor stub
	}

	public static JavaScriptEclipseProjectPath make(IJavaScriptProject p) throws IOException, CoreException {
		return new JavaScriptEclipseProjectPath(p);
	}

	@Override
	protected IJavaScriptProject makeProject(IProject p) {
		try {
			if (p.hasNature(JavaScriptCore.NATURE_ID)) {
				return JavaScriptCore.create(p);
			} else {
				return null;
			}
		} catch (CoreException e) {
			return null;
		}
	}

	@Override
	protected IIncludePathEntry resolve(IIncludePathEntry entry) {
		return JavaScriptCore.getResolvedIncludepathEntry(entry);
	}

	@Override
	protected void resolveClasspathEntry(IJavaScriptProject project,
			IIncludePathEntry entry,
			ILoader loader,
			boolean includeSource, boolean cpeFromMainProject) {
		IIncludePathEntry e = JavaScriptCore.getResolvedIncludepathEntry(entry);
		switch (e.getEntryKind()) {
		case IIncludePathEntry.CPE_SOURCE:
			resolveSourcePathEntry(JSLoader.JAVASCRIPT, true, cpeFromMainProject, e.getPath(), null, "js");
		}
	}

	@Override
	protected void resolveProjectClasspathEntries(IJavaScriptProject project,
			boolean includeSource) {
	    try {
			resolveClasspathEntries(project, Arrays.asList(project.getRawIncludepath()), Loader.EXTENSION, includeSource, true);
		} catch (JavaScriptModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
