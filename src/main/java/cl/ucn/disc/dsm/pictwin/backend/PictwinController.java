package cl.ucn.disc.dsm.pictwin.backend;

import cl.ucn.disc.dsm.pictwin.backend.model.Pic;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class PictwinController {

    @Autowired
    private PicRepository repository;

    @GetMapping("/pics")
    public List<Pic> index(){
        return this.repository.findAll();
    }
}
