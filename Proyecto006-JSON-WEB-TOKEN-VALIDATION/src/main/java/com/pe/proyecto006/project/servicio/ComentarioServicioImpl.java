package com.pe.proyecto006.project.servicio;

import com.pe.proyecto006.project.dto.ComentarioDTO;
import com.pe.proyecto006.project.entidades.Comentario;
import com.pe.proyecto006.project.entidades.Publicacion;
import com.pe.proyecto006.project.excepciones.BlogAppException;
import com.pe.proyecto006.project.excepciones.ResourceNotFoundException;
import com.pe.proyecto006.project.repositorio.ComentarioRepositorio;
import com.pe.proyecto006.project.repositorio.PublicacionRepositorio;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ComentarioServicioImpl implements ComentarioServicio {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private ComentarioRepositorio comentarioRepositorio;
    
    @Autowired
    private PublicacionRepositorio publicacionRepositorio;
    
    @Override
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = mapearEntidad(comentarioDTO);
        //USO EL METODO DE BUSCAR LA PUBLICACION
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepositorio.save(comentario);
        
           return mapearDTO(nuevoComentario);
    }
    
    @Override
    public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId){
        List<Comentario> comentarios = comentarioRepositorio.findByPublicacionId(publicacionId);
        /*
        CON ESTO USAMOS EL MAP PARA QUE LA LISTA QUE NOS ENTREGA LA MAPEEMOS A UN DTO CADA OBJETO DE LA LISTA
        Y LUEGO LO COLECTAMOS EN UNA LISTA DE TIPO LIST
        */
        return comentarios.stream().map(comentario -> mapearDTO(comentario)).collect(Collectors.toList());
    }
    
    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId ));//ESTO ES SI NO LOGRA ENCONTRAR NI LA PUBLICACION NI COMENTARIO
        
        //SI EL ID QUE TIENE EL COMENTARIO NO ES IGUAL AL ID DE LA PUBLICACION 
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        
        //PARA RETORNAR LA RESPUESTA SE DEBE DE MAPEAR EL COMENTARIO A DTO
        return mapearDTO(comentario);
        
    }
    
    public ComentarioDTO actualizarComentario(Long publicacionId, ComentarioDTO solicitudComentario){
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio.findById(solicitudComentario.getId())
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", solicitudComentario.getId() ));//ESTO ES SI NO LOGRA ENCONTRAR NI LA PUBLICACION NI COMENTARIO
        
        //SI EL ID QUE TIENE EL COMENTARIO NO ES IGUAL AL ID DE LA PUBLICACION 
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        
        comentario.setNombre(solicitudComentario.getNombre());
        comentario.setEmail(solicitudComentario.getEmail());
        comentario.setCuerpo(solicitudComentario.getCuerpo());
        
        Comentario comentarioActualizado = comentarioRepositorio.save(comentario);
        
        return mapearDTO(comentarioActualizado);
    }
    
    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId){
        Publicacion publicacion = publicacionRepositorio.findById(publicacionId).orElseThrow(()-> new ResourceNotFoundException("Publicacion", "id", publicacionId));
        
        Comentario comentario = comentarioRepositorio.findById(comentarioId)
                .orElseThrow(()-> new ResourceNotFoundException("Comentario", "id", comentarioId ));//ESTO ES SI NO LOGRA ENCONTRAR NI LA PUBLICACION NI COMENTARIO
        
        //SI EL ID QUE TIENE EL COMENTARIO NO ES IGUAL AL ID DE LA PUBLICACION 
        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }
        
        comentarioRepositorio.delete(comentario);
    }
    
    
    
    
    //METODOS GENERICOS PARA USAR ENTRE ENTIDAD Y DTO
    private ComentarioDTO mapearDTO(Comentario comentario){
        
        ComentarioDTO comentarioDTO = modelMapper.map(comentario, ComentarioDTO.class);
        
        /*ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getNombre());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());*/
        
        return comentarioDTO;
    }
    
    private Comentario mapearEntidad(ComentarioDTO comentarioDTO){
        
        Comentario comentario = modelMapper.map(comentarioDTO, Comentario.class);
        
        /*Comentario comentario = new Comentario();
        comentario.setId(comentarioDTO.getId());
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());*/
        
        return comentario;
    }

    
    
    
    
    
}
