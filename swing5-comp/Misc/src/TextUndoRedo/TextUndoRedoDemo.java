package TextUndoRedo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

/**
 * @author idanilov
 * @jdk 1.5
 */
public class TextUndoRedoDemo extends JFrame {

    public TextUndoRedoDemo() {
        super(TextUndoRedoDemo.class.getSimpleName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createContents());
    }

    private JComponent createContents() {
        JPanel result = new JPanel(new BorderLayout());
        JTextArea texArea = new JTextArea("some text");
        result.add(texArea, BorderLayout.CENTER);

        final UndoManager undo = new UndoManager();
        Document doc = texArea.getDocument();

        doc.addUndoableEditListener(new UndoableEditListener() {

            public void undoableEditHappened(UndoableEditEvent evt) {
                undo.addEdit(evt.getEdit());
            }
        });
        texArea.getActionMap().put("Undo", new AbstractAction("Undo") {

            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undo.canUndo()) {
                        undo.undo();
                    }
                } catch (CannotUndoException e) {
                }
            }
        });
        texArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        texArea.getActionMap().put("Redo", new AbstractAction("Redo") {

            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undo.canRedo()) {
                        undo.redo();
                    }
                } catch (CannotRedoException e) {
                }
            }
        });
        texArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
        return result;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame f = new TextUndoRedoDemo();
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

}
