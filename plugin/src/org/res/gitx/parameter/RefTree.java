package org.res.gitx.parameter;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.res.gitx.util.Resolver;

/**
 * Choose a reference from a tree of options (branches, tags, remotes) or by
 * entering a SHA or one of the standard symbolic refs like HEAD or ORIG_HEAD.
 * 
 * Mostly works, but if the tree gets too long the later items won't be visible.
 * They can however be selected via arrow-key navigation
 * 
 */
public class RefTree
      extends Composite {

   private Tree tree;
   private Text text;
   private String currentBranch;

   RefTree(Composite parent, String defaultReference, boolean skipCurrentBranch) {
      super(parent, SWT.NULL);
      if (skipCurrentBranch) {
         currentBranch = Resolver.resolveVariable("git_branch");
      }
      createContent(defaultReference);
   }
   
   public String getText() {
      return text.getText();
   }

   private void createContent(String defaultReference) {
      setLayout(new GridLayout(1, false));
      text = new Text(this, SWT.BORDER);
      if (defaultReference != null) {
         text.setText(defaultReference);
      }
      text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      tree = new Tree(this, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
      
      for (RefType type : RefType.values()) {
         List<String> refs = type.getRefs();
         if (refs.isEmpty()) {
            continue;
         }
         TreeItem baseItem = new TreeItem(tree, SWT.NULL);
         baseItem.setText(type.name());
         for (String choice : refs) {
            if (choice.equals(currentBranch)) {
               /* Don't offer current branch as an option. */
               continue;
            }
            TreeItem choiceItem = new TreeItem(baseItem, SWT.NULL);
            choiceItem.setText(choice);
         }
         baseItem.setExpanded(true);
      }
      tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
      
      tree.addListener(SWT.Selection, new Listener() {
         @Override
         public void handleEvent(Event event) {
           if (event.detail != SWT.CHECK) {
              TreeItem item = (TreeItem) event.item;
              if (item.getParentItem() != null) {
                 text.setText(item.getText());
              }
           }
         }
       });
   }
}