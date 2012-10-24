package com.id.swingeditor.d2;

import java.awt.Graphics2D;
import java.awt.Insets;

/**
 * @author idanilov
 *
 */
public class EmptyBorder extends AbstractBorder {

    private Insets ins = D2Util.EMPTY_INSTETS;

    public EmptyBorder(int i) {
        setInsets(D2Util.createInsets(i));
    }

    public EmptyBorder(Insets ins) {
        setInsets(ins);
    }

    public void setInsets(Insets ins) {
        this.ins = ins;
    }

    public Insets getInsets(XFigure figure) {
        return ins;
    }

    public void paint(XFigure f, Graphics2D g) {

    }

}
