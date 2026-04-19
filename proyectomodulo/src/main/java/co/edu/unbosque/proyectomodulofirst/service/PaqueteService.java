package co.edu.unbosque.proyectomodulofirst.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.proyectomodulofirst.dto.PaqueteDTO;
import co.edu.unbosque.proyectomodulofirst.entity.Paquete;
import co.edu.unbosque.proyectomodulofirst.exception.CiudadException;
import co.edu.unbosque.proyectomodulofirst.exception.DireccionException;
import co.edu.unbosque.proyectomodulofirst.exception.InvalidDataException;
import co.edu.unbosque.proyectomodulofirst.exception.LanzadorDeExcepcion;
import co.edu.unbosque.proyectomodulofirst.exception.PesoException;
import co.edu.unbosque.proyectomodulofirst.exception.TipoPaqueteException;
import co.edu.unbosque.proyectomodulofirst.repository.PaqueteRepository;

@Service
public class PaqueteService implements CRUDOperation<PaqueteDTO> {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpleadoConductorService conductorService;

    @Autowired
    private EmpleadoManipuladorService manipuladorService;

    @Autowired
    private PaqueteRepository paqueteRepo;

    @Autowired
    private ModelMapper mapper;

    public PaqueteService() {
    }

    // 0  - Creado exitosamente
    // 1  - Tipo de paquete nulo
    // 2  - Peso invalido
    // 3  - Ciudad de origen invalida
    // 4  - Ciudad de destino invalida
    // 5  - Direccion de origen invalida
    // 6  - Direccion de destino invalida
    // 7  - Id invalido
    // 8  - Usuario no existe
    // 9  - Conductor no existe
    // 10 - Manipulador no existe
    // 11 - Conductor ocupado
    // 12 - Manipulador ocupado
    @Override
    public int create(PaqueteDTO newData) {
        try {
            LanzadorDeExcepcion.verificarTipoPaquete(newData.getTipo());
            LanzadorDeExcepcion.verificarPeso(newData.getPeso());
            LanzadorDeExcepcion.verificarCiudad(newData.getCiudadDeOrigen(), "ciudad de origen");
            LanzadorDeExcepcion.verificarCiudad(newData.getCiudadDeDestino(), "ciudad de destino");
            LanzadorDeExcepcion.verificarDireccion(newData.getDireccionDeOrigen(), "direccion de origen");
            LanzadorDeExcepcion.verificarDireccion(newData.getDireccionDeDestino(), "direccion de destino");
            LanzadorDeExcepcion.verificarIdNegativo(newData.getIdUsuario());
            LanzadorDeExcepcion.verificarIdNegativo(newData.getIdConductor());
            LanzadorDeExcepcion.verificarIdNegativo(newData.getIdManipulador());
        } catch (TipoPaqueteException e) {
            return 1;
        } catch (PesoException e) {
            return 2;
        } catch (CiudadException e) {
            return 3;
        } catch (DireccionException e) {
            return 5;
        } catch (InvalidDataException e) {
            return 7;
        }

        if (!usuarioService.exist(newData.getIdUsuario())) return 8;
        if (!conductorService.exist(newData.getIdConductor())) return 9;
        if (!manipuladorService.exist(newData.getIdManipulador())) return 10;

        List<Paquete> paquetes = (List<Paquete>) paqueteRepo.findAll();
        for (Paquete p : paquetes) {
            if (p.getIdConductor() == newData.getIdConductor()
                    && p.getTiempoRestanteMinutos() > 0) return 11;
            if (p.getIdManipulador() == newData.getIdManipulador()
                    && p.getTiempoRestanteMinutos() > 0) return 12;
        }

        newData.setFechaEnvio(LocalDateTime.now());
        newData.setMaxHoras(newData.getTipo().getMaxHoras());
        newData.setTiempo(newData.getMaxHoras());
        paqueteRepo.save(mapper.map(newData, Paquete.class));
        return 0;
    }

    @Override
    public List<PaqueteDTO> getAll() {
        List<Paquete> entityList = (List<Paquete>) paqueteRepo.findAll();
        List<PaqueteDTO> dtoList = new ArrayList<>();
        entityList.forEach((entidad) -> {
            PaqueteDTO dto = mapper.map(entidad, PaqueteDTO.class);
            dto.setTiempo((int) entidad.getTiempoRestanteMinutos());
            dtoList.add(dto);
        });
        return dtoList;
    }

    // 0 - Eliminado exitosamente
    // 1 - No encontrado
    // 2 - Id invalido
    @Override
    public int deleteById(Long id) {
        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 2;
        }
        Optional<Paquete> encontrado = paqueteRepo.findById(id);
        if (encontrado.isPresent()) {
            paqueteRepo.delete(encontrado.get());
            return 0;
        }
        return 1;
    }

    // 0 - Actualizado exitosamente
    // 1 - Id invalido
    // 2 - Ciudad de destino invalida
    // 3 - Direccion de destino invalida
    // 4 - No encontrado
    @Override
    public int updateById(Long id, PaqueteDTO newData) {
        try {
            LanzadorDeExcepcion.verificarIdNegativo(id);
        } catch (InvalidDataException e) {
            return 1;
        }

        Optional<Paquete> encontrado = paqueteRepo.findById(id);

        if (encontrado.isPresent()) {
            try {
                LanzadorDeExcepcion.verificarCiudad(newData.getCiudadDeDestino(), "ciudad de destino");
                LanzadorDeExcepcion.verificarDireccion(newData.getDireccionDeDestino(), "direccion de destino");
            } catch (CiudadException e) {
                return 2;
            } catch (DireccionException e) {
                return 3;
            }

            Paquete temp = encontrado.get();
            temp.setCiudadDeDestino(newData.getCiudadDeDestino());
            temp.setDireccionDeDestino(newData.getDireccionDeDestino());
            paqueteRepo.save(temp);
            return 0;
        }

        return 4;
    }

    @Override
    public long count() {
        return paqueteRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return paqueteRepo.existsById(id);
    }
}
