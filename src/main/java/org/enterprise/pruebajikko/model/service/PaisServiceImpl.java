package org.enterprise.pruebajikko.model.service;

import org.enterprise.pruebajikko.model.entity.Pais;
import org.enterprise.pruebajikko.model.repository.PaisRepository;
import org.enterprise.pruebajikko.view.dto.PaisDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaisServiceImpl implements IPaisService{

  private final PaisRepository paisRepository;

  public PaisServiceImpl(PaisRepository paisRepository) {
    this.paisRepository = paisRepository;
  }

  @Override
  public  List<PaisDto> findPais(String pais) {
    List<PaisDto> paisesInfo = new ArrayList<>();
    PaisDto paisInfo = null;
    Long poblacionTotal = 0L;
    List<Object> result = paisRepository.findById(pais);

    for (Object item : result) {
      Object[] fields = (Object[]) item;
      Boolean capital = (Boolean) fields[4];
      Long poblacionDep = (Long) fields[2];
      poblacionTotal += poblacionDep;

      if (capital) {
        paisInfo = PaisDto.builder()
          .nombre((String) fields[0])
          .moneda((String) fields[1])
          .capital((String) fields[3])
          .build();
      }
    }

    if (Objects.nonNull(paisInfo)) {
      paisInfo.setPoblacion(poblacionTotal);
      paisesInfo.add(paisInfo);
    }
    return paisesInfo;
  }

  @Override
  public void createPais(Pais pais) {
    paisRepository.save(pais);
  }

  @Override
  public void deletePais(Integer pais) {
    paisRepository.deleteById(pais);
  }

  @Override
  public void updatePais(Pais pais) {
    paisRepository.save(pais);
  }

  @Override
  public Pais findById(Integer id) {
    return paisRepository.findById(id).orElse(null);
  }


}
