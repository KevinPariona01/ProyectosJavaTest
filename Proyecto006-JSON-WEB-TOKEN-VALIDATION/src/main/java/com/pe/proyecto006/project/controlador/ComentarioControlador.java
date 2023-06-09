package com.pe.proyecto006.project.controlador;

import com.pe.proyecto006.project.dto.ComentarioDTO;
import com.pe.proyecto006.project.servicio.ComentarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class ComentarioControlador {
    
    @Autowired
    private ComentarioServicio comentarioServicio;
    
    //EM METODO GET SIN BODY O TAMBIEN PERO NO LO LEEAR PORQUE ES GET
    //http://localhost:8080/api/publicaciones/6/comentarios
    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable(value="publicacionId") long publicacionId){
         return comentarioServicio.obtenerComentarioPorPublicacionId(publicacionId);
    }
    
    
    //FORMA DE COMO CONSUMIR EL API Y EL CUERPO DEL COMENTARIO EN UN BODY
    //http://localhost:8080/api/publicaciones/6/comentarios
    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable(value="publicacionId") long publicacionId, @RequestBody ComentarioDTO comentarioDTO){
        
        return new ResponseEntity<>(comentarioServicio.crearComentario(publicacionId, comentarioDTO), HttpStatus.CREATED);
    }
    
    //http://localhost:8080/api/publicaciones/6/comentarios/1
    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> obtenerComentariPorId(@PathVariable(value="publicacionId") long publicacionId, @PathVariable(value="comentarioId") long comentarioId){
        ComentarioDTO comentarioDTO = comentarioServicio.obtenerComentarioPorId(publicacionId, comentarioId);
         return new ResponseEntity<>(comentarioDTO, HttpStatus.OK);
    }
    
    
    /*
    BODY
    {
    "id":1,
    "nombre":"Contenido2",
    "email":"kx@gmail.com",
    "cuerpo":"cuerpo2"
}
    */
    //http://localhost:8080/api/publicaciones/6/comentarios
    @PutMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> actualizarComentario(@PathVariable(value="publicacionId") long publicacionId, @RequestBody ComentarioDTO comentarioDTO){
        ComentarioDTO comentarioActualizado = comentarioServicio.actualizarComentario(publicacionId, comentarioDTO);
        return new ResponseEntity<>(comentarioActualizado, HttpStatus.OK);
    }
    
    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> elminarComentario(@PathVariable(value="publicacionId") long publicacionId, @PathVariable(value="comentarioId") long comentarioId){
        comentarioServicio.eliminarComentario(publicacionId, comentarioId);
        return new ResponseEntity<>("Comentario eliminado cone exito", HttpStatus.OK);
    }

}
