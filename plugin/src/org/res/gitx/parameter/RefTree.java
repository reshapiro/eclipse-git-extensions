package org.res.gitx.parameter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Early version of a reference chooser
 * 
 * 
 */
public class RefTree
      extends Composite {

   private Tree tree;
   private Text text;

   RefTree(Composite parent, String defaultReference) {
      super(parent, SWT.NULL);
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
         TreeItem baseItem = new TreeItem(tree, SWT.NULL);
         baseItem.setText(type.name());
         for (String choice : type.getRefs()) {
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
              text.setText(item.getText());
           }
         }
       });
   }
}