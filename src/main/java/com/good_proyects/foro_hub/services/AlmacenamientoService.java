package com.good_proyects.foro_hub.services;
import com.good_proyects.foro_hub.services.iServices.iAlmacenamientoService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
//por si falla poner @AllArgsConstructor
public class AlmacenamientoService implements iAlmacenamientoService {

    private final static String ALMACENAMIENTO_LOCAL = "mediafile";

    @PostConstruct
    @Override
    public void iniciar() {
        try {
            Files.createDirectories(Paths.get(ALMACENAMIENTO_LOCAL));
        } catch (IOException e) {
            throw new RuntimeException("ERROR ALMACENAMIENTO: Falla al inicializar ruta de almacenamiento!", e);
        }
    }

    @Override
    public String almacenar(MultipartFile archivo) {
        String nombreArchivoOriginal = archivo.getOriginalFilename();
        String nombreArchivo = UUID.randomUUID()+"."+ StringUtils.getFilenameExtension(nombreArchivoOriginal);

        if (archivo.isEmpty()){
            throw new RuntimeException("ERROR VACIO: No es posible almacenar un archivo vacio!");
        }

        try {
            InputStream entrada = archivo.getInputStream();
            Files.copy(entrada, Paths.get(ALMACENAMIENTO_LOCAL).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("ERROR:Falla al almacener el archivo ",e);
        }
        return nombreArchivo;
    }

    @Override
    public Path cargar(String nombreArchivo) {
        return Paths.get(ALMACENAMIENTO_LOCAL).resolve(nombreArchivo);
    }

    @Override
    public Resource cargarRecurso(String nombreArchivo) {
        try {
            Path archivo = cargar(nombreArchivo);
            Resource recurso = new UrlResource(archivo.toUri());

            if (recurso.exists() || recurso.isReadable()){
                return recurso;
            }else {
                throw new RuntimeException("ERROR LECTURA: No es posible leer el archivo! " + nombreArchivo);
            }
        }catch (MalformedURLException e){
            throw new RuntimeException("ERROR: No se puede leer el archivo! "+nombreArchivo, e);
        }
    }

    @Override
    public void eliminarRecurso(String nombreArchivo) {
        Path archivo = cargar(nombreArchivo);
        try {
            FileSystemUtils.deleteRecursively(archivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
