package org.res.gitx.parameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.res.gitx.util.Resolver;

/**
 * Utility to get at branch, tag and remote reference names
 * 
 * The use of java.io.File here could be problematic in Windows
 * @author reshapiro
 *
 */
public enum RefType {
   BRANCHES("heads"), TAGS("tags"), REMOTES("remotes");

   private static final String PACKED_REFS_FILE = "packed-refs";
   private static final String EGIT_GIT_DIR_VAR = "git_dir";
   private static final String REFS = "refs/";
   
   private final String subdir;

   RefType(String subdir) {
      this.subdir = subdir;
   }

   /**
    * 
    * @return the references of the given type
    */
   public List<String> getRefs() {
      String gitPath = Resolver.resolveVariable(EGIT_GIT_DIR_VAR);
      if (gitPath == null) {
         return null;
      }
      File dotGit = new File(gitPath);
      File refsDir = new File(new File(dotGit, REFS), subdir);
      if (!refsDir.exists()) {
         return null;
      }
      
      Set<String> refNames = getPackedRefs(dotGit);
      String basePath = refsDir.getPath();
      walkDirectory(refsDir, basePath.length(), refNames);
      List<String> sortedRefNames = new ArrayList<>(refNames);
      Collections.sort(sortedRefNames);
      return sortedRefNames;
   }

   private void walkDirectory(File dir, int beginIndex, Set<String> names) {
      if (dir == null) {
         return;
      }
      for (File file : dir.listFiles()) {
         if (file.isDirectory()) {
            walkDirectory(file, beginIndex, names);
         } else {
            String fullPath = file.getPath();
            names.add(fullPath.substring(beginIndex+1));
         }
      }
   }
   
   private Set<String> getPackedRefs(File dotGit) {
      Set<String> packed = new HashSet<>();
      File packedRefs = new File(dotGit, PACKED_REFS_FILE);
      try (BufferedReader reader = new BufferedReader(new FileReader(packedRefs))) {
         String line;
         while ((line = reader.readLine()) != null) {
            int refs = line.indexOf(" " + REFS);
            if (refs < 0) {
               continue;
            }
            String[] split = line.split(" ");
            if (split.length == 2) {
               String fullName = split[1];
               if (fullName.startsWith(REFS)) {
                  String strippedName = fullName.substring(REFS.length());
                  int typeIndex = strippedName.indexOf('/');
                  if (typeIndex > 0) {
                     String type = strippedName.substring(0, typeIndex);
                     if (type.equalsIgnoreCase(subdir)) {
                        String name = strippedName.substring(typeIndex+1);
                        packed.add(name);
                     }
                  }
               }
            }
            
         }
      } catch (IOException e) {
        /* Can't read packed-refs, that's ok. */
      }
      return packed;
   }
}