package CheckBoxedBorder._2;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Used to simplify building panel with borders.
 * Plain border with title or border with checkbox can be created.  
 * @author idanilov
 */
public class TitledPanel extends JPanel {

	private JCheckBox checkbox;	
	private JComponent innerPanel;		
	private static final int CHECK_BOX_OFFSET = 3;
	private static final Insets DEFAULT_INSETS = new Insets(0,5,5,5);
	
	public TitledPanel(final String title, final JComponent innerPanel) {
		this(title,innerPanel,new JCheckBox(title,true),DEFAULT_INSETS);
	}
	
	public TitledPanel(final String title, final JComponent innerPanel, final JCheckBox checkBox) {
		this(title,innerPanel,checkBox,DEFAULT_INSETS);
	}
	
	/**
	 * @param title - title for border.
	 * @param innerComp - inner comonent to build border around.
	 * @param checkBox - checkbox component. If null simple title border will created.
	 * @param innerEmptyBorder - border insets. 
	 */
	public TitledPanel(final String title, final JComponent innerComp, final JCheckBox checkBox,final Insets innerEmptyBorder) {
		super(null);
		if (innerComp == null) {
			throw new IllegalArgumentException("Inner panel must be not null");
		}
		if (checkBox == null) {
			throw new IllegalArgumentException("Checkbox must be not null");
		}
		innerPanel = innerComp;
		innerPanel.setBorder(new EmptyBorder(innerEmptyBorder));		
		checkbox = checkBox;
		checkbox.setText(title);
		prepareCheckBox();
		super.setLayout(null);//use absolute positions.
		super.setBorder(BorderFactory.createTitledBorder("X"));//placeholder.
		super.add(checkbox);
		super.add(innerPanel);
	}

	public void doLayout() {
		doAbsouluteLayout(); 
	}
	
	private void doAbsouluteLayout() {
		Dimension size = getSize();
		Insets insets = getInsets();
		innerPanel.setLocation(insets.left, insets.top);
		innerPanel.setSize(size.width - insets.left - insets.right,
				size.height - insets.top - insets.bottom);
		innerPanel.validate();
		//relocate and resize the check box. 
		checkbox.setLocation(insets.left + CHECK_BOX_OFFSET, 0); 
		checkbox.setSize(checkbox.getSize().width, insets.top); 
	}
	
	public void setTitle(final String title) {
		checkbox.setText(title);
	}
	
	private void prepareCheckBox() {
		checkbox.setBorder(null);		
		checkbox.setSize(checkbox.getPreferredSize());
		checkbox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selected(checkbox.isSelected());
			}
		});
	}
	
	/**
	 * Override to react on selection.
	 * By default - all children are enabled/disable.
	 */
	protected void selected(final boolean isSelected) {
		setChildrenEnabled(innerPanel, isSelected);
		innerPanel.revalidate();
	}
	
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public Dimension getPreferredSize() {
		Dimension checkBoxDimension  = checkbox.getPreferredSize();
		Dimension innerPanelDimension = innerPanel.getPreferredSize();
		Insets insets = getInsets();
		int width = checkBoxDimension.width;
		if (innerPanelDimension.width > width) {
			width = innerPanelDimension.width;      
		}
		width = width + insets.left + insets.right;
		int height = innerPanelDimension.height + insets.top + insets.bottom;
		Dimension result = new Dimension(width, height);
		return result;
	}  

	public JComponent getInnerPanel() {
		return innerPanel;
	}

	public JCheckBox getCheckbox() {
		return checkbox;
	}
	
	public final void setLayout(final LayoutManager mgr) {
        if (mgr != null) {
            throw new IllegalArgumentException("setLayout() is not allowed");
        }
	}
	
	public final void setBorder(final Border border) {
        if (border != null) {
            throw new IllegalArgumentException("setBorder() is not allowed");
        }
	}
	
   /** Enable/disable a component and its children
     */
	public static void setChildrenEnabled(final Component parent, final boolean enabled) {
		if (parent == null) {
			return;
		}
		parent.setEnabled(enabled);
		if (parent instanceof Container) {
			Component [] comps = ((Container)parent).getComponents();
			for (int i=0; i < comps.length; i++) {
				setChildrenEnabled(comps[i],enabled);
			}
		}
	}

}