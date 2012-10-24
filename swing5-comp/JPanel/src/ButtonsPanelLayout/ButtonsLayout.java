package ButtonsPanelLayout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.SwingConstants;

/**
 * @author idanilov
 * 
 */
public class ButtonsLayout implements LayoutManager {

	private int type;
	private int alignment;
	private int gap;
    private boolean shrink;

	/**
	 * @param type SwingConstants.HORIZONTAL or SwingConstants.VERTICAL 
	 * @param alignment Possible values: 
	 * <br> SwingConstants.LEFT or SwingConstants.TOP (have the same effect)
	 * SwingConstants.RIGHT or SwingConstants.BOTTOM (have the same effect) 
	 * @param gap Distance between button in pixels
	 */
	public ButtonsLayout(final int type,final int alignment, final int gap) {
		setType(type);
		setAlignment(alignment);
		setGap(gap);
	}

	public ButtonsLayout(final int alignment,final int gap) {
		this(SwingConstants.HORIZONTAL,alignment,gap);
	}
	
	public ButtonsLayout(final int gap) {
		this(SwingConstants.HORIZONTAL,SwingConstants.RIGHT, gap);
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public int getAlignment() {
		return alignment;
	}

	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}

	public int getGap() {
		return gap;
	}

	public void setGap(int gap) {
		this.gap = gap;
	}

    public boolean isShrink() {
        return shrink;
    }
    
    /**
     * If true - components will be resized proprtinally to fit into container space.
     * @param shrink
     */
    public void setShrink(final boolean shrink) {
        this.shrink = shrink;
    }
    
	public void layoutContainer(Container container) {
		Insets insets = container.getInsets();
		Component[] children = container.getComponents();
		Dimension dim[] = dimensions(container);
		int maxWidth = dim[0].width;
		int maxHeight = dim[0].height;
		int usedWidth = dim[1].width;
		int usedHeight = dim[1].height;
		switch (alignment) {
			case SwingConstants.LEFT:
			case SwingConstants.TOP:
				for (int i = 0; i < children.length; i++) {
					if (!children[i].isVisible()) {
						continue;
					}
					if (type == SwingConstants.HORIZONTAL) {
						children[i].setBounds(insets.left + (maxWidth + gap) * i,insets.top, 
								maxWidth, maxHeight);
					} else if (type == SwingConstants.VERTICAL) {
						children[i].setBounds(insets.left,insets.top + (maxHeight + gap) * i,
								maxWidth, maxHeight);
					}
				}
				break;
			case SwingConstants.RIGHT:
			case SwingConstants.BOTTOM:
				for (int i = 0; i < children.length; i++) {
					if (!children[i].isVisible()) {
						continue;
					}
					if (type == SwingConstants.HORIZONTAL) {
						children[i].setBounds(container.getWidth() - insets.right
								- usedWidth + (maxWidth + gap) * i, insets.top,
								maxWidth, maxHeight);
					} else if (type == SwingConstants.VERTICAL) {
						children[i].setBounds(insets.left,
								container.getHeight() - insets.bottom
								- usedHeight + (maxHeight + gap) * i,
								maxWidth, maxHeight);
						
					}					
				}
				break;
		}
	}

	public Dimension minimumLayoutSize(Container c) {
		return preferredLayoutSize(c);
	}

	public Dimension preferredLayoutSize(Container container) {
		Insets insets = container.getInsets();
		Dimension dim[] = dimensions(container);
		int usedWidth = dim[1].width;
		int usedHeight = dim[1].height;
		return new Dimension(insets.left + usedWidth + insets.right, insets.top
				+ usedHeight + insets.bottom);
	}

	public void addLayoutComponent(String string, Component comp) {
	}

	public void removeLayoutComponent(Component c) {
	}
	
	Dimension[] dimensions(final Container container) {
        Component[] children = container.getComponents();
		int maxWidth = 0;
		int maxHeight = 0;
		int visibleCount = 0;
		Dimension componentPreferredSize;
		for (int i = 0, c = children.length; i < c; i++) {
			if (children[i].isVisible()) {
				componentPreferredSize = children[i].getPreferredSize();
				maxWidth = Math.max(maxWidth, componentPreferredSize.width);
				maxHeight = Math.max(maxHeight, componentPreferredSize.height);
				visibleCount++;
			}
		}
        if (shrink) {
            Insets containerInsets = container.getInsets();
            if (type == SwingConstants.HORIZONTAL) {
                int prefferedButtonsWidth = maxWidth * visibleCount + gap * (visibleCount - 1);
                int fullContainerWidth = container.getWidth() - containerInsets.left - containerInsets.right;
                if (fullContainerWidth > 0) {
                    if (prefferedButtonsWidth > fullContainerWidth) {
                        int diff = fullContainerWidth - gap * (visibleCount - 1);
                        maxWidth = diff/visibleCount;
                    }
                }
            } else if (type == SwingConstants.VERTICAL) {
                int prefferedButtonsHeight = maxHeight * visibleCount + gap * (visibleCount - 1);
                int fullContainerHeight = container.getHeight() - containerInsets.top - containerInsets.bottom;
                if (fullContainerHeight > 0) {
                    if (prefferedButtonsHeight > fullContainerHeight) {
                        int diff = fullContainerHeight - gap * (visibleCount - 1);
                        maxHeight = diff/visibleCount;
                    }
                }
            }
        }
		int usedWidth = 0;
		int usedHeight = 0; 
		if (type == SwingConstants.HORIZONTAL) {
			usedWidth = maxWidth * visibleCount + gap * (visibleCount - 1);
			usedHeight = maxHeight;
		} else if (type == SwingConstants.VERTICAL) {
			usedHeight = maxHeight * visibleCount + gap * (visibleCount - 1);
			usedWidth = maxWidth;
		}
		return new Dimension[] { new Dimension(maxWidth, maxHeight),
				new Dimension(usedWidth, usedHeight), };
	}	

}