package com.pe.proyecto006.project.servicio;

import com.pe.proyecto006.project.dto.ComentarioDTO;
import java.util.List;

public interface ComentarioServicio {
    
    //PARA CREAR UN COMENTARIO NECESITO EL id DE LA PUBLICACION Y el comentario
    public ComentarioDTO crearComentario(long publicacionId, ComentarioDTO comentarioDTO);
    
    //TRAER COMENTARIOS POR PUBLICACION
    public List<ComentarioDTO> obtenerComentarioPorPublicacionId(long publicacionId);
    
    public ComentarioDTO obtenerComentariPorId(Long publicacionId, Long comentarioId );
    
    public ComentarioDTO actualizarComentario(Long publicacionId, ComentarioDTO solicitudComentario);
    
    public void eliminarComentario(Long publicacionId, Long comentarioId);
}
