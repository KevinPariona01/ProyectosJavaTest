package com.pe.proyecto006.project.servicio;

import com.pe.proyecto006.project.dto.PublicacionDTO;
import com.pe.proyecto006.project.dto.PublicacionRespuestaDTO;
import java.util.List;

public interface PublicacionServicio {
  

    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    
    public List<PublicacionDTO> obtenerPublicaciones();
    
    public PublicacionRespuestaDTO obtenerPublicacionesPaginadas(int numeroDePagina, int medidaPagina, String ordenarPor, String sortDir);
    
    public PublicacionDTO obtenerPublicacionPorId(long id);
    
    public PublicacionDTO actualizarPublcacion(PublicacionDTO publicacionDTO, long id);
    
    public void eliminarPublicacion(long id);
    
}
