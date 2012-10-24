package com.id.swingeditor.d2;

import java.awt.Insets;
import java.awt.Rectangle;

/**
 * @author idanilov
 *
 */
public class D2Util {

    public static final Insets EMPTY_INSTETS = createInsets(0);

    public static Insets createInsets(int i) {
        return new Insets(i, i, i, i);
    }

    public static void crop(Rectangle r, Insets insets) {
        if (r == null) {
            return;
        }
        r.x += insets.left;
        r.y += insets.top;
        r.width -= insets.right;
        r.height -= insets.bottom;
    }

    public static void grow(Rectangle r, Insets insets) {
        if (r == null || insets == null) {
            return;
        }
        r.x -= insets.left;
        r.y -= insets.top;
        r.width += insets.left + insets.right;
        r.height += insets.top + insets.bottom;
    }

    public static void shrink(Rectangle r, Insets insets) {
        if (r == null || insets == null) {
            return;
        }
        Insets i = new Insets(-insets.top, -insets.left, -insets.bottom, -insets.right);
        grow(r, i);
    }

}