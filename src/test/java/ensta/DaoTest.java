package ensta;

import java.util.List;

import com.ensta.librarymanager.dao.impl.MembreDao;
import com.ensta.librarymanager.modele.Membre;

public class DaoTest {
    public static void main(String[] args) throws Exception {
        MembreDao md = MembreDao.getInstance();
        List<Membre> list = md.getList();
        for (Membre m : list) {
            System.out.println(m);
        }
    }
}