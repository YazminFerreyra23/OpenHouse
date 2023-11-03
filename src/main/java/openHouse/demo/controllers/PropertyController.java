package openHouse.demo.controllers;

import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import openHouse.demo.entities.Comment;
import openHouse.demo.entities.Property;
import openHouse.demo.entities.User;
import openHouse.demo.enums.City;
import openHouse.demo.enums.PropType;
import openHouse.demo.exceptions.MiException;
import openHouse.demo.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/propiedad")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;
  
    @GetMapping("/registrarPropiedad")
    public String registrarPropiedad(ModelMap modelo, HttpSession session){
        User user = (User) session.getAttribute("usersession");
        modelo.put("Cities", City.values());
        modelo.put("Propiedades", PropType.values());
        modelo.put("user", user);
        return "registrar_propiedad.html";
    }
    
    @PostMapping("/registrarPropiedad/{id}")
    public String  registroPropiedad(@PathVariable String id, @RequestParam Double precioBase, @RequestParam String codigoPostal, 
            @RequestParam String direccion, @RequestParam String descripcion, ModelMap modelo,MultipartFile archivo,@RequestParam String ciudad, 
            @RequestParam String tipoPropiedad,@DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaAlta, @DateTimeFormat(pattern = "yyyy-MM-dd")Date fechaBaja ){
        
        try {
            propertyService.crearProperty(precioBase, codigoPostal, direccion, descripcion,
                    id, archivo, ciudad, tipoPropiedad, fechaAlta, fechaBaja);
            modelo.put("exito", "Propiedad cargada correctamente");
            return "inicio.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("precioBase",precioBase);
            modelo.put("codigoPostal",codigoPostal);
            modelo.put("direccion",direccion);
            modelo.put("descripcion",descripcion);
            return "registrar_propiedad.html";
        }
    }

    @PostMapping("/detalles/{id}")
    public String mostrarPropiedad(@PathVariable String id, ModelMap modelo){
        Property propiedad = propertyService.getOne(id);
        List<Comment> comentarios = propiedad.getComentarios();
        modelo.put("comentarios", comentarios);
        modelo.put("propiedad", propiedad);
        return "propiedad_detalles.html";
    }
    

    
    
    @GetMapping("/buscarPorCP")
    public String buscarPorCodigoPostal(@RequestParam String codigoPostal, ModelMap modelo) {
        List<Property> propiedadesCP = propertyService.buscarPorCodigoPostal(codigoPostal);
        modelo.addAttribute("propiedadesCP", propiedadesCP);
        return "busqueda.html";
    }
    
    @GetMapping("/buscarPorCiudad")
    public String buscarPorCiudad(@RequestParam String ciudad, ModelMap modelo){
        
        List<Property> propiedadesCiudad = propertyService.buscarPorCiudad(ciudad);
        
        modelo.addAttribute("propiedadesCiudad", propiedadesCiudad);
        
        return "busqueda.html";
    }
    
    @GetMapping("/buscarSegunPrecio")
    public String buscarSegunPrecio(@RequestParam String minimo, String maximo, ModelMap modelo){
        
        List<Property> propiedadesSegunPrecio = propertyService.buscarSegunPrecio(minimo, maximo);
        
        modelo.addAttribute("propiedadesSegunPrecio", propiedadesSegunPrecio);
        
        return "busqueda.html";
    }
}
