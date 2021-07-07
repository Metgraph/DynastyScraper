
#DinastyScraper

Scraper e visualizzatore grafico della gerarchia familiare delle varie dinastie romane.

#Requisti ambiente

Java versione 1.8
Chrome
Driver chrome compatibili con la propria versione di chrome
avere il jar selenium-server-standalone versione 3 (il programma è stato testato con la versione 3.141.59)
Connessione a internet.

#Avvio

Esecuzione del file Main.java attraverso IDE.

#Installazione

entrare nella cartella src
eseguire il comando javac -d ../bin -classpath "path/to/selenium-server-standalone.jar" dataManagement/Storage.java inputInterface/inputGUI.java main/Main.java outpugui/EmperorGUI.java webscraper/Member.java webscraper/DynastiesScraper.java webscraper/Dynasty.java webscraper/Member.java webscraper/WebScraper.java
spostarsi nella cartella bin dove sono presenti i file class generati
eseguire il comando jar -cvfm DynastyScraper.jar ../src/META-INF/MANIFEST.MF dataManagement/*.class inputInterface/*.class main/*.class outpugui/*.class webscraper/*.class
eseguire il comando java -cp DynastyScraper.jar;path/to/selenium-server-standalone.jar main.Main 

#Bug

Se chiusa la finestra di selezione delle dinastie manualmente(usando il pulsante di chiusura delle finestre dell' OS) il browser rimarrà aperto,
tale avvenimento non è considerabile come bug, in quanto la visualizzazione del browser è solo a scopo dimostrativo del funzionamento del processo \
di scraping.

#Copyright

Copyright (c) 2021 Silvio Gori,Matteo Bernardo,Edoardo Lunati, Alessandro Lommi

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

