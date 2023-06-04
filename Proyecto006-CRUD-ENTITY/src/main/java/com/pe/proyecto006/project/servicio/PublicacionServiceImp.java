package com.pe.proyecto006.project.servicio;

import com.pe.proyecto006.project.dto.PublicacionDTO;
import com.pe.proyecto006.project.dto.PublicacionRespuestaDTO;
import com.pe.proyecto006.project.entidades.Publicacion;
import com.pe.proyecto006.project.excepciones.ResourceNotFoundException;
import com.pe.proyecto006.project.repositorio.PublicacionRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

//INDICO QUE ESTE ES UN SERVICIO DE SPRING
@Service
@Slf4j
public class PublicacionServiceImp implements PublicacionServicio {
    
    //CON ESTA ESTA NOTACION REALIZO LA INYECCION DE LA DEPENDENCIA DE MANERA AUTOMATICA
    //BUSCARA SUS METODOS GET AND SET Y ESTO EVITA INICIALIZARLA EN EL CONSTRUCTOR AQUI
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    
    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO){
        
        /*
        ESTO ES COMO EL SERVICIO PARA INSERTAR UNA PUBLICACION
        ENTONCES: 
        CUANDO SE ENVIA LA INFORMACION DESDE EL CLIENTE EJEMPL0 EL POSTMAN ENVIA UN JSON CON LOS SIGUIENTES ATRIBUTOS
        ESTO LO RECIBE EL DTO
        */
        
           //CONVERTIMOS DE DTO A ENTIDAD
           Publicacion publicacion = mapearEntidad(publicacionDTO);
           
           //GUARDAMOS EN LA BD
           Publicacion nuevaPublicacion = publicacionRepositorio.save(publicacion);
           
           //UNA VEZ GUARDADO
           //CONVERTIMOS DE ENTIDAD A DTO
           PublicacionDTO publicacionRespuesta = mapearDTO(nuevaPublicacion);
           
           //Y RETORNAMOS EL JSON QUE SE INSERTO COMO RESPUESTA
           return publicacionRespuesta;
        
    }
    
    @Override
    public List<PublicacionDTO> obtenerPublicaciones(){
        List<Publicacion> publicaciones = publicacionRepositorio.findAll();
        /*
        A ESTA LISTA QUE OBTENEMOS DE LA BD LA ESTAMOS AÑADIENDO EN UN FLUJO STREAM
        Y LA ESTAMOS MAPEANDO Y CADA OBJETO QUE OBTENGO EN EL OBJETO publicacion LA VAMOS A MAPEAR A DTO
        Y LO VAMOS A MOSTRAR EN UNA LISTA
        */ 
        return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
    }
    
    @Override
    public PublicacionRespuestaDTO obtenerPublicacionesPaginadas(int numeroDePagina, int medidaPagina, String ordenarPor, String sortDir){
        //DE SPRING BOOT FRAMEWORK DOMAIN
        //LO QUE SE HACE ES CREAR UN SORT PARA ORDENAR Y LUEGO PREGUNTAR SI ESE sorDir que se obteiene COMO PARAMETRO ES ASCENDENTE ENTONCES
        //ORDENO POR EL VALOR QUE ESTOY ORDENANDO DE MANERA ASCEDENTE Y CASO CONTRARIO SI NO ES IGUAL ENTONCES LO ORDENAR POR LA VARIABLE
        //QUE ESTOY ORDENANDO ordenarPor PERO DE MANERA DESCENDENTE
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(ordenarPor).ascending():Sort.by(ordenarPor).descending();
        //SIN EN ASC O DESC ERA ASI
        //Pageable pageable = PageRequest.of(numeroDePagina, medidaPagina, Sort.by(ordenarPor));
        Pageable pageable = PageRequest.of(numeroDePagina, medidaPagina, sort);
        Page<Publicacion> publicaciones = publicacionRepositorio.findAll(pageable);
        
        //ANTES ERA ASI NOMAS CUANDO NO SE PASABAN ESOS DOS PARAMETROS NUEVOS DE NUMERO DE PAGINA Y MEDIDA DE PAGINA
        //List<Publicacion> publicaciones = publicacionRepositorio.findAll();
        
        List<Publicacion> Listapublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido =  Listapublicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
        
        PublicacionRespuestaDTO publicacionRespuestaDTO = new PublicacionRespuestaDTO();
        publicacionRespuestaDTO.setContenido(contenido);
        publicacionRespuestaDTO.setNumeroPagina(numeroDePagina);
        publicacionRespuestaDTO.setMedidaPagina(medidaPagina);
        publicacionRespuestaDTO.setTotalElementos(publicaciones.getTotalElements());//ME DICE EL TOTAL DE ELEMENTOS QUE TIENE LA TABLA
        publicacionRespuestaDTO.setTotalPaginas(publicaciones.getTotalPages());//EL TOTAL DE PAGINAS QUE TIENE DE ACUERDO A SU CONTENIDO DE LA TABLA PODRIA TENER 2 PAGINAS O ETC
        publicacionRespuestaDTO.setUltima(publicaciones.isLast());//SIRVE PARA VER SI ES LA ULTIMA, SI ES LA ULTIMA ME SALE TRUE Y SI NO ES LA ULTIMA ME SALE FALSE
        /*
        A ESTA LISTA QUE OBTENEMOS DE LA BD LA ESTAMOS AÑADIENDO EN UN FLUJO STREAM
        Y LA ESTAMOS MAPEANDO Y CADA OBJETO QUE OBTENGO EN EL OBJETO publicacion LA VAMOS A MAPEAR A DTO
        Y LO VAMOS A MOSTRAR EN UNA LISTA
        */ 
        //ANTES
        //return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
        return publicacionRespuestaDTO;
    }
    
    @Override
    public PublicacionDTO obtenerPublicacionPorId(long id){
        //BUSCA POR ID Y SI NO ESCUENTRA POR ESE TIENE EL ORELSE NOS DA LA EXCEPCION QUE HEMOS CREADO MANDANDOS LOS PARAMETROS PARA QUE IMPRIMA
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));
           return mapearDTO(publicacion);
    }
    
    @Override
    public PublicacionDTO actualizarPublcacion(PublicacionDTO publicacionDTO, long id){
        log.info("Parametro que llega: "+publicacionDTO);
        //PRIMERO BUSCAMOS LA PUBLICACION
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));
        publicacion.setTitulo(publicacionDTO.getTitulo());
        publicacion.setContenido(publicacionDTO.getContenido());
        publicacion.setDescripcion(publicacionDTO.getDescripcion());
        
        Publicacion publicacionActualizada = publicacionRepositorio.save(publicacion);
        log.info("Parametro que se va: "+publicacionActualizada);
        log.info("Parametro que se va mapeado a dto: "+mapearDTO(publicacionActualizada));
        return mapearDTO(publicacionActualizada);
    }
    
    @Override
    public void eliminarPublicacion(long id){
        Publicacion publicacion = publicacionRepositorio.findById(id).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", id));
        publicacionRepositorio.delete(publicacion);
    }
    
    //CONVIERTE ENTIDAD A DTO 
    private PublicacionDTO mapearDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());
        
        return publicacionDTO;
    }
    
    //CONVIERTE DTO A ENTIDAD
    private Publicacion mapearEntidad(PublicacionDTO publicacionDTO){
        
           Publicacion publicacion = new Publicacion();
           publicacion.setTitulo(publicacionDTO.getTitulo());
           publicacion.setDescripcion(publicacionDTO.getDescripcion());
           publicacion.setContenido(publicacionDTO.getContenido());
           
        
        return publicacion;
    }
    
}
