package com.pe.proyecto006.project.excepciones;

import com.pe.proyecto006.project.dto.ErrorDetalles;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


//ESTA NOTACION LE DICE QUE ESTA CLASE VA A PODER MANEJAR EXCEPCIONES HANDLER
//CON ESTO PUEDE CAPTURAR TODOS LAS CAPTURAS DE LAS EXPCECIONES DE LA APLICACION

//LA EXTENSION ES PARA USAR EL METODO DE LAS VALIDACIONES QUE AÑADIMOS AL DTO
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    
    //ESTA NOTACION SIGNIFICA QUE POR CADA EXCEPTION QUE HAGAMOS CON ESTE TIPO DE EXCEPTION ResourceNotFoundException ESTE METODO ACTUARA Y TOMARA LA 
    //EXCEPCION PARA ADJUNTARLA AL MENSAJE DE ESTE METODO
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetalles> manejarResourceNotForundException(ResourceNotFoundException exception, WebRequest webRequest){
       ErrorDetalles errorDetalles = new ErrorDetalles( new Date(), exception.getMessage(), webRequest.getDescription(false)); 
       return new ResponseEntity<>(errorDetalles, HttpStatus.NOT_FOUND);
    }
    
     //ESTA NOTACION SIGNIFICA QUE POR CADA EXCEPTION QUE HAGAMOS CON ESTE TIPO DE EXCEPTION ResourceNotFoundException ESTE METODO ACTUARA Y TOMARA LA 
    //EXCEPCION PARA ADJUNTARLA AL MENSAJE DE ESTE METODOO
    @ExceptionHandler(BlogAppException.class)
    public ResponseEntity<ErrorDetalles> manejarBlogAppException(BlogAppException exception, WebRequest webRequest){
       ErrorDetalles errorDetalles = new ErrorDetalles( new Date(), exception.getMessage(), webRequest.getDescription(false)); 
       return new ResponseEntity<>(errorDetalles, HttpStatus.BAD_REQUEST);
    }
    
     //ESTA NOTACION SIGNIFICA QUE POR CADA EXCEPTION QUE HAGAMOS CON ESTE TIPO DE EXCEPTION ResourceNotFoundException ESTE METODO ACTUARA Y TOMARA LA 
    //EXCEPCION PARA ADJUNTARLA AL MENSAJE DE ESTE METODO
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetalles> manejarGlobalException(Exception exception, WebRequest webRequest){
       ErrorDetalles errorDetalles = new ErrorDetalles( new Date(), exception.getMessage(), webRequest.getDescription(false)); 
       return new ResponseEntity<>(errorDetalles, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //CUANDO LO QUE SE ENVIA NO ES VALIDO ENTONCES ESTE METODO SALE, PARA LAS VALIDACIONES QUE HEMOS PUESTO AL DTO
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
            //PARA OBTENER EN QUE VARIABLE ESTA EL ERROR
            String nombreCampo = ((FieldError)error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(nombreCampo, mensaje);//LLENAMOS EL MAP DE ERRORES QUE CREAMOS
        });
        
        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }
    
    
    
}
