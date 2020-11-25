package ru.netcracker.practice;

import ru.netcracker.practice.buildings.interfaces.Building;
import ru.netcracker.practice.buildings.interfaces.Floor;
import ru.netcracker.practice.buildings.interfaces.Space;
import ru.netcracker.practice.buildings.util.factory.BuildingFactory;
import ru.netcracker.practice.buildings.util.factory.DwellingFactory;
import ru.netcracker.practice.buildings.util.factory.HotelFactory;
import ru.netcracker.practice.buildings.util.factory.OfficeFactory;
import ru.netcracker.practice.buildings.util.other.Buildings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BuildingForm {
    private JPanel rootPanel;
    private JLabel header1;
    private JLabel header2;
    private JLabel header3;
    private JLabel type;
    private JLabel floors;
    private JLabel buildingArea;
    private JLabel floorNumber;
    private JLabel spaces;
    private JLabel floorArea;
    private JLabel spaceNumber;
    private JLabel rooms;
    private JLabel area;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JScrollPane scrollPane;

    private File buildingFile;

    public BuildingForm() {
        JFrame frame = new JFrame();

        frame.setContentPane(rootPanel);
        frame.setJMenuBar(createMenuBar());

//        frame.setResizable(false);
        frame.setSize(800, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new BuildingForm();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu lookAndFeel = new JMenu("Look&Feel");

        ButtonGroup group = new ButtonGroup();

        for (UIManager.LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(lookAndFeelInfo.getName());

            item.addActionListener(click -> {
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
                    rootPanel.repaint();
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            });
            group.add(item);
            lookAndFeel.add(item);
        }

        JMenuItem dwelling = new JMenuItem("Open dwelling");
        JMenuItem officeBuilding = new JMenuItem("Open office building");
        JMenuItem hotel = new JMenuItem("Open hotel");

        JFileChooser chooser = new JFileChooser("src/main/resources/");
        chooser.setDialogTitle("Выберите файл");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        dwelling.addActionListener(click ->
            actionsOnOpen(chooser, new DwellingFactory())
        );
        officeBuilding.addActionListener(click ->
            actionsOnOpen(chooser, new OfficeFactory())
        );
        hotel.addActionListener(click ->
            actionsOnOpen(chooser, new HotelFactory())
        );

        file.add(dwelling);
        file.add(officeBuilding);
        file.add(hotel);

        menu.add(file);
        menu.add(lookAndFeel);

        return menu;
    }

    private void actionsOnOpen(JFileChooser chooser, BuildingFactory factory) {
        resetFourthPanel();

        buildingFile = getFromChoose(chooser);

        Buildings.setBuildingFactory(factory);

        Building building = readBuilding();

        if (building == null)
            JOptionPane.showMessageDialog(rootPanel,
                    "Choose correct file to read building",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        else
            fillPanes(building);
    }

    private void resetFourthPanel() {
        for (Component panel : panel4.getComponents()) {
            panel4.remove(panel);
            panel4.repaint();
        }
    }

    private File getFromChoose(JFileChooser chooser) {
        int result = chooser.showOpenDialog(rootPanel);
        if (result == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();
        return null;
    }

    private Building readBuilding() {
        if (buildingFile != null) {
            try {
                return Buildings.readBuilding(new FileReader(buildingFile));
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(rootPanel,
                        "Can't read building from that file",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(rootPanel,
                "File is null",
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
        return null;
    }

    private void fillPanes(Building building) {
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.PAGE_AXIS));
        panel4.add(new JLabel("Building schema:"));

        type.setText(building.getClass().getSimpleName());
        floors.setText("Floors amount: " + building.getBuildingFloors());
        buildingArea.setText("Total area: " + building.getBuildingArea());

        Floor[] floors = building.getFloorsAsArray();
        for (int i = 0; i < floors.length; i++) {
            JPanel floorPanel = new JPanel();

            JLabel floorLabel = new JLabel("Floor #" + i);
            floorPanel.add(floorLabel);

            floorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            floorPanel.setLayout(new FlowLayout());

            floorPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    String labelText = floorLabel.getText();

                    Floor floor = building.getFloor(
                            Integer.parseInt(labelText.substring(7)));

                    floorNumber.setText(labelText);
                    spaces.setText("Spaces amount: " + floor.getTotalSpaces());
                    floorArea.setText("Floor total area: " + floor.getTotalArea());
                }
            });

            Space[] spaces = floors[i].getSpacesAsArray();
            for (int j = 0; j < spaces.length; j++) {
                JButton spaceButton = new JButton("Room #" + j);

                spaceButton.addActionListener(click -> {
                    spaceNumber.setText(spaceButton.getText());

                    Space space = building.getFloor(Integer.parseInt(
                            floorLabel.getText().substring(7)
                    )).getSpace(Integer.parseInt(
                            spaceButton.getText().substring(6))
                    );

                    area.setText("Space area: " + space.getArea());
                    rooms.setText("Space total rooms: " + space.getRooms());
                });

                floorPanel.add(spaceButton);
            }

            panel4.add(floorPanel);
        }
    }

    private void createUIComponents() {
    }
}
