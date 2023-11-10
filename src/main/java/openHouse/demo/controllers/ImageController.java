package openHouse.demo.controllers;

import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.services.PropertyService;
import openHouse.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    UserService userService;
    
    @Autowired
    PropertyService propertyService;

    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenCliente(@PathVariable String id) {
        User cliente = userService.getOne(id);
        byte[] imagen = cliente.getImage().getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    @GetMapping("/propiedad/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable String id) {
        Property propiedad = propertyService.getOne(id);
        byte[] imagen = propiedad.getImagenes().get(0).getContent();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }
    
    
}
