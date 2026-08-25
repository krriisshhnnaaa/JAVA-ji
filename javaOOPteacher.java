import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JavaOOPTeacher extends JFrame {
    
    private JTextPane lessonTextPane;
    private JTextArea codeEditor;
    private JTextArea outputConsole;
    private VisualizationPanel vizPanel;
    
    private List<Lesson> lessons;
    private int currentLessonIndex = 0;
    
    public JavaOOPTeacher() {
        setTitle("Java OOP Teacher");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initLessons();
        initUI();
        loadLesson(currentLessonIndex);
    }
    
    private void initLessons() {
        lessons = new ArrayList<>();
        
        // Lesson 1: Basics - Printing
        lessons.add(new Lesson(
            "Lesson 1: Introduction & Output",
            "Welcome to the Java OOP Teacher!\n\n" +
            "Before we dive into Object-Oriented Programming (OOP), let's learn how to make the computer talk.\n" +
            "In Java, we use the 'System.out.println()' command to print text to the screen.\n\n" +
            "Task: Write a program that prints 'Hello, World!' to the console.\n" +
            "Hint: Type System.out.println(\"Hello, World!\"); in the code editor below.",
            "System.out.println(\"Hello, World!\");",
            "Hello, World!",
            VizType.TEXT
        ));
        
        // Lesson 2: Classes and Objects
        lessons.add(new Lesson(
            "Lesson 2: Classes and Objects",
            "At the heart of OOP are 'Classes' and 'Objects'.\n\n" +
            "Think of a Class as a blueprint, and an Object as the actual house built from that blueprint.\n" +
            "You can build many houses from one blueprint.\n\n" +
            "Task: Let's create an object. Suppose we have a blueprint called 'Car'.\n" +
            "Type: Car myCar = new Car();",
            "Car myCar = new Car();",
            "Created a new Car object named myCar!",
            VizType.CLASS_OBJECT
        ));
        
        // Lesson 3: Attributes
        lessons.add(new Lesson(
            "Lesson 3: Attributes (Properties)",
            "Objects can have properties, also called attributes or fields.\n" +
            "For our 'Car' object, an attribute could be its color.\n\n" +
            "Task: Set the color of your car to 'Red'.\n" +
            "Type: myCar.color = \"Red\";",
            "myCar.color = \"Red\";",
            "Set myCar's color to Red!",
            VizType.ATTRIBUTES
        ));
        
        // Lesson 4: Methods
        lessons.add(new Lesson(
            "Lesson 4: Methods (Behaviors)",
            "Objects can also do things! These actions are called methods.\n" +
            "Our Car can start its engine.\n\n" +
            "Task: Call the startEngine method on your car.\n" +
            "Type: myCar.startEngine();",
            "myCar.startEngine();",
            "Vroom! Engine started.",
            VizType.METHODS
        ));
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        
        // --- Top: Lesson Info and Visualization ---
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lessonTextPane = new JTextPane();
        lessonTextPane.setEditable(false);
        lessonTextPane.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane lessonScroll = new JScrollPane(lessonTextPane);
        
        vizPanel = new VisualizationPanel();
        vizPanel.setBorder(BorderFactory.createTitledBorder("Visualization"));
        
        topPanel.add(lessonScroll);
        topPanel.add(vizPanel);
        
        add(topPanel, BorderLayout.CENTER);
        
        // --- Bottom: Code Editor and Console ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(1000, 300));
        
        JPanel editorPanel = new JPanel(new BorderLayout());
        editorPanel.setBorder(BorderFactory.createTitledBorder("Code Editor"));
        codeEditor = new JTextArea();
        codeEditor.setFont(new Font("Monospaced", Font.PLAIN, 16));
        editorPanel.add(new JScrollPane(codeEditor), BorderLayout.CENTER);
        
        JPanel consolePanel = new JPanel(new BorderLayout());
        consolePanel.setBorder(BorderFactory.createTitledBorder("Output Console"));
        consolePanel.setPreferredSize(new Dimension(300, 300));
        outputConsole = new JTextArea();
        outputConsole.setEditable(false);
        outputConsole.setBackground(Color.BLACK);
        outputConsole.setForeground(Color.GREEN);
        outputConsole.setFont(new Font("Monospaced", Font.BOLD, 14));
        consolePanel.add(new JScrollPane(outputConsole), BorderLayout.CENTER);
        
        bottomPanel.add(editorPanel, BorderLayout.CENTER);
        bottomPanel.add(consolePanel, BorderLayout.EAST);
        
        // --- Buttons ---
        JPanel buttonPanel = new JPanel();
        JButton runBtn = new JButton("Run Code");
        JButton prevBtn = new JButton("Previous Lesson");
        JButton nextBtn = new JButton("Next Lesson");
        
        runBtn.addActionListener(e -> runSimulation());
        prevBtn.addActionListener(e -> {
            if (currentLessonIndex > 0) {
                currentLessonIndex--;
                loadLesson(currentLessonIndex);
            }
        });
        nextBtn.addActionListener(e -> {
            if (currentLessonIndex < lessons.size() - 1) {
                currentLessonIndex++;
                loadLesson(currentLessonIndex);
            }
        });
        
        buttonPanel.add(prevBtn);
        buttonPanel.add(runBtn);
        buttonPanel.add(nextBtn);
        
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadLesson(int index) {
        Lesson lesson = lessons.get(index);
        lessonTextPane.setText("=== " + lesson.title + " ===\n\n" + lesson.content);
        codeEditor.setText("");
        outputConsole.setText("Ready.\n");
        vizPanel.setVizType(lesson.vizType);
    }
    
    private void runSimulation() {
        String code = codeEditor.getText().trim();
        Lesson currentLesson = lessons.get(currentLessonIndex);
        
        // Simple string-based simulation
        if (code.isEmpty()) {
            outputConsole.setText("Error: Code editor is empty.");
            return;
        }
        
        // Extremely simple evaluation
        String normalizedCode = code.replaceAll("\\s+", "");
        String expectedCode = currentLesson.expectedCode.replaceAll("\\s+", "");
        
        if (normalizedCode.equals(expectedCode) || normalizedCode.contains(expectedCode)) {
            outputConsole.setText("> " + code + "\n\n" + currentLesson.successOutput + "\n\nSuccess! You can move to the next lesson.");
            // Update visualization to success state if applicable
            vizPanel.setSuccess(true);
        } else {
            if (currentLessonIndex == 0 && code.contains("System.out.print")) {
                 outputConsole.setText("Almost there! Make sure it matches exactly (check capitalization and punctuation).");
            } else {
                 outputConsole.setText("Error: The code doesn't seem to do what's expected for this lesson.\nCheck your syntax and try again.");
            }
             vizPanel.setSuccess(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JavaOOPTeacher().setVisible(true);
        });
    }
}

enum VizType {
    TEXT, CLASS_OBJECT, ATTRIBUTES, METHODS
}

class Lesson {
    String title;
    String content;
    String expectedCode;
    String successOutput;
    VizType vizType;
    
    public Lesson(String title, String content, String expectedCode, String successOutput, VizType vizType) {
        this.title = title;
        this.content = content;
        this.expectedCode = expectedCode;
        this.successOutput = successOutput;
        this.vizType = vizType;
    }
}

class VisualizationPanel extends JPanel {
    private VizType currentViz = VizType.TEXT;
    private boolean isSuccess = false;
    
    public void setVizType(VizType type) {
        this.currentViz = type;
        this.isSuccess = false;
        repaint();
    }
    
    public void setSuccess(boolean success) {
        this.isSuccess = success;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        
        switch (currentViz) {
            case TEXT:
                drawTextViz(g2, w, h);
                break;
            case CLASS_OBJECT:
                drawClassObjectViz(g2, w, h);
                break;
            case ATTRIBUTES:
                drawAttributesViz(g2, w, h);
                break;
            case METHODS:
                drawMethodsViz(g2, w, h);
                break;
        }
    }
    
    private void drawTextViz(Graphics2D g2, int w, int h) {
        g2.setColor(Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String msg = isSuccess ? "Hello, World!" : "Waiting for output...";
        FontMetrics fm = g2.getFontMetrics();
        int x = (w - fm.stringWidth(msg)) / 2;
        int y = (h - fm.getHeight()) / 2 + fm.getAscent();
        
        if (isSuccess) g2.setColor(new Color(0, 150, 0)); // Green on success
        g2.drawString(msg, x, y);
    }
    
    private void drawClassObjectViz(Graphics2D g2, int w, int h) {
        // Draw Blueprint (Class)
        g2.setColor(Color.BLUE);
        g2.drawRect(50, 50, 150, 200);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Blueprint: Car", 60, 80);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.drawString("- wheels", 60, 110);
        g2.drawString("- color", 60, 130);
        
        if (isSuccess) {
            // Draw Object (Instance)
            g2.setColor(new Color(150, 50, 50));
            g2.fillRect(250, 100, 150, 100);
            g2.setColor(Color.WHITE);
            g2.drawString("Object: myCar", 270, 130);
            g2.drawString("Instance of Car", 270, 160);
            
            // Arrow
            g2.setColor(Color.BLACK);
            g2.drawLine(200, 150, 250, 150);
            g2.fillPolygon(new int[]{240, 250, 240}, new int[]{145, 150, 155}, 3);
        }
    }
    
    private void drawAttributesViz(Graphics2D g2, int w, int h) {
        // Draw Object
        g2.setColor(new Color(150, 50, 50));
        g2.fillRect(150, 80, 200, 150);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("myCar", 220, 110);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("wheels = 4", 170, 150);
        
        if (isSuccess) {
            g2.setColor(Color.GREEN);
            g2.drawString("color = \"Red\"", 170, 180);
        } else {
            g2.setColor(Color.LIGHT_GRAY);
            g2.drawString("color = null", 170, 180);
        }
    }
    
    private void drawMethodsViz(Graphics2D g2, int w, int h) {
         // Draw Object
        g2.setColor(new Color(150, 50, 50));
        g2.fillRect(150, 80, 200, 150);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("myCar", 220, 110);
        
        if (isSuccess) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(170, 130, 40, 40);
            g2.setColor(Color.BLACK);
            g2.drawString("ON", 175, 155);
            g2.setColor(Color.WHITE);
            g2.drawString("Engine is running!", 220, 155);
            
            // Draw exhaust smoke
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillOval(100, 180, 30, 30);
            g2.fillOval(60, 190, 40, 40);
        } else {
            g2.setColor(Color.DARK_GRAY);
            g2.fillOval(170, 130, 40, 40);
            g2.setColor(Color.WHITE);
            g2.drawString("OFF", 172, 155);
            g2.drawString("Engine stopped.", 220, 155);
        }
    }
}
