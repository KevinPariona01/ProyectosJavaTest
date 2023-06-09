package com.pe.proyecto006.project.controlador;

import com.pe.proyecto006.project.dto.PublicacionDTO;
import com.pe.proyecto006.project.dto.PublicacionRespuestaDTO;
import com.pe.proyecto006.project.servicio.PublicacionServicio;
import com.pe.proyecto006.project.utilerias.AppConstantes;
import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publicaciones")
@Slf4j
public class PublicacionControlador {
    
        @Autowired
        private PublicacionServicio publicacionServicio;
        
        //AÑADIMOS EL VALID PARA QUE VALIDE LO QUE HEMOS PUESTO
        //NOTA: EL VALID DEBE ESTAR AL COSTADO DEL REQUEST BODY
        @PostMapping
        public ResponseEntity<PublicacionDTO> crearPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO ){
            //LE PASAMOS AL SERVICIO EL PARAMETRO QUE NOS LLEGA EN LA PETICION, SABEMOS QUE EL SERVICIO VA A DEVOLVER
            //UN TIPO PublicacionDTO para obtener la respuesta POR ESO NO HAY PROBLEMA YA QUE AQUI EL ResponseEntity ES 
            //DE TIPO PublicacionDTO Y EL SERVICIO RETORNA EL MISMO TIPO ESO LE MANDAMOS DE PARAMETRO AL ResponseEntity
            //YA NO PONEMOS EL TIPO EN <> PORQUE SE SOBREENTIENDE PERO SE PUEDE PONER, Y SI TODO SALE BIEN LE MANDAMOS
            //TAMBIEN EL HTTP STATUS QUE SE CREO CORRECTAMENTE
               return new ResponseEntity<>(publicacionServicio.crearPublicacion(publicacionDTO), HttpStatus.CREATED);
        }
        
        @GetMapping
        public List<PublicacionDTO> obtenerPublicaciones( ){
            
            return publicacionServicio.obtenerPublicaciones();
        }
        
        /*
        CUANDO USAMOS EL EL RequestParam COMO PARAMETRO QUE RECIBE AHI SI PONEMOS DE ESTA FORMA LA RUTA
        LOS DOS PARAMETROS SI NO ENVIAMOS TIENEN OPCIONES POR DEFAULT
        http://localhost:8080/api/publicaciones/paginadas?numeroDePagina=0&medidaPagina=3&ordenarPor=contenido&sortDir=ASC
        */
        @GetMapping("/paginadas")
        public PublicacionRespuestaDTO obtenerPublicacionesPaginadas(
                /*@RequestParam(value="numeroDePagina", defaultValue="0", required=false) int numeroDePagina, 
                @RequestParam(value="medidaPagina", defaultValue="10", required=false) int medidaPagina,
                @RequestParam(value="ordenarPor", defaultValue="id", required=false) String ordenarPor ,
                @RequestParam(value="sortDir", defaultValue="asc", required=false) String sortDir*/
                @RequestParam(value="numeroDePagina", defaultValue= AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO, required=false) int numeroDePagina, 
                @RequestParam(value="medidaPagina", defaultValue=AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO, required=false) int medidaPagina,
                @RequestParam(value="ordenarPor", defaultValue=AppConstantes.ORDENAR_POR_DEFECTO, required=false) String ordenarPor ,
                @RequestParam(value="sortDir", defaultValue=AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO, required=false) String sortDir
        ){
            
            return publicacionServicio.obtenerPublicacionesPaginadas(numeroDePagina, medidaPagina, ordenarPor, sortDir);
        }
        
        //LA PETICION SERIA ASI http://localhost:8080/api/publicaciones/1
        @GetMapping("/{id}")
        public ResponseEntity<PublicacionDTO> obtenerPublicacionPorId(@PathVariable(name="id") long id){
            return ResponseEntity.ok(publicacionServicio.obtenerPublicacionPorId(id));
        }
        
        //LA PETICION SERIA ASI http://localhost:8080/api/publicaciones/1
        //EL CUERPO DEL DTO ES LO MISMO, EN EL BODY
        @PutMapping("/{id}")
        public ResponseEntity<PublicacionDTO> actualizarPublcacion(@Valid @RequestBody PublicacionDTO publicacionDTO, @PathVariable(name="id") long id){
            log.info("INGRESO1: " + publicacionDTO);
            PublicacionDTO publicacionRespuesta = publicacionServicio.actualizarPublcacion(publicacionDTO, id);
            log.info("INGRESO: " + publicacionRespuesta);
            return new ResponseEntity<>(publicacionRespuesta, HttpStatus.OK);
        }
        
        @DeleteMapping("/{id}")
        public ResponseEntity<String> eliminarPublicacion(@PathVariable(name="id") long id){
                publicacionServicio.eliminarPublicacion(id);
                return new ResponseEntity<>("Publicacion eliminada correctamente",HttpStatus.OK);
        }
    
}
