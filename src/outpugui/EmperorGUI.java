package outpugui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import java.util.ArrayList;

import webscraper.Member;

public class EmperorGUI extends JFrame implements HyperlinkListener {
    private ArrayList<Member> members;
    private boolean finished = true;
    private String dinasty;

    //Main constructor method

    public EmperorGUI(ArrayList<Member> members, String dinasty) {

        this.dinasty = dinasty;
        this.members = members;

        //Defining and initializing components

        JFrame emperorFrame = new JFrame("Dinasty");
        JPanel treePanel = new JPanel();
        JPanel emperorPanel = new JPanel();
        JPanel buttons = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("visualizza imperatori");
        JButton home = new JButton("home");

        //Adding action to the home button

        home.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {

                finished = false;
                isFinished();

                //Method for closing the frame

                emperorFrame.dispose();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        //Setting layout of emperor list for the menubar in a new panel

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        //Creating the menu bar and making it shift to the right

        menuBar.add(Box.createHorizontalGlue());
        menuBar.setPreferredSize(new Dimension(0,20));

        //Defining settings of the panel containing the tree representation

        treePanel.setLayout(new BorderLayout());
        treePanel.setPreferredSize(new Dimension(960, 720));
        treePanel.setBackground(Color.WHITE);

        //Defining tree parameters

        JTree generation = createTree(emperorFrame,emperorPanel,treePanel,buttons);
        generation.setRootVisible(true);
        expandAllNodes(generation,0, generation.getRowCount());
        generation.setCellRenderer(new ButtonRenderer());
        treePanel.add(generation, BorderLayout.CENTER);

        //Defining the scrollbar of the tree panel

        JScrollPane treeScrollbar = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        //Defining parameters of the panel containing the emperor stats

        emperorPanel.setLayout(new BoxLayout(emperorPanel, BoxLayout.Y_AXIS));
        emperorPanel.setPreferredSize(new Dimension(320, 720));

        //Defining main frame parameters

        emperorFrame.setMinimumSize(new Dimension(720, 720));
        emperorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emperorFrame.setLayout(new BorderLayout());

        //Add methods for building the frame

        menu.add(buttons);
        menuBar.add(home);
        menuBar.add(menu);
        emperorFrame.add(menuBar, BorderLayout.NORTH);
        emperorFrame.add(treeScrollbar);

        //Setting the frame visible and center the allignement

        emperorFrame.setVisible(true);
        emperorFrame.setLocationRelativeTo(null);

    }

    //Method helping return true if home is clicked

    public boolean isFinished(){ return finished;}

    //Method that expands the tree in the panel

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){

        //this function is based on the expandrow method it needs to be called for every child of the tree, so is made recursive for ease of use

        for(int i=startingIndex;i<rowCount;++i){

            tree.expandRow(i);
        }

        //Condition for not trying expanding the leaves

        if(tree.getRowCount()!=rowCount){

            expandAllNodes(tree, rowCount, tree.getRowCount());

        }
    }

    /**Method used for creating the tree nodes, it returns the a node type, creates the buttons for the menu and differentiates the emperors from the other members of the family
     *
     * @param human
     * @param emperorFrame
     * @param emperorPanel
     * @param treepanel
     * @param buttons
     * @return NodeExtender
     */

    private NodeExtender createNode(Member human, JFrame emperorFrame, JPanel emperorPanel,JPanel treepanel,JPanel buttons){

        if(human.isEmperor()){

            //Creating the button of a single emperor, is applied only for emperors

            JButton emperorButton = new JButton(human.getName());
            emperorButton.setPreferredSize(new Dimension(200,30));
            emperorButton.setMaximumSize(new Dimension(200,30));
            emperorButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    //the button refreshes the panel based on the selected emperor

                    emperorPanel.removeAll();
                    emperorBuilder(human.getName(),human.getUrl(), human.getBiography(), human.getImageURL(),emperorFrame,emperorPanel);
                    emperorPanel.revalidate();
                    emperorFrame.add(emperorPanel, BorderLayout.WEST);
                    emperorFrame.revalidate();
                    emperorFrame.repaint();

                }

            });

            //Adding the buttons to the menu panel

            buttons.add(emperorButton, BorderLayout.LINE_END);


            //Checks the family list of the emperor being analyzed if is null only returns himself as a leaf

            if(human.getIssue()==null){

                return new NodeExtender(human);

            }
            NodeExtender me = new NodeExtender(human);

            /*If the condition is not respected, so we have more information of the family members of this emperor,
             * the Method calls himself recursively at the end will return the nodes relative to him */

            for(Member son: human.getIssue()){

                me.add(createNode(son,emperorFrame, emperorPanel,treepanel, buttons));

            }

            return me;
        }

        /* Works like the the emperor part of node creator, but works with the other members of the family
         * and returns the node at the end */

        if(human.getIssue()==null){

            return new NodeExtender(human);

        }
        NodeExtender me = new NodeExtender(human);

        for(Member son: human.getIssue()){

            me.add(createNode(son,emperorFrame, emperorPanel,treepanel, buttons));

        }

        return me;

    }

    /**Method for creating the tree
     *
     * @param emperorFrame
     * @param emperorPanel
     * @param treepanel
     * @param buttons
     * @return JTree
     */

    private JTree createTree(JFrame emperorFrame, JPanel emperorPanel, JPanel treepanel, JPanel buttons){

        //Creates a root for the tree

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(dinasty);

        try {
            for (Member emperor : members) {

                //Adding nodes to the root created by

                root.add(createNode(emperor, emperorFrame, emperorPanel, treepanel, buttons));
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        //Instancing the tree and returning it

        DefaultTreeModel model = new DefaultTreeModel(root);
        return new JTree(model);
    }

    /**Method for creating the panel with the emperor stats
     *
     * @param name
     * @param url
     * @param bio
     * @param pic
     * @param emperorFrame
     * @param emperorPanel
     * @return JPanel
     */

    private JPanel emperorBuilder(String name, String url, String bio, String pic,JFrame emperorFrame, JPanel emperorPanel ){

        //Creating the image part using a jlabel

        ImageIcon picture = new ImageIcon();
        JLabel emperorPic;
        BufferedImage image = null;

        //Image exceptions
        try {

            URL picurl = new URL(pic);
            image = ImageIO.read(picurl);
            picture = new ImageIcon(image);

        } catch (MalformedURLException ex) {
            picture = new ImageIcon();
            ex.printStackTrace();

        } catch (IOException iox) {
            picture = new ImageIcon();
            iox.printStackTrace();
        } catch (NullPointerException e){
            picture = new ImageIcon();
            e.printStackTrace();
        } catch (IllegalArgumentException Ile){
            picture = new ImageIcon();
            Ile.printStackTrace();
        }


        emperorPic = new JLabel(picture);
        emperorPic.setAlignmentX(CENTER_ALIGNMENT);

        //creating the textpane contaning the emperor name

        JTextPane emperorName = new JTextPane();
        emperorName.setText(name);
        emperorName.setMaximumSize(new Dimension(320,480));
        emperorName.setFont(new Font("Hoefler Text", Font.BOLD, 20));
        emperorName.setAlignmentX(CENTER_ALIGNMENT);
        emperorName.setBorder(new EmptyBorder(20, 20, 20, 20));
        emperorName.setEditable(false);

        //Making it clickable by using mouselisteners

        emperorName.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(emperorName.getText() != ""){
                    try {URL urlFixed = new URL(url);
                        Desktop.getDesktop().browse(urlFixed.toURI());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (URISyntaxException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(emperorName.getText() != ""){
                    emperorName.setForeground(Color.BLUE);
                    emperorName.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(emperorName.getText() != "") {
                    emperorName.setForeground(Color.BLACK);
                    emperorName.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

            }
        });

        //Creating the bio panel

        JEditorPane emperorBio = new JEditorPane();
        emperorBio.setContentType("text/html");
        emperorBio.setText("<html>" + bio + "</html>");
        emperorBio.setAlignmentX(CENTER_ALIGNMENT);
        emperorBio.setEditable(false);
        emperorBio.setCaretPosition(0);

        //Hyperlinks reader that also adds the wikipedia root domain to the url making it clickable

        emperorBio.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(event.getEventType())) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        URL root = new URL("https://it.wikipedia.org" + event.getDescription());
                        desktop.getDesktop().browse(root.toURI());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (URISyntaxException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
                    }
                }
            }
        });

        //Scrollpane for managing long text

        JScrollPane emperorScrollbar = new JScrollPane(emperorBio, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        emperorScrollbar.setBorder(new MatteBorder(20,20,20,0, Color.WHITE));

        //Add methods for the panel, settings of layout and background color and return

        emperorPanel.add(emperorName);
        emperorPanel.add(emperorPic);
        emperorPanel.add(emperorScrollbar);
        emperorPanel.setBackground(Color.WHITE);
        emperorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0, 99, 181)));
        return emperorPanel;

    }

    //Hyperlink override, but modifications are not needed

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {

    }
}

//Class for overriding the tree cel renderer and making it customizable

class ButtonRenderer extends JFrame implements TreeCellRenderer{

    private Member member;

    //Override of the renderer method, makes the name red if is an emperor, checks if is adopted and adds their relative wifes

    @Override
    public Component getTreeCellRendererComponent(JTree    tree,
                                                  Object   value,
                                                  boolean  selected,
                                                  boolean  expanded,
                                                  boolean  leaf,
                                                  int      row,
                                                  boolean  hasFocus) {
        NodeExtender equal= new NodeExtender(member);
        if(value.getClass().equals(equal.getClass())){
            NodeExtender node = (NodeExtender) value;
            Member human = node.getMember();

            String spouses = "";
            if(human.getSpouses() != null && human.getSpouses().size()>0){
                spouses = " mogli (";
                for(int i = 0; i < human.getSpouses().size(); i++){

                    spouses+=human.getSpouses().get(i).getName();
                    if(i < human.getSpouses().size()-1) spouses+=", ";
                }
                spouses+= ")";
            }

            if (human.isEmperor()){

                if (human.isAdopted()){
                    JLabel emperor = new JLabel(human.getName() + " (adottato)" + spouses);
                    emperor.setForeground(Color.RED);
                    return emperor;
                }

                JLabel emperor = new JLabel(human.getName() + spouses);
                emperor.setForeground(Color.RED);
                return emperor;

            } else {

                if (human.isAdopted()){
                    return new JLabel(human.getName() + " (adottato/a)" + spouses);
                }
                return new JLabel(human.getName() + spouses);


            }
        }
        return new JTextArea(value.toString());

    }
}

//Class needed for cruising the tree and properly read it defining the node extender object and modifying it so is usable with the member type

class NodeExtender extends DefaultMutableTreeNode{

    private Member member;

    public NodeExtender(Member value){

        super(value);
        member = value;

    }

    //Get method for returning member type

    public Member getMember() {

        return member;

    }

}