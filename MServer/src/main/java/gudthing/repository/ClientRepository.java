package gudthing.repository;

import gudthing.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Ben on 24/04/2016.
 */
public interface ClientRepository extends JpaRepository<Client, Integer>{
    List<Client> findAll();
}
