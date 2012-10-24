import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * @author idanilov
 *
 */
public class AlcatelDefaultTheme extends DefaultMetalTheme {

	/**
	 * Is used for active window borders, shadows of selected items, and labels - RGB(60, 60, 60)
	 */
	private final ColorUIResource primary1 = new ColorUIResource(60, 60, 60);

	/**
	 * Is used for selected menu titles and items, active scroll boxes, and progress bar fill - RGB(147, 182, 210)
	 */
	private final ColorUIResource primary2 = new ColorUIResource(255, 128, 64);//bright orange.

	/**
	 * Is used for large colored areas, such as the title bar of active internal frames and selected text - RGB(193, 214, 230)
	 */
	private final ColorUIResource primary3 = primary2;

	/**
	 * Is used for the dark border that creates flush 3D effects for items such as command buttons - RGB(102, 102, 102)
	 */
	private final ColorUIResource secondary1 = new ColorUIResource(102, 102, 102);

	/**
	 * Is used for inactive window borders, shadows, pressed buttons, and dimmed command button text  - RGB(153, 153, 153)
	 */
	private final ColorUIResource secondary2 = new ColorUIResource(153, 153, 153);

	/**
	 * Is used for the background canvas and inactive title bars for internal frames, is dependence on property "<B>plaf.metal.color.UseSystemColor</B>".
	 * If property "<B>plaf.metal.color.UseSystemColor</B>" is <code>true</code> <br> <code>secondary3</code> = "<B>SystemColor.control</B>"
	 * else <code>secondary3</code> = RGB (212, 208, 200) - default control's color for Win200.
	 */
	private final ColorUIResource secondary3;

	/**
	 * Is used for the focus - RGB(0, 100, 200).
	 */
	private final ColorUIResource focusColor = new ColorUIResource(91, 135, 206);

	/**
	 * Is used for Buttons, checkboxes, menu titles, and window titles.
	 * <br>Font name takes from property "<B>plaf.metal.font.name</B>" - "<B>SansSerif</B>" by default.
	 * <br>Font size takes from property "<B>plaf.metal.font.size</B>" - <B>11</B> by default.
	 * <br>Font is bold takes from property "<B>plaf.metal.font.bold</B>" - <code>false</code> by default.
	 * <br>Font is italic takes from property "<B>plaf.metal.font.italic</B>" - <code>false</code> by default.
	 */
	private FontUIResource controlFont;

	/**
	 * Is used for Tree views and tool tips.
	 * <br>Font name takes from property "<B>plaf.metal.font.name</B>" - "<B>SansSerif</B>" by default.
	 * <br>Font size takes from property "<B>plaf.metal.font.size</B>" - <B>11</B> by default.
	 * <br>Font is bold takes from property "<B>plaf.metal.font.bold</B>" - <code>false</code> by default.
	 * <br>Font is italic takes from property "<B>plaf.metal.font.italic</B>" - <code>false</code> by default.
	 */
	private FontUIResource systemFont;

	/**
	 * Is used for Text fields and tables.
	 * <br>Font name takes from property "<B>plaf.metal.font.name</B>" - "<B>SansSerif</B>" by default.
	 * <br>Font size takes from property "<B>plaf.metal.font.size</B>" - <B>11</B> by default.
	 * <br>Font is bold takes from property "<B>plaf.metal.font.bold</B>" - <code>false</code> by default.
	 * <br>Font is italic takes from property "<B>plaf.metal.font.italic</B>" - <code>false</code> by default.
	 */
	private FontUIResource userFont;

	/**
	 * Is used for Keyboard shortcuts in menus and tool tips.
	 * <br>Font name takes from property "<B>plaf.metal.font.name</B>" - "<B>SansSerif</B>" by default.
	 * <br>Font size takes from property "<B>plaf.metal.font.size</B>" - <B>11</B> by default.
	 * <br>Font is bold takes from property "<B>plaf.metal.font.bold</B>" - <code>false</code> by default.
	 * <br>Font is italic takes from property "<B>plaf.metal.font.italic</B>" - <code>false</code> by default.
	 */
	private FontUIResource smallFont;

	public String getName() {
		return AlcatelDefaultTheme.class.getSimpleName();
	}

	/**
	 *  Note: the properties listed here can currently be used by people
	 *  providing runtimes to hint what fonts are good.  For example the bold
	 *  dialog font looks bad on a Mac, so Apple could use this property to hint at a good font.
	 *  However, we don't promise to support these forever.  We may move to getting these
	 *  from the swing.properties file, or elsewhere.
	 */
	public AlcatelDefaultTheme() {
		boolean useSystemColor = true;
		if (useSystemColor) {
			secondary3 = new ColorUIResource(SystemColor.control);
		} else {
			secondary3 = new ColorUIResource(212, 208, 200);
		}
		String fontName = "SansSerif";
		int fontSize = 11;
		boolean fontBold = false;
		boolean fontItalic = false;
		int style = Font.PLAIN;
		if (fontBold) {
			style |= Font.BOLD;
		}
		if (fontItalic) {
			style |= Font.ITALIC;
		}
		FontUIResource oneFont = new FontUIResource(fontName, style, fontSize);
		controlFont = oneFont;
		systemFont = oneFont;
		userFont = oneFont;
		smallFont = oneFont;
	}

	// these are blue in Metal Default Theme
	protected ColorUIResource getPrimary1() {
		return primary1;
	}

	protected ColorUIResource getPrimary2() {
		return primary2;
	}

	protected ColorUIResource getPrimary3() {
		return primary3;
	}

	// these are gray in Metal Default Theme
	protected ColorUIResource getSecondary1() {
		return secondary1;
	}

	protected ColorUIResource getSecondary2() {
		return secondary2;
	}

	protected ColorUIResource getSecondary3() {
		return secondary3;
	}

	public FontUIResource getControlTextFont() {
		return controlFont;
	}

	public FontUIResource getSystemTextFont() {
		return systemFont;
	}

	public FontUIResource getUserTextFont() {
		return userFont;
	}

	public FontUIResource getMenuTextFont() {
		return controlFont;
	}

	public FontUIResource getWindowTitleFont() {
		return controlFont;
	}

	public FontUIResource getSubTextFont() {
		return smallFont;
	}

	public ColorUIResource getFocusColor() {
		return focusColor;
	}

}
