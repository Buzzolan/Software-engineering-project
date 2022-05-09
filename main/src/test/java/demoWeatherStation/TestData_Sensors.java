// il package deve corrispondere

package demoWeatherStation;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestData_Sensors {
    @Test
    public void testreadData(){
        Data_Sensors lettura=new Data_Sensors();
        lettura.readData(lettura);
        assertTrue("La temperatura dovrebbe essere nel range",-50<= lettura.getTemperature() && lettura.getTemperature() <= 50 );
        assertTrue("L'umidità dovrebbe essere nel range",0<= lettura.getHumidity() && lettura.getHumidity() <= 100);
        assertTrue("La pressione dovrebbe essere nel range",900<= lettura.getPressure() && lettura.getPressure() <= 1100);
        assertTrue("La velocità del vento dovrebbe essere nel range",0<= lettura.getWind_Speed() && lettura.getWind_Speed() <= 200);
        assertTrue("La direzione del vento dovrebbe essere nel range",0<= lettura.getWind_direction() && lettura.getWind_direction() <= 4);
        assertTrue("Le precipitazioni dovrebbe essere nel range",0<= lettura.getRainfall() && lettura.getIrradiation() <= 50);
        assertTrue("L'irraggiamento solare dovrebbe essere nel range",0<= lettura.getIrradiation() && lettura.getIrradiation() <= 50);
    }

    @Test
    public void testlevelBattery(){
        Data_Sensors readBattery=new Data_Sensors();

        int level = readBattery.levelBattery();
        assertTrue(0<= level && level <= 100);

    }

    @Test
    public void testClose_Solar_panel(){
        Data_Sensors Pannelli_Solari =new Data_Sensors();
        Pannelli_Solari.Close_solar_panels();
        assertFalse(Pannelli_Solari.getSolar_panels());

        Pannelli_Solari.setSolar_panels(true);
        assertTrue(Pannelli_Solari.getSolar_panels());

    }
}
