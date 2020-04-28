import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Sun May 22 17:17:11 PDT 2005
 */



/**
 * @author Jean Bovet
 */
public class DialogUpdateAvailable extends JDialog {
	public DialogUpdateAvailable(Frame owner) {
		super(owner);
		initComponents();
	}

	public DialogUpdateAvailable(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPane = new JPanel();
		infoLabel = new JLabel();
		scrollPane1 = new JScrollPane();
		infoText = new JTextArea();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		CellConstraints cc = new CellConstraints();

		//======== this ========
		setTitle("Check for Updates");
		Container contentPane2 = getContentPane();
		contentPane2.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(Borders.DIALOG_BORDER);
			dialogPane.setPreferredSize(new Dimension(400, 200));
			dialogPane.setLayout(new BorderLayout());
			
			//======== contentPane ========
			{
				contentPane.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, FormSpec.DEFAULT_GROW)
					},
					new RowSpec[] {
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.LINE_GAP_ROWSPEC,
						new RowSpec(RowSpec.CENTER, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
						FormFactory.LINE_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC
					}));
				
				//---- infoLabel ----
				infoLabel.setText("A new version of ANTLRWorks is available:");
				contentPane.add(infoLabel, cc.xy(1, 1));
				
				//======== scrollPane1 ========
				{
					scrollPane1.setOpaque(false);
					
					//---- infoText ----
					infoText.setBorder(null);
					infoText.setEditable(false);
					infoText.setLineWrap(true);
					infoText.setOpaque(false);
					infoText.setText("New version available");
					infoText.setWrapStyleWord(true);
					scrollPane1.setViewportView(infoText);
				}
				contentPane.add(scrollPane1, cc.xywh(1, 3, 3, 3));
			}
			dialogPane.add(contentPane, BorderLayout.CENTER);
			
			//======== buttonBar ========
			{
				buttonBar.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
				buttonBar.setLayout(new FormLayout(
					new ColumnSpec[] {
						FormFactory.GLUE_COLSPEC,
						FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
						FormFactory.BUTTON_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.BUTTON_COLSPEC
					},
					RowSpec.decodeSpecs("pref")));
				
				//---- okButton ----
				okButton.setText("Download");
				buttonBar.add(okButton, cc.xy(3, 1));
				
				//---- cancelButton ----
				cancelButton.setText("Cancel");
				buttonBar.add(cancelButton, cc.xy(5, 1));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane2.add(dialogPane, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPane;
	private JLabel infoLabel;
	private JScrollPane scrollPane1;
	private JTextArea infoText;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
