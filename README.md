# Software-engineering-project
Documento dei requisiti per la Wilderness weather station:

**Raccolta e comprensione dei requisiti con l’utente:**

**Stackholders coinvolti:**

**Customer**: L’organizzazione 3BMeteo in collaborazione con l’aeronautica militare.

**Supplier**: L’azienda software OnePiece.

**User**: Staff tecnico del reparto di meteorologia in ambienti selvaggi dell’organizzazione 3BMeteo e utenti esterno che vogliono monitorare la stazione meteo in collaborazione con l’azienda 3BMeteo.

1) Dominio di applicazione:

Il software verrà usato per il monitoraggio meteorologico nelle aree selvagge del Paese, come Alpi, Appennini, entroterra della Sardegna e zone marittime come Trieste, Sicilia e Puglia. I dati acquisiti ci daranno una visione dei cambiamenti climatici globali, inoltre aiuteranno a capire imminenti disastri meteorologici così da evitare voli con condizioni climatiche avverse. Quindi verranno posizionate delle stazioni meteorologiche nei punti di maggiore rilevanza climatica nelle aree non urbanizzate nelle quali sarà implementato un sistema che fornirà un servizio di raccolta e processamento dati in quasi totale autonomia e poi invierà i dati raccolti alla stazione centrale.

1) Caratteristiche utente: Il software per la stazione meteo è rivolto ad un utente con una buona conoscenza del dominio applicativo ma senza particolari conoscenze informatiche.

1) Funzionalità da fornire:

- Raccolta dati: temperatura, pressione atmosferica, irraggiamento solare, precipitazioni, velocità del vento e direzione del vento.
- Inviare i dati raccolti alla stazione centrale.
- Deve inviare i dati periodicamente.
- I dati inviati devono essere solo quelli necessari essendo che la stazione meteorologica si trova in ambienti estremi e quindi la connessione può essere disturbata o interrotta.
- La stazione deve autoalimentarsi e quindi deve saper gestire la fonte di energia in caso di situazioni pericolose.
- La stazione può essere aggiornata o controllata da remoto.
- Esegue un back up dei dati.

1) Requisiti non funzionali:

- Comprare licenza per le stazioni meteorologiche.
- Se la strumentazione principale è danneggiata, deve essere facilmente rimpiazzabile.
- Permessi per posizionare le stazioni meteorologiche.
- Posizionare le stazioni nei luoghi migliori per la raccolta dati ma non troppo esposti a rischi. 
- I sensori delle stazioni meteorologiche devono essere molto resistenti essendo che potrebbero essere esposti a forti intemperie.
- L’azienda ha sempre interagito con Java come linguaggio di programmazione per i suoi software quindi si trova più a suo agio con questo tipo di linguaggio.

1) Vincoli:
- Utilizzo UML come Design language.
- Programming language: Java.
- Build environment: Gradle

1) Story:

L’azienda 3BMeteo, in collaborazione con l’aeronautica militare, ha deciso di monitorare il clima nelle zone più selvagge del territorio Italiano, come gli Appennini, le Alpi o l’entroterra della Sardegna. A tal proposito è stata progettata una stazione meteorologica con lo scopo di raccogliere informazioni sul clima nella zona circostante.

La stazione meteo ha i seguenti sensori: termometro per la temperatura, barometro per la pressione, igrometro per l’umidità atmosferica, anemometro per la velocità del vento, banderuola per la direzione del vento, pluviometro per il calcolo delle precipitazioni, e un solarimetro per il calcolo dell’irradiamento solare.  

La stazione meteo invia i dati raccolti alla stazione centrale, ma prima, i dati devono essere rielaborati perché un continuo invio di materiale andrebbe a consumare troppa batteria e l’invio di pacchetti troppo pesanti non è possibile perché il link al sistema centrale è un link a bassa banda, inoltre la stazione meteo si autoalimenta attraverso pannelli solari o turbine a vento.

Il luogo in cui la stazione può essere collocata è un posto ad alto rischio di eventi atmosferici ad alta intensità quindi la stazione deve cercare di preservarsi il più possibile, per esempio in caso di forte vento i pannelli solari si devono chiudere perché potrebbero essere danneggiati.

A causa dell’ambiente e delle condizioni meteorologiche il sistema deve avere un metodo per monitorare il corretto funzionamento della stazione, per esempio se un sensore non funziona perché è stato danneggiato la stazione centrale deve poter eseguire un test da remoto per verificare quale sensore non funziona. 

Come buona prassi per quanto riguarda la gestione dei dati, il sistema deve fare il back up dei dati quando necessario.

Per finire, la stazione meteo può essere aggiornata e controllata da remoto in caso di necessità.

Grazie ai dati raccolti l’aeronautica militare può avere una maggiore consapevolezza degli agenti atmosferici in atto così da coordinare meglio i voli.

1) ` `Scenario sulla raccolta e invio dei dati:
- Assunzione iniziale: La stazione meteo ha sette sensori e ad ogni ora il sistema legge il valore dei sensori.
- Flusso Nominale: Ogni 60 minuti vado a leggere il valore dei sensori, quindi creo un oggetto con: temperatura, pressione, umidità, velocità del vento, direzione del vento, precipitazioni e solarimetro. Salvo i dati nel mio database locale e spedisco i dati.
- Cosa potrebbe andare storto: Se un sensore non funziona nell’array viene inserito un valore di default e questo sarà un indicatore di un problema nella stazione. Se invece l’intera stazione è stata compromessa, cioè è incapace di inviare qualsiasi segnale, bisogna mandare una squadra di tecnici addetta alle riparazioni. Poi potrei avere problemi nell’inviare i dati a causa di qualche interferenza, in quel caso non succede nulla, perché prima di inviarli ho fatto il back up.
- Altre attività: Da remoto lancio il comando per iniziare la raccolta o la cessazione dei dati. Il valore di default deve essere valore coerente perché potrebbero esserci ambiguità con i dati reali. Prima di inviare i dati appena raccolti devo salvarli sul back up giornaliero.
- Conclusione: Alla fine di questo scenario la stazione deve aver inviato il pacchetto di dati alla stazione centrale, il back up viene aggiornato con i nuovi dati e il timer di un’ora viene settato.
  1) Scenario aggiornamento stazione meteo:
- Assunzione iniziale: Potrebbe succedere che il sistema centrale abbia bisogno di esigenze diverse e quindi necessita un aggiornamento.
- Flusso Nominale: Quando ho bisogno di aggiornare una stazione meteo devo accedere alla sua pagina principale e da lì inviare il segnale riconfigurazione. A questo punto la stazione esegue delle operazioni preliminare per fare in modo che l’aggiornamento non interferisca con l’attività di sistema, poi scarica l’upgrade, lo esegue. 
- ` `Cosa potrebbe andare storto: Durante il download o l’esecuzione della riconfigurazione potrebbero esserci dei bug o dei malfunzionamenti. In caso questi siano molto gravi da compromettere il sistema bisogna inviare una squadra di tecnici, altrimenti basta rieseguire la riconfigurazione. Essendo che un’uscita dei tecnici è dispendiosa, prima dell’aggiornamento bisogna interrompere tutti i processi che potrebbero causare problemi.
- Altre attività: Dopo l’aggiornamento non viene riprese l’avvio automatico dei dati ma l’operatore dovrà eseguire un test per verificare che tutto funzioni correttamente e poi, a seconda del report del test, può scegliere se riprendere la raccolta dati.
- Conclusione: Al termine dell’attività la riconfigurazione ha avuto successo e la stazione ha ripreso a lavorare correttamente.
  1) Scenario Chiusura pannelli solari e gestione invio dei dati in caso di pericolo:
- Assunzione iniziale: La mia stazione sta captando dei segnali di allarme che sono: forte vento, grandi quantità di precipitazione oppure impatto pericolo dovuto da animali selvatici o grossa grandine.
- Flusso Nominale: Quando viene eseguita la lettura la stazione intercetta la velocità del vento, quindi chiude i pannelli solari e ferma la trasmissione dei dati. Ogni tot di minuti riesegue una lettura e controlla che la velocità del vento sia calta sotto i 100km/h. Quando esegue una lettura accettabile riprende l’invio dei dati e apre i pannelli solari.
- Cosa potrebbe andare storto: Può verificarsi un falso allarme ma questo non reca danni alla struttura. Se invece la perturbazione è troppo violenta e ho danni irreparabili devo far uscire una squadra ti tecnici. Come supporto, alla riaccensione della stazione meteo posso eseguire un test e vedere se ci sono sensori che non funzionano.
- Altre attività: La stazione resta inattiva fino a quando non riceve un segnale di “cessato pericolo”.
- Conclusione: L’attività risulta completata quando la stazione riprende il suo corretto funzionamento.
  1) Scenario di Testing della stazione meteo:
- Assunzione iniziale: Da remoto viene lanciato il comando di Testing.
- Flusso Nominale: La stazione meteo riceve l’istruzione di Testing e avvia un monitoraggio del sistema, controlla principalmente se i sensori funzionano e lo stato della batteria. Finito il test crea un report con lo stato attuale della macchina.
- Cosa potrebbe andare storto: Il test potrebbe rilevare un sensore guasto che in realtà non è rotto. A rinforzare ciò lo staff tecnico può rilanciare il testing della macchina.
- Altre attività: Quando eseguo il test viene bloccata la raccolta dati. 
- Conclusione: Alla fine dell’attività il test deve essere terminato e visualizzo il report sullo stato. Ora posso scegliere se riprendere la raccolta dati oppure valutare i danni riportarti nel report.
  1) Scenario back up dei dati:
- Assunzione iniziale: La stazione meteo ha appena finito di raccogliere i dati dai sensori ed è pronta a trasmetterli.
- Flusso Nominale: Ora che ho finito di raccogliere i dati li salvo nel mini-database interno alla stazione. Una volta che i dati sono salvati correttamente posso procedere con la trasmissione dei dati.
- Cosa potrebbe andare storto: Il database potrebbe essere pieno, in tal caso vado a sovrascrivere i dati più vecchi.
- Altre attività: Per accedere al back up in caso di necessità bisogna recuperare la memoria fisica dalla stazione meteo perché l’invio di una mole troppo elevata di dati è sconsigliato.
  1) Conclusione: Il back up è completo quando ho salvato correttamente i dati raccolti nel mini-database. 
  1) Scenario comando da remoto:
- Assunzione inziale: La macchina è connessa alla rete e necessito di eseguire un comando da remoto.
- Flusso Nominale: Per una qualsiasi ragione devo prendere il controllo dalla macchina da remoto, allora prima di tutto scelgo dalla mia interfaccia l’operazione che voglio eseguire. Una volta scelta l’operazione viene inviato un comando specifico alla stazione meteo che lo esegue.
- Cosa potrebbe andare storto: La stazione non potrebbe essere connessa per problemi all’antenna, allora deve essere inviata una squadra di tecnici a sistemare il guasto. 
- Altre attività: Non risultano altre attività.
- Conclusione: Lo scenario è concluso quando l’operazione inviata da remoto è stata eseguita correttamente.


**2) Specifica dei requisiti**

2.1) Lista dei requisiti interfaccia:

2.1.0) Interfaccia utente: Il sistema software deve essere dotato di un’interfaccia semplice, con poche e semplici istruzioni attivabili con dei pulsanti.

2.1.1) Interfaccia hardware: Il sistema software deve interfacciarsi con i sensori della stazione meteo.

2.1.2) Interfaccia software: Il sistema software non deve interfacciarsi con nessun sistema software.

2.2) Requisiti funzionali:

2.2.1) Raccolta dati: Il sistema deve inviare i dati raccolta dai sensori alla stazione centrale ogni 5 minuti. (I cambiamenti climatici possono essere improvvisi, allora è necessario inviare i dati frequentemente).

2.2.2) Gestione da remoto: La stazione meteo deve essere controllabile da remoto e deve essere possibile eseguire i seguenti comandi: Testing, aggiornamenti, riconfigurazione avvio della raccolta dati e stop della raccolta dati. (Pochi comandi ma essenziali, così da coprire le funzionalità principali della stazione meteo e da rendere l’interfaccia semplice e non troppo complessa).

2.2.3) Aggiornamento e riconfigurazione: La stazione meteo deve ricevere da remoto il comando di aggiornamento e riconfigurazione, poi interrompe la raccolta dei dati. Ora posso scegliere il file di riconfigurazione da caricare nella stazione meteo. Il file deve partire in automatico una volta scaricato nella stazione ed effettua l’aggiornamento/riconfigurazione. Prima di lanciare il comando di ripresa della raccolta dati va eseguito un test di controllo ma è opzionale. (Grazie a questo protocollo posso verificare se l’aggiornamento/riconfigurazione è andato a buon fine).

2.2.4) Back up dei dati: Prima di inviare i dati alla stazione centrale la stazione meteo esegue un back up dei dati e li salva nella memoria locale. (Con questo metodo abbiamo un certo livello di garanzia di non perdere i dati per via di interferenze del segnale).

2.2.5) Gestione dei dati di back up: I dati vengono memorizzati in locale e quando ho esaurito la memoria i nuovi dati vanno a sovrascrivere i più vecchi. (Questo sembra un buon metodo, ma se in fase di implementazione o durante il test da parte degli utenti risulta poco efficace può cambiare).

2.2.6) Recupero dati di back up: Se dei dati vengono persi o danneggiati nella stazione centrale possono essere recuperati direttamente dalla stazione meteo. In futuro però è previsto un aggiornamento per permettere alla stazione centrale di visualizzarli. Allora bisogna implementare un database che permetta le operazioni più elementari. (La stazione centrale è dotata di un back up proprio ma questo sistema mi garantisce di avere un ulteriore back up dei dati).

2.2.7) Gestione stazione meteo in caso di pericolo: In caso di forte vento, il sistema si dovrebbe proteggere arrestando l’invio di dati per preservare energia e ripiegare i pannelli solari. Se il segnale d’allarme persiste la stazione rimane sempre con i sensori spenti e con i pannelli solari ripiegati, se invece, il segnale d’allarme cessa, riprende la sua normale attività. (Il vento è la principale causa di pericolo secondo uno studio condotto dalla guardia forestale in collaborazione con 3BMeteo) 

**3) Note sul progetto:**

- Per eseguire gli acceptance test bisogna prima andare nella pagina WeatherStation e disattivare il refresh automatico altrimenti non funzionano. In cima alla pagina ho già messo le istruzioni da eseguire. L’applicazione deve partire da zero perché viene testato anche l’avvio della raccolta dati la prima volta.
- Essendo una webapp mi sono molto di più concentrato sugli acceptance test e ho commentato il codice in modo che faccia anche da documento di Quality assurance
- Non ho eseguito la copertura degli unit test perchè non ho eseguito il test del Webcontroller.

