package org.openmrs.module.resourcesapp.fragment.controller;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.PatientDashboardService;
import org.openmrs.module.hospitalcore.model.OpdDrugOrder;
import org.openmrs.module.hospitalcore.util.PatientDashboardConstants;
import org.openmrs.module.resourcesapp.model.VisitDetail;
import org.openmrs.module.resourcesapp.model.VisitSummary;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

public class VisitSummaryFragmentController {

	private static final int ORDER_LOCATION_ID = 1;

	public void controller(FragmentConfiguration config,
			FragmentModel model) {
		config.require("patientId");
		Integer patientId = Integer.parseInt(config.get("patientId").toString());
        PatientDashboardService dashboardService = Context.getService(PatientDashboardService.class);
        Location location = Context.getLocationService().getLocation(ORDER_LOCATION_ID);

        Patient patient = Context.getPatientService().getPatient(patientId);

        AdministrationService administrationService = Context.getAdministrationService();
        String gpOPDEncType = administrationService.getGlobalProperty(PatientDashboardConstants.PROPERTY_OPD_ENCOUTNER_TYPE);
        EncounterType labOPDType = Context.getEncounterService().getEncounterType(gpOPDEncType);
        List<Encounter> encounters = dashboardService.getEncounter(patient, location, labOPDType, null);
        
        List<VisitSummary> visitSummaries = new ArrayList<VisitSummary>();

        int i=0;
        
        for (Encounter enc : encounters) {
            VisitSummary visitSummary = new VisitSummary();
            visitSummary.setVisitDate(enc.getDateCreated());
            visitSummary.setEncounterId(enc.getEncounterId());
            String outcomeConceptName = Context.getAdministrationService().getGlobalProperty(PatientDashboardConstants.PROPERTY_VISIT_OUTCOME);
            Concept outcomeConcept = Context.getConceptService().getConcept(outcomeConceptName);
            for (Obs obs : enc.getAllObs()) {
                if (obs.getConcept().equals(outcomeConcept)) {
                    visitSummary.setOutcome(obs.getValueText());
                }
            }
            visitSummaries.add(visitSummary);

            i++;

            if (i >=20){
                break;
            }
        }
        model.addAttribute("patient", patient);
        model.addAttribute("visitSummaries", visitSummaries);
	}

	public SimpleObject getVisitSummaryDetails(
			@RequestParam("encounterId") Integer encounterId,
			UiUtils ui
			) {
		Encounter encounter = Context.getEncounterService().getEncounter(encounterId);
		VisitDetail visitDetail = VisitDetail.create(encounter);
		
		SimpleObject detail = SimpleObject.fromObject(visitDetail, ui, "history","diagnosis", "symptoms", "procedures", "investigations","physicalExamination","visitOutcome","internalReferral","externalReferral");
		
		List<OpdDrugOrder> opdDrugs = Context.getService(PatientDashboardService.class).getOpdDrugOrder(encounter);
		List<SimpleObject> drugs = SimpleObject.fromCollection(opdDrugs, ui, "inventoryDrug.name",
				"inventoryDrug.unit.name", "inventoryDrugFormulation.name", "inventoryDrugFormulation.dozage","dosage", "dosageUnit.name");
		return SimpleObject.create("notes", detail, "drugs", drugs);
	}
}
