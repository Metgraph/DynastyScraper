package outpugui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
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
    private String dinasty = "Giulio Claudia";
    private ArrayList<Member> members;
    private boolean finished = true;

    public EmperorGUI(ArrayList<Member> members, String dinasty) {
        this.dinasty = dinasty;
        this.members = members;
        //la gestione di tale pagina è divisa in 2 jpanel
        JFrame emperorFrame = new JFrame("Dinasty");
        JPanel treePanel = new JPanel();
        JPanel emperorPanel = new JPanel();
        JPanel buttons = new JPanel();
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("visualizza imperatori");
        JButton home = new JButton("home");
        //creo il menù che conterrà gli imperatori clickabili nella menubar
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        //creo la menubar che contiene il pulsante home e gli imperatori e la definisco
        menuBar.add(Box.createHorizontalGlue());
        menuBar.setPreferredSize(new Dimension(0,20));
        //setto la base del panel dell'albero
        treePanel.setLayout(new BorderLayout());
        treePanel.setPreferredSize(new Dimension(960, 720));
        //setto la base del pannello dei singoli imperatori
        emperorPanel.setLayout(new BoxLayout(emperorPanel, BoxLayout.Y_AXIS));
        emperorPanel.setPreferredSize(new Dimension(320, 720));
        //mouse event per il tato home
        home.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                finished = false;
                isFinished();
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


        //creo il frame che contyiene per 3/4 la GUI dell' albero e per 1/4 la pagina relativa all' imperatore
        //fondamentale caratteristica è l'interruzione del programma a chiusura della finestra
        emperorFrame.setLocationRelativeTo(null);
        emperorFrame.setSize(1280, 720);
        emperorFrame.setMinimumSize(new Dimension(1280, 720));
        emperorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emperorFrame.setLayout(new BorderLayout());



        //componenti albero
        JScrollPane treeScrollbar = new JScrollPane(treePanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        treePanel.setBackground(Color.WHITE);



        JTree generation = createTree(emperorFrame,emperorPanel,treePanel,buttons);
        generation.setAlignmentX(CENTER_ALIGNMENT);
        generation.setAlignmentY(CENTER_ALIGNMENT);
        generation.setRootVisible(true);
        expandAllNodes(generation,0, generation.getRowCount());
        generation.setCellRenderer(new ButtonRenderer());
        treePanel.add(generation);


        Dimension treeSize = treePanel.getPreferredSize();
        System.out.println(Math.abs(treeSize.width/2));


        //aggiungo i panel al frame e lo rendo visibile
        menu.add(buttons);
        menuBar.add(home);
        menuBar.add(menu);
        emperorFrame.add(menuBar, BorderLayout.NORTH);
        emperorFrame.add(treeScrollbar, BorderLayout.CENTER);


        emperorFrame.setVisible(true);

    }

    public boolean isFinished(){ return finished;}

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    private NodeExtender createNode(Member human, JFrame emperorFrame, JPanel emperorPanel,JPanel treepanel,JPanel buttons){
        if(human.isEmperor()){
            JButton emperorButton = new JButton(human.getName());
            emperorButton.setPreferredSize(new Dimension(200,30));
            emperorButton.setMaximumSize(new Dimension(200,30));
            emperorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    emperorPanel.removeAll();
                    emperorBuilder(human.getName(),human.getUrl(), human.getBiography(),human.getImageURL(),emperorFrame,emperorPanel);
                    emperorPanel.revalidate();
                    emperorFrame.add(emperorPanel, BorderLayout.WEST);
                    emperorFrame.revalidate();
                    emperorFrame.repaint();
                }
            });
            buttons.add(emperorButton, BorderLayout.LINE_END);
            if(human.getIssue()==null){
                return new NodeExtender(human);
            }
            NodeExtender me = new NodeExtender(human);
            for(Member son: human.getIssue()){
                me.add(createNode(son,emperorFrame, emperorPanel,treepanel, buttons));
            }

            return me;
        }
        if(human.getIssue()==null){
            return new NodeExtender(human);
        }
        NodeExtender me = new NodeExtender(human);
        for(Member son: human.getIssue()){
            me.add(createNode(son,emperorFrame, emperorPanel,treepanel, buttons));
        }

        return me;

    }
    private JTree createTree(JFrame emperorFrame, JPanel emperorPanel, JPanel treepanel, JPanel buttons){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(dinasty);
        for(Member emperor: members){
            root.add(createNode(emperor,emperorFrame, emperorPanel,treepanel,buttons));
        }

        DefaultTreeModel model = new DefaultTreeModel(root);
        return new JTree(model);
    }

    //crea il pannello imperatore, usando un metodo, rendendo il tutto dinamico
    private JPanel emperorBuilder(String name, String url, String bio, String pic,JFrame emperorFrame, JPanel emperorPanel ){
        //componenti della scheda imperatore

        //immagine dell'imperatore presa tramite url
        BufferedImage image = null;
        try {
            URL picurl = new URL(pic);
            image = ImageIO.read(picurl);
        } catch (MalformedURLException ex) {
            System.out.println("malformed url");
        } catch (IOException iox) {
            System.out.println("File not found");
        }
        float widthchecker = image.getWidth();
        System.out.println(widthchecker);
        ImageIcon picture = new ImageIcon(image);
        JLabel emperorPic = new JLabel(picture);
        emperorPic.setAlignmentX(CENTER_ALIGNMENT);

        //nome dell'ímperatore passato dalla variabile
        JTextPane emperorName = new JTextPane();
        emperorName.setText(name);
        emperorName.setMaximumSize(new Dimension(320,480));
        emperorName.setFont(new Font("Hoefler Text", Font.BOLD, 20));
        emperorName.setAlignmentX(CENTER_ALIGNMENT);
        emperorName.setBorder(new EmptyBorder(20, 20, 20, 20));
        //rendo la lable clickabile così da far aprire la pagina di wikipedia

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

        //pannello della biografia
        JEditorPane emperorBio = new JEditorPane();
        //il pannello legge codice html, ma i doppi apici vanno sostituiti da singoli
        emperorBio.setContentType("text/html");
        emperorBio.setText("<html>" + bio + "</html>");
        emperorBio.setAlignmentX(CENTER_ALIGNMENT);
        emperorBio.setEditable(false);
        //lettore di hyperlink e href che apre il default browser e ti apre la pagina cliccata
        emperorBio.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if (HyperlinkEvent.EventType.ACTIVATED.equals(event.getEventType())) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        URL root = new URL("https://it.wikipedia.org" + event.getDescription());
                        System.out.println(root);
                        desktop.getDesktop().browse(root.toURI());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (URISyntaxException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
                    }
                }
            }
        });
        //contentitore scrollbar per la gestione di testi lunghi
        JScrollPane emperorScrollbar = new JScrollPane(emperorBio, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        emperorScrollbar.setBorder(new MatteBorder(20,20,20,0, Color.WHITE));

        //pannello dell' imperatore
        emperorPanel.add(emperorName);
        emperorPanel.add(emperorPic);
        emperorPanel.add(emperorScrollbar);
        emperorPanel.setBackground(Color.WHITE);
        emperorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(0, 99, 181)));

        return emperorPanel;

    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {

    }
}
class ButtonRenderer extends JFrame implements TreeCellRenderer{
    private Member member;
    //override dell'albero per differenziazione imperatori
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
            if (human.isEmperor()){
                JLabel emperor = new JLabel(human.getName());
                emperor.setForeground(Color.RED);
                return emperor;
            } else {
                return new JLabel(human.getName());

            }
        }
        return new JTextArea(value.toString());

    }
}
//classe per implementare l'albero sottoclasse di deafaultmutabletreenode e modificare in override il renderer
class NodeExtender extends DefaultMutableTreeNode{
    private Member member;
    public NodeExtender(Member value){
        super(value);
        member = value;


    }

    public Member getMember() {
        return member;
    }

}