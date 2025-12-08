package vista.componentes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Tabla con modelo no editable para listados generales.
 */
public class TablaDefault extends JTable {

    public TablaDefault(DefaultTableModel modelo) {
        super(modelo);
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
}
