package org.res.gitx.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionService;

/**
 * Utility for managing selection
 * 
 * @author reshapiro
 * 
 */
public class SelectionHelper {

   private static final String EGIT_WORK_TREE_VAR = "git_work_tree";
   private final ISelectionService selectionService;
   private final List<String> selectedPaths = new ArrayList<>();

   public SelectionHelper(ISelectionService iSelectionService) {
      this.selectionService = iSelectionService;
   }

   
   private File checktGitRoot(IPath path, File expectedRoot) {
      File offeredFile = new File( path.toString());
      File pathFile = offeredFile;
      
      while (true) {
         pathFile = pathFile.getParentFile();
         if (pathFile == null) {
            return null;
         }
         File rootCandiate = new File(pathFile, ".git");
         if (rootCandiate.isDirectory()) {
            File gitRoot = rootCandiate.getParentFile();
            if (gitRoot.equals(expectedRoot)) {
               /* This really wants to be relative to expected root */
               return offeredFile;
            }
         }
      }
   }

   /**
    * Attempt to gather all selected paths in such a way that will allow Git
    * operations on selection. Only the first part is working right now
    * 
    * Still to do
    * 
    * For each selected file determine what work tree it's part of, if any.
    * 
    * Ignore any files that are not part of any work tree.
    * 
    * If the remaining items are all in the same tree, provide a structure that
    * includes the root of the common work and the relative path of each
    * selected file in it.
    * 
    * If the selection includes files from multiple trees, flag an error.
    */
   public List<String> updateSelectedPaths() {
      selectedPaths.clear();
      String workTree = Resolver.resolveVariable(EGIT_WORK_TREE_VAR);
      File workTreeFile = new Path(workTree).toFile();

      if (selectionService == null) {
         /* WTF> */
         return selectedPaths;
      }

      IStructuredSelection selection = (IStructuredSelection) selectionService.getSelection();
      if (selection == null) {
         /* nothing selected */
         return selectedPaths;
      }

      for (Object selectedElement : selection.toList()) {
         IResource resource = (IResource) Platform.getAdapterManager().getAdapter(selectedElement, IResource.class);
         if (resource != null) {
            IPath resourcePath = resource.getLocation();
            addIfMatch(workTreeFile, resourcePath);
         }
      }
      return selectedPaths;
   }


   void addIfMatch(File workTreeFile, IPath path) {
      File file = checktGitRoot(path, workTreeFile);
      if (file != null) {
         String basePath = workTreeFile.getPath();
         String filePath = file.getPath();
         String relativePath = filePath.substring(basePath.length()+1);
         selectedPaths.add(relativePath);
      }
   }
}
