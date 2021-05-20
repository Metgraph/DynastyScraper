package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.io.File;

public class EmperorGUI extends JFrame implements HyperlinkListener {
    private String name = "Gaio Giulio Cesare";
    private File biotxt = new File("bmoc/bio");
    private String bio = "<p> Cesare decise allora, nel <a href='/wiki/74_a.C.' title='74 a.C.'>74 a.C.</a>, di recarsi a <a href='/wiki/Rodi' title='Rodi'>Rodi</a>, vera e propria meta di pellegrinaggio per i giovani romani delle classi più alte, desiderosi di apprendere la cultura e la filosofia greca.<sup id='cite_ref-36' class='reference'><a href='#cite_note-36'>[34]</a></sup>\n" +
            "Durante il viaggio fu però rapito dai <a href='/wiki/Pirateria' title='Pirateria'>pirati</a>, che lo portarono sull'isola di <a href='/wiki/Farmacussa' class='mw-redirect' title='Farmacussa'>Farmacussa</a>, una delle <a href='/wiki/Sporadi_meridionali' class='mw-redirect' title='Sporadi meridionali'>Sporadi meridionali</a> a sud di <a href='/wiki/Mileto_(Asia_Minore)' title='Mileto (Asia Minore)'>Mileto</a>.<sup id='cite_ref-37' class='reference'><a href='#cite_note-37'>[35]</a></sup> Quando questi gli chiesero di pagare venti <a href='/wiki/Talento_(peso)' title='Talento (peso)'>talenti</a>, Cesare rispose che ne avrebbe consegnati cinquanta e mandò i suoi compagni a Mileto perché ottenessero la somma di denaro con cui pagare il riscatto, mentre lui sarebbe rimasto a Farmacussa con due schiavi e il medico personale.<sup id='cite_ref-38' class='reference'><a href='#cite_note-38'>[36]</a></sup> Durante la permanenza sull'isola, che si protrasse per trentotto giorni,<sup id='cite_ref-39' class='reference'><a href='#cite_note-39'>[37]</a></sup> Cesare compose numerose poesie e le sottopose poi al giudizio dei suoi carcerieri; più in generale, mantenne un comportamento piuttosto particolare con i pirati, trattandoli sempre come se fosse lui ad avere in mano le loro vite e promettendo più volte che una volta tornato libero li avrebbe fatti uccidere tutti.<sup id='cite_ref-40' class='reference'><a href='#cite_note-40'>[38]</a></sup> Quando i suoi compagni ritornarono, portando con sé il denaro che le città avevano offerto loro per pagare il riscatto,<sup id='cite_ref-41' class='reference'><a href='#cite_note-41'>[39]</a></sup> Cesare si rifugiò nella <a href='/wiki/Asia_(provincia_romana)' title='Asia (provincia romana)'>provincia d'Asia</a>, governata dal <a href='/wiki/Propretore' title='Propretore'>propretore</a> <a href='/w/index.php?title=Marco_Iunco&action=edit&redlink=1' class='new' title='Marco Iunco (la pagina non esiste)'>Marco Iunco</a>.<sup id='cite_ref-42' class='reference'><a href='#cite_note-42'>[40]</a></sup> Giunto a Mileto, Cesare armò delle navi e tornò in tutta fretta a Farmacussa, dove catturò senza difficoltà i pirati; poi si recò con i prigionieri al seguito in <a href='/wiki/Bitinia' title='Bitinia'>Bitinia</a>, dove Iunco stava sovrintendendo all'attuazione delle volontà espresse da Nicomede IV nel suo testamento. Qui chiese al propretore di provvedere alla punizione dei pirati, ma questi si rifiutò, tentando invece di impadronirsi del denaro sottratto ai pirati stessi,<sup id='cite_ref-43' class='reference'><a href='#cite_note-43'>[41]</a></sup> e decise poi di rivendere i prigionieri.<sup id='cite_ref-44' class='reference'><a href='#cite_note-44'>[42]</a></sup> Cesare allora, prima che Iunco potesse mettere in atto i suoi progetti, si rimise in mare lasciando la Bitinia e procedette egli stesso all'esecuzione dei prigionieri: li fece crocifiggere dopo averli strangolati, in modo da evitare loro una lunga e atroce agonia.<sup id='cite_ref-45' class='reference'><a href='#cite_note-45'>[43]</a></sup> In questo modo, secondo le fonti filocesariane, egli non fece altro che adempiere ciò che aveva promesso ai pirati durante la prigionia,<sup id='cite_ref-46' class='reference'><a href='#cite_note-46'>[44]</a></sup> e poté anzi restituire i soldi che i suoi compagni avevano dovuto richiedere per il riscatto.<sup id='cite_ref-47' class='reference'><a href='#cite_note-47'>[45]</a></sup>" +
            "</p>";
    private String url = "https://it.wikipedia.org/wiki/Gaio_Giulio_Cesare";
    private String pic = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg/165px-Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg";


    public EmperorGUI() {

        //la gestione di tale pagina è divisa in 2 jpanel
        JPanel treepanel = new JPanel();
        JPanel emperorPanel = new JPanel();
        emperorPanel.setLayout(new BoxLayout(emperorPanel, BoxLayout.Y_AXIS));
        treepanel.setPreferredSize(new Dimension(960, 720));
        emperorPanel.setPreferredSize(new Dimension(320, 720));

        //creo il frame che contyiene per 3/4 la GUI dell' albero e per 1/4 la pagina relativa all' imperatore
        //fondamentale caratteristica è l'interruzione del programma a chiusura della finestra
        JFrame emperorFrame = new JFrame("Dinasty");
        emperorFrame.setLocationRelativeTo(null);
        emperorFrame.setSize(1280, 720);
        emperorFrame.setMinimumSize(new Dimension(1280, 720));
        emperorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emperorFrame.setLayout(new BorderLayout());


        //diuchiaro eventuali bottoni
        JButton button = new JButton("switch");


        treepanel.add(new JLabel());
        button.setLocation(400, 200);
        button.setAlignmentX(0);
        treepanel.add(button);

        //componenti della scheda imperatore
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
        JLabel emperorPic = new JLabel(new ImageIcon(image));
        emperorPic.setAlignmentX(CENTER_ALIGNMENT);

        JLabel emperorName = new JLabel(name);
        emperorName.setFont(new Font("Arial", Font.BOLD, 24));
        emperorName.setAlignmentX(CENTER_ALIGNMENT);

        JEditorPane emperorBio = new JEditorPane();
        JScrollPane emperorScrollbar = new JScrollPane(emperorBio, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        emperorScrollbar.setMaximumSize(new Dimension(310,emperorPanel.getHeight()%2));
        emperorScrollbar.setBorder(new EmptyBorder(20,20,20, 5));
        emperorBio.setContentType("text/html");
        emperorBio.setText("<html>" + bio + "</html>");
        emperorBio.setAlignmentX(CENTER_ALIGNMENT);
        emperorBio.setEditable(false);
        emperorBio.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent event) {
                if(HyperlinkEvent.EventType.ACTIVATED.equals(event.getEventType())){
                    System.out.println();
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





        //pannello dell' imperatore
        emperorPanel.add(emperorName);
        emperorPanel.add(emperorPic);
        emperorPanel.add(emperorScrollbar);
        emperorPanel.add(Box.createVerticalGlue());
        emperorPanel.setBackground(Color.WHITE);
        emperorPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.LIGHT_GRAY));


        //aggiungo i panel al frame e lo rendo visibile
        emperorFrame.add(emperorPanel, BorderLayout.WEST);
        emperorFrame.add(treepanel, BorderLayout.CENTER);


        emperorFrame.setVisible(true);

    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {

    }
}

