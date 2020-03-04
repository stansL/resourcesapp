package org.openmrs.module.resourcesapp.fragment.controller;

import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;


public class ResourcesFragmentController {
    public String loadStuff(
            FragmentConfiguration config,
            FragmentModel model,
            UiUtils uiutils
    ) {
        config.require("fileName");


        String fileName = config.get("fileName").toString();
        System.out.println("TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
        System.out.println(fileName);
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
        return "redirect:" + fileName;
    }

}
