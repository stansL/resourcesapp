package org.openmrs.module.resourcesapp.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.referenceapplication.ReferenceApplicationWebConstants;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPageController {

    public String get(UiSessionContext sessionContext,
                      PageModel model,
                      PageRequest pageRequest,
                      UiUtils ui) {
        pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL, ui.thisUrl());
        Map<String, String> types = new HashMap<String, String>();
        types.put("admission", "icon-signin");
        types.put("transfer", "icon-random");
        types.put("discharge", "icon-signout");
        types.put("vitals", "icon-vitals");
        types.put("encounter", "icon-stethoscope");
        sessionContext.requireAuthentication();
        Boolean isPriviledged = Context.hasPrivilege("Access OPD");
        if (!isPriviledged) {
            return "redirect: index.htm";
        }


        File resourcesFile = OpenmrsUtil.getDirectoryInApplicationDataDirectory("mpd");
        List<SimpleObject> objects = new ArrayList<SimpleObject>();
        for (File f : resourcesFile.listFiles()) {
            String[] nameParts = f.getName().split("_");
            for (int i = 0; i < nameParts.length; i++) {
                System.out.println(nameParts[i]);
            }

            SimpleObject simpleObject = SimpleObject.create("displayName", nameParts[0],
                    "shortDescription", nameParts[1],
                    "version", nameParts[2],
                    "icon", types.get(nameParts[3].split("\\.(?=[^\\.]+$)")[0]));
            objects.add(simpleObject);
        }

        model.addAttribute("files", objects);


//        model.addAttribute("patientId", patientId);
        return null;
    }
}