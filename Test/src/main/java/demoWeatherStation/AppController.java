package demoWeatherStation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.lang.*;

@Controller
public class AppController {

    private static String UPLOADED_FOLDER = "src/main/resources/templates";    // salva il file caricato nel folder
    private  ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private int c=0; //controllore per impedire l'avvio di più thread
    private int R=0;

    @Autowired // leggi e scrivi dal db, unisco il controller e il model
    private Data_SensorRepository repository;

    @RequestMapping("/")
    public String index(){
        return "WeatherStation";
    }
    @RequestMapping("/WeatherStation")
    public String index(Model model)
    {

        // interrogo il database
        LinkedList<Data_Sensors> data = new LinkedList<>();
        for (Data_Sensors p: repository.findAll()){
            data.add(p); // aggiungo il dato trovato nel database alla lista
        }

        if(data.isEmpty()){
            return "WeatherStation";
        }else {
            model.addAttribute("Data_Sensor", data.getLast());// metto data nel model e la rendo disponibile al templeate list
        }
        return "WeatherStation";
    }


    @RequestMapping("/avvioLettura") // catturo azione dal template
    public String avvioLettura (
            @RequestParam(name="startData", required=true) boolean startData //prende il input il valore del bottone
    ){
        if(c==0) {
            System.out.println("Sto leggendo i dati");
            R=0;
            c=c+1;
            if (startData) {
                scheduler = Executors.newSingleThreadScheduledExecutor();// avvio il tread
                //ora creo una task periodica che legge i dati ogni 5 minuti
                Runnable startCollection = new Runnable() {
                    public void run() {

                        Data_Sensors lettura = new Data_Sensors();
                        lettura.readData(lettura);
                        repository.save(lettura);// salvo i dati di back up
                        if(lettura.getWind_Speed() > 100){

                            scheduler.shutdown(); //stoppo la lettura automatica dei dati perchè c'è pericolo vento
                            c=0; // setto a zero il controller così posso riavviare la lettura

                            lettura.Close_solar_panels();// chiudo i pannelli solari

                            System.out.println("test vento veloce "+ lettura.getWind_Speed());
                            System.out.println("pannelli solari "+ lettura.getSolar_panels());

                            //Ogni 5 secondi controllo se il vento è ancora superiore ai 100 km/h , quando una nuova lettura mi soddisfa la condizione esco dal while e riprendo la lettura dei dati in automatico
                            while(lettura.getWind_Speed() > 100) {
                                try {
                                    // thread to sleep for 10000 milliseconds
                                    Thread.sleep(5000);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                                // esegue una nuova lettura per verificare che la velocità del vento sia diminuita
                                lettura.readData(lettura);
                                System.out.println("nuova velocità del vento " + lettura.getWind_Speed());
                            }
                        if(R==0) { // Se R è 0, allora posso riprendere la lettura perchè da remoto nessuno a premuto il pulasante per la fermare la raccolta dati
                            avvioLettura(true);
                        }




                        }


                        lettura.setSolar_panels(true);
                        System.out.println("pannelli solari " + lettura.getSolar_panels());




                    }
                };

                scheduler.scheduleAtFixedRate(startCollection, 0, 2, TimeUnit.SECONDS);
                System.out.println("Ho terminato l'esecunzione");

            }
        }




        //System.out.println(startData);
        return "redirect:/WeatherStation";
    }
    @RequestMapping("/stopTask")
    public String stopTask(
            @RequestParam(name="startData", required=true) boolean startData
    ){
        R=1;// cosi non riparte in automatico la lettura dei dati
        c=0;// ora che ho settato il numero di thread posso riprendere la mia task programmata
        if(!startData) {

            scheduler.shutdown();

        }
        return "redirect:/WeatherStation";
    }

    @RequestMapping("/TestingStatus")
    public String TestingStatus(
            @RequestParam(name="Testing", required=true) boolean starTesting,
            Model model
    ){
        //prima di avviare il testing fermo la raccolta dati:
        stopTask(false);
        c=c+1; // ulteriore sicurezza per bloccare lettura
        // Il testing va a monitorare il livello di batteria e se i vari sensori funzionano in maniera corretta
        String TestTemperature;
        String TestPressure;
        String TestHumidity;
        String TestWind_Speed;
        String TestWind_direction;
        String TestRainfall;
        String TestIrradiation;

        Data_Sensors TestingData = new Data_Sensors();

        //Testo se la lettura dei sensori è avvenuta in maniera corretta:


        TestingData.readData(TestingData); // vado a fare una lettura dei sensori e controllo che i parametri restituiti siano corretti
        if(TestingData.getWind_Speed() > 100){
            TestingData.Close_solar_panels();
            System.out.println("test vento veloce "+ TestingData.getWind_Speed());
            System.out.println("pannelli solari "+ TestingData.getSolar_panels());
        }
        //Test Temperatura:
        if(-40<= TestingData.getTemperature() && TestingData.getTemperature()<= 45){
            TestTemperature= "In funzione";
            model.addAttribute("TestTemperature", TestTemperature);
        }else{
            TestTemperature= "Non funzionante";
            model.addAttribute("TestTemperature", TestTemperature);
        }

        //Test Pressione
        if(900 <= TestingData.getPressure() && TestingData.getPressure() <= 1100){
            TestPressure="In funzione";
            model.addAttribute("TestPressure", TestPressure);
        }else{
            TestPressure="Non funzionante";
            model.addAttribute("TestPressure", TestPressure);
        }

        //Test umidità
        if(0 <= TestingData.getHumidity() && TestingData.getHumidity() <= 100){
            TestHumidity="In funzione";
            model.addAttribute("TestHumidity", TestHumidity);
        }else{
            TestHumidity="Non funzionante";
            model.addAttribute("TestHumidity", TestHumidity);
        }

        //Test velocità vento
        if(0 <= TestingData.getHumidity() && TestingData.getHumidity() <= 100){
            TestWind_Speed="In funzione";
            model.addAttribute("TestWind_Speed", TestWind_Speed);
        }else{
            TestWind_Speed="Non funzionante";
            model.addAttribute("TestWind_Speed", TestWind_Speed);
        }

        //Test banderuola
        if(1 <= TestingData.getWind_direction() && TestingData.getWind_direction() <= 4){
            TestWind_direction="In funzione";
            model.addAttribute("TestWind_direction", TestWind_direction);
        }else{
            TestWind_direction="Non funzionante";
            model.addAttribute("TestWind_direction", TestWind_direction);
        }

        //Test precipitazioni
        if(0 <= TestingData.getRainfall() && TestingData.getRainfall() <= 100){
            TestRainfall="In funzione";
            model.addAttribute("TestRainfall", TestRainfall);
        }else{
            TestRainfall="Non funzionante";
            model.addAttribute("TestRainfall", TestRainfall);
        }

        //Test irraggiamento solare

        if(0 <= TestingData.getIrradiation() && TestingData.getIrradiation() <= 100){
            TestIrradiation="In funzione";
            model.addAttribute("TestIrradiation", TestIrradiation);
        }else{
            TestIrradiation="Non funzionante";
            model.addAttribute("TestIrradiation", TestIrradiation);
        }



        //Test livello batteria
        Data_Sensors read_levelBattery= new Data_Sensors();
        int GetBattery= read_levelBattery.levelBattery();
        String output;
        if(70 <= GetBattery && GetBattery <= 100){
            output="Batteria carica";
            model.addAttribute("TestBattery", output);
            model.addAttribute("leveBattery", GetBattery);
        }else if(30 <= TestingData.getBattery() && TestingData.getBattery() <= 69){
            output="Livello medio batteria";
            model.addAttribute("TestBattery", output);
            model.addAttribute("leveBattery", GetBattery);
        }else if(5 <= TestingData.getBattery() && TestingData.getBattery() <= 29){
            output="Livello medio scarico";
            model.addAttribute("TestBattery", output);
            model.addAttribute("leveBattery", GetBattery);
        }else{
            output="Batteria scarica o non funzionante";
            model.addAttribute("TestBattery", output);
            model.addAttribute("leveBattery", GetBattery);
        }

        //test per verificare lo stato dei pannelli solari
        if(TestingData.getSolar_panels()){
            output="pannelli solari aperti";
            model.addAttribute("TestPannelliSolari", output);

        }else{
            output="pannelli solari chiusi";
            model.addAttribute("TestPannelliSolari", output);
        }


        c=0;// pronto per riprendere la lettura dei dati


        return "/ReportStatus";
    }

    @RequestMapping("/StartConfigure")
    public String StartConfigure(
            @RequestParam(name="StartConfigure", required = true) boolean StartConfigure
    ){
        System.out.println("fino a qui bene");
        if(StartConfigure){
            stopTask(false);

        }
        return("Configure");
    }
    @RequestMapping("/upload")
    public String upload(
            @RequestParam(name="FileConfigure", required = true) MultipartFile FileConfigure,
            RedirectAttributes redirectAttributes
     ){
        if (FileConfigure.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Scegli file di configurazione");
            return "redirect:uploadStatus";
        }
        try {

            // estraggo il file e lo salvo
            byte[] bytes = FileConfigure.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + FileConfigure.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "Configurazione caricata correttamente '" + FileConfigure.getOriginalFilename() + "'");

        } catch (IOException e) { //IOException è la classe base per le eccezioni generate durante l'accesso a informazioni tramite flussi, file e directory.
            e.printStackTrace();// utile per verificare dove ho l'errore
        }


        return "redirect:/uploadStatus";
    }

    @RequestMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }


    /*
    Runnable drawRunnable = new Runnable() {
        public void run() {
            Data_Sensors lettura= new Data_Sensors();
            lettura.readData(lettura);
            repository.save(lettura);
        }
    };*/





}