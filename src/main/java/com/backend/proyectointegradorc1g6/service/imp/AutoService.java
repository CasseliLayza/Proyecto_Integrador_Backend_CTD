package com.backend.proyectointegradorc1g6.service.imp;

import com.backend.proyectointegradorc1g6.dto.input.AutoDtoInput;
import com.backend.proyectointegradorc1g6.dto.output.AutoDtoOut;
import com.backend.proyectointegradorc1g6.entity.Auto;
import com.backend.proyectointegradorc1g6.entity.Imagen;
import com.backend.proyectointegradorc1g6.exception.MatriculaDuplicadaException;
import com.backend.proyectointegradorc1g6.exception.ResourceNotFoundException;
import com.backend.proyectointegradorc1g6.repository.AutosRepository;
import com.backend.proyectointegradorc1g6.service.IAutoService;
import com.backend.proyectointegradorc1g6.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AutoService implements IAutoService {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private AutosRepository autosRepository;
    private ModelMapper modelMapper;
    @Autowired
    private S3Service s3Service;

    public AutoService(AutosRepository autosRepository, ModelMapper modelMapper) {
        this.autosRepository = autosRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }


    @Override
    public AutoDtoOut registrarAuto(AutoDtoInput autoDtoInput) throws MatriculaDuplicadaException {
        LOGGER.info("autoDtoInput --> {}", JsonPrinter.toString(autoDtoInput));

        String matricula = autoDtoInput.getMatricula();
        if (autosRepository.findByMatricula(matricula) != null)
          throw new MatriculaDuplicadaException("La " + matricula + " que intenta registrar ya existe en el sistema");
        //if (autosRepository.existsByMatricula(matricula))
          //  throw new MatriculaDuplicadaException("La " + matricula + " que intenta registrar ya existe en el sistema");

        Auto autoARegistrar = modelMapper.map(autoDtoInput, Auto.class);

        List<Imagen> imagenes = autoARegistrar.getImagenes().stream()
                .map(imagenDto -> {
                    Imagen imagen = modelMapper.map(imagenDto, Imagen.class);
                    imagen.setAuto(autoARegistrar);
                    return imagen;
                }).collect(Collectors.toList());
        autoARegistrar.setImagenes(imagenes);
        Auto AutoRegistrado = autosRepository.save(autoARegistrar);
        //LOGGER.info("AutoRegistrado --> {}", JsonPrinter.toString(AutoRegistrado));
        LOGGER.info("AutoRegistrado --> {}", AutoRegistrado.toString());

        AutoDtoOut autoDtoOut = modelMapper.map(AutoRegistrado, AutoDtoOut.class);
        LOGGER.info("autoDtoOut --> {}", JsonPrinter.toString(autoDtoOut));

        return autoDtoOut;
    }

    @Override
    public List<AutoDtoOut> listarAutos() {
        List<AutoDtoOut> autoDtoOuts = autosRepository.findAll().stream()
                .map(auto -> modelMapper.map(auto, AutoDtoOut.class)).collect(Collectors.toList());
        LOGGER.info("Listado de todos los autos --> {}", JsonPrinter.toString(autoDtoOuts));
        return autoDtoOuts;
    }

    @Override
    public AutoDtoOut buscarAuto(Long id) {
        LOGGER.info("inputId --> {}", id);

        Auto autoBuscado = autosRepository.findById(id).orElse(null);
        LOGGER.info("Auto buscado --> {}", autoBuscado);
        AutoDtoOut autoDtoOut = null;
        if (autoBuscado != null) {
            autoDtoOut = modelMapper.map(autoBuscado, AutoDtoOut.class);
            LOGGER.info("Auto encontrado --> {}", JsonPrinter.toString(autoDtoOut));
        } else {
            LOGGER.info("Auto no encontrado, verificar el id --> {} ", id);
        }

        return autoDtoOut;
    }

    @Override
    public AutoDtoOut actualizarAuto(AutoDtoInput autoDtoInput, Long id) throws ResourceNotFoundException {

        LOGGER.info("autoDtoInput --> {}", JsonPrinter.toString(autoDtoInput));
        LOGGER.info("id input --> {}", id);

        AutoDtoOut autoDtoOut = null;
        Auto autoEncontrado = autosRepository.findById(id).orElse(null);

        if (autoEncontrado != null) {
            Auto autoAActualizar = modelMapper.map(autoDtoInput, Auto.class);
            autoAActualizar.setId(autoEncontrado.getId());
            List<Imagen> imagenes = autoAActualizar.getImagenes().stream()
                    .peek(imagen -> imagen.setAuto(autoAActualizar))
                    .collect(Collectors.toList());
            autoAActualizar.setImagenes(imagenes);
            Auto autoActualizado = autosRepository.save(autoAActualizar);
            LOGGER.info("Auto actualizado --> {}", autoActualizado);

            autoDtoOut = modelMapper.map(autoActualizado, AutoDtoOut.class);
            LOGGER.info("autoDtoOut --> {}", JsonPrinter.toString(autoDtoOut));
        } else {
            LOGGER.error("No fue posible actualizar el auto porque no se encuentra en nuestra base de datos, verificar id {}", id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de auto a actualizar con id: " + id);
        }

        return autoDtoOut;
    }

    @Override
    public void eliminarAuto(Long id) throws ResourceNotFoundException {
        if (buscarAuto(id) != null) {
            autosRepository.deleteById(id);
            LOGGER.info("Auto eliminado, con id --> {}", id);
        } else {
            LOGGER.info("No existe registro de auto con id: " + id);
            //Custom exception
            throw new ResourceNotFoundException("No existe registro de auto con id: " + id);

        }

    }

    public AutoDtoOut registrarAuto(AutoDtoInput autoDtoInput, List<MultipartFile> imagenes, int indiceImagenPrincipal) throws MatriculaDuplicadaException {

        LOGGER.info("autoDtoInput --> {}", JsonPrinter.toString(autoDtoInput));

        String matricula = autoDtoInput.getMatricula();
        if (autosRepository.existsByMatricula(matricula)) {
            throw new MatriculaDuplicadaException("La " + matricula + " que intenta registrar ya existe en el sistema");
        }

        Auto autoARegistrar = modelMapper.map(autoDtoInput, Auto.class);

        List<Imagen> imagenesActualizadas = IntStream.range(0, imagenes.size())
                .mapToObj(i -> {
                    try {
                        MultipartFile archivoImagen = imagenes.get(i);

                        String urlS3 = s3Service.putObject(archivoImagen);

                        Imagen imagen = new Imagen();
                        imagen.setUrl(urlS3);
                        imagen.setAuto(autoARegistrar);

                        if (i == indiceImagenPrincipal) {
                            //autoARegistrar.setImagenPrincipal(imagen);
                            imagen.setEsPrincipal(true);
                        }

                        return imagen;
                    } catch (Exception e) {
                        LOGGER.error("Error al subir imagen a S3 --> {}", e.getMessage());
                        throw new RuntimeException("Error al subir imagen", e);
                    }
                })
                .collect(Collectors.toList());

        autoARegistrar.setImagenes(imagenesActualizadas);
        Auto autoRegistrado = autosRepository.save(autoARegistrar);
        LOGGER.info("Auto registrado --> {}", autoRegistrado);

        AutoDtoOut autoDtoOut = modelMapper.map(autoRegistrado, AutoDtoOut.class);
        LOGGER.info("autoDtoOut --> {}", JsonPrinter.toString(autoDtoOut));

        return autoDtoOut;
    }


    private void configureMapping() {
        modelMapper.typeMap(AutoDtoInput.class, Auto.class)
                .addMappings(mapper -> mapper.map(AutoDtoInput::getImagenes, Auto::setImagenes));
        modelMapper.typeMap(Auto.class, AutoDtoOut.class)
                .addMappings(mapper -> mapper.map(Auto::getImagenes, AutoDtoOut::setImagenes));

/*        modelMapper.typeMap(ImagenDtoInput.class, Imagen.class)
                .addMappings(mapper -> mapper.map(ImagenDtoInput::getAuto, Imagen::setAuto));
        modelMapper.typeMap(Imagen.class, ImagenDtoOut.class)
                .addMappings(mapper -> mapper.map(src -> src.getAuto().getId(), ImagenDtoOut::setAutoId));*/
    }
}
