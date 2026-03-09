import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;

/**
 * A JFrame for text manipulation.
 * 
 * @author Hendrik Speleers
 * @author NMCGJ, AY 2025-2026
 */
public class TextFrame extends JFrame {

   private static final long serialVersionUID = 1L;

   /**
    * Constructs a text frame with a given title.
    * 
    * @param title
    *           the frame title
    */
   public TextFrame(String title) {
      setTitle(title);
      setFrameOptions();
      addComponents();
      addMenuBar();
   }

   /**
    * Visualizes the text frame.
    */
   public void start() {
      setVisible(true);
   }

   /**
    * Closes the text frame.
    */
   public void close() {
      setVisible(false);
      dispose();
   }

   /**
    * Sets the general options for the frame.
    */
   protected void setFrameOptions() {
      Toolkit toolkit = Toolkit.getDefaultToolkit();
      Dimension dimension = toolkit.getScreenSize();

      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setBounds(dimension.width / 6, dimension.height / 6,
            dimension.width * 2 / 3, dimension.height * 2 / 3);
   }

   protected JMenuBar menuBar;
   protected JMenu menuFile;
   protected JMenuItem menuFileNew, menuFileOpen, menuFileSave, menuFileExit;

   protected File file;
   protected JFileChooser fileChooser;
   protected FileNameExtensionFilter textFilter, javaFilter;

   /**
    * Adds the menu bar to the frame.
    */
   protected void addMenuBar() {
      ActionListener menuFileListener = new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            JMenuItem actionMenu = (JMenuItem) e.getSource();
            if (actionMenu == menuFileNew) {
               newFile();
            } else if (actionMenu == menuFileOpen) {
               openFile();
            } else if (actionMenu == menuFileSave) {
               saveFile();
            } else if (actionMenu == menuFileExit) {
               close();
            }
         }
      };

      fileChooser = new JFileChooser();
      textFilter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
      javaFilter = new FileNameExtensionFilter("Java Files (*.java)", "java");
      fileChooser.addChoosableFileFilter(textFilter);
      fileChooser.addChoosableFileFilter(javaFilter);
      fileChooser.setAcceptAllFileFilterUsed(false);
      file = new File("");

      menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      menuFile = new JMenu("File");
      menuFile.setMnemonic(KeyEvent.VK_F);
      menuBar.add(menuFile);

      menuFileNew = new JMenuItem("New");
      menuFileNew.setMnemonic(KeyEvent.VK_N);
      menuFileNew.addActionListener(menuFileListener);
      menuFile.add(menuFileNew);

      menuFileOpen = new JMenuItem("Open");
      menuFileOpen.setMnemonic(KeyEvent.VK_O);
      menuFileOpen.addActionListener(menuFileListener);
      menuFile.add(menuFileOpen);

      menuFileSave = new JMenuItem("Save");
      menuFileSave.setMnemonic(KeyEvent.VK_S);
      menuFileSave.addActionListener(menuFileListener);
      menuFile.add(menuFileSave);

      menuFile.addSeparator();

      menuFileExit = new JMenuItem("Exit");
      menuFileExit.setMnemonic(KeyEvent.VK_X);
      menuFileExit.addActionListener(menuFileListener);
      menuFile.add(menuFileExit);
   }

   /**
    * Indicates whether the menu bar is visible or not.
    * 
    * @param visible
    *           the new status
    */
   public void setMenuVisible(boolean visible) {
      menuBar.setVisible(visible);
   }

   protected JTextArea textArea;
   protected JScrollPane scrollPane;

   /**
    * Adds the components to the frame.
    */
   protected void addComponents() {
      textArea = new JTextArea();
      textArea.setEditable(false);
      textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
      textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      scrollPane = new JScrollPane(textArea);

      JPanel textPanel = new JPanel();
      textPanel.setLayout(new BorderLayout());
      textPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
      textPanel.add(scrollPane, BorderLayout.CENTER);

      Container contentPane = getContentPane();
      contentPane.add(textPanel, BorderLayout.CENTER);
   }

   /**
    * Clears the text (and possibly the related file pointer).
    */
   protected void newFile() {
      file = new File("");
      clearText();
   }

   /**
    * Opens a file and the text.
    */
   protected void openFile() {
      fileChooser.setDialogTitle("Open File");
      fileChooser.setSelectedFile(file);
      int rVal = fileChooser.showOpenDialog(this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
         file = fileChooser.getSelectedFile();
         loadTextFile(file);
      }
   }

   /**
    * Saves the text to a file.
    */
   protected void saveFile() {
      fileChooser.setDialogTitle("Save File");
      fileChooser.setSelectedFile(file);
      int rVal = fileChooser.showSaveDialog(this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
         file = fileChooser.getSelectedFile();
         saveTextFile(file);
      }
   }
   
   /**
    * Indicates whether the text field is scrollable or not.
    * 
    * @param scrollable
    *           the new status
    */
   public void setTextScrollable(boolean scrollable) {
      int hpolicy = (scrollable) ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED 
                                 : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
      int vpolicy = (scrollable) ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED 
                                 : JScrollPane.VERTICAL_SCROLLBAR_NEVER;
      scrollPane.setHorizontalScrollBarPolicy(hpolicy);
      scrollPane.setVerticalScrollBarPolicy(vpolicy);
   }

   /**
    * Indicates whether the text field is editable or not.
    * 
    * @param editable
    *           the new status
    */
   public void setTextEditable(boolean editable) {
      textArea.setEditable(editable);
   }

   /**
    * Gets the content of the text field.
    * 
    * @return the current text
    */
   public String getText() {
      return textArea.getText();
   }

   /**
    * Sets the content of the text field.
    * 
    * @param text
    *           the new text
    */
   public void setText(String text) {
      textArea.setText(text);
   }

   /**
    * Adds content to the text field.
    * 
    * @param text
    *           the added text
    */
   public void addText(String text) {
      textArea.append(text);
   }

   /**
    * Clears the text field.
    */
   public void clearText() {
      textArea.setText("");
   }

   /**
    * Loads the content from a file to the text field.
    * 
    * @param file
    *           the file
    */
   public void loadTextFile(File file) {
      try {
         if (file.canRead()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder text = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
               text.append(line + "\n");
            }
            reader.close();
            setText(text.toString());
         }
      } catch (IOException exc) {
         showErrorDialog("Failed to load text from file.");
      }
   }

   /**
    * Saves the content from the text field to a file.
    * 
    * @param file
    *           the file
    */
   public void saveTextFile(File file) {
      try {
         file.createNewFile();
         if (file.canWrite()) {
            PrintWriter writer = new PrintWriter(new BufferedWriter(
                  new FileWriter(file)));
            writer.print(getText());
            writer.close();
         }
      } catch (IOException exc) {
         showErrorDialog("Failed to save text to file.");
      }
   }

   /**
    * Sets the preferred dimension of the text field.
    * 
    * @param rows
    *           the number of rows in the field
    * @param columns
    *           the number of columns in the field
    */
   protected void setTextDimension(int rows, int columns) {
      textArea.setColumns(columns);
      textArea.setRows(rows);
      pack();
   }

   /**
    * Shows a dialog with a given message.
    * 
    * @param message
    *           the message
    * @param title
    *           the dialog title
    */
   protected void showMessageDialog(String message, String title) {
      JOptionPane.showMessageDialog(this, message, title,
            JOptionPane.PLAIN_MESSAGE);
   }

   /**
    * Shows a dialog with a given error message.
    * 
    * @param message
    *           the error message
    */
   protected void showErrorDialog(String message) {
      JOptionPane.showMessageDialog(this, message, "Error",
            JOptionPane.ERROR_MESSAGE);
   }

   /**
    * A small test of the TextFrame class.
    */
   public static void main(String[] args) {
      TextFrame frame = new TextFrame("My Text Frame");
      frame.setMenuVisible(true);
      frame.setTextEditable(true);
      frame.setText("Hello, world!");
      frame.start();
   }

}