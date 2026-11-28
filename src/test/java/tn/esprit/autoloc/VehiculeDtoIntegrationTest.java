package tn.esprit.autoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test d'intégration sur une base H2 en mémoire :
 * - démarrage du contexte (valide toutes les @Query des repositories)
 * - endpoints /vehicule/dto/*
 * - requêtes corrigées de VehiculeRepository et ReservationRepository
 */
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:autoloc_test;MODE=MySQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "logging.level.org.hibernate.SQL=WARN"
})
@Transactional
class VehiculeDtoIntegrationTest {

    @Autowired WebApplicationContext context;
    @Autowired AgenceRepository agenceRepo;
    @Autowired VehiculeRepository vehiculeRepo;
    @Autowired ClientRepository clientRepo;
    @Autowired ReservationRepository reservationRepo;

    MockMvc mvc;
    Agence agence;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        agence = agenceRepo.save(new Agence(null, "Agence Tunis", "Tunis", "Av. Bourguiba", "71000000", null, null));
    }

    @Test
    void lectureVehiculesViaDto() throws Exception {
        Vehicule dispo = vehiculeRepo.save(new Vehicule(null, "123TU4567", "Peugeot", "208", CategorieVehicule.CITADINE,
                new BigDecimal("80.00"), StatutVehicule.DISPONIBLE, agence, null, null));
        vehiculeRepo.save(new Vehicule(null, "456TU7890", "Kia", "Sportage", CategorieVehicule.SUV,
                new BigDecimal("150.00"), StatutVehicule.LOUE, null, null, null));

        // Le DTO n'expose que idVehicule, marque, modele, tarifJournalier
        mvc.perform(get("/vehicule/dto/getVehiculeId/" + dispo.getIdVehicule()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idVehicule").value(dispo.getIdVehicule()))
                .andExpect(jsonPath("$.marque").value("Peugeot"))
                .andExpect(jsonPath("$.modele").value("208"))
                .andExpect(jsonPath("$.tarifJournalier").value(80.00))
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.immatriculation").doesNotExist())
                .andExpect(jsonPath("$.agence").doesNotExist());

        mvc.perform(get("/vehicule/dto/getVehicules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
        mvc.perform(get("/vehicule/dto/disponibles"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].marque").value("Peugeot"));
        mvc.perform(get("/vehicule/dto/agence/" + agence.getId()))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void requetesRepositoriesCorrigees() {
        Vehicule v = new Vehicule(null, "999TU1", "Renault", "Clio", CategorieVehicule.CITADINE,
                new BigDecimal("70.00"), StatutVehicule.DISPONIBLE, agence, null, null);
        vehiculeRepo.save(v);
        Client c = clientRepo.save(new Client(null, "Ben Ali", "Sami", "sami@test.tn", "22000000",
                "P123", new Date(), null));
        Date demain = new Date(System.currentTimeMillis() + 86_400_000L);
        reservationRepo.save(new Reservation(null, demain, demain, StatutReservation.CONFIRMEE, v, null, c));

        assertThat(vehiculeRepo.findByCategorie(CategorieVehicule.CITADINE)).hasSize(1);
        assertThat(vehiculeRepo.findByCategorie(CategorieVehicule.SUV)).isEmpty();
        assertThat(vehiculeRepo.countVehiculesByAgence()).hasSize(1);

        assertThat(reservationRepo.getReservationsByClient(c.getIdClient())).hasSize(1);
        List<Object[]> counts = reservationRepo.countReservationsByClient();
        assertThat(counts).hasSize(1);
        assertThat(counts.get(0)[2]).isEqualTo(1L);
        assertThat(reservationRepo.findByDateDebutAfter(new Date())).hasSize(1);
    }
}
