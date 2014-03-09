package org.res.gitx.parameter;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
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
 * Basic structure ok but lots of issues with open/close, scrolling etc
 * 
 * 
 */
public class RefTree
      extends Composite {

   private Text text;

   RefTree(Composite parent, String defaultReference) {
      super(parent, SWT.NULL);
      createContent(defaultReference);
   }

   public String getText() {
      return text.getText();
   }

   private void createContent(String defaultReference) {
      Map<RefType, Ref[]> model = new EnumMap<>(RefType.class);

      for (RefType type : RefType.values()) {
         List<String> names = type.getRefs();
         Ref[] refs = new Ref[names.size()];
         int index = 0;
         for (String name : names) {
            Ref ref = new Ref(type, name);
            refs[index++] = ref;
         }
         model.put(type, refs);
      }

      setLayout(new GridLayout(1, false));
      text = new Text(this, SWT.BORDER);
      if (defaultReference != null) {
         text.setText(defaultReference);
      }
      text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

      ScrolledComposite scrollPane = new ScrolledComposite(this, SWT.H_SCROLL | SWT.V_SCROLL);
      scrollPane.setExpandHorizontal(true);
      scrollPane.setExpandVertical(true);
      scrollPane.setLayoutData(new GridData(GridData.FILL_BOTH));
      TreeViewer resultViewer = new TreeViewer(scrollPane, SWT.SINGLE);
      resultViewer.setContentProvider(new ContentProvider(model));
      resultViewer.setInput(model);
      Tree tree = resultViewer.getTree();
      tree.addListener(SWT.Selection, new EventListener());
      for (TreeItem item : tree.getItems()) {
         item.setExpanded(true);
      }
      scrollPane.setContent(tree);
      
   }

   private static final class Ref {
      private final RefType parent;
      private final String name;

      Ref(RefType parent, String name) {
         this.parent = parent;
         this.name = name;
      }

      @Override
      public String toString() {
         return name;
      }
   }

   private static final class ContentProvider
         implements ITreeContentProvider {

      private static final Object[] EMPTY = new Object[0];

      private final Map<RefType, Ref[]> model;

      ContentProvider(Map<RefType, Ref[]> model) {
         this.model = model;
      }

      @Override
      public void dispose() {
         /* don't care */
      }

      @Override
      public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
         /* don't care */
      }

      @Override
      public Object[] getElements(Object inputElement) {
         return RefType.values();
      }

      @Override
      public Object[] getChildren(Object parentElement) {
         if (parentElement instanceof RefType) {
            return model.get(parentElement);
         }
         return EMPTY;
      }

      @Override
      public Object getParent(Object element) {
         if (element instanceof RefType) {
            return null;
         }
         return ((Ref) element).parent;
      }

      @Override
      public boolean hasChildren(Object element) {
         if (element instanceof Ref) {
            return false;
         }
         return model.get(element).length > 0;

      }
   }

   private final class EventListener
         implements Listener {
      @Override
      public void handleEvent(Event event) {
         if (event.detail != SWT.CHECK) {
            TreeItem item = (TreeItem) event.item;
            Object datum = item.getData();
            if (datum instanceof Ref) {
               Ref ref = (Ref) datum;
               text.setText(ref.name);
            } else {
               text.setText("");
            }
         }
      }
   }
}