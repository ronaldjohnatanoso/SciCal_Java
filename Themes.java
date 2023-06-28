
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Themes extends JPanel implements ActionListener {
     Color Ocean =new Color(0,255,230);
     Color Sunset = new Color(255,87,34);
     Color Peach = new Color(255,204,153);

     Color THEME = Ocean; //use by the project , default
     Color BUTTON_THEME = Peach; //use by project, default color for buttons
    
    static ArrayList<Component> nonButtonComps = new ArrayList<Component>(); //list of non_button components to change with theme later
    static ArrayList<Component> buttonComps = new ArrayList<Component>();//list of button components to change with theme later

    JButton goToMain = new JButton("Go Back To Main Page");
    Main mainFrame;
    OptionPage optionPage;

    JButton bg_ocean = new JButton("Bg: Ocean");
    JButton bg_sunset = new JButton("Bg: Sunset");
    JButton bg_peach = new JButton("Bg: Peach");
    JButton button_ocean = new JButton("Button: Ocean");
    JButton button_sunset = new JButton("Button: Sunset");
    JButton button_peach = new JButton("Button: Peach");

    JPanel colorsLedger = new JPanel();
    JLabel pageTitle = new JLabel("Themes");

    public Themes(Main mainFrame, OptionPage optPage){ //receive main and option page
        this.mainFrame = mainFrame; //reference to our main page
        this.optionPage = optPage;
        setLayout(new BorderLayout());
        setSize(Main.BUTTONS_FRAME_WIDTH, Main.BUTTONS_FRAME_HEIGHT);
        Themes.nonButtonChangableComp(this); //add to list of components for theme change later
        Themes.buttonChangableComp(goToMain); //add to list of components for theme change later

        // go back button listener
        goToMain.addActionListener(this);
        bg_ocean.addActionListener(this);
        bg_sunset.addActionListener(this);
        bg_peach.addActionListener(this);
        button_ocean.addActionListener(this);
        button_sunset.addActionListener(this);
        button_peach.addActionListener(this);

        Themes.buttonChangableComp(bg_ocean); //add to list of components for theme change later
        Themes.buttonChangableComp(bg_sunset); //add to list of components for theme change later
        Themes.buttonChangableComp(bg_peach); //add to list of components for theme change later
        Themes.buttonChangableComp(button_ocean); //add to list of components for theme change later
        Themes.buttonChangableComp(button_sunset); //add to list of components for theme change later
        Themes.buttonChangableComp(button_peach); //add to list of components for theme change later


        //color list
        colorsLedger.setLayout(new FlowLayout());
        colorsLedger.setSize(500, 500);
        pageTitle.setHorizontalAlignment(SwingConstants.CENTER);
        Themes.nonButtonChangableComp(colorsLedger); //add to list of components for theme change later

        //add colors to list panel
        colorsLedger.add(bg_ocean);
        colorsLedger.add(bg_sunset);
        colorsLedger.add(bg_peach);
        colorsLedger.add(button_ocean);
        colorsLedger.add(button_sunset);
        colorsLedger.add(button_peach);
        add(pageTitle,BorderLayout.NORTH);
        add(goToMain, BorderLayout.SOUTH);
        add(colorsLedger,BorderLayout.CENTER);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goToMain) {
            mainFrame.pageCL.show(mainFrame.getContentPane(), "1"); //show back to main page
        }
        if(e.getSource()==bg_ocean){ //theme buttons
            THEME = Ocean;
        }
        if(e.getSource()==bg_sunset){
            THEME = Sunset;
        }
        if(e.getSource()==bg_peach){
            THEME = Peach;
        }
        if(e.getSource()==button_ocean){
            BUTTON_THEME = Ocean;
        }
        if(e.getSource()==button_sunset){
            BUTTON_THEME = Sunset;
        }
        if(e.getSource()==button_peach){
            BUTTON_THEME = Peach;
        }
        themeBackground(nonButtonComps); //update the background theme
        themeBackground(buttonComps, BUTTON_THEME); //update the button theme
    }

    public void themeBackground(ArrayList<Component> comps){ //super class to receive any jbutton , jlabel etc
        //loop every component and apply theme
        for(Component comp: comps){
            comp.setBackground(THEME);
        }
    }

    //overloaded method to change buttons only
    public void themeBackground(ArrayList<Component> comps, Color buttonColor){
        //loop only for buttons
        for(Component comp: comps){
            comp.setBackground(BUTTON_THEME);
        }
    }

    public static void nonButtonChangableComp(Component cmp ){ //collects all non_button
        nonButtonComps.add(cmp);
    }

    public static void buttonChangableComp(Component cmp){ //collects all buttons
        buttonComps.add(cmp);
    }


}
