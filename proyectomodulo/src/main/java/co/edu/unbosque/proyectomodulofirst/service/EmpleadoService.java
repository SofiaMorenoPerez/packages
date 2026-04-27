package co.edu.unbosque.proyectomodulofirst.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.proyectomodulofirst.dto.EmpleadoDTO;
import co.edu.unbosque.proyectomodulofirst.entity.Empleado;
import co.edu.unbosque.proyectomodulofirst.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements CRUDOperation<EmpleadoDTO>{

	@Autowired
	private EmpleadoRepository empleadoRepo;
	
	@Autowired
	private ModelMapper mapper;

	public EmpleadoService() {

	}
	
    @Override
    public List<EmpleadoDTO> getAll() {
        List<Empleado> entityList = (List<Empleado>) empleadoRepo.findAll();
        List<EmpleadoDTO> dtoList = new ArrayList<>();

        entityList.forEach((entidad) -> {
            EmpleadoDTO dto = mapper.map(entidad, EmpleadoDTO.class);
            dtoList.add(dto);
        });

        return dtoList;
    }

    @Override
    public int deleteById(Long id) {
        Optional<Empleado> encontrado = empleadoRepo.findById(id);

        if (encontrado.isPresent()) {
            empleadoRepo.delete(encontrado.get());
            return 0;
        }

        return 1;
    }

    @Override
    public int updateById(Long id, EmpleadoDTO newData) {
        Optional<Empleado> encontrado = empleadoRepo.findById(id);
        
        if (encontrado.isPresent()) {
            Empleado temp = encontrado.get();

            temp.setNombre(newData.getNombre());
            temp.setEdad(newData.getEdad());
            temp.setFechaInicio(newData.getFechaInicio());

            empleadoRepo.save(temp);
            return 0;
        }

        return 1;
    }

    @Override
    public long count() {
        return empleadoRepo.count();
    }

    @Override
    public boolean exist(Long id) {
        return empleadoRepo.existsById(id);
    }

	@Override
	public int create(EmpleadoDTO data) {
		return 0;
	}
}
