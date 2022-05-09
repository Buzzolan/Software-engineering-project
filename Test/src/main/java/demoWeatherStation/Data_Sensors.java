package demoWeatherStation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Calendar;

@Entity //definisco la persistenza della classe essendo che salvo i dati in un db
public class Data_Sensors {
    @Id // dico al db di usare id come chiave primaria
    @GeneratedValue(strategy = GenerationType.AUTO ) // il mio id viene generato in automatico con numeri interi
    private long id; //identifica la mia lettura dei dati
    private int day;
    private String month;
    private double temperature;
    private double humidity;
    private double pressure;
    private double Wind_Speed;
    private double Wind_direction;
    private double rainfall;
    private double irradiation;
    private int battery;
    private boolean Solar_panels;

    protected Data_Sensors(){}

    public Data_Sensors(int day, String month, double temperature, double humidity, double pressure, double Wind_Speed, double Wind_direction, double rainfall, double irradiation)
    {
        this.day= day;
        this.month= month;
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        this.Wind_Speed=Wind_Speed;
        this.Wind_direction=Wind_direction;
        this.rainfall=rainfall;
        this.irradiation=irradiation;
        //this.battery=battery;
    }

    public long getId() {
        return id;
    }
    public int getDay() {
        return day;
    }
    public String getMonth() {
        return month;
    }
    public double getTemperature() {
        return temperature;
    }
    public double getHumidity() {
        return humidity;
    }
    public double getPressure() {
        return pressure;
    }
    public double getWind_Speed() {
        return Wind_Speed;
    }
    public double getWind_direction() {
        return Wind_direction;
    }
    public double getRainfall() {
        return rainfall;
    }
    public double getIrradiation() {
        return irradiation;
    }
    public int getBattery() {
        return battery;
    }
    public boolean getSolar_panels(){return Solar_panels;}
    public void setSolar_panels(boolean open){this.Solar_panels=open;}

    public void setData(int day, String month, double temperature, double humidity, double pressure, double Wind_Speed, double Wind_direction, double rainfall, double irradiation) {
        this.day= day;
        this.month= month;
        this.temperature=temperature;
        this.humidity=humidity;
        this.pressure=pressure;
        this.Wind_Speed=Wind_Speed;
        this.Wind_direction=Wind_direction;
        this.rainfall=rainfall;
        this.irradiation=irradiation;
        //this.battery=battery;
    }

    public void readData(Data_Sensors lettura){ //immagino di leggere i dati dai vari sensori
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        int monthNumber  = cal.get(Calendar.MONTH);
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        month= monthNames[monthNumber];
        temperature= Math.round(Math.random() * (50 - -50) + -50); // qui c'è la funzione che legge la temperatura dal sensore, ma per velocità creaimo una variabile casuale
        humidity= Math.round(Math.random() * 100);// se il sensore non funziona e quindi non può restituire il valore la funzione assegna un valore di default che nel report mi da un errore
        pressure= Math.round(Math.random() * (1100 - 900) + 900);
        Wind_Speed= Math.round(Math.random() * 200);
        Wind_direction= Math.round(Math.random() * 4);
        rainfall= Math.round(Math.random() * 100);
        irradiation= Math.round(Math.random() * 50);

        lettura.setData( day,  month, temperature, humidity,  pressure,  Wind_Speed,  Wind_direction,  rainfall, irradiation);
    }

    public int levelBattery(){
        battery=(int)(Math.random() * 100);
        return battery;
    }

    public void Close_solar_panels(){
        Solar_panels=false;// quando i pannelli solari sono false allora si chiudono;

    }


}
