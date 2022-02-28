package co.edu.uptc.presentacion;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * The Class TablePanel.
 */
public class TablePanel extends JPanel{
	
	/** The sp table. */
	private JScrollPane spTable;
	
	/** The dtm table mode. */
	private DefaultTableModel dtmTableMode;
	
	/** The t table. */
	private JTable tTable;
	
	/** The head. */
	private String[] head;
	
	/** The width. */
	private int width;
	
	/** The height. */
	private int height;
	
	/**
	 * Instantiates a new table panel.
	 *
	 * @param head   the head
	 * @param width  the width
	 * @param height the height
	 */
	public TablePanel(String[] head, int width, int height) {
		setLayout(null);
		this.head = head;
		this.width = width;
		this.height = height;
		initialize();
		insert();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		dtmTableMode = new DefaultTableModel();
		dtmTableMode.setColumnIdentifiers(head);
		tTable = new JTable(dtmTableMode);
		spTable = new JScrollPane(tTable);
		spTable.setBounds(0, 0, width, height);
	}

	/**
	 * Insert.
	 */
	private void insert() {
		add(spTable);
	}
	
	/**
	 * Assign controller.
	 *
	 * @param controller the controller
	 */
//	public void assignController(Control controller) {
//		tTable.addMouseListener(controller);
//	}
	
	/**
	 * Clean table.
	 */
	public void cleanTable(){
		int rows = dtmTableMode.getRowCount();
		for (int i = 0; i < rows; i++) {
			dtmTableMode.removeRow(0);
		}
	}

	/**
	 * Show table.
	 *
	 * @param data the data
	 */
	public void showTable(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			if (data[i][0]!=null) {
				dtmTableMode.addRow(data[i]);
			}
		}
	}
	
	/**
	 * Gets the exactly value at.
	 *
	 * @return the exactly value at
	 */
	public String getExactlyValueAt() {
		try {
			if (!((String) tTable.getValueAt(getSelectedRow(), getSelectedColumn())).equals("")) {
				return (String) tTable.getValueAt(getSelectedRow(), getSelectedColumn());
			}else;
			return "-1";
		} catch (Exception e) {
			return "-1";
		}
	}
	
	/**
	 * Gets the value at.
	 *
	 * @return the value at
	 */
	public String getValueAt() {
		try {
			return (String) tTable.getValueAt(getSelectedRow(), 0);
		} catch (Exception e) {
			return "-1";
		}
	}
	
	
	/**
	 * Gets the selected row.
	 *
	 * @return the selected row
	 */
	public int getSelectedRow() {
		return tTable.getSelectedRow();
	}
	
	/**
	 * Gets the selected column.
	 *
	 * @return the selected column
	 */
	public int getSelectedColumn() {
		return tTable.getSelectedColumn();
	}
	
}
