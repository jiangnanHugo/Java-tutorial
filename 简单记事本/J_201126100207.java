package j_201126100207;

import java.awt.*;
import java.awt.event.*;
import static java.awt.event.InputEvent.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

public class J_201126100207 extends JFrame {

    public final static String imagePath = "image/";
    public final static Icon NEW24 = new ImageIcon(imagePath + "New24.gif", "new");
    public final static Icon OPEN24 = new ImageIcon(imagePath + "Open24.gif", "open");
    public final static Icon SAVE24 = new ImageIcon(imagePath + "Save24.gif", "save");
    //内部成员；
    private JFileChooser choose;
    private File selectedFile = null;
    private JTextArea textarea;
    private boolean newPage = false;
    private Font font;
//===========================================================================================================

    public J_201126100207(String title) {
        Container content = getContentPane();
        setTitle(title);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JMenuBar mb = new JMenuBar();
        setJMenuBar(mb);

        //菜单栏标题及内容设置
        JMenu fileMenu = new JMenu("文件(F)");
        mb.add(fileMenu);
        fileMenu.setMnemonic('F');
        JMenu javaTest = new JMenu("Java上机题目");
        mb.add(javaTest);
        JMenu communication = new JMenu("通讯录(C)");
        mb.add(communication);
        communication.setMnemonic('C');
        JMenu help = new JMenu("帮助(H)");
        mb.add(help);
        help.setMnemonic('H');
        //文件一栏的菜单
        JMenuItem newItem = new JMenuItem("新建(N)");
        fileMenu.add(newItem);
        JMenuItem openItem = new JMenuItem("打开(O)...");
        fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("保存(S)");
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        JMenuItem fontColorItem = new JMenuItem("字体与颜色(F)...");
        fileMenu.add(fontColorItem);
        fileMenu.addSeparator();
        JMenuItem exitItem = new JMenuItem("退出(X)");
        fileMenu.add(exitItem);
        //Java上机题目一栏的菜单
        JMenuItem repreatItem = new JMenuItem("回文数(H)...");
        javaTest.add(repreatItem);
        JMenuItem translationItem = new JMenuItem("数字与英语互译(T)...");
        javaTest.add(translationItem);
        JMenuItem countItem = new JMenuItem("统计英文数据(C)...");
        javaTest.add(countItem);
        JMenuItem sumItem = new JMenuItem("文本文件求和(M)...");
        javaTest.add(sumItem);
        //通讯录一栏的菜单
        JMenuItem maintainItem = new JMenuItem("通讯维护(W)...");
        communication.add(maintainItem);
        communication.addSeparator();
        JMenuItem setupItem = new JMenuItem("通讯录存储文件设置(L)...");
        communication.add(setupItem);
        JMenuItem aboutItem = new JMenuItem("关于(G)...");
        help.add(aboutItem);

        //设置textArea：添加滚动条，设置默认字体，及背景颜色
        textarea = new JTextArea();
        JScrollPane scorl = new JScrollPane(textarea);
        content.add(scorl);
        //font= new Font("宋体", Font.PLAIN, 14);
        textarea.setFont(font);
        //toolbar组件定义
        JToolBar toolBar = new JToolBar();
        JButton newButton = new JButton(NEW24);
        newButton.setToolTipText("新建文件");
        JButton openButton = new JButton(OPEN24);
        openButton.setToolTipText("打开文件");
        JButton saveButton = new JButton(SAVE24);
        saveButton.setToolTipText("保存文件");
        toolBar.add(newButton);
        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.setFloatable(false);

        JPanel pane = new JPanel();
        BorderLayout bord = new BorderLayout();
        pane.setLayout(bord);
        pane.add("North", toolBar);
        pane.add("Center", scorl);
        content.add(pane);
        //快捷键设置
        newItem.setAccelerator(KeyStroke.getKeyStroke('N', CTRL_DOWN_MASK));
        openItem.setAccelerator(KeyStroke.getKeyStroke('O', CTRL_DOWN_MASK));
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S', CTRL_DOWN_MASK));
        fontColorItem.setAccelerator(KeyStroke.getKeyStroke('F', CTRL_DOWN_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke('X', CTRL_DOWN_MASK));
        repreatItem.setAccelerator(KeyStroke.getKeyStroke('H', CTRL_DOWN_MASK));
        translationItem.setAccelerator(KeyStroke.getKeyStroke('T', CTRL_DOWN_MASK));
        countItem.setAccelerator(KeyStroke.getKeyStroke('C', CTRL_DOWN_MASK));
        sumItem.setAccelerator(KeyStroke.getKeyStroke('M', CTRL_DOWN_MASK));
        maintainItem.setAccelerator(KeyStroke.getKeyStroke('W', CTRL_DOWN_MASK));
        setupItem.setAccelerator(KeyStroke.getKeyStroke('L', CTRL_DOWN_MASK));
        aboutItem.setAccelerator(KeyStroke.getKeyStroke('G', CTRL_DOWN_MASK));

//===================================================================================================================       
        //事件处理：
        //窗口关闭
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                if (selectedFile == null && textarea.getText().equals("")) {
                    int retuenValue = JOptionPane.showConfirmDialog(null, " 确定要退出系统吗？", "是否退出", JOptionPane.YES_NO_OPTION);
                    if (retuenValue == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    int btn = JOptionPane.showConfirmDialog(null, "是否保存到"
                            + selectedFile + "?", "是否保存",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                    } else if (btn == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    } else {
                        if (selectedFile == null
                                && !textarea.getText().equals("")) {
                            choose = new JFileChooser();
                            int state = choose.showSaveDialog(J_201126100207.this);
                            if (state == JFileChooser.APPROVE_OPTION) {
                                try {
                                    File file = choose.getSelectedFile();
                                    BufferedWriter bw = new BufferedWriter(
                                            new FileWriter(file));
                                    String str = textarea.getText();
                                    String[] lines = str.split("\n");
                                    for (String line : lines) {
                                        bw.write(line + "\r\n");
                                    }
                                    bw.flush();
                                    file.createNewFile();
                                    bw.close();
                                } catch (IOException e) {
                                    JOptionPane.showConfirmDialog(null,
                                            "保存文件失败！", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            System.exit(0);
                        } else if (selectedFile != null) {
                            try {
                                BufferedWriter bw = new BufferedWriter(
                                        new FileWriter(selectedFile));
                                String str = textarea.getText();
                                String[] lines = str.split("\n");
                                for (String line : lines) {
                                    bw.write(line + "\r\n");
                                }
                                bw.flush();
                                bw.close();
                            } catch (IOException e) {
                            }
                            System.exit(0);
                        }
                    }
                }
            }
        });

        //新建按钮
        newItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null && textarea.getText().equals("")) {
                    return;
                } else {
                    int btn = JOptionPane.showConfirmDialog(J_201126100207.this, "是否保存到...?", "是否保存", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (btn == JOptionPane.YES_OPTION) {
                        if (!textarea.getText().equals("")) {
                            JFileChooser choose = new JFileChooser();
                            int state = choose.showSaveDialog(J_201126100207.this);
                            if (state == JFileChooser.APPROVE_OPTION) {
                                try {
                                    File file = choose.getSelectedFile();
                                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                                    String str = textarea.getText();
                                    String[] lines = str.split("\n");
                                    for (String line : lines) {
                                        bw.write(line + "\r\n");
                                    }
                                    bw.flush();
                                    file.createNewFile();
                                    bw.close();

                                } catch (IOException ex) {
                                    JOptionPane.showConfirmDialog(J_201126100207.this,
                                            "保存文件失败！", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
                textarea.setText("");//清空textarea中的内容;

            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null && textarea.getText().equals("")) {
                    return;
                } else {
                    int btn = JOptionPane.showConfirmDialog(J_201126100207.this, "是否保存到...?", "是否保存", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (btn == JOptionPane.YES_OPTION) {
                        if (!textarea.getText().equals("")) {
                            choose = new JFileChooser();
                            int state = choose.showSaveDialog(J_201126100207.this);
                            if (state == JFileChooser.APPROVE_OPTION) {
                                try {
                                    File file = choose.getSelectedFile();
                                    BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                                    String str = textarea.getText();
                                    String[] lines = str.split("\n");
                                    for (String line : lines) {
                                        bw.write(line + "\r\n");
                                    }
                                    bw.flush();
                                    file.createNewFile();
                                    bw.close();
                                } catch (IOException ex) {
                                    JOptionPane.showConfirmDialog(J_201126100207.this,
                                            "保存文件失败！", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
                textarea.setText("");//清空textarea中的内容;
            }
        });


        //打开按钮
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null && textarea.getText().equals("")) {
                } else {
                    int btn = JOptionPane.showConfirmDialog(J_201126100207.this,
                            "是否保存到...?", "是否保存",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (btn == JOptionPane.YES_OPTION) {
                    }
                }
                choose = new JFileChooser();
                choose.addChoosableFileFilter(new fileFilter("java"));
                choose.addChoosableFileFilter(new fileFilter("txt"));
                choose.addChoosableFileFilter(new fileFilter("cpp"));
                int state = choose.showOpenDialog(J_201126100207.this);
                if (state == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedFile = choose.getSelectedFile();
                        BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                        textarea.read(br, "");
                        br.close();
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(J_201126100207.this,
                                "打开文件失败", "error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //打开图标
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null && textarea.getText().equals("")) {
                } else {
                    int btn = JOptionPane.showConfirmDialog(J_201126100207.this,
                            "是否保存到...?", "是否保存",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (btn == JOptionPane.YES_OPTION) {
                    }
                }
                choose = new JFileChooser();
                choose.addChoosableFileFilter(new fileFilter("java"));
                choose.addChoosableFileFilter(new fileFilter("txt"));
                choose.addChoosableFileFilter(new fileFilter("cpp"));
                int state = choose.showOpenDialog(J_201126100207.this);
                if (state == JFileChooser.APPROVE_OPTION) {
                    try {
                        selectedFile = choose.getSelectedFile();
                        BufferedReader br = new BufferedReader(new FileReader(selectedFile));
                        textarea.read(br, "");
                        br.close();
                    } catch (IOException ex) {
                        JOptionPane.showConfirmDialog(J_201126100207.this,
                                "打开文件失败", "error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //保存按钮
        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (newPage == true || selectedFile == null) {
                    choose = new JFileChooser();
                    int state = choose.showSaveDialog(J_201126100207.this);
                    if (state == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = choose.getSelectedFile();
                            BufferedWriter bw = new BufferedWriter(
                                    new FileWriter(file));
                            String str = textarea.getText();
                            String[] lines = str.split("\n");
                            for (String line : lines) {
                                bw.write(line + "\r\n");
                            }
                            bw.flush();
                            file.createNewFile();
                            bw.close();
                            newPage = false;
                            selectedFile = file;
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(J_201126100207.this, "保存文件失败",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (newPage == false && selectedFile != null) {

                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(
                                selectedFile));
                        String str = textarea.getText();
                        String[] lines = str.split("\n");
                        for (String line : lines) {
                            bw.write(line + "\r\n");
                        }
                        bw.flush();
                        bw.close();
                        newPage = false;
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(J_201126100207.this, "保存文件失败",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        //保存图标
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (newPage == true || selectedFile == null) {
                    choose = new JFileChooser();
                    int state = choose.showSaveDialog(J_201126100207.this);
                    if (state == JFileChooser.APPROVE_OPTION) {
                        try {
                            File file = choose.getSelectedFile();
                            BufferedWriter bw = new BufferedWriter(
                                    new FileWriter(file));
                            String str = textarea.getText();
                            String[] lines = str.split("\n");
                            for (String line : lines) {
                                bw.write(line + "\r\n");
                            }
                            bw.flush();
                            file.createNewFile();
                            bw.close();
                            newPage = false;
                            selectedFile = file;
                        } catch (IOException e) {
                            JOptionPane.showMessageDialog(J_201126100207.this, "保存文件失败",
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (newPage == false && selectedFile != null) {

                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(
                                selectedFile));
                        String str = textarea.getText();
                        String[] lines = str.split("\n");
                        for (String line : lines) {
                            bw.write(line + "\r\n");
                        }
                        bw.flush();
                        bw.close();
                        newPage = false;
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(J_201126100207.this, "保存文件失败",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        //字体颜色设置
        fontColorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FontDialog fontdialog = new FontDialog(J_201126100207.this);


            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                if (selectedFile == null && textarea.getText().equals("")) {
                    int retuenValue = JOptionPane.showConfirmDialog(null, " 确定要退出系统吗？", "是否退出", JOptionPane.YES_NO_OPTION);
                    if (retuenValue == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                } else {
                    int btn = JOptionPane.showConfirmDialog(null, "是否保存到"
                            + selectedFile + "?", "是否保存",
                            JOptionPane.YES_NO_CANCEL_OPTION);
                    if (btn == JOptionPane.CANCEL_OPTION) {
                        return;
                    } else if (btn == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    } else {
                        if (selectedFile == null
                                && !textarea.getText().equals("")) {
                            choose = new JFileChooser();
                            int state = choose.showSaveDialog(J_201126100207.this);
                            if (state == JFileChooser.APPROVE_OPTION) {
                                try {
                                    File file = choose.getSelectedFile();
                                    BufferedWriter bw = new BufferedWriter(
                                            new FileWriter(file));
                                    String str = textarea.getText();
                                    String[] lines = str.split("\n");
                                    for (String line : lines) {
                                        bw.write(line + "\r\n");
                                    }
                                    bw.flush();
                                    file.createNewFile();
                                    bw.close();
                                } catch (IOException e) {
                                    JOptionPane.showConfirmDialog(null,
                                            "保存文件失败！", "ERROR",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            System.exit(0);
                        } else if (selectedFile != null) {
                            try {
                                BufferedWriter bw = new BufferedWriter(
                                        new FileWriter(selectedFile));
                                String str = textarea.getText();
                                String[] lines = str.split("\n");
                                for (String line : lines) {
                                    bw.write(line + "\r\n");
                                }
                                bw.flush();
                                bw.close();
                            } catch (IOException e) {
                            }
                            System.exit(0);
                        }
                    }
                }
            }
        });

        repreatItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                repreatDialog repreat = new repreatDialog(J_201126100207.this);
            }
        });

        translationItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                translateDialog translation = new translateDialog(J_201126100207.this);
            }
        });


        countItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                countDialog CountDialog = new countDialog(J_201126100207.this);

            }
        });

        sumItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                sumDialog sum = new sumDialog(J_201126100207.this);
            }
        });

        //关于按钮
        aboutItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(J_201126100207.this, "Java记事本" + "\n"
                        + "designed by 姜楠\n" + " in ZJUT", "关于",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }
//================================================================================================================

    /**
     * 内部类定义以及实现
     */
    private class FontDialog extends JDialog implements ActionListener, ItemListener {

        private JButton fontColorButton;
        private JButton backGroundButton;
        private JButton okButton;
        private JButton cancelButton;
        private String fontName;
        private int fontSize;
        private Font font;
        private JComboBox nameCombo;
        private JComboBox sizeCombo;
        private JLabel fontDisplay;

        public FontDialog(J_201126100207 window) {
            GridLayout grid = new GridLayout(6, 2);
            setLayout(grid);
            setModal(true);
            setTitle("设置颜色与字体");

            font = textarea.getFont();
            fontName = font.getFontName();
            fontSize = font.getSize();

            //获取所有的字体
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String[] fontNames = ge.getAvailableFontFamilyNames();
            nameCombo = new JComboBox(fontNames);
            nameCombo.addItemListener(this);

            //创建和添加组件到FontDialog
            JLabel nameLabel = new JLabel("字体名称");
            add(nameLabel);
            add(nameCombo);

            JLabel sizeLabel = new JLabel("字体大小");
            String[] size = {"8", "10", "12", "14", "16", "18", "20", "22", "24", "26", "28", "30", "32", "34", "42", "52", "62", "72"};
            sizeCombo = new JComboBox(size);
            sizeCombo.addItemListener(this);
            add(sizeLabel);
            add(sizeCombo);

            JLabel fontLabel = new JLabel("字体颜色");
            fontColorButton = new JButton("设置...");
            fontColorButton.addActionListener(this);
            add(fontLabel);
            add(fontColorButton);
            JLabel backGroundLabel = new JLabel("背景颜色");
            backGroundButton = new JButton("设置...");
            backGroundButton.addActionListener(this);
            add(backGroundLabel);
            add(backGroundButton);
            JLabel displayLabel = new JLabel("字体预览");
            fontDisplay = new JLabel("Java 字体预览");
            fontDisplay.setFont(font);
            JPanel displayPanel = new JPanel(true);
            displayPanel.setBackground(Color.WHITE);
            displayPanel.add(fontDisplay);
            add(displayLabel);

            add(displayPanel);
            okButton = new JButton("确定");
            okButton.addActionListener(this);
            cancelButton = new JButton("取消");
            cancelButton.addActionListener(this);
            add(okButton);
            add(cancelButton);
            setSize(600, 300);
            setLocationRelativeTo(window);
            setVisible(true);
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getSource() == nameCombo) {
                    fontName = (String) nameCombo.getSelectedItem();
                } else if (e.getSource() == sizeCombo) {
                    fontSize = Integer.parseInt(sizeCombo.getSelectedItem().toString());
                }
                font = new Font(fontName, font.PLAIN, fontSize);
                fontDisplay.setFont(font);
                fontDisplay.repaint();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fontColorButton) {
                Color color = JColorChooser.showDialog(null, "选择字体颜色", getBackground());
                textarea.setForeground(color);
            } else if (e.getSource() == backGroundButton) {
                Color color = JColorChooser.showDialog(null, "选择背景颜色", getBackground());
                textarea.setForeground(color);
            } else if (e.getSource() == okButton) {
                textarea.setFont(font);               // Set the selected font
                setVisible(false);
            } else if (e.getSource() == cancelButton) {
                setVisible(false);
            }
        }
    }

//================================================================================================================
    private class fileFilter extends FileFilter {

        String ext;

        public fileFilter(String ext) {
            this.ext = ext;
        }

        @Override
        public boolean accept(File file) {
            String name = file.getName().toLowerCase();
            if (name.endsWith(ext) || file.isDirectory()) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        public String getDescription() {
            if (ext.equals("java")) {
                return "*.java";
            } else if (ext.equals("txt")) {
                return "*.txt";
            } else if (ext.equals("cpp")) {
                return "*.cpp";
            }
            return "";
        }
    }
//================================================================================================================

    private class countDialog extends JDialog implements ActionListener {

        JLabel label1, label2, label3;
        JTextField textField1, textField2, textField3;
        JButton okButton;
        JButton cancelButton;

        public countDialog(J_201126100207 window) {
            GridLayout grid = new GridLayout(4, 2);
            setLayout(grid);
            setModal(true);
            setTitle("统计英文数据");

            label1 = new JLabel("以字母w 开头的单词数");
            add(label1);
            textField1 = new JTextField();
            textField1.setEditable(false);
            add(textField1);
            label2 = new JLabel("含'or'字符串的单词数");
            add(label2);
            textField2 = new JTextField();
            textField2.setEditable(false);
            add(textField2);
            label3 = new JLabel("长度为 3  的单词数");
            add(label3);
            textField3 = new JTextField();
            textField3.setEditable(false);
            add(textField3);
            JPanel okPanel = new JPanel();
            okButton = new JButton("确定");
            okPanel.add(okButton);
            okButton.addActionListener(this);
            this.add(okPanel);
            JPanel cancelPanel = new JPanel();
            cancelButton = new JButton("退出");
            cancelPanel.add(cancelButton);
            cancelButton.addActionListener(this);
            this.add(cancelPanel);

            int beginWithW = 0, includeOr = 0, lengthEqualToThree = 0;
            String str = textarea.getText();
            if (!str.equals("")) {
                String[] lines = str.split("\n");
                if (!lines.equals("")) {
                    for (String line : lines) {
                        String t[] = line.split(" ");
                        for (String temp : t) {
                            if (temp.charAt(0) == 'W' || temp.charAt(0) == 'w') {
                                beginWithW++;
                            }
                            if (temp.indexOf("or") != -1) {
                                includeOr++;
                            }
                            if (temp.length() == 3) {
                                lengthEqualToThree++;
                            }
                        }
                    }
                }
            }
            textField1.setText(String.valueOf(beginWithW));
            textField2.setText(String.valueOf(includeOr));
            textField3.setText(String.valueOf(lengthEqualToThree));
            setSize(400, 200);
            setLocationRelativeTo(window);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton || e.getSource() == cancelButton) {
                this.setVisible(false);
            }
        }
    }

    private class translateDialog extends JDialog implements ActionListener {

        JLabel cinLabel, coutLabel;
        JTextField cinTextField, coutTextField;
        JButton okButton;
        JButton cancelButton;
        String x[] = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String y[] = {"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
        String z[] = {"twenty", "thirty", "fourty", "fifty", "sixty", "seventy", "eighty", "ninety"};

        public translateDialog(J_201126100207 window) {
            GridLayout grid = new GridLayout(3, 2);
            setLayout(grid);
            setModal(true);
            setTitle("数字与英文互译");

            cinLabel = new JLabel("请输入一个不大于100的数字或英文：");
            add(cinLabel);
            cinTextField = new JTextField();
            add(cinTextField);
            coutLabel = new JLabel("显示翻译结果：");
            add(coutLabel);
            coutTextField = new JTextField();
            coutTextField.setEditable(false);
            add(coutTextField);

            JPanel okPanel = new JPanel();
            okButton = new JButton("确定");
            okPanel.add(okButton);
            add(okPanel);
            okButton.addActionListener(this);
            JPanel cancelPanel = new JPanel();
            cancelButton = new JButton("取消");
            cancelPanel.add(cancelButton);
            add(cancelPanel);
            cancelButton.addActionListener(this);

            setSize(500, 150);
            setLocationRelativeTo(window);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                String str = cinTextField.getText();
                String trim = str.trim();
                if (trim.equals("")) {
                    JOptionPane.showMessageDialog(translateDialog.this, "未按要求输入，请重新输入",
                            "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (trim.charAt(0) <= '9' && trim.charAt(0) >= '0') {
                        int temp = Integer.parseInt(trim);
                        String s = null;
                        if (temp <= 9) {
                            s = x[temp];
                        }
                        if (temp >= 10 && temp <= 19) {
                            s = y[temp - 10];
                        } else if(temp>=20 && temp<=99){
                            s = z[temp / 10 - 2];
                            if (temp % 10 != 0) {
                                s += " " + x[temp % 10];
                            }
                        }
                        coutTextField.setText(s);
                    } 
                    else {
                        int answer = 0;
                        String[] part = trim.split(" ");
                        for (int i = 0; i < part.length; i++) {
                            if (!part[i].equals("")) {
                                for (int j = 0; j < x.length; j++) {
                                    if(part[i].equals(x[j])){
                                        answer+=j+1;
                                    }
                                }
                                for (int j = 0; j < y.length; j++) {
                                    if(part[i].equals(y[j])){
                                        answer+=j+10;
                                    }
                                }
                                for (int j = 0; j < z.length; j++) {
                                    if(part[i].equals(z[j])){
                                        answer+=(j+2)*10;
                                    }
                                }
                            }
                        }
                       coutTextField.setText(String.valueOf(answer));
                    }

                }
            } else if (e.getSource() == cancelButton) {
                this.setVisible(false);
            }

        }
    }

    private class repreatDialog extends JDialog implements ActionListener {

        JLabel cinLabel;
        JTextField cinTextField;
        JButton judgeButton;
        JButton cancelButton;

        public repreatDialog(J_201126100207 window) {
            GridLayout grid = new GridLayout(2, 2);
            setLayout(grid);
            setModal(true);
            setTitle("回文数");

            cinLabel = new JLabel("输入1-99999之间的整数:");
            add(cinLabel);
            cinTextField = new JTextField();
            add(cinTextField);
            JPanel judgePanel = new JPanel();
            judgeButton = new JButton("判断是否是回文数?");
            judgePanel.add(judgeButton);
            add(judgePanel);
            judgeButton.addActionListener(this);
            JPanel cancelPanel = new JPanel();
            cancelButton = new JButton("取消");
            cancelPanel.add(cancelButton);
            add(cancelPanel);
            cancelButton.addActionListener(this);

            setSize(400, 100);
            setLocationRelativeTo(window);
            setVisible(true);
        }

        private boolean isLegal(String s) {
            if (s.length() > 5) {
                return false;
            } else {
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) > '9' || s.charAt(i) < '0') {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean isReverse(String s) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == judgeButton) {
                String str = cinTextField.getText();
                if (str.equals("")) {
                    JOptionPane.showMessageDialog(repreatDialog.this, "未按要求输入，请重新输入",
                            "错误", JOptionPane.ERROR_MESSAGE);
                } else if (isLegal(str)) {
                    if (isReverse(str)) {
                        JOptionPane.showMessageDialog(repreatDialog.this, str+"是回文数",
                                "确认", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(repreatDialog.this, str+"不是回文数",
                                "确认", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                cinTextField.setText("");

            } else if (e.getSource() == cancelButton) {
                this.setVisible(false);
            }
        }
    }

    private class sumDialog extends JDialog implements ActionListener, ChangeListener {

        JProgressBar progressBar;
        JLabel sumLabel;
        Timer timer;
        JButton okButton;
        int values = 0, number = 0, total = 0;
        String[] lines, temp;

        public sumDialog(J_201126100207 window) {
            GridLayout grid = new GridLayout(3, 1);
            setLayout(grid);
            setModal(true);
            setTitle("文本文件求和");

            sumLabel = new JLabel("", JLabel.CENTER);
            this.add(sumLabel);
            progressBar = new JProgressBar();
            progressBar.setOrientation(JProgressBar.HORIZONTAL);
            progressBar.setMinimum(0);
            progressBar.setMaximum(100);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);
            progressBar.addChangeListener(this);
            progressBar.setPreferredSize(new Dimension(200, 30));
            progressBar.setBorderPainted(true);
            this.add(progressBar);
            timer = new Timer(5, this);
            JPanel okPanel = new JPanel();
            okButton = new JButton("开始计算");
            okPanel.add(okButton);
            add(okPanel);
            okButton.addActionListener(this);
            setSize(400, 200);
            setLocationRelativeTo(window);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == okButton) {
                timer.start();
                number = 0;
                values = 0;
                String str = textarea.getText();
                lines = str.split("\n");
                for (int i = 0; i < lines.length; i++) {
                    temp = lines[i].split(" ");
                    for (int j = 0; j < temp.length; j++) {
                        total++;
                    }
                }
                for (int i = 0; i < lines.length; i++) {
                    for (int j = 0; j < temp.length; j++, number++) {
                        if (!temp[j].equals("")) {
                            values += Integer.parseInt(temp[j]);
                        }
                    }
                }
            } else if (e.getSource() == timer) {
                int value = progressBar.getValue();
                if (value < 100) {
                    value = (int) 100 * number / total;
                    progressBar.setValue(value);
                } else {
                    timer.stop();
                    JOptionPane.showMessageDialog(sumDialog.this, Integer.toString(values),
                            "确认", JOptionPane.INFORMATION_MESSAGE);
                    progressBar.setValue(0);
                }
            }
        }

        @Override
        public void stateChanged(ChangeEvent e1) {
            int value = progressBar.getValue();
            if (e1.getSource() == progressBar) {
                sumLabel.setText("目前已完成进度：" + Integer.toString(value) + "%");
            }
        }
    }
//================================================================================================================    

    //窗口居中函数
    public static void centerWindow(Window f) {
        Toolkit tk = f.getToolkit();
        Dimension dm = tk.getScreenSize();
        f.setLocation((int) (dm.getWidth() - f.getWidth()) / 2, (int) (dm.getHeight() - f.getHeight()) / 2);
    }

    public static void main(String[] args) {
        JFrame fr = new J_201126100207("201126100207-姜楠-Java 程序设计综合实验");
        fr.setSize(1000, 500);
        centerWindow(fr);
        fr.setVisible(true);
    }
}