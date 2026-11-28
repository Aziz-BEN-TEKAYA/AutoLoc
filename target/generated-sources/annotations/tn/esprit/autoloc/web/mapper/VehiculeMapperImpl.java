package tn.esprit.autoloc.web.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.web.dto.VehiculeDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-28T09:50:23+0100",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.9 (Oracle Corporation)"
)
@Component
public class VehiculeMapperImpl implements VehiculeMapper {

    @Override
    public VehiculeDTO toDTO(Vehicule vehicule) {
        if ( vehicule == null ) {
            return null;
        }

        VehiculeDTO.VehiculeDTOBuilder vehiculeDTO = VehiculeDTO.builder();

        vehiculeDTO.idVehicule( vehicule.getIdVehicule() );
        vehiculeDTO.marque( vehicule.getMarque() );
        vehiculeDTO.modele( vehicule.getModele() );
        vehiculeDTO.tarifJournalier( vehicule.getTarifJournalier() );

        return vehiculeDTO.build();
    }

    @Override
    public List<VehiculeDTO> toDTOList(List<Vehicule> vehicules) {
        if ( vehicules == null ) {
            return null;
        }

        List<VehiculeDTO> list = new ArrayList<VehiculeDTO>( vehicules.size() );
        for ( Vehicule vehicule : vehicules ) {
            list.add( toDTO( vehicule ) );
        }

        return list;
    }
}
