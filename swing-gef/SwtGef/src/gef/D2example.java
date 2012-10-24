package gef;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class D2example {

	static Rectangle r1 = new Rectangle(100, 100, 50, 50);
	static Rectangle r2 = new Rectangle(130, 120, 60, 70);

	static Figure fig2;

	public static void main(String[] args) {
		Display d = new Display();
		final Shell shell = new Shell(d);
		shell.setSize(400, 400);
		shell.setText("D2example");
		shell.setLayout(new FillLayout());
		Composite contents = new Composite(shell, 0);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		contents.setLayout(layout);

		final Button b = new Button(contents, SWT.TOGGLE);
		b.setLayoutData(new GridData());
		b.setText("Resize");
		b.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				System.err.println(">> ");

				boolean selected = b.getSelection();
				if (selected) {
					fig2.setBounds(r2);
				} else {
					fig2.setBounds(r1);
				}

			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Canvas canvas = new Canvas(contents, 0);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		LightweightSystem lws = new LightweightSystem(canvas);
		Figure contentsFig = new Figure() {
			//			@Override
			//			public void paint(Graphics graphics) {
			//				super.paint(graphics);
			//				System.err.println("ddd " + getSize());
			//				graphics.setBackgroundColor(ColorConstants.red);
			//				graphics.fillOval(0,0,200,200);
			//			}
		};
		contentsFig.setBackgroundColor(ColorConstants.white);
		//XYLayout contentsLayout = new XYLayout();
		//contentsFig.setLayoutManager(contentsLayout);
		lws.setContents(contentsFig);

		Figure fig1 = new Figure();
		fig1.setOpaque(true);
		fig1.setBackgroundColor(ColorConstants.red);
		//contentsLayout.setConstraint(fig1, new Rectangle(10,10,-1,-1));

		fig2 = new Figure();
		fig2.setBounds(r1);
		fig2.setLayoutManager(new XYLayout());
		fig2.setOpaque(true);
		fig2.setBackgroundColor(ColorConstants.green);
		//contentsLayout.setConstraint(fig2, new Rectangle(200, 200, -1, -1));
		Figure fig3 = new Figure() {
			@Override
			public void setBounds(Rectangle rect) {
				System.err.println("child set bouns:");
				super.setBounds(rect);
			}
			
			@Override
			protected void primTranslate(int dx, int dy) {
				System.err.println("child ::");
				super.primTranslate(dx, dy);
			}
		};
		fig3.setOpaque(true);
		fig3.setBackgroundColor(ColorConstants.blue);
		fig2.add(fig3, new Rectangle(10, 10, 20, 30));

		//contentsFig.add(fig1, new Rectangle(0, 0, 100, 100));
		contentsFig.add(fig2, new Rectangle(100, 100, 50, 50));

		shell.open();
		while (!shell.isDisposed())
			while (!d.readAndDispatch())
				d.sleep();
	}
}
