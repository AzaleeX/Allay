package org.allaymc.server.gui;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.jgoodies.forms.layout.FormLayout;
import org.allaymc.api.eventbus.EventHandler;
import org.allaymc.api.eventbus.event.server.player.PlayerInitializedEvent;
import org.allaymc.api.eventbus.event.server.player.PlayerQuitEvent;
import org.allaymc.api.server.Server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Allay Project 2024/5/19
 *
 * @author daoge_cmd
 */
public class Dashboard {
    private static Dashboard INSTANCE;

    private JPanel rootPane;
    private JTabbedPane tabbedPane;
    private JPanel playerTab;
    private JPanel perfTab;
    private JTable playerTable;
    private JPanel pluginTab;
    private JTable pluginTable;
    private JLabel onlinePlayerCount;
    private JTabbedPane tabbedPane1;
    private GraphPanel ramGraph;
    private List<Integer> ramValues;

    public static Dashboard getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        FlatMacDarkLaf.setup();

        // Init the frame
        INSTANCE = new Dashboard();
        JFrame frame = new JFrame("Dashboard");
        frame.setContentPane(INSTANCE.rootPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLocationRelativeTo(null);
        // Set icon
        URL image = Dashboard.class.getClassLoader().getResource("icon.png");
        if (image != null) {
            ImageIcon icon = new ImageIcon(image);
            frame.setIconImage(icon.getImage());
        }
        // Show the frame
        frame.setVisible(true);

        var server = Server.getInstance();
        server.getEventBus().registerListener(INSTANCE);
        // TODO: Update the table if any plugin disabled or enabled
        INSTANCE.updatePluginTable();

        return INSTANCE;
    }

    @EventHandler
    protected void onPlayerJoin(PlayerInitializedEvent event) {
        updateOnlinePlayerCount();
        updateOnlinePlayerTable();
    }

    @EventHandler
    protected void onPlayerQuit(PlayerQuitEvent event) {
        updateOnlinePlayerCount();
        updateOnlinePlayerTable();
    }

    protected void updateOnlinePlayerCount() {
        SwingUtilities.invokeLater(() -> onlinePlayerCount.setText("Online: " + Server.getInstance().getOnlinePlayerCount()));
    }

    protected void updateOnlinePlayerTable() {
        var title = new String[] {"Name", "Address", "UUID"};
        var players = Server.getInstance().getOnlinePlayers().values();
        String[][] data;
        if (players.isEmpty()) {
            data = new String[3][0];
        } else {
            data = new String[3][players.size() - 1];
            int row = 0;
            for (var player : players) {
                data[row] = new String[]{
                        player.getOriginName(),
                        player.getClientSession().getSocketAddress().toString(),
                        player.getUUID().toString()
                };
                row++;
            }
        }
        var model = new UneditableDefaultTableModel(data, title);
        SwingUtilities.invokeLater(() -> playerTable.setModel(model));
    }

    protected void updatePluginTable() {
        var title = new String[] {"Name", "Description", "Version", "Author"};
        var plugins = Server.getInstance().getPluginManager().getPlugins().values();
        var data = new String[4][plugins.size() - 1];
        int row = 0;
        for (var plugin : plugins) {
            var descriptor = plugin.descriptor();
            data[row] = new String[] {
                    descriptor.getName(),
                    descriptor.getDescription().isBlank() ? "N/A" : descriptor.getDescription(),
                    descriptor.getVersion(),
                    String.join(", ", descriptor.getAuthors())
            };
            row++;
        }
        var model = new UneditableDefaultTableModel(data, title);
        SwingUtilities.invokeLater(() -> pluginTable.setModel(model));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPane = new JPanel();
        rootPane.setLayout(new GridLayoutManager(1, 1, new Insets(8, 8, 8, 8), -1, -1));
        tabbedPane = new JTabbedPane();
        rootPane.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        playerTab = new JPanel();
        playerTab.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("Players", playerTab);
        final JLabel label1 = new JLabel();
        label1.setEnabled(true);
        label1.setHorizontalAlignment(2);
        label1.setText("Online: ");
        playerTab.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        playerTab.add(scrollPane1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        playerTable = new JTable();
        playerTable.setAutoCreateRowSorter(true);
        scrollPane1.setViewportView(playerTable);
        perfTab = new JPanel();
        perfTab.setLayout(new FormLayout("", ""));
        tabbedPane.addTab("Performance", perfTab);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPane;
    }

    private static final long MEGABYTE = 1024L * 1024L;

    private void createUIComponents() {
        ramGraph = new GraphPanel();
        ramValues = new ArrayList<>();
        // Set the ram graph to 0
        for (int i = 0; i < 50; i++) {
            ramValues.add(0);
        }
        ramGraph.setValues(ramValues);
        ramGraph.setXLabel("Memory Usage");

        Runnable periodicTask = () -> {
            // Update ram graph
            final long freeMemory = Runtime.getRuntime().freeMemory();
            final long totalMemory = Runtime.getRuntime().totalMemory();
            final int freePercent = (int) (freeMemory * 100.0 / totalMemory + 0.5);
            ramValues.add(100 - freePercent);

            ramGraph.setXLabel("Usage: " + String.format("%,d", (totalMemory - freeMemory) / MEGABYTE) + "mb (" + freePercent + "% free)");

            // Trim the list
            int k = ramValues.size();
            if (k > 50)
                ramValues.subList(0, k - 50).clear();

            // Update the graph
            ramGraph.setValues(ramValues);
        };

        // SwingUtilities.invokeLater is called so that we don't run into threading issues with the GUI
        Server.getInstance().getScheduler().scheduleRepeating(
                Server.getInstance(),
                () -> {
                    SwingUtilities.invokeLater(periodicTask);
                    return true;},
                20);
    }

    private static class UneditableDefaultTableModel extends DefaultTableModel {
        public UneditableDefaultTableModel(String[][] data, String[] title) {super(data, title);}

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
