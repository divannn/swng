public class XXX extends JFrame {

	public XXX() {
		super(XXX.class.getSimpleName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(createContents());
	}
	
	private JComponent createContents() {
		JPanel result = new JPanel();
		return result;
	}
	
	public static void main(String[] args) {
		JFrame f = new XXX();
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
