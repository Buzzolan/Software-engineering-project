package demoWeatherStation;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface Data_SensorRepository extends CrudRepository <Data_Sensors, Long>{
    // uso interfaccia perchè certe funzioni sono già implementate
    //come cercare i dati nel database
    List<Data_Sensors> findByDay(String day); // il framework implementa la funzionalità

    Data_Sensors findById(long id);

}
