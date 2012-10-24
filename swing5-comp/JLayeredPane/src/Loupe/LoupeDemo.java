package Loupe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author romainguy
 * @author idanilov
 * @jdk 1.5
 */
public class LoupeDemo extends JFrame {

	private Icon[] images;
	private JLayeredPane layeredPane;
	private Loupe loupe;

	public LoupeDemo() {
		super(LoupeDemo.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createImages();
		setContentPane(createContents());
	}

	private void createImages() {
		images = new ImageIcon[4];
		for (int i = 0; i < images.length; i++) {
			String nextName = "photo" + (i + 1) + ".jpg";
			URL url = LoupeDemo.class.getResource(nextName);
			images[i] = new ImageIcon(url);
		}
	}

	private JComponent createContents() {
		JPanel result = new JPanel(new BorderLayout());
		result.add(createControls(), BorderLayout.NORTH);
		layeredPane = new JLayeredPane();
		createLoupe();
		createLayers();
		result.add(layeredPane, BorderLayout.CENTER);
		setLoupLayer(1);
		return result;
	}

	private void setLoupLayer(int ind) {
	    layeredPane.setLayer(loupe, JLayeredPane.DEFAULT_LAYER.intValue() + ind);
	}
	
	private JComponent createControls() {
		JPanel result = new JPanel(new FlowLayout(FlowLayout.LEADING));
		JComboBox layerCombo = new JComboBox(new String[] { "Layer 0", "Layer 1", "Layer 2",
				"Layer 3", "Layer 4" });
		layerCombo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent actionEvent) {
				JComboBox combo = (JComboBox) actionEvent.getSource();
				int layerId = combo.getSelectedIndex();
				setLoupLayer(JLayeredPane.DEFAULT_LAYER.intValue() + layerId + 1);
			}

		});
		result.add(new JLabel("Loupe Layer:"));
		result.add(layerCombo);

		JSlider zoomSelection = new JSlider(1, 10, 2);
		zoomSelection.setPaintTicks(true);
		zoomSelection.setPaintLabels(true);
		zoomSelection.setSnapToTicks(true);
		zoomSelection.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent changeEvent) {
				JSlider zoomer = (JSlider) changeEvent.getSource();
				loupe.setZoomLevel(zoomer.getValue());
			}

		});

		result.add(Box.createHorizontalStrut(20));
		result.add(new JLabel("Zoom: "));
		result.add(new JLabel("1"));
		result.add(zoomSelection);
		result.add(new JLabel("10"));

		return result;
	}

	private void createLoupe() {
		loupe = new Loupe(layeredPane);
		loupe.setSize(loupe.getPreferredSize());
		loupe.setLocation(0, 0);
		layeredPane.add(loupe, new Integer(JLayeredPane.DEFAULT_LAYER.intValue() + 1));
	}

	private void createLayers() {
		int x = 0;
		int w = 0;
		for (int i = 0; i < images.length; i++) {
			Icon nextIcon = images[i];
			w = nextIcon.getIconWidth();
			JLabel nextLabel = new JLabel(nextIcon);
			nextLabel.setBounds(x, 0, w, nextIcon.getIconHeight());
			x += w;
			layeredPane
					.add(nextLabel, new Integer(JLayeredPane.DEFAULT_LAYER.intValue() + (i + 1)));
		}
	}

	public static void main(String[] args) {
		JFrame f = new LoupeDemo();
		f.setSize(800, 400);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
