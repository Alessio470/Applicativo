package dao;

import model.Volo;
import java.util.List;

public interface VoloDAO {
    void inserisciVolo(Volo v) throws Exception;
    List<Volo> leggiTuttiIVoli() throws Exception;
}
