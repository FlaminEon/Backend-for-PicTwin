package cl.ucn.disc.dsm.pictwin.backend;

import cl.ucn.disc.dsm.pictwin.backend.nodel.pic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PicRepository extends ListCrudRepository<Pic, Long> {
}
